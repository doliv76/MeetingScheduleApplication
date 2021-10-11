package Model;

import java.util.Calendar;

public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String start;
    private String end;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    
    public Appointment() {}
    public Appointment(int appointmentId, int customerId, int userId, String title, String description,
            String location, String contact, String type, String url, String start, String end,
            Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getAppointmentId() {
        return this.appointmentId;
    }
    
    public int getCustomertId() {
        return this.customerId;
    }
    public int getUserId() {
        return this.userId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
                
    public String getLocation() {
        return this.location;
    }
                
    public String getContact() {
        return this.contact;
    }
                
    public String getType() {
        return this.type;
    }
                
    public String getUrl() {
        return this.url;
    }
    
    public String getStart() {
        return this.start;
    }
    
    public String getEnd() {
        return this.end;
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
    
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public void setCustomertId(int customerId) {
        this.customerId = customerId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
                
    public void setLocation(String location) {
        this.location = location;
    }
                
    public void setContact(String contact) {
        this.contact = contact;
    }
                
    public void setType(String type) {
        this.type = type;
    }
                
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setStart(String start) {
        this.start = start;
    }
    
    public void setEnd(String end) {
        this.end = end;
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
