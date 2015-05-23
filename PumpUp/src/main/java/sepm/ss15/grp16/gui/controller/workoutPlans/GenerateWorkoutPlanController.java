package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;

import java.io.IOException;
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
public class GenerateWorkoutPlanController extends Controller implements Initializable {


    private static final Logger LOGGER = LogManager.getLogger();
    private StageTransitionLoader transitionLoader;
    private GeneratedWorkoutplanService generatedWorkoutplanService;
    private ToggleGroup toggleGroup;
    private List<EquipmentCategory> equipment;
    private List<CheckBox> boxes;
    private CategoryService categoryService;
    private BooleanProperty displayClosed = new SimpleBooleanProperty();


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

    /**
     * Sets the service. Will be injected by Spring.
     *
     * @param categoryService service for the categories
     */
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        displayClosed.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                stage.close();
            }
        });
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
        /**
         * Balance and Flexibility disabled until implementtion
         * TODO: remove it after impl.
         */
        balanceRadio.setDisable(true);
        flexibilityRadio.setDisable(true);
        boxes.add(barbellCheck);
        boxes.add(yogaBallCheck);
        boxes.add(dumbbellCheck);
        boxes.add(chinupBarCheck);
        boxes.add(expanderCheck);
        boxes.add(medicineBallCheck);
        boxes.add(absRollerCheck);
        boxes.add(jumpingRopeCheck);
        boxes.add(punchbagCheck);
        selectAllCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (new_val) {
                    for (CheckBox box : boxes) {
                        box.setSelected(true);
                    }
                }
            }
        });

        barbellCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        yogaBallCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        dumbbellCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        chinupBarCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        expanderCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        medicineBallCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        absRollerCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        jumpingRopeCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });

        punchbagCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                if (!new_val) {
                    selectAllCheck.setSelected(false);
                }
            }
        });


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
            /*if(!box.isSelected()) {
                equipment.add(new EquipmentCategory(Integer.parseInt(box.getId()), box.getText()));
            }*/

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

        Stage stage = null;
        GeneratedWorkoutPlanResultController controller = new GeneratedWorkoutPlanResultController();
        try {
            FXMLLoader loader = new FXMLLoader();
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            loader.setControllerFactory(context::getBean);


            loader.setLocation(this.getClass().getClassLoader().getResource("fxml/workoutPlans/GeneratedWorkoutPlanResult.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            stage = new Stage();
            stage.setTitle("Generierter Trainingsplan");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(barbellCheck.getScene().getWindow());
            Scene scene = new Scene(page);
            stage.setScene(scene);

            controller = loader.getController();
            controller.setStage(stage);
            final GeneratedWorkoutPlanResultController controller1 = controller;

            // Show the dialog and wait until the user closes it
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            stage.setMaximized(true);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    e.consume();
                    controller1.cancelClicked();
                }
            });
            stage.show();
            LOGGER.info("New stage successfully launched!");
            // user closed dialog
        } catch (IOException e) {
            LOGGER.info("Error on opening new stage, reason: " + e.getMessage());
            e.printStackTrace();
        }
        controller.setGeneratedWorkoutPlan(result);
        controller.setParentController(this);
        controller.setFlag(true);

    }

    /**
     * Sets the boolean property which signals that the child stage
     * has been closed.
     *
     * @param val a boolean variable to trigger the listener
     */
    public void setFlag(boolean val) {
        this.displayClosed.set(val);
    }


}
