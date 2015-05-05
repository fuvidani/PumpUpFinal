package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    private HBox usernameHBox;

    @FXML
    private ImageView userImgView;

    @FXML
    private Label usernameLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userChartController.initialize(location,resources);
        this.userDataController.initialize(location,resources);
        this.calendarController.initialize(location,resources);

    }


    @FXML
    void statisicsButtonClicked(ActionEvent event) {

    }

    @FXML
    void viewWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void editWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void manageWorkoutClicked(ActionEvent event) {

    }

    @FXML
    void calendarClicked(ActionEvent event) {

    }

    @FXML
    void trainingClicked(ActionEvent event) {

    }



}
