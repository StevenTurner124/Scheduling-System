package Scheduler;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.util.Date;

/**
 * creates City and its contents
 */
public class City {
    private IntegerProperty cityID;
    private StringProperty city;
    private IntegerProperty countryID;
    private DateFormat createDate;
    private StringProperty createdBy;
    private DateFormat lastUpd;
    private StringProperty lastUpdBy;

    City(int cityID, String city, int countryID, Date createDate, String createdBy, Date lastUpd, String lastUpdBy){
        this.cityID= new SimpleIntegerProperty(cityID);
        this.city= new SimpleStringProperty(city);
        this.countryID= new SimpleIntegerProperty(countryID);
        //this.createDate= new DateFormat(createDate);//
        this.createdBy= new SimpleStringProperty(createdBy);
        this.lastUpdBy=new SimpleStringProperty(lastUpdBy);


    }

    /**
     * call for getting city id
     */
    public IntegerProperty getCityID(){
        return cityID;
    }


}
