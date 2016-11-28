package election;

/**
 * Vote.java
 * The Class Vote.
 *
 * @author islam
 */
public class Vote {

    /** The voter id. */
    private int voterID;
    
    /** The candidate id. */
    private int candidateID;
    
    /** The booth id. */
    private int boothID;

    /**
     * Instantiates a new vote.
     *
     * @param voterID the voter id
     * @param candidateID the candidate id
     * @param boothID the booth id
     */
    public Vote(int voterID, int candidateID, int boothID) {
        super();
        this.voterID = voterID;
        this.candidateID = candidateID;
        this.boothID = boothID;
    }

    /**
     * Gets the voter id.
     *
     * @return the voterID
     */
    public int getVoterID() {
        return voterID;
    }

    /**
     * Gets the candidate id.
     *
     * @return the candidateID
     */
    public int getCandidateId() {
        return candidateID;
    }

    /**
     * Gets the booth id.
     *
     * @return the boothID
     */
    public int getBoothID() {
        return boothID;
    }
}
