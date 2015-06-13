package cardgamesim;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
public class CardDeck
{
    
	/**
	 * LinkedList to store the Cards in the deck. Implemented as 
	 * LinkedList<> as capacity needs to be flexible and only top 
	 * and bottom of the deck accessed.
	 */
	private LinkedList<Card> deck;

	/**
	 * The number of the deck. First deck will be to the left of first 
	 * player, etc.
	 */
	private int deckNumber;

	/**
	 * No argument constructor made private, as it does not make sense to
	 * have a 'default' Card Deck.
	 */
	private CardDeck() {}
    
        /**
         * Construct a new Card Deck
         * 
         * @param deckNumber The number the new deck should have
         */
	public CardDeck(int deckNumber)
	{
		this.deck = new LinkedList<>();
		this.deckNumber = deckNumber;
	}
    
    /**
     * Adds a card to the bottom of the deck.
     * 
     * @param card The card to add to the deck.
     * @return Nothing.
     */
    public synchronized void addCard(Card card)
    {
        this.deck.add(card);
    }
    
    /**
     * Returns the number of cards in the list.
     * 
     * @return Number
     */
    public int getSize(){
        return this.deck.size();
    }
    
    
    /**
     * Retrieves the top card of the deck.
     * 
     * @return Returns the card retrieved
     */
    public synchronized Card popCard()
    {
        return this.deck.remove();
    }
    
    /**
     * Checks if the deck is empty.
     * 
     * @return boolean value of emptiness
     */
    public synchronized boolean isEmpty()
    {
        return this.deck.isEmpty();
    }
    
    /**
     * Save each deck's state to its corresponding output file.
     * 
     * @return Nothing.
     */
    public void saveToFile()
    {
        String filename = "deck" + deckNumber + "_output.txt";
    
        // Stores all cards in array for easy access of face values 
        // and printing
        Card[] deckArray = this.deck
                                .toArray(new Card[this.deck.size()]);
        try
        {
            FileWriter fw = new FileWriter(filename);
            PrintWriter pw = new PrintWriter(fw);
            // Initialise line to print
            String line = "deck " + deckNumber + " contents : ";
            
            for(int i = 0; i < deckArray.length; i++)
            {
                // Append face value of next card in deck to line.
                line += deckArray[i].getNumber() + " ";
            }
            
            // Write to File and close FileWriter
            pw.println(line);
            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Something bad happened.. Try again!");
        }
    }
}