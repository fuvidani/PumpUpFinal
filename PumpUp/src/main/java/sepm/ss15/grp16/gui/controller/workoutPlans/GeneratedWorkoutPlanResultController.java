package sepm.ss15.grp16.gui.controller.workoutPlans;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
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

import java.io.File;
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
public class GeneratedWorkoutPlanResultController extends Controller{


    private static final Logger LOGGER = LogManager.getLogger();
    private Trainingsplan generatedWorkoutPlan;
    private TrainingsplanService trainingsplanService;
    private BooleanProperty DTOArrived = new SimpleBooleanProperty();
    private boolean saved;

    @FXML
    private ListView<TrainingsSession> listView;

    @FXML
    private Button saveButton;

    @Override
    public void initController() {
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
                                    value += set.getOrder_nr() + ": " + set.getRepeat() + " Sek. - " + set.getExercise().getName() + "\n\n\n\n";
                                } else {
                                    value += set.getOrder_nr() + ": " + set.getRepeat() + " x " + set.getExercise().getName() + "\n\n\n\n";
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
        GenerateWorkoutPlanController controller = (GenerateWorkoutPlanController)this.getParentController();
        this.generatedWorkoutPlan = controller.getGeneratedWorkoutPlan();
        this.setFlag(true);

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
        cancelClicked();
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
            mainFrame.navigateToParent();
            LOGGER.info("user clicked 'Cancel', leaving GeneratedWorkoutPlanResult...");
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
                mainFrame.navigateToParent();
                LOGGER.info("user clicked 'Cancel', leaving GeneratedWorkoutPlanResult...");
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

    @FXML
    public void shareFacebookClicked() throws Exception{
        String appID = "428485184010923";
        String appSecret = "62d1eb97169a4ec1af1c2efe06155544";
        String accessToken = "CAACEdEose0cBALQD3De2s9zW445h6MQk142fd5JZCyVZAxlMHOaFuxoox6T6kNOFZCjYdeEW08D0xb6fFanHxigHBpZBgHsERe26BcixBkgLUFiUYOYBKG3WPqmvVx0X6Nw1u1ebnNm0ncldZC0Q6CKLWITRw5s34RoDZBhtB5CDG2fgMRjLS4Ws1vXnBKLBWzdviBEOX0EDYn3B8ZChMqkFTZAGBGRTWWAZD";

        // Setting up the webview
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load("https://www.facebook.com/dialog/feed?app_id=428485184010923&display=popup&name=PumpUp!&description=Share%20your%20workout%20results%20with%20PumpUp!&caption=Do%20you%20want%20to%20get%20in%20shape?&link=https%3A%2F%2Fdevelopers.facebook.com%2Fapps%2F428485184010923%2F&redirect_uri=https%3A%2F%2Ffacebook.com%2F");
        Stage stage = new Stage();
        stage.initOwner(this.stage);
        stage.setScene(new Scene(webView, 500, 300));
        stage.show();
    }

}
