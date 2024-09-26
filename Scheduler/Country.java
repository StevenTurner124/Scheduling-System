package Scheduler;

/**
 * Construct Country
 */

public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country){
        this.country=country;
        this.countryID=countryID;
    }

    /**
     * Country ID getter
     * @return countryID
     */

    public int getCountryID(){
        return countryID;
    }

    /**
     * Country getter
     * @return country
     */

    public String getCountry(){
        return country;
    }

    /**
     * Setter for Country ID
     * @param countryID
     */

    public void setCountryID(int countryID){
        this.countryID=countryID;
    }

    /**
     * setter for Country
     * @param country
     */

    public void setCountry(String country){
        this.country=country;
    }
}