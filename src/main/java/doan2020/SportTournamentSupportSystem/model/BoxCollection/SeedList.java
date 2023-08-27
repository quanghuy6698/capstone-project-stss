package doan2020.SportTournamentSupportSystem.model.BoxCollection;

import java.io.Serializable;
import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.model.Box.Seed;
import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

public class SeedList extends ArrayList<Seed> implements Serializable {

	private static final long serialVersionUID = 3368100502342752403L;

	private int totalSeed;

	public SeedList() {
		this.totalSeed = 0;
	}

	public SeedList(int totalTeam) {
		for (int i = 1; i <= totalTeam; i++) {
			Seed s = new Seed(i);
			this.add(s);
		}
		this.totalSeed = totalTeam;
	}

	public SeedList(int totalTeam, int firstSeed) {
		
		for (int i = firstSeed; i <= totalTeam + firstSeed - 1; i++) {
			Seed s = new Seed(i);
			this.add(s);
		}
		this.totalSeed = totalTeam;
	}

	public void applyTeams(ArrayList<TeamEntity> teams) {
		System.out.println("SeedList: applyTeams: start");
		java.util.Collections.sort(teams, new TeamEntity());
		int seedNo = 0;
		for (Seed seed : this) {
			Team t = new Team();
			t.setId(teams.get(seedNo).getId());
			t.setShortName(teams.get(seedNo).getShortName());
			t.setFullName(teams.get(seedNo).getFullName());

			seed.setTeam(t);
			seedNo++;
		}
		System.out.println("SeedList: applyTeams: finish");
	}

	public void applyDescriptions(ArrayList<BoxDescription> descriptions) {
		int seedNo = 0;
		for (Seed seed : this) {
			seed.setDescription(descriptions.get(seedNo));
			seedNo++;
		}
	}

	public void applyDescriptions(int firstSeed) {
		System.out.println("Before apply:");
		System.out.println(this.toString());
		int seedNo = 0;
		for (Seed seed : this) {
			seed.getDescription().setUnitIndex(seedNo + firstSeed);;
			seedNo++;
		}
		
		System.out.println("After apply:");
		System.out.println(this.toString());

	}

	@Override
	public String toString() {
		String s = "";
		for (Seed seed: this) {
			s += "\n[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[" + seed.getDescription().getDescription();
		}
		return s;
	}

	public int getTotalSeed() {
		return totalSeed;
	}
	
	

}
