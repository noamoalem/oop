/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 */
public class Library {

    private final int VALIDID = 0;

    private final int FAIL = -1;

    /**
     * The maximal number of books this library can hold.
     */
    private final int bookCapacity;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    private final int borrowedBooks;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    private final int patronCapacity;

    /**
     * Array of all the books owned by this library.
     */
    private Book[] bookArray;

    /**
     * Array of all the patrons that registered to this library.
     */
    private Patron[] patronArray;

    /**
     * Array that count how many books each patron borrowing at the moment. for example patron with id 0 will
     * be in the first place of the array and the number that be there is the number of books he borrow.
     */
    private int[] booksToPatronArray;

    /**
     * The number of books the library hold.
     */
    private int currentNumOfBooks = 0;

    /**
     * The number of patrons that register to the library at the moment.
     */
    private int currentNumOfPatrons = 0;

    /*----=  Constructors  =-----*/

    /**
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a patron to borrow at the same
     *                          time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        bookCapacity = maxBookCapacity;
        borrowedBooks = maxBorrowedBooks;
        patronCapacity = maxPatronCapacity;
        bookArray = new Book[bookCapacity];
        patronArray = new Patron[patronCapacity];
        booksToPatronArray = new int[patronCapacity];
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        if (getBookId(book) == FAIL) { //The library dose't owen this book.
            if (currentNumOfBooks < bookCapacity) {
                bookArray[currentNumOfBooks] = book; // Adding the book to the library.
                currentNumOfBooks++;
                return currentNumOfBooks - 1;
            }
            return FAIL;
        }
        for (int i = 0; i < bookCapacity; i++) {
            if (bookArray[i] == book) {
                return i; // The book already in the library.
            }
        }
        return FAIL;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId){
        if (bookId >= VALIDID && bookId < bookCapacity) {
            return bookArray[bookId] != null;
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book){
        for (int i = 0; i<bookCapacity; i++) {
            if (bookArray[i] == book) {
                return i; // The book owned by this library.
            }
        }
        return FAIL;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId){
        if (isBookIdValid(bookId)){ // Check if the library own this book.
            if (bookArray[bookId].getCurrentBorrowerId() == -1){ // no patron is currently borrowing the book.
                return true;
            }
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered or if the patron was already registered. a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){
        for (int i = 0; i<patronCapacity; i++) {
            if (patronArray[i] == patron) {
                return i; // The patron already register to this library.
            }
        }
        if (currentNumOfPatrons<patronCapacity){
            patronArray[currentNumOfPatrons] = patron;
            currentNumOfPatrons ++;
            return currentNumOfPatrons -1;
        }
        return FAIL;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId){
        if (patronId >= VALIDID && patronId < patronCapacity) {
            return patronArray[patronId] != null;
        }
        return false;
    }


    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1
     * otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron){
        for (int i = 0; i<patronCapacity; i++) {
            if (patronArray[i] == patron) {
                return i; // The patron already register to this library.
            }
        }
        return FAIL;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId){
        if (isPatronIdValid(patronId)){
            if (isBookAvailable(bookId)){
                Book book = bookArray[bookId];
                // Check the patron isn't already borrowing the maximal number of books allowed,
                // and will enjoy this book
                if (booksToPatronArray[patronId]<borrowedBooks && patronArray[patronId].willEnjoyBook(book)){
                    booksToPatronArray[patronId]++;
                    book.setBorrowerId(patronId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){
        if (isBookIdValid(bookId) && !isBookAvailable(bookId)) {
            Book book = bookArray[bookId];
            booksToPatronArray[book.getCurrentBorrowerId()]--;
            book.setBorrowerId(FAIL);
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most. Null if no book is
     * available.
     */
    Book suggestBookToPatron(int patronId){
        int flag = 0;
        Patron patron = patronArray[patronId];
        Book bestBook = new Book("0", "0", 0, 0,
                0, 0);
        if (isPatronIdValid(patronId)) {
            for (int i = 0; i < bookCapacity; i++) {
                if (isBookAvailable(i) && (patron.willEnjoyBook(bookArray[i])) &&
                        (patron.getBookScore(bookArray[i]) > patron.getBookScore(bestBook))) {
                    bestBook = bookArray[i];
                    flag = 1;
                }
            }
            if (flag == 1) { // best book has change.
                return bestBook;
            }
        }
        return null;
    }
}
