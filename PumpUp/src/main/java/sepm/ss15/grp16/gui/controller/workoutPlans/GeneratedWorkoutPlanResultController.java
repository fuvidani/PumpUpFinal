package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.TrainingsplanService;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 16.05.15.
 * This controller is responsible for displaying the generated workout plan correctly.
 * When the generated workout plan has arrived it is instantly displayed and the user can
 * save the plan, dismiss it or export it to the own calendar.
 */
public class GeneratedWorkoutPlanResultController extends Controller implements Initializable {


    private static final Logger LOGGER = LogManager.getLogger();
    private Trainingsplan generatedWorkoutPlan;
    private StageTransitionLoader transitionLoader;
    private TrainingsplanService trainingsplanService;
    private BooleanProperty DTOArrived = new SimpleBooleanProperty();
    private GenerateWorkoutPlanController parent;
    private boolean saved;

    @FXML
    private ListView<TrainingsSession> listView;

    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        saved = false;
        DTOArrived.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                displayWorkoutPlan();
            }
        });
        listView.setCellFactory(new Callback<ListView<TrainingsSession>, ListCell<TrainingsSession>>() {
            @Override
            public ListCell<TrainingsSession> call(ListView<TrainingsSession> p) {
                return new ListCell<TrainingsSession>() {
                    @Override
                    protected void updateItem(TrainingsSession t, boolean bln) {
                        super.updateItem(t, bln);
                        Pane pane = null;
                        if (t != null) {
                            pane = new Pane();
                            String title = t.getName();
                            String value = "";

                            for (ExerciseSet set : t.getExerciseSets()) {
                                if (set.getType() == ExerciseSet.SetType.time) {
                                    value += set.getOrder_nr() + ": " + set.getRepeat() + " sek. - " + set.getExercise().getName() + "\n\n\n\n";
                                } else {
                                    value += set.getOrder_nr() + ": " + set.getRepeat() + " -  " + set.getExercise().getName() + "\n\n\n\n";
                                }
                            }


                            final Text leftText = new Text(title);
                            leftText.setFont(Font.font("Verdana", 16));

                            leftText.setTextOrigin(VPos.CENTER);
                            leftText.relocate(80, 0);

                            final Text middleText = new Text(value);
                            middleText.setFont(Font.font("Verdana", 14));
                            middleText.setTextOrigin(VPos.TOP);
                            final double em = leftText.getLayoutBounds().getHeight();
                            middleText.relocate(0, 2 * em);

                            pane.getChildren().addAll(leftText, middleText);
                        }
                        setText("");
                        setGraphic(pane);
                    }

                };
            }
        });


        LOGGER.info("GeneratedWorkoutPlanResult successfully initialized!");

    }

    /**
     * Sets the service. Will be injected by Spring.
     *
     * @param service service to save the generated workout routine
     */
    public void setTrainingsplanService(TrainingsplanService service) {
        this.trainingsplanService = service;
    }

    /**
     * This method will only be called by the parent controller.
     * Sets the DTO for later displaying.
     *
     * @param generatedWorkoutPlan the DTO that must be displayed for the user
     */
    public void setGeneratedWorkoutPlan(Trainingsplan generatedWorkoutPlan) {
        this.generatedWorkoutPlan = generatedWorkoutPlan;
        LOGGER.info("Generated workoutplan arrived.");
    }

    /**
     * This method is called when the user hits the 'Trainingsplan speichern' button.
     * It delegates the saving request towards the service and lets the user know that
     * the workout routine has been saved. On error, an error dialog is displayed.
     * Additionally, the button is disabled because the routine can be saved only once.
     */
    @FXML
    public void saveWorkoutPlanClicked() {
        LOGGER.info("Save button clicked, delegating request towards the service layer...");
        try {
            trainingsplanService.create(generatedWorkoutPlan);
        } catch (ServiceException e) {
            LOGGER.error("Service threw exception, catched in GUI. Real reason: " + e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Generieren");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Generierter Trainingsplan");
        alert.setContentText("Der neu generierte Trainingsplan wurde erfolgreich gespeichert.");
        alert.showAndWait();
        saveButton.setDisable(true);
        saved = true;
    }

    /**
     * This method is called whenever the user hits the 'Abbrechen' button.
     * Before the actual close-down the user is asked for confirmation only if the
     * workout routine has not been saved.
     * Depending on the user's choice the stage either closes or stays open.
     */
    @FXML
    public void cancelClicked() {
        if (saved) {
            stage.close();
            parent.setFlag(true);
            LOGGER.info("User clicked 'Cancel', leaving GeneratedWorkoutPlanResult...");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ansicht verlassen.");
            alert.setHeaderText("Wenn Sie abbrechen, wir der angezeigte Trainingsplan nicht gespeichert und verworfen.");
            alert.setContentText("Möchten Sie die Ansicht verlassen?");
            ButtonType yes = new ButtonType("Ja");
            ButtonType cancel = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yes, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                stage.close();
                parent.setFlag(true);
                LOGGER.info("User clicked 'Cancel', leaving GeneratedWorkoutPlanResult...");
            } else {
                alert.close();
            }
        }
    }

    /**
     * Sets the boolean property which signals that the DTO has
     * successfully arrived.
     *
     * @param val a boolean variable to trigger the listener
     */
    public void setFlag(boolean val) {
        DTOArrived.set(val);
    }

    public boolean getSaved() {
        return saved;
    }

    /**
     * Sets the parent controller of this one in order to be able to
     * send signal to it.
     *
     * @param parentController the parent controller
     */
    public void setParentController(GenerateWorkoutPlanController parentController) {
        this.parent = parentController;
    }

    /**
     * This method is automatically called by the listener when the generated workout routine
     * has arrived. It simply displays the workout routine with all its sessions and exercises.
     */
    private void displayWorkoutPlan() {
        List<TrainingsSession> sessions = generatedWorkoutPlan.getTrainingsSessions();
        ObservableList<TrainingsSession> data = FXCollections.observableArrayList(sessions);
        if (sessions.size() == 3) {
            listView.setMaxWidth(760);
        } else if (sessions.size() == 2) {
            listView.setMaxWidth(525);
        }
        listView.setItems(data);

        LOGGER.info("Generated workout successfully displayed!");
    }


}
