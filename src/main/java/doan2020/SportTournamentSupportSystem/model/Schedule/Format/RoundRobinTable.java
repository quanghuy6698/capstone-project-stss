package doan2020.SportTournamentSupportSystem.model.Schedule.Format;

import java.io.Serializable;
import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;
import doan2020.SportTournamentSupportSystem.model.Schedule.ScheduleStruct;
import doan2020.SportTournamentSupportSystem.model.Box.MatchSlot;

public class RoundRobinTable extends ScheduleStruct implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name = "RoundRobinTable";
	private String matchNaming = Const.ROUND_ROBIN_MATCH_NAMING;

	private int totalMatch;
	private boolean homeMatch = false;

	private int totalTeam;

	public RoundRobinTable() {
		super();
	}

	public RoundRobinTable(Long id, int totalTeam, boolean homeMatch) {
		super(totalTeam, id.intValue());
		this.id = id;
		if (id >= 0) {
			this.matchNaming = Const.TABLE_NAMING.charAt(this.id.intValue()) + "-";
			this.name = "Table " + Const.TABLE_NAMING.charAt(this.id.intValue());
		}
		this.homeMatch = homeMatch;
		this.totalTeam = totalTeam;
		this.totalRound = calTotalRound(totalTeam, homeMatch);
		this.matches = this.createMatches(this.totalTeam, homeMatch);
		this.setTotalMatch(this.matches.size());
	}

	public RoundRobinTable(int totalTeam, boolean homeMatch) {
		super(totalTeam);
		this.id = -1l;
		this.homeMatch = homeMatch;
		this.totalTeam = totalTeam;
		this.totalRound = calTotalRound(totalTeam, homeMatch);
		this.matches = this.createMatches(this.totalTeam, homeMatch);
		this.setTotalMatch(this.matches.size());
	}

	public RoundRobinTable(int totalTeam) {
		super(totalTeam);
		this.id = -1l;
		this.homeMatch = false;
		this.totalTeam = totalTeam;
		this.totalRound = calTotalRound(totalTeam, false);
		this.matches = this.createMatches(this.totalTeam, homeMatch);
		this.setTotalMatch(this.matches.size());
	}

	public RoundRobinTable(Long id, ArrayList<Match> matches, int totalTeam, boolean homeMatch) {
		super(totalTeam, id.intValue());
		System.out.println("RoundRobinTable: Constructor: start");
		this.id = id;
		if (id >= 0) {
			this.matchNaming = Const.TABLE_NAMING.charAt(this.id.intValue()) + "-";
			this.name = "Table " + Const.TABLE_NAMING.charAt(this.id.intValue());
		}
		System.out.println("RoundRobinTable: Constructor: set id ok");
		this.homeMatch = homeMatch;
		this.totalTeam = totalTeam;
		System.out.println("RoundRobinTable: Constructor: set total team ok");
		this.totalRound = calTotalRound(totalTeam, homeMatch);
		this.matches = matches;
		System.out.println("RoundRobinTable: Constructor: set matches ok");
		this.setTotalMatch(this.matches.size());
		System.out.println("RoundRobinTable: Constructor: finish");
	}

	private ArrayList<Match> createMatches(int totalTeam, boolean homeMatch) {
		ArrayList<Match> matches = new ArrayList<>();

		if (totalTeam < 1) {
			return matches;
		}

		if (totalTeam == 1) {
			Match info = new Match();
			info.setMatchNo(1);
			info.setName(this.matchNaming + info.getMatchNo());
			info.setRoundNo(1);
			info.setTeam1(new MatchSlot());
			info.setTeam2(new MatchSlot());
			info.setWinner(info.getTeam1());
			info.setLoser(new MatchSlot());
			info.setStatus(-1);
			info.getTeam1().setDescription(new BoxDescription((long) 1));
			info.getTeam2().setDescription(new BoxDescription());
			matches.add(info);
			return matches;
		}

		if (homeMatch) {
			matches.addAll(createMatches(totalTeam, false));
		}

		int totalSeed = totalTeam + totalTeam % 2; // totalSeed % 2 == 0 (always)

		int runSeed = totalSeed;
		int rouletteTotal = totalSeed - 1;
		int coupleTotal = (rouletteTotal - 1) / 2;
		int totalMatch = matches.size();

		for (int round = 1; round <= rouletteTotal; round++) {
			int freeSeed = round;

			if (runSeed == totalTeam) {
				totalMatch++;
				Match info = new Match();
				info.setMatchNo(totalMatch);
				info.setName(this.matchNaming + info.getMatchNo());
				info.setRoundNo(homeMatch ? this.totalRound / 2 + round : round);
				info.setTeam1(new MatchSlot());
				info.setTeam2(new MatchSlot());
				info.setWinner(new MatchSlot());
				info.setLoser(new MatchSlot());
				info.setStatus(0);
				if ((freeSeed % 2 != 0 && !homeMatch) || (freeSeed % 2 == 0 && homeMatch)) {
					info.getTeam1().setDescription(new BoxDescription((long) freeSeed));
					info.getTeam2().setDescription(new BoxDescription((long) runSeed));
				} else {
					info.getTeam1().setDescription(new BoxDescription((long) runSeed));
					info.getTeam2().setDescription(new BoxDescription((long) freeSeed));
				}
				matches.add(info);

			} else {
				totalMatch++;
				Match info = new Match();
				info.setMatchNo(totalMatch);
				info.setName(this.matchNaming + info.getMatchNo());
				info.setRoundNo(homeMatch ? this.totalRound / 2 + round : round);
				info.setTeam1(new MatchSlot());
				info.setTeam2(new MatchSlot());
				info.setWinner(info.getTeam1());
				info.setLoser(new MatchSlot());
				info.setStatus(-1);
				info.getTeam1().setDescription(new BoxDescription((long) freeSeed));
				info.getTeam2().setDescription(new BoxDescription());
				matches.add(info);
			}

			for (int i = 1; i <= coupleTotal; i++) {
				int left = (freeSeed - 1 + i + rouletteTotal) % rouletteTotal + 1;
				int right = (freeSeed - 1 - i + rouletteTotal) % rouletteTotal + 1;

				totalMatch++;
				Match info = new Match();
				info.setMatchNo(totalMatch);
				info.setName(this.matchNaming + info.getMatchNo());
				info.setRoundNo(homeMatch ? this.totalRound / 2 + round : round);
				info.setTeam1(new MatchSlot());
				info.setTeam2(new MatchSlot());
				info.setWinner(new MatchSlot());
				info.setLoser(new MatchSlot());
				info.setStatus(0);
				if (homeMatch) {
					info.getTeam1().setDescription(new BoxDescription((long) left));
					info.getTeam2().setDescription(new BoxDescription((long) right));
				} else {
					info.getTeam1().setDescription(new BoxDescription((long) right));
					info.getTeam2().setDescription(new BoxDescription((long) left));
				}

				matches.add(info);
			}

		}

		return matches;
	}

	@Override
	protected void applyTeams() {
		System.out.println("RR: applyTeams: start");
		for (Match match : this.matches) {
			BoxDescription description1 = match.getTeam1().getDescription();
			if (description1.getDescType() == 0)
				match.getTeam1().setTeam(this.seedList.get((description1.getUnitIndex() - 1) % totalTeam).getTeam());

			BoxDescription description2 = match.getTeam2().getDescription();
			if (description2.getDescType() == 0)
				match.getTeam2().setTeam(this.seedList.get((description2.getUnitIndex() - 1) % totalTeam).getTeam());
		}
		System.out.println("RR: applyTeams: finish");
	}

	@Override
	protected void applyDescriptions() {

		for (Match match : this.matches) {
			BoxDescription description1 = match.getTeam1().getDescription();
			if (description1.getDescType() == 0)
				match.getTeam1().setDescription(this.seedList.get((description1.getUnitIndex() - 1) % totalTeam).getDescription());
			BoxDescription description2 = match.getTeam2().getDescription();
			if (description2.getDescType() == 0)
				match.getTeam2().setDescription(this.seedList.get((description2.getUnitIndex() - 1) % totalTeam).getDescription());
		}
	}

	protected int calTotalRound(int totalTeam, boolean hasHomeMatch) {
		if (totalTeam < 1) {
			return 0;
		}
		if (totalTeam == 1) {
			return 1;
		}
		int totalRound = totalTeam - ((totalTeam + 1) % 2);
		if (hasHomeMatch)
			totalRound *= 2;
		return totalRound;
	}

	public boolean isHomeMatch() {
		return homeMatch;
	}

	public void setHomeMatch(boolean homeMatch) {
		this.homeMatch = homeMatch;
	}

	public int getTotalMatch() {
		return totalMatch;
	}

	public void setTotalMatch(int totalMatch) {
		this.totalMatch = totalMatch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

}
