package sepm.ss15.grp16.gui.controller.workout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.music.Playlist;
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
    private static final Logger  LOGGER  = LogManager.getLogger(WorkoutstartController.class);
    public               boolean started = false;

    private File                  dir_selection;
    private UserService           userService;
    @FXML
    private ListView<ExerciseSet> toDoListView;
    @FXML
    private Label                 musicPathLabel;
    @FXML
    private Label                 trainingTypeLabel;
    @FXML
    private CheckBox fullscreenBox;

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
        toDoListView.setCellFactory(new Callback<ListView<ExerciseSet>, ListCell<ExerciseSet>>() {
            @Override
            public ListCell<ExerciseSet> call(ListView<ExerciseSet> p) {
                return new ListCell<ExerciseSet>() {
                    @Override
                    protected void updateItem(ExerciseSet t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getRepresentationText());
                        }
                    }

                };
            }
        });
        toDoListView.setItems(sessions);
    }

    @FXML
    void browseMusicClicked(ActionEvent event) {
        boolean success = false;
        while (!success) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Musik suchen");
            File file = directoryChooser.showDialog(stage);
            if (file != null && file.isDirectory() && checkSupportedFormat(file.list())) {
                dir_selection = file;
                musicPathLabel.setText(file.getAbsolutePath());
                success = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ordner leer");
                alert.setHeaderText("Der ausgewählte Ordner ist leer!");
                alert.setContentText("Wollen Sie einen anderen Ordner wählen?");
                ButtonType yes = new ButtonType("Ja");
                ButtonType no = new ButtonType("Nein", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yes, no);
                if (alert.showAndWait().get() == no) {
                    dir_selection = null;
                    musicPathLabel.setText("");
                    success = true;
                }
            }
        }
    }

    @FXML
    void startButtonClicked(ActionEvent event) {
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
            alert.setTitle("Keine Musik gew\u00e4hlt");
            alert.setHeaderText("Sie haben keine Musik gew\u00e4hlt");
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

    public boolean started() {
        return started;
    }

    private String getExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return extension;
    }

    private boolean checkSupportedFormat(String[] list) {
        for (String filepath : list) {
            String extension = getExtension(filepath);
            for (Playlist.SupportedFormat format : Playlist.SupportedFormat.values()) {
                if (format.name().equals(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFullScreen()
    {
        return fullscreenBox.isSelected();
    }
}
