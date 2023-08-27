import React from 'react';
import { connect } from 'react-redux';
import ReduxBlockUi from 'react-block-ui/redux';
import BracketRound from 'components/BracketRound';
import { IState } from 'redux-saga/reducers';
import { IBigRequest, IParams } from 'interfaces/common';
import { queryListTeams } from 'components/Teams/actions';
import { SWAP_TWO_TEAM_IN_BRACKET_SUCCESS, SWAP_TWO_TEAM_IN_BRACKET_FAILED } from 'components/BracketTeam/reducers';
import { SWAP_TWO_TEAM_IN_BRACKET } from 'redux-saga/actions';
import { queryBracketBoardInfo, setBracketStartedStatus } from './actions';
import './styles.css';

interface IBracketBoardProps extends React.ClassAttributes<BracketBoard> {
  bracketBoardInfo: IParams | null;
  competitionId: number;
  listTeam: IParams[] | null;

  queryBracketBoardInfo(params: IBigRequest): void;
  setBracketStartedStatus(params: boolean): void;
  queryListTeams(params: IBigRequest): void;
}

interface IBracketBoardState {
}

class BracketBoard extends React.Component<IBracketBoardProps, IBracketBoardState> {

  constructor(props: IBracketBoardProps) {
    super(props);
    this.state = {
    };
  }

  shouldComponentUpdate(nextProps: IBracketBoardProps, nextState: IBracketBoardState) {
    if (nextProps.bracketBoardInfo == null) {
      this.props.setBracketStartedStatus(false);
    } else {
      // if (this.props.bracketBoardInfo !== nextProps.bracketBoardInfo) {
      //   this.props.setBracketStartedStatus(nextProps.bracketBoardInfo.started);
      // }
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
        competitionId: this.props.competitionId,
      },
      data: {},
    };
    this.props.queryBracketBoardInfo(params);
    params = {
      path: '',
      param: {
        competitionId: this.props.competitionId,
        limit: 999,
      },
      data: {},
    };
    this.props.queryListTeams(params);
  }

  render() {
    return (
      <ReduxBlockUi
        tag="div"
        block={SWAP_TWO_TEAM_IN_BRACKET}
        unblock={[SWAP_TWO_TEAM_IN_BRACKET_SUCCESS, SWAP_TWO_TEAM_IN_BRACKET_FAILED]}
      >
      <div className="BracketBoard-container-container">
        <div className="BracketBoard-container">
          {this.props.bracketBoardInfo == null || this.props.listTeam == null
            ? <p>Chưa có thông tin!</p>
            : (this.props.bracketBoardInfo.listRound != null ? (this.props.bracketBoardInfo.listRound as IParams[]).map((item, index) =>
              (<BracketRound competitionId={this.props.competitionId} index={index} info={item} key={index} roundNo={index + 1} totalRound={(this.props.bracketBoardInfo!.listRound! as IParams[]).length} />)) :
              (this.props.bracketBoardInfo.listWinRound as IParams[]).map((item, index) =>
                <BracketRound competitionId={this.props.competitionId} index={index} info={item} key={index} roundNo={index + 1} totalRound={(this.props.bracketBoardInfo!.listWinRound! as IParams[]).length} />)
            )
          }
        </div>
        <div className="BracketBoard-container">
          {this.props.bracketBoardInfo != null && this.props.bracketBoardInfo.listLoseRound != null && (this.props.bracketBoardInfo.listLoseRound as IParams[]).map((item, index) =>
            <BracketRound competitionId={this.props.competitionId} index={index} info={item} key={index} roundNo={index + 1} totalRound={(this.props.bracketBoardInfo!.listLoseRound! as IParams[]).length} />)}
        </div>
      </div>
      </ReduxBlockUi>
    );
  }

}

const mapStateToProps = (state: IState) => {
  return {
    listTeam: state.listTeam,
    bracketBoardInfo: state.bracketBoardInfo,
  };
};

export default connect(
  mapStateToProps,
  {
    queryBracketBoardInfo,
    setBracketStartedStatus,
    queryListTeams
  }
)(BracketBoard);