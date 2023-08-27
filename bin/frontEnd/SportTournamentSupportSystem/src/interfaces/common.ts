import { AnyAction } from "redux";
import * as toast from 'react-toastify';
import { MATCH_TYPE } from "global";

export type TypeAny =
  | undefined
  | Function
  | null
  | string
  | number
  | FormData
  | boolean
  | string[]
  | number[]
  | FileList
  | Date
  | FormData
  | INotification
  | INotification[]
  | IBracketMatchInfo[]
  | IBracketMatchInfo
  | IBracketTeamInfo
  | IBracketTeamInfo[]
  | ITeamInfo
  | ITeamInfo[]
  | IBracketRoundInfo
  | IBracketRoundInfo[]
  | IBracketBoardInfo
  | IBracketBoardInfo[];

export interface IParams {
  [s: string]: TypeAny;
}

export interface IBigRequest {
  path: string | number;
  param: IParams;
  data: IParams;
}

export interface IRequest<T> extends AnyAction {
  response: IResponseType;
  data: T;
}

export interface IResponseType {
  success: string;
  failed: string;
}

export interface INotification {
  type: toast.TypeOptions;
  title: string;
  content: string | React.ReactNode;
  contentParams?: { [s: string]: TypeAny };
  time: Date;
  option?: toast.ToastOptions;
  ignore?: boolean;
  showNotification?: boolean;
}

export interface IAction<T> {
  type: string;
  payload: T;
  data?: T;
}

export interface IResponse<T> {
  data: T;
  status?: IParams;
}

export interface IBracketMatchInfo {
  id: string;
  type: MATCH_TYPE;
  numericalOrderMatch: number;
  listTeam: IBracketTeamInfo[];
  time?: string;
  location?: string;
  editable: boolean;
}

export interface IBracketTeamInfo {
  teamInfo?: ITeamInfo;
  top?: '1' | '2' | '3' | 'in10' | 'other';
  score?: string;
}

export interface ITeamInfo {
  id: string;
  name?: string;
}

export interface IBracketRoundInfo {
  roundName: string;
  roundNumber: number;
  listMatch: IBracketMatchInfo[];
}

export interface IBracketBoardInfo {
  id: string;
  listRound: IBracketRoundInfo[];
  started: boolean;
}