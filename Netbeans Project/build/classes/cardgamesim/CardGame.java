package cardgamesim;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
public class CardGame
{
    
    /**
     * The winning player's number. Initially -1 as no player will have 
     * won.
     */
    private volatile static int winnerNo = -1;
    
    /**
     * Array to store all CardDecks for easy access by index.
     */
    private static CardDeck[] decks;
    
    /**
     * Array to store all player threads for easy access by index.
     */
    private static Player[] players;
    
    /**
     * Enables keyboard input in main menu.
     */
    private static final BufferedReader keyboardInput = 
                 new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * The initialPack. To be filled when checked for validity.
     */
    private static CardDeck initialPack;
    
    /**
     * Indicates whether the simulation application should be exited
     * when current round is over.
     */
    public static boolean applicationCompleted = false;
    
    /**
     * Main Method of Application, includes user menu and thread 
     * management/creation.
     * 
     * @param args No arguments implemented
     * @throws IOException Due to KeyboardInput
     * @throws InterruptedException Due to Thread Management
     * @return Nothing.
     */
    public static void main(String[] args) throws IOException, 
                                                InterruptedException
    {
        System.out.println("Welcome to the best Card Game Simulation" 
                                                     + " App ever!");
        
        do
		{
            int numberOfPlayers = 0;
            int numberOfCardsInHand = 0;
            int strategy = 0;
            String filename = "";
            winnerNo = -1;

            // Encapsulate menu in do..while loop to keep asking until 
            // valid input has been entered.
            do
            {
                try
                {
                    System.out.println("Please enter the number of "
                                         + "players in the game..");
                    numberOfPlayers = Integer
                                  .parseInt(keyboardInput.readLine());
                    if(numberOfPlayers <= 0)
                    {
                        System.out.println("You did not enter an "
                                           + "integer larger than 0.");
                    }
                } 
                catch(IOException e)
                {
                    System.out.println("Something bad happened.. Try "
                                                          + "again!");

                } catch(NumberFormatException e)
                {
                    System.out.println("Please enter a valid "
                                                + "integer.");
                } 
            } while(numberOfPlayers <= 0);

            // Create arrays of CardDecks and Players as soon as we 
            // know the numbers.
            players = new Player[numberOfPlayers];
            decks = new CardDeck[numberOfPlayers];

            do
            {
                try
                {
                    System.out.println("Please enter the number of "
                                    + "cards in the players' hand..");
                    numberOfCardsInHand = Integer
                                   .parseInt(keyboardInput.readLine());
                    if(numberOfCardsInHand <= 0)
                    {
                        System.out.println("You did not enter an "
                                        + "integer larger than 0.");
                    }
                } 
                catch(IOException e)
                {
                    System.out.println("Something bad happened.. "
                                                  + "Try again!");

                } 
                catch(NumberFormatException e)
                {
                    System.out.println("Please enter a valid "
                                                + "integer.");
                } 
            } while(numberOfCardsInHand <= 0);

            do
            {
                try
                {
                    System.out.println("Please enter the strategy you "
                                     + "would like all players to use "
                                     + "(1 or 2)..");
                    strategy = Integer
                                   .parseInt(keyboardInput.readLine());
                    // Currently only two strategies implemented, so
                    // 1 or 2
                    if(!((strategy == 1) || (strategy == 2)))
                    {
                        System.out.println("Please enter a valid "
                                            + "strategy number.");
                    }
                } 
                catch(IOException e)
                {
                    System.out.println("Something bad happened.. "
                                                  + "Try again!");

                } 
                catch(NumberFormatException e)
                {
                    System.out.println("Please enter a valid "
                                                + "integer.");
                }
            } while(!((strategy == 1) || (strategy == 2)));

            do
            {
                try
                {
                    System.out.println("Please enter the location of "
                                        + "the input deck to use by "
                                        + "the card game "
                                        + "simulation..");
                    filename = keyboardInput.readLine();
                } 
                catch(IOException e)
                {
                    System.out.println("Something bad happened.. "
                                                  + "Try again!");
                }

            } while(!isDeckValid(filename, numberOfCardsInHand, 
                                             numberOfPlayers));

           // Compute and store the number of the deck to the right 
           // to the player as last player will have to refer to 
           // first deck and not n + 1.
            
            int rightDeckNo;

            KeyInputGenerator kG = new KeyInputGenerator();
            KeyInputListenerClass kI2 = new KeyInputListenerClass();
            kG.addKeyInputEventListener(kI2); 

            for(int i = 0; i < numberOfPlayers; i++)
            {

                if (i==numberOfPlayers - 1)
                {
                    rightDeckNo = 1;
                }
                else
                {
                    rightDeckNo = i + 2;
                }
                players[i] = new Player(i + 1, rightDeckNo, strategy, 
                                        numberOfCardsInHand, kI2, kG);
            }
			
            // Start listening to pause requests by user.
            kG.start();

            // Give out cards from initial Pack in round-robin
            // fashion.
            for(int j=0; j<numberOfCardsInHand; j++)
            {
                for(Player player: players)
                {
                    player.addCardToHand(initialPack.popCard());
                }
            }
			
            for(int m = 0; m < numberOfPlayers; m++)
            {
                decks[m] = new CardDeck(m + 1);
            }
			
            // And the fill the decks similarly.
            while(!initialPack.isEmpty())
            {
                // The same number of cards will be in each deck.
                for(CardDeck deck: decks)
                {
                    deck.addCard(initialPack.popCard());
                }
            }

            for(Player player: players)
            {
                player.start();
            }
			
            // Join threads when simulation done and winner set.
            for(Player player: players)
            {
                player.join();
            }

            for(CardDeck deck: decks)
            {
                deck.saveToFile();
            }
            System.out.println("Would you like to run another "
                                + "simulation? (Enter n to quit; "
                                + "y to start a new simulation)");
            // Join the KeyInputGenerator Thread to reset it or exit
            // the application
            kG.join();
    
        }while(!applicationCompleted);
    }

