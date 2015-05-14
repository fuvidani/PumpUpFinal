package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.Training.TrainingsplanService;
import sepm.ss15.grp16.service.Training.impl.TrainingsPlanServiceImpl;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is the central point of all workout plans assigned to the user.
 */
public class WorkoutPlansController extends Controller implements Initializable {
	private TrainingsplanService trainingsplanService;
	private UserService userService;

	private Trainingsplan selection;

	private StageTransitionLoader transitionLoader;

	@FXML
	private CheckBox defaultWorkoutPlansCheck;

	@FXML
	private CheckBox customWorkoutPlansCheck;

	@FXML
	private TableView<List<TrainingsSession>> workoutPlanTable;

	@FXML
	private Label trainingNameLabel;

	@FXML
	private TextArea trainingDescr;

	@FXML
	private ListView<Trainingsplan> workoutPlansListView;

	@FXML
	private Button calenderBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private Button copyBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.transitionLoader = new StageTransitionLoader(this);

		try {
			ObservableList<Trainingsplan> data =
					FXCollections.observableArrayList(
							trainingsplanService.findAll()
					);

			workoutPlansListView.setCellFactory(new Callback<ListView<Trainingsplan>, ListCell<Trainingsplan>>() {
				@Override
				public ListCell<Trainingsplan> call(ListView<Trainingsplan> p) {
					return new ListCell<Trainingsplan>() {
						@Override
						protected void updateItem(Trainingsplan t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getName());
							}
						}

					};
				}
			});

			workoutPlansListView.getSelectionModel().selectedItemProperty().addListener(
					(ov, old_val, new_val) -> {
						if (workoutPlansListView.getSelectionModel().getSelectedItems() != null && new_val != null) {
							List<List<TrainingsSession>> lists = new ArrayList<>();
							lists.add(new_val.getTrainingsSessions());
							updateSessionTable(lists);
							selection = new_val;
							trainingDescr.setText(new_val.getDescr());
							trainingNameLabel.setText(new_val.getName());

							if (selection.getUser() != null) {
								deleteBtn.setDisable(false);
								editBtn.setDisable(false);
							}
							deleteBtn.setDisable(false);
							calenderBtn.setDisable(false);
							copyBtn.setDisable(false);

						}
					});

			workoutPlansListView.setItems(data);

			workoutPlanTable.setPlaceholder(new Label(""));

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private void updateSessionTable(List<List<TrainingsSession>> sessions) {
		ObservableList<List<TrainingsSession>> data = FXCollections.observableArrayList(sessions);

		workoutPlanTable.getItems().clear();
		workoutPlanTable.setItems(data);

		workoutPlanTable.getColumns().clear();

		if (sessions.get(0) != null) {
			for (int i = 0; i < sessions.get(0).size(); i++) {
				TableColumn<List<TrainingsSession>, String> col = new TableColumn<>("Session " + (i + 1));

				final int k = i;
				col.setCellValueFactory(p -> {
					List<ExerciseSet> sets = p.getValue().get(k).getExerciseSets();
					String value = "";
					if (sets != null) {
						for (ExerciseSet set : sets) {
							value += set.getOrder_nr() + ": " + set.getRepeat() + " " + set.getExercise().getName() + "\n";
						}
					}
					return new SimpleStringProperty(value);
				});
				col.setMinWidth(200);

				workoutPlanTable.getColumns().add(col);
			}
		}
		workoutPlanTable.setPlaceholder(new Label("Trainingsplan enthhält keine Sessions"));
	}


	@FXML
	void filterUserPlans(ActionEvent event) {
		try {
			List<Trainingsplan> list;
			if (customWorkoutPlansCheck.isSelected()) {
				//User user = userService.getCurrentUser();
				User user = new User(1, null, null, null, null, null);

				list = trainingsplanService.find(new Trainingsplan(null, user, null, null, null, null));
				if (list == null) list = new ArrayList<>();
			} else {
				list = trainingsplanService.findAll();
			}
			ObservableList<Trainingsplan> data = FXCollections.observableArrayList(list);
			workoutPlansListView.setItems(data);

			clearSelection();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void filterDefPlans(ActionEvent event) {
		try {
			List<Trainingsplan> list;
			if (customWorkoutPlansCheck.isSelected()) {

				list = trainingsplanService.find(new Trainingsplan());
				if (list == null) list = new ArrayList<>();

			} else {
				list = trainingsplanService.findAll();
			}
			ObservableList<Trainingsplan> data = FXCollections.observableArrayList(list);
			workoutPlansListView.setItems(data);

			clearSelection();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	private void clearSelection() {
		selection = null;

		trainingDescr.setText("");
		trainingNameLabel.setText("");

		deleteBtn.setDisable(true);
		editBtn.setDisable(true);
		copyBtn.setDisable(true);
		calenderBtn.setDisable(true);
	}

	@FXML
	void newWorkoutPlanClicked(ActionEvent event) {
		transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) workoutPlanTable.getScene().getWindow(), "Trainingsplan erstellen / bearbeiten", 1000, 620, true);
	}

	@FXML
	void copyWorkoutPlanClicked(ActionEvent event) {
		transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) workoutPlanTable.getScene().getWindow(), "Trainingsplan erstellen / bearbeiten", 1000, 620, true);
	}

	@FXML
	void generateWorkoutPlanClicked(ActionEvent event) {
		transitionLoader.openStage("fxml/GenerateWorkoutPlan.fxml", (Stage) workoutPlanTable.getScene().getWindow(), "Trainingsplan generieren", 600, 400, false);
	}

	@FXML
	void deleteWorkoutPlanClicked(ActionEvent event) {
		try {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText("Sind Sie sicher?");
			alert.setContentText(selection.getName() + " wirklich löschen?");
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
				workoutPlansListView.setItems(data);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void editWorkoutPlanClicked(ActionEvent event) {
		transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) workoutPlanTable.getScene().getWindow(), "Trainingsplan erstellen / bearbeiten", 1000, 620, true);
	}

	@FXML
	void embedInCalenderClicked(ActionEvent event) {
		transitionLoader.openStage("fxml/WorkoutPlanIntoCalendar.fxml", (Stage) workoutPlanTable.getScene().getWindow(), "Trainingsplan in Kalender einfügen", 600, 400, false);
	}

	@FXML
	void getBackClicked(ActionEvent event) {
		this.stage.close();
	}


	public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
		this.trainingsplanService = trainingsplanService;
	}
}
