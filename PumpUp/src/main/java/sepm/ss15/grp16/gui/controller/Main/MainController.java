package sepm.ss15.grp16.gui.controller.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sepm.ss15.grp16.gui.controller.Main.Main_CalendarController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserChartController;
import sepm.ss15.grp16.gui.controller.Main.Main_UserDataController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 * Controller of the main stage.
 *
 */
public class MainController implements Initializable {

    private Main_UserDataController userDataController;
    private Main_UserChartController userChartController;
    private Main_CalendarController calendarController;

    @FXML
    private Label currentTrainingTypeLabel;

    @FXML
    private ImageView userImgView;

    @FXML
    private Label usernameLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userChartController = new Main_UserChartController();
        this.userDataController = new Main_UserDataController();
        this.calendarController = new Main_CalendarController();
        this.userChartController.initialize(location,resources);
        this.userDataController.initialize(location,resources);
        this.calendarController.initialize(location,resources);

    }



    @FXML
    void bodyPhotosButtonClicked(ActionEvent event) {

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

    }

    @FXML
    void exercisesButtonClicked(ActionEvent event) {

    }

    @FXML
    void calendarClicked(ActionEvent event) {

    }

    @FXML
    void trainingClicked(ActionEvent event) {

    }




}
