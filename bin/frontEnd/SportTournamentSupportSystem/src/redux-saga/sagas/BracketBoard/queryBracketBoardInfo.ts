import { call, takeLatest, put } from 'redux-saga/effects';
import { query, METHOD } from 'utils/socketApi';
import { IRequest, IParams, IBigRequest } from 'interfaces/common';
import { GET_BRACKET_BOARD_INFO } from 'redux-saga/actions';

const queryBracketBoardInfo = (data: IParams, path: string | number, param: IParams) => {
  const uri = 'schedule';
  const datas = { ...data };
  const paths = path;
  const params = { ...param };
  return query(uri, METHOD.GET, datas, params, paths);
};

let listMatchesFull: IParams[] = [];
let listWinMatchesFull: IParams[] = [];
let listLoseMatchesFull: IParams[] = [];
let listRound: IParams[] = [];
let listWinRound: IParams[] = [];
let listLoseRound: IParams[] = [];

const DFS = (node: IParams, number: number, maxRound: number, fakeId?: number) => {
  if (node.left != null) {
    if (number === 3) {
      if ((node.degree as number) % 2 === 0) {
        DFS(node.left as IParams, number, maxRound, fakeId);
      } else {
        DFS(node.left as IParams, number, maxRound, fakeId! * 2);
      }
    } else {
      DFS(node.left as IParams, number, maxRound);
    }
  } else if (node.left == null && (((node.data as IParams).roundNo as number) >= 2 && ((node.data as IParams).roundNo as number) < maxRound) && number !== 3) {
    node.left = {
      id: -1,
      data: {
        loser: null,
        matchNo: -1,
        name: '',
        roundNo: ((node.data as IParams).roundNo as number) - 1,
        status: null,
        team1: null,
        team1Description: null,
        team2: null,
        team2Description: null,
        winner: null,
      },
      degree: null,
      left: null,
      right: null,
    } as IParams;
    DFS(node.left as IParams, number, maxRound);
  }
  if (node.right != null) {
    if (number === 3) {
      if ((node.degree as number) % 2 === 0) {
        DFS(node.right as IParams, number, maxRound, fakeId);
      } else {
        DFS(node.right as IParams, number, maxRound, (fakeId! * 2) + 1);
      }
    } else {
      DFS(node.right as IParams, number, maxRound);
    }
  } else if (node.right == null && (((node.data as IParams).roundNo as number) >= 2 && ((node.data as IParams).roundNo as number) < maxRound) && number !== 3) {
    node.right = {
      id: -1,
      data: {
        loser: null,
        matchNo: -1,
        name: '',
        roundNo: ((node.data as IParams).roundNo as number) - 1,
        status: null,
        team1: null,
        team1Description: null,
        team2: null,
        team2Description: null,
        winner: null,
      },
      degree: null,
      left: null,
      right: null,
    } as IParams;
    DFS(node.right as IParams, number, maxRound);
  }
  if (number === 1) {
    listMatchesFull.push(node);
  } else if (number === 2) {
    listWinMatchesFull.push(node);
  } else if (number === 3) {
    if (((node.data as IParams).name as string).includes('B')) {
      listLoseMatchesFull.push({ ...node, fakeId });
    }
  }
}

function* doQueryBracketBoardInfo(request: IRequest<IBigRequest>) {
  try {
    const response = yield call(queryBracketBoardInfo, request.data.data, request.data.path, request.data.param);
    const data = response.data.result;
    if (response.data.error.MessageCode === 0) {
      if (data.Schedule != null) {
        if (data.Schedule.WinBranch == null) {
          yield call(DFS, data.Schedule.Bracket.root, 1, data.Schedule.Bracket.root.data.roundNo);
          let listMatches = [];
          for (let i = 1; i <= data.Schedule.Bracket.root.data.roundNo; i++) {
            for (let j = 0; j < listMatchesFull.length; j++) {
              if ((listMatchesFull[j].data as IParams).roundNo === i) {
                listMatches.push(listMatchesFull[j]);
              }
            }
            listRound.push({ listMatches });
            listMatches = [];
          }
          yield put({
            type: request.response.success,
            payload: { listRound },
          });
          listRound = [];
          listMatchesFull = [];
        } else {
          yield call(DFS, data.Schedule.WinBranch.root, 2, data.Schedule.WinBranch.root.data.roundNo);
          let listWinMatches = [];
          for (let i = 1; i <= data.Schedule.WinBranch.root.data.roundNo; i++) {
            for (let j = 0; j < listWinMatchesFull.length; j++) {
              if ((listWinMatchesFull[j].data as IParams).roundNo === i) {
                listWinMatches.push(listWinMatchesFull[j]);
              }
            }
            listWinRound.push({ listWinMatches });
            listWinMatches = [];
          }
          yield call(DFS, data.Schedule.LoseBranch.root, 3, data.Schedule.LoseBranch.root.data.roundNo, 1);
          let listLoseMatches = [];
          for (let i = 1; i <= data.Schedule.LoseBranch.root.data.roundNo; i++) {
            for (let j = 0; j < listLoseMatchesFull.length; j++) {
              if ((listLoseMatchesFull[j].data as IParams).roundNo === i) {
                listLoseMatches.push(listLoseMatchesFull[j]);
              }
            }
            listLoseRound.push({ listLoseMatches });
            listLoseMatches = [];
          }
          yield put({
            type: request.response.success,
            payload: { listWinRound, listLoseRound },
          });
          listWinRound = [];
          listWinMatchesFull = [];
          listLoseRound = [];
          listLoseMatchesFull = [];
        }

      }
    } else {
      throw new Error(response.data.error.Message);
    }
  } catch (error) {
    yield put({
      type: request.response.failed,
    });
  }
}

export default function* watchQueryBracketBoardInfo() {
  yield takeLatest(GET_BRACKET_BOARD_INFO, doQueryBracketBoardInfo);
}
