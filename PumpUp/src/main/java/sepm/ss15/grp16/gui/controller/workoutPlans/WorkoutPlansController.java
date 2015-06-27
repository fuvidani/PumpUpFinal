package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.training.impl.TrainingsPlanServiceImpl;
import sepm.ss15.grp16.service.user.UserService;

import java.util.*;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is the central point of all workout plans assigned to the user.
 */
public class WorkoutPlansController extends Controller {
    private static final Logger LOGGER = LogManager.getLogger(WorkoutPlansController.class);

    private Trainingsplan plan_interClassCommunication;
    private Trainingsplan generatedWorkoutPlan;
    private String selectedGoal;
    private TrainingsplanService trainingsplanService;
    private UserService userService;

    private Trainingsplan selection;

    @FXML
    private CheckBox defaultWorkoutPlansCheck;

    @FXML
    private CheckBox customWorkoutPlansCheck;

    @FXML
    private Label trainingNameLabel;

    @FXML
    private TextArea trainingDescr;

    @FXML
    private Label txtDuration;

    @FXML
    private Label txtCal_sum;

    @FXML
    private Label txtCal_mean;

    @FXML
    private Label txtTraining;

    @FXML
    private Label txtEquip;

    @FXML
    private Label txtMuscle;

    @FXML
    private ListView<Trainingsplan> workoutPlansListView;

    @FXML
    private ListView<TrainingsSession> listViewSessions;

    @FXML
    private Button calenderBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button copyBtn;

    @FXML
    private Button newBtn;

