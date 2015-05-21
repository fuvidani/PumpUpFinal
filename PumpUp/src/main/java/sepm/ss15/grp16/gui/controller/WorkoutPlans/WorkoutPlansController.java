package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.training.impl.TrainingsPlanServiceImpl;
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
	private static final Logger LOGGER = LogManager.getLogger(WorkoutPlansController.class);

	private TrainingsplanService trainingsplanService;
	private UserService userService;

	private Trainingsplan selection;

	private StageTransitionLoader transitionLoader;

	@FXML
	private CheckBox defaultWorkoutPlansCheck;

	@FXML
	private CheckBox customWorkoutPlansCheck;

	@FXML
	private Label trainingNameLabel;

	@FXML
	private TextArea trainingDescr;

	@FXML
	private Text txtDuration;

	@FXML
	private Text txtCal_sum;

	@FXML
	private Text txtCal_mean;

	@FXML
	private Text txtCat;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.transitionLoader = new StageTransitionLoader(this);

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
								calenderBtn.setDisable(false);
							} else {
								deleteBtn.setDisable(true);
								editBtn.setDisable(true);
								calenderBtn.setDisable(true);
							}
							copyBtn.setDisable(false);
							updateInformations(new_val);

						}
					});

			setUpListView();
			workoutPlansListView.setItems(data);


		} catch (ServiceException e) {
			e.printStackTrace();
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
						if (category instanceof TrainingsCategory && ! trainingsCategoryList.contains(category)) {
							trainingsCategoryList.add((TrainingsCategory) category);
						} else if (category instanceof EquipmentCategory && ! equipmentCategoryList.contains(category)) {
							equipmentCategoryList.add((EquipmentCategory) category);
						} else if (category instanceof MusclegroupCategory && ! musclegroupCategories.contains(category)) {
							musclegroupCategories.add((MusclegroupCategory) category);
						}
					}
				}
			}
			int calories_mean = calories_sum / plan.getTrainingsSessions().size();
			txtCal_sum.setText(String.valueOf(calories_sum));
			txtCal_mean.setText(String.valueOf(calories_mean));

			String value = null;
			if (!trainingsCategoryList.isEmpty()) {
				value = "Art: \n";
				for (TrainingsCategory category : trainingsCategoryList) {
					value += "    - " + category.getName() + "\n";
				}
			}
			if (!equipmentCategoryList.isEmpty()) {
				value += " \nGe\u00e4rte: \n";
				for (EquipmentCategory category : equipmentCategoryList) {
					value += "    - " + category.getName() + "\n";
				}
			}
			if (!musclegroupCategories.isEmpty()) {
				value += " \nMuskeln: \n";
				for (MusclegroupCategory category : musclegroupCategories) {
					value += "    - " + category.getName() + "\n";
				}
			}

			txtCat.setText(value);
		} else  {
			txtCat.setText("");
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


		/*if (sessions.get(0) != null) {
			for (int i = 0; i < sessions.get(0).size(); i++) {
				TableColumn<List<TrainingsSession>, String> col = new TableColumn<>(sessions.get(0).get(i).getName());

				final int k = i;
				col.setCellValueFactory(p -> {
					List<ExerciseSet> sets = p.getValue().get(k).getExerciseSets();
					String value = "";
					if (sets != null) {
						for (ExerciseSet set : sets) {
							value += set.getOrder_nr() + ": " + set.getRepeat() + " " + set.getExercise().getName() + "\n";
						}
					} else {
						value += "";
					}
					return new SimpleStringProperty(value);
				});
				col.setMinWidth(200);

				workoutPlanTable.getColumns().add(col);
			}
		}
		workoutPlanTable.setPlaceholder(new Label("Trainingsplan enth\u00e4lt keine Sessions"));*/
	}

	@FXML
	void filterUserPlans(ActionEvent event) {
		try {
			List<Trainingsplan> list;
			if (customWorkoutPlansCheck.isSelected()) {
				User user = userService.getLoggedInUser();
				//User user = new User(1, null, null, null, null, null);

				list = trainingsplanService.find(new Trainingsplan(null, user, null, null, null, null, null));
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

							for (ExerciseSet set : t.getExerciseSets()) {
								value += set.getOrder_nr() + ": " + set.getRepeat() + " " + set.getExercise().getName() + "\n";
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
	void newWorkoutPlanClicked(ActionEvent event) {
		Create_Edit_WorkoutPlanController.plan_interClassCommunication = null;

		Stage thiststage = (Stage) listViewSessions.getScene().getWindow();
		thiststage.hide();
		transitionLoader.openWaitStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(), "Trainingsplan erstellen", 1300, 700, false);
		thiststage.show();

		updateTable();
		setUpListView();
		clearSelection();
	}

	@FXML
	void copyWorkoutPlanClicked(ActionEvent event) {
		Trainingsplan toCopy = new Trainingsplan(selection);
		toCopy.setId(null);
		Create_Edit_WorkoutPlanController.plan_interClassCommunication = toCopy;
		transitionLoader.openWaitStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(),
				"Trainingsplan " + selection.getName() + " kopieren", 1000, 620, true);
		updateTable();
		setUpListView();
		clearSelection();
	}

	@FXML
	void generateWorkoutPlanClicked(ActionEvent event) {
		transitionLoader.openWaitStage("fxml/GenerateWorkoutPlan.fxml", (Stage) listViewSessions.getScene().getWindow(), "Trainingsplan generieren", 600, 400, false);
		updateTable();
		setUpListView();
		clearSelection();
	}

	@FXML
	void deleteWorkoutPlanClicked(ActionEvent event) {
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
			e.printStackTrace();
		}
	}

	@FXML
	void editWorkoutPlanClicked(ActionEvent event) {
		Stage thiststage = (Stage) listViewSessions.getScene().getWindow();
		Create_Edit_WorkoutPlanController.plan_interClassCommunication = selection;

		transitionLoader.openWaitStage("fxml/Create_Edit_WorkoutPlans.fxml", (Stage) listViewSessions.getScene().getWindow(),
				"Trainingsplan " + selection.getName() + " bearbeiten", 1000, 620, true);

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
			e.printStackTrace();
		}
	}

	@FXML
	void embedInCalenderClicked(ActionEvent event) {
		WorkoutPlanToCalendarController.plan_interClassCommunication = selection;
		transitionLoader.openWaitStage("fxml/WorkoutPlanIntoCalendar.fxml", (Stage) listViewSessions.getScene().getWindow(), "Trainingsplan in Kalender exportieren", 800, 600, false);
	}

	@FXML
	void getBackClicked(ActionEvent event) {
		this.stage.close();
	}


	public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
		this.trainingsplanService = trainingsplanService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
