
import java.util.Arrays;
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
		head = new DNode(val); 
	}

	public DNode getFront() {
		return head;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		/* your code here */
		DNode pointer = this.head;
		while (pointer.next != null) {
			pointer = pointer.next;
		}
		return pointer;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		/* your code here */
		DNode pointer = this.head;
		while (pointer.prev != null) {
			pointer = pointer.prev;
		}
		DNode front = new DNode(null, d, pointer);
		pointer.prev = front;
		head = front;
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		/* your code here */
		DNode pointer = this.getBack();
		DNode back = new DNode(pointer, d, null);
		pointer.next = back;
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		/* your code here */
		if (this.head != null){
			if (this.head.next == null) {
				DNode delete = this.head;
				this.head = null;
				return delete;
			}
			else {
				DNode delete = this.getBack();
				delete.prev.next = null;
				return delete;
			}
		}
		else 
			return this.head;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		double[] arrayValues = new double[this.getSize()];
		DNode pointer = this.head;
		int i = 0;
		while (pointer.next != null) {
			arrayValues[i] = pointer.val;
			pointer = pointer.next;
			i += 1;
		}
		arrayValues[i] = pointer.val;
		return "<" + Arrays.toString(arrayValues) + ">";
	}
	private int getSize() {
		int size = 1;
		DNode pointer = this.head;
		while (pointer.next != null) {
			pointer = pointer.next;
			size += 1;
		}
		return size;
	}
	public void deleteByIndex(int i) {
		DNode pointer = this.head;
		int k = 0;
		while (k < i) {
			pointer = pointer.prev;
			k += 1;
		}
		pointer.prev.next = pointer.next;
		pointer.next.prev = pointer.prev;
	}


	public static class DNode {
		public DNode prev;
		public DNode next;
		public double val;
		
		private DNode(double val) {
			this(null, val, null);
		}
		
		private DNode(DNode prev, double val, DNode next) {
			this.prev = prev;
			this.val = val;
			this.next =next;
		}
	}
	
}
