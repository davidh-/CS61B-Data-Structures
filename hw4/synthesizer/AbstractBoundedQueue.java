package synthesizer;

public abstract class AbstractBoundedQueue implements BoundedQueue {
	protected int fillCount;
	protected int capacity;

	public int capacity() {
		return capacity;
	}
	public int fillCount() {
		return fillCount;
	}
	public boolean isEmpty() {
		return capacity == 0 ? true : false;
	}
	public boolean isFull() {
		return capacity == fillCount ? true : false;
	}
	public abstract double peek();
	public abstract double dequeue();
	public abstract void enqueue(double x);
}