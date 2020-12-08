package orders;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class sort a file array according to a given order(size, type or alphabet order)
 */
public class MergeSort {
    private final static int NEGATIVE = -1;
    private final static int POSITIVE = 1;
    private final static int ZERO = 0;
    private Comparator<File> sortNeeded;
    private boolean isReversed;

    /**
     * The constructor.
     * @param comp given comparator.
     */
    public MergeSort(Comparator<File> comp, boolean reversed){
        sortNeeded = comp;
        isReversed = reversed;
    }

    /**
     * This method sort given array.
     * @param fileArray given files array.
     * @param left the fist index.
     * @param mid the middle index.
     * @param right the last index.
     */
    void merge(ArrayList<File> fileArray, int left, int mid, int right) {
        int firstHalf = mid - left + 1;
        int secondHalf = right - mid;

        ArrayList<File> leftArray = new ArrayList<>();
        ArrayList<File> rightArray = new ArrayList<>();

        for (int i=0; i<firstHalf; ++i)
            leftArray.add(fileArray.get(left + i));

        for (int j=0; j<secondHalf; ++j)
            rightArray.add(fileArray.get(mid + 1+ j));

        int i = 0, j = 0;

        int k = left;
        while (i < firstHalf && j < secondHalf) {
            if (!isReversed){
                if (sortNeeded.compare(leftArray.get(i), rightArray.get(j)) > ZERO) {
                    fileArray.set(k,leftArray.get(i));
                    i++;
                }
                else{
                    fileArray.set(k,rightArray.get(j));
                    j++;
                }
                k++;
            }
            else {
                if (sortNeeded.compare(leftArray.get(i), rightArray.get(j)) < ZERO) {
                    fileArray.set(k,leftArray.get(i));
                    i++;
                }
                else {
                    fileArray.set(k,rightArray.get(j));
                    j++;
                }
                k++;
            }
        }

        while (i < firstHalf) {
            fileArray.set(k,leftArray.get(i));
            i++;
            k++;
        }

        while (j < secondHalf) {
            fileArray.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    /**
     * This method perform the merge sort on given array, using the method merge.
     * @param fileArray given files array.
     * @param left the fist index.
     * @param right the last index.
     */
    void mergeSort(ArrayList<File> fileArray, int left, int right){
        if (left<right){
            int mid = (left+right)/2;
            mergeSort(fileArray,left,mid);
            mergeSort(fileArray,mid+1,right);
            merge(fileArray,left,mid,right);
        }
    }
}
