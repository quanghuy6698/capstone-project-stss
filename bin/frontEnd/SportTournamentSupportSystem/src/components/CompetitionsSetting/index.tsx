import React from 'react';
import { connect } from 'react-redux';
import { Styles } from 'react-modal';
import Select, { ValueType, OptionTypeBase } from 'react-select';
import 'react-block-ui/style.css';
import CompetitionSettingCompetitionsItem from 'components/CompetitionSettingCompetitionsItem';
import CompetitionSettingCompetitionsAddItem from 'components/CompetitionSettingCompetitionsAddItem';
import CustomModal from 'components/CustomModal';
import TextInput from 'components/TextInput';
import { IBigRequest, IParams } from 'interfaces/common';
import { IState } from 'redux-saga/reducers';
import { queryAllCompetitionsByTournamentId, addACompetition } from './actions';
import './styles.css';

interface ICompetitionSettingProps extends React.ClassAttributes<CompetitionSetting> {
  tournamentId: number;
  tournamentInfo: IParams | null;
  allCompetitionByTournamentId: IParams[] | null;

  onChangeCompetitionSetting(): void;
  queryAllCompetitionsByTournamentId(param: IBigRequest): void;
  addACompetition(param: IBigRequest): void;
}

interface ICompetitionSettingState {
  showModal: boolean;
  competitionName: string;
  competitionNameError: boolean;
  competitionNameErrorContent: string;
  competitionFormatError: boolean;
  competitionFormatErrorContent: string;
  selectedSport: ValueType<OptionTypeBase>;
  onePhase: boolean;
  twoPhase: boolean;
  selectedCompetitionFormatPhase1: ValueType<OptionTypeBase>;
  selectedCompetitionFormatPhase2: ValueType<OptionTypeBase>;
  maxAmountTeamOfGroup: number;
  maxAmountTeamOfGroupError: boolean;
  maxAmountTeamOfGroupErrorContent: string;
  limitAmountOfTeamchecked: boolean;
  maxAmountOfTeam: number;
  maxAmountOfTeamError: boolean;
  maxAmountOfTeamErrorContent: string;
  maxAmountMemberOfTeam: number;
  minAmountMemberOfTeam: number;
  minAmountMemberOfTeamError: boolean;
  maxAmountMemberOfTeamError: boolean;
  maxAmountMemberOfTeamErrorContent: string;
  minAmountMemberOfTeamErrorContent: string;
}

const competitionFormatOptions1 = [
  { value: 1, label: 'Đấu loại' },
  { value: 2, label: 'Đấu vòng tròn tính điểm' },
];

const competitionFormatOptions2 = [
  { value: 1, label: 'Đấu loại' },
];

const customStyles: Styles = {
  content: {
    top: '15%',
    left: '15%',
    right: '15%',
    bottom: '15%',
    backgroundColor: '#2b303d',
    display: 'flex',
    flexDirection: 'column',
  },
  overlay: {
    zIndex: 100001,
  },
};

const sportOptions = [
  { value: 1, label: 'Bóng đá' },
  { value: 2, label: 'Bóng chuyền' },
  { value: 3, label: 'Bóng bàn' },
  { value: 4, label: 'Bóng rổ' },
  { value: 5, label: 'Cờ vua' },
  { value: 6, label: 'Cờ vây' },
  { value: 7, label: 'Cờ tướng' },
  { value: 8, label: 'Cầu lông' },
  { value: 9, label: 'Quần vợt' },
  { value: 10, label: 'Vovinam' },
  { value: 11, label: 'Taekwondo' },
  { value: 12, label: 'Karatedo' },
  { value: 13, label: 'Pencak silat' },
  { value: 14, label: 'Wushu' },
  { value: 15, label: 'Judo' },
  { value: 16, label: 'E-Sport' },
];

class CompetitionSetting extends React.Component<ICompetitionSettingProps, ICompetitionSettingState> {
  constructor(props: ICompetitionSettingProps) {
    super(props);
    this.state = {
      showModal: false,
      competitionName: '',
      competitionNameError: false,
      competitionNameErrorContent: '',
      competitionFormatError: false,
      competitionFormatErrorContent: '',
      selectedSport: null,
      onePhase: true,
      twoPhase: false,
      selectedCompetitionFormatPhase1: null,
      selectedCompetitionFormatPhase2: null,
      limitAmountOfTeamchecked: false,
      maxAmountTeamOfGroup: 0,
      maxAmountTeamOfGroupError: false,
      maxAmountTeamOfGroupErrorContent: '',
      maxAmountOfTeam: 0,
      maxAmountOfTeamError: false,
      maxAmountOfTeamErrorContent: '',
      maxAmountMemberOfTeamError: false,
      maxAmountMemberOfTeamErrorContent: '',
      maxAmountMemberOfTeam: 0,
      minAmountMemberOfTeam: 0,
      minAmountMemberOfTeamError: false,
      minAmountMemberOfTeamErrorContent: '',

    };
  }

