package cardgamesim;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Player playing the simulated game.
 * 
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
public class Player extends Thread {
    /**
     * The player's preferred card's face value.
     */
    private int cardPreference;
    
    /**
     * The number of the player.
     */
    private int playerNo;
    
    /**
     * An ArrayList to store the cards in the player's hand.
     */
    private ArrayList<Card> hand;
    
    /**
     * The number of the deck to the player's right hand side.
     */
    private int rightDeckNo;
    
    /**
     * The strategy the player is employing.
     */
    private int strategy; //not static for further expansion
    
    /**
     * The name of the player's output file.
     */
    private String fileName;
    
    /**
     * The listenerThread for pausing/resuming the game.
     */
    private final KeyInputListenerClass listenerThread;
    
    /**
     * The generatorThread for pausing/resuming the game.
     */
    private final KeyInputGenerator generatorThread;

    /**
     * Creates a new player thread.
     * 
     * @param playerNo The number of the player
     * @param rightDeckNo The number of the deck to the right of the 
     *        player
     * @param strategy The ID of the strategy employed by the player
     */
    public Player(int playerNo, int rightDeckNo, int strategy, int k, 
                        KeyInputListenerClass kL, KeyInputGenerator kG)
    {
        this.playerNo = playerNo;
        // So doesn't have to be computed. cpu vs memory trade off.
        this.rightDeckNo = rightDeckNo; 
        this.strategy = strategy;
        this.cardPreference = playerNo;
        this.hand = new ArrayList<>(k);
        this.listenerThread = kL;
        this.generatorThread = kG;
        this.fileName = "player" + this.playerNo + "_output.txt";
        try 
        {
            FileWriter fw = new FileWriter(fileName);
            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Something bad happened.. Try again!");
        }
    }
    
    /**
     * Run method of the thread to control the player's actions.
     * 
     * @overrides The inherited run method from Thread as defined
     *            by the interface Runnable.
     * @return Nothing.
     */
    @Override
    public void run()
	{
        
        // Write initial hand to file.
        appendToPlayerLog("player " + this.playerNo + " initial hand " 
                                            + this.getHandAsString());
        
        // Run as long as not won.
        this.hasWon();
        String temp;
        while(CardGame.getWinner() == -1)
        {
            if(this.generatorThread.isPaused())
            {
                synchronized(this.listenerThread) {
                    while (this.generatorThread.isPaused())
					{
                        try 
                        {
                            this.listenerThread.wait();
                        } 
                        catch (InterruptedException ex) 
                        {
                            System.out.println("Thread has been " 
                                                + "interrupted");
                        }
                    }       
                }
            }
            
            else if(!(CardGame.isDeckEmpty(this.playerNo)))
            {
                Card cardDrawn = CardGame.getTopCard(this.playerNo);
                
                temp = "player " + this.playerNo + " draws a " 
                        + cardDrawn.getNumber() + " from deck " 
                        + this.playerNo;
                
                System.out.println(temp);
                appendToPlayerLog(temp);
                this.hand.add(cardDrawn);
                
                // Decide which card to remove.
                Card cardDiscard = this.getCardToBeDiscarded();
                
                temp = "player " + this.playerNo + " discards a " 
                        + cardDiscard.getNumber() + " to deck " 
                        + this.rightDeckNo;
                
                System.out.println(temp);
                appendToPlayerLog(temp);
                CardGame.putCardBack(this.rightDeckNo, cardDiscard);
                
                temp = "player " + this.playerNo + " current hand : " 
                        + this.getHandAsString();
                
                System.out.println(temp);
                appendToPlayerLog(temp);
                
                this.hasWon();   
            }
            
        }
        // Eventually, put out info to user that player has won.
        
        // The number of the winning player.
        int winnerNo = CardGame.getWinner(); 
        
        if (winnerNo == this.playerNo) 
        {
            temp = "player " + this.playerNo + " wins";
        }
        else 
        {
            temp = "player " + winnerNo + " has informed player " 
                    + this.playerNo + " that player " + winnerNo 
                    + " has won";
        }
        
        System.out.println(temp);
        appendToPlayerLog(temp);

        appendToPlayerLog("player " + this.playerNo + " exits");
        appendToPlayerLog("player " + this.playerNo + " final hand : " 
                                            + this.getHandAsString());
    }
    
    /**
     * Adds card to hand of player for main game when giving out cards
     * Can't be done in thread creation as round robin required.
     * 
     * @param card card to add
     * @return Nothing.
     */
    public void addCardToHand(Card card)
    {
        this.hand.add(card);
    }
    
    /**
     * Checks whether the player has won with its current sets of cards 
     * on the hand and calls the method to finish the game, if so.
     * 
     * @return Nothing.
     */
    private void hasWon()
    {
        int numberOfEqualCards = 1;
        int previousCardValue = this.hand.get(0).getNumber();
        
        // Iterate over remaining cards on hand until next
        // card doesn't match previous card
        for (int i = 1; i < this.hand.size(); i++)
        {
            if (previousCardValue == this.hand.get(i).getNumber())
            {
                numberOfEqualCards++;
            }
            else
            {
                break;
            }
        }
        if (numberOfEqualCards == this.hand.size())
        {
            CardGame.setWinner(this.playerNo);
        }
    }
    
    /**
     * Private method to return all the cards on the player's hand as 
     * a String.
     */
    private String getHandAsString()
    {
        String handString = "";
        
        for(int i = 0; i < this.hand.size(); i++)
        {
            handString += this.hand.get(i).getNumber() + " ";
        }
        
        return handString;
    }
    
    /**
     * Determines which card to get rid of, depending on the strategy.
     * 
     * @return Card to be removed from hand and put onto deck on the 
     *         right to the player.
     */
    private Card getCardToBeDiscarded()
    {
        Card cardToBeDiscarded = null;
        switch (this.strategy)
        {
            case 2:
                
                // HashMap to store number of different and equal cards
                // Key = face value; Value = amount of cards
                HashMap<Integer, Integer> cardMap = new HashMap<>();
               
                for (Card c : this.hand)
                {
                    int key = c.getNumber();
                    
                    if(!(cardMap.containsKey(key)))
                    {
                        cardMap.put(new Integer(key), new Integer(1));
                    }
                    
                    else
                    {
                        cardMap.put(new Integer(key), 
                                    new Integer(cardMap.get(key) + 1));
                    }
                }
                
                // Find out which key has the biggest value
                // there may be two with the highest, but we take the 
                // first one because this may still lead to quicker 
                // finishes.
                
                // Initialise variables to impossible values
                int biggestValue = -1;
                int keyWithBiggestValue = -1;
                int numberOfCards = cardMap.size();
                
                // Iterate over all cards to get the most represnted
                // card in hand.
                for (Integer key : cardMap.keySet()
                                  .toArray(new Integer[numberOfCards]))
                {
                    // Compare to previous and replace if necessary.
                    if (cardMap.get(key) > biggestValue)
                    {
                        keyWithBiggestValue = key;
                        biggestValue = cardMap.get(key);
                    }
                }
                
                this.cardPreference = keyWithBiggestValue;
                
                this.strategy = 1;
                // Continue to case 1 to choose card to remove from new
                // preferences.
                
            // Default strategy where player index-number card.
            case 1: 
                
                for (int i = 0; i < this.hand.size(); i++)
                {
                    Card card = this.hand.get(i);
                    if(!(card.getNumber() == this.cardPreference))
                    {
                        cardToBeDiscarded = this.hand.remove(i);
                        break;
                    } 
                    
                }
                
                if (cardToBeDiscarded == null)
                {
                    cardToBeDiscarded = this.hand.remove(0);
                }
       }
        
       return cardToBeDiscarded;
    }
    
    /**
     * Appends out put to player's log file
     * 
     * @param text String to append
     * @return Nothing.
     */
    private void appendToPlayerLog(String text)
    {
        try
        {
            FileWriter fw = new FileWriter(fileName, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(text);
            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Something bad happened.. Try again!");
        }
    }
}