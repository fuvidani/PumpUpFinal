package sepm.ss15.grp16.gui.controller.Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.Exercises.ExercisesController;
import sepm.ss15.grp16.gui.controller.Main.Main_CalendarController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserChartController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserDataController;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.User.LoginController;
import sepm.ss15.grp16.gui.controller.WorkoutPlans.WorkoutPlansController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 * Controller of the main stage.
 *
 */
public class MainController extends Controller implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger();
    private StageTransitionLoader transitionLoader;
    private Main_UserDataController userDataController;
    private Main_UserChartController userChartController;
    private Main_CalendarController calendarController;

    @FXML
    private Label currentTrainingTypeLabel;

    @FXML
    private ImageView userImgView;

    @FXML
    private Label usernameLabel;

    @FXML
    private TableView<?> userDataTable;

    @FXML
    private LineChart<?, ?> userDataChart;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        this.userImgView = new ImageView();
        this.userChartController = new Main_UserChartController();
        this.userDataController = new Main_UserDataController();
        this.calendarController = new Main_CalendarController();
        this.userChartController.initialize(location,resources);
        this.userDataController.initialize(location,resources);
        this.calendarController.initialize(location, resources);

    }



    @FXML
    void editUserDataButtonClicked(ActionEvent event) {
        // TODO: Either small dialog window for editing data or live editing into the table itself.
    }

    @FXML
    void statisicsButtonClicked(ActionEvent event) {
        // TODO: Michi's Statistiken
    }

    @FXML
    void viewCurrentWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutplans.fxml",(Stage)userImgView.getScene().getWindow(),"Trainingspläne",1000,620, true);
    }

    @FXML
    void viewAllWorkoutPlansClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutplans.fxml",(Stage)userImgView.getScene().getWindow(),"Trainingspläne",1000,620, true);
    }

    @FXML
    void exercisesButtonClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Exercises.fxml", (Stage) userImgView.getScene().getWindow(), "Übungen", 1100, 750, true);
    }

    @FXML
    void calendarClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Calendar.fxml", (Stage) userImgView.getScene().getWindow(), "Trainingskalender", 1000, 500, false);
    }

    @FXML
    void trainingClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutstart.fxml", (Stage) userImgView.getScene().getWindow(), "Trainingsvorbereitung", 800, 600, false);
    }



    @FXML
    void editBodyDataClicked(ActionEvent event) {

    }

    @FXML
    void manageBodyPhotosClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/BodyPhotos.fxml", (Stage)userImgView.getScene().getWindow(),"Fotos", 1000, 600, false);
    }

    @FXML
    void logoutClicked(ActionEvent event) {

    }


    @FXML
    void exercisesMenuClicked(ActionEvent event) {

    }

    @FXML
    void openCalendarMenuClicked(ActionEvent event) {

    }

    @FXML
    void aboutMenuClicked(ActionEvent event) {

    }




}
