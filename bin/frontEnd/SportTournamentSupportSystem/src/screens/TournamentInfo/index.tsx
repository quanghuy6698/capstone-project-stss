import React, { ReactNode } from 'react';
import { connect } from 'react-redux';
import Select, { ValueType, OptionTypeBase } from 'react-select';
import { RouteComponentProps } from 'react-router-dom';
import * as H from 'history';
import { StaticContext } from 'react-router';
import Skeleton from 'react-loading-skeleton';
import { AiFillCamera } from 'react-icons/ai';
import BracketBoard from 'components/BracketBoard';
import CustomTab from 'components/CustomTab';
import BracketSchedule from 'components/BracketSchedule';
import BracketRank from 'components/BracketRank';
import TournamentListTeam from 'components/TournamentListTeam';
import TournamentSetting from 'components/TournamentSetting';
import CompetitionsSetting from 'components/CompetitionsSetting';
import { IBigRequest, IParams } from 'interfaces/common';
import { COOKIES_TYPE } from 'global';
import { IState } from 'redux-saga/reducers';
import { cookies } from 'utils/cookies';
import { formatDateToDisplay } from 'utils/datetime';
import { onEditBracketMode, deleteListSelectingTeam } from 'components/BracketTeam/actions';
import { updateBackgroundTournament, updateAvatarTournament, queryTournamentInfo, querySportsByTournament, finishTournament, queryCompetitionsBySportAndTournament, startTournament } from './actions';
import './styles.css';

interface ITournamentInfoProps extends React.ClassAttributes<TournamentInfo> {
  routerInfo: RouteComponentProps<any, StaticContext, H.LocationState>;
  tournamentInfo: IParams | null;
  listSportsByTournament: IParams[] | null;
  listCompetitionsBySportAndTournament: IParams[] | null;

  queryTournamentInfo(param: IBigRequest): void;
  querySportsByTournament(param: IBigRequest): void;
  queryCompetitionsBySportAndTournament(param: IBigRequest): void;
  startTournament(param: IBigRequest): void;
  finishTournament(param: IBigRequest): void;
  updateAvatarTournament(param: IBigRequest): void;
  updateBackgroundTournament(param: IBigRequest): void;
  onEditBracketMode(status: boolean): void;
  deleteListSelectingTeam(): void;
}

interface ITournamentInfoState {
  selectedSport: ValueType<OptionTypeBase>;
  selectedCompetition: ValueType<OptionTypeBase>;
}

let sportOptions: IParams[] = [
];

let competitionOptions: IParams[] = [
];

class TournamentInfo extends React.Component<ITournamentInfoProps, ITournamentInfoState> {
  private tabList: string[] = [];
  private componentList: ReactNode[] = [];

  constructor(props: ITournamentInfoProps) {
    super(props);
    this.state = {
      selectedSport: null,
      selectedCompetition: null,
    };
  }

