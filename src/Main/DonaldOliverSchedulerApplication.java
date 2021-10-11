/* C195: Software II Performance Assessment
 * 
 * @author Donald Oliver
 * 
 */
package Main;

import DAOImplementation.UserDAOImpl;
import Model.User;
import java.sql.Connection;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.DBConnection;

public class DonaldOliverSchedulerApplication extends Application {
    private double xOffset;
    private double yOffset;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
                //Remove default Windows application border
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        //The two following lambda expressions makes it possible to move the application without the standard StageStyle
        //which is being used to override the standard Windows display of the application window
        //These even handlers are only implemented in the login screen. I intended to implement them for every screen, 
        //but as this was outside of the requirements and more of a personal functionality
        //that I was currious about I didn't finish implementing them for every screen. 
        //Lambda mouse event handler
        scene.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        Image image = new Image("/utils/icons/pointer.png");
        scene.setCursor(new ImageCursor(image));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
    
}
