package Scheduler;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * creation of Appointment and its parameters
 */
public class Appointment {
    private int appointmentId;
    private int customerID;
    private int userID;
    private Customer customer;
    private String title;

    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String customerName;
    private Date createDate;
    private  String createdBy;
    private Timestamp lastUpd;
    private String lastUpdBy;
    private String user;
    private LocalDate startDate;
    private LocalDate endDate;
    private int contactID;

    /**
     * Creates call for Appointment and its parameters
     * @param appointmentId integer
     * @param customerID integer
     * @param userID integer
     * @param title String
     * @param description String
     * @param location String
     * @param contact String
     * @param type String
     * @param startDate LocalDate
     * @param endDate LocalDate
     * @param startTime LocalDateTime
     * @param endTime LocalDateTime
     * @param contactID int
     */

    public Appointment(int appointmentId, int customerID, int userID, String title, String description, String location, String contact,
                       String type, LocalDate startDate, LocalDate endDate, LocalDateTime startTime, LocalDateTime endTime, int contactID) {
        this.appointmentId=appointmentId;
        this.title=title;
        this.description=description;
        this.location=location;
        this.type=type;
        this.startDate=startDate;
        this.startTime=startTime;
        this.endDate=endDate;
        this.endTime=endTime;
        this.customerID=customerID;
        this.userID=userID;
        this.contact=contact;
        this.contactID=contactID;
    }

    /**
     * Getters
     */
    public int getAppointmentId(){
        return appointmentId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public String getType(){
        return type;
    }
    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public int getCustomerID(){
        return customerID;
    }
    public int getUserID(){
        return userID;
    }
    public int getContactID(){
        return contactID;
    }
    public String getContact(){
        return contact;
    }

    /**
     * Setters
     */
    public void setAppointmentId(int appointmentId){
        this.appointmentId=appointmentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate=startDate;
    }
    public void setStartTime(LocalDateTime startTime){
        this.startTime=startTime;
    }
    public void setEndDate(LocalDate endDate){
        this.endDate=endDate;
    }
    public void setEndTime(LocalDateTime endTime){
        this.endTime=endTime;
    }
    public void setCustomerID(int customerID){
        this.customerID=customerID;
    }
    public void setUserID(int userID){
        this.userID=userID;
    }
    public void setContactID(int contactID){
        this.contactID=contactID;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
