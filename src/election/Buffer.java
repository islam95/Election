package election;

/**
 * Buffer.java
 * The Class Buffer.
 *
 * @author islam
 */
public class Buffer {

    /** The capacity. */
    private int capacity;
    
    /** The store. */
    private Vote store[];
    
    /** The start index. */
    private int startIndex = 0;
    
    /** The finish index. */
    private int finishIndex = 0;
    
    /** The semaphore object. */
    private Semaphore sem = new Semaphore(1);
    
    /** The items. */
    private int items = 0;
    
    /**
     * Instantiates a new buffer. 
     * Creates a bounded buffer with capacity cap
     *
     * @param capacity the capacity
     */
    public Buffer(int capacity) {
        this.capacity = capacity;
        this.store = new Vote[capacity];
        sem = new Semaphore(capacity);
    }

    /**
     * Insert item. 
     * Places the vote in the buffer; if no space is left in the
     * buffer, block until a space becomes available
     *
     * @param vote the vote
     */
    public void insertItem(Vote vote) {
        store[startIndex] = vote;
        startIndex = (startIndex + 1) % capacity;
        items++;
        // occupiedCells.up();
    }

    /**
     * Removes the item. 
     * Take an item from the buffer and return it; if there is
     * no item in the buffer, block until one becomes available
     *
     * @return the vote
     */
    public Vote removeItem() {

        Vote value = null;
        // occupiedCells.down();
        value = store[finishIndex];
        finishIndex = (finishIndex + 1) % capacity;
        items--;
        // emptyCells.up();
        return value;
    }

    /**
     * Acquire.
     */
    public void acquire() {
        sem.down();
    }

    /**
     * Release.
     */
    public void release() {
        sem.up();
    }

    /**
     * Checks if it is empty.
     *
     * @return true, if it is empty
     */
    public boolean isEmpty() {
        return (items == 0);
    }

    /**
     * Checks if it is full.
     *
     * @return true, if it is full
     */
    public boolean isFull() {
        return (items == capacity);
    }
}
