package doan2020.SportTournamentSupportSystem.model.Schedule.Format;

import java.io.Serializable;
import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.model.Box.MatchSlot;
import doan2020.SportTournamentSupportSystem.model.BoxCollection.SeedList;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;
import doan2020.SportTournamentSupportSystem.model.Struct.BTree;
import doan2020.SportTournamentSupportSystem.model.Struct.DoubleBTree;
import doan2020.SportTournamentSupportSystem.model.Struct.Node;

public class DoubleEliminationTree extends SingleEliminationTree implements Serializable {

	private static final long serialVersionUID = 1L;

	private String loseBranchNaming = Const.LOSE_BRANCH_NAMING;

	private Integer totalLoseBranchRound;
	protected int completeLoseTreeDegree;
	protected int firstRoundTotalLoseMatch;
	protected int firstRoundCurrentLoseMatch;

	private DoubleBTree<Match> loseBranch = new DoubleBTree<>();
	private Match summaryFinal;
	private Match optionFinal;

	// ------------Constructor

	public DoubleEliminationTree() {
		super(Const.WIN_BRANCH_NAMING);
	}

	public DoubleEliminationTree(int totalTeam) {
		super(totalTeam, Const.WIN_BRANCH_NAMING);

		System.out.println("DoubleEliminationTree: Contructor: winTree OK");
		this.bracket.setName("WinBranch");

		this.firstRoundTotalLoseMatch = this.calFirstRoundTotalLoseMatch(totalTeam);
		this.firstRoundCurrentLoseMatch = 0;
		this.totalLoseBranchRound = calTotalLoseBranchRound(this.totalTeam);
		System.out.println("DoubleEliminationTree: Contructor: etc number OK");

		this.loseBranch = new DoubleBTree<Match>(this.buildLoseBranch(null, 1, this.totalTeam - 1, 1));

		this.loseBranch.setName("LoseBranch");

		System.out.println("DoubleEliminationTree: Contructor: loseTree OK");

//		this.summaryFinal = getSummaryFinal();
//		System.out.println("DoubleEliminationTree: Contructor: get summary final OK");
//		this.optionFinal = getOptionFinal();
//		System.out.println("DoubleEliminationTree: Contructor: get option final OK");

		System.out.println("WIN BRANCH TOTAL MATCH: " + this.bracket.toArrayList().size());
		System.out.println("WIN BRANCH TOTAL MATCH: " + this.matches.size());
		if (this.loseBranch != null)
			this.matches.addAll(this.loseBranch.toArrayList());
		System.out.println("DoubleEliminationTree: Contructor: finish");
	}

	public DoubleEliminationTree(BTree<Match> winBranch, DoubleBTree<Match> loseBranch, int totalTeam) {
		super(winBranch, totalTeam, Const.WIN_BRANCH_NAMING);
		System.out.println("DoubleEliminationTree: Contructor: winTree OK");
		this.bracket.setName("WinBranch");

		this.firstRoundTotalLoseMatch = this.calFirstRoundTotalLoseMatch(totalTeam);
		System.out.println("FIRST ROUND TOTAL LOSE MATCH: " + this.firstRoundTotalLoseMatch);
		this.firstRoundCurrentLoseMatch = 0;
		this.totalLoseBranchRound = calTotalLoseBranchRound(this.totalTeam);

		this.loseBranch = loseBranch;
		this.loseBranch.setName("LoseBranch");

//		this.summaryFinal = getSummaryFinal();
//		this.optionFinal = getOptionFinal();

		System.out.println("WIN BRANCH TOTAL MATCH: " + this.bracket.toArrayList().size());
		System.out.println("WIN BRANCH TOTAL MATCH: " + this.matches.size());
		if (this.loseBranch != null)
			this.matches.addAll(this.loseBranch.toArrayList());
		System.out.println("DoubleEliminationTree: Contructor: finish");

	}

	// ------------Getter setter

	public SeedList getSeedList() {
		return seedList;
	}

	public BTree<Match> getWinBranch() {
		return this.bracket;
	}

	public DoubleBTree<Match> getLoseBranch() {
		return loseBranch;
	}

	public Integer getTotalLoseBranchRound() {
		return totalLoseBranchRound;
	}

	// ------------Logic code

	// -----------------Lose branch
	private Node<Match> buildLoseBranch(Node<Match> parent, Integer index, Integer totalLoseTeam, Integer leftIndex) {
		System.out.println("EliminationTree: buildLoseBranch: start");

		Node<Match> node = new Node<Match>();
		Match info = new Match();

		if (totalLoseTeam < 1) {
			return null;
		}

		System.out.println("EliminationTree: buildLoseBranch: CP1: totalLoseTeam: " + totalLoseTeam.toString());

		node.setId(index);
		node.setNextIfWin(parent);

		info.setTeam1(new MatchSlot());
		info.setTeam2(new MatchSlot());
		info.getTeam1().setDescription(new BoxDescription());
		info.getTeam2().setDescription(new BoxDescription());
		info.setWinner(new MatchSlot());
		info.setLoser(new MatchSlot());

		if (parent == null) { // this is root
			info.setRoundNo(this.totalLoseBranchRound);
			node.setDegree(0);
		} else {
			info.setRoundNo(parent.getData().getRoundNo() - 1);
			node.setDegree(parent.getDegree() + 1);
		}

		System.out.println("DoubleEliminationTree: buildLoseBranch: index: " + index);

		if (info.getRoundNo() == 1 && this.firstRoundCurrentLoseMatch < this.firstRoundTotalLoseMatch) {

			this.firstRoundCurrentLoseMatch++;
			info.setMatchNo(this.firstRoundCurrentLoseMatch);
			System.out.println("DoubleEliminationTree: buildLoseBranch: case1: " + info.getMatchNo());
		} else {
			info.setMatchNo(calLoseMatchNo(index, node.getDegree() + 1));
			System.out.println("DoubleEliminationTree: buildLoseBranch: case2: " + info.getMatchNo());
		}

		info.setTeam1(this.bracket.getById(leftIndex).getData().getLoser());
		node.setData(info);
		node.setLeft(this.bracket.getById(leftIndex));
		this.bracket.getById(leftIndex).setNextIfLose(node);

		if (totalLoseTeam == 1) {
			info.setMatchNo(1);
			info.setRoundNo(1);
			info.setName(this.loseBranchNaming + info.getMatchNo());
			info.setWinner(info.getTeam1());
			info.getLoser().setDescription(new BoxDescription(5l, info.getMatchNo()));

			info.setStatus(-1);

			node.setData(info);
			return node;
		}

		Integer rightIndex = reverseIndex(leftIndex) * 2 + 1;
		Integer rightLeftIndex = rightIndex - 1;

		if (this.bracket.getById(rightLeftIndex) == null) {
			node.setRight(this.bracket.getById(rightIndex));
			this.bracket.getById(rightIndex).setNextIfLose(node);

			info.setTeam2(this.bracket.getById(rightIndex).getData().getLoser());

			info.setStatus(3);

		} else {
			node.setRight(buildRightLoseBranch(node, index, totalLoseTeam, rightLeftIndex));

			info.setTeam1(this.bracket.getById(leftIndex).getData().getLoser());
			info.setTeam2(node.getRight().getData().getWinner());
			info.setStatus(4);

		}

		info.setName(this.loseBranchNaming + info.getMatchNo());

		info.getWinner().setDescription(new BoxDescription(3l, info.getMatchNo()));
		info.getLoser().setDescription(new BoxDescription(5l, info.getMatchNo()));

		node.setData(info);
		System.out.println("Check parent");
		System.out.println("DE: node: " + node);
		System.out.println("DE: parent: " + parent);
		System.out.println("EliminationTree: buildLoseBranch: finish");

		return node;
	}

