package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.FormatEntity;
import doan2020.SportTournamentSupportSystem.entity.GroupStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.model.BoxCollection.RankingTable;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;
import doan2020.SportTournamentSupportSystem.model.Indexing.RevertMapping;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.DoubleEliminationScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.FinalStageScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.GroupStageScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.RoundRobinScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.ScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.SingleEliminationScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.UnknownScheduleDTO;
import doan2020.SportTournamentSupportSystem.model.Schedule.Format.DoubleEliminationTree;
import doan2020.SportTournamentSupportSystem.model.Schedule.Format.RoundRobinTable;
import doan2020.SportTournamentSupportSystem.model.Schedule.Format.SingleEliminationTree;
import doan2020.SportTournamentSupportSystem.model.Struct.Node;
import doan2020.SportTournamentSupportSystem.service.ICompetitionService;
import doan2020.SportTournamentSupportSystem.service.IFileStorageService;
import doan2020.SportTournamentSupportSystem.service.IMatchService;
import doan2020.SportTournamentSupportSystem.service.IScheduleService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;
import doan2020.SportTournamentSupportSystem.validator.Validator;

@Service
public class ScheduleService implements IScheduleService {

	@Autowired
	private IFileStorageService fileService;

	@Autowired
	private ICompetitionService competitionService;

	@Autowired
	private ITeamService teamService;

	@Autowired
	private Validator validator;

	@Autowired
	private IMatchService matchService;

	@Override
	public ScheduleDTO getSchedule(CompetitionEntity thisCompetition) {
		System.out.println("ScheduleService: getSchedule: start");
		ScheduleDTO schedule;
		Long competitionId = thisCompetition.getId();
		try {
			schedule = getScheduleFromFile(competitionId);
			// Check for changes in team numbers
			int dbTeamNumber = teamService.countByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_JOINED)
					.intValue();
			int cfTeamNumber = schedule.getTotalTeam();

			boolean checkTeamNumber = (dbTeamNumber == cfTeamNumber);

			String dbFinalStageFormat = thisCompetition.getFinalStageSetting().getFormat().getName();
			String cfFinalStageFormat = schedule.getFinalStageSchedule().getFormatName();

			boolean checkFinalStageFormat = (dbFinalStageFormat.contains(cfFinalStageFormat));
			boolean checkGroupStageFormat = true;
			if (thisCompetition.isHasGroupStage()) {
				String dbGroupStageFormat = thisCompetition.getGroupStageSetting().getFormat().getName();
				String cfGroupStageFormat = schedule.getGroupStageSchedule().getFormatName();
				checkGroupStageFormat = (dbGroupStageFormat.contains(cfGroupStageFormat));
			}

			if (checkFinalStageFormat && checkGroupStageFormat && checkTeamNumber) {

				boolean checkFinalStageOption = true;
				boolean checkGroupStageOption = true;

				if (cfFinalStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
					SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule.getFinalStageSchedule();
					boolean db = thisCompetition.getFinalStageSetting().isHasHomeMatch();
					boolean cf = fss.isHasMatch34();
					checkFinalStageOption = (db == cf);
				}

				if (cfFinalStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
					RoundRobinScheduleDTO fss = (RoundRobinScheduleDTO) schedule.getFinalStageSchedule();
					boolean db = thisCompetition.getFinalStageSetting().isHasHomeMatch();
					boolean cf = fss.isHasHomeMatch();
					checkFinalStageOption = (db == cf);
				}
				if (thisCompetition.isHasGroupStage()) {
					String cfGroupStageFormat = schedule.getGroupStageSchedule().getFormatName();
					if (cfGroupStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
						SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule
								.getGroupStageSchedule().getTables().get(0);
						boolean db = thisCompetition.getFinalStageSetting().isHasHomeMatch();
						boolean cf = fss.isHasMatch34();
						checkFinalStageOption = (db == cf);
					}

					if (cfGroupStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
						RoundRobinScheduleDTO fss = (RoundRobinScheduleDTO) schedule.getGroupStageSchedule().getTables()
								.get(0);
						boolean db = thisCompetition.getFinalStageSetting().isHasHomeMatch();
						boolean cf = fss.isHasHomeMatch();
						checkFinalStageOption = (db == cf);
					}
				}
				if (checkFinalStageOption && checkGroupStageOption) {

					return schedule;
				} else {
					System.out.println("setting has change");
					System.out.println("ScheduleService: getSchedule: finish -> ScheduleService: createSchedule:");
					return createSchedule(thisCompetition);
				}
			} else {
				System.out.println("setting has change");
				System.out.println("ScheduleService: getSchedule: finish -> ScheduleService: createSchedule:");
				return createSchedule(thisCompetition);
			}
		} catch (Exception e) {
			System.out.println("schedule not yet created");
			System.out.println("ScheduleService: getSchedule: finish -> ScheduleService: createSchedule:");
			return createSchedule(thisCompetition);
		}
	}

	private ScheduleDTO getScheduleFromFile(Long competitionId) {
		String fileName = Const.COMPETITION_SCHEDULING;
		String absFolderPath = null;
		String absFilePath = null;
		ScheduleDTO schedule = new ScheduleDTO();

		try {

			String folder = Const.COMPETITION_FILESYSTEM + Const.COMPETITION_FOLDER_NAMING + competitionId;
			absFolderPath = fileService.getFileStorageLocation(folder).toString();
			absFilePath = absFolderPath + "\\" + fileName;
			schedule = (ScheduleDTO) fileService.getObjectFromFile(absFilePath);

		} catch (Exception e) {
		}

		return schedule;
	}

	private String saveScheduleToFile(ScheduleDTO schedule, Long competitionId) {
		String fileName = Const.COMPETITION_SCHEDULING;
		String absFolderPath = null;
		String absFilePath = null;

		try {
			String folder = Const.COMPETITION_FILESYSTEM + Const.COMPETITION_FOLDER_NAMING + competitionId;
			absFolderPath = fileService.getFileStorageLocation(folder).toString();
			absFilePath = absFolderPath + "\\" + fileName;
			absFilePath = fileService.saveObjectToFile(schedule, absFilePath);

			System.out.println("ScheduleService: saveSchedule: absFileName: \n[" + absFilePath + "]");
		} catch (Exception e) {
		}

		return absFilePath;

	}

	/*----------------------------------------------------------------------
	 * initialize matches in db
	 * include createMatchesInDatabase() - 3 overload
	 * 
	 */

	public void createMatchesInDatabase(CompetitionEntity thisCompetition) {

//		try {

		System.out.println("ScheduleService: createMatchesInDatabase: start");
		Long competitionId = thisCompetition.getId();
		System.out.println("get id done");
		ScheduleDTO schedule = getSchedule(thisCompetition);
		System.out.println("get schedule done");
		String finalStageFormat = schedule.getFinalStageSchedule().getFormatName();
		System.out.println("get final stage schedule done");
		ArrayList<TeamEntity> teams = new ArrayList<>();

		System.out.println("init OK");
		if (!schedule.isHasGroupStage()) {
			System.out.println("hasn\'t group stage");
			teams.addAll(teamService.findByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_JOINED));

		} else {
			System.out.println("has group stage");
			GroupStageScheduleDTO groupStageSchedule = schedule.getGroupStageSchedule();
			String groupStageFormat = groupStageSchedule.getFormatName();
			ArrayList<FinalStageScheduleDTO> tables = groupStageSchedule.getTables();
			ArrayList<TeamEntity> realTeams = new ArrayList<>();

			realTeams.addAll(teamService.findByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_JOINED));
			Collections.sort(realTeams, new TeamEntity());

			int totalMatch = 0;
			int finishedMatch = 0;

			for (int tableId = 0; tableId < tables.size(); tableId++) {

				ArrayList<TeamEntity> tableTeams = new ArrayList<>();

				int firstSeed = tables.get(tableId).getFirstSeed();
				int totalTeam = tables.get(tableId).getTotalTeam();

				int seed = 0;
				while (seed < totalTeam) {
					tableTeams.add(realTeams.get(seed + firstSeed - 1));
					seed++;
				}

				if (groupStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
					tables.set(tableId, createMatchesInDatabase((SingleEliminationScheduleDTO) tables.get(tableId),
							tableTeams, thisCompetition));
				}
				if (groupStageFormat.contains(Const.DOUBLE_ELIMINATION_FORMAT)) {
					tables.set(tableId, createMatchesInDatabase((DoubleEliminationScheduleDTO) tables.get(tableId),
							tableTeams, thisCompetition));
				}
				if (groupStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
					tables.set(tableId, createMatchesInDatabase((RoundRobinScheduleDTO) tables.get(tableId), tableTeams,
							thisCompetition));
				}

				System.out.println(tables.get(tableId).getRankingTable().get(0).getTeam());

				totalMatch += tables.get(tableId).getProcess().getTotalMatch();
				finishedMatch += tables.get(tableId).getProcess().getFinishedMatch();
				groupStageSchedule.getMapping().putAll(tables.get(tableId).getMapping());

				if (tables.get(tableId).getProcess().isFinish()) {

					int top = (int) Math.min(schedule.getGroupStageSchedule().getAdvanceTeamPerTable(),
							tables.get(tableId).getRankingTable().size());
					if (finalStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
						schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
								(SingleEliminationScheduleDTO) schedule.getFinalStageSchedule(),
								tables.get(tableId).getRankingTable(), (long) tableId, top));
					}
					if (finalStageFormat.contains(Const.DOUBLE_ELIMINATION_FORMAT)) {
						schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
								(DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule(),
								tables.get(tableId).getRankingTable(), (long) tableId, top));
					}
					if (finalStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
						schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
								(RoundRobinScheduleDTO) schedule.getFinalStageSchedule(),
								tables.get(tableId).getRankingTable(), (long) tableId, top));
					}
				}

			}

			schedule.getMapping().putAll(groupStageSchedule.getMapping());
			schedule.setGroupStageSchedule(groupStageSchedule);

			groupStageSchedule.getProcess().setTotalMatch(totalMatch);
			groupStageSchedule.getProcess().setFinishedMatch(finishedMatch);
			if (totalMatch == finishedMatch)
				groupStageSchedule.getProcess().setFinish(true);
			else
				groupStageSchedule.getProcess().setFinish(false);
			if (totalMatch > 0) {
				groupStageSchedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
			}

			schedule.getProcess().setTotalMatch(totalMatch);
			schedule.getProcess().setFinishedMatch(finishedMatch);
			if (totalMatch == finishedMatch)
				schedule.getProcess().setFinish(true);
			else
				schedule.getProcess().setFinish(false);
			if (totalMatch > 0) {
				schedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
			}
			//
		}

		System.out.println("save for final stage");

		if (finalStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
			SingleEliminationScheduleDTO finalStageSchedule = (SingleEliminationScheduleDTO) schedule
					.getFinalStageSchedule();
			schedule.setFinalStageSchedule(createMatchesInDatabase(finalStageSchedule, teams, thisCompetition));
			schedule.getMapping().putAll(finalStageSchedule.getMapping());
			schedule.getFinalStageSchedule().setProcess(finalStageSchedule.getProcess());

		}
		if (finalStageFormat.contains(Const.DOUBLE_ELIMINATION_FORMAT)) {
			DoubleEliminationScheduleDTO finalStageSchedule = (DoubleEliminationScheduleDTO) schedule
					.getFinalStageSchedule();
			schedule.setFinalStageSchedule(createMatchesInDatabase(finalStageSchedule, teams, thisCompetition));
			schedule.getMapping().putAll(finalStageSchedule.getMapping());
			schedule.getFinalStageSchedule().setProcess(finalStageSchedule.getProcess());
		}
		if (finalStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
			RoundRobinScheduleDTO finalStageSchedule = (RoundRobinScheduleDTO) schedule.getFinalStageSchedule();
			schedule.setFinalStageSchedule(createMatchesInDatabase(finalStageSchedule, teams, thisCompetition));
			schedule.getMapping().putAll(finalStageSchedule.getMapping());
			schedule.getFinalStageSchedule().setProcess(finalStageSchedule.getProcess());
		}

		int totalMatch = schedule.getProcess().getTotalMatch()
				+ schedule.getFinalStageSchedule().getProcess().getTotalMatch();
		int finishedMatch = schedule.getProcess().getFinishedMatch()
				+ schedule.getFinalStageSchedule().getProcess().getFinishedMatch();

		System.out.println("totalMatch: " + totalMatch);

		schedule.getProcess().setTotalMatch(totalMatch);
		schedule.getProcess().setFinishedMatch(finishedMatch);
		if (totalMatch == finishedMatch)
			schedule.getProcess().setFinish(true);
		else
			schedule.getProcess().setFinish(false);
		if (totalMatch > 0) {
			schedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
		}

		saveScheduleToFile(schedule, competitionId);