  componentDidMount() {
    this.requestData();
  }

  private requestData = () => {
    const params = {
      path: '',
      param: {
        tournamentId: this.props.tournamentId,
        limit: 99,
      },
      data: {},
    };
    this.props.queryAllCompetitionsByTournamentId(params);
  }

  // private onSubmit = () => {
  //   this.props.onChangeCompetitionSetting();
  // }

  private handleCloseModal = () => {
    this.setState({
      showModal: false,
    });
  }

  private handleOpenModal = () => {
    this.setState({
      showModal: true,
    });
  }

  private validate = () => {
    let competitionNameError = false;
    let competitionNameErrorContent = '';
    let competitionFormatError = false;
    let competitionFormatErrorContent = '';
    if (this.state.competitionName.trim() === '') {
      if (this.state.selectedSport == null) {
        competitionNameError = true;
        competitionNameErrorContent = 'Tên đăng nhập không được trống, môn thể thao không được trống';
      } else {
        competitionNameError = true;
        competitionNameErrorContent = 'Tên đăng nhập không được trống';
      }
    } else {
      if (this.state.selectedSport == null) {
        competitionNameError = true;
        competitionNameErrorContent = 'Môn thể thao không được trống';
      }
    }
    if (this.state.selectedCompetitionFormatPhase1 == null || (this.state.twoPhase && this.state.selectedCompetitionFormatPhase2 == null)) {
      competitionFormatError = true;
      competitionFormatErrorContent = 'Thể thức không được trống';
    }
    if (this.props.allCompetitionByTournamentId!.find(element => element.name === this.state.competitionName)) {
      competitionNameError = true;
      competitionNameErrorContent = 'Tên cuộc thi này đã tồn tại';
    }

    return { competitionNameError, competitionNameErrorContent, competitionFormatError, competitionFormatErrorContent };
  }

  private onChangeMaxAmountMemberOfTeam = (value: string) => {
    let tempValue = 0;
    if (!isNaN(value as unknown as number)) {
      tempValue = Number(value);
    } else {
      tempValue = 0;
    }
    this.setState({ maxAmountMemberOfTeam: tempValue, });
  }

  private onChangeMinAmountMemberOfTeam = (value: string) => {
    let tempValue = 0;
    if (!isNaN(value as unknown as number)) {
      tempValue = Number(value);
    } else {
      tempValue = 0;
    }
    this.setState({ minAmountMemberOfTeam: tempValue, });
  }

  private handleConfirmModal = () => {
    const { competitionNameError, competitionNameErrorContent, competitionFormatError, competitionFormatErrorContent } = this.validate();
    this.setState({
      competitionNameError,
      competitionNameErrorContent,
      competitionFormatError,
      competitionFormatErrorContent
    });
    if (competitionNameError === true || competitionFormatError === true) {
      return;
    }

    const params = {
      path: '',
      param: {},
      data: {
        name: this.state.competitionName,
        tournamentId: this.props.tournamentId,
        sportId: (this.state.selectedSport as IParams).value,
        description: '',
        mainFormatId: this.state.onePhase === true ? (this.state.selectedCompetitionFormatPhase1 as IParams).value : (this.state.selectedCompetitionFormatPhase2 as IParams).value,
        groupStageFormatId: this.state.onePhase === true ? null : (this.state.selectedCompetitionFormatPhase1 as IParams).value,
        groupStage: this.state.twoPhase === true,
        status: 'dit me thang hieu',
        url: '',
      },
    };
    this.props.addACompetition(params);
    this.setState({
      showModal: false,
    });
  }

  private onChangeCompetitionName = (value: string) => {
    this.setState({ competitionName: value, });
  }

