import React from 'react';
import { connect } from 'react-redux';
import Teams from 'components/Teams';
import './styles.css';

interface ITournamentListTeamProps extends React.ClassAttributes<TournamentListTeam> {
  id: number;
}

interface ITournamentListTeamState {
}

class TournamentListTeam extends React.Component<ITournamentListTeamProps, ITournamentListTeamState> {
  constructor(props: ITournamentListTeamProps) {
    super(props);
    this.state = {
    };
  }

  render() {
    return (
      <div className="TournamentListTeam-container">
        {/* <Teams id={this.props.id} type={'competition'} /> */}
      </div>
    );
  }
}

export default connect(
  null,
  null
)(TournamentListTeam);