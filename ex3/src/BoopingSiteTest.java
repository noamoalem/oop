import oop.ex3.searchengine.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The BoopingSiteTest class implements a tests of the BoopingSite methods.
 */
public class BoopingSiteTest {
    private static final Hotel[] emptyArray = new Hotel[0];
    private static final String GOA = "goa";
    private static final String RISHIKESH = "rishikesh";
    private static final String MANALI = "manali";
    private static final String DELHI = "delhi";
    private BoopingSite boopingSite;
    private BoopingSite boopingSite2;
    private String fileName;
    private String fileName2;

    @Before
    /**
     * This method initialize LongTermStorage object. to use this object in the tests.
     */
    public void createTestObjects(){
        fileName = "hotels_dataset.txt";
        fileName2 = "hotels_tst1.txt";
        boopingSite = new BoopingSite(fileName);
        boopingSite2 = new BoopingSite(fileName2);
    }

    @Test
    /**
     * This method test the getHotelsInCityByRating of BoopingSite class.
     */
    public void testGetHotelsInCityByRating(){
        Hotel[] sortedHotelGoa = boopingSite.getHotelsInCityByRating(GOA);
        for (Hotel hotel:sortedHotelGoa){
            assertEquals("the city is goa!",GOA,hotel.getCity());
        }
        for (int i =0; i<sortedHotelGoa.length-1; i++){
            // check sorted from the highest star-rating to the lowest
            assertTrue(sortedHotelGoa[i].getStarRating()>=sortedHotelGoa[i+1].getStarRating());
            if (sortedHotelGoa[i].getStarRating()==sortedHotelGoa[i+1].getStarRating()){
                int compareHotelsName =
                        sortedHotelGoa[i].getPropertyName().compareTo(sortedHotelGoa[i+1].getPropertyName());
                // "a" < "b". "a".compareTo ("b") return negative number. check order by alphabet.
                assertTrue("need to be order by alphabet",compareHotelsName<=0);
            }
        }

        Hotel[] sortedHotelRishikesh = boopingSite.getHotelsInCityByRating(RISHIKESH);
        for (Hotel hotel:sortedHotelRishikesh){
            assertEquals("the city is rishikesh!",RISHIKESH,hotel.getCity());
        }

        for (int i =0; i< sortedHotelRishikesh.length-1; i++){
            // check sorted from the highest star-rating to the lowest
            assertTrue(sortedHotelRishikesh[i].getStarRating()>=sortedHotelRishikesh[i+1].getStarRating());
            assertFalse(sortedHotelRishikesh[i].getStarRating()<sortedHotelRishikesh[i+1].getStarRating());
            if (sortedHotelRishikesh[i].getStarRating()==sortedHotelRishikesh[i+1].getStarRating()){
                int compareHotelsName =
             sortedHotelRishikesh[i].getPropertyName().compareTo(sortedHotelRishikesh[i+1].getPropertyName());
                // check order by alphabet.
                assertTrue("need to be order by alphabet",compareHotelsName<=0);
            }
        }
        assertArrayEquals("should be empty array",emptyArray,
                                            boopingSite.getHotelsInCityByRating("no such city"));

    }

    @Test
    /**
     * This method test the getHotelsInCityByRating of BoopingSite class.
     */
    public void testGetHotelsInCityByRating2(){
        Hotel[] sortedHotelManali = boopingSite2.getHotelsInCityByRating(MANALI);
        for (Hotel hotel:sortedHotelManali){
            assertEquals("the city is manali!",MANALI,hotel.getCity());
        }
        for (int i =0; i< sortedHotelManali.length-1; i++){
            // check sorted from the highest star-rating to the lowest
            assertTrue(sortedHotelManali[i].getStarRating()>=sortedHotelManali[i+1].getStarRating());
            assertFalse(sortedHotelManali[i].getStarRating()<sortedHotelManali[i+1].getStarRating());
            if (sortedHotelManali[i].getStarRating()==sortedHotelManali[i+1].getStarRating()){
                int compareHotelsName =
                  sortedHotelManali[i].getPropertyName().compareTo(sortedHotelManali[i+1].getPropertyName());
                // check order by alphabet.
                assertTrue("need to be order by alphabet",compareHotelsName<=0);
            }
        }

    }
    @Test
    /**
     * This method test the getHotelByProximity of BoopingSite class.
     */
    public void testGetHotelsByProximity(){
        double[] givenPoint = new double[]{28.43424184,77.05041922};
        Hotel[] sortedByProximity = boopingSite.getHotelsByProximity(givenPoint[0],givenPoint[1]);
        for (int i =0; i< sortedByProximity.length-1; i++){

            double[] hoteliPoint = new double[]
                    {sortedByProximity[i].getLatitude(),sortedByProximity[i].getLongitude()};
            double[] hoteli1Point = new double[]
                    {sortedByProximity[i+1].getLatitude(),sortedByProximity[i+1].getLongitude()};

            assertTrue("The distance should be smaller!",
                    euclideanDistance(givenPoint,hoteliPoint)<=
                                            euclideanDistance(givenPoint,hoteli1Point));
            if (euclideanDistance(givenPoint, hoteliPoint) == euclideanDistance(givenPoint, hoteli1Point)) {
                // check order by point of interest.
                assertTrue(sortedByProximity[i].getNumPOI()>= sortedByProximity[i+1].getNumPOI());
            }
        }

        double[] givenPoint2 = new double[]{32.2459085,77.1826108};
        Hotel[] sortedByProximity2 = boopingSite2.getHotelsByProximity(givenPoint2[0],givenPoint2[1]);
        for (int i =0; i< sortedByProximity2.length-1; i++){

            double[] hoteliPoint2 = new double[]
                    {sortedByProximity2[i].getLatitude(), sortedByProximity2[i].getLongitude()};
            double[] hoteli1Point2 = new double[]
                    {sortedByProximity2[i+1].getLatitude(),sortedByProximity2[i+1].getLongitude()};

            assertTrue("The distance should be smaller!",
                    euclideanDistance(givenPoint2,hoteliPoint2)<=
                                                  euclideanDistance(givenPoint2,hoteli1Point2));
            if (euclideanDistance(givenPoint2, hoteliPoint2)==euclideanDistance(givenPoint2, hoteli1Point2)){
                // check order by point of interest.
                assertTrue(sortedByProximity2[i].getNumPOI()>= sortedByProximity2[i+1].getNumPOI());

            }
        }
        // check the first in the array will be what we expected.
        assertEquals("032e7cb8f2524c89bd412d0eb16f25ee", boopingSite.getHotelsByProximity
                (15.4586072,73.8167385)[0].getUniqueId());
        assertEquals("1b42d47ffa7a51885ab8529fef7e8131", boopingSite.getHotelsByProximity
                (32.2236026,77.1858995)[0].getUniqueId());
        // check illegal input.
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (100,200));
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (80,200));
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (110,150));
        }


    @Test
    /**
     * This method test the getHotelsInCityByProximity of BoopingSite class.
     */
    public void testGetHotelsInCityByProximity(){
        double[] givenPoint = new double[]{30.06022175, 78.39080391};
        Hotel[] sortedByCityProximity = boopingSite.getHotelsInCityByProximity
                ("rishikesh",givenPoint[0],givenPoint[1]);
        for (int i =0; i< sortedByCityProximity.length-1; i++){
            double[] hoteliPoint = new double[]
                    {sortedByCityProximity[i].getLatitude(),sortedByCityProximity[i].getLongitude()};
            double[] hoteli1Point = new double[]
                    {sortedByCityProximity[i+1].getLatitude(),sortedByCityProximity[i+1].getLongitude()};
            assertEquals("the city is rishikesh!",RISHIKESH,
                    sortedByCityProximity[i].getCity());
            assertTrue("The distance should be smaller!",
                    euclideanDistance(givenPoint,hoteliPoint)<=
                                           euclideanDistance(givenPoint,hoteli1Point));
            if (euclideanDistance(givenPoint, hoteliPoint) == euclideanDistance(givenPoint, hoteli1Point)) {
                // check order by point of interest.
                assertTrue(sortedByCityProximity[i].getNumPOI()>=
                                                    sortedByCityProximity[i+1].getNumPOI());
            }
        }

        Hotel[] sortedByCityProximity2 = boopingSite.getHotelsInCityByProximity
                (DELHI,givenPoint[0],givenPoint[1]);
        for (int i =0; i< sortedByCityProximity2.length-1; i++){
            double[] hoteliPoint = new double[]
                    {sortedByCityProximity2[i].getLatitude(),sortedByCityProximity2[i].getLongitude()};
            double[] hoteli1Point = new double[]
                    {sortedByCityProximity2[i+1].getLatitude(),sortedByCityProximity2[i+1].getLongitude()};
            assertEquals("the city is Delhi!",DELHI,
                    sortedByCityProximity2[i].getCity());
            assertTrue("The distance should be smaller!",
                    euclideanDistance(givenPoint,hoteliPoint)<=
                            euclideanDistance(givenPoint,hoteli1Point));
            if (euclideanDistance(givenPoint, hoteliPoint) == euclideanDistance(givenPoint, hoteli1Point)) {
                // check order by point of interest.
                assertTrue(sortedByCityProximity2[i].getNumPOI()>=
                        sortedByCityProximity2[i+1].getNumPOI());
            }
        }
        Hotel[] sortedByCityProximity3 = boopingSite2.getHotelsInCityByProximity
                (MANALI,givenPoint[0],givenPoint[1]);
        for (int i =0; i< sortedByCityProximity3.length-1; i++){
            double[] hoteliPoint = new double[]
                    {sortedByCityProximity3[i].getLatitude(),sortedByCityProximity3[i].getLongitude()};
            double[] hoteli1Point = new double[]
                    {sortedByCityProximity3[i+1].getLatitude(),sortedByCityProximity3[i+1].getLongitude()};
            assertEquals("the city is manali!",MANALI,
                    sortedByCityProximity3[i].getCity());
            assertTrue("The distance should be smaller!",
                    euclideanDistance(givenPoint,hoteliPoint)<=
                            euclideanDistance(givenPoint,hoteli1Point));
            if (euclideanDistance(givenPoint, hoteliPoint) == euclideanDistance(givenPoint, hoteli1Point)) {
                // check order by point of interest.
                assertTrue(sortedByCityProximity3[i].getNumPOI()>=
                        sortedByCityProximity3[i+1].getNumPOI());
            }
        }
        // check finding all the hotels in the given city.
        assertEquals("should be true", sortedByCityProximity.length,
                boopingSite.getHotelsInCityByRating(RISHIKESH).length);
        assertEquals("should be true", sortedByCityProximity2.length,
                boopingSite.getHotelsInCityByRating(DELHI).length);
        assertEquals("should be true", sortedByCityProximity3.length,
                boopingSite2.getHotelsInCityByRating(MANALI).length);
        // check the first in the array will be what we expected.
        assertEquals("032e7cb8f2524c89bd412d0eb16f25ee", boopingSite.getHotelsInCityByProximity
                (GOA,15.4586072,73.8167385)[0].getUniqueId());
        assertEquals("8e81338aa7b97ab3d6ea358a831bf768", boopingSite.getHotelsInCityByProximity
                (GOA,15.5408996,73.761342)[0].getUniqueId());
        // check illegal input.
        assertArrayEquals(emptyArray,boopingSite.getHotelsInCityByProximity
                ("no such city",15.5408996,73.761342));
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (100,200));
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (72,250));
        assertArrayEquals("should by empty",emptyArray ,boopingSite.getHotelsByProximity
                (91,134));

    }

    /**
     * This method finds the Euclidean distance of two points.
     * @param point1 one point.
     * @param point2 second point.
     * @return The Euclidean distance of two points.
     */
    private double euclideanDistance(double[] point1, double[] point2) {
        double x1x2 = Math.pow((point1[0] - point2[0]), 2);
        double y1y2 = Math.pow((point1[1] - point2[1]), 2);
        return Math.pow((x1x2 + y1y2), 0.5);
    }

}

