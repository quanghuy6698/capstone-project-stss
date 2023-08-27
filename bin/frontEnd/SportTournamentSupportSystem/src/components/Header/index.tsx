import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Select, { ValueType, OptionTypeBase } from 'react-select';
import { FaChevronDown, FaChevronUp, FaSearch } from 'react-icons/fa';
import { RiCloseLine } from 'react-icons/ri';
import { COOKIES_TYPE } from 'global';
import { IBigRequest, IParams } from 'interfaces/common';
import history from "utils/history";
import { cookies } from 'utils/cookies';
import { IState } from 'redux-saga/reducers';
import { setGlobalSearchString } from 'redux-saga/global-actions/SetGlobalSearchString-actions';
import { logOut, searchTournaments, searchUsers } from './actions';
import './styles.css';

interface IHeaderProps extends React.ClassAttributes<Header> {
  currentPage: 'login' | 'signUp' | 'tournaments' | 'tournamentInfo' | 'users' | 'userInfo' | 'home' | 'forgotPassword' | 'newTournament' | 'changePassword' | 'active';
  currentUserInfo: IParams | null;

  logOut(): void;
  searchTournaments(params: IBigRequest): void;
  searchUsers(params: IBigRequest): void;
  setGlobalSearchString(params: string): void;
}

interface IHeaderState {
  showUserOption: boolean;
  searchText: string;
  selectedSearchOption: ValueType<OptionTypeBase>;
}

const searchOption = [
  { value: 1, label: 'Tìm Giải' },
  { value: 2, label: 'Tìm Người dùng' },
];

class Header extends React.Component<IHeaderProps, IHeaderState> {
  constructor(props: IHeaderProps) {
    super(props);
    this.state = {
      showUserOption: false,
      searchText: '',
      selectedSearchOption: { value: 1, label: 'Tìm Giải' },
    };
  }

  shouldComponentUpdate(nextProps: IHeaderProps, nextState: IHeaderState) {
    if (this.props.currentPage !== nextProps.currentPage) {
      this.setState({
        showUserOption: false,
      });
    }
    return true;
  }

  private handleShowUserOption = () => {
    this.setState({
      showUserOption: !this.state.showUserOption,
    });
  }


  private hideUserOption = () => {
    this.setState({
      showUserOption: false,
    });
  }

  private logOut = () => {
    this.hideUserOption();
    this.props.logOut();
  }

  private handleSearch = () => {
    const params = {
      path: '',
      param: {
        page: 1,
        limit: 3,
        searchString: this.state.searchText,
      },
      data: {},
    };

    this.props.setGlobalSearchString(this.state.searchText.trim());
    if ((this.state.selectedSearchOption as IParams).value === 1) {
      this.props.searchTournaments(params);
      history.push("/tournaments");
    } else {
      this.props.searchUsers(params);
      history.push("/users");
    }
  }

  private onChangeSearchOption = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedSearchOption: value,
    });
  }

  private clearSearchText = () => {
    this.setState({
      searchText: '',
    });
  }

  private onChangeSearchText = (value: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({
      searchText: value.target.value,
    });
  }

  private keyPressed = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      this.handleSearch();
    }
  }

  render() {
    const currentUserInfo = this.props.currentUserInfo;
    return (
      <div className="Header-container">
        <div className="Logo-container">
          <Link to="/" style={{ height: 50 }}>
            <img style={{ width: '100%', height: '100%' }} src={require('../../assets/logo.png')} alt={'logo'} />
          </Link>
        </div>
        <div className="Option-container">
          <Link to="/tournaments" style={{ textDecoration: 'none' }} onClick={() => { this.props.setGlobalSearchString('') }}>
            <div className="Link"><p className="Link-text">Các giải đấu</p></div>
          </Link>
          {/* <Link to="/news" style={{ textDecoration: 'none' }}>
            <div className="Link"><p className="Link-text">Tin tức</p></div>
          </Link>
          <Link to="/events" style={{ textDecoration: 'none' }}>
            <div className="Link"><p className="Link-text">Sự kiện</p></div>
          </Link> */}
        </div>
        <div className="SearchBar-container">
          <Select
            options={searchOption}
            className="Select Select1"
            defaultValue={this.state.selectedSearchOption}
            value={this.state.selectedSearchOption}
            onChange={this.onChangeSearchOption}
            menuPlacement={'top'}
          />
          <input type={'text'} onKeyPress={this.keyPressed} className={'Header-text-input'} onChange={this.onChangeSearchText} value={this.state.searchText} />
          {this.state.searchText !== '' && <div className={'Header-icon-container'} onClick={this.clearSearchText}>
            <RiCloseLine className={'Header-icon'} size={30} />
          </div>}
          <div className={'Header-icon-container'} onClick={this.handleSearch}>
            <FaSearch className={'Header-icon'} size={25} />
          </div>
        </div>
        {this.props.currentPage !== 'newTournament' && <Link to="/newTournament" style={{ textDecoration: 'none' }}>
          <div className="SignUp-Button-container"><p className="Button">Tạo giải ngay</p></div>
        </Link>}
        {currentUserInfo != null && cookies.get(COOKIES_TYPE.AUTH_TOKEN) != null ?
          <div className="Right-container Right-container-hover">
            <div className={'UserOption-container'} onClick={this.handleShowUserOption}>
              <div className={'UserOption-avatar-container'}>
                <img className={'UserOption-avatar-image'} src={require('../../assets/7ab1b0125d485c8dd6a4e78832b0a4b2fbed3cf8.png')} alt={'logo'} />
              </div>
              <p className={'UserOption-name-text'}>{`${currentUserInfo.firstName}`}</p>
              {this.state.showUserOption === true ? <FaChevronDown color={'white'} /> : <FaChevronUp color={'white'} />}
            </div>
            {this.state.showUserOption === true &&
              <div
                className={'UserOption-dropdown-container'}
              >
                <Link to={`/user/${currentUserInfo.id}`} style={{ textDecoration: 'none' }}>
                  <div className={'UserOption-dropdown-item-container UserOption-dropdown-item-container1'}>
                    <p>Thông tin</p>
                  </div>
                </Link>
                <Link to={`/changePassword`} style={{ textDecoration: 'none' }}>
                  <div className={'UserOption-dropdown-item-container UserOption-dropdown-item-container2'}>
                    <p>Đổi mật khẩu</p>
                  </div>
                </Link>
                <div className={'UserOption-dropdown-item-container UserOption-dropdown-item-container1'} onClick={this.logOut}>
                  <p>Đăng xuất</p>
                </div>
              </div>
            }
          </div>
          : <div className="Right-container">
            {this.props.currentPage === 'login' && <div className="Link-text-no-hover-container"><p className="Link-text-no-hover">Bạn chưa có tài khoản?</p></div>}
            {this.props.currentPage === 'login' ?
              <Link to="/signUp" style={{ textDecoration: 'none' }}>
                <div className="SignUp-Button-container"><p className="Button">Đăng ký</p></div>
              </Link> :
              <Link to="/login" style={{ textDecoration: 'none' }}>
                <div className="SignUp-Button-container"><p className="Button">Đăng nhập</p></div>
              </Link>}
          </div>}
      </div>
    )
  }
}

const mapStateToProps = (state: IState) => {
  return {
    currentUserInfo: state.currentUserInfo,
  };
};

export default connect(
  mapStateToProps,
  { logOut, searchTournaments, setGlobalSearchString, searchUsers }
)(Header);