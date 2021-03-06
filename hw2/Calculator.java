import list.EquationList;

public class Calculator {
    // YOU MAY WISH TO ADD SOME FIELDS
    EquationList list;
    /**
     * TASK 2: ADDING WITH BIT OPERATIONS
     * add() is a method which computes the sum of two integers x and y using 
     * only bitwise operators.
     * @param x is an integer which is one of two addends
     * @param y is an integer which is the other of the two addends
     * @return the sum of x and y
     **/
    public int add(int x, int y) {
        int carry = x & y;
        int base = x ^ y;
        int newCarry = 0;
        int newBase = 0;
        while (carry != 0) {
            carry = carry << 1;
            newCarry = carry & base;
            newBase = carry ^ base;
            carry = newCarry;
            base = newBase;
        }
        return base;
    }

    /**
     * TASK 3: MULTIPLYING WITH BIT OPERATIONS
     * multiply() is a method which computes the product of two integers x and 
     * y using only bitwise operators.
     * @param x is an integer which is one of the two numbers to multiply
     * @param y is an integer which is the other of the two numbers to multiply
     * @return the product of x and y
     **/
    public int multiply(int x, int y) {
        int sum = 0;
        int shift = 0;
        while (y != 0) {
            if ((y & 1) == 1) {
                sum = add(sum, x << shift);
                shift = add(shift, 1);
            }
            else
                shift = add(shift, 1); 
            y = y >>> 1;
        }
        return sum;
    }

    /**
     * TASK 5A: CALCULATOR HISTORY - IMPLEMENTING THE HISTORY DATA STRUCTURE
     * saveEquation() updates calculator history by storing the equation and 
     * the corresponding result.
     * Note: You only need to save equations, not other commands.  See spec for 
     * details.
     * @param equation is a String representation of the equation, ex. "1 + 2"
     * @param result is an integer corresponding to the result of the equation
     **/
    public void saveEquation(String equation, int result) {
        if (list != null) {
            list.next = new EquationList(list.equation, list.result, list.next);
            list.equation = equation;
            list.result = result;
        }
        else
            list = new EquationList(equation, result, null);
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printAllHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  Please print in 
     * the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printAllHistory() {
        printHistory(2147483647);
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  A maximum of n 
     * equations should be printed out.  Please print in the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printHistory(int n) {
        if (list == null)
            return;
        EquationList temp = list;
        while (n > 0) {
            if (temp.next == null) {
                System.out.println(temp.equation + " = " + temp.result);
                n = 0;
            }
            else {
                System.out.println(temp.equation + " = " + temp.result);
                temp = temp.next;
                n -= 1;
            }
        }
    }    

    /**
     * TASK 6: CLEAR AND UNDO
     * undoEquation() removes the most recent equation we saved to our history.
    **/
    public void undoEquation() {
        list = list.next;
    }

    /**
     * TASK 6: CLEAR AND UNDO
     * clearHistory() removes all entries in our history.
     **/
    public void clearHistory() {
        list = null;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeSum() computes the sum over the result of each equation in our 
     * history.
     * @return the sum of all of the results in history
     **/
    public int cumulativeSum() {
        EquationList pointer = list;
        int total = 0;
        while (pointer != null) {
            total += pointer.result;
            pointer = pointer.next;
        }
        return total;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeProduct() computes the product over the result of each equation 
     * in history.
     * @return the product of all of the results in history
     **/
    public int cumulativeProduct() {
        EquationList pointer = list;
        int total = 1;
        while (pointer != null) {
            total *= pointer.result;
            pointer = pointer.next;
        }
        return total;
    }
}