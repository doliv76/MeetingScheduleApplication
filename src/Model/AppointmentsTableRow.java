package Model;

public class AppointmentsTableRow {
    private String customerName;
    private String userName;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    
    public AppointmentsTableRow(){}
    public AppointmentsTableRow(String customerName, String userName, String description, String location, String type, String start, String end) {
        this.customerName = customerName;
        this.userName = userName;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }
    
    public String getCustomerName() {
        return this.customerName;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getStart() {
        return this.start;
    }
    
    public String getEnd() {
        return this.end;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public void setConsultantName(String userName) {
        this.userName = userName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setStart(String start) {
        this.start = start;
    }
    
    public void setEnd(String end) {
        this.end = end;
    }
}