    /**
     * Checks the input deck's validity and add cards to the initial 
     * pack.
     * 
     * @param filename The filename of the deck
     * @param k The number of cards in the hand of the player
     * @param n The number of players in the game
     * @return True/False for validity of exception
     * @throws FileNotFoundException Due to external file being read 
     *                               but not found.
     * @throws IOException 
     */
    private static boolean isDeckValid (String filename, int k, int n) 
                              throws FileNotFoundException, IOException
    {
        initialPack = new CardDeck(0);
       /**
        *  Count for the number of lines/cards in input deck.
        */
        int noOfLines = 0;
        BufferedReader fileContents;
		
        try
        {
        fileContents = new BufferedReader(new FileReader(filename));
        } catch (IOException e)
        {
            System.out.println("IO exception, check if filepath is "
                                                        + "correct.");
            return false;
        }
        
        // For line read from deck file.
        
        String line;
        
	// Read file until EOF or enough lines read (2 * k * n)    
        while(((line = fileContents.readLine()) != null) && 
                                            (noOfLines < (2 * k * n))) 
        {
		   
            // Temporary store for card's face value.
            int cardNumber;
            try 
            {
                cardNumber = Integer.parseInt(line);
                if(!(cardNumber >= 0))
                {
                    System.out.println("A negative integer was found, "
                                        + "check file "
                                        + "contents.");
                    return false;
                }
            } 
            catch(NumberFormatException e)
            {
                System.out.println("Lines couldn't be read as "
                                    + "non-negative integers, "
                                    + "check file contents.");
                return false;
            }
            initialPack.addCard(new Card(cardNumber));    
            noOfLines++;
        }
        
        if(noOfLines == (2 * k * n)) 
        {
            return true;
        }
        
        System.out.println("Not enough data to fill decks; check "
                            + "file contents..");
        return false;
    }
    
    /**
     * Recognises that a player has won the game and saves its details.
     * 
     * @param winnerID The player number of the one who won.
     * @return Nothing.
     */
    public static synchronized void setWinner(int winnerID)
    {
         // To prevent threads that are half-way through doing a turn 
         // to change the already set winning player ID.    
        if(CardGame.winnerNo == -1)
        {
            CardGame.winnerNo = winnerID;
        }
    }
    
    /**
     * Returns the winning player's number
     * 
     * @return -1 if no player has won, otherwise winning players no.
     */
    public static synchronized int getWinner()
    {
        return CardGame.winnerNo;
    }
    
    /**
     * Checks whether the deck is empty or not.
     * 
     * @param deckNo The number of the deck to check for emptiness.
     * @return true or false whether there are cards in the deck.
     */
    public static synchronized boolean isDeckEmpty(int deckNo)
    {
        return decks[deckNo - 1].isEmpty();
    }
    
