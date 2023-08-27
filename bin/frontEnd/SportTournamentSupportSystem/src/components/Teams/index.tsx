import React from 'react';
import { connect } from 'react-redux';
import Skeleton from 'react-loading-skeleton';
import UserInfoTeamsItem from 'components/UserInfoTeamsItem';
import TextInput from 'components/TextInput';
import { IBigRequest, IParams } from 'interfaces/common';
import { IState } from 'redux-saga/reducers';
import { queryListTeams, addATeamToCompetition } from './actions';
import './styles.css';

interface IUserInfoTeamsProps extends React.ClassAttributes<UserInfoTeams> {
  id: number;
  type: 'user' | 'competition';
  listTeam: IParams[] | null;
  tournamentInfo: IParams | null;
  competitionInfo: IParams | null;
  currentUserInfo: IParams | null;

  queryListTeams(param: IBigRequest): void;
  addATeamToCompetition(param: IBigRequest): void;
}

interface IUserInfoTeamsState {
  teamNameToAdd: string;
  teamNameToAddError: boolean;
  teamNameToAddErrorContent: string;
  teamShortNameToAdd: string;
  teamShortNameToAddError: boolean;
  teamShortNameToAddErrorContent: string;
}

class UserInfoTeams extends React.Component<IUserInfoTeamsProps, IUserInfoTeamsState> {
  constructor(props: IUserInfoTeamsProps) {
    super(props);
    this.state = {
      teamNameToAdd: '',
      teamNameToAddError: false,
      teamNameToAddErrorContent: '',
      teamShortNameToAdd: '',
      teamShortNameToAddError: false,
      teamShortNameToAddErrorContent: '',
    };
  }

  componentDidMount() {
    this.requestData();
  }

  private requestData = () => {
    const params = {
      path: '',
      param: {
        ...this.props.type === 'user' ? { userId: this.props.id } : { competitionId: this.props.id },
        limit: 999,
      },
      data: {},
    };
    this.props.queryListTeams(params);
  }

  private validate = () => {
    let teamNameToAddError = false;
    let teamNameToAddErrorContent = '';
    let teamShortNameToAddError = false;
    let teamShortNameToAddErrorContent = '';
    if (this.state.teamNameToAdd.trim() === '') {
      teamNameToAddError = true;
      teamNameToAddErrorContent = 'Tên đội không được trống';
    }
    if (this.state.teamShortNameToAdd.trim() === '') {
      teamNameToAddError = true;
      teamNameToAddErrorContent = 'Tên ngắn đội không được trống';
    }
    let listTemp = [];
    if (this.props.listTeam!.map((item, index) => { if (item.fullName === this.state.teamNameToAdd) { listTemp.push(item); } })) {
      if (listTemp.length > 0) {
        teamNameToAddError = true;
        teamNameToAddErrorContent = 'Tên đội đã tồn tại trong cuộc thi';
      }
    }
    listTemp = [];
    if (this.props.listTeam!.map((item, index) => { if (item.shortName === this.state.teamShortNameToAdd) { listTemp.push(item); } })) {
      if (listTemp.length > 0) {
        teamShortNameToAddError = true;
        teamShortNameToAddErrorContent = 'Tên ngắn đội đã tồn tại trong cuộc thi';
      }
    }

    return { teamNameToAddError, teamNameToAddErrorContent, teamShortNameToAddError, teamShortNameToAddErrorContent };
  }

  private handleAddATeam = () => {
    const { teamNameToAddError, teamNameToAddErrorContent, teamShortNameToAddError, teamShortNameToAddErrorContent } = this.validate();
    this.setState({
      teamNameToAddError,
      teamNameToAddErrorContent,
      teamShortNameToAddError,
      teamShortNameToAddErrorContent
    });
    if (teamNameToAddError === true || teamShortNameToAddError === true) {
      return;
    }
    const params = {
      path: '',
      param: {},
      data: {
        fullName: this.state.teamNameToAdd,
        shortName: this.state.teamShortNameToAdd,
        description: '',
        creatorId: this.props.currentUserInfo != null && this.props.currentUserInfo.id,
        competitionId: this.props.id,
        status: 'joined',
        url: '',
      },
    };
    this.props.addATeamToCompetition(params);
    this.setState({ teamNameToAdd: '', teamShortNameToAdd: '', });
  }

  private onChangeTeamNameToAdd = (value: string) => {
    this.setState({ teamNameToAdd: value, });
  }

  private onChangeTeamShortNameToAdd = (value: string) => {
    this.setState({ teamShortNameToAdd: value, });
  }

  render() {
    return (
      <div className="UserInfoTeams-container">
        {this.props.listTeam ? (this.props.listTeam.length > 0 ?
          this.props.listTeam.map(
            (item, index) => <UserInfoTeamsItem competitionInfo={this.props.competitionInfo} tournamentInfo={this.props.tournamentInfo} info={item} index={index} key={index} />
          ) : <p>Không tìm thấy đội nào!</p>) :
          <Skeleton />
        }
        {this.props.listTeam != null && <div className="UserInfoTeamsAddItem-container2">
          <div className="UserInfoTeamsAddItem-container-container">
            <div className="UserInfoTeamsAddItem-container-container-container">
              <TextInput style={{ width: 200 }} label={'Nhập tên đội'} value={this.state.teamNameToAdd} error={this.state.teamNameToAddError} errorContent={this.state.teamNameToAddErrorContent} onChangeText={this.onChangeTeamNameToAdd} />
              <TextInput style={{ width: 200 }} label={'Nhập tên ngắn đội'} value={this.state.teamShortNameToAdd} error={this.state.teamShortNameToAddError} errorContent={this.state.teamShortNameToAddErrorContent} onChangeText={this.onChangeTeamShortNameToAdd} />
              <div className="UserInfoTeamsAddItem-login-container">
                <div className="UserInfoTeamsAddItem-login" onClick={this.handleAddATeam}>
                  <h4 className="UserInfoTeamsAddItem-login-text">Thêm đội</h4>
                </div>
              </div>
            </div>
          </div>
        </div>}
      </div>
    );
  }
}

const mapStateToProps = (state: IState) => {
  return {
    listTeam: state.listTeam,
    currentUserInfo: state.currentUserInfo,
  };
};

export default connect(
  mapStateToProps,
  { queryListTeams, addATeamToCompetition }
)(UserInfoTeams);