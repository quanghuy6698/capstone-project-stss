import { IBigRequest } from "interfaces/common";
import { EDIT_COMPETITION_INFO } from "redux-saga/actions";
import { EDIT_COMPETITION_INFO_SUCCESS, EDIT_COMPETITION_INFO_FAILED } from "./reducers";

export const editCompetitionInfo = (data: IBigRequest) => ({
  type: EDIT_COMPETITION_INFO,
  response: {
    success: EDIT_COMPETITION_INFO_SUCCESS,
    failed: EDIT_COMPETITION_INFO_FAILED,
  },
  data: {
    path: data.path,
    param: data.param,
    data: data.data,
  },
});