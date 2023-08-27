import { call, takeLatest, put } from 'redux-saga/effects';
import { query, METHOD } from 'utils/socketApi';
import { IRequest, IParams, IBigRequest } from 'interfaces/common';
import { COMMON_SHOW_NOTIFICATION, EDIT_COMPETITION_INFO } from 'redux-saga/actions';
import store from 'redux-saga/store';
import { QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_SUCCESS } from 'components/CompetitionsSetting/reducers';


const editCompetitionInfo = (data: IParams, path: string | number, param: IParams) => {
  const uri = 'competition';
  const datas = { ...data };
  const paths = path;
  const params = { ...param };
  return query(uri, METHOD.PUT, datas, params, paths);
};

function* doEditCompetitionInfo(request: IRequest<IBigRequest>) {
  try {
    const response = yield call(editCompetitionInfo, request.data.data, request.data.path, request.data.param);
    const data = response.data.result;
    if (response.data.error.MessageCode === 0) {
      yield put({
        type: request.response.success,
        payload: data,
      });
      store.getState().allCompetitionByTournamentId![request.data.data.indexOfEdit as number] = data.Competition;
      const newArray = store.getState().allCompetitionByTournamentId != null ? store.getState().allCompetitionByTournamentId?.splice(0) : [];
      newArray![request.data.data.indexOfEdit as number] = data.Competition;
      yield put({
        type: QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_SUCCESS,
        payload: newArray,
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
        title: 'EditCompetitionInfo',
        content: error,
        time: new Date(),
      },
    });
  }
}

export default function* watchEditCompetitionInfo() {
  yield takeLatest(EDIT_COMPETITION_INFO, doEditCompetitionInfo);
}
