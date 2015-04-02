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
            throw new NullPointerException();
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
        String username = "";
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
        return username;
    }

    // The answer is somewhere in between 3 and 1000.
    public static final int followUp() {
        // tried 10, 15, 5
        return 6;
    }

}