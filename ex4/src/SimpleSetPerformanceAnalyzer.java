import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * This class check the running time of several action on each one of 5 data structure.
 */
public class SimpleSetPerformanceAnalyzer {
    private static final int MILLISECONDS = 1000000;
    private static final int WARM_UP_FACTOR = 70000;
    private static final int LINKED_LIST_ITERATION = 7000;
    private static final int LINKED_LIST = 3;
    private static final String HI = "hi";
    private static final String POSITIVE_NUM = "23";
    private static final String NEGATIVE_NUM = "-13170890158";
    private static SimpleSet[] dataStructuresArray;
    private static final String[] data1Array = Ex4Utils.file2array("data1.txt");
    private static final String[] data2Array = Ex4Utils.file2array("data2.txt");

    /**
     * Constructs a new SimpleSet array filled with openHashSet, closeHashSet, linkedList, treeSet & hashSet.
     */
    public SimpleSetPerformanceAnalyzer(){
        SimpleSet hashSet = new CollectionFacadeSet(new HashSet<String>());
        SimpleSet treeSet = new CollectionFacadeSet(new TreeSet<String>());
        SimpleSet linkedList = new CollectionFacadeSet(new LinkedList<String>());
        SimpleSet closeHashSet = new ClosedHashSet();
        SimpleSet openHashSet = new OpenHashSet();
        dataStructuresArray = new SimpleSet[]{openHashSet, closeHashSet, treeSet, linkedList, hashSet};
    }

    /**
     * This method adding all the words in data1.txt/data2.txt, one by one, to  a given data structures.
     * @param dataSt Data structure.
     * @param dataArray data1.txt/data2.txt
     */
    private void addingData(SimpleSet dataSt, String[] dataArray){
        for (String word: dataArray){
            dataSt.add(word);
        }
    }


    /**
     * This method search the string in given data structures.
     * @param dataSt dataSt Data structure to search in.
     * @param searchVal String to search.
     */
    private void contains(SimpleSet dataSt, String searchVal) {
        if (dataSt == dataStructuresArray[LINKED_LIST]){
            int i = 0;
            while (i < LINKED_LIST_ITERATION) {
                dataSt.contains(searchVal);
                i++;
            }
        }
        else {
            int i = 0;
            while (i < WARM_UP_FACTOR) {
                dataSt.contains(searchVal);
                i++;
            }
        }
    }

    /**
     * This method measured the running time in milliseconds of adding strings one by one to each one of the
     * 5 data structure.
     * @param dataTxt all the strings to add to the data structure.
     */
    private void checkTimeLoad(String[] dataTxt) {
        for (SimpleSet dataSet : dataStructuresArray) {
            long timeBefore = System.nanoTime();
            addingData(dataSet, dataTxt);
            long difference = System.nanoTime() - timeBefore;
            System.out.println(dataSet.toString() + " " + difference / MILLISECONDS);
        }
    }
    /**
     * This method measured the running time in milliseconds of searching the string hi in each one of the
     * 5 data structure.
     */
    private void checkTimeContainHi(){
        int iteration;
        for (SimpleSet dataSet:dataStructuresArray){
            if (dataSet != dataStructuresArray[LINKED_LIST]){
                warmUp(HI,dataSet);
                iteration = WARM_UP_FACTOR;
            }
            else {
                iteration = LINKED_LIST_ITERATION;
            }
        long timeBefore = System.nanoTime();
        contains(dataSet,HI);
        long difference = System.nanoTime() - timeBefore;
        System.out.println(dataSet.toString()+" "+difference / iteration);
        }
    }

    /**
     * This method measured the running time in milliseconds of searching the string -13170890158 in each one
     * of the 5 data structure.
     */
    private void checkTimeContainNegative(){
        int iteration;
        for (SimpleSet dataSet:dataStructuresArray) {
            if (dataSet != dataStructuresArray[LINKED_LIST]) {
                warmUp(NEGATIVE_NUM, dataSet);
                iteration = WARM_UP_FACTOR;
            }
            else {
                iteration = LINKED_LIST_ITERATION;
            }
            long timeBefore = System.nanoTime();
            contains(dataSet,NEGATIVE_NUM);
            long difference = System.nanoTime() - timeBefore;
            System.out.println(dataSet.toString() + " " + difference / iteration);
        }
    }

    /**
     * This method measured the running time in milliseconds of searching the string 23 in each one of the
     * 5 data structure.
     */
    private void checkTimeContainPositive(){
        int iteration;
        for (SimpleSet dataSet:dataStructuresArray){
            if (dataSet != dataStructuresArray[LINKED_LIST]){
                warmUp(POSITIVE_NUM,dataSet);
                iteration = WARM_UP_FACTOR;
            }
            else {
                iteration = LINKED_LIST_ITERATION;
            }
            long timeBefore = System.nanoTime();
            contains(dataSet,POSITIVE_NUM);
            long difference = System.nanoTime() - timeBefore;
            System.out.println(dataSet.toString()+" "+difference / iteration);
        }
    }

    /**
     * This method perform the warm-up, means its call contains 70000 times.
     * @param toSearch given string to find if the data structure include it.
     * @param dataSt Data structure to search in.
     */
    private void warmUp (String toSearch, SimpleSet dataSt){
        int i = 0;
        while (i < WARM_UP_FACTOR) {
            dataSt.contains(toSearch);
            i++;
        }
    }

    /**
     * This method runs all the method that measured the running time of specific action.
     * it initialized 2 SimpleSetPerformanceAnalyzer objects that one load with data1, the other with data2.
     * and perform the needed method on each one of them.
     * @param args no args.
     */
    public static void main (String[]args){
        SimpleSetPerformanceAnalyzer ANALYZER = new SimpleSetPerformanceAnalyzer();
        System.out.println("--------------------add data1------------");
        ANALYZER.checkTimeLoad(data1Array);
        System.out.println("--------------------contain hi in data1------------");
        ANALYZER.checkTimeContainHi();
        System.out.println("--------------------contain -131... in data1------------");
        ANALYZER.checkTimeContainNegative();

        SimpleSetPerformanceAnalyzer ANALYZER2 = new SimpleSetPerformanceAnalyzer();
        System.out.println("--------------------add data2------------");
        ANALYZER2.checkTimeLoad(data2Array);
        System.out.println("--------------------contain hi in data2------------");
        ANALYZER2.checkTimeContainHi();
        System.out.println("--------------------contain 23 in data2------------");
        ANALYZER2.checkTimeContainPositive();

        }
}
