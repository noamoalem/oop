import oop.ex3.searchengine.*;
import java.util.Comparator;

/**
 * This class implement comparator. to sort hotels according their rating/alphabet order.
 */
public class SortByRating implements Comparator<Hotel> {
    private static final int  POSITIVE = 1;
    private static final int  NEGATIVE = -1;
    @Override
    /**
     * This method compare between two hotel which one has greater rating.
     * @return 1 if the first hotel has greater rating/he before the second in alphabet order.
     *        -1 if the second hotel has greater rating/he before the second in alphabet order.
     *        0 otherwise.
     */
    public int compare(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating()>hotel2.getStarRating()){
            return POSITIVE;
        }
        if (hotel1.getStarRating()<hotel2.getStarRating()){
            return NEGATIVE;
        }
        else {
            int compare = hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
            if (compare <= 0){
                return POSITIVE;
            }
        }
        return 0;
    }
}
