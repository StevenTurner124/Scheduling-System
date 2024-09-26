package Scheduler;

/**
 * Constructor
 */
public class Customer {

    private int custID;
    private String custName;
    private String address;

    private String postCode;
    private String phoneNum;
    private String country;;
    private int divID;
    private String div;
    private String age;
    private int vip;




    public Customer(int custID,
                    String custName,
                    String address,
                    String postCode,
                    String phoneNum,
                    String div,
                    int divID,
                    String country,
                    String age,
                    int vip){
        this.custID=custID;
        this.custName=custName;
        this.address=address;
        this.postCode=postCode;
        this.phoneNum=phoneNum;
        this.div=div;
        this.divID=divID;
        this.country=country;
        this.age=age;
        this.vip=vip;
    }


    /**
     * Getter for CustID
     * @return custID
     */

    public int getCustID(){
        return custID;
    }

    /**
     * Getter for CustName
     * @return custName
     */

    public String getCustName(){
        return custName;
    }

    /**
     * Getter for Address
     * @return address
     */

    public String getAddress(){
        return address;
    }

    /**
     * Getter for PostCode
     * @return postCode
     */

    public String getPostCode(){
        return postCode;
    }

    /**
     *Getter for phoneNum
     * @return phoneNum
     */

    public String getPhoneNum(){
        return phoneNum;
    }

    /**
     *  Getter for div
     * @return div
     */

    public String getDiv(){
        return div;
    }

    /**
     * Getter for divID
     * @return divID
     */

    public int getDivID(){
        return divID;
    }

    /**
     * Getter for country
     * @return country
     */

    public String getCountry(){
        return country;
    }

    /**
     * Setter for CustName
     * @param custID
     */

    public void setCustID(int custID){
        this.custID=custID;
    }

    /**
     * Setter for CustName
     * @param custName
     */

    public void setCustName(String custName){
        this.custName=custName;
    }

    /**
     * Setter for Address
     * @param address
     */



    public void setAddress(String address) {
        this.address = address;
    }

    /** Setter for PostCode
     * @param postCode
     *
     */


    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Setter for PhoneNum
     * @param phoneNum
     */


    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     *  Setter for Div
     * @param div
     */


    public void setDiv(String div) {
        this.div = div;
    }

    /**
     *  setter for DivID
     * @param divID
     */


    public void setDivID(int divID) {
        this.divID = divID;
    }

    /**
     * setter for country
     * @param country
     */


    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public void setVip(int vip){this.vip=vip;}
    public int getVip(){return vip;}
}
