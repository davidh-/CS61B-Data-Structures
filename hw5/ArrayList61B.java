import java.util.AbstractList;
/** 
 *  @author David Dominguez Hooper
 */

public class ArrayList61B<E> extends AbstractList<E>{
	private E[] arrayList;
	private int size;
	private int numberOfElements;

	/* This constructor should initialize the size of the internal 
	 * array to be initialCapacity and throw an IllegalArgumentException
	 * if initialCapacity is less than 1.
	 */
	public ArrayList61B(int initialCapacity) { 
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		else {
			this.size = initialCapacity;
			this.arrayList = (E[]) new Object[this.size];
			this.numberOfElements = 0;
		}
	}
	/* This constructor should initialize the size of 
	 * the internal array to be 1. 
	 */
	public ArrayList61B() {
		this.size = 1;
		this.arrayList = (E[]) new Object[this.size];
	}
	/* This method should return the ith element in the list.
	 * This method should throw an IllegalArgumentException if 
	 * i is less than 0 or greater than or equal to the number 
	 * of elements in the list. 
	 */
	@Override
	public E get(int i) {
		if (i < 0 || i >= this.numberOfElements) {
			throw new IllegalArgumentException();
		}
		else {
			return this.arrayList[i];
		}
	}
	/* This method should add item to the end of the list, resizing 
	 * the internal array if necessary. This method should always return 
	 * true (if you are curious about this, read the api for AbstractList). 
	 */
	@Override
	public boolean add(E item) {
		if (this.numberOfElements == this.size) {
			E[] newArrayList = (E[]) new Object[this.size * 2];
			int i = 0;
			while (i < this.size) {
				newArrayList[i] = this.arrayList[i];
				i += 1;
			}
			this.arrayList = newArrayList;
			this.size = this.size * 2;
		}
		this.arrayList[this.numberOfElements] = item;
		this.numberOfElements += 1;
		return true;
	}
	/* This method should return the number of elements in the list. 
	 * Note that this is not necessarily the same as the number of elements 
	 * in the internal array. 
	 */
	@Override
	public int size() {
		return this.numberOfElements;
	}
}