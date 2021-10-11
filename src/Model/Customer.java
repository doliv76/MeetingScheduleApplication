package Model;

import java.util.Calendar;

public class Customer {
    private int customerId;
    private String customerName;
    private int addressId;
    private boolean active;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    
    public Customer() {
        
    }
    
    public Customer(int customerId, String customerName, int addressId, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getCustomerId() {
        return this.customerId;
    }
    
    public String getCustomerName() {
        return this.customerName;
    }
    
    public int getAddressId() {
        return this.addressId;
    }
    
    public boolean getActive() {
        return this.active;
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
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public void setActive(boolean active) {
        this.active = active;
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
