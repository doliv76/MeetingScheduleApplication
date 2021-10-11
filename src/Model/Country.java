package Model;

import java.util.Calendar;

public class Country {
   private int countryId;
   private String country;
   private Calendar createDate;
   private String createdBy;
   private Calendar lastUpdate;
   private String lastUpdateBy;
   
   public Country() {}
   public Country(int countryId, String country, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
       this.countryId = countryId;
       this.country = country;
       this.createDate = createDate;
       this.createdBy = createdBy;
       this.lastUpdate = lastUpdate;
       this.lastUpdateBy = lastUpdateBy;
   }
   
   public int getCountryId() {
       return this.countryId;
   }
   
   public String getCountry() {
       return this.country;
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
   
   public void setCountryId(int countryId) {
       this.countryId = countryId;
   }
   
   public void setCountry(String country) {
       this.country = country;
   }
   
   public void setCreateDate(Calendar createDate) {
       this.createDate = createDate;
   }
   
   public void setCreatedBy(String createdBy) {
       this.createdBy = createdBy;
   }
    public void setLastupdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