//		System.out.println("ScheduleService: createMatchesInDatabase: no exception");
//		} catch (Exception e) {
//			System.out.println("ScheduleService: createMatchesInDatabase: has exception");
//			System.out.println(e);
//		}
		System.out.println("ScheduleService: createMatchesInDatabase: finish");

	}

	private SingleEliminationScheduleDTO createMatchesInDatabase(SingleEliminationScheduleDTO schedule,
			ArrayList<TeamEntity> teams, CompetitionEntity competition) {
		System.out.println("ScheduleService: SingleEliminationScheduleDTO createMatchesInDatabase: start");
		SingleEliminationTree tree = new SingleEliminationTree(schedule.getBracket(), schedule.getTotalTeam());
		tree.applyTeams(teams);
		int totalMatch = 0;
		int finishedMatch = 0;
		System.out.println("match total: " + tree.getMatches().size());
		for (Match match : tree.getMatches()) {

			totalMatch++;
			MatchEntity realMatch = new MatchEntity();
			realMatch.setCompetition(competition);

			realMatch.setName(match.getName());
			realMatch.setLocation(match.getLocation());
			realMatch.setTime(validator.formatStringToDate(match.getTime()));

			if (match.getStatus() == -1) {
				realMatch.setStatus(Const.MATCH_STATUS_FINISHED);
				realMatch.setWinnner(teamService.findOneById(match.getWinner().getTeam().getId()));
				finishedMatch++;
			}
			if (match.getStatus() == 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PLAYING);
			}
			if (match.getStatus() > 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PENDING);
			}

			try {
				realMatch.setTeam1(teamService.findOneById(match.getTeam1().getTeam().getId()));
				realMatch.setTeam2(teamService.findOneById(match.getTeam2().getTeam().getId()));
			} catch (Exception e) {
			}

			int nodeId = tree.getBracket().findNodeByData(match).getId();

			realMatch = matchService.create(realMatch);
			match.setId(realMatch.getId());

			RevertMapping map = new RevertMapping();
			map.setRealMatchId(match.getId());
			map.setLocation(0);
			map.setScheduleType(1);
			map.setTableId(schedule.getTableId());
			map.setNodeId(nodeId);
			System.out.println("map set OK");
			schedule.getMapping().put(match.getId(), map);
		}
		if (tree.getTotalTeam() >= 4 && schedule.isHasMatch34()) {
			System.out.println("Has match 34");
			totalMatch++;
			Match match = schedule.getMatch34();
			System.out.println("get match34 ok");
			System.out.println("match34: " + match);
			MatchEntity realMatch = new MatchEntity();
			realMatch.setCompetition(competition);

			realMatch.setName(match.getName());
			realMatch.setLocation(match.getLocation());
			realMatch.setTime(validator.formatStringToDate(match.getTime()));

			realMatch.setStatus(Const.MATCH_STATUS_PENDING);

			realMatch = matchService.create(realMatch);
			match.setId(realMatch.getId());
			RevertMapping map = new RevertMapping();

			System.out.println("init for revert mapping");
			map.setRealMatchId(match.getId());
			System.out.println("set id ok");
			map.setLocation(3);
			map.setScheduleType(1);
			map.setTableId(schedule.getTableId());
			System.out.println("set table id ok");
			map.setNodeId(-1);
			schedule.getMapping().put(match.getId(), map);
		}

		System.out.println("update schedule");
		System.out.println("totalMatch: " + totalMatch);
		System.out.println("finishedMatch: " + finishedMatch);

		schedule.setBracket(tree.getBracket());
		schedule.setRankingTable(tree.getRankingTable());
		schedule.getProcess().setTotalMatch(totalMatch);
		schedule.getProcess().setFinishedMatch(finishedMatch);
		if (totalMatch == finishedMatch)
			schedule.getProcess().setFinish(true);
		else
			schedule.getProcess().setFinish(false);
		if (totalMatch > 0) {
			schedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
		}

		System.out.println("ScheduleService: SingleEliminationScheduleDTO createMatchesInDatabase: finish");
		return schedule;
	}

	private DoubleEliminationScheduleDTO createMatchesInDatabase(DoubleEliminationScheduleDTO schedule,
			ArrayList<TeamEntity> teams, CompetitionEntity competition) {
		System.out.println("ScheduleService: saveFinalScheduleToDatabase: start");
		DoubleEliminationTree tree = new DoubleEliminationTree(schedule.getWinBranch(), schedule.getLoseBranch(),
				schedule.getTotalTeam());
		tree.applyTeams(teams);
		int totalMatch = 0;
		int finishedMatch = 0;
		System.out.println("apply tree OK");
		for (Match match : tree.getMatches()) {
			System.out.println("match " + match);
			totalMatch++;

			MatchEntity realMatch = new MatchEntity();
			realMatch.setCompetition(competition);
			realMatch.setName(match.getName());
			realMatch.setLocation(match.getLocation());
			realMatch.setTime(validator.formatStringToDate(match.getTime()));
			System.out.println("init DE - cp1");
			if (match.getStatus() == -1) {
				realMatch.setStatus(Const.MATCH_STATUS_FINISHED);
//				realMatch.setWinnner(teamService.findOneById(match.getWinner().getTeam().getId()));
//				finishedMatch++;
			}
			if (match.getStatus() == 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PLAYING);
			}
			if (match.getStatus() > 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PENDING);
			}
			System.out.println("try to set team");
			try {
				realMatch.setTeam1(teamService.findOneById(match.getTeam1().getTeam().getId()));
				realMatch.setTeam2(teamService.findOneById(match.getTeam2().getTeam().getId()));
			} catch (Exception e) {
			}

			System.out.println("set team ok");

			int nodeId;
			RevertMapping map = new RevertMapping();

			if (match.getName().contains(Const.LOSE_BRANCH_NAMING)) {
				System.out.println("in lose branch");
				Node<Match> node = tree.getLoseBranch().findNodeByData(match);
				nodeId = node.getId();
				map.setLocation(2);
				map.setDegree(node.getDegree() + 1);
			} else {
				Node<Match> node = tree.getWinBranch().findNodeByData(match);
				nodeId = node.getId();
				map.setLocation(1);
				map.setDegree(node.getDegree());
			}
			System.out.println("try to create in db");
			realMatch = matchService.create(realMatch);
			match.setId(realMatch.getId());

			System.out.println("try to mapping");
			map.setRealMatchId(match.getId());
			map.setScheduleType(2);
			map.setTableId(schedule.getTableId());
			map.setNodeId(nodeId);
			System.out.println("init map OK");
			schedule.getMapping().put(match.getId(), map);
			System.out.println("mapping OK");
		}
		System.out.println("solve summay final");
		if (tree.getTotalTeam() >= 2) {
			totalMatch++;
			Match finalMatch = tree.getSummaryFinal();
			MatchEntity realFinalMatch = new MatchEntity();
			realFinalMatch.setCompetition(competition);

			realFinalMatch.setName(finalMatch.getName());
			realFinalMatch.setLocation(finalMatch.getLocation());
			realFinalMatch.setTime(validator.formatStringToDate(finalMatch.getTime()));

			realFinalMatch.setStatus(Const.MATCH_STATUS_PENDING);

			realFinalMatch = matchService.create(realFinalMatch);

			System.out.println("finalMatchId: " + realFinalMatch.getId());

			finalMatch.setId(realFinalMatch.getId());

			RevertMapping map = new RevertMapping();
			map.setRealMatchId(finalMatch.getId());
			map.setLocation(4);
			map.setScheduleType(2);
			map.setTableId(schedule.getTableId());
			map.setNodeId(-1);
			map.setDegree(0);
			schedule.getMapping().put(finalMatch.getId(), map);

			totalMatch++;

			Match optionMatch = tree.getOptionFinal();
			MatchEntity realOptionMatch = new MatchEntity();
			realOptionMatch.setCompetition(competition);

			realOptionMatch.setName(optionMatch.getName());
			realOptionMatch.setLocation(optionMatch.getLocation());
			realOptionMatch.setTime(validator.formatStringToDate(optionMatch.getTime()));

			realOptionMatch.setStatus(Const.MATCH_STATUS_UNKNOWN);

			realOptionMatch = matchService.create(realOptionMatch);
			optionMatch.setId(realOptionMatch.getId());

			RevertMapping map2 = new RevertMapping();
			map2.setRealMatchId(optionMatch.getId());
			map2.setLocation(5);
			map2.setScheduleType(2);
			map2.setTableId(schedule.getTableId());
			map2.setNodeId(-1);
			map2.setDegree(0);
			schedule.getMapping().put(optionMatch.getId(), map2);

			schedule.setSummaryFinal(finalMatch);
			schedule.setOptionFinal(optionMatch);
		}
		schedule.setWinBranch(tree.getWinBranch());
		schedule.setLoseBranch(tree.getLoseBranch());
		schedule.setRankingTable(tree.getRankingTable());

		schedule.getProcess().setTotalMatch(totalMatch);
		schedule.getProcess().setFinishedMatch(finishedMatch);
		if (totalMatch == finishedMatch)
			schedule.getProcess().setFinish(true);
		else
			schedule.getProcess().setFinish(false);
		if (totalMatch > 0) {
			schedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
		}

		System.out.println("ScheduleService: saveFinalScheduleToDatabase: finish");
		return schedule;
	}

	private RoundRobinScheduleDTO createMatchesInDatabase(RoundRobinScheduleDTO schedule, ArrayList<TeamEntity> teams,
			CompetitionEntity competition) {
		System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: start");
		RoundRobinTable table = new RoundRobinTable((long) schedule.getTableId(), schedule.getMatches(),
				schedule.getTotalTeam(), schedule.isHasHomeMatch());
		System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: cp1");
		table.applyTeams(teams);
		int totalMatch = 0;
		int finishedMatch = 0;
		System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: cp2");
		for (Match match : table.getMatches()) {
			totalMatch++;
			MatchEntity realMatch = new MatchEntity();
			realMatch.setCompetition(competition);
			realMatch.setName(match.getName());
			realMatch.setLocation(match.getLocation());
			realMatch.setTime(validator.formatStringToDate(match.getTime()));

			if (match.getStatus() == -1) {
				realMatch.setStatus(Const.MATCH_STATUS_FINISHED);
				realMatch.setWinnner(teamService.findOneById(match.getWinner().getTeam().getId()));
				finishedMatch++;
			}
			if (match.getStatus() == 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PLAYING);
			}
			if (match.getStatus() > 0) {
				realMatch.setStatus(Const.MATCH_STATUS_PENDING);
			}

			try {
				realMatch.setTeam1(teamService.findOneById(match.getTeam1().getTeam().getId()));
				realMatch.setTeam2(teamService.findOneById(match.getTeam2().getTeam().getId()));
			} catch (Exception e) {
			}

			System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: cp3");

			realMatch = matchService.create(realMatch);
			match.setId(realMatch.getId());

			RevertMapping map = new RevertMapping();
			map.setRealMatchId(match.getId());
			map.setLocation(-1);
			map.setScheduleType(3);
			map.setTableId(schedule.getTableId());
			map.setNodeId(match.getMatchNo());
			map.setDegree(0);
			System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: cp4");
			schedule.getMapping().put(match.getId(), map);
		}

		System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: cp5");
		schedule.setMatches(table.getMatches());
		schedule.setRankingTable(table.getRankingTable());
		System.out.println("RR create match: rankingtable" + table.getRankingTable().get(0).getTeam());
		schedule.getProcess().setTotalMatch(totalMatch);
		schedule.getProcess().setFinishedMatch(finishedMatch);
		if (totalMatch == finishedMatch)
			schedule.getProcess().setFinish(true);
		else
			schedule.getProcess().setFinish(false);
		if (totalMatch > 0) {
			schedule.getProcess().setProcess(new Double(finishedMatch) / new Double(totalMatch));
		}
		System.out.println("ScheduleService: RoundRobinScheduleDTO createMatchesInDatabase: finish");
		return schedule;
	}
	// ----------------------------------------------------------------------

	/*
	 * -----------------------------------------------------------------------------
	 * ---create
	 */
	@Override
	public ScheduleDTO createSchedule(CompetitionEntity thisCompetition) {
		System.out.println("ScheduleService: createSchedule: start");
		ScheduleDTO schedule = new ScheduleDTO();
		HashMap<Long, RevertMapping> map = new HashMap<Long, RevertMapping>();

		int totalTeamBeforeGroupStage;
		int totalTeamAfterGroupStage;

		Long competitionId = thisCompetition.getId();

		FormatEntity finalFormat;
		FormatEntity groupFormat;

		ArrayList<BoxDescription> descriptions = new ArrayList<>();

		totalTeamBeforeGroupStage = teamService.countByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_JOINED)
				.intValue();

		if (totalTeamBeforeGroupStage < 1) {
			System.out.println("ScheduleService: createSchedule: finish");
			return null;
		}

		boolean hasGroupStage = thisCompetition.isHasGroupStage();

		FinalStageSettingEntity fsse = thisCompetition.getFinalStageSetting();

		FinalStageScheduleDTO fss;
		GroupStageScheduleDTO gss = new GroupStageScheduleDTO();

		// group
		if (hasGroupStage) {

			GroupStageSettingEntity gsse = thisCompetition.getGroupStageSetting();
			groupFormat = gsse.getFormat();
			boolean hasHomeMatch = gsse.isHasHomeMatch();
			int maxTeamPerTable = gsse.getMaxTeamPerTable();
			int advanceTeamPerTable = gsse.getAdvanceTeamPerTable();
			int totalTable = (int) (totalTeamBeforeGroupStage / maxTeamPerTable);

			int totalTeamInFinalTable;

			if (totalTable != 0) {
				totalTeamInFinalTable = totalTeamBeforeGroupStage % maxTeamPerTable;
			} else {
				totalTeamInFinalTable = totalTeamBeforeGroupStage;
			}

			if (totalTeamInFinalTable == 0) {
				totalTeamInFinalTable = maxTeamPerTable;
			} else {
				totalTable++;
			}

			System.out.println("totalTeam: " + totalTeamBeforeGroupStage);
			System.out.println("maxTeamPerTable: " + maxTeamPerTable);
			System.out.println("total Table: " + totalTable);
			System.out.println("totalTeamInFinalTable: " + totalTeamInFinalTable);

			// schedule for group stage
			String formatName = groupFormat.getName();
			gss = groupStageScheduling(totalTeamBeforeGroupStage, formatName, hasHomeMatch, maxTeamPerTable,
					advanceTeamPerTable, totalTable, totalTeamInFinalTable);

			totalTeamAfterGroupStage = (totalTable - 1) * advanceTeamPerTable
					+ (int) Math.min(totalTeamInFinalTable, advanceTeamPerTable);

			// create descriptions
			ArrayList<RankingTable> rankingTables = new ArrayList<>();

			ArrayList<FinalStageScheduleDTO> tables = gss.getTables();
			for (FinalStageScheduleDTO tb : tables) {
				RankingTable rankingTable = tb.getRankingTable();
				rankingTables.add(rankingTable);
			}

			for (int i = 0; i < advanceTeamPerTable; i++) {
				for (RankingTable tb : rankingTables) {
					if (tb.size() > i) {
						descriptions.add(tb.get(i).getDescription());
					}
				}
			}

			map.putAll(gss.getMapping());

		} else {

			totalTeamAfterGroupStage = totalTeamBeforeGroupStage;
		}

		// final

		finalFormat = fsse.getFormat();
		boolean hasHomeMatch = fsse.isHasHomeMatch();
		String formatName = finalFormat.getName();
		fss = finalStageScheduling(totalTeamAfterGroupStage, formatName, hasHomeMatch, -1, descriptions, 1);

		schedule.setTotalTeam(totalTeamBeforeGroupStage);
		schedule.setHasGroupStage(hasGroupStage);
		schedule.setFinalStageSchedule(fss);
		schedule.setGroupStageSchedule(gss);

		map.putAll(fss.getMapping());
		schedule.setMapping(map);

		saveScheduleToFile(schedule, competitionId);

		System.out.println("ScheduleService: createSchedule: finish");
		return schedule;
	}

	private FinalStageScheduleDTO finalStageScheduling(int totalTeam, String formatName, boolean hasHomeMatch,
			int tableId, ArrayList<BoxDescription> descriptions, int firstSeed) {
		System.out.println("ScheduleService: finalStageScheduling: start");

		// debug -------------------------------------------------------
		System.out.println("ScheduleService: finalStageScheduling: totalTeam: " + totalTeam);
		System.out.println("ScheduleService: finalStageScheduling: formatName: " + formatName);
		System.out.println("ScheduleService: finalStageScheduling: tableId: " + tableId);
		// -------------------------------------------------------------
		if (totalTeam < 1) {
			return null;
		} else {
			try {
				if (formatName.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
					SingleEliminationScheduleDTO dto = new SingleEliminationScheduleDTO();
					System.out.println("ScheduleService: finalStageScheduling: -> SingleElimination: Constructor:");
					SingleEliminationTree tree = new SingleEliminationTree(totalTeam);
					System.out.println("SingleElimination: Constructor: -> ScheduleService: finalStageScheduling:");
					System.out.println("ScheduleService: finalStageScheduling: build tree OK");
					if (tableId < 0) {
						tree.applyDescriptions(descriptions);
						System.out.println("ScheduleService: finalStageScheduling: apply description OK");
					} else {
						tree.applyDescriptions(firstSeed);
						System.out.println("ScheduleService: finalStageScheduling: apply description OK");
					}

					tree.setTableId(tableId);
					System.out.println("ScheduleService: finalStageScheduling: set table id OK");

					dto.setBracket(tree.getBracket());
					dto.setHasMatch34(hasHomeMatch);
					dto.setMatch34(tree.getMatch34());

					dto.setRankingTable(tree.getRankingTable());
					dto.setMatches(tree.getMatches());
					dto.setFormatName(formatName);
					dto.setTotalTeam(totalTeam);
					dto.setTableId(tableId);
					if (tableId >= 0)
						dto.setTableName("" + Const.TABLE_NAMING.charAt(tableId));
					else
						dto.setTableName("");
					dto.setFirstSeed(firstSeed);
					dto.setTotalRound(tree.getTotalRound());
					dto.setRoundsNaming();
					dto.setStatus(Const.STAGE_INITIALIZING);
					dto.setMapping(new HashMap<Long, RevertMapping>());

					System.out.println("ScheduleService: finalStageScheduling: finish");
					return dto;
				}

				if (formatName.contains(Const.DOUBLE_ELIMINATION_FORMAT)) {

					DoubleEliminationScheduleDTO dto = new DoubleEliminationScheduleDTO();

					DoubleEliminationTree tree = new DoubleEliminationTree(totalTeam);
					if (tableId < 0)
						tree.applyDescriptions(descriptions);
					else {
						tree.applyDescriptions(firstSeed);
					}
					tree.setTableId(tableId);

					System.out.println("DE - CP1");

					dto.setWinBranch(tree.getWinBranch());
					dto.setLoseBranch(tree.getLoseBranch());
					System.out.println("DE - CP1-1");
					dto.setSummaryFinal(tree.getSummaryFinal());
					dto.setOptionFinal(tree.getOptionFinal());
					System.out.println("DE - CP1-2");
					dto.setTotalWinRound(tree.getTotalRound());
					dto.setTotalLoseRound(tree.getTotalLoseBranchRound());

					System.out.println("DE - CP2");

					dto.setRankingTable(tree.getRankingTable());
					dto.setMatches(tree.getMatches());
					dto.setFormatName(formatName);
					dto.setTotalTeam(totalTeam);
					dto.setTableId(tableId);
					if (tableId >= 0)
						dto.setTableName("" + Const.TABLE_NAMING.charAt(tableId));
					else
						dto.setTableName("");
					dto.setFirstSeed(firstSeed);
					dto.setTotalRound(tree.getTotalRound());
					dto.setRoundsNaming();
					dto.setStatus(Const.STAGE_INITIALIZING);
					dto.setMapping(new HashMap<Long, RevertMapping>());

					System.out.println("ScheduleService: finalStageScheduling: finish");
					return dto;
				}

				if (formatName.contains(Const.ROUND_ROBIN_FORMAT)) {

					RoundRobinScheduleDTO dto = new RoundRobinScheduleDTO();
					RoundRobinTable table;

					if (tableId >= 0) {
						table = new RoundRobinTable((long) tableId, totalTeam, hasHomeMatch);
						table.applyDescriptions(firstSeed);

					} else {
						table = new RoundRobinTable(totalTeam, hasHomeMatch);
						table.applyDescriptions(descriptions);
					}

					System.out.println("RR -CP1");

					table.setTableId(tableId);
					dto.setHasHomeMatch(hasHomeMatch);

					dto.setRankingTable(table.getRankingTable());
					dto.setMatches(table.getMatches());
					dto.setFormatName(formatName);
					dto.setTotalTeam(totalTeam);
					dto.setTableId(tableId);
					if (tableId >= 0)
						dto.setTableName("" + Const.TABLE_NAMING.charAt(tableId));
					else
						dto.setTableName("");
					dto.setFirstSeed(firstSeed);
					dto.setTotalRound(table.getTotalRound());
					dto.setRoundsNaming();
					dto.setStatus(Const.STAGE_INITIALIZING);
					dto.setMapping(new HashMap<Long, RevertMapping>());

					System.out.println("ScheduleService: finalStageScheduling: finish");
					return dto;
				}
			} catch (Exception e) {
				System.out.println("ScheduleService: finalStageScheduling: has exception");
				System.out.println(e);
				System.out.println("-------");
				System.out.println("ScheduleService: finalStageScheduling: finish");
				return null;
			}
		}

		FinalStageScheduleDTO schedule = new UnknownScheduleDTO();

		schedule.setRankingTable(new RankingTable());
		schedule.setMatches(new ArrayList<>());
		schedule.setFormatName(Const.ANOTHER_FORMAT);
		schedule.setTotalTeam(totalTeam);
		schedule.setTableId(tableId);
		schedule.setFirstSeed(firstSeed);
		schedule.setTotalRound(0);
		System.out.println("ScheduleService: finalStageScheduling: finish");
		return schedule;
	}

	private GroupStageScheduleDTO groupStageScheduling(int totalTeam, String formatName, boolean hasHomeMatch,
			int maxTeamPerTable, int advanceTeamPerTable, int totalTable, int totalTeamInFinalTable) {
		System.out.println("ScheduleService: groupStageScheduling: start");
		GroupStageScheduleDTO dto = new GroupStageScheduleDTO();

		System.out.println("totalTable: " + totalTable);

		dto.setTotalTeam(totalTeam);
		dto.setFormatName(formatName);
		dto.setHasHomeMatch(hasHomeMatch);
		dto.setMaxTeamPerTable(maxTeamPerTable);
		dto.setAdvanceTeamPerTable(advanceTeamPerTable);
		dto.setTotalTable(totalTable);
		dto.setTotalTeamInFinalTable(totalTeamInFinalTable);

		HashMap<Long, RevertMapping> map = new HashMap<Long, RevertMapping>();
		ArrayList<FinalStageScheduleDTO> tables = new ArrayList<>();
		ArrayList<BoxDescription> descriptions = new ArrayList<>();

		for (int tableId = 0; tableId < totalTable - 1; tableId++) {

			int firstSeed = tableId * maxTeamPerTable + 1;

			System.out.println("ScheduleService: groupStageScheduling: -> ScheduleService: finalStageScheduling");
			FinalStageScheduleDTO table = finalStageScheduling(maxTeamPerTable, formatName, hasHomeMatch, tableId,
					descriptions, firstSeed);
			System.out.println("ScheduleService: finalStageScheduling -> ScheduleService: groupStageScheduling");
			map.putAll(table.getMapping());
			tables.add(table);
		}

		int firstSeed = (totalTable - 1) * maxTeamPerTable + 1;
		System.out.println("ScheduleService: groupStageScheduling: -> ScheduleService: finalStageScheduling");
		tables.add(finalStageScheduling(totalTeamInFinalTable, formatName, hasHomeMatch, totalTable - 1, descriptions,
				firstSeed));
		System.out.println("ScheduleService: finalStageScheduling -> ScheduleService: groupStageScheduling");
		dto.setTables(tables);
		dto.setStatus(Const.STAGE_INITIALIZING);
		dto.setMapping(map);

		System.out.println("ScheduleService: groupStageScheduling: finish");
		return dto;
	}

	// --------------------

	/*
	 * --------------------------------------------------------------------- update
	 * result for a match after finished synchronize db and schedule
	 */

	@Override
	public ScheduleDTO finishMatch(MatchEntity match) {

		System.out.println("ScheduleService: finishMatch: start");
		ArrayList<ResultEntity> results = new ArrayList<>();
		results.addAll(match.getResults());

		CompetitionEntity thisCompetition = match.getCompetition();
		Long competitionId = thisCompetition.getId();
		ScheduleDTO schedule = getScheduleFromFile(competitionId);

		HashMap<Long, RevertMapping> allMap = schedule.getMapping();

		RevertMapping thisMap = allMap.get(match.getId());
		int nodeId = thisMap.getNodeId();
		int tableId = thisMap.getTableId();
		int degree = thisMap.getDegree();
		int location = thisMap.getLocation();

		System.out.println("LOCATION: " + location);
		System.out.println("nodeId: " + nodeId);
		System.out.println("tableId: " + tableId);
		System.out.println("degree: " + degree);

		Match thisMatch = new Match();
		RankingTable rankingTable = new RankingTable();

		Match nextIfWin = new Match();
		Match nextIfLose = new Match();

		MatchEntity nextIfWinReal = null;
		MatchEntity nextIfLoseReal = null;

		Long winnerId = match.getWinnner().getId();
		Long loserId = null;
		try {
			loserId = match.getLoser().getId();
		} catch (Exception e) {
		}
		System.out.println("Finish match: winnerId " + winnerId);
		System.out.println("Finish match: loserId " + loserId);

		boolean isLastMatch = false;

		switch (location) {
		case -1:
			System.out.println("case -1");
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				RoundRobinScheduleDTO thisTable = (RoundRobinScheduleDTO) gss.getTables().get(thisMap.getTableId());
				thisMatch = thisTable.getMatches().get(nodeId - 1);
				rankingTable = thisTable.getRankingTable();
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				System.out.println("total match: " + thisTable.getProcess().getTotalMatch());
				System.out.println("finished match: " + thisTable.getProcess().getFinishedMatch());
				System.out.println("is finish: " + thisTable.getProcess().isFinish());
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}
			} else {
				RoundRobinScheduleDTO fss = (RoundRobinScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getMatches().get(nodeId - 1);
				rankingTable = fss.getRankingTable();
				fss.getProcess().finishMatch();
			}
			updateMatch(match, thisMatch, results, rankingTable, 0);
			nextIfWin = null;
			nextIfLose = null;

			break;
		case 0:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				SingleEliminationScheduleDTO thisTable = (SingleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getBracket().getById(nodeId).getData();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					nextIfWin = thisTable.getBracket().getById(nodeId).getNextIfWin().getData();
					nextIfLose = thisTable.getBracket().getById(nodeId).getNextIfLose().getData();
				} catch (Exception e) {
				}
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}
			} else {
				SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getBracket().getById(nodeId).getData();
				rankingTable = fss.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					nextIfWin = fss.getBracket().getById(nodeId).getNextIfWin().getData();
					nextIfLose = fss.getBracket().getById(nodeId).getNextIfLose().getData();
				} catch (Exception e) {
				}
				fss.getProcess().finishMatch();
			}
			break;
		case 1:
			System.out.println("case 1: start");
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getWinBranch().getById(nodeId).getData();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					nextIfWin = thisTable.getWinBranch().getById(nodeId).getNextIfWin().getData();
					nextIfLose = thisTable.getWinBranch().getById(nodeId).getNextIfLose().getData();
				} catch (Exception e) {
				}
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}
			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getWinBranch().getById(nodeId).getData();
				rankingTable = fss.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					nextIfWin = fss.getWinBranch().getById(nodeId).getNextIfWin().getData();
					nextIfLose = fss.getWinBranch().getById(nodeId).getNextIfLose().getData();
				} catch (Exception e) {
				}
				fss.getProcess().finishMatch();
			}
			System.out.println("case 1: finish");
			break;
		case 2:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getLoseBranch().getByIdAndDegree(nodeId, degree).getData();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					nextIfWin = thisTable.getLoseBranch().getByIdAndDegree(nodeId, degree).getNextIfWin().getData();

				} catch (Exception e) {
				}
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}

			} else {
				
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getLoseBranch().getByIdAndDegree(nodeId, degree).getData();
				rankingTable = fss.getRankingTable();
				
				updateMatch(match, thisMatch, results, rankingTable, 1);
				try {
					
					nextIfWin = fss.getLoseBranch().getByIdAndDegree(nodeId, degree).getNextIfWin().getData();
					System.out.println("case 2: thisMatch: " + thisMatch);
					System.out.println("case 2: nextIfWin: " + nextIfWin);
				} catch (Exception e) {
				}
				fss.getProcess().finishMatch();
			}
			nextIfLose = null;
			break;
		case 3:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				SingleEliminationScheduleDTO thisTable = (SingleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getMatch34();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}
			} else {
				SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getMatch34();
				rankingTable = fss.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				fss.getProcess().finishMatch();
			}
			nextIfWin = null;
			nextIfLose = null;
			break;
		case 4:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getSummaryFinal();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				nextIfWin = thisTable.getOptionFinal();
				nextIfLose = null;

				System.out.println("location 4");

				nextIfWinReal = matchService.findOneById(nextIfWin.getId());

				Team team1 = thisMatch.getTeam1().getTeam();
				Team team2 = thisMatch.getTeam2().getTeam();

				if (team2 != null && team2.getId().longValue() == winnerId.longValue()) {
					System.out.println("set id");
					nextIfWinReal.setTeam1(teamService.findOneById(team2.getId()));
					nextIfWinReal.setTeam2(teamService.findOneById(team1.getId()));
					nextIfWinReal.setStatus(Const.MATCH_STATUS_PLAYING);
				} else {
					nextIfWinReal.setStatus(Const.MATCH_STATUS_FINISHED);
					thisTable.getProcess().finishMatch();
					gss.getProcess().finishMatch();
					schedule.getProcess().finishMatch();
				}
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}

			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getSummaryFinal();
				rankingTable = fss.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				fss.getProcess().finishMatch();
				nextIfWin = fss.getOptionFinal();
				nextIfLose = null;

				nextIfWinReal = matchService.findOneById(nextIfWin.getId());

				Team team1 = thisMatch.getTeam1().getTeam();
				Team team2 = thisMatch.getTeam2().getTeam();

				if (team2 != null && team2.getId().longValue() == winnerId.longValue()) {
					System.out.println("set id");
					nextIfWinReal.setTeam1(teamService.findOneById(team2.getId()));
					nextIfWinReal.setTeam2(teamService.findOneById(team1.getId()));
					nextIfWinReal.setStatus(Const.MATCH_STATUS_PLAYING);
				} else {
					nextIfWinReal.setStatus(Const.MATCH_STATUS_FINISHED);
					fss.getProcess().finishMatch();
					schedule.getProcess().finishMatch();
				}
			}

			break;
		case 5:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables()
						.get(thisMap.getTableId());
				thisMatch = thisTable.getOptionFinal();
				rankingTable = thisTable.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				thisTable.getProcess().finishMatch();
				gss.getProcess().finishMatch();
				if (thisTable.getProcess().isFinish()) {
					isLastMatch = true;
				}
			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getOptionFinal();
				rankingTable = fss.getRankingTable();
				updateMatch(match, thisMatch, results, rankingTable, 1);
				fss.getProcess().finishMatch();
			}
			nextIfWin = null;
			nextIfLose = null;
			break;

		}

		schedule.getProcess().finishMatch();

		if (location != 4) {
			System.out.println("locaton: " + location);
			if (nextIfWin != null && nextIfWin.getId() != null) {
				System.out.println("set nextIfWin");
				System.out.println("nextIfWin id: " + nextIfWin.getId());
				nextIfWinReal = matchService.findOneById(nextIfWin.getId().longValue());
				System.out.println("nextIfWin name: " + nextIfWinReal.getName());
				Team team1 = nextIfWin.getTeam1().getTeam();
				Team team2 = nextIfWin.getTeam2().getTeam();
				System.out.println("team1: " + team1);
				System.out.println("team2: " + team2);

				if (team1 != null && team1.getId().longValue() == winnerId.longValue()) {
					System.out.println("Finish match: team1.getId() " + team1.getId());
					nextIfWinReal.setTeam1(teamService.findOneById(team1.getId()));

				}

				if (team2 != null && team2.getId().longValue() == winnerId.longValue()) {
					System.out.println("Finish match: team2.getId() " + team2.getId());
					nextIfWinReal.setTeam2(teamService.findOneById(team2.getId()));

				}
				if (team1 != null && team2 != null) {
					nextIfWinReal.setStatus(Const.MATCH_STATUS_PLAYING);
				}
			}

			if (loserId != null && nextIfLose != null && nextIfLose.getId() != null) {
				System.out.println("set nextIfLose");
				nextIfLoseReal = matchService.findOneById(nextIfLose.getId());
				Team team1 = nextIfLose.getTeam1().getTeam();
				Team team2 = nextIfLose.getTeam2().getTeam();
				if (team1 != null && team1.getId().longValue() == loserId.longValue()) {
					System.out.println("Finish match: team1.getId() " + team1.getId());
					nextIfLoseReal.setTeam1(teamService.findOneById(team1.getId()));
					System.out.println(nextIfLoseReal.getTeam1().getId());
				}
				if (team2 != null && team2.getId().longValue() == loserId.longValue()) {
					System.out.println("Finish match: team2.getId() " + team2.getId());
					nextIfLoseReal.setTeam2(teamService.findOneById(team2.getId()));
					System.out.println(nextIfLoseReal.getTeam2().getId());
				}
				if (team1 != null && team2 != null) {
					nextIfLoseReal.setStatus(Const.MATCH_STATUS_PLAYING);
				}
				if (nextIfLose.getStatus() == -1) {
					System.out.println("finish a finished match");
					nextIfLoseReal.setWinnner(nextIfLoseReal.getTeam1());
					matchService.update(nextIfLoseReal.getId(), nextIfLoseReal);
					System.out.println("nextIfLose: winner: " + nextIfLoseReal.getWinnner());
					saveScheduleToFile(schedule, competitionId);
					schedule = finishMatch(nextIfLoseReal);
				}
			}
		} else {

		}

		if (nextIfWinReal != null) {
			matchService.update(nextIfWinReal.getId(), nextIfWinReal);
		}

		if (nextIfLoseReal != null) {
			matchService.update(nextIfLoseReal.getId(), nextIfLoseReal);
		}

		if (tableId >= 0 && isLastMatch) {
			System.out.println("Last match");
			String finalStageFormat = schedule.getFinalStageSchedule().getFormatName();
			int top = (int) Math.min(schedule.getGroupStageSchedule().getAdvanceTeamPerTable(), rankingTable.size());
			if (finalStageFormat.contains(Const.SINGLE_ELIMINATION_FORMAT)) {
				System.out.println("SINGLE_ELIMINATION_FORMAT");
				schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
						(SingleEliminationScheduleDTO) schedule.getFinalStageSchedule(), rankingTable, (long) tableId,
						top));
			}
			if (finalStageFormat.contains(Const.DOUBLE_ELIMINATION_FORMAT)) {
				System.out.println("DOUBLE_ELIMINATION_FORMAT");
				schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
						(DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule(), rankingTable, (long) tableId,
						top));
			}
			if (finalStageFormat.contains(Const.ROUND_ROBIN_FORMAT)) {
				System.out.println("ROUND_ROBIN_FORMAT");
				schedule.setFinalStageSchedule(updateTeamFromGroupStageToFinalStage(
						(RoundRobinScheduleDTO) schedule.getFinalStageSchedule(), rankingTable, (long) tableId, top));
			}

		}

		saveScheduleToFile(schedule, match.getCompetition().getId());
		System.out.println("ScheduleService: finishMatch: finish");
		return schedule;
	}

	private void updateMatch(MatchEntity match, Match thisMatch, ArrayList<ResultEntity> results, RankingTable rt,
			int type) {

		Integer team1Score = 0;
		Integer team2Score = 0;

		Double team1Diff = 0.0;
		Double team2Diff = 0.0;

		Double team1Elo = rt.getEloByTeamId(match.getTeam1().getId());

		Double team2Elo = 0.0;
		if (match.getTeam2() != null)
			team2Elo = rt.getEloByTeamId(match.getTeam2().getId());

		Double diff = Math.abs(team1Elo - team2Elo);

		Double winElo = diff;
		Double loseElo = -1 * diff;

		if (diff < Const.EPSILON) {
			winElo = team1Elo / 100.0;
			loseElo = -1 * winElo;
		}

		if (type == 0) {
			winElo = Const.DEFAULT_ELO / 10.0;
			loseElo = 0.0;
		}

		for (ResultEntity r : results) {
			Double tmpDiff1 = new Double(r.getTeam1Score() - r.getTeam2Score());
			Double tmpDiff2 = 0 - tmpDiff1;

			team1Diff += tmpDiff1;
			team2Diff += tmpDiff2;

			if (tmpDiff1 > 0)
				team1Score++;

			if (tmpDiff2 > 0)
				team2Score++;
		}

		if (match.getWinnner().getId() == match.getTeam1().getId()) {
			thisMatch.getWinner().setTeam(thisMatch.getTeam1().getTeam());
			thisMatch.getLoser().setTeam(thisMatch.getTeam2().getTeam());
			if (team1Elo <= team2Elo || type == 0) {
				rt.updateByTeamId(match.getTeam1().getId(), team1Score, team1Diff, true, winElo);
				if (match.getTeam2() != null)
					rt.updateByTeamId(match.getTeam2().getId(), team2Score, team2Diff, false, loseElo);
			}
		} else {
			thisMatch.getWinner().setTeam(thisMatch.getTeam2().getTeam());
			thisMatch.getLoser().setTeam(thisMatch.getTeam1().getTeam());
			if (team1Elo >= team2Elo || type == 0) {
				rt.updateByTeamId(match.getTeam1().getId(), team1Score, team1Diff, false, loseElo);
				if (match.getTeam2() != null)
					rt.updateByTeamId(match.getTeam2().getId(), team2Score, team2Diff, true, winElo);
			}
		}

		thisMatch.getTeam1().setScore(team1Score);

		thisMatch.getTeam1().setDifference(team1Diff);

		if (match.getTeam2() != null) {
			thisMatch.getTeam2().setScore(team2Score);
			thisMatch.getTeam2().setDifference(team2Diff);
		}
	}

	private SingleEliminationScheduleDTO updateTeamFromGroupStageToFinalStage(
			SingleEliminationScheduleDTO finalSchedule, RankingTable rt, Long tableId, int getTop) {

		updateMatchInSE(finalSchedule.getBracket().getRoot(), tableId, rt);
		for (int rank = 0; rank < getTop; rank++) {
			Team t = rt.get(rank).getTeam();
			System.out.println("SingleEliminationScheduleDTO updateTeamFromGroupStageToFinalStage: rank: " + rank);
			finalSchedule.getRankingTable().addTeam(t);
			System.out.println("SingleEliminationScheduleDTO updateTeamFromGroupStageToFinalStage: update: "
					+ finalSchedule.getRankingTable().get(0));
		}
		return finalSchedule;
	}

	private void updateMatchInSE(Node<Match> node, Long tableId, RankingTable rt) {
		if (node == null) {
			return;
		}

		BoxDescription des1 = null;
		BoxDescription des2 = null;
		try {
			des1 = node.getData().getTeam1().getDescription();
			des2 = node.getData().getTeam2().getDescription();
		} catch (Exception e) {
		}
		if (des1 != null && des1.getDescType() == 1) {
			Long unitId = (long) des1.getUnitIndex();
			Long rank = des1.getUnitRank();
			if (unitId.longValue() == tableId.longValue()) {
				Team team = rt.get(rank.intValue() - 1).getTeam();
				TeamEntity realTeam = teamService.findOneById(team.getId());
				Match match = node.getData();
				MatchEntity realMatch = matchService.findOneById(match.getId());
				match.getTeam1().setTeam(team);
				realMatch.setTeam1(realTeam);
				matchService.update(realMatch.getId(), realMatch);
			}
		}
		if (des2 != null && des2.getDescType() == 1) {
			Long unitId = (long) des2.getUnitIndex();
			Long rank = des2.getUnitRank();
			if (unitId.longValue() == tableId.longValue()) {
				Team team = rt.get(rank.intValue() - 1).getTeam();
				TeamEntity realTeam = teamService.findOneById(team.getId());
				Match match = node.getData();
				MatchEntity realMatch = matchService.findOneById(match.getId());
				match.getTeam2().setTeam(team);
				realMatch.setTeam2(realTeam);
				matchService.update(realMatch.getId(), realMatch);
			}
		}
		updateMatchInSE(node.getLeft(), tableId, rt);
		updateMatchInSE(node.getRight(), tableId, rt);
	}

	private DoubleEliminationScheduleDTO updateTeamFromGroupStageToFinalStage(
			DoubleEliminationScheduleDTO finalSchedule, RankingTable rt, Long tableId, int getTop) {
		updateMatchInSE(finalSchedule.getWinBranch().getRoot(), tableId, rt);
		for (int rank = 0; rank < getTop; rank++) {
			Team t = rt.get(rank).getTeam();
			finalSchedule.getRankingTable().addTeam(t);
		}
		return finalSchedule;
	}

	private RoundRobinScheduleDTO updateTeamFromGroupStageToFinalStage(RoundRobinScheduleDTO finalSchedule,
			RankingTable rt, Long tableId, int getTop) {
		for (Match match : finalSchedule.getMatches()) {
			BoxDescription des1 = null;
			BoxDescription des2 = null;
			try {
				des1 = match.getTeam1().getDescription();
				des2 = match.getTeam2().getDescription();
			} catch (Exception e) {
			}
			if (des1 != null && des1.getDescType() == 1) {
				Long unitId = (long) des1.getUnitIndex();
				Long rank = des1.getUnitRank();
				if (unitId.longValue() == tableId.longValue()) {
					Team team = rt.get(rank.intValue() - 1).getTeam();
					TeamEntity realTeam = teamService.findOneById(team.getId());
					MatchEntity realMatch = matchService.findOneById(match.getId());
					match.getTeam1().setTeam(team);
					realMatch.setTeam1(realTeam);
					matchService.update(realMatch.getId(), realMatch);
				}
			}
			if (des2 != null && des2.getDescType() == 1) {
				Long unitId = (long) des2.getUnitIndex();
				Long rank = des2.getUnitRank();
				if (unitId.longValue() == tableId.longValue()) {
					Team team = rt.get(rank.intValue() - 1).getTeam();
					TeamEntity realTeam = teamService.findOneById(team.getId());
					MatchEntity realMatch = matchService.findOneById(match.getId());
					match.getTeam2().setTeam(team);
					realMatch.setTeam2(realTeam);
					matchService.update(realMatch.getId(), realMatch);
				}
			}
		}
		for (int rank = 0; rank < getTop; rank++) {
			Team t = rt.get(rank).getTeam();
			finalSchedule.getRankingTable().addTeam(t);
		}
		return finalSchedule;
	}

	// ----------------------------------------------------------------------

	@Override
	public ScheduleDTO changeMatchInfo(CompetitionEntity competition, Integer nodeId, Integer degree, Integer location,
			Integer tableId, HashMap<String, Object> newInfo) {
		Match thisMatch = new Match();

		ScheduleDTO schedule = getSchedule(competition);

		switch (location) {
		case -1:
			System.out.println("case -1");
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				RoundRobinScheduleDTO thisTable = (RoundRobinScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getMatches().get(nodeId - 1);

			} else {
				RoundRobinScheduleDTO fss = (RoundRobinScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getMatches().get(nodeId - 1);
			}

			break;
		case 0:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				SingleEliminationScheduleDTO thisTable = (SingleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getBracket().getById(nodeId).getData();

			} else {
				SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getBracket().getById(nodeId).getData();

			}
			break;
		case 1:

			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getWinBranch().getById(nodeId).getData();

			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getWinBranch().getById(nodeId).getData();

			}

			break;
		case 2:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getLoseBranch().getByIdAndDegree(nodeId, degree).getData();

			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getLoseBranch().getByIdAndDegree(nodeId, degree).getData();

			}
			break;
		case 3:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				SingleEliminationScheduleDTO thisTable = (SingleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getMatch34();

			} else {
				SingleEliminationScheduleDTO fss = (SingleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getMatch34();

			}
			break;
		case 4:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getSummaryFinal();

			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getSummaryFinal();

			}

			break;
		case 5:
			if (tableId >= 0) {
				GroupStageScheduleDTO gss = schedule.getGroupStageSchedule();
				DoubleEliminationScheduleDTO thisTable = (DoubleEliminationScheduleDTO) gss.getTables().get(tableId);
				thisMatch = thisTable.getOptionFinal();
			} else {
				DoubleEliminationScheduleDTO fss = (DoubleEliminationScheduleDTO) schedule.getFinalStageSchedule();
				thisMatch = fss.getOptionFinal();
			}
			break;

		}

		thisMatch.setLocation((String) newInfo.get("location"));
		thisMatch.setTime((String) newInfo.get("time"));

		saveScheduleToFile(schedule, competition.getId());

		return schedule;
	}
}
