package DAOImplementation;

import Model.AppointmentsTableRow;
import Model.ReportTableRow;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReportTableRowDAOImpl {
    public static ObservableList<ReportTableRow> getAllTableRows(Connection connection) throws SQLException, Exception {
        Month month = now().getMonth();
        System.out.println("Current month: " + month.toString());
        List<String> typeList = new ArrayList();
        Map<String, Integer> typeFreqMap = new HashMap<>();
        ObservableList<AppointmentsTableRow> appTableRows = FXCollections.observableArrayList();
        ObservableList<AppointmentsTableRow> temp = FXCollections.observableArrayList();
        ObservableList<ReportTableRow> reportTableRows = FXCollections.observableArrayList();
        temp = AppointmentsTableRowDAOImpl.getAllTableRows(connection);

        //Filter all appointments in temp by current month
        LocalDateTime inputLDT;
        Month appMonth;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        for(AppointmentsTableRow app: temp) {
            appStrStart = app.getStart();
            System.out.println("String: " + appStrStart);
            
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            appMonth = inputLDT.getMonth();
            System.out.println("Appointment month: " + appMonth);
            if(appMonth == month) {
                System.out.println("MATCH");
                appTableRows.add(app);
            }
        }
        
        
        for(AppointmentsTableRow apps: appTableRows ) {
            typeList.add(apps.getType());
        }
        getAppointmentTypeFrequencyMap(typeList, typeFreqMap);
        populateReportList(reportTableRows, typeFreqMap);
        
        return reportTableRows;
    }
    
    public static ObservableList<ReportTableRow> getUserTableRows(Connection connection, int userId) throws SQLException, Exception {
        List<String> typeList = new ArrayList();
        Map<String, Integer> typeFreqMap = new HashMap<>();
        ObservableList<AppointmentsTableRow> appTableRows = FXCollections.observableArrayList();
        ObservableList<ReportTableRow> reportTableRows = FXCollections.observableArrayList();
        appTableRows = AppointmentsTableRowDAOImpl.getUserTableRows(connection, userId);
        
        if(!appTableRows.isEmpty()) {
            for(AppointmentsTableRow apps: appTableRows ) {
                typeList.add(apps.getType());
            }
            getAppointmentTypeFrequencyMap(typeList, typeFreqMap);
            populateReportList(reportTableRows, typeFreqMap);
        }
        return reportTableRows;
    }
    
    //Justification: a two lambda functions are used in this method to expediate the 
    //creation of a map of key, value pairs that sets unique Appointment types
    //as the map keys and the frequency of each appointment type in the system as the value.
    //This map is them used to create a report table of the frequencies of each type of Appointment.
    //The first lambda passes each type from the typeList to the Map.compute method.
    //The second lambda is used to compare the incoming type from the typeList to the key, value pairs stored 
    //in the frequency map. If the type is a new Appointment type, the method sets that type as a new key and sets its 
    //value to one. If the type is an existing key in the map, the method increments the value of that key by 1.
    private static Map<String, Integer> getAppointmentTypeFrequencyMap(List<String> typeList, Map<String, Integer> typeFreqMap) {
        typeList.forEach( type ->
            typeFreqMap.compute(type, (key, value) ->
                value != null ? value + 1 : 1));
        return typeFreqMap;
    }
    
    private static ObservableList<ReportTableRow> populateReportList(ObservableList<ReportTableRow> reportTableRows, Map<String, Integer> typeFreqMap) {
        Iterator iter = typeFreqMap.entrySet().iterator();
        ObservableList<ReportTableRow> tempList = FXCollections.observableArrayList();
        while (iter.hasNext()) {
            ReportTableRow temp = new ReportTableRow();
            Map.Entry report = (Map.Entry) iter.next();
            temp.setType(report.getKey().toString());
            temp.setAppointmentNumber(Integer.parseInt(report.getValue().toString()));
            tempList.add(temp);
        }
        reportTableRows.addAll(tempList);
        return reportTableRows;
    }
}