  shouldComponentUpdate(nextProps: ITournamentInfoProps, nextState: ITournamentInfoState) {
    if (this.props.tournamentInfo !== nextProps.tournamentInfo || nextState.selectedCompetition !== this.state.selectedCompetition) {
      if (nextProps.tournamentInfo == null || nextProps.tournamentInfo.Tournament == null) {
        this.tabList = [];
        this.componentList = [];
      } else if (nextState.selectedCompetition != null) {
        if (nextProps.tournamentInfo != null && (nextProps.tournamentInfo as IParams).Config != null && ((nextProps.tournamentInfo as IParams).Config as IParams).canEdit === true) {
          this.tabList = ['Nhánh thi đấu', 'Lịch thi đấu', 'Bảng xếp hạng', 'Thông tin', 'Danh sách các đội', 'Cài đặt', 'Cài đặt các cuộc thi'];
          this.componentList = [<BracketBoard competitionId={(nextState.selectedCompetition as IParams).value as number} />, <BracketSchedule competitionId={(nextState.selectedCompetition as IParams).value as number} />, <BracketRank />, <div />, <TournamentListTeam id={Number(this.props.routerInfo.match.params.tournamentId)} />, <TournamentSetting tournamentInfo={nextProps.tournamentInfo.Tournament as IParams} tournamentId={Number(this.props.routerInfo.match.params.tournamentId)} />, <CompetitionsSetting tournamentInfo={nextProps.tournamentInfo as IParams} tournamentId={Number(this.props.routerInfo.match.params.tournamentId)} onChangeCompetitionSetting={this.onChangeCompetitionSetting} />];
        } else {
          this.tabList = ['Nhánh thi đấu', 'Lịch thi đấu', 'Bảng xếp hạng', 'Thông tin', 'Danh sách các đội'];
          this.componentList = [<BracketBoard competitionId={(nextState.selectedCompetition as IParams).value as number} />, <BracketSchedule competitionId={(nextState.selectedCompetition as IParams).value as number} />, <BracketRank />, <div />, <TournamentListTeam id={Number(this.props.routerInfo.match.params.tournamentId)} />];
        }
      } else {
        if (nextProps.tournamentInfo != null && (nextProps.tournamentInfo as IParams).Config != null && ((nextProps.tournamentInfo as IParams).Config as IParams).canEdit === true) {
          this.tabList = ['Cài đặt', 'Cài đặt các cuộc thi'];
          this.componentList = [<TournamentSetting tournamentId={Number(this.props.routerInfo.match.params.tournamentId)} tournamentInfo={nextProps.tournamentInfo.Tournament as IParams} />, <CompetitionsSetting tournamentInfo={nextProps.tournamentInfo.Tournament as IParams} tournamentId={Number(this.props.routerInfo.match.params.tournamentId)} onChangeCompetitionSetting={this.onChangeCompetitionSetting} />];
        } else {
          this.tabList = [];
          this.componentList = [];
        }
      }
    }
    if (this.state.selectedSport !== nextState.selectedSport || this.state.selectedCompetition !== nextState.selectedCompetition) {
      this.props.onEditBracketMode(false);
      this.props.deleteListSelectingTeam();
    }
    if (this.props.listSportsByTournament !== nextProps.listSportsByTournament) {
      sportOptions = [];
      if (nextProps.listSportsByTournament != null) {
        nextProps.listSportsByTournament.map((item, index) => sportOptions.push({ value: item.id, label: item.fullName }));
      }
    }
    if (this.props.listCompetitionsBySportAndTournament !== nextProps.listCompetitionsBySportAndTournament) {
      competitionOptions = [];
      if (nextProps.listCompetitionsBySportAndTournament != null) {
        nextProps.listCompetitionsBySportAndTournament.map((item, index) => competitionOptions.push({ value: item.id, label: item.name }));
      }
    }
    if (this.state.selectedSport !== nextState.selectedSport && nextState.selectedSport != null) {
      competitionOptions = [];
      this.setState({
        selectedCompetition: null,
      });
      const params = {
        path: '',
        param: {
          tournamentId: Number(this.props.routerInfo.match.params.tournamentId),
          sportId: (nextState.selectedSport as IParams).value,
        },
        data: {},
      };
      this.props.queryCompetitionsBySportAndTournament(params);
    }
    return true;
  }

  componentDidMount() {
    this.requestData();
  }

  private requestData = () => {
    let params: IBigRequest = {
      path: '',
      param: {
        id: Number(this.props.routerInfo.match.params.tournamentId),
      },
      data: {},
    };
    this.props.queryTournamentInfo(params);
    params = {
      path: '',
      param: {
        tournamentId: Number(this.props.routerInfo.match.params.tournamentId),
      },
      data: {},
    };
    this.props.querySportsByTournament(params);
  }

