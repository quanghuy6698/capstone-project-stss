import { all } from 'redux-saga/effects';
import { login } from './sagas/Login';
import { signUp } from './sagas/SignUp';
import { showNotification } from './sagas/Common';
import { queryBracketBoardInfo } from './sagas/BracketBoard';
import { queryUserInfo } from './sagas/QueryUserInfo';
import { logOut } from './sagas/LogOut';
import { queryTournamentInfo } from './sagas/QueryTournamentInfo';
import { isUsernameExisted } from './sagas/IsUsernameExisted';
import { queryListTournamentsOfUser } from './sagas/QueryListTournamentsOfUser';
import { queryListTeams } from './sagas/QueryListTeams';
import { createNewTournament } from './sagas/CreateNewTournament';
import { editUserInfo } from './sagas/EditUserInfo';
import { editTournamentInfo } from './sagas/EditTournamentInfo';
import { queryListCompetitionOfTournament } from './sagas/QueryListCompetitionOfTournament';
import { addACompetition } from './sagas/AddACompetition';
import { editCompetitionInfo } from './sagas/EditCompetitionInfo';
import { isEmailExisted } from './sagas/IsEmailExisted';
import { queryListTournaments } from './sagas/QueryListTournament';
import { queryListPlayerOfTeam } from './sagas/QueryListPlayerOfTeam';
import { activeAccount } from './sagas/ActiveAccount';
import { searchTournament } from './sagas/SearchTournaments';
import { updateBackground } from './sagas/UpdateBackground';
import { queryListUsers } from './sagas/QueryListUsers';
import { searchUsers } from './sagas/SearchUsers';
import { updateAvatar } from './sagas/UpdateAvatar';
import { querySportsByTournament } from './sagas/QuerySportsByTournament';
import { queryCompetitionsBySportAndTournament } from './sagas/QueryCompetitionsBySportAndTournament';
import { addATeamToCompetition } from './sagas/AddATeamToCompetition';
import { updateAvatarTournament } from './sagas/UpdateAvatarTournament';
import { updateBackgroundTournament } from './sagas/UpdateBackgroundTournament';
import { addListTeamSelecting } from './sagas/AddListTeamSelecting';
import { swapTwoTeamInBracket } from './sagas/SwapTwoTeamInBracket';
import { deleteListSelectingTeam } from './sagas/DeleteListSelectingTeam';

export default function* () {
  yield all([
    login(),
    signUp(),
    showNotification(),
    queryBracketBoardInfo(),
    queryUserInfo(),
    logOut(),
    queryTournamentInfo(),
    isUsernameExisted(),
    isEmailExisted(),
    queryListTournamentsOfUser(),
    queryListTournaments(),
    queryListTeams(),
    createNewTournament(),
    editUserInfo(),
    editTournamentInfo(),
    queryListCompetitionOfTournament(),
    addACompetition(),
    editCompetitionInfo(),
    queryListPlayerOfTeam(),
    activeAccount(),
    searchTournament(),
    updateBackground(),
    updateAvatar(),
    queryListUsers(),
    searchUsers(),
    querySportsByTournament(),
    queryCompetitionsBySportAndTournament(),
    addATeamToCompetition(),
    updateAvatarTournament(),
    updateBackgroundTournament(),
    addListTeamSelecting(),
    swapTwoTeamInBracket(),
    deleteListSelectingTeam(),
  ]);
}