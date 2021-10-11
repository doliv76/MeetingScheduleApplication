package Model;

/**
 *
 * @author upont
 */
public class ReportTableRow {
    private String type;
    private int appointmentNumber;
    
    public ReportTableRow(){}
    public ReportTableRow(String type, int appointmentNumber) {
        this.type = type;
        this.appointmentNumber = appointmentNumber;
    }
    
    public String getType() {
        return this.type;
    }
    
    public int getAppointmentNumber() {
        return this.appointmentNumber;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setAppointmentNumber(int appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }
    
    @Override
    public String toString() {
        return String.format("Type: " + this.getType() + "Frequency: " + this.getAppointmentNumber());
    }
}