  private onChangeSport = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedSport: value,
    });
  }

  private onChangeLimitAmountOfTeam = () => {
    this.setState({
      limitAmountOfTeamchecked: !this.state.limitAmountOfTeamchecked,
    });
  };

  private onChangeMaxAmountOfTeam = (value: string) => {
    let tempValue = 0;
    if (!isNaN(value as unknown as number)) {
      tempValue = Number(value);
    } else {
      tempValue = 0;
    }
    this.setState({ maxAmountOfTeam: tempValue, });
  }

  private OnChoose1 = () => {
    this.setState({
      onePhase: true,
      twoPhase: false,
    });
  }

  private OnChoose2 = () => {
    this.setState({
      onePhase: false,
      twoPhase: true,
    });
  }

  private onChangeCompetitionFormatPhase1 = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedCompetitionFormatPhase1: value,
    });
  }

  private onChangeCompetitionFormatPhase2 = (value: ValueType<OptionTypeBase>) => {
    this.setState({
      selectedCompetitionFormatPhase2: value,
    });
  }

  private onChangeMaxAmountTeamOfGroup = (value: string) => {
    let tempValue = 0;
    if (!isNaN(value as unknown as number)) {
      tempValue = Number(value);
    } else {
      tempValue = 0;
    }
    this.setState({ maxAmountTeamOfGroup: tempValue, });
  }

  render() {
    if (this.props.allCompetitionByTournamentId != null) {
      return (
        <div className="CompetitionSetting-container">
          {this.props.allCompetitionByTournamentId.map((item, index) => {
            return (<CompetitionSettingCompetitionsItem tournamentInfo={this.props.tournamentInfo} info={item} index={index} key={index} listCompetition={this.props.allCompetitionByTournamentId} tournamentId={this.props.tournamentId} />);
          })}
          <CompetitionSettingCompetitionsAddItem handleAddACompetition={this.handleOpenModal} />
          <CustomModal
            customStyles={customStyles}
            handleCloseModal={this.handleCloseModal}
            showModal={this.state.showModal}
            handleConfirmModal={this.handleConfirmModal}
          >
            <div className="CompetitionSetting-header-name-container">
              <h2>Tạo một cuộc thi mới trong giải</h2>
            </div>
            <div className="CompetitionSetting-tournament-name-container">
              <TextInput label={'Nhập tên cuộc thi'} value={this.state.competitionName} error={this.state.competitionNameError} errorContent={this.state.competitionNameErrorContent} onChangeText={this.onChangeCompetitionName} />
            </div>
            <div className="TournamentInfo-info-item">
              <p className="TournamentInfo-text">Bộ môn</p>
              <Select
                options={sportOptions}
                className="Select"
                defaultValue={this.state.selectedSport}
                value={this.state.selectedSport}
                onChange={this.onChangeSport}
                maxMenuHeight={140}
              />
            </div>
            <div className="TournamentInfo-info-item">
              <p className="TournamentInfo-text">Cách tổ chức giải:</p>
              <input type="radio" name="competitionType" onClick={this.OnChoose1} checked={this.state.onePhase} readOnly />
              <label onClick={this.OnChoose1}>1 giai đoạn</label>
              <input type="radio" name="competitionType" onClick={this.OnChoose2} checked={this.state.twoPhase} readOnly />
              <label onClick={this.OnChoose2}>2 giai đoạn</label>
            </div>
            <div className="TournamentInfo-info-item">
              <p className="TournamentInfo-text">{`Thể thức${this.state.onePhase === true ? '' : ' giai đoạn 1'}`}</p>
              <Select
                options={competitionFormatOptions1}
                className="Select"
                defaultValue={this.state.selectedCompetitionFormatPhase1}
                value={this.state.selectedCompetitionFormatPhase1}
                onChange={this.onChangeCompetitionFormatPhase1}
                menuPlacement={'top'}
              />
              {this.state.twoPhase === true && <p className="TournamentInfo-text">Thể thức giai đoạn 2</p>}
              {this.state.twoPhase === true && <Select
                options={competitionFormatOptions2}
                className="Select"
                defaultValue={this.state.selectedCompetitionFormatPhase2}
                value={this.state.selectedCompetitionFormatPhase2}
                onChange={this.onChangeCompetitionFormatPhase2}
                menuPlacement={'top'}
              />}
            </div>
            {this.state.competitionFormatError === true && <div className="TournamentInfo-info-item">
              <p className={'TextInput-error-text'}>{this.state.competitionFormatErrorContent}</p>
            </div>}
            {this.state.selectedCompetitionFormatPhase1 && (this.state.selectedCompetitionFormatPhase1 as IParams).value === 2 && <div className="TournamentInfo-info-item">
              <TextInput value={this.state.maxAmountTeamOfGroup as unknown as string} label={`Giới hạn số lượng đội trong 1 bảng${this.state.twoPhase ? ' của giai đoạn 1' : ''}`} onChangeText={this.onChangeMaxAmountTeamOfGroup} error={this.state.maxAmountTeamOfGroupError} errorContent={this.state.maxAmountTeamOfGroupErrorContent} />
            </div>}
            <div className="TournamentInfo-info-item">
              <div className="CompetitionSettingCompetitionsItem-option-container-item">
                <label className="Checkbox-label">
                  <input
                    type="checkbox"
                    checked={this.state.limitAmountOfTeamchecked}
                    onChange={this.onChangeLimitAmountOfTeam}
                  />
            Giới hạn số đội tối đa
          </label>
              </div>
            </div>
            {this.state.limitAmountOfTeamchecked === true && <TextInput value={this.state.maxAmountOfTeam as unknown as string} label={'Số lượng đội tối đa'} onChangeText={this.onChangeMaxAmountOfTeam} error={this.state.maxAmountOfTeamError} errorContent={this.state.maxAmountOfTeamErrorContent} />}
          </CustomModal>
        </div>
      );
    } else {
      return (
        <div className="CompetitionSetting-container">
        </div>
      );
    }
  }
}

const mapStateToProps = (state: IState) => {
  return {
    allCompetitionByTournamentId: state.allCompetitionByTournamentId,
  };
};

export default connect(
  mapStateToProps,
  { queryAllCompetitionsByTournamentId, addACompetition }
)(CompetitionSetting);