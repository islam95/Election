package election;

/**
 * Booth.java
 * The Booth Class
 *
 * @author islam
 */
public class Booth extends Thread {

    /** The Constant maximum idle time in milli seconds. */
    private static final int MAX_TIME = 3 * 1000;
    
    /** The Constant minimum idle time in milli seconds. */
    private static final int MIN_TIME = (int) (0.25 * 1000);
    
    /** The booth id. */
    private int boothID;
    
    /** The buffer. */
    private Buffer buffer;

    /**
     * Constructor instantiates a new booth.
     *
     * @param buffer the buffer
     * @param boothID the booth id
     */
    public Booth(Buffer buffer, int boothID) {
        super();
        this.boothID = boothID;
        this.buffer = buffer;
    }

    /*
     * The run method.
     */
    @Override
    public void run() {
        // Create a vote
        boolean exit = false;
        while (!exit) {
            try {
                Thread.sleep(MIN_TIME + (int) (Math.random() * ((MAX_TIME - MIN_TIME) + 1)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            buffer.acquire();

            if (!buffer.isFull()) {
                int voterID = Election.getNextVoterID();
                if (voterID <= Election.votes) {
                    Vote vote = new Vote(voterID, Election.selectRandomCandidate(), boothID);

                    buffer.insertItem(vote);
                    System.out.printf("%-8s %-3s %-9s %-3s %-7s %-3s%n",
                            "Voter:", vote.getVoterID(),
                            "Candidate:", vote.getCandidateId(),
                            "Booth:", boothID);
                } else {
                    exit = true;
                }
            }
            buffer.release();
        }
    }
}
