/** Class that determines whether or not a year is a leap year.
<<<<<<< HEAD
 *  @author David Dominguez Hooper
=======
 *  @author YOUR NAME HERE
>>>>>>> 723db3597ab5deab90cc9b32f4b5a5dcac0f719a
 */
public class Year {

    /** @param  year to be analyzed
     *  @return true if year is a leap year
     *          false if year is not a leap year
     */
    static boolean isLeapYear(int year) {
<<<<<<< HEAD
        if (year % 400 == 0)
            return true;

        else if ((year % 4 == 0) && (year % 100 != 0))
            return true;

        else
            return false;
=======
        return true;    // YOUR CODE HERE
>>>>>>> 723db3597ab5deab90cc9b32f4b5a5dcac0f719a
    }

    /** Calls isLeapYear to print correct statement.
     *  @param  year to be analyzed
     */
    private static void checkLeapYear(int year) {
        if (isLeapYear(year)) {
            System.out.printf("%d is a leap year.\n", year);
        } else {
            System.out.printf("%d is not a leap year.\n", year);
        }
    }

    /** Must be provided an integer as a command line argument ARGS. */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java Year 2000");
        }
        for (int i = 0; i < args.length; i++) {
            try {
                int year = Integer.parseInt(args[i]);
                checkLeapYear(year);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
    }

}

