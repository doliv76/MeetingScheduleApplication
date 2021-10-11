package Model;

public class CustomersTableRow {
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String city;
    private String country;
    
    public CustomersTableRow(){}
    public CustomersTableRow(String customerName, String address, String postalCode, String phone, String city, String country) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }
    
    public String getCustomerName() {
        return this.customerName;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
}
