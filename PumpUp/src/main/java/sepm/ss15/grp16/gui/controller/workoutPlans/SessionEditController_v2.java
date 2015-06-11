package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.exercises.VideoPlayable;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.exercise.impl.ExerciseServiceImpl;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionEditController_v2 extends Controller{
    private static final Logger LOGGER = LogManager.getLogger(SessionEditController_v2.class);

    private TrainingsSession session_interClassCommunication;

    private ExerciseService exerciseService;
    private UserService userService;

    private ObservableList<Exercise> masterdata;

    private ExerciseSet selection_set;
    private Exercise selection_exercise;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtFilter;

    @FXML
    private TableView<Exercise> tblvExercises;

    @FXML
    private TableColumn<Exercise, String> tblcName;

    @FXML
    private TableColumn<Exercise, Integer> tblcCalo;

    @FXML
    private TableColumn<Exercise, String> tblcCat;

    @FXML
    private Button btnUp;

    @FXML
    private Button btnDown;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnShow;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<ExerciseSet> tblvExerciseTable;

    @FXML
    private TableColumn<ExerciseSet, Integer> tblcOrder;

    @FXML
    private TableColumn<ExerciseSet, String> tblcType;

    @FXML
    private TableColumn<ExerciseSet, String> tblcExercise;

    @FXML
    void OnClickCancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00c4nderungen verwerfen");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        alert.setContentText("Alle \u00c4nderungen w\u00fcrden verlorgen gehen!");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            session_interClassCommunication = null;
            mainFrame.navigateToParent();
        }
    }

    @FXML
    void onClickFinish(ActionEvent event) {
        TrainingsSession session = createValidSession();
        if (session != null) {
            // Create_Edit_WorkoutPlanController.session_interClassCommunication = session;
            ((Create_Edit_WorkoutPlanController) this.getParentController()).setSession_interClassCommunication(session);
//            stage.close();
            mainFrame.navigateToParent();
        }
    }

    private TrainingsSession createValidSession() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Falsche Daten!");
        boolean error = false;
        String errormessage = "";

        String name = txtName.getText();

        if (name == null || name.equals("")) {
            error = true;
            errormessage = "Name ist leer!";
        }

        List<ExerciseSet> data = tblvExerciseTable.getItems();

        if (data == null || data.isEmpty()) {
            error = true;
            errormessage = "Keine \u00dcbungen hinzugef\u00fcgt!";
        }

        if (error) {
            alert.setContentText(errormessage);
            alert.showAndWait();
            return null;
        } else {

            if (session_interClassCommunication != null) {
                return new TrainingsSession(session_interClassCommunication.getId(), userService.getLoggedInUser(), name, false, data);
            } else {
                return new TrainingsSession(null, userService.getLoggedInUser(), name, false, data);
            }
        }
    }

    @FXML
    private void onClickUp(ActionEvent event) {

        ObservableList<ExerciseSet> sets =
                FXCollections.observableArrayList(
                        tblvExerciseTable.getItems()
                );

        sets.remove(selection_set);
        sets.stream().filter(set -> set.getOrder_nr() + 1 == selection_set.getOrder_nr()).forEach(set -> {
            set.setOrder_nr(set.getOrder_nr() + 1);
            selection_set.setOrder_nr(selection_set.getOrder_nr() - 1);
        });
        sets.add(selection_set);

        tblvExerciseTable.getItems().clear();
        tblvExerciseTable.setItems(sets);
        tblvExerciseTable.sort();
        clearSelection();
    }

    @FXML
    private void onClickDown(ActionEvent event) {
        ObservableList<ExerciseSet> sets =
                FXCollections.observableArrayList(
                        tblvExerciseTable.getItems()
                );

        sets.remove(selection_set);
        sets.stream().filter(set -> set.getOrder_nr() - 1 == selection_set.getOrder_nr()).forEach(set -> {
            set.setOrder_nr(set.getOrder_nr() - 1);
            selection_set.setOrder_nr(selection_set.getOrder_nr() + 1);
        });
        sets.add(selection_set);

        tblvExerciseTable.getItems().clear();
        tblvExerciseTable.setItems(sets);
        tblvExerciseTable.sort();
        clearSelection();
    }

    @FXML
    void onClickEdit(ActionEvent event) {
        List<ExerciseSet> sets = launchDialog(selection_set, false);
        if (sets != null) {
            tblvExerciseTable.getItems().remove(selection_set);
            tblvExerciseTable.getItems().addAll(sets);
            clearSelection();
            tblvExerciseTable.sort();
        }
    }

    @FXML
    void onClickDelete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("L\u00f6schen best\u00e4tigen");
        alert.setHeaderText("Wollen Sie die \u00dcbung wirklich aus der Session l\u00f6schen?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            tblvExerciseTable.getItems().remove(selection_set);

            List<ExerciseSet> sets = tblvExerciseTable.getItems();
            if (sets != null) {
                for (int i = 0; i < sets.size(); i++) {
                    ExerciseSet set = sets.get(i);
                    set.setOrder_nr(i + 1);
                }
            }

            clearSelection();
        }
    }

    @FXML
    void OnClickAdd(ActionEvent event) {
        ExerciseSet set = new ExerciseSet();
        set.setExercise(selection_exercise);
        List<ExerciseSet> sets = launchDialog(set, true);
        if (sets != null) {
            tblvExerciseTable.getItems().addAll(sets);
            clearSelection();
        }
    }

    public Exercise getExercise() {
        return selection_exercise;
    }

    @FXML
    void onClickShow(ActionEvent event) {
        LOGGER.debug("exercise to display: " + selection_exercise);

        mainFrame.navigateToChild(PageEnum.DisplayExercise);
    }

    @Override
    public void initController() {
        session_interClassCommunication = ((Create_Edit_WorkoutPlanController) this.getParentController()).getSession_interClassCommunication();
        try {
            tblcOrder.setCellValueFactory(new PropertyValueFactory<>("order_nr"));
            tblcExercise.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getExercise().getName()));
            tblcType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRepeat() +
                    ((p.getValue().getType() == ExerciseSet.SetType.repeat) ? " x" : " sek")));

            tblvExerciseTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != null) {
                    selection_set = new ExerciseSet(newValue);
                    btnDelete.setDisable(false);
                    btnUp.setDisable(false);
                    btnDown.setDisable(false);
                    btnEdit.setDisable(false);
                }
            });

            if (session_interClassCommunication != null) {
                txtName.setText(session_interClassCommunication.getName());
                ObservableList<ExerciseSet> data =
                        FXCollections.observableArrayList(
                                session_interClassCommunication.getExerciseSets()
                        );

                tblvExerciseTable.setItems(data);

            }
            tblvExerciseTable.sortPolicyProperty().set(t -> {
                Comparator<ExerciseSet> comparator = (r1, r2) -> r1.getOrder_nr() < r2.getOrder_nr() ? -1 : 1;
                FXCollections.sort(tblvExerciseTable.getItems(), comparator);
                return true;
            });

            tblcOrder.setSortType(TableColumn.SortType.ASCENDING);

            tblcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tblcCalo.setCellValueFactory(new PropertyValueFactory<>("calories"));
            tblcCat.setCellValueFactory(p -> {
                List<AbsractCategory> categories = p.getValue().getCategories();
                List<TrainingsCategory> trainingsCategories = categories.stream()
                        .filter(absractCategory -> absractCategory instanceof TrainingsCategory)
                        .map(absractCategory -> (TrainingsCategory) absractCategory)
                        .collect(Collectors.toList());

                String value = "";
                for (int i = 0; i < trainingsCategories.size(); i++) {
                    TrainingsCategory category = trainingsCategories.get(i);
                    if (i + 1 == trainingsCategories.size()) {
                        value += category.getName();
                    } else {
                        value += category.getName() + ", ";
                    }
                }
                return new SimpleStringProperty(value);
            });

            masterdata = FXCollections.observableArrayList(
                    exerciseService.findAll()
            );

            tblvExercises.setItems(masterdata);

            tblvExercises.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != null) {
                    selection_exercise = newValue;
                    btnShow.setDisable(false);
                    btnAdd.setDisable(false);
                }
            });

            txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
                updateFilteredData();
            });
        } catch (ServiceException e) {
            LOGGER.error("Error opening SetStage, Errormessage: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim \u00f6ffnen des Fensters!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateFilteredData() {
        ObservableList<Exercise> filteredData = FXCollections.observableArrayList(masterdata);
        ObservableList<Exercise> temp = FXCollections.observableArrayList();
        temp.addAll(filteredData.stream().filter(this::matchesFilter).collect(Collectors.toList()));

        tblvExercises.setItems(temp);
    }

    private boolean matchesFilter(Exercise e) {
        String filter = txtFilter.getText();
        return txtFilter.getText() == null || filter.isEmpty() || e.getName().toLowerCase().contains(filter.toLowerCase());

    }

    private void clearSelection() {
        selection_set = null;

        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        btnUp.setDisable(true);
        btnDown.setDisable(true);

        tblvExerciseTable.getSelectionModel().clearSelection();

    }

    private List<ExerciseSet> launchDialog(ExerciseSet set, boolean add) {
        // Create the custom dialog.
        Dialog<Pair<String, Pair<String, ExerciseSet.SetType>>> dialog = new Dialog<>();

        String header = "\u00dcbung '" + (add ? (set.getExercise().getName() + "' hinzf\u00fcgen") : (set.getExercise().getName() + "' bearbeiten"));
        dialog.setHeaderText(header);

        // Set the button types.
        ButtonType finishButtonType = new ButtonType("Fertig", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(finishButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField count = new TextField();
        count.setPromptText("3");
        count.setText("1");
        TextField repeat_count = new TextField();
        repeat_count.setPromptText("10");
        if (set.getRepeat() != null) repeat_count.setText(String.valueOf(set.getRepeat()));
        HBox box = new HBox();
        RadioButton repeat = new RadioButton("Wiederholungen");
        RadioButton minutes = new RadioButton("Minuten");

        if (set.getType() != null) {
            if (set.getType() == ExerciseSet.SetType.repeat) repeat.setSelected(true);
            else minutes.setSelected(true);
        } else repeat.setSelected(true);

        ToggleGroup group = new ToggleGroup();
        repeat.setToggleGroup(group);
        minutes.setToggleGroup(group);
        box.getChildren().addAll(repeat, minutes);

        if (add) {
            grid.add(new Label("S\u00e4tze:"), 1, 0);
            grid.add(count, 0, 0);
        }
        grid.add(repeat_count, 0, 1);
        grid.add(box, 1, 1);

        Node finishButton = dialog.getDialogPane().lookupButton(finishButtonType);
        repeat_count.textProperty().addListener((observable, oldValue, newValue) -> {
            finishButton.setDisable(newValue.trim().isEmpty());
        });

        if (add) {
            finishButton.setDisable(true);
        }

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(repeat_count::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == finishButtonType) {
                String repeat_type = ((RadioButton) group.getSelectedToggle()).getText();

                ExerciseSet.SetType setType;

                if (repeat_type.equals("Wiederholungen")) {
                    setType = ExerciseSet.SetType.repeat;
                } else {
                    setType = ExerciseSet.SetType.time;
                }
                return new Pair<>(count.getText(), new Pair<>(repeat_count.getText(), setType));
            }
            return null;
        });

        Optional<Pair<String, Pair<String, ExerciseSet.SetType>>> result = dialog.showAndWait();

        final String[] result_count = new String[1];
        final String[] result_repeat_count = new String[1];
        final ExerciseSet.SetType[] result_setTexe = new ExerciseSet.SetType[1];

        if (result.isPresent()) {

            result.ifPresent(dialogresult -> {
                result_count[0] = dialogresult.getKey();
                result_repeat_count[0] = dialogresult.getValue().getKey();
                result_setTexe[0] = dialogresult.getValue().getValue();
            });

            set = createValidSet(set, result_repeat_count[0], result_setTexe[0]);

            List<ExerciseSet> sets = new ArrayList<>();
            Integer repeat_int;

            try {
                repeat_int = Integer.parseInt(result_count[0]);
            } catch (NumberFormatException e) {
                repeat_int = 1;
            }

            if (set != null) {
                for (int i = 0; i < repeat_int; i++) {
                    ExerciseSet newset = new ExerciseSet(set);
                    newset.setOrder_nr(set.getOrder_nr() + i);
                    sets.add(newset);
                }
            }
            return sets;
        } else {
            return null;
        }
    }

    private ExerciseSet createValidSet(ExerciseSet set, String repeat, ExerciseSet.SetType setType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Falsche Daten!");
        boolean error = false;
        String errormessage = "";

        Integer repeat_int = null;

        if (repeat == null || repeat.equals("")) {
            error = true;
            errormessage = "Keine Wiederholungen oder Zeit angegeben!";
        } else {
            try {
                repeat_int = Integer.parseInt(repeat);
            } catch (NumberFormatException e) {
                error = true;
                errormessage = setType.name() + " muss eine ganzzahlige Zahl sein!";
            }
        }

        if (error) {
            alert.setContentText(errormessage);
            alert.showAndWait();
            return null;
        } else {
            Integer order_nr;

            if (set.getOrder_nr() != null) {
                order_nr = set.getOrder_nr();
            } else {
                order_nr = tblvExerciseTable.getItems().size() + 1;
            }
            return new ExerciseSet(null, set.getExercise(), userService.getLoggedInUser(),
                    repeat_int, setType, order_nr, false);
        }

    }

    public void setExerciseService(ExerciseServiceImpl exerciseService) {
        this.exerciseService = exerciseService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
}


