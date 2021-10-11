package Model;

import java.util.Calendar;

/**
 *
 * @author Donald Oliver
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private boolean active;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    
    public User() {
    }
    
    public User(int userId, String userName, String password, boolean active, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        setUserId(userId);
        setUserName(userName);
        setPassword(password);
        this.active = active;
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdateBy(lastUpdateBy);
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getPassword() {
        return this.password;
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
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setActive() {
        this.active = !this.active;
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
