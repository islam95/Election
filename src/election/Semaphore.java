package election;

/**
 *
 * @author islam
 */
/**
 * ****************************************************************************
 * Semaphore.java
 *
 * Provides a basic semaphore class. Before attempting to acquire a shared
 * resource, first call down. If the resource is busy the semaphore forces a
 * wait. Once the resource has been finished with, call up to notify other
 * threads that might be waiting on the resource.
 *
 * ****************************************************************************
 */
public class Semaphore {

    // *************************************************************
    // Class properties.
    // Allow for both counting or mutex semaphores.
    private int count;

    // *************************************************************
    // Constructor
    public Semaphore(int n) {
        count = n;
    }

    // *************************************************************
    // Public class methods.
    // Only the standard up and down operators are allowed.
    public synchronized void down() {

        while (count == 0) {

            try {
                wait(); // Blocking call.
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        count--;
    }

    public synchronized void up() {
        count++;
        notify();
    }
}
