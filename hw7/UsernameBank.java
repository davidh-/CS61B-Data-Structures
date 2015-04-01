import java.util.Map;
import java.util.HashMap;

public class UsernameBank {

    // Instance variables (remember, they should be private!)
    private HashMap<String, String> database;
    private HashMap<String, Integer> badEmails;
    private HashMap<String, Integer> badUsernames;

    public UsernameBank() {
        database = new HashMap<String, String>();
        badEmails = new HashMap<String, Integer>();
        badUsernames = new HashMap<String, Integer>();
    }

    public void generateUsername(String username, String email) {
        Username test = new Username(username);
        if (!email.contains("@") || database.containsValue(email)) {
            return;
        }
        if (database.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists in database.");
        }
        else if (username == null || email == null) {
            throw new NullPointerException("Username or email is null.");
        }
        else {
            database.put(username, email);
        } 
    }
    public String getEmail(String username) {
        if (username == null) {
            throw new NullPointerException();
        }
        else {
            try {
                Username test = new Username(username);
                if (!database.containsKey(username)) {
                    putBadInfoAway(username, badUsernames);
                    return null;
                }
                else {
                    return database.get(username);
                }

            } catch (Exception e) {
                putBadInfoAway(username, badUsernames);
                return null;
            }
        }
    }
    private void putBadInfoAway (String info, HashMap<String, Integer> badInfoDatabase) {
        if (badInfoDatabase.containsKey(info)) {
            badInfoDatabase.put(info, badInfoDatabase.get(info) + 1);
        } else {
            badInfoDatabase.put(info, 1);
        }
    }

    public String getUsername(String userEmail)  {
        if (userEmail == null) {
            return null;
        }
        else {
            for (String username : database.keySet()) {
                if (database.get(username).equals(userEmail)) {
                    return username;
                }
            }
            putBadInfoAway(userEmail, badEmails);
            return null;
        }
    }

    public Map<String, Integer> getBadEmails() {
        return badEmails;
    }

    public Map<String, Integer> getBadUsernames() {
        return badUsernames;
    }

    public String suggestUsername() {
        return null;
    }

    // The answer is somewhere in between 3 and 1000.
    public static final int followUp() {
        // YOUR CODE HERE
        return 10;
    }

}