package Scheduler;

import java.security.Timestamp;
import java.util.Date;

public class Address {
    private int addressID= 0;
    private String address;
    private String address2;
    private int cityID=0;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Timestamp lastUpd;
    private String lastUpdBy;

    /**
     * address creator
     * @param addressID int
     * @param address String
     * @param address2 String
     * @param cityID int
     * @param postalCode String
     * @param phone String
     * @param createDate Date
     * @param createdBy String
     * @param lastUpd TimeStamp
     * @param lastUpdBy String
     */
    public Address(int addressID, String address, String address2, int cityID, String postalCode, String phone, Date createDate, String createdBy, Timestamp lastUpd, String lastUpdBy){
        setAddressID(addressID);
        setAddress(address);
        setAddress2(address2);
        setCityID(cityID);
        setPostalCode(postalCode);
        setPhone(phone);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpd(lastUpd);
        setLastUpdBy(lastUpdBy);
    }

    /**
     * Address ID getter
     * @return addressID
     */
    public int getAddressID(){
        return this.addressID;
    }

    /**
     * address getter
     * @return address
     */
    public String  getAddress(){
        return this.address;
    }
    public String getAddress2(){
        return this.address2;
    }

    /**
     * city id getter
     * @return cityID
     */
    public int getCityID() {
        return this.cityID;
    }

    /**
     * post code getter
     * @return postalCode
     */
    public String getPostalCode(){
        return this.postalCode;
    }

    /**
     * phone number getter
     * @return phone
     */
    public String getPhone(){
        return this.phone;
    }

    /**
     * create date getter
     * @return createDate
     */
    public Date getCreateDate(){
        return this.createDate;
    }

    /**
     * getter for created by
     * @return createdBy
     */
    public String getCreatedBy(){
        return this.createdBy;
    }

    /**
     * last updated  getter
     * @return lastUpd
     */
    public Timestamp getLastUpd(){
        return this.lastUpd;
    }

    /**
     * last updated by getter
     * @return lastUpdBy
     */
    public String getLastUpdBy(){
        return this.lastUpdBy;
    }

    /**
     * Address ID setter
     */
    private void setAddressID(int addressID){
        this.addressID=0;
    }

    /**
     * address setter
     */
    private void setAddress(String address){
        this.address=address;
    }
    private void setAddress2(String address2){
        this.address2=address2;
    }

    /**
     * city id setter
     */
    private void setCityID(int cityID){
        this.cityID=0;
    }

    /**
     * postal code setter
     */
    private void setPostalCode(String postalCode){
        this.postalCode=postalCode;
    }

    /**
     * phone number setter
     */
    private void setPhone(String phone){
        this.phone=phone;
    }

    /**
     * created date setter
     */
    private void setCreateDate(Date createDate){
        this.createDate=createDate;
    }

    /**
     * created by setter
     */
    private void setCreatedBy(String createdBy){
        this.createdBy=createdBy;
    }

    /**
     * last updated setter
     */
    private void setLastUpd(Timestamp lastUpd){
        this.lastUpd=lastUpd;
    }

    /**
     * last updated by setter
     */
    private void setLastUpdBy(String lastUpdBy){
        this.lastUpdBy=lastUpdBy;
    }
}

