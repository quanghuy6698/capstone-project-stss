import { IParams, IAction } from "interfaces/common";

export const QUERY_LIST_PLAYER_OF_TEAM_SUCCESS = 'QUERY_LIST_PLAYER_OF_TEAM_SUCCESS';
export const QUERY_LIST_PLAYER_OF_TEAM_FAILED = 'QUERY_LIST_PLAYER_OF_TEAM_FAILED';

export function ListPlayerOfTeam(state: IParams[] | null = null, action: IAction<IParams[] | null>) {
  switch (action.type) {
    case QUERY_LIST_PLAYER_OF_TEAM_SUCCESS:
      return action.payload;
    case QUERY_LIST_PLAYER_OF_TEAM_FAILED:
      return null;
    default:
      return state;
  }
}