package cardgamesim;

/**
 * @version 2013-11-13
 * @author 620017002 and 620007094
 */
public class Card 
{
    /**
     * The face value of the card.
     */
    private int number;
    
    /**
     * A default Card object is not needed because the values of the 
     * number attribute are defined by an input file.
     */
    private Card(){}
    
    
    /**
     * A one argument constructor that creates Card objects with their 
     * number attribute equal to the argument.
     * 
     * @param number Face value of card.
     * @require number >= 0
     */
    public Card(int number)
    {
        this.number = number;
    }
    
    /**
     * This method returns the face value of the Card instance upon 
     * which this method is called.
     * 
     * @return Face value of card.
     * @ensure getNumber() = this.number
     */
    public int getNumber()
    {
        return this.number;
    }
}
