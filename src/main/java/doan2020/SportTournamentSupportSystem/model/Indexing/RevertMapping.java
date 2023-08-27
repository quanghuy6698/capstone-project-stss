package doan2020.SportTournamentSupportSystem.model.Indexing;

import java.io.Serializable;

public class RevertMapping implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long realMatchId;
	
	/* 
	 * 1 - se
	 * 2 - de
	 * 3 - rr
	 */
	private int scheduleType; 
	
	/* 
	 * -1 - rr
	 * 0 - se bracket
	 * 1 - winBranch
	 * 2 - loseBranch
	 * 3 - match34
	 * 4 - summaryFinalMatch, 
	 * 5 - optionFinalMatch
	 */
	private int location; 
	
	private int tableId; // -1: final stage, 0-x
	private int nodeId;
	private int degree;
	
	public Long getRealMatchId() {
		return realMatchId;
	}
	public void setRealMatchId(Long realMatchId) {
		this.realMatchId = realMatchId;
	}
	public int getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(int scheduleType) {
		this.scheduleType = scheduleType;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	
}