    /**
     * Removes top card from deck and returns it
     * 
     * @param deckNo Number of Deck to pop card from
     * @return the Card removed from deck.
     */
    public static synchronized Card getTopCard(int deckNo)
    {
        return decks[deckNo - 1].popCard();
    }
    
    /**
     * To put card onto bottom of deck.
     * 
     * @param deckNo The deck to put the card on.
     * @param card The card to be put back.
     * @return Nothing.
     */
    public static synchronized void putCardBack(int deckNo, Card card)
    {
        decks[deckNo - 1].addCard(card);
    }
}

/**
 * Thread to detect user input for pausing and resuming the game.
 * 
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
class KeyInputListenerClass implements KeyInputListener
{
    
    /**
     * Processes the Event and pauses or resumes the threads as 
     * required.
     * 
     * @overrides keyInputOccurance method defined in the interface 
     *            KeyInputListener.
     * @param e The KeyInputEvent to process.
     * @return Nothing.
     */
    @Override
    public void keyInputOccurance(KeyInputEvent e)
    {
        Object o = e.getSource();
        if(o instanceof KeyInputGenerator)
        {
            KeyInputGenerator eventSource = (KeyInputGenerator)o;
            if(eventSource.isPaused())
            {
                System.out.println("Program has been paused. Enter "
                                    + "r to resume.");
            }
            else
            {
                synchronized(this){
                    System.out.println("Program has been resumed.");
                    this.notifyAll();
                }
            }
        }
    }
}

/**
 * Thread to pause the threads.
 * 
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
class KeyInputGenerator extends Thread
{
    /**
     * Store listeners in array for easy access.
     */
    private KeyInputListenerClass[] listeners = 
                                          new KeyInputListenerClass[1];
    
    /**
     * State of game (paused or not).
     */
    private boolean paused = false;
    
    /**
     * Enables user input in regard to pausing or resuming the game.
     */
    private static BufferedReader keyBoardInput = 
                  new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Run method of the KeyInputGenerator which gets executed when the
     * thread is started. Reacts on user input of "p" and "r" to pause
     * or resume the game.
     * 
     * @overrides The inherited run method from Thread as defined by 
     *            the interface Runnable.
     * @return Nothing.
     */
    @Override
    public void run()
    {
        try
        {
            String input = "";
            
            while((CardGame.getWinner() == -1))
            {
                input = keyBoardInput.readLine();
                if((CardGame.getWinner() == -1)) 
                {
                    if((input.toLowerCase().equals("p")) 
                                                    && (!this.paused))
                    {
                        this.paused = true;
                        fireKeyInputEvent(new KeyInputEvent(this));
                    }
                    else if((input.toLowerCase().equals("r")) 
                                                    && (this.paused))
                    {
                        this.paused = false;
                        fireKeyInputEvent(new KeyInputEvent(this));
                    }
                }
            }
          
            if(input.toLowerCase().equals("n"))
            {
                CardGame.applicationCompleted = true;
            }
            else{
                boolean inputValid = false;
                while(!inputValid){
                    System.out.println("Please enter y or n.");
                    input = keyBoardInput.readLine().toLowerCase();
                    switch(input) {
                        case "n":
                            CardGame.applicationCompleted = true;
                        case "y":
                            inputValid = true;
                            break;
                    }
                }
            }
            
        }   
        catch(IOException e)
        {
            System.out.println("Something bad happened.. Try again!");
        }
    }
    
    /**
     * Adding a new KeyInputEventListener to listeners.
     * 
     * @param t The KeyInputListener to add.
     * @return Nothing.
     */
    public void addKeyInputEventListener(KeyInputListenerClass t)
    {
        this.listeners[0] = t;
    }
    
    
    /**
     * Remove the KeyInputEventListener.
     * 
     * @return Nothing.
     */
    public void removeKeyInputEventListener()
    {
        listeners[0] = null;
    }
    
    /**
     * Processes KeyInputEvent on Listener.
     * 
     * @param evt Event to process.
     * @return Nothing.
     */
    void fireKeyInputEvent(KeyInputEvent evt)
    {
        this.listeners[0].keyInputOccurance(evt); 
    }
    
    /**
     * Checks if the user requested to pause the game.
     * 
     * @return True/False of state.
     */
    public boolean isPaused()
    {
        return this.paused;
    }
}