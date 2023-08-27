import { IBigRequest } from "interfaces/common";
import { QUERY_LIST_PLAYER_OF_TEAM } from "redux-saga/actions";
import { QUERY_LIST_PLAYER_OF_TEAM_SUCCESS, QUERY_LIST_PLAYER_OF_TEAM_FAILED } from "./reducers";

export const queryListPlayerOfTeam = (data: IBigRequest) => ({
  type: QUERY_LIST_PLAYER_OF_TEAM,
  response: {
    success: QUERY_LIST_PLAYER_OF_TEAM_SUCCESS,
    failed: QUERY_LIST_PLAYER_OF_TEAM_FAILED,
  },
  data: {
    path: data.path,
    param: data.param,
    data: data.data,
  },
});