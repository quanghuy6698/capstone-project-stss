package doan2020.SportTournamentSupportSystem.model.Struct;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Node<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer degree;
	private E data;

	private Node<E> left;
	private Node<E> right;

	@JsonBackReference
	private Node<E> nextIfWin = null;

	@JsonBackReference
	private Node<E> nextIfLose = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Node<E> getLeft() {
		return left;
	}

	public void setLeft(Node<E> left) {
		this.left = left;
	}

	public Node<E> getRight() {
		return right;
	}

	public void setRight(Node<E> right) {
		this.right = right;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public Node<E> getNextIfWin() {
		return nextIfWin;
	}

	public void setNextIfWin(Node<E> nextIfWin) {
		this.nextIfWin = nextIfWin;
	}

	public Node<E> getNextIfLose() {
		return nextIfLose;
	}

	public void setNextIfLose(Node<E> nextIfLose) {
		this.nextIfLose = nextIfLose;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", degree=" + degree + ", nextIfWin=" + nextIfWin +"]";
	}

}
