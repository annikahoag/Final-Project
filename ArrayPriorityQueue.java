import java.io.*;

public class ArrayPriorityQueue {
	Event[] A;
	int n;

	public ArrayPriorityQueue(int capacity) {
		A = new Event[capacity];
		n = 0;
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public Event getMin() {
		return A[1];
	}

	public void insert(Event k) {
		A[n+1] = k;
		n++;
		int cIndex = n;
		int pIndex = parentIndex(n);
		//while child is not root and child is smaller than parent
		//smaller is found using compareTo method in Events()
		while (pIndex >= 1 && A[cIndex].compareTo(A[pIndex]) < 0) {
			swap(cIndex,pIndex);
			cIndex = pIndex;
			pIndex = parentIndex(cIndex);
		}
	}

	public Event extractMin() {
		swap(1,n);
		n--;
		int pIndex = 1;
		int lCIndex;
		int rCIndex;
		while (hasSmallerChild(pIndex)) {
			rCIndex = rightChildIndex(pIndex);
			lCIndex = leftChildIndex(pIndex);
			//No right child, then swap parent with left child
			if (rCIndex > n) {
				swap(pIndex, lCIndex);
				pIndex = lCIndex;
			}
			else { //Two children, swap with the smaller child
				if (A[lCIndex].compareTo(A[rCIndex]) < 0 ){
					swap(pIndex,lCIndex);
					pIndex = lCIndex;
				}
				else {
					swap(pIndex,rCIndex);
					pIndex = rCIndex;
				}
			}
		}
		return A[n+1];
	}

	private int parentIndex(int cIndex) {
		return cIndex/2;
	}

	private int leftChildIndex(int pIndex) {
		return pIndex * 2;
	}

	private int rightChildIndex(int pIndex) {
		return (pIndex * 2) + 1;
	}

	private void swap(int pIndex, int cIndex) {
		Event temp = A[cIndex];
		A[cIndex] = A[pIndex];
		A[pIndex] = temp;
	}

	private boolean hasSmallerChild(int pIndex) {
		int rCIndex = rightChildIndex(pIndex);
		int lCIndex = leftChildIndex(pIndex);
		if (rCIndex <= n && A[rCIndex].compareTo(A[pIndex]) < 0)
			return true;
		else if (lCIndex <= n && A[lCIndex].compareTo(A[pIndex]) < 0)
			return true;
		else
			return false;
	}

	
	public String getElement(int i){
		return A[i].toString();
	}

	public String toString(){
		// if (isEmpty()){
			String s = "";
			for(int i=1; i<n; i++){
				s = s + " " + this.getElement(i);
			}
			return s;
		// }else{
		// 	return null;
		// }
	}

	public void display(){
		for (int i=1; i<n; i++){
			System.out.print(A[i].toString() + ", ");
		}
	}

	// public boolean 


}