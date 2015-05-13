package sepm.ss15.grp16.gui.controller.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.controller.Exercises.ExercisesController;
import sepm.ss15.grp16.gui.controller.Main.Main_CalendarController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserChartController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserDataController;
import sepm.ss15.grp16.gui.controller.WorkoutPlans.WorkoutPlansController;
import sepm.ss15.grp16.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 * Controller of the main stage.
 *
 */
public class MainController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger();
    private Main_UserDataController userDataController;
    private Main_UserChartController userChartController;
    private Main_CalendarController calendarController;
    private UserService userService;

    @FXML
    private Label currentTrainingTypeLabel;

    @FXML
    private ImageView userImgView;

    @FXML
    private Label usernameLabel;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userChartController = new Main_UserChartController();
        this.userDataController = new Main_UserDataController();
        this.calendarController = new Main_CalendarController();
        this.userChartController.initialize(location,resources);
        this.userDataController.initialize(location,resources);
        this.calendarController.initialize(location,resources);
        this.usernameLabel.setText(userService.getLoggedInUser().getUsername());
    }



    @FXML
    void bodyPhotosButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getClassLoader().getResource("fxml/BodyPhotos.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage primStage = (Stage) userImgView.getScene().getWindow();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fotos");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BodyPhotosController controller = loader.getController();
            controller.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.setMinWidth(1000);
            dialogStage.setMinHeight(600);
            dialogStage.showAndWait();
            // user closed dialog, time to update the table view
        }catch (IOException e){
            LOGGER.info("Error on opening BodyPhotos stage, reason: " + e.getMessage());
        }
        LOGGER.info("New stage successfully launched!");
    }

    @FXML
    void editUserDataButtonClicked(ActionEvent event) {

    }

    @FXML
    void statisicsButtonClicked(ActionEvent event) {

    }

    @FXML
    void viewCurrentWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void viewAllWorkoutPlansClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getClassLoader().getResource("fxml/Workoutplans.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage primStage = (Stage) userImgView.getScene().getWindow();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Trainingspläne");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WorkoutPlansController controller = loader.getController();
            controller.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.setMinWidth(1000);
            dialogStage.setMinHeight(620);
            dialogStage.showAndWait();
            // user closed dialog, time to update the table view
        }catch (IOException e){
            LOGGER.info("Error on opening Workoutplans stage, reason: " + e.getMessage());
        }
        LOGGER.info("New stage successfully launched!");
    }

    @FXML
    void exercisesButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getClassLoader().getResource("fxml/Exercises.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage primStage = (Stage) userImgView.getScene().getWindow();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Übungen");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ExercisesController controller = loader.getController();
            controller.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.setMinWidth(1000);
            dialogStage.setMinHeight(620);
            dialogStage.showAndWait();
            // user closed dialog, time to update the table view
        }catch (IOException e){
            LOGGER.info("Error on opening Exercises stage, reason: " + e.getMessage());
        }
        LOGGER.info("New stage successfully launched!");
    }

    @FXML
    void calendarClicked(ActionEvent event) {

    }

    @FXML
    void trainingClicked(ActionEvent event) {

    }




}
