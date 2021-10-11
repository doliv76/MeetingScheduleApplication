package Controller;

import DAOImplementation.AppointmentsTableRowDAOImpl;
import DAOImplementation.ReportTableRowDAOImpl;
import DAOImplementation.UserDAOImpl;
import Model.Appointment;
import Model.AppointmentsTableRow;
import Model.ReportTableRow;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.DBConnection;

/**
 *
 * @author upont
 */
public class ReportController implements Initializable {
    private static Connection connection;
    private AppointmentsTableRow tableRow;
    private static User user;
    private static Appointment appointment = new Appointment();
    private ObservableList<AppointmentsTableRow> allAppointments = FXCollections.observableArrayList();
    private ObservableList<ReportTableRow> typeReport = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    
    //Appointments by User
    @FXML
    private TableView<AppointmentsTableRow> appByUserTable;
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
    
    //Current Month Appointment Totals By Type
    @FXML
    private TableView<ReportTableRow> monthTotalsByTypeTable;
    @FXML
    private TableColumn<ReportTableRow, String> monthAppointmentType;
    @FXML
    private TableColumn<ReportTableRow, Integer> monthFrequency;
    
    //User Appointment Totals By Type
    @FXML
    private TableView<ReportTableRow> userTypeTable;
    @FXML
    private TableColumn<ReportTableRow, String> userAppType;
    @FXML
    private TableColumn<ReportTableRow, Integer> userFrequency;
    
    @FXML
    private ComboBox<String> userAppointmentComboBox;
    
    @FXML
    private void closeButtonAction(ActionEvent event) {
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
    private void initUserTables(ActionEvent event) throws IOException, SQLException, Exception {
        allAppointments = FXCollections.observableArrayList();
        typeReport = FXCollections.observableArrayList();
        String userName = userAppointmentComboBox.getValue();
        System.out.println(userName);
        user = UserDAOImpl.getUserByName(userName, connection);
        int userId = user.getUserId();
        System.out.println("UserId: " + userId);
        allAppointments = AppointmentsTableRowDAOImpl.getUserTableRows(connection, userId);
        
        //Prevent null pointer exception when allAppointments has no user appointments
        try {
            typeReport = ReportTableRowDAOImpl.getUserTableRows(connection, userId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        appByUserTable.setItems(allAppointments);
        appByUserTable.refresh();
        userTypeTable.setItems(typeReport);
        userTypeTable.refresh();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        user = LoginController.getUser();
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        monthAppointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthFrequency.setCellValueFactory(new PropertyValueFactory<>("appointmentNumber"));
        userAppType.setCellValueFactory(new PropertyValueFactory<>("type"));
        userFrequency.setCellValueFactory(new PropertyValueFactory<>("appointmentNumber"));
        
        try {
            initUserBox(allUsers);
            allAppointments.addAll(AppointmentsTableRowDAOImpl.getAllTableRows(connection));
            typeReport.addAll(ReportTableRowDAOImpl.getAllTableRows(connection));
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        monthTotalsByTypeTable.setItems(typeReport);
        monthTotalsByTypeTable.refresh();
    }
    
    private void initUserBox(ObservableList<User> user) throws SQLException, Exception {
        user.clear();
        user.addAll(UserDAOImpl.getAllUsers(connection));
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for(User us: user) {
            userNames.add(us.getUserName());
        }
        System.out.println(userNames.toString());
        userAppointmentComboBox.setItems(userNames);
    }
    
}
