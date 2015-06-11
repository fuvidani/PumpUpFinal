package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.training.impl.TrainingsPlanServiceImpl;
import sepm.ss15.grp16.service.user.UserService;

import java.util.*;


public class Create_Edit_WorkoutPlanController extends Controller {
    private static final Logger LOGGER = LogManager.getLogger(Create_Edit_WorkoutPlanController.class);

    private Trainingsplan plan_interClassCommunication;
    private TrainingsSession session_interClassCommunication;

    private TrainingsplanService trainingsplanService;
    private UserService userService;
    private TrainingsSession selection;

    @FXML
    private Button btnEditSession;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextArea txtDescr;

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
    private Button btnDeleteSession;

    @FXML
    private ListView<TrainingsSession> listViewSessions;

    @FXML
    private Button btnAddSession;

    @FXML
    private Button btnDecreaseDif;

    @FXML
    private Button btnIncreaseDif;

    @FXML
    private Label lblTitel;

    @Override
    public void initController() {

        setUpListView();
        plan_interClassCommunication = ((WorkoutPlansController) this.getParentController()).getPlan_interClassCommunication();

        if (plan_interClassCommunication != null) {
            if (plan_interClassCommunication.getId() != null) {
                txtName.setText(plan_interClassCommunication.getName());
            } else {
                String name = userService.getLoggedInUser().getUsername() + "'";
                if (!name.endsWith("s'")) {
                    name += "s";
                }
                txtName.setText(name + " " + plan_interClassCommunication.getName());
            }
            txtDescr.setText((plan_interClassCommunication.getDescr()));
            txtDuration.setText(String.valueOf(plan_interClassCommunication.getDuration()));

            if (plan_interClassCommunication.getTrainingsSessions() != null) {

                ObservableList<TrainingsSession> data =
                        FXCollections.observableArrayList(
                                plan_interClassCommunication.getTrainingsSessions()
                        );
                listViewSessions.setItems(data);
                updateInformations();
            }
        }

        listViewSessions.getSelectionModel().selectedItemProperty().addListener(
                (ov, old_val, new_val) -> {
                    if (listViewSessions.getSelectionModel().getSelectedItems() != null && new_val != null) {
                        selection = new TrainingsSession(new_val);
                        btnDeleteSession.setDisable(false);
                        btnEditSession.setDisable(false);
                        btnIncreaseDif.setDisable(false);
                        btnDecreaseDif.setDisable(false);
                    }
                });
        btnIncreaseDif.setTooltip(new Tooltip("Schwierigkeit erh\u00f6hen"));
        btnDecreaseDif.setTooltip(new Tooltip("Schwierigkeit reduzieren"));
    }

    /**
     * updates the information panel
     */
    private void updateInformations() {

        List<TrainingsSession> sessions = listViewSessions.getItems();

        if (sessions != null && plan_interClassCommunication != null) {
            int calories_sum = 0;
            List<TrainingsCategory> trainingsCategoryList = new ArrayList<>();
            List<EquipmentCategory> equipmentCategoryList = new ArrayList<>();
            List<MusclegroupCategory> musclegroupCategories = new ArrayList<>();

            for (TrainingsSession session : plan_interClassCommunication.getTrainingsSessions()) {
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
            int calories_mean = calories_sum / plan_interClassCommunication.getTrainingsSessions().size();
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
        }
    }

    /**
     * Reads the input datas. If they are valid, the TrainingsPlanService is called and the Trainingsplan
     * will be created or updated. Otherwise an error is thrown with an alert dialog
     *
     * @param event
     */
    @FXML
    public void saveWorkoutClicked(ActionEvent event) {

        Trainingsplan trainingsplan = createValidPlan();
        if (trainingsplan != null) {
            try {
                if (plan_interClassCommunication != null) {
                    if (plan_interClassCommunication.getId() == null) {
                        trainingsplanService.create(trainingsplan);
                    } else {
                        trainingsplanService.update(trainingsplan);
                    }
                } else {
                    trainingsplanService.create(trainingsplan);
                }
                plan_interClassCommunication = null;
                session_interClassCommunication = null;

                //this.stage.close();
                mainFrame.navigateToParent();

            } catch (ServiceException e) {
                LOGGER.error("Error opening Create_Edit_Stage, Errormessage: " + e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehler beim \u00f6ffnen des Fensters!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private Trainingsplan createValidPlan() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Falsche Daten!");
        boolean error = false;
        String errormessage = "";

        String name = txtName.getText();
        String descr = txtDescr.getText();
        String duration = txtDuration.getText();

        Integer duration_int = null;

        if (name == null || name.equals("")) {
            error = true;
            errormessage = "Name ist leer!";
        }

        if (duration == null || duration.equals("")) {
            error = true;
            errormessage = "Dauer ist leer!";
        }

        if (duration == null || duration.equals("")) {
            error = true;
            errormessage = "Dauer ist leer!";
        } else {
            try {
                duration_int = Integer.parseInt(duration);
            } catch (NumberFormatException e) {
                error = true;
                errormessage = "Dauer muss eine ganzzahlige Zahl sein!";
            }
        }

        List<TrainingsSession> data = listViewSessions.getItems();

        if (data == null || data.isEmpty()) {
            error = true;
            errormessage = "Keine Sessions hinzugef\u00fcgt!";
        }

        if (error) {
            alert.setContentText(errormessage);
            alert.showAndWait();
            return null;
        } else {
            if (plan_interClassCommunication != null) {
                return new Trainingsplan(plan_interClassCommunication.getId(), userService.getLoggedInUser(), name, descr, false, duration_int, data);
            } else {
                return new Trainingsplan(null, userService.getLoggedInUser(), name, descr, false, duration_int, data);
            }
        }
    }

    private void setUpListView() {
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

                            //setText(leftText);
                            pane.getChildren().addAll(leftText, middleText);
                        }
                        setText("");
                        setGraphic(pane);
                    }

                };
            }
        });
    }

    /**
     * Ask for confirmation and close the window at confirmation
     * @param event
     */
    @FXML
    public void cancelClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00c4nderungen verwerfen");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        alert.setContentText("Alle \u00c4nderungen w\u00fcrden verlorgen gehen!");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            plan_interClassCommunication = null;
            session_interClassCommunication = null;
            //this.stage.close();
            mainFrame.navigateToParent();
        }
    }

    /**
     * opens the sessionEdit Window
     * @param event
     */
    @FXML
    public void addSession(ActionEvent event) {
        mainFrame.openDialog(PageEnum.SessionEdit);
        if (session_interClassCommunication != null) {
            listViewSessions.getItems().add(session_interClassCommunication);
            session_interClassCommunication = null;
            setUpListView();
            updateInformations();
        }
    }

    /**
     * opens the sessionEdit Window
     * @param event
     */
    @FXML
    public void editSession(ActionEvent event) {
        if (selection != null) {
            //SessionEditController_v2.session_interClassCommunication = selection;
            session_interClassCommunication = selection;
        }

        //transitionLoader.openWaitStage("fxml/workoutPlans/SessionEdit_v2.fxml", (Stage) listViewSessions.getScene().getWindow(), "Session bearbeiten", 600, 400, false);
        mainFrame.openDialog(PageEnum.SessionEdit);

        if (session_interClassCommunication != null) {
            listViewSessions.getItems().remove(selection);
            listViewSessions.getItems().add(session_interClassCommunication);
            session_interClassCommunication = null;
            setUpListView();
            updateInformations();
        }
    }


    @FXML
    public void deleteSession(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("L\u00f6schen best\u00e4tigen");
        alert.setHeaderText("Wollen Sie die \u00dcbung wirklich aus der Session l\u00f6schen?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            List<TrainingsSession> data = listViewSessions.getItems();

            if (selection != null) {
                listViewSessions.getSelectionModel().clearSelection();
                data.remove(data.indexOf(selection));
                updateInformations();
            }
        }
    }

    @FXML
    public void onClickIncreaseDif(ActionEvent event) {
        Trainingsplan plan = new Trainingsplan();
        plan.setTrainingsSessions(listViewSessions.getItems());
        trainingsplanService.increaseDifficulty(plan);
        ObservableList<TrainingsSession> data = FXCollections.observableArrayList(plan.getTrainingsSessions());
        listViewSessions.setItems(data);
        setUpListView();
        updateInformations();
    }

    @FXML
    public void OnClickDecreaseDif(ActionEvent event) {
        Trainingsplan plan = new Trainingsplan();
        plan.setTrainingsSessions(listViewSessions.getItems());
        trainingsplanService.decreaseDifficulty(plan);
        ObservableList<TrainingsSession> data = FXCollections.observableArrayList(plan.getTrainingsSessions());
        listViewSessions.setItems(data);
        setUpListView();
        updateInformations();
    }

    public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
        this.trainingsplanService = trainingsplanService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public TrainingsSession getSession_interClassCommunication() {
        return session_interClassCommunication;
    }

    public void setSession_interClassCommunication(TrainingsSession session_interClassCommunication) {
        this.session_interClassCommunication = session_interClassCommunication;
    }
}
