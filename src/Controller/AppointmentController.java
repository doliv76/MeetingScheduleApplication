package Controller;

import DAOImplementation.AppointmentDAOImpl;
import DAOImplementation.AppointmentsTableRowDAOImpl;
import DAOImplementation.CustomerDAOImpl;
import DAOImplementation.UserDAOImpl;
import Model.Appointment;
import Model.AppointmentsTableRow;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DBConnection;

public class AppointmentController implements Initializable {

    private static Connection connection;
    private AppointmentsTableRow tableRow = new AppointmentsTableRow();
    private static User user;
    private static Customer customer = new Customer();
    private static Appointment appointment = new Appointment();
    private ObservableList<AppointmentsTableRow> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private Tooltip createTip = new Tooltip("Create Appointment");
    private Tooltip updateTip = new Tooltip("Update Appointment");
    private Tooltip deleteTip = new Tooltip("Delete Appointment");

    @FXML
    private AnchorPane createAppointmentPane;
    @FXML
    private AnchorPane appointmentListPane;
    @FXML
    private AnchorPane updateAppointmentPane;
    @FXML
    private ComboBox<String> createUserComboBox;
    @FXML
    private ComboBox<String> updateUserComboBox;
    @FXML
    private ComboBox<String> createCustomerComboBox;
    @FXML
    private ComboBox<String> updateCustomerComboBox;
    @FXML
    private TableView<AppointmentsTableRow> appointmentsTable;
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
    private Button createAppointmentButton;
    @FXML
    private Button updateAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelDeleteButton;
    @FXML
    private TextField createTitleField;
    @FXML
    private TextArea createDescriptionField;
    @FXML
    private TextField createLocationField;
    @FXML
    private TextField createStartField;
    @FXML
    private TextField createTypeField;
    @FXML
    private TextField createEndField;
    @FXML
    private TextField createContactField;
    @FXML
    private TextField updateTitleField;
    @FXML
    private TextArea updateDescriptionField;
    @FXML
    private TextField updateLocationField;
    @FXML
    private TextField updateStartField;
    @FXML
    private TextField updateTypeField;
    @FXML
    private TextField updateEndField;
    @FXML
    private TextField updateContactField;

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void minimizeButtonAction(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void returnButtonAction(ActionEvent event) throws IOException {
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        mainScreenScene.setCursor(new ImageCursor(image));
        mainScreenStage.setScene(mainScreenScene);
        mainScreenStage.show();
    }

    @FXML
    private void createAppointmentHandler(ActionEvent event) throws IOException, SQLException, Exception {
        initUserBox(allUsers);
        initCustomerBox(allCustomers);
        appointmentListPane.setVisible(false);
        createAppointmentPane.setVisible(true);
        updateAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
    }

    @FXML
    private void updateAppointmentHandler(ActionEvent event) throws IOException, SQLException, Exception {
        tableRow = appointmentsTable.getSelectionModel().getSelectedItem();
        System.out.println("Appointment Type: " + appointment.getType() + " Appointment Start: " + appointment.getStart());
        System.out.println("TableRow Type: " + tableRow.getType() + " TableRow Start: " + tableRow.getStart());
        if (tableRow.getCustomerName() == null) {
            System.out.println("select a appointment to update...");
            tableRow = appointmentsTable.getSelectionModel().getSelectedItem();
        }

        initUserBox(allUsers);
        initCustomerBox(allCustomers);
        populateUpdateFields();
        tableRow = appointmentsTable.getSelectionModel().getSelectedItem();
        appointmentListPane.setVisible(false);
        createAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
        updateAppointmentPane.setVisible(true);
    }

    @FXML
    private void deleteAppointmentHandler(ActionEvent event) throws IOException {
        tableRow = appointmentsTable.getSelectionModel().getSelectedItem();
        createAppointmentButton.setDisable(true);
        updateAppointmentButton.setDisable(true);
        deleteButton.setDisable(false);
        cancelDeleteButton.setDisable(false);
    }

    @FXML
    private void createButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        createAppointmentPane.setVisible(false);
        appointmentListPane.setVisible(true);
        updateAppointmentButton.setDisable(false);
        deleteAppointmentButton.setDisable(false);
        createAppointment();
        clearCreateFields();
        initUserBox(allUsers);
        initCustomerBox(allCustomers);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        createAppointmentPane.setVisible(false);
        updateAppointmentPane.setVisible(false);
        appointmentListPane.setVisible(true);
        deleteButton.setDisable(true);
        cancelDeleteButton.setDisable(true);
        updateAppointmentButton.setDisable(false);
        deleteAppointmentButton.setDisable(false);
        createAppointmentButton.setDisable(false);
        initUserBox(allUsers);
        initCustomerBox(allCustomers);

    }

