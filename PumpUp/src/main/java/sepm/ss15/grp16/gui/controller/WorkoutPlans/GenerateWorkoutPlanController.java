package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.EquipmentCategory;
import sepm.ss15.grp16.entity.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.TrainingsCategory;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.Training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the auto-generated workout plans depending on the user's criteria.
 * This is just a skeleton, further planning and implementation required.
 */
public class GenerateWorkoutPlanController extends Controller implements Initializable{


    private static final Logger LOGGER = LogManager.getLogger();
    private StageTransitionLoader transitionLoader;
    private GeneratedWorkoutplanService generatedWorkoutplanService;
    private ToggleGroup toggleGroup;
    private List<EquipmentCategory> equipment;
    private List<CheckBox> boxes;

    @FXML
    private RadioButton strengthRadio;

    @FXML
    private RadioButton balanceRadio;

    @FXML
    private RadioButton enduranceRadio;

    @FXML
    private RadioButton flexibilityRadio;


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




    public void setGeneratedWorkoutplanService(GeneratedWorkoutplanService generatedWorkoutplanService) {
        this.generatedWorkoutplanService = generatedWorkoutplanService;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        toggleGroup = new ToggleGroup();
        boxes = new LinkedList<CheckBox>();
        equipment = new ArrayList<EquipmentCategory>();
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
        LOGGER.info("Controller successfully initialized!");
    }

    @FXML
    void generateButtonClicked() {
        for(CheckBox box: boxes){
            equipment.add(new EquipmentCategory(Integer.parseInt(box.getId()),box.getText()));
        }
        RadioButton button = (RadioButton)toggleGroup.getSelectedToggle();
        TrainingsCategory goal = button != null ? new TrainingsCategory(Integer.parseInt(button.getId()), button.getText()): null;
        Gen_WorkoutplanPreferences preferences = new Gen_WorkoutplanPreferences(1, goal, equipment);
        Trainingsplan result;
        try {
            result = generatedWorkoutplanService.generate(preferences);
        }catch (ServiceException e){
            LOGGER.error("Service threw exception, catched in GUI. Real reason: " + e.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Generieren");
            if(e instanceof ValidationException){
                alert.setContentText(((ValidationException) e).getValidationMessage());
            }else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            return;
        }
        LOGGER.info("Generated workoutplan from service received, delegating towards the next window...");
        transitionLoader.openStage("fxml/GeneratedWorkoutPlanResult.fxml",(Stage)barbellCheck.getScene().getWindow(),"Generierter Trainingsplan",300,300,true);
        GeneratedWorkoutPlanResultController controller = (GeneratedWorkoutPlanResultController)transitionLoader.getTo();
        controller.setGeneratedWorkoutPlan(result);
    }


}
