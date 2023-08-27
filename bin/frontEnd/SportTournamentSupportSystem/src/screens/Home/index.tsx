import React from 'react';
import { connect } from 'react-redux';
import BracketBoard from 'components/BracketBoard';
import './styles.css';

interface IHomeProps extends React.ClassAttributes<Home> {
}

interface IHomeState {
}

class Home extends React.Component<IHomeProps, IHomeState> {
  constructor(props: IHomeProps) {
    super(props);
    this.state = {
    };
  }

  render() {
    return (
      <div className="Container-login">
        <div className="Container-login-middle">
          <h1>Quản lý giải đấu đơn giản</h1>
          {/* <BracketBoard /> */}
        </div>
      </div>
    );
  }
}

export default connect(
  null,
  null
)(Home);