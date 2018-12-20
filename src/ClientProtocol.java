//---------------------------------------------------------------
//         Project
//         ClientProtocol.java
//         Protocol for responding to Knock! Knock! jokes from KnockServer.
//---------------------------------------------------------------

public class ClientProtocol {

    private static final int WAITING = 0;
    private static final int SENTWHOTHERE = 1;
    private static final int SENTCLUEWHO = 2;
    private static final int YESNO = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private final String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    private final String[] answers = {"Turnip the heat, it's cold in here!",
        "I didn't know you could yodel!",
        "Bless you!",
        "Is there an owl in here?",
        "Is there an echo in here?"};

    public String processInput(String theInput) throws InterruptedException {
        String theOutput = null;
        if(theInput == null || theInput.trim().length() == 0) {
            state = -1;
        }
        else if (state == WAITING) {//StringUtils.isEmtpy()  
            if (theInput.equalsIgnoreCase("Knock! Knock!")) {
                theOutput = "Who's there?";
                state = SENTWHOTHERE;
            } else {
                theOutput = "You're supposed to say \"Knock Knock!\"! "
                        + "Try again.";
            }
        } else if (state == SENTWHOTHERE) {
            if (theInput.equalsIgnoreCase(clues[currentJoke])) {
                theOutput = clues[currentJoke] + " who?";
                state = SENTCLUEWHO;
            } else {
                theOutput = "You're supposed to say \""
                        + clues[currentJoke]
                        + "! Try again.";
            }
        } else if (state == SENTCLUEWHO) {
            if (theInput.equalsIgnoreCase(answers[currentJoke] + " Want another? (y/n)")) {
                theOutput = "y";
                state = WAITING;
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
            } else {
                theOutput = "You're supposed to say \""
                        + answers[currentJoke]
                        + "! Try again.";
                state = -1;
            }
        }
        return theOutput;
    }
}
