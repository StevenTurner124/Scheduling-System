package Scheduler;

/**
 * User Class constructor
 */
public class User {
    private  int userID;
    private  String username;
    public  String password;

    public User(int userID, String username, String password){
        this.userID=userID;
        this.username=username;
        this.password=password;
    }

    /**
     * Getters
     * @return userID,username,password
     */
    public int getUserID(){
        return userID;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    /**
     * setters
     * @param userID, username, password
     */

    public void setUserID(int userID){
        this.username=username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
}
