import java.io.*;

public class ArrayPriorityQueue {
	int[] A;
	int n;

	public ArrayPriorityQueue(int capacity) {
		A = new int[capacity];
		n = 0;
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int getMin() {
		return A[1];
	}

	public void insert(int k) {
		A[n+1] = k;
		n++;
		int cIndex = n;
		int pIndex = parentIndex(n);
		//while child is not root and child is smaller than parent
		while (pIndex >= 1 && A[cIndex] < A[pIndex]) {
			swap(cIndex,pIndex);
			cIndex = pIndex;
			pIndex = parentIndex(cIndex);
		}
	}

	public int extractMin() {
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
				if (A[lCIndex] < A[rCIndex]) {
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
		int temp = A[cIndex];
		A[cIndex] = A[pIndex];
		A[pIndex] = temp;
	}

	private boolean hasSmallerChild(int pIndex) {
		int rCIndex = rightChildIndex(pIndex);
		int lCIndex = leftChildIndex(pIndex);
		if (rCIndex <= n && A[rCIndex] < A[pIndex])
			return true;
		else if (lCIndex <= n && A[lCIndex] < A[pIndex])
			return true;
		else
			return false;
	}
		
}