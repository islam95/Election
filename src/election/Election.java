package election;

/**
 * Election.java 
 * Class Election has the main method which executes the program
 * simulating an election.
 *
 * @author islam
 */
public class Election {

    /** The candidate running tally. */
    public static int[] candidateRunningTally;
    
    /** The buffer. */
    private static Buffer buffer;
    
    /** The voters counter. */
    public static int votersCounter = 0;
    
    /** The candidates. */
    private static int candidates = 3;
    
    /** The votes. */
    public static int votes = 10;
    
    /** The booths. */
    private static int booths = 6;
    
    /** The officers. */
    private static int officers = 5;
    
    /** The officer threads. */
    private static Officer[] officerThreads;
    
    /** The booth threads. */
    private static Booth[] boothThreads;

    /**
     * The Main method.
     * Arguments can be inserted in Project properties/Run category.
     * Or you can always run the command line using jar file.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // 1) Candidates, 2) Votes to be placed, 3) Booths available and 
        // 4) Officers at hand
        if (args.length < 4) {
            System.out.println("Program parameters missing. 1) Candidates, "
                    + "2) Votes, 3) Booths, 4) Officers.");
            return;
        }
        System.out.print("Arguments: ");
        for(int i = 0; i < args.length; i++){
            System.out.print(args[i] + " ");
        }
        
        candidates = Integer.parseInt(args[0]);
        votes = Integer.parseInt(args[1]);
        booths = Integer.parseInt(args[2]);
        officers = Integer.parseInt(args[3]);
        if (booths < officers) {
            System.out.println("\nThe number of booths must be greater than the "
                    + "number of officers.");
            return;
        }
        System.out.println();
        
        initialise();
        waitForThreadCompletion();
        displayTally();
    }
    
    /**
     * Initialise.
     */
    public static void initialise(){
        
        candidateRunningTally = new int[candidates];
        buffer = new Buffer(officers);
        officerThreads = new Officer[officers];
        
        for(int i = 0; i < officers; i++){
            Officer officerThread = new Officer(buffer, i + 1);
            officerThreads[i] = officerThread;
            officerThread.start();
        }
        
        boothThreads = new Booth[booths];
        
        for(int i = 0; i < booths; i++){
            new Booth(buffer, i + 1).start();
            Booth boothThread = new Booth(buffer, i + 1);
            boothThreads[i] = boothThread;
            boothThread.start();
        }
    }
    
    /**
     * Wait for thread completion.
     */
    public static void waitForThreadCompletion(){
        try{
            for(Booth boothThread : boothThreads){
                boothThread.join();
            }
            for(Officer officerThread : officerThreads){
                officerThread.join();
            }
            
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    
    }
    
    /**
     * Display tally.
     */
    public static void displayTally(){
        
        int maxVotes = Integer.MIN_VALUE;
        int winingIndex =- 1;
        
        for(int i = 0; i < candidates; i++){
            if(candidateRunningTally[i] >= maxVotes){
                winingIndex = i;
                maxVotes = candidateRunningTally[i];
            }
        }
        // Output of results
        System.out.println();
        for(int i = 0; i < candidates; i++){
            System.out.printf("%-10s %-3s %-5s %-10s%n", "Candidate:", i, "Votes =", 
                    candidateRunningTally[i] + (i == winingIndex ? " wins the election." : ""));
        }
    }
    
    
    /**
     * Select random candidate
     * 
     * @return the integer
     */
    public static int selectRandomCandidate(){
        // Get a random andidate
        int candidate;
        candidate = (int)(Math.random() * (candidates));
        return candidate;
    }
    
    /**
     * Adds the tally.
     * 
     * @param candidateIndex the candidate index
     */
    public static void addTally(int candidateIndex){
        synchronized(lock){
        candidateRunningTally[candidateIndex] += 1;
        }
    }
    
    /**
     * The constant lock.
     */
    public static final Object lock = new Object();
    
    /**
     * Gets the next voter id.
     * 
     * @return the next voter id 
     */
    public static int getNextVoterID(){
        synchronized(lock){
            return ++Election.votersCounter;
        }
    }
    
}