  private onChangeSport = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedSport: value,
    });
  }

  private onChangeCompetition = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedCompetition: value,
    });
  }

  private onChangeCompetitionSetting = () => {
    this.setState({
      selectedCompetition: null,
      selectedSport: null,
    });
  }

  private updateBackground = (selectorFiles: FileList | null) => {
    if (selectorFiles !== null && selectorFiles.length > 0) {
      const params = {
        path: '',
        param: {
          id: Number(this.props.routerInfo.match.params.tournamentId),
          file: selectorFiles,
        },
        data: {
        },
      };

      this.props.updateBackgroundTournament(params);
    }
  };

  private updateAvatar = (selectorFiles: FileList | null) => {
    if (selectorFiles !== null && selectorFiles.length > 0) {
      const params = {
        path: '',
        param: {
          id: Number(this.props.routerInfo.match.params.tournamentId),
          file: selectorFiles,
        },
        data: {
        },
      };

      this.props.updateAvatarTournament(params);
    }
  };

  private handleStartTournament = () => {
    const confirm = window.confirm('Khi bắt đầu giải bạn sẽ không thể thay đổi thông tin các cuộc thi và các đội nữa, bạn có chắc chắn?')
    if (confirm === true) {
      const params = {
        path: '',
        param: {
          id: Number(this.props.routerInfo.match.params.tournamentId),
        },
        data: {
        },
      };

      this.props.startTournament(params);
    }
  };

  private handleFinishTournament = () => {
    const confirm = window.confirm('Khi Kết thúc giải đồng nghĩa với việc tất cả các cuộc thi cũng sẽ kết thúc, bạn có chắc chắn?')
    if (confirm === true) {
      const params = {
        path: '',
        param: {
          id: Number(this.props.routerInfo.match.params.tournamentId),
        },
        data: {
        },
      };

      this.props.finishTournament(params);
    }
  };

  render() {
    return (
      <div className="TournamentInfo-Container">
        <div className="TournamentInfo-background-image-container">
          <img className={'TournamentInfo-background-image'} src={require('../../assets/7ab1b0125d485c8dd6a4e78832b0a4b2fbed3cf8.png')} alt={'logo'} />
          {this.props.tournamentInfo != null && (this.props.tournamentInfo as IParams).Config != null && ((this.props.tournamentInfo as IParams).Config as IParams).canEdit === true && <AiFillCamera className={'TournamentInfo-change-image-icon'} />}
          {this.props.tournamentInfo != null && (this.props.tournamentInfo as IParams).Config != null && ((this.props.tournamentInfo as IParams).Config as IParams).canEdit === true && <div className={'TournamentInfo-Overlay'}>
            <input type="file" onChange={(e) => this.updateBackground(e.target.files)} />
          </div>}
        </div>
        <div className="TournamentInfo-content-container">
          <div className="TournamentInfo-content-info-container">
            <div className="TournamentInfo-content-info-basic-info-container">
              <div className="TournamentInfo-content-info-basic-info-container-container">
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <p className="TournamentInfo-name-text">{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? (this.props.tournamentInfo.Tournament as IParams).fullName : <Skeleton width={400} height={30} />}</p>
                </div>
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Tên ngắn: ${(this.props.tournamentInfo.Tournament as IParams).shortName}` : <Skeleton width={200} height={20} />}</p>
                  </div>
                </div>
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Nhà tài trợ: ${(this.props.tournamentInfo.Tournament as IParams).donor}` : <Skeleton width={250} height={20} />}</p>
                  </div>
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Trạng thái: ${((this.props.tournamentInfo.Tournament as IParams).status === 'processing' ? 'Đang diễn ra' : (this.props.tournamentInfo.status == null ? 'Chưa diễn ra' : 'Đã kết thúc'))}` : <Skeleton width={225} height={20} />}</p>
                  </div>
                </div>
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Ngày bắt đầu: ${formatDateToDisplay((this.props.tournamentInfo.Tournament as IParams).openingTime as string | undefined, 'dd/MM/yyyy', 'yyyy-MM-dd')}` : <Skeleton width={250} height={20} />}</p>
                  </div>
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Địa điểm khai mạc: ${(this.props.tournamentInfo.Tournament as IParams).openingLocation}` : <Skeleton width={275} height={20} />}</p>
                  </div>
                </div>
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Ngày kết thúc: ${formatDateToDisplay((this.props.tournamentInfo.Tournament as IParams).closingTime as string | undefined, 'dd/MM/yyyy', 'yyyy-MM-dd')}` : <Skeleton width={250} height={20} />}</p>
                  </div>
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Địa điểm bế mạc: ${(this.props.tournamentInfo.Tournament as IParams).closingLocation}` : <Skeleton width={275} height={20} />}</p>
                  </div>
                </div>
                <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                  <div className="TournamentInfo-info-item">
                    <p>{this.props.tournamentInfo != null && this.props.tournamentInfo.Tournament ? `Mô tả: ${(this.props.tournamentInfo.Tournament as IParams).description}` : <Skeleton width={300} height={20} />}</p>
                  </div>
                </div>
              </div>
              <img className={'TournamentInfo-avatar-image'} src={require('../../assets/7ab1b0125d485c8dd6a4e78832b0a4b2fbed3cf8.png')} alt={'logo'} />
              {this.props.tournamentInfo != null && (this.props.tournamentInfo as IParams).Config != null && ((this.props.tournamentInfo as IParams).Config as IParams).canEdit === true && <AiFillCamera className={'TournamentInfo-change-avatar-icon'} />}
              {this.props.tournamentInfo != null && (this.props.tournamentInfo as IParams).Config != null && ((this.props.tournamentInfo as IParams).Config as IParams).canEdit === true && <div className={'TournamentInfo-Overlay2'}>
                <input type="file" onChange={(e) => this.updateAvatar(e.target.files)} />
              </div>}
            </div>
            {/* <div className="TournamentInfo-login-container">
              <div
                className="TournamentInfo-login"
                onClick={this.handleStartTournament}
              >
                <h4 className="TournamentInfo-login-text">Bắt đầu giải</h4>
              </div>
            </div> */}
            {this.props.tournamentInfo != null && (this.props.tournamentInfo as IParams).Config != null &&
              (((this.props.tournamentInfo as IParams).Config as IParams).canEdit === true ?
                (this.props.tournamentInfo.Tournament != null && ((this.props.tournamentInfo.Tournament as IParams).status === 'unStarted' ? <div className="TournamentInfo-login-container">
                  <div
                    className="TournamentInfo-login"
                    onClick={this.handleStartTournament}
                  >
                    <h4 className="TournamentInfo-login-text">Bắt đầu giải</h4>
                  </div>
                </div> : ((this.props.tournamentInfo.Tournament as IParams).status === 'started' ?
                  <div className="TournamentInfo-login-container">
                    <div
                      className="TournamentInfo-login"
                      onClick={this.handleFinishTournament}
                    >
                      <h4 className="TournamentInfo-login-text">Kết thúc giải</h4>
                    </div>
                  </div> : null))) :
                (cookies.get(COOKIES_TYPE.AUTH_TOKEN) != null &&
                  (<div className="TournamentInfo-login-container">
                    <div
                      className="TournamentInfo-login"
                    // onClick={this.handleStartTournament}
                    >
                      <h4 className="TournamentInfo-login-text">Tham gia giải</h4>
                    </div>
                  </div>)
                ))
            }
            <div className="TournamentInfo-content-info-basic-info-container-container TournamentInfo-oddBackground" style={{ width: '96%' }}>
              <div className="TournamentInfo-content-info-basic-info-container-singleRow">
                <p className="TournamentInfo-name-text">Các cuộc thi trong giải</p>
                <div className="TournamentInfo-info-item">
                  <p className="TournamentInfo-text">Bộ môn</p>
                  <Select
                    options={sportOptions}
                    className="Select"
                    defaultValue={this.state.selectedSport}
                    value={this.state.selectedSport}
                    onChange={this.onChangeSport}
                    menuPlacement={'top'}
                  />
                </div>
                <div className="TournamentInfo-info-item">
                  <p className="TournamentInfo-text">Cuộc thi thuộc bộ môn</p>
                  <Select
                    options={competitionOptions}
                    className={`Select ${this.state.selectedSport == null && `Select-disabled`}`}
                    defaultValue={this.state.selectedCompetition}
                    value={this.state.selectedCompetition}
                    onChange={this.onChangeCompetition}
                    isDisabled={this.state.selectedSport == null}
                    menuPlacement={'top'}
                  />
                </div>
              </div>
            </div>
            <div className="TournamentInfo-content-info-advanced-info-container">
              <CustomTab tabList={this.tabList} componentList={this.componentList} selectedIndex={0}></CustomTab>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state: IState) => {
  return {
    tournamentInfo: state.tournamentInfo,
    listSportsByTournament: state.listSportsByTournament,
    listCompetitionsBySportAndTournament: state.listCompetitionsBySportAndTournament,
  };
};

export default connect(
  mapStateToProps,
  { deleteListSelectingTeam, onEditBracketMode, updateBackgroundTournament, updateAvatarTournament, queryTournamentInfo, querySportsByTournament, queryCompetitionsBySportAndTournament, startTournament, finishTournament }
)(TournamentInfo);