package Model;

import java.util.Calendar;

public class City {
     private int cityId;
    private String city;
    private int countryId;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    
    public City() {
        
    }
    
    public City(int cityId, String city, int countryId, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getCityId() {
        return this.cityId;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public int getCountryId() {
        return this.countryId;
    }
    
    public Calendar getCreateDate() {
        return this.createDate;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }
    
    public Calendar getLastUpdate() {
        return this.lastUpdate;
    }
    
    public String getLastUpdateBy() {
        return this.lastUpdateBy;
    }
    
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
