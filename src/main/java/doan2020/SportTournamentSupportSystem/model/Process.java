package doan2020.SportTournamentSupportSystem.model;

import java.io.Serializable;

public class Process implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer totalMatch = 0;
	private Integer finishedMatch = 0;
	private Double process = 0.0;
	private boolean isFinish = false;
	
	public Integer getTotalMatch() {
		return totalMatch;
	}
	public void setTotalMatch(Integer totalMatch) {
		this.totalMatch = totalMatch;
	}
	public Integer getFinishedMatch() {
		return finishedMatch;
	}
	public void setFinishedMatch(Integer finishedMatch) {
		this.finishedMatch = finishedMatch;
	}
	public Double getProcess() {
		return process;
	}
	public void setProcess(Double process) {
		this.process = process;
	}
	public boolean isFinish() {
		return isFinish;
	}
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
	public void finishMatch() {
		this.finishedMatch ++;
		this.isFinish = (this.finishedMatch.intValue() == this.totalMatch.intValue());
		if (this.totalMatch > 0)
			this.process = new Double(this.finishedMatch) / new Double(this.totalMatch);
	}
	

}
