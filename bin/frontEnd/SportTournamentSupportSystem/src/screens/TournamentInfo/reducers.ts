import { IAction, IParams } from "interfaces/common";

export const QUERY_TOURNAMENT_INFO_SUCCESS = 'QUERY_TOURNAMENT_INFO_SUCCESS';
export const QUERY_TOURNAMENT_INFO_FAILED = 'QUERY_TOURNAMENT_INFO_FAILED';
export const QUERY_SPORTS_BY_TOURNAMENT_SUCCESS = 'QUERY_SPORTS_BY_TOURNAMENT_SUCCESS';
export const QUERY_SPORTS_BY_TOURNAMENT_FAILED = 'QUERY_SPORTS_BY_TOURNAMENT_FAILED';
export const QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_SUCCESS = 'QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_SUCCESS';
export const QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_FAILED = 'QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_FAILED';
export const START_TOURNAMENT_SUCCESS = 'START_TOURNAMENT_SUCCESS';
export const START_TOURNAMENT_FAILED = 'START_TOURNAMENT_FAILED';
export const FINISH_TOURNAMENT_SUCCESS = 'FINISH_TOURNAMENT_SUCCESS';
export const FINISH_TOURNAMENT_FAILED = 'FINISH_TOURNAMENT_FAILED';
export const TOURNAMENT_UPDATE_AVATAR_SUCCESS = 'TOURNAMENT_UPDATE_AVATAR_SUCCESS';
export const TOURNAMENT_UPDATE_AVATAR_FAILED = 'TOURNAMENT_UPDATE_AVATAR_FAILED';
export const TOURNAMENT_UPDATE_BACKGROUND_SUCCESS = 'TOURNAMENT_UPDATE_BACKGROUND_SUCCESS';
export const TOURNAMENT_UPDATE_BACKGROUND_FAILED = 'TOURNAMENT_UPDATE_BACKGROUND_FAILED';

export function TournamentInfo(state: IParams | null = null, action: IAction<IParams>) {
  switch (action.type) {
    case QUERY_TOURNAMENT_INFO_SUCCESS:
      return action.payload;
    case QUERY_TOURNAMENT_INFO_FAILED:
      return null;
    default:
      return state;
  }
}

export function ListSportsByTournament(state: IParams[] | null = null, action: IAction<IParams[]>) {
  switch (action.type) {
    case QUERY_SPORTS_BY_TOURNAMENT_SUCCESS:
      return action.payload;
    case QUERY_SPORTS_BY_TOURNAMENT_FAILED:
      return null;
    default:
      return state;
  }
}

export function ListCompetitionsBySportAndTournament(state: IParams[] | null = null, action: IAction<IParams[]>) {
  switch (action.type) {
    case QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_SUCCESS:
      return action.payload;
    case QUERY_COMPETITIONS_BY_SPORT_AND_TOURNAMENT_FAILED:
      return null;
    default:
      return state;
  }
}