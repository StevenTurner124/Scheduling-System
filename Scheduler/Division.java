package Scheduler;

/**
 * Constructor for Division Class
 */
public class Division {
    private int divID;
    private String div;
    private int countryID;

    public Division(int divID, String div, int countryID){
        this.divID=divID;
        this.div=div;
        this.countryID=countryID;
    }

    /**
     * divid getter
     * @return divID
     */

    public int getDivID(){

        return divID;
    }

    /**
     * divid setter
     * @param divID
     */

    public void setDivID(int divID){

        this.divID=divID;
    }

    /**
     *  div getter
     * @return  div
     */

    public String getDiv(){

        return div;
    }

    /**
     * div setter
     * @param div
     */

    public void setDiv(String div){

        this.div=div;
    }

    /**
     *  countryID getter
     * @return countryID
     */

    public int getCountryID(){

        return countryID;
    }

    /**
     * countryID setter
     * @param countryID
     */


    public void setCountryID(int countryID) {

        this.countryID = countryID;
    }

}
