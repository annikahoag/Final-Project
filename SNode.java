/**
* A Node class for Singly Linked List
* Storing String elements representing the user's friends' emails
*/

public class SNode {
	private String element;	//The String element stored at the node
	private SNode next;		//Reference/Pointer to the next node

	//Constructor
	public SNode(String e, SNode n) {
		element = e;
		next = n;
	}

	public String getElement() {
		return element;
	}

	public SNode getNext() {
		return next;
	}

	public void setElement(String newElement) {
		element = newElement;
	}

	public void setNext(SNode newNext) {
		next = newNext;
	}

	//Returns the string representation of the SNode
	public String toString() {
		return "(" + element + ")";	
	}

	// public static void main(String[] args) {
	// 	SNode n1 = new SNode(12, null);
	// 	SNode n2 = new SNode(14, n1);
	// 	SNode n3 = new SNode(20, n2);
	// 	System.out.println(n3);

	// }
}