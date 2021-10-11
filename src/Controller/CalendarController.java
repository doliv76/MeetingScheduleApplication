package Controller;

import DAOImplementation.AppointmentsTableRowDAOImpl;
import Model.Appointment;
import Model.AppointmentsTableRow;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utils.DBConnection;

public class CalendarController implements Initializable {
    private static Connection connection;
    private AppointmentsTableRow tableRow;
    private static User user = new User();
    private static Customer customer = new Customer();
    private static Appointment appointment = new Appointment();
    private ObservableList<AppointmentsTableRow> allAppointments = FXCollections.observableArrayList();
    private ObservableList<AppointmentsTableRow> selectedWeekAppointments = FXCollections.observableArrayList();
    private ObservableList<AppointmentsTableRow> selectedMonthAppointments = FXCollections.observableArrayList();
    private ObservableList<AppointmentsTableRow> currentWeekAppointments = FXCollections.observableArrayList();
    private ObservableList<AppointmentsTableRow> currentMonthAppointments = FXCollections.observableArrayList();
    private Tooltip weekTip = new Tooltip("View Calendar by Week");
    private Tooltip monthTip = new Tooltip("View Calendar by Month");
    
    private StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                      DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            @Override
            public String toString(LocalDate date) {
            if(date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
    
            @Override
            public LocalDate fromString(String string) {
            if(!string.isEmpty() && string != null) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
            }
        };   
    
    
    @FXML
    private AnchorPane calendarListPane;
    @FXML
    private AnchorPane calendarWeekPane;
    @FXML
    private AnchorPane selectedWeekDisplayPane;
    @FXML
    private AnchorPane calendarMonthPane;
    @FXML
    private AnchorPane selectedMonthDisplayPane;
    
    
    @FXML
    private TableView<AppointmentsTableRow> calendarTable;
    @FXML
    private TableColumn<AppointmentsTableRow, String> customerName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> userName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> type;
    @FXML
    private TableColumn<AppointmentsTableRow, String> location;
    @FXML
    private TableColumn<AppointmentsTableRow, String> description;
    @FXML
    private TableColumn<AppointmentsTableRow, String> start;
    @FXML
    private TableColumn<AppointmentsTableRow, String> end;
    @FXML
    private TableView<AppointmentsTableRow> currentWeekTable;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekCustomerName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekUserName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekType;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekLocation;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekDescription;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekStart;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentWeekEnd;
    @FXML
    private TableView<AppointmentsTableRow> selectedWeekTable;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekCustomerName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekUserName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekType;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekLocation;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekDescription;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekStart;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedWeekEnd;
    @FXML
    private TableView<AppointmentsTableRow> currentMonthTable;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthCustomerName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthUserName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthType;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthLocation;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthDescription;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthStart;
    @FXML
    private TableColumn<AppointmentsTableRow, String> currentMonthEnd;
    @FXML
    private TableView<AppointmentsTableRow> selectedMonthTable;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthCustomerName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthUserName;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthType;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthLocation;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthDescription;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthStart;
    @FXML
    private TableColumn<AppointmentsTableRow, String> selectedMonthEnd;
    @FXML
    private DatePicker selectedWeekDatePicker;
    @FXML
    private DatePicker selectedMonthDatePicker;
    @FXML
    private Button weekButton;
    @FXML
    private Button monthButton;
    
    @FXML
    private void closeButtonAction(ActionEvent event)  {
        Platform.exit();
    }
    
    @FXML
    private void minimizeButtonAction(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }
    
    @FXML
    private void returnButtonAction(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        Stage mainScreenStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        mainScreenScene.setCursor(new ImageCursor(image));
        mainScreenStage.setScene(mainScreenScene);
        mainScreenStage.show();
    }
    
    @FXML
    private void weekButtonHandler(ActionEvent event) throws IOException, Exception {
        LocalDate now = LocalDate.now();
        Locale locale = Locale.getDefault();
        calendarListPane.setVisible(false);
        calendarWeekPane.setVisible(true);
        selectedWeekTable.setVisible(false);
        selectedWeekDatePicker.setVisible(true);
        monthButton.setDisable(true);
        int week = now.get(WeekFields.of(locale).weekOfWeekBasedYear());
        System.out.println("Week for to compare: " + week);
        initCurrentWeekTable(week, locale);
        selectedWeekDatePicker.setConverter(dateConverter);
        selectedWeekDatePicker.setPromptText("yyyy-MM-dd");
    }
    
    @FXML
    private void monthButtonHandler(ActionEvent event) throws IOException, Exception {
        LocalDate now = LocalDate.now();
        calendarListPane.setVisible(false);
        calendarMonthPane.setVisible(true);
        selectedMonthTable.setVisible(false);
        selectedMonthDatePicker.setVisible(true);
        weekButton.setDisable(true);
        Month month = now.getMonth();
        initCurrentMonthTable(month);
        selectedMonthDatePicker.setConverter(dateConverter);
        selectedMonthDatePicker.setPromptText("yyyy-MM-dd");
    }
    
    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        calendarWeekPane.setVisible(false);
        calendarMonthPane.setVisible(false);
        calendarListPane.setVisible(true);
        weekButton.setDisable(false);
        monthButton.setDisable(false);
    }
    
    @FXML
    private void weekDatePickerHandler(ActionEvent event) throws IOException, Exception {
        LocalDate selected = selectedWeekDatePicker.getValue();
        Locale locale = Locale.getDefault();
        int week = selected.get(WeekFields.of(locale).weekOfWeekBasedYear());
        initSelectedWeekTable(week, locale);
        selectedWeekDatePicker.setVisible((false));
        selectedWeekTable.setVisible(true);
        
    }
    
    @FXML
    private void monthDatePickerHandler(ActionEvent event) throws IOException, Exception {
        LocalDate selected = selectedMonthDatePicker.getValue();
        Locale locale = Locale.getDefault();
        Month month = selected.getMonth();
        System.out.println("Month: " + month.toString());
        initSelectedMonthTable(month);
        selectedMonthDatePicker.setVisible(false);
        selectedMonthTable.setVisible(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        calendarTable.getSelectionModel().clearSelection();
        user = LoginController.getUser();
        initToolTips();
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        selectedWeekCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        selectedWeekUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        selectedWeekDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        selectedWeekLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        selectedWeekType.setCellValueFactory(new PropertyValueFactory<>("type"));
        selectedWeekStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        selectedWeekEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        selectedMonthCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        selectedMonthUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        selectedMonthDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        selectedMonthLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        selectedMonthType.setCellValueFactory(new PropertyValueFactory<>("type"));
        selectedMonthStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        selectedMonthEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        currentWeekCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        currentWeekUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        currentWeekDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        currentWeekLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        currentWeekType.setCellValueFactory(new PropertyValueFactory<>("type"));
        currentWeekStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        currentWeekEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        currentMonthCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        currentMonthUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        currentMonthDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        currentMonthLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        currentMonthType.setCellValueFactory(new PropertyValueFactory<>("type"));
        currentMonthStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        currentMonthEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        try {
            allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        calendarTable.setItems(allAppointments);
        calendarTable.refresh();
    }
    
    private void initToolTips() {
        Tooltip.install(weekButton, weekTip);
        Tooltip.install(monthButton, monthTip);
    }
    
    private void initCurrentWeekTable(int weekNum, Locale locale) throws Exception {
        System.out.println("weekNum :" + weekNum);
        currentWeekAppointments.clear();
        LocalDateTime inputLDT;
        int appWeek = 0;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        for(AppointmentsTableRow app: allAppointments) {
            appStrStart = app.getStart();
            System.out.println("String: " + appStrStart);
            
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            appWeek = inputLDT.get(WeekFields.of(locale).weekOfWeekBasedYear());
            System.out.println("Appointment week number: " + appWeek);
            if(appWeek == weekNum) {
                System.out.println("MATCH");
                currentWeekAppointments.add(app);
            }
            currentWeekTable.setItems(currentWeekAppointments);
            currentWeekTable.refresh();
        }
    }
    
    private void initCurrentMonthTable(Month month) throws Exception{
        currentMonthAppointments.clear();
        LocalDateTime inputLDT;
        Month appMonth;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        for(AppointmentsTableRow app: allAppointments) {
            appStrStart = app.getStart();
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            appMonth = inputLDT.getMonth();
            if(appMonth.compareTo(month) == 0) {
                currentMonthAppointments.add(app);
            }
            currentMonthTable.setItems(currentMonthAppointments);
            currentMonthTable.refresh();
        }
    }
    
    private void initSelectedWeekTable(int weekNum, Locale locale) throws Exception {
        System.out.println("weekNum :" + weekNum);
        selectedWeekAppointments.clear();
        LocalDateTime inputLDT;
        int appWeek = 0;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        for(AppointmentsTableRow app: allAppointments) {
            appStrStart = app.getStart();
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            appWeek = inputLDT.get(WeekFields.of(locale).weekOfWeekBasedYear());
            if(appWeek == weekNum) {
                selectedWeekAppointments.add(app);
            }
            selectedWeekTable.setItems(selectedWeekAppointments);
            selectedWeekTable.refresh();
        }
        
    }
    
    private void initSelectedMonthTable(Month month) throws Exception {
        selectedMonthAppointments.clear();
        LocalDateTime inputLDT;
        Month appMonth;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        for(AppointmentsTableRow app: allAppointments) {
            appStrStart = app.getStart();
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            appMonth = inputLDT.getMonth();
            if(appMonth.compareTo(month) == 0) {
                selectedMonthAppointments.add(app);
            }
            selectedMonthTable.setItems(selectedMonthAppointments);
            selectedMonthTable.refresh();
        }
    }
}
