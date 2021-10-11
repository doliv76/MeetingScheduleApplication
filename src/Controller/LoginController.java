package Controller;

import DAOImplementation.AppointmentsTableRowDAOImpl;
import DAOImplementation.UserDAOImpl;
import Model.AppointmentsTableRow;
import Model.User;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.DBConnection;

public class LoginController implements Initializable {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label loginLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    private ObservableList<AppointmentsTableRow> allAppointments = FXCollections.observableArrayList();
    private static Connection connection;
    private static User user = new User();

    public static User getUser() {
        return user;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnectionReference();
        languageHandler();
    }

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void minimizeButtonAction(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void loginButtonHandler(ActionEvent event) throws Exception {
        String userName, password;
        userName = userNameField.getText();
        password = passwordField.getText();
        user = loginCurrentUser(userName, password, connection);
        if (user == null) {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            if (Locale.getDefault().getLanguage().equals("es")) {
                alert1.setTitle("¡Alerta de cita inminente!");
                alert1.setHeaderText("Tiene una cita programada para comenzar dentro de los próximos 15 minutos");
                alert1.setContentText("Haga clic en Aceptar para confirmar");
            } else {
                alert1.setTitle("User Login Error");
                alert1.setHeaderText("You have entered either an incorrect username or password. Try again.");
                alert1.setContentText("Click Ok to confirm");
            }
            Optional<ButtonType> result1 = alert1.showAndWait();
            result1.get();
            System.out.println("this user does not exist or the password doesn't match our records for"
                    + " the user on file.");
            //implement error messages
        } else {
            if ((user.getUserName().equals(userName)) && (user.getPassword().equals(password))) {
                System.out.println("you logged in!");

                try {
                    allAppointments.addAll(AppointmentsTableRowDAOImpl.getUserTableRows(connection, user.getUserId()));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

                toHomeScreen(event);
            }
        }
    }

    private User loginCurrentUser(String userName, String password, Connection connection) throws Exception {
        try {
            user = UserDAOImpl.getUser(userName, password, connection);
            if (user == null) {
                System.out.println("Login ERROR...");
            } else {
                System.out.println(
                        "Record: " + " | " + user.getUserId() + " | " + user.getUserName() + " | " + " | " + user.getPassword() + " | " + user.getCreatedBy() + " | " + user.getCreateDate().getTime() + " | "
                        + user.getLastUpdate().getTime() + " | " + user.getLastUpdateBy());

                return user;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private void toHomeScreen(ActionEvent event) throws IOException {
        if (dialogueHandler()) {
            writeLoginToFile(user);
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View/HomeScreen.fxml"));
            Scene mainScreenScene = new Scene(mainScreenParent);
            Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Image image = new Image("/utils/icons/pointer.png");
            mainScreenScene.setCursor(new ImageCursor(image));
            mainScreenStage.setScene(mainScreenScene);
            mainScreenStage.show();
        }
    }

    private boolean dialogueHandler() {
        LocalTime inputLT, nowLT, nowLaterLT;
        LocalDateTime inputLDT;
        String appStrStart;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.s");
        nowLT = LocalTime.now().minusSeconds(1);
        nowLaterLT = nowLT.plusMinutes(15).plusSeconds(1);
        for (AppointmentsTableRow app : allAppointments) {
            appStrStart = app.getStart();
            inputLDT = LocalDateTime.parse(appStrStart, dateFormatter);
            inputLT = inputLDT.toLocalTime();

            if (nowLT.isBefore(inputLT) && nowLaterLT.isAfter(inputLT)) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                if (Locale.getDefault().getLanguage().equals("es")) {
                    alert1.setTitle("¡Alerta de cita inminente!");
                    alert1.setHeaderText("Tiene una cita programada para comenzar dentro de los próximos 15 minutos");
                    alert1.setContentText("Haga clic en Aceptar para confirmar");
                } else {
                    alert1.setTitle("Imminent Appointment Alert!");
                    alert1.setHeaderText("You have an appointment scheduled to begin within the next 15 minutes");
                    alert1.setContentText("Click Ok to confirm");
                }
                Optional<ButtonType> result1 = alert1.showAndWait();
                return result1.get() == ButtonType.OK;
            }
        }
        return true;
    }

    private void writeLoginToFile(User user) throws IOException, FileNotFoundException {
        String filename = "src/utils/log/userActivityLog.txt", item;
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        item = "User " + user.getUserName() + " logged in on "
                + LocalDate.now() + " at " + LocalTime.now() + ";";
        outputFile.println(item);
        outputFile.close();
        System.out.println("File written");
    }

    private void languageHandler() {
        Locale spanish = new Locale("es", "ES");
        ResourceBundle rbun = ResourceBundle.getBundle("utils.language/Nat", spanish);

        if (Locale.getDefault().getLanguage().equals("es")) {
            //format loginLabel to store multiple words as a single-string key
            String formattedString = loginLabel.getText().replaceAll(" ", "_");
            loginLabel.setText(rbun.getString(formattedString));

            formattedString = userNameLabel.getText().replaceAll(" ", "_");
            userNameLabel.setText(rbun.getString(formattedString));

            formattedString = passwordLabel.getText();
            passwordLabel.setText(rbun.getString(formattedString));

            formattedString = loginButton.getText();
            loginButton.setText(rbun.getString(formattedString));

            formattedString = userNameField.getPromptText().replaceAll(" ", "_");
            userNameField.setPromptText(rbun.getString(formattedString));

            formattedString = passwordField.getPromptText().replaceAll(" ", "_");
            passwordField.setPromptText(rbun.getString(formattedString));
        }
    }
}
