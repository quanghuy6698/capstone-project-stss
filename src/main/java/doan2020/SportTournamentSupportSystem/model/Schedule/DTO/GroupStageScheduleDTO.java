package doan2020.SportTournamentSupportSystem.model.Schedule.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import doan2020.SportTournamentSupportSystem.model.Process;
import doan2020.SportTournamentSupportSystem.model.Indexing.RevertMapping;

public class GroupStageScheduleDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected int totalTeam;
	protected String formatName;
	protected boolean hasHomeMatch;
	protected int maxTeamPerTable;
	protected int advanceTeamPerTable;
	protected int totalTable;
	protected int totalTeamInFinalTable;
	protected String status;
	protected HashMap<Long, RevertMapping> mapping;
	protected Process process = new Process();
	
	ArrayList<FinalStageScheduleDTO> tables;

	public int getTotalTeam() {
		return totalTeam;
	}

	public void setTotalTeam(int totalTeam) {
		this.totalTeam = totalTeam;
	}

	public String getFormatName() {
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

	public boolean isHasHomeMatch() {
		return hasHomeMatch;
	}

	public void setHasHomeMatch(boolean hasHomeMatch) {
		this.hasHomeMatch = hasHomeMatch;
	}

	public int getMaxTeamPerTable() {
		return maxTeamPerTable;
	}

	public void setMaxTeamPerTable(int maxTeamPerTable) {
		this.maxTeamPerTable = maxTeamPerTable;
	}

	public int getAdvanceTeamPerTable() {
		return advanceTeamPerTable;
	}

	public void setAdvanceTeamPerTable(int advanceTeamPerTable) {
		this.advanceTeamPerTable = advanceTeamPerTable;
	}

	public int getTotalTable() {
		return totalTable;
	}

	public void setTotalTable(int totalTable) {
		this.totalTable = totalTable;
	}

	public int getTotalTeamInFinalTable() {
		return totalTeamInFinalTable;
	}

	public void setTotalTeamInFinalTable(int totalTeamInFinalTable) {
		this.totalTeamInFinalTable = totalTeamInFinalTable;
	}

	public ArrayList<FinalStageScheduleDTO> getTables() {
		return tables;
	}

	public void setTables(ArrayList<FinalStageScheduleDTO> tables) {
		this.tables = tables;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<Long, RevertMapping> getMapping() {
		return mapping;
	}

	public void setMapping(HashMap<Long, RevertMapping> map) {
		this.mapping = map;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

}
