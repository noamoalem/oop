/**
 * This class represents a library patron that has a name and assigns values to different literary aspects
 * of books.
 */
public class Patron {

    /** The patron first name. */
    private final  String firstName;

    /** The patron first name. */
    private final  String lastName;

    /** The weight the patron assigns to the comic aspects of books. */
    private final int comicPreference;

    /** The weight the patron assigns to the dramatic aspects of books. */
    private final int dramaticPreference;

    /** The weight the patron assigns to the educational aspects of books. */
    private final int educationalPreference;

    /**  The minimal literary value a book must have for this patron to enjoy it. */
    private final int enjoymentThreshold;

    /**
     * Constructor. Creates a new patron with the given characteristics.
     * @param patronFirstName The first name of the patron.
     * @param patronLastName The last name of the patron.
     * @param comicTendency The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy
     *                                 it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold){
        firstName = patronFirstName;
        lastName = patronLastName;
        comicPreference = comicTendency;
        dramaticPreference = dramaticTendency;
        educationalPreference = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * This method returns a string representation of the patron.
     * @return The String representation of this patron.
     */
    String stringRepresentation(){
        return firstName + " " + lastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book The book to asses.
     * @return The literary value this patron assigns to the given book.
     */
    int getBookScore(Book book){
        return dramaticPreference * book.dramaticValue + educationalPreference * book.educationalValue +
                comicPreference * book.comicValue;

    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * @param book The book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book){
        if (getBookScore(book)>enjoymentThreshold){
            return true;
        }
        return false;
    }
}
