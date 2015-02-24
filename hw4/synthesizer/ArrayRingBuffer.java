// Make sure to make this class a part of the synthesizer package
package synthesizer;

public class ArrayRingBuffer extends AbstractBoundedQueue {
    /* Index for the next dequeue or peek. */
    private int first;           
    /* Index for the next enqueue. */
    private int last;             
    /* Array for storing the buffer data. */
    private double[] rb;
    /** Create a new ArrayRingBuffer with the given capacity. */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0. 
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue.
        this.rb = new double[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
    }

    /** Adds x to the end of the ring buffer. If there is no room, then
    * throw new RuntimeException("Ring buffer overflow") 
    */
    public void enqueue(double x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        // is there room?
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        else {
            if (this.last == this.capacity) {
                this.rb[0] = x;
                this.last = 1;
            }
            else {
                this.rb[last] = x;
                this.last += 1;
            }
            this.fillCount += 1;
        }
    }

    /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
    * throw new RuntimeException("Ring buffer underflow");
    */
    public double dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first.
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        else {
            double deleted;
            if (this.first == this.capacity) {
                deleted = this.rb[0];
                this.rb[0] = 0;
                this.first = 1;
            }
            else {
                deleted = this.rb[this.first];
                this.rb[this.first] = 0;
                this.first += 1;
            }
            this.fillCount -= 1;
            return deleted;
        }
    }

    /** Return oldest item, but don't remove it. */
    public double peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        else {
            if (this.first == this.capacity) {
                return this.rb[0];
            }
            else {
                return this.rb[this.first];
            }  
        }
    }



}
