package doan2020.SportTournamentSupportSystem.model.Struct;

import java.io.Serializable;
import java.util.ArrayList;

public class DoubleBTree<E> extends BTree<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	public DoubleBTree() {
		super();
	}

	public DoubleBTree(Node<E> root) {
		super(root);
	}

	public Node<E> getByIdAndDegree(int index, int degree) {

		if (index < 1) {
			return null;
		}

		if (degree < 1) {
			return null;
		}

		if (index == 1 && degree == 1) {
			return this.root;
		}

		Node<E> parent;
		if (degree % 2 == 0) {
			parent = getByIdAndDegree(index, degree - 1);
			if (parent == null)
				return null;
			return parent.getRight();
		} else {
			parent = getByIdAndDegree(index / 2, degree - 1);
			if (parent == null)
				return null;

			if (index % 2 == 0) {
				return parent.getLeft();
			} else {
				return parent.getRight();
			}
		}
	}

	@Override
	public String toString() {
		return "{" + this.toString(this.root) + "}";
	}

	public String toString(Node<E> node) {
		if (node == null) {
			return "null";
		}

		String s = "id: " + node.getId() + ",\n";
		s += "data: {" + node.getData().toString() + "},\n";
		s += "left: {" + this.toString(node.getLeft()) + "}\n";
		s += "right: {" + this.toString(node.getRight()) + "}\n";

		return s;
	}

	@Override
	public ArrayList<E> toArrayList() {
		return toArrayList(this.root, 1, 1);
	}

	private ArrayList<E> toArrayList(Node<E> node, int id, int degree) {
		ArrayList<E> list = new ArrayList<>();

		if (node == null || node.getDegree() != degree - 1)
			return list;

		if (degree % 2 == 0) {
			list.addAll(toArrayList(node.getLeft(), id * 2, degree + 1));
			list.addAll(toArrayList(node.getRight(), id * 2 + 1, degree + 1));
		} else {
			list.addAll(toArrayList(node.getRight(), id, degree + 1));
		}

		list.add(node.getData());

		return list;
	}

	@Override
	public Node<E> findNodeByData(E data) {
		return findNodeByData(this.getRoot(), 1, 1, data);
	}

	protected Node<E> findNodeByData(Node<E> node, int id, int degree, E data) {

		if (node == null || node.getDegree() != degree - 1) {
			return null;
		}

		if (node.getData().equals(data)) {
			return node;
		}

		Node<E> findLeft;
		Node<E> findRight;

		if (degree % 2 == 0) {
			findLeft = findNodeByData(node.getLeft(), id * 2, degree + 1, data);
			findRight = findNodeByData(node.getRight(), id * 2 + 1, degree + 1, data);
		} else {
			findLeft = null;
			findRight = findNodeByData(node.getRight(), id, degree + 1, data);
		}

		if (findLeft != null)
			return findLeft;

		if (findRight != null)
			return findRight;

		return null;
	}

}
