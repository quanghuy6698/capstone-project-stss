import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { FaEdit } from 'react-icons/fa';
import { MdDelete } from 'react-icons/md';
import { IParams, IBigRequest } from 'interfaces/common';
import { formatGender } from 'utils/common';
import { formatDateToDisplay } from 'utils/datetime';
import { IState } from 'redux-saga/reducers';
import { queryUserInfo } from 'screens/UserInfo/actions';
import { queryListPlayerOfTeam } from './actions';
import './styles.css';

interface IUserInfoTeamsItemProps extends React.ClassAttributes<UserInfoTeamsItem> {
  info: IParams;
  index: number;
  tournamentInfo: IParams | null;
  competitionInfo: IParams | null;
  listPlayerOfTeam?: IParams[] | null;
  userInfo: IParams | null;

  queryListPlayerOfTeam(param: IBigRequest): void;
  queryUserInfo(param: IBigRequest): void;
}

interface IUserInfoTeamsItemState {
  seeMoreInfo: boolean;
}

class UserInfoTeamsItem extends React.Component<IUserInfoTeamsItemProps, IUserInfoTeamsItemState> {
  constructor(props: IUserInfoTeamsItemProps) {
    super(props);
    this.state = {
      seeMoreInfo: false,
    };
  }

  componentDidMount() {
    this.requestData();
  }

  private requestData = () => {
    let params: IBigRequest = {
      path: '',
      param: {
        teamId: this.props.info.id,
      },
      data: {},
    };
    this.props.queryListPlayerOfTeam(params);
    params = {
      path: '',
      param: {
        id: this.props.info.creatorId,
      },
      data: {},
    };
    this.props.queryUserInfo(params);
  }

  private handleSeeMore = () => {
    this.setState({
      seeMoreInfo: !this.state.seeMoreInfo,
    });
  }

  render() {
    return (
      <div className="UserInfoTeamsItem-info-container">
        <div className="UserInfoTeamsItem-container">
          <div className="UserInfoTeamsItem-container-container" onClick={this.handleSeeMore}>
            <div className="UserInfoTeamsItem-order-number-container">
              <div className="UserInfoTeamsItem-order-number-box">
                <p>{this.props.index + 1}</p>
              </div>
            </div>
            <div className="UserInfoTeamsItem-team-name-container">
              <p>{this.props.info && this.props.info.fullName}</p>
            </div>
            <div className="UserInfoTeamsItem-team-setting-container">
              <div className="UserInfoTeamsItem-team-setting-container-container">
                <FaEdit className="UserInfoTeamsItem-team-setting-icon" />
              </div>
              <div className="UserInfoTeamsItem-team-setting-container-container">
                <MdDelete className="UserInfoTeamsItem-team-setting-icon" />
              </div>
            </div>
          </div>
        </div>
        {this.state.seeMoreInfo === true &&
          <div className="UserInfoTeamsItem-moreInfo-container">
            <div className="UserInfoTeamsItem-moreInfo-normalInfo-container">
              <p>Tên ngắn: {this.props.info.shortName}</p>
              <p>Giải tham gia: {this.props.tournamentInfo && (this.props.tournamentInfo.Tournament as IParams).fullName}</p>
              <p>Bộ môn tham gia: Bóng đá</p>
              <p>Tên cuộc thi: {this.props.competitionInfo && this.props.competitionInfo.name}</p>
              <p>Quản lý của đội: <Link style={{ fontWeight: 'bold' }} target={'_blank'} to={`/user/${this.props.info.creatorId}`}>
              {this.props.userInfo != null ? `${(this.props.userInfo.User as IParams).firstName} ${(this.props.userInfo.User as IParams).lastName}` : ''}
              </Link></p>
              <p>Danh sách thành viên:</p>
            </div>
            <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-container">
              <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-container">
                <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-orderNumber">
                  <p>Thứ tự</p>
                </div>
                <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-memberName">
                  <p>Tên</p>
                </div>
                <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-gender">
                  <p>Giới tính</p>
                </div>
                <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-dob">
                  <p>Ngày sinh</p>
                </div>
                <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-email">
                  <p>Email</p>
                </div>
              </div>
              {
                this.props.listPlayerOfTeam != null &&
                this.props.listPlayerOfTeam.map((item, index) =>
                  <div className={`UserInfoTeamsItem-moreInfo-listTeamInfo-item-container ${index % 2 === 0 ? 'UserInfoTeamsItem-moreInfo-listTeamInfo-item-container1' : 'UserInfoTeamsItem-moreInfo-listTeamInfo-item-container2'}`} key={index}>
                    <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-orderNumber">
                      <p>{index + 1}</p>
                    </div>
                    <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-memberName">
                      <p>{`${item.firstName} ${item.lastName}`}</p>
                    </div>
                    <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-gender">
                      <p>{formatGender(item.gender as boolean)}</p>
                    </div>
                    <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-dob">
                      <p>{`${formatDateToDisplay(item.dob as string | undefined, 'dd/MM/yyyy', 'yyyy-MM-dd')}`}</p>
                    </div>
                    <div className="UserInfoTeamsItem-moreInfo-listTeamInfo-item-email">
                      <p>{item.email}</p>
                    </div>
                  </div>)
              }
            </div>
          </div>}
      </div>
    );
  }
}

const mapStateToProps = (state: IState) => {
  return {
    listPlayerOfTeam: state.listPlayerOfTeam,
    userInfo: state.userInfo,
  };
};

export default connect(
  mapStateToProps,
  { queryListPlayerOfTeam, queryUserInfo }
)(UserInfoTeamsItem);