import { IBigRequest } from "interfaces/common";
import { QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID, ADD_A_COMPETITION } from "redux-saga/actions";
import { QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_SUCCESS, QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_FAILED, ADD_A_COMPETITION_SUCCESS, ADD_A_COMPETITION_FAILED } from "./reducers";

export const queryAllCompetitionsByTournamentId = (data: IBigRequest) => ({
  type: QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID,
  response: {
    success: QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_SUCCESS,
    failed: QUERY_ALL_COMPETITION_BY_TOURNAMENT_ID_FAILED,
  },
  data: {
    path: data.path,
    param: data.param,
    data: data.data,
  },
});

export const addACompetition = (data: IBigRequest) => ({
  type: ADD_A_COMPETITION,
  response: {
    success: ADD_A_COMPETITION_SUCCESS,
    failed: ADD_A_COMPETITION_FAILED,
  },
  data: {
    path: data.path,
    param: data.param,
    data: data.data,
  },
});