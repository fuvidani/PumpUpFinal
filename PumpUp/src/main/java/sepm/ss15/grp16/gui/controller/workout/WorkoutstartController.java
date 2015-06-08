package sepm.ss15.grp16.gui.controller.workout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.main.MainController;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.UserService;

import java.io.File;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This controller controls the little pop-up window before the actual training starts.
 */
public class WorkoutstartController extends Controller {
    private static final Logger LOGGER = LogManager.getLogger(WorkoutstartController.class);

    private File dir_selection;

    private UserService userService;

    @FXML
    private ListView<ExerciseSet> toDoListView;

    @FXML
    private Label musicPathLabel;

    @FXML
    private Label trainingTypeLabel;

    @FXML
    private Button startButton;

    public boolean started = false;

    public WorkoutstartController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initController() {
        User user = userService.getLoggedInUser();
        String playlist = user.getPlaylist();
        if (playlist != null) {
            musicPathLabel.setText(playlist);
            dir_selection = new File(playlist);
        }


        // Getting Appointment to execute
        MainController mainController = (MainController) getParentController();
        Appointment appointment = mainController.getExecutionAppointment();

        ObservableList<ExerciseSet> sessions = FXCollections.observableList(appointment.getSession().getExerciseSets());
        toDoListView.setCellFactory(new Callback<ListView<ExerciseSet>, ListCell<ExerciseSet>>(){

            @Override
            public ListCell<ExerciseSet> call(ListView<ExerciseSet> p) {

                ListCell<ExerciseSet> cell = new ListCell<ExerciseSet>(){

                    @Override
                    protected void updateItem(ExerciseSet t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getRepresentationText());
                        }
                    }

                };

                return cell;
            }
        });
        toDoListView.setItems(sessions);
    }

    @FXML
    void browseMusicClicked(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Musik suchen");
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            dir_selection = file;
            musicPathLabel.setText(file.getAbsolutePath());
        }

    }

    @FXML
    void startButtonClicked(ActionEvent event) {
        //transitionLoader.openStage("fxml/workout/Workout.fxml", (Stage) toDoListView.getScene().getWindow(), "training", 1100, 750, true);
        if (dir_selection != null) {
            try {
                User user = userService.getLoggedInUser();
                user.setPlaylist(dir_selection.getAbsolutePath());
                userService.update(user);
                userService.setLoggedInUser(user);
                started = true;
                mainFrame.navigateToParent();
            } catch (ServiceException e) {
                LOGGER.error("Error setting last playlist to user, Errormessage: " + e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehler beim laden der Musik aufgetreten!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Keine Musik gewählt");
            alert.setHeaderText("Sie haben keine Musik gewählt");
            alert.setContentText("Wollen Sie ohne Musik trainieren?");
            ButtonType yes = new ButtonType("Ja");
            ButtonType cancel = new ButtonType("Nein", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yes, cancel);
            if (alert.showAndWait().get() == yes) {
                mainFrame.navigateToParent();
                started = true;
            }
        }
    }

    public boolean started()
    {
        return started;
    }
}
