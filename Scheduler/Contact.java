package Scheduler;

/**
 * creates contact class
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    public Contact(int contactID, String contactName, String contactEmail){
        this.contactID=contactID;
        this.contactName=contactName;
        this.contactEmail=contactEmail;
    }

    /**
     * Contact ID getter
     * @return contactID
     */

    public int getContactID(){
        return contactID;
    }

    /**
     * Contact ID setter
     * @param contactID
     */


    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Contact Name getter
     * @return contactName
     */

    public String getContactName(){
        return contactName;
    }

    /**
     * contact name setter
     * @param contactName
     */
    public void setContactName(String contactName){
        this.contactName=contactName;
    }

    /**
     * Contact Email getter
     * @return contactEmail
     */

    public String getContactEmail(){
        return contactEmail;
    }

    /**
     * Contact Email setter
     * @param contactEmail
     */

    public void setContactEmail(String contactEmail){
        this.contactEmail=contactEmail;
    }
}
