package Model;

import java.util.Calendar;

public class Address {
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    
    public Address() {
        
    }
    
    public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getAddressId() {
        return this.addressId;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String getAddress2() {
        return this.address2;
    }
    
    public int getCityId() {
        return this.cityId;
    }
    
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public String getPhone() {
        return this.phone;
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
    
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
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