    @FXML
    private void deleteButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        updateAppointmentButton.setDisable(false);
        deleteAppointmentButton.setDisable(false);
        createAppointmentButton.setDisable(false);
        cancelDeleteButton.setDisable(true);
        deleteButton.setDisable(true);
        deleteAppointment();
    }

    @FXML
    private void updateButtonHandler(ActionEvent event) throws IOException, SQLException, Exception {
        updateAppointmentPane.setVisible(false);
        createAppointmentPane.setVisible(false);
        appointmentListPane.setVisible(true);
        updateAppointmentButton.setDisable(false);
        deleteAppointmentButton.setDisable(false);
        createAppointmentButton.setDisable(false);
        updateAppointment();
        clearUpdateFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        appointmentsTable.getSelectionModel().clearSelection();
        initToolTips();
        user = LoginController.getUser();
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));

        try {
            allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        appointmentsTable.setItems(allAppointments);
        appointmentsTable.refresh();
    }

    private void clearCreateFields() {
        createTitleField.clear();
        createDescriptionField.clear();
        createLocationField.clear();
        createStartField.clear();
        createTypeField.clear();
        createEndField.clear();
        createContactField.clear();
    }

    private void clearUpdateFields() {
        updateTitleField.clear();
        updateDescriptionField.clear();
        updateLocationField.clear();
        updateStartField.clear();
        updateTypeField.clear();
        updateEndField.clear();
        updateContactField.clear();
    }

    private void populateUpdateFields() throws SQLException, Exception {
        String type = tableRow.getType();
        String start = tableRow.getStart();
        int index = 0;
        appointment = new Appointment();
        appointment = AppointmentDAOImpl.getAppointmentByTypeAndStart(tableRow.getType(), convertToUTC(tableRow.getStart()), connection);
        customer = new Customer();
        customer = CustomerDAOImpl.getCustomerByName(tableRow.getCustomerName(), connection);
        user = new User();
        user = UserDAOImpl.getUserByName(tableRow.getUserName(), connection);
        
        for (Customer cus : allCustomers) {
            if (cus.getCustomerId() == customer.getCustomerId()) {
                index = allCustomers.indexOf(cus);
            }
        }
        updateCustomerComboBox.getSelectionModel().select(index);

        for (User use : allUsers) {
            if (use.getUserId() == user.getUserId()) {
                index = allUsers.indexOf(use);
            }
        }
        updateUserComboBox.getSelectionModel().select(index);

        System.out.println("Start..........." + start);
        updateTitleField.setText(appointment.getTitle());
        updateDescriptionField.setText(appointment.getDescription());
        updateLocationField.setText(appointment.getLocation());
        updateStartField.setText(convertToLocal(appointment.getStart()));
        updateTypeField.setText(appointment.getType());
        updateEndField.setText(convertToLocal(appointment.getEnd()));
        updateContactField.setText(appointment.getContact());
    }

    private void initToolTips() {
        Tooltip.install(createAppointmentButton, createTip);
        Tooltip.install(updateAppointmentButton, updateTip);
        Tooltip.install(deleteAppointmentButton, deleteTip);
    }

    private void initUserBox(ObservableList<User> user) throws SQLException, Exception {
        user.clear();
        user.addAll(UserDAOImpl.getAllUsers(connection));
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (User us : user) {
            userNames.add(us.getUserName());
        }
        createUserComboBox.setItems(userNames);
        updateUserComboBox.setItems(userNames);
        createUserComboBox.getSelectionModel().clearSelection();
        updateUserComboBox.getSelectionModel().clearSelection();
    }

    private void initCustomerBox(ObservableList<Customer> customer) throws SQLException, Exception {
        customer.clear();
        customer.addAll(CustomerDAOImpl.getAllCustomers(connection));
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer cus : customer) {
            customerNames.add(cus.getCustomerName());
        }
        createCustomerComboBox.setItems(customerNames);
        updateCustomerComboBox.setItems(customerNames);
        createCustomerComboBox.getSelectionModel().clearSelection();
        updateCustomerComboBox.getSelectionModel().clearSelection();

    }

    private void createAppointment() throws SQLException, Exception {
        appointment = new Appointment();
        String locCustomerName = createCustomerComboBox.getValue().toString();
        String locUserName = createUserComboBox.getValue().toString();
        customer = CustomerDAOImpl.getCustomerByName(locCustomerName, connection);
        user = UserDAOImpl.getUserByName(locUserName, connection);
        appointment.setCustomertId(customer.getCustomerId());
        appointment.setUserId(user.getUserId());
        appointment.setTitle(createTitleField.getText());
        appointment.setDescription(createDescriptionField.getText());
        appointment.setLocation(createLocationField.getText());
        appointment.setContact(createContactField.getText());
        appointment.setType(createTypeField.getText());
        appointment.setStart(createStartField.getText());
        appointment.setEnd(createEndField.getText());

        //Create input validation for the start and end time input
        //to ensure it parses to a valid date time
        //Need to implement a method to call here to do the conflict checking 
        //for scheduling a consultant during a given period of time
        if (!checkDuringBusinessHours(appointment, "yyyy-MM-dd HH:mm:ss")) {
            dialogueHandler(2);
        } else if (!checkAppointmentConflicts(appointment, 0)) {
            AppointmentDAOImpl.createAppointment(appointment, user, customer, connection);
            allAppointments.clear();
            allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
            appointmentsTable.setItems(allAppointments);
            appointmentsTable.refresh();
            clearFields();
        } else {
            dialogueHandler(0);
        }
    }

    private void updateAppointment() throws SQLException, Exception {
        int appId = appointment.getAppointmentId();
        String appointmentUrl = appointment.getUrl();
        appointment = new Appointment();
        String locCustomerName = updateCustomerComboBox.getValue().toString();
        String locUserName = updateUserComboBox.getValue().toString();
        customer = CustomerDAOImpl.getCustomerByName(locCustomerName, connection);
        user = UserDAOImpl.getUserByName(locUserName, connection);
        appointment.setAppointmentId(appId);
        appointment.setCustomertId(customer.getCustomerId());
        appointment.setUserId(user.getUserId());
        appointment.setTitle(updateTitleField.getText());
        appointment.setDescription(updateDescriptionField.getText());
        appointment.setLocation(updateLocationField.getText());
        appointment.setContact(updateContactField.getText());
        appointment.setType(updateTypeField.getText());
        appointment.setUrl(appointmentUrl);
        appointment.setStart(updateStartField.getText());
        appointment.setEnd(updateEndField.getText());

        if (!checkDuringBusinessHours(appointment, "yyyy-MM-dd HH:mm:ss.s")) {
            dialogueHandler(3);
        } else if (!checkAppointmentConflicts(appointment, 1)) {
            AppointmentDAOImpl.updateAppointment(appointment, customer, user, connection);
            allAppointments.clear();
            allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
            appointmentsTable.setItems(allAppointments);
            appointmentsTable.refresh();
        } else {
            dialogueHandler(1);
        }
    }

    private void deleteAppointment() throws SQLException, Exception {
        String selectedAppointmentType = appointmentsTable.getSelectionModel().getSelectedItem().getType();
        String selectedAppointmentStart = convertToUTC(appointmentsTable.getSelectionModel().getSelectedItem().getStart());
        System.out.println("SelectedAppointmentStart: " + selectedAppointmentStart);
        appointment = new Appointment();
        appointment = AppointmentDAOImpl.getAppointmentByTypeAndStart(selectedAppointmentType, selectedAppointmentStart, connection);
        AppointmentDAOImpl.deleteAppointment(appointment, connection);
        allAppointments.clear();
        allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
        appointmentsTable.setItems(allAppointments);
        appointmentsTable.refresh();
    }

    private static String convertToUTC(String resultSetString) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        LocalDateTime inputLDT, convLDT;

        inputLDT = LocalDateTime.parse(resultSetString, dFor);

        ZonedDateTime inputZDT = inputLDT.atZone(zoneId);

        OffsetDateTime inputODT = inputZDT.withZoneSameInstant(ZoneId.of("UTC")).toOffsetDateTime();

        convLDT = inputODT.toLocalDateTime();

        resultSetString = dFor.format(convLDT);

        return resultSetString;
    }

    private static String convertToLocal(String resultSetString) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
        LocalDateTime inputLDT, convLDT;

        //Parse ResultSet String into LocalDateTime object accordint to dFor DateTimeFormatter Pattern
        inputLDT = LocalDateTime.parse(resultSetString, dFor);

        //Create ZonedDateTime object to convert inputLDT to a ZDT to store UTC DATETIME info from DB
        ZonedDateTime inputZDT = inputLDT.atZone(ZoneOffset.UTC);

        //Create OffsetDateTime to offset the DateTime to system local for displaying in the UI
        OffsetDateTime inputODT = inputZDT.withZoneSameInstant(ZoneId.of(zoneId.getId())).toOffsetDateTime();

        //Convert inputODT to a LocalDateTime to format back into a string displayable by the UI
        convLDT = inputODT.toLocalDateTime();

        //Format TimeZone-adjust convLDT to the resultSetString for passing back to the caller
        resultSetString = dFor.format(convLDT);

        return resultSetString;
    }

    private void clearFields() {
        createTitleField.clear();
        createDescriptionField.clear();
        createLocationField.clear();
        createContactField.clear();
        createStartField.clear();
        createEndField.clear();
        createTypeField.clear();

    }
    private boolean checkDuringBusinessHours(Appointment newAppointment, String formatter) {
        //Business hours are from 08:00 to 17:30, but no appointments are
        //scheduled to start within the first or last 15 minutes of the business day
        LocalTime open, close;
        open = LocalTime.of(8,15);
        close = LocalTime.of(17, 15);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formatter);
        String appStrStart = newAppointment.getStart();
        String appStrEnd = newAppointment.getEnd();
        LocalDateTime inputStartLDT = LocalDateTime.parse(appStrStart, dateFormatter);
        LocalDateTime inputEndLDT = LocalDateTime.parse(appStrEnd, dateFormatter);
        LocalTime inputStartTime = inputStartLDT.toLocalTime();
        LocalTime inputEndTime = inputEndLDT.toLocalTime();
        if((open.isBefore(inputStartTime) || open.equals(inputStartTime))
                && (close.isAfter(inputEndTime) || close.equals(inputEndTime))) {
            return true;
        }
        return false;
    }

    //Check create/update Appointment start/end time conflicts
    private boolean checkAppointmentConflicts(Appointment newAppointment, int checkFlag) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        LocalDateTime inputStartLDT, inputEndLDT, newAppStartLDT, newAppEndLDT;
        LocalTime inputStartTime, inputEndTime, newStartTime, newEndTime;
        LocalDate inputStartDate, inputEndDate, newStartDate, newEndDate;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String appStrStart, newStart, appStrEnd, newEnd;
        newStart = newAppointment.getStart();
        newEnd = newAppointment.getEnd();

        try {
            appointmentList = AppointmentDAOImpl.getAllAppointments(connection);
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        switch (checkFlag) {
            case 0: //Create Appointment
                newAppStartLDT = LocalDateTime.parse(newStart, dateFormatter);
                newAppEndLDT = LocalDateTime.parse(newEnd, dateFormatter);
                newStartTime = newAppStartLDT.toLocalTime();
                newEndTime = newAppEndLDT.toLocalTime();
                newStartDate = newAppStartLDT.toLocalDate();
                newEndDate = newAppEndLDT.toLocalDate();
                for (Appointment app : appointmentList) {
                    dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
                    appStrStart = app.getStart();
                    appStrEnd = app.getEnd();
                    inputStartLDT = LocalDateTime.parse(appStrStart, dateFormatter);
                    inputEndLDT = LocalDateTime.parse(appStrEnd, dateFormatter);
                    inputStartDate = inputStartLDT.toLocalDate();
                    inputEndDate = inputEndLDT.toLocalDate();
                    inputStartTime = inputStartLDT.toLocalTime();
                    inputEndTime = inputEndLDT.toLocalTime();

                    if (app.getUserId() == newAppointment.getUserId()) {
                        System.out.println("User match...");
                        if ((newStartDate.equals(inputStartDate)) && (newEndDate.equals(inputEndDate))) {
                            System.out.println("Appointments on same day");
                            if (inputStartTime.isBefore(newStartTime) && inputEndTime.isAfter(newEndTime)) {
                                System.out.println("Inclusive appointment conflict");
                                return true;
                            } else if (inputStartTime.isAfter(newStartTime) && inputEndTime.isAfter(newEndTime)) {
                                System.out.println("Exclusive left appointment conflict");
                                return true;
                            } else if (inputStartTime.isBefore(newStartTime) && inputEndTime.isBefore(newEndTime)) {
                                System.out.println("Exclusive right appointment conflict");
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
                break;

            case 1: //Update Appointment
                dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
                newAppStartLDT = LocalDateTime.parse(newStart, dateFormatter);
                newAppEndLDT = LocalDateTime.parse(newEnd, dateFormatter);
                newStartTime = newAppStartLDT.toLocalTime();
                newEndTime = newAppEndLDT.toLocalTime();
                newStartDate = newAppStartLDT.toLocalDate();
                newEndDate = newAppEndLDT.toLocalDate();
                for (Appointment app : appointmentList) {
                    appStrStart = app.getStart();
                    appStrEnd = app.getEnd();
                    inputStartLDT = LocalDateTime.parse(appStrStart, dateFormatter);
                    inputEndLDT = LocalDateTime.parse(appStrEnd, dateFormatter);
                    inputStartDate = inputStartLDT.toLocalDate();
                    inputEndDate = inputEndLDT.toLocalDate();
                    inputStartTime = inputStartLDT.toLocalTime();
                    inputEndTime = inputEndLDT.toLocalTime();

                    if (app.getUserId() == newAppointment.getUserId()) {
                        System.out.println("User match...");
                        if (app.getAppointmentId() == newAppointment.getAppointmentId()) {
                            //do nothing
                        } else {
                            System.out.println("Checking for conflicts...");
                            if ((newStartDate.equals(inputStartDate)) && (newEndDate.equals(inputEndDate))) {
                                System.out.println("Appointments on same day");
                                if (inputStartTime.isBefore(newStartTime) && inputEndTime.isAfter(newEndTime)) {
                                    System.out.println("Inclusive appointment conflict");
                                    return true;
                                } else if (inputStartTime.isAfter(newStartTime) && inputEndTime.isAfter(newEndTime)) {
                                    System.out.println("Exclusive left appointment conflict");
                                    return true;
                                } else if (inputStartTime.isBefore(newStartTime) && inputEndTime.isBefore(newEndTime)) {
                                    System.out.println("Exclusive right appointment conflict");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
        }
        return false;
    }
    
    private boolean dialogueHandler(int code) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result;
        switch (code) {
            case 0: //Create Appointment Conflict Message
                alert.setTitle("Create Appointment Conflict");
                alert.setHeaderText("The appointment being created conflicted with the start"
                        + " and/or end time of another, existent appointment. Returning the the Appointment"
                        + " Screen without creating a new Appointment...");
                alert.setContentText("Click Ok to confirm");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 1: //Update Appointment Conflict Message
                alert.setTitle("Update Appointment Conflict");
                alert.setHeaderText("The Appointment being updated conflicted with the start"
                        + " and/or end time of another, existent Appointment. Returning to the Appointment"
                        + " Screen with all changes discarded...");
                alert.setContentText("Click Ok to confirm");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 2: //Create Appointment outside of business hours message
                alert.setTitle("Create Appointment Conflict");
                alert.setHeaderText("The Appointment being created had start or end times outside of the"
                        + " normal operating hours: 08:15 and 17:15. Returning to the Appointment Screen"
                        + " without creating a new Appointment...");
                alert.setContentText("Click Ok to confirm");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
            case 3: //Update Appointment outside of business hours message
                alert.setTitle("Update Appointment Conflict");
                alert.setHeaderText("The Appointment being updated had start or end times outside of the"
                        + " normal operating hours: 08:15 and 17:15. Returning to the Appointment Screen"
                        + " with all changes discarded...");
                alert.setContentText("Click Ok to confirm");
                result = alert.showAndWait();
                return result.get() == ButtonType.OK;
        }
        return true;
    }
}
