public class Username {

    // Potentially useless note: (int) '0' == 48, (int) 'a' == 97

    // Instance Variables (remember, they should be private!)
    // YOUR CODE HERE
    private String username;

    public Username() {
        username = "";
        int i = 0;
        int randomMax = (int) (Math.random() * (4 - 2)) + 2;
        while (i < randomMax) {
            int randomPick = (int) (Math.random() * (4 - 1)) + 1;
            if (randomPick == 1) {
                username += (char) ((int) (Math.random() * (58 - 48)) + 48);
            }
            else if (randomPick == 2) {
                username += (char) ((int) (Math.random() * (91 - 65)) + 65);
            }
            else {
                username += (char) ((int) (Math.random() * (123 - 97)) + 97);
            }
            i++;
        }
        this.username = username;
    }

    public Username(String reqName) {
        if (reqName == null) {
            throw new NullPointerException("Requested username is null!");
        }
        else if (reqName.length() > 3 || reqName.length() < 2) {
            throw new IllegalArgumentException("Username is not of correct length.");
        }
        else if (!reqName.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("Username contains characters that are not alphanumeric.");
        }
        else {
            username = reqName;
        }
    }

    @Override
    public boolean equals(Object o) {
        // YOUR CODE HERE
        return false;
    }

    @Override
    public int hashCode() { 
        // YOUR CODE HERE
        return 0;
    }

    public static void main(String[] args) {

    }
}