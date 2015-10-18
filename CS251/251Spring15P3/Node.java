public class Node {
	private String info;
	private String name;//Would store the names (Left or Right)
	private Node leftChild = null;
	private Node rightChild = null;
	
	public Node() {
		this("");
	}
	
	public Node(String info) {
		this.info = info;
	}
	
	public void setinfo(String info) {
		this.info = info;
	}
	public void setname(String info) {
		this.name = info;
	}

	public void setLeftChild(Node n) {
		leftChild = n;
	}
	
	public void setRightChild(Node n) {
		rightChild = n;
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	
	public Node getRightChild() {
		return rightChild;
	}
	
	public String getinfo() {
		return info;
	}
	
	public String getname() { //Returns the name L or R and so on
		return name;
	}
	
	public boolean isLeaf() {
		return rightChild == null && leftChild == null;
	}	
}