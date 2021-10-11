/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.DBConnection;

/**
 *
 * @author upont
 */
public class HomeController implements Initializable {
    private static Connection connection;
    private Tooltip customersTip = new Tooltip("Customers");
    private Tooltip appointmentsTip = new Tooltip("Appointments");
    private Tooltip calendarTip = new Tooltip("Calendar");
    private Tooltip reportsTip = new Tooltip("Reports");
    
    @FXML
    private Label userLabel;
    
    @FXML
    private Button customersButton;
    
    @FXML
    private Button appointmentButton;
    
    @FXML
    private Button calendarButton;
    
    @FXML
    private Button reportsButton;
    
    @FXML
    private void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void minimizeButtonAction(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }
    
    @FXML
    private void customersButtonHandler(ActionEvent event) throws Exception {
        Parent customersScreenParent = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
        Scene customersScreenScene = new Scene(customersScreenParent);
        Stage customersScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        customersScreenScene.setCursor(new ImageCursor(image));
        customersScreenStage.setScene(customersScreenScene);
        customersScreenStage.show();
    }
    
    @FXML
    private void appointmentButtonHandler(ActionEvent event) throws Exception {
        Parent appointmentScreenParent = FXMLLoader.load(getClass().getResource("/View/AppointmentScreen.fxml"));
        Scene appointmentScreenScene = new Scene(appointmentScreenParent);
        Stage appointmentScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        appointmentScreenScene.setCursor(new ImageCursor(image));
        appointmentScreenStage.setScene(appointmentScreenScene);
        appointmentScreenStage.show();
    }
    
    @FXML
    private void calendarButtonHandler(ActionEvent event) throws Exception {
        Parent calendarScreenParent = FXMLLoader.load(getClass().getResource("/View/CalendarScreen.fxml"));
        Scene calendarScreenScene = new Scene(calendarScreenParent);
        Stage calendarScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        calendarScreenScene.setCursor(new ImageCursor(image));
        calendarScreenStage.setScene(calendarScreenScene);
        calendarScreenStage.show();
    }

    @FXML
    private void reportsButtonHandler(ActionEvent event) throws Exception {
        Parent reportsScreenParent = FXMLLoader.load(getClass().getResource("/View/ReportScreen.fxml"));
        Scene reportsScreenScene = new Scene(reportsScreenParent);
        Stage reportsScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image image = new Image("/utils/icons/pointer.png");
        reportsScreenScene.setCursor(new ImageCursor(image));
        reportsScreenStage.setScene(reportsScreenScene);
        reportsScreenStage.show();
    }
    
    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        User user = LoginController.getUser();
        userLabel.setText(user.getUserName());
        initToolTips();        
    }
    
    private void initToolTips() {
        Tooltip.install(customersButton, customersTip);
        Tooltip.install(appointmentButton, appointmentsTip);
        Tooltip.install(calendarButton, calendarTip);
        Tooltip.install(reportsButton, reportsTip);
    }
}
