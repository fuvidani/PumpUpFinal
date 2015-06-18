package sepm.ss15.grp16.gui.controller.workoutPlans;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the auto-generated workout plans depending on the user's criteria.
 * This is just a skeleton, further planning and implementation required.
 */
public class GenerateWorkoutPlanController extends Controller {


    private static final Logger LOGGER = LogManager.getLogger();
    private GeneratedWorkoutplanService generatedWorkoutplanService;
    private ToggleGroup toggleGroup;
    private List<EquipmentCategory> equipment;
    private List<CheckBox> boxes;
    private BooleanProperty displayClosed = new SimpleBooleanProperty();
    private Trainingsplan generatedWorkoutPlan;


    /**
     * Radio buttons
     */
    @FXML
    private RadioButton strengthRadio;

    @FXML
    private RadioButton balanceRadio;

    @FXML
    private RadioButton enduranceRadio;

    @FXML
    private RadioButton flexibilityRadio;

    /**
     * Checkboxes
     */
    @FXML
    private CheckBox barbellCheck;

    @FXML
    private CheckBox punchbagCheck;

    @FXML
    private CheckBox yogaBallCheck;

    @FXML
    private CheckBox dumbbellCheck;

    @FXML
    private CheckBox chinupBarCheck;

    @FXML
    private CheckBox expanderCheck;

    @FXML
    private CheckBox medicineBallCheck;

    @FXML
    private CheckBox absRollerCheck;

    @FXML
    private CheckBox jumpingRopeCheck;

    @FXML
    private CheckBox selectAllCheck;


    /**
     * Sets the service. Will be injected by Spring.
     *
     * @param generatedWorkoutplanService service to generate workout plans
     */
    public void setGeneratedWorkoutplanService(GeneratedWorkoutplanService generatedWorkoutplanService) {
        this.generatedWorkoutplanService = generatedWorkoutplanService;

    }


    @Override
    public void initController() {
        toggleGroup = new ToggleGroup();
        boxes = new LinkedList<>();
        equipment = new ArrayList<>();
        strengthRadio.setToggleGroup(toggleGroup);
        strengthRadio.setId("1");
        balanceRadio.setToggleGroup(toggleGroup);
        balanceRadio.setId("2");
        enduranceRadio.setToggleGroup(toggleGroup);
        enduranceRadio.setId("0");
        flexibilityRadio.setToggleGroup(toggleGroup);
        flexibilityRadio.setId("3");
        barbellCheck.setId("16");
        yogaBallCheck.setId("21");
        dumbbellCheck.setId("15");
        chinupBarCheck.setId("14");
        expanderCheck.setId("19");
        medicineBallCheck.setId("13");
        absRollerCheck.setId("20");
        jumpingRopeCheck.setId("17");
        punchbagCheck.setId("18");
        boxes.add(barbellCheck);
        boxes.add(yogaBallCheck);
        boxes.add(dumbbellCheck);
        boxes.add(chinupBarCheck);
        boxes.add(expanderCheck);
        boxes.add(medicineBallCheck);
        boxes.add(absRollerCheck);
        boxes.add(jumpingRopeCheck);
        boxes.add(punchbagCheck);
        selectAllCheck.setOnAction(event -> {
            if (selectAllCheck.isSelected()) {
                for (CheckBox box : boxes) {
                    box.setSelected(true);
                }
            }
        });

        for (CheckBox box : boxes) {
            box.setOnAction(event -> {
                if (!box.isSelected()) {
                    selectAllCheck.setSelected(false);
                }
            });
        }
        this.displayClosed.set(false);
        LOGGER.info("Controller successfully initialized!");
    }

    /**
     * This method is called whenever the user hits the 'Generate' button.
     * It reads all ticked checkboxes and the radio buttons. After that the corresponding
     * service for generating the workout plan is called by delegating the necessary information.
     * When the workout routine is received, it is shown on a new stage.
     */
    @FXML
    void generateButtonClicked() {
        for (CheckBox box : boxes) {
            if (!box.isSelected()) {
                equipment.add(new EquipmentCategory(Integer.parseInt(box.getId()), box.getText()));
            }
        }
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        TrainingsCategory goal = button != null ? new TrainingsCategory(Integer.parseInt(button.getId()), button.getText()) : null;
        Gen_WorkoutplanPreferences preferences = new Gen_WorkoutplanPreferences(1, goal, equipment);
        Trainingsplan result;
        try {
            result = generatedWorkoutplanService.generate(preferences);
        } catch (ServiceException e) {
            LOGGER.error("Service threw exception, catched in GUI. Real reason: " + e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Generieren");
            if (e instanceof ValidationException) {
                alert.setContentText(((ValidationException) e).getValidationMessage());
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            return;
        }
        LOGGER.info("Generated workoutplan from service received, delegating towards the next window...");
        this.generatedWorkoutPlan = result;
        this.displayClosed.set(true);
        mainFrame.navigateToParent();
    }

    /**
     * Children controllers will call this method to get DTO
     *
     * @return the generated workout routine iff the corresponding method has been called
     */
    public Trainingsplan getGeneratedWorkoutPlan() {
        return this.generatedWorkoutPlan;
    }

    /**
     * Children controllers will call this method to know which goal the user picked.
     *
     * @return the goal as a string
     */
    public String getSelectedGoal() {
        return (toggleGroup.getSelectedToggle()) != null ? ((RadioButton) toggleGroup.getSelectedToggle()).getText() : null;
    }

    /**
     * This method will be called by the parent controller to determine
     * whether the "Generate" button has been hit or not.
     *
     * @return true if user decided to generate a workout plan, otherwise false
     */
    public boolean getFlag() {
        return this.displayClosed.get();
    }
}