	private Node<Match> buildRightLoseBranch(Node<Match> parent, Integer index, Integer totalLoseTeam,
			Integer leftIndex) {

		System.out.println("EliminationTree: buildRightLoseBranch: start");

		Node<Match> node = new Node<Match>();
		Match info = new Match();

		if (totalLoseTeam < 2) {
			return null;
		}

		System.out.println("EliminationTree: buildRightLoseBranch: CP1: totalLoseTeam: " + totalLoseTeam.toString());

		node.setId(index);
		node.setNextIfWin(parent);
		info.setTeam1(new MatchSlot());
		info.setTeam2(new MatchSlot());
		info.setWinner(new MatchSlot());
		info.setLoser(new MatchSlot());

		System.out.println("EliminationTree: buildRightLoseBranch: CP2: parent: " + parent.getData().getRoundNo());

		info.setRoundNo(parent.getData().getRoundNo() - 1);

		node.setDegree(parent.getDegree() + 1);

		System.out.println("DoubleEliminationTree: buildRightLoseBranch: index: " + index);
		if (info.getRoundNo() == 1 && this.firstRoundCurrentLoseMatch < this.firstRoundTotalLoseMatch) {
			this.firstRoundCurrentLoseMatch++;
			info.setMatchNo(this.firstRoundCurrentLoseMatch);
			System.out.println("DoubleEliminationTree: buildRightLoseBranch: case1: " + info.getMatchNo());
		} else {
			info.setMatchNo(calLoseMatchNo(index, node.getDegree() + 1));
			System.out.println("DoubleEliminationTree: buildRightLoseBranch: case2: " + info.getMatchNo());
		}

		Integer rightIndex = leftIndex + 1;

		Integer leftRightIndex = reverseIndex(leftIndex) * 2 + 1;
		Integer rightRightIndex = reverseIndex(rightIndex) * 2 + 1;

		node.setData(info);

		System.out.println("EliminationTree: buildRightLoseBranch: CP3: rightRightIndex: " + rightRightIndex);

		if (this.bracket.getById(rightRightIndex) == null && this.bracket.getById(leftRightIndex) == null) {

			node.setLeft(this.bracket.getById(leftIndex));
			node.setRight(this.bracket.getById(rightIndex));

			info.setTeam1(this.bracket.getById(leftIndex).getData().getLoser());
			info.setTeam2(this.bracket.getById(rightIndex).getData().getLoser());

			this.bracket.getById(leftIndex).setNextIfLose(node);
			this.bracket.getById(rightIndex).setNextIfLose(node);
			;
			info.setStatus(3);

		} else {
			if (this.bracket.getById(leftRightIndex) == null) {
				node.setLeft(this.bracket.getById(leftIndex));
				node.setRight(buildLoseBranch(node, index * 2 + 1, totalLoseTeam, rightIndex));

				info.setTeam1(this.bracket.getById(leftIndex).getData().getLoser());
				info.setTeam2(node.getRight().getData().getWinner());

				this.bracket.getById(leftIndex).setNextIfLose(node);

				info.setStatus(4);
			}

			if (this.bracket.getById(rightRightIndex) == null) {
				node.setLeft(buildLoseBranch(node, index * 2, totalLoseTeam, leftIndex));
				node.setRight(this.bracket.getById(rightIndex));

				info.setTeam1(node.getLeft().getData().getWinner());
				info.setTeam2(this.bracket.getById(rightIndex).getData().getLoser());

				this.bracket.getById(rightIndex).setNextIfLose(node);

				info.setStatus(4);
			}

			if (this.bracket.getById(rightRightIndex) != null && this.bracket.getById(leftRightIndex) != null) {
				node.setLeft(buildLoseBranch(node, index * 2, totalLoseTeam, leftIndex));
				node.setRight(buildLoseBranch(node, index * 2 + 1, totalLoseTeam, rightIndex));

				info.setTeam1(node.getLeft().getData().getWinner());
				info.setTeam2(node.getRight().getData().getWinner());
				info.setStatus(1);
			}
		}

		info.setName(this.loseBranchNaming + info.getMatchNo());
		info.getWinner().setDescription(new BoxDescription(3l, info.getRoundNo()));
		info.getLoser().setDescription(new BoxDescription(5l, info.getRoundNo()));

		node.setData(info);

		System.out.println("Check parent");
		System.out.println("DE: node: " + node);
		System.out.println("DE: parent: " + parent);
		System.out.println("EliminationTree: buildRightLoseBranch: finish");
		return node;
	}

	// ------------------ browse the tree

	protected void setSummaryFinal() {
		
		if (this.totalTeam < 2) {
			this.summaryFinal = null;
			return;
		}

		this.summaryFinal = new Match();
		this.summaryFinal.setMatchNo(1);
		this.summaryFinal.setRoundNo(1);
		this.summaryFinal.setName(Const.SUMMARY_FINAL + 1);

		MatchSlot winner = new MatchSlot();
		MatchSlot loser = new MatchSlot();

		winner.setDescription(new BoxDescription(-1l, Const.WIN_MATCH, 0l, Const.SUMMARY_FINAL, 1));
		loser.setDescription(new BoxDescription(-1l, Const.LOSE_MATCH, 0l, Const.SUMMARY_FINAL, 1));

		this.summaryFinal.setWinner(winner);
		this.summaryFinal.setLoser(loser);

		Node<Match> node = new Node<>();
		node.setData(this.summaryFinal);

		this.summaryFinal.setTeam1(this.bracket.getRoot().getData().getWinner());
		this.bracket.getRoot().setNextIfWin(node);

		this.summaryFinal.setTeam2(this.loseBranch.getRoot().getData().getWinner());
		this.loseBranch.getRoot().setNextIfWin(node);

	}

