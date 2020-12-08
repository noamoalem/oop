import oop.ex3.searchengine.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The BoopingSite class implements a booping Site object that have the ability to search hotels according
 * to some parameters.
 */
public class BoopingSite {
    private static final int LATITUDE_LIMIT = 90;
    private static int LONGITUDE_LIMIT = 180;
    private static final int firstBigger = 0;
    private static final Hotel[] emptyArray = new Hotel[0];
    private final String fileName;
    private Hotel[] allHotels;
    private Map<Hotel, Integer> hotelsCityRatingMap;
    private SortByRating sortByRating = new SortByRating();
    private SortByProximity sortByProximity;

    /**
     * the constructor. initialize boopingSite object.
     * @param name The hotels file name.
     */
    public BoopingSite(String name) {
        fileName = name;
        allHotels = HotelDataset.getHotels(fileName);
    }

    /**
     * This method finds all the hotel in given city, and sorted them by rating.
     * @param city given city to search hotels in.
     * @return returns an array of hotels located in the given city, sorted from the highest star-rating
     * to the lowest.
     * Hotels that have the same rating will be organized according to the alphabet order of the hotel name.
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        hotelsCityRatingMap = new HashMap<>();
        Hotel[] allHotels = HotelDataset.getHotels(fileName);
        for (Hotel hotel : allHotels) {
            if (hotel.getCity().equals(city)) {
                hotelsCityRatingMap.put(hotel, hotel.getStarRating());
            }
        }
        Hotel[] hotelsInCityArray=hotelsCityRatingMap.keySet().toArray(new Hotel[hotelsCityRatingMap.size()]);
        for (int i = 0; i<hotelsInCityArray.length -1; i++){
            for (int j = 0; j < hotelsInCityArray.length - 1 - i; j++) {
                int compareRes = sortByRating.compare(hotelsInCityArray[j],hotelsInCityArray[j+1]);
                if (compareRes > firstBigger){
                    continue;
                }
                Hotel tempHotel = hotelsInCityArray[j];
                hotelsInCityArray[j] = hotelsInCityArray[j + 1];
                hotelsInCityArray[j + 1] = tempHotel;
            }
        }
        return hotelsInCityArray;
    }

    /**
     * This method sort the hotel from the closest to a given point to the farthest.
     * @param latitude The latitude
     * @param longitude The longitude
     * @return array of hotels, sorted according to their Euclidean distance from the
     * given geographic location, in ascending order. Hotels that are at the same distance from the given
     * location are organized according to the number of points-of-interest for which they are close to.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if (latitude<-LATITUDE_LIMIT || latitude> LATITUDE_LIMIT || longitude<-LONGITUDE_LIMIT || longitude>
                LONGITUDE_LIMIT){
            return emptyArray;
        }
        double[] checkPoint = new double[]{latitude, longitude};
        sortArrayByProximity(checkPoint,allHotels);
        return allHotels;
    }

    /**
     * This method sort the hotel in a given city from the closest to a given point to the farthest.
     * @param city given city to search in.
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @return array of hotels in the given city, sorted according to their Euclidean distance from the given
     * geographic location, in ascending order. Hotels that are at the same distance from the given location
     * are organized according to the number of points-of-interest for which they are close to.
     */
    public Hotel[] getHotelsInCityByProximity(String city,double latitude, double longitude) {
        if (latitude < -LATITUDE_LIMIT || latitude > LATITUDE_LIMIT || longitude < -LONGITUDE_LIMIT || longitude >
                LONGITUDE_LIMIT) {
            return emptyArray;
        }
        double[] checkPoint = new double[]{latitude, longitude};

        sortByProximity = new SortByProximity(checkPoint);
        hotelsCityRatingMap = new HashMap<>();
        for (Hotel hotel : allHotels) {
            if (hotel.getCity().equals(city)) {
                hotelsCityRatingMap.put(hotel, hotel.getStarRating());
            }
        }
        Hotel[] hotelsInCityArray=hotelsCityRatingMap.keySet().toArray(new Hotel[hotelsCityRatingMap.size()]);
        return sortArrayByProximity(checkPoint,hotelsInCityArray);
    }

    /**
     * This method sort hotel array according to the distance from the closest to a given point to the
     * farthest. using compare of the class sortByProximity.
     * @param point given point to check the distance from.
     * @param hotels hotels array to sort.
     * @return hotels array sorted according to their Euclidean distance from the given
     *  geographic location, in ascending order. Hotels that are at the same distance from the given location
     *  are organized according to the number of points-of-interest for which they are close to.
     */
    private Hotel[] sortArrayByProximity(double[] point,Hotel[] hotels){
        sortByProximity = new SortByProximity(point);
        for (int i = 0; i < hotels.length - 1; i++) {
            for (int j = 0; j < hotels.length - 1 - i; j++) {
                int compareRes = sortByProximity.compare(hotels[j], hotels[j + 1]);
                if (compareRes > firstBigger) {
                    continue;
                }
                Hotel tempHotel = hotels[j];
                hotels[j] = hotels[j + 1];
                hotels[j + 1] = tempHotel;
            }
        }
        return hotels;
    }
}
