/* Radix.java */
package radix;
import java.lang.Math;
import java.util.Arrays;
/**
 * Sorts is a class that contains an implementation of radix sort.
 * @author David Dominguez Hooper
 */
public class Sorts {


    /**
     *  Sorts an array of int keys according to the values of <b>one</b>
     *  of the base-16 digits of each key. Returns a <b>NEW</b> array and
     *  does not modify the input array.
     *  
     *  @param key is an array of ints.  Assume no key is negative.
     *  @param whichDigit is a number in 0...7 specifying which base-16 digit
     *    is the sort key. 0 indicates the least significant digit which
     *    7 indicates the most significant digit
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys sorted according to the chosen digit.
     **/
    public static int[] countingSort(int[] keys, int whichDigit) {
        int[] counts = new int[16];
        for (int key : keys) {
            int index = (key >> (whichDigit * 4)) & 0b1111;
            counts[index] = counts[index] + 1;
        }
        int[] startingPoints = new int[16];
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            if (i != 0) {
                startingPoints[i] = total;
            }
            total += counts[i];
        }
        int[] sorted = new int[keys.length];
        for (int key : keys) {
            int digit = (key >> (whichDigit * 4)) & 0b1111;
            sorted[startingPoints[digit]] = key;
            startingPoints[digit] += 1;
        }
        return sorted;
    }

    /**
     *  radixSort() sorts an array of int keys (using all 32 bits
     *  of each key to determine the ordering). Returns a <b>NEW</b> array
     *  and does not modify the input array
     *  @param key is an array of ints.  Assume no key is negative.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys in sorted order.
     **/

    public static int[] radixSort(int[] keys) {
        int[] sorted = keys.clone();
        int width = 0;
        int max = 0;
        for (int key : keys) {
            if (key > max) {
                max = key;
            }
        }
        while (max != 0) {
            width += 1;
            max = (int) Math.floor(max / 10);
        }
        int  i = 0;
        while (i < width) {
            sorted = countingSort(sorted, i);
            i += 1;
        }
        return sorted;
    }
}
