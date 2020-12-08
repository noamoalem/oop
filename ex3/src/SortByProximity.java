import oop.ex3.searchengine.*;
import java.util.Comparator;

/**
 * This class implement comparator. to sort hotels according their distance to some point.
 */
public class SortByProximity implements Comparator<Hotel> {
    private double[] checkPoint;
    private static final int  POSITIVE = 1;
    private static final int  NEGATIVE = -1;
    private static final int  POWER_NUM = 2;

    public SortByProximity(double[] Point){
        checkPoint=Point;
    }

    /**
     * This method compare between two hotel which one is the closest for some point.
     * @param hotel1 first hotel.
     * @param hotel2 second hotel.
     * @return 1 if the first hotel is more far from the point/has less point of interest the the second hotel.
     *        -1 if the first hotel is more close to the point/has more point of interest the the second hotel.
     *         0 if both of the hotel have same distance from the point and same number of point of interest.
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        double[] point1 = new double[]{hotel1.getLatitude(), hotel1.getLongitude()};
        double[] point2 = new double[]{hotel2.getLatitude(), hotel2.getLongitude()};
        if (euclideanDistance(point1, checkPoint) > euclideanDistance(point2, checkPoint)) {
            return NEGATIVE;
        }
        if (euclideanDistance(point1, checkPoint) < euclideanDistance(point2, checkPoint)) {
            return POSITIVE;
        }
        else {
            if (hotel1.getNumPOI() > hotel2.getNumPOI()) {
                return POSITIVE;
            }
            if (hotel1.getNumPOI() < hotel2.getNumPOI()) {
                return NEGATIVE;
            }
        }
        return 0;
    }

    /**
     * This method finds the Euclidean distance of two points.
     * @param point1 one point.
     * @param point2 second point.
     * @return The Euclidean distance of two points.
     */
    private double euclideanDistance(double[] point1, double[] point2) {
        double x1x2 = Math.pow((point1[0] - point2[0]), POWER_NUM);
        double y1y2 = Math.pow((point1[1] - point2[1]), POWER_NUM);
        return Math.pow((x1x2 + y1y2), 0.5);
    }

}