	protected void setOptionFinal() {
		if (this.getSummaryFinal() == null) {
			this.optionFinal = null;
			return;
		}
		this.optionFinal = new Match();
		this.optionFinal.setMatchNo(2);
		this.optionFinal.setRoundNo(2);
		this.optionFinal.setName(Const.SUMMARY_FINAL + 2);

		MatchSlot winner = new MatchSlot();
		MatchSlot loser = new MatchSlot();

		winner.setDescription(new BoxDescription(-1l, Const.WIN_MATCH, 0l, Const.SUMMARY_FINAL, 2));
		loser.setDescription(new BoxDescription(-1l, Const.LOSE_MATCH, 0l, Const.SUMMARY_FINAL, 2));

		this.optionFinal.setWinner(winner);
		this.optionFinal.setLoser(loser);

		this.optionFinal.setTeam1(this.getSummaryFinal().getWinner());
		this.optionFinal.setTeam2(this.getSummaryFinal().getLoser());

	}

	public Match getSummaryFinal() {
		if (this.summaryFinal == null) {
			this.setSummaryFinal();
		}

		return this.summaryFinal;
	}

	public Match getOptionFinal() {
		if (this.optionFinal == null) {
			this.setOptionFinal();
		}

		return this.optionFinal;
	}

	// ----------- support logic code

	private Integer reverseIndex(Integer index) {
		Integer k = ceilLog2(index + 1);
		Integer _2powk = (int) Math.pow(2, k);
		return _2powk + _2powk / 2 - 1 - index;
	}

	private Integer calTotalLoseBranchRound(Integer size) {
		int totalRound = 0;
		int x = 1;
		while (x < size) {
			x *= 2;
			totalRound++;
		}

		x /= 2;
		totalRound = (totalRound - 1) * 2;
		if (x + x / 2 >= size)
			totalRound -= 1;

		return totalRound;

	}

	//

	private int calLoseTreeCompleteDegree(int totalTeam) {

		int totalDegree = calTotalLoseBranchRound(totalTeam);
		int logicDegree = totalDegree / 2;

		int completeTreeTotalNode = (int) Math.pow(2, logicDegree + 1);
		if (totalDegree % 2 == 1) {
			completeTreeTotalNode += (int) Math.pow(2, logicDegree);
		}

		if (completeTreeTotalNode == totalTeam) {
			return totalDegree;
		} else {
			return totalDegree - 1;
		}
	}

	private int calTotalCompleteLoseTreeNode(int degree) {
		int totalNode = 2 * ((int) Math.pow(2, degree / 2) - 1);

		if (degree % 2 == 1) {
			totalNode += (int) Math.pow(2, degree / 2);
		}

		return totalNode;
	}

	private int calTotalTeamInCompleteTree(int degree) {
		int realDegree = (degree + 1) / 2;
		int totalTeam = (int) Math.pow(2, realDegree) - 1;
		if (degree % 2 == 0) {
			totalTeam += (int) Math.pow(2, realDegree);
		} else {
			totalTeam += (int) Math.pow(2, realDegree - 1);
		}
		return totalTeam;
	}

	private int calFirstRoundTotalLoseMatch(int totalTeam) {
		int degree = calLoseTreeCompleteDegree(totalTeam);
		return totalTeam - calTotalTeamInCompleteTree(degree) - 1;
	}

	private int calLoseMatchNo(int index, int nodeDegree) {
		System.out.println("\nDoubleElimination: calLoseMatchNo: start --------------------------------------");
		int matchNo = 0;

		int completeTreeDegree = calLoseTreeCompleteDegree(this.totalTeam);

		int totalNodeBelow = (int) calTotalCompleteLoseTreeNode(completeTreeDegree)
				- calTotalCompleteLoseTreeNode(nodeDegree);
		int totalNodeAbove = (int) Math.pow(2, (nodeDegree - 1) / 2) - 1;

		matchNo = index + totalNodeBelow - totalNodeAbove + this.firstRoundTotalLoseMatch;

		System.out.println("index: " + index);
		System.out.println("nodeDegree: " + nodeDegree);
		System.out.println("completeTreeDegree: " + completeTreeDegree);
		System.out.println("totalNodeBelow: " + totalNodeBelow);
		System.out.println("totalNodeAbove: " + totalNodeAbove);
		System.out.println("matchNo: " + matchNo);
		System.out.println("DoubleElimination: calLoseMatchNo: finish -------------------------------------\n");
		return matchNo;

	}

//	public static void main(String[] args) {
//		int totalTeam = 15;
//		DoubleEliminationTree tree = new DoubleEliminationTree(totalTeam);
//		System.out.println("matchNo: " + tree.getLoseBranch().getByIdAndDegree(1, 2).getData().getTeam1().getDescription().getDescription());
//	}

}
