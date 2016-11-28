package election;

/**
 * Officer.java
 * The Officer Class
 *
 * @author islam
 */
public class Officer extends Thread {

    /** The Constant maximum idle time in milli seconds. */
    private static final int MAX_TIME = 3 * 1000;
    
    /** The Constant minimum idle time in milli seconds. */
    private static final int MIN_TIME = (int) (0.25 * 1000);
    
    /** The buffer. */
    private Buffer buffer;
    
    /** The officer id. */
    private int officerID;

    /**
     * Instantiates a new officer.
     *
     * @param buffer the buffer
     * @param officerID the officer id
     */
    public Officer(Buffer buffer, int officerID) {
        super();
        this.buffer = buffer;
        this.officerID = officerID;
    }

    /**
     * Run method.
     */
    @Override
    public void run() {

        boolean exit = false;
        while (!exit) {
            if (Election.votersCounter <= Election.votes) {

                try {
                    Thread.sleep(MIN_TIME + (int) (Math.random() * ((MAX_TIME - MIN_TIME) + 1)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                buffer.acquire();

                if (!buffer.isEmpty()) {
                    Vote vote = buffer.removeItem();
                    if (vote != null) {
                        System.out.printf("%-8s %-3s %-9s %-3s %-7s %-3s%n",
                                "Officer:", officerID,
                                "Candidate:", vote.getCandidateId(),
                                "Voter:", vote.getVoterID());
                        Election.addTally(vote.getCandidateId());
                    }
                }

                buffer.release();
            } else {
                exit = true;
            }
        }
    }
}
