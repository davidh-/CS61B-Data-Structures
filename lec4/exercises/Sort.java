/** Class for printing args in sorted order.
 *
 *  The exercise in this file is to finish the print() and main() methods.
 *  Given that JUnit doesn't give us any way to test print statements,
 *  you do not need to write any tests.
 *
 *  @author Josh Hug
 */

public class Sort {
    /** Sorts the array. */
    public static void sort(String[] a) {
        sort(a, 0);
    }


    /** Sorts A starting from position START. */
    private static void sort(String[] a, int start) {
        if (start == a.length)
            return;
        int mindex = indexOfSmallest(s, start);

        swap(a, start, mindex);

        sort(a, start + 1);
    }

    /** Find the smallest item in A starting from START. */
    public static int indexOfSmallest(String[] a, int start) {
        // start by assuming smallest thing is in position start
        int mindex = start;
        int i = start;
        while (i < a.length) {
            int compareNumber = a[i].comapreTo(a[mindex]);
            if (a[i] < a[mindex]) {
                mindex = i;
            }
            i+=1;
        }
    }


    /** Swaps items in position ix and iy such that the
      * actual data in the array is changed. */
    public static void swap(String[] a, int ix, int iy) {
        /* YOUR CODE HERE */
        String temp = a[ix];
        a[ix] = a[iy];
        
    }



    /** Prints A. */
    public static void print(String[] a) {

    }

    /** Prints args in sorted order.
     */

    public static void main(String[] args) {
        testIndexOfSmallest();
    }
} 