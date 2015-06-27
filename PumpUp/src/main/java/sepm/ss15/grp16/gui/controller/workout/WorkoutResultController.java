package sepm.ss15.grp16.gui.controller.workout;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.training.WorkoutResult;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for properly displaying the workout results.
 */
public class WorkoutResultController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();


    @FXML
    private TableView<HashMap.Entry<ExerciseSet, WorkoutResult.ExecutionTimePair>> exercisesTable;

    @FXML
    private TableColumn<HashMap.Entry<ExerciseSet, WorkoutResult.ExecutionTimePair>, String> exercise;

    @FXML
    private TableColumn<HashMap.Entry<ExerciseSet, WorkoutResult.ExecutionTimePair>, String> repete;

    @FXML
    private TableColumn<HashMap.Entry<ExerciseSet, WorkoutResult.ExecutionTimePair>, String> duration;

    @FXML
    private LineChart<?, ?> workoutChart;

    @FXML
    private Label burnedCaloriesLabel;

    private ObservableList<HashMap.Entry<ExerciseSet, WorkoutResult.ExecutionTimePair>> masterData;

    private CalendarService calendarService;

    public WorkoutResultController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public void initController() {
        WorkoutController workoutController = (WorkoutController) getParentController();

        WorkoutResult workoutResult = workoutController.getWorkoutResult();

        try {
            calendarService.setAppointmentAsTrained(workoutResult.getAppointment().getId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        double calorin = 0;
        for (ExerciseSet set : workoutResult.getAppointment().getSession().getExerciseSets()) {
            calorin += set.getExercise().getCalories() * ((workoutResult.getList().get(set).getDuration() == null) ? 0 : workoutResult.getList().get(set).getDuration());
        }
        calorin = (double)Math.round(calorin * 100) / 100;
        burnedCaloriesLabel.setText(calorin + "");

        masterData = FXCollections.observableList(new LinkedList<>(workoutResult.getList().entrySet()));
        exercise.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().getExercise().getName()));
        duration.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getDuration() + ""));
        repete.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getRepetion() == null ? "" : p.getValue().getValue().getRepetion() + ""));
        exercisesTable.setItems(masterData);
    }

    /**
     * This method is called whenever the user hits the "Auf Facebook teilen" button at the
     * results window. It opens up a WebView with a URL pointing to a feed dialog.
     * If there is available internet connection and the user is successfully authenticated, the user is able
     * to publish a message on his or her wall with our application link underneath the message.
     * Otherwise an error dialog is shown that no connection could be established.
     * After publishing the user is automatically redirected to the Facebook page.
     * The user must close the WebView personally.
     */
    @FXML
    void shareFacebookClicked() {
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://www.facebook.com/dialog/feed?app_id=428485184010923&display=popup&name=PumpUp!&description=Share%20your%20workout%20results%20with%20PumpUp!&caption=Do%20you%20want%20to%20get%20in%20shape?&link=https%3A%2F%2Ffacebook.com%2FPumpUpTUVienna%2F&redirect_uri=https%3A%2F%2Ffacebook.com%2F"));
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Öffnen des Browsers");
            alert.setContentText("Es konnte keine Internetverbindung hergestellt werden, somit können Sie leider derzeit Facebook nicht erreichen.");
            alert.showAndWait();
        } catch (URISyntaxException ex) {
            LOGGER.info("Error in URI: " + ex.getMessage());
        }
    }

    @FXML
    void endWorkoutClicked(ActionEvent event) {
        mainFrame.navigateToParent();
    }


}
