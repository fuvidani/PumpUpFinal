package sepm.ss15.grp16.gui.controller.workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.controller.Controller;
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
    private ListView<?> toDoListView;

    @FXML
    private Label musicPathLabel;

    @FXML
    private Label trainingTypeLabel;

    @FXML
    private Button startButton;

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
            }
        }
    }


}
