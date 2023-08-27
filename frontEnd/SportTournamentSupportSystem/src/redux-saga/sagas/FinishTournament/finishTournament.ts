import { call, put, takeLatest } from 'redux-saga/effects';
import { IParams, IRequest, IBigRequest } from 'interfaces/common';
import store from 'redux-saga/store';
import { COMMON_SHOW_NOTIFICATION, FINISH_TOURNAMENT } from 'redux-saga/actions';
import { QUERY_TOURNAMENT_INFO_SUCCESS } from 'screens/TournamentInfo/reducers';
import { query, METHOD } from 'utils/socketApi';

const finishTournament = (data: IParams, path: string | number, param: IParams) => {
  const uri = 'tournament/finish';
  const datas = { ...data };
  const paths = path;
  const params = { ...param };
  return query(uri, METHOD.POST, datas, params, paths);
};

function* doFinishTournament(request: IRequest<IBigRequest>) {
  try {
    const response = yield call(finishTournament, request.data.data, request.data.path, request.data.param);
    const data = response.data.result;
    if (response.data.error.MessageCode === 0) {
      yield put({
        type: request.response.success,
        payload: data,
      });
      yield put({
        type: QUERY_TOURNAMENT_INFO_SUCCESS,
        payload: { ...store.getState().tournamentInfo, Tournament: data.Tournament },
      });
    } else {
      throw new Error(response.data.error.Message);
    }
  } catch (error) {
    yield put({
      type: request.response.failed,
    });
    yield put({
      type: COMMON_SHOW_NOTIFICATION,
      data: {
        type: 'error',
        title: 'FinishTournament',
        content: error,
        time: new Date(),
      },
    });
  }
}

export default function* watchFinishTournament() {
  yield takeLatest(FINISH_TOURNAMENT, doFinishTournament);
}