    @Override
    public void initController() {

        try {
            ObservableList<Trainingsplan> data =
                    FXCollections.observableArrayList(
                            trainingsplanService.findAll()
                    );

            workoutPlansListView.getSelectionModel().selectedItemProperty().addListener(
                    (ov, old_val, new_val) -> {
                        if (workoutPlansListView.getSelectionModel().getSelectedItems() != null && new_val != null) {
                            updateSessionList(new_val.getTrainingsSessions());
                            selection = new Trainingsplan(new_val);
                            txtDuration.setText(String.valueOf(new_val.getDuration()));
                            trainingDescr.setText(new_val.getDescr());
                            trainingNameLabel.setText(new_val.getName());

                            if (selection.getUser() != null) {
                                deleteBtn.setDisable(false);
                                editBtn.setDisable(false);
                            } else {
                                deleteBtn.setDisable(true);
                                editBtn.setDisable(true);
                            }
                            copyBtn.setDisable(false);
                            calenderBtn.setDisable(false);
                            updateInformations(new_val);

                        }
                    });

            setUpListView();
            workoutPlansListView.setItems(data);

            workoutPlansListView.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if (workoutPlansListView.getSelectionModel().getSelectedItem().getUser() != null) {
                        this.editWorkoutPlanClicked(null);
                    } else {
                        this.copyWorkoutPlanClicked(null);
                    }
                }
            });

            listViewSessions.setMouseTransparent(true);
            listViewSessions.setFocusTraversable(false);

            newBtn.setTooltip(new Tooltip("Neuen Trainingsplan erstellen"));
            editBtn.setTooltip(new Tooltip("Trainingsplan bearbeiten"));
            copyBtn.setTooltip(new Tooltip("Trainingsplan kopieren & bearbeiten"));
            deleteBtn.setTooltip(new Tooltip("Trainingsplan l\u00F6schen"));
        } catch (ServiceException e) {
            LOGGER.error("+e");
        }
    }

    private void updateInformations(Trainingsplan plan) {

        if (plan != null && plan.getTrainingsSessions() != null) {
            int calories_sum = 0;
            List<TrainingsCategory> trainingsCategoryList = new ArrayList<>();
            List<EquipmentCategory> equipmentCategoryList = new ArrayList<>();
            List<MusclegroupCategory> musclegroupCategories = new ArrayList<>();

            for (TrainingsSession session : plan.getTrainingsSessions()) {
                for (ExerciseSet set : session.getExerciseSets()) {
                    calories_sum += set.getRepeat() * set.getExercise().getCalories();

                    List<AbsractCategory> absractCategories = set.getExercise().getCategories();

                    for (AbsractCategory category : absractCategories) {
                        if (category instanceof TrainingsCategory && !trainingsCategoryList.contains(category)) {
                            trainingsCategoryList.add((TrainingsCategory) category);
                        } else if (category instanceof EquipmentCategory && !equipmentCategoryList.contains(category)) {
                            equipmentCategoryList.add((EquipmentCategory) category);
                        } else if (category instanceof MusclegroupCategory && !musclegroupCategories.contains(category)) {
                            musclegroupCategories.add((MusclegroupCategory) category);
                        }
                    }
                }
            }
            int calories_mean = calories_sum / plan.getTrainingsSessions().size();
            txtCal_sum.setText(String.valueOf(calories_sum));
            txtCal_mean.setText(String.valueOf(calories_mean));

            String value_training = "";
            String value_equip = "";
            String value_muscle = "";
            if (!trainingsCategoryList.isEmpty()) {
                value_training = "Art: \n";
                for (TrainingsCategory category : trainingsCategoryList) {
                    value_training += "    - " + category.getName() + "\n";
                }
            }
            if (!equipmentCategoryList.isEmpty()) {
                value_equip = "Ge\u00e4rte: \n";
                for (EquipmentCategory category : equipmentCategoryList) {
                    value_equip += "    - " + category.getName() + "\n";
                }
            }
            if (!musclegroupCategories.isEmpty()) {
                value_muscle = "Muskeln: \n";
                for (MusclegroupCategory category : musclegroupCategories) {
                    value_muscle += "    - " + category.getName() + "\n";
                }
            }

            txtTraining.setText(value_training);
            txtEquip.setText(value_equip);
            txtMuscle.setText(value_muscle);
        } else {
            txtTraining.setText("");
            txtEquip.setText("");
            txtMuscle.setText("");
            txtCal_sum.setText("");
            txtCal_mean.setText("");
        }
    }

    private void updateSessionList(List<TrainingsSession> sessions) {
        listViewSessions.getItems().clear();
        if (sessions != null) {
            ObservableList<TrainingsSession> data = FXCollections.observableArrayList(sessions);
            listViewSessions.setItems(data);
        }
    }

    @FXML
    public void filterUserPlans(ActionEvent event) {
        try {
            List<Trainingsplan> list;
            if (customWorkoutPlansCheck.isSelected()) {
                User user = userService.getLoggedInUser();

                list = trainingsplanService.find(new Trainingsplan(null, user, null, null, null, null, null));
                if (list == null) list = new ArrayList<>();
            } else {
                list = trainingsplanService.findAll();
            }
            ObservableList<Trainingsplan> data = FXCollections.observableArrayList(list);
            workoutPlansListView.setItems(data);

            clearSelection();
        } catch (ServiceException e) {
            LOGGER.error("+e");
        }
    }

    @FXML
    public void filterDefPlans(ActionEvent event) {
        try {
            List<Trainingsplan> list;

            if (defaultWorkoutPlansCheck.isSelected()) {
                list = trainingsplanService.getDefaultPlans();
            } else {
                list = trainingsplanService.findAll();
            }
            ObservableList<Trainingsplan> data = FXCollections.observableArrayList(list);
            workoutPlansListView.setItems(data);

            clearSelection();
        } catch (ServiceException e) {
            LOGGER.error("+e");
        }
    }

    private void clearSelection() {
        selection = null;

        trainingDescr.setText("");
        trainingNameLabel.setText("");
        txtDuration.setText("");
        listViewSessions.getItems().clear();
        workoutPlansListView.getSelectionModel().clearSelection();

        deleteBtn.setDisable(true);
        editBtn.setDisable(true);
        copyBtn.setDisable(true);
        calenderBtn.setDisable(true);
    }

    private void setUpListView() {
        workoutPlansListView.setCellFactory(new Callback<ListView<Trainingsplan>, ListCell<Trainingsplan>>() {
            @Override
            public ListCell<Trainingsplan> call(ListView<Trainingsplan> p) {
                return new ListCell<Trainingsplan>() {
                    @Override
                    protected void updateItem(Trainingsplan t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName());
                        } else {
                            setText("");
                        }
                    }

                };
            }
        });

        listViewSessions.setCellFactory(new Callback<ListView<TrainingsSession>, ListCell<TrainingsSession>>() {
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

                            Map<Integer, Pair<Integer, ExerciseSet>> setMap = new HashMap<>();

                            for (ExerciseSet set : t.getExerciseSets()) {
                                Pair<Integer, ExerciseSet> set_map = setMap.get(setMap.size());

                                if (set_map != null) {
                                    if (!set_map.getValue().getExercise().equals(set.getExercise()) ||
                                            !set_map.getValue().getRepeat().equals(set.getRepeat()) ||
                                            !set_map.getValue().getType().equals(set.getType())) {
                                        setMap.put(setMap.size() + 1, new Pair<>(1, set));
                                    } else {
                                        setMap.replace(setMap.size(), set_map, new Pair<>(set_map.getKey() + 1, set_map.getValue()));
                                    }
                                } else {
                                    setMap.put(setMap.size() + 1, new Pair<>(1, set));
                                }
                            }

                            for (Map.Entry<Integer, Pair<Integer, ExerciseSet>> entry : setMap.entrySet()) {
                                value += entry.getValue().getKey() + "x "
                                        + entry.getValue().getValue().getRepeat()
                                        + (entry.getValue().getValue().getType() == ExerciseSet.SetType.time ? "s " : " ")
                                        + entry.getValue().getValue().getExercise().getName() + "\n";
                            }


                            final Text leftText = new Text(title);
                            //leftText.setFont(_itemFont);
                            leftText.setTextOrigin(VPos.CENTER);
                            leftText.relocate(0, 0);

                            final Text middleText = new Text(value);
                            //middleText.setFont(_itemFont);
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
    }

    @FXML
    public void newWorkoutPlanClicked(ActionEvent event) {
        //Create_Edit_WorkoutPlanController.plan_interClassCommunication = null;
        plan_interClassCommunication = null;

        //transitionLoader.openWaitStage("fxml/workoutPlans/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(),
        //      "Trainingsplan erstellen", 1000, 620, true);
        mainFrame.navigateToChild(PageEnum.Workoutplan_create_edit);

        updateTable();
        setUpListView();
        clearSelection();
    }

    @FXML
    public void copyWorkoutPlanClicked(ActionEvent event) {
        Trainingsplan toCopy = new Trainingsplan(selection);
        toCopy.setId(null);
        //Create_Edit_WorkoutPlanController.plan_interClassCommunication = toCopy;
        plan_interClassCommunication = toCopy;
        //transitionLoader.openWaitStage("fxml/workoutPlans/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(),
        //      "Trainingsplan " + selection.getName() + " kopieren", 1000, 620, true);
        mainFrame.navigateToChild(PageEnum.Workoutplan_create_edit);
        updateTable();
        setUpListView();
        clearSelection();
    }

    @FXML
    public void generateWorkoutPlanClicked(ActionEvent event) {
        mainFrame.openDialog(PageEnum.Workoutplan_generate);
        GenerateWorkoutPlanController controller = (GenerateWorkoutPlanController) this.getChildController();
        if (controller.getFlag()) {
            this.generatedWorkoutPlan = controller.getGeneratedWorkoutPlan();
            this.selectedGoal = controller.getSelectedGoal();
            mainFrame.navigateToChild(PageEnum.Workoutplan_generate_result);
            updateTable();
            setUpListView();
            clearSelection();
        }
    }


    /**
     * Will be called by the GeneratedWorkoutPlanResultController to get the DTO.
     *
     * @return the generated workout plan by the service
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
        return this.selectedGoal;
    }

    @FXML
    public void deleteWorkoutPlanClicked(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("L\u00f6schen best\u00e4tigen");
            alert.setHeaderText("Sind Sie sicher?");
            alert.setContentText(selection.getName() + " wirklich l\u00f6schen?");
            ButtonType yes = new ButtonType("Ja");
            ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yes, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yes) {
                trainingsplanService.delete(selection);
                clearSelection();
                List<Trainingsplan> list = trainingsplanService.findAll();
                ObservableList<Trainingsplan> data = FXCollections.observableArrayList(list);
                workoutPlansListView.getItems().clear();
                workoutPlansListView.getSelectionModel().clearSelection();
                workoutPlansListView.setItems(data);
                updateTable();
                setUpListView();
                clearSelection();
            }
        } catch (ServiceException e) {
            LOGGER.error("Error opening SetStage, Errormessage: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim \u00f6ffnen des Fensters!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

            LOGGER.error("+e");
        }
    }

    @FXML
    public void editWorkoutPlanClicked(ActionEvent event) {
        Stage thiststage = (Stage) listViewSessions.getScene().getWindow();
        //Create_Edit_WorkoutPlanController.plan_interClassCommunication = selection;
        plan_interClassCommunication = selection;

        // transitionLoader.openWaitStage("fxml/workoutPlans/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(),
        //         "Trainingsplan " + selection.getName() + " bearbeiten", 1000, 620, true);
        mainFrame.navigateToChild(PageEnum.Workoutplan_create_edit);

        updateTable();
        setUpListView();
        clearSelection();
    }

    public void updateTable() {
        try {
            ObservableList<Trainingsplan> data =
                    FXCollections.observableArrayList(
                            trainingsplanService.findAll()
                    );
            workoutPlansListView.setItems(data);
        } catch (ServiceException e) {
            LOGGER.error("+e");
        }
    }

    @FXML
    public void embedInCalenderClicked(ActionEvent event) {
        plan_interClassCommunication = selection;
        mainFrame.openDialog(PageEnum.Workoutplan_calender_dialog);
        if (((WorkoutPlanToCalendarController) this.getChildController()).isFinished())
            mainFrame.navigateToChild(PageEnum.Calendar);
    }

    @FXML
    public void getBackClicked(ActionEvent event) {
        //this.stage.close();
        mainFrame.navigateToParent();
    }

    public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
        this.trainingsplanService = trainingsplanService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Trainingsplan getPlan_interClassCommunication() {
        return plan_interClassCommunication;
    }

    public void setPlan_interClassCommunication(Trainingsplan plan_interClassCommunication) {
        this.plan_interClassCommunication = plan_interClassCommunication;
    }
}
