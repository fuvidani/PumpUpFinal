package sepm.ss15.grp16.gui.controller.workoutPlans;

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
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.training.impl.TrainingsPlanServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Create_Edit_WorkoutPlanController extends Controller implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(Create_Edit_WorkoutPlanController.class);

	private StageTransitionLoader transitionLoader;

	private TrainingsplanService trainingsplanService;
	private UserService userService;

	public static Trainingsplan plan_interClassCommunication;
	public static TrainingsSession session_interClassCommunication;
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
	private Text txtCal_sum;

	@FXML
	private Text txtCal_mean;

	@FXML
	private Text txtCat;

	@FXML
	private Button btnDeleteSession;

	@FXML
	private ListView<TrainingsSession> listViewSessions;

	@FXML
	private Button btnAddSession;

	@FXML
	private Label lblTitel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.transitionLoader = new StageTransitionLoader(this);

		setUpListView();

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
					}
				});
	}

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
			int calories_mean = calories_sum / plan_interClassCommunication.getTrainingsSessions().size();
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
		}
	}

	@FXML
	void saveWorkoutClicked(ActionEvent event) {

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

				this.stage.close();

			} catch (ServiceException e) {
				LOGGER.error("Error opening Create_Edit_Stage, Errormessage: " + e);
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Fehler beim \u00f6ffnen des Fensters!");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				e.printStackTrace();
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
							//String value = t.getName() + "\n\n";
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

	@FXML
	void cancelClicked(ActionEvent event) {
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
			this.stage.close();
		}
	}

	@FXML
	void addSession(ActionEvent event) {
		transitionLoader.openWaitStage("fxml/workoutPlans/Session.fxml", (Stage) listViewSessions.getScene().getWindow(), "Session hinzuf\u00fcgen", 600, 400, false);
		if (session_interClassCommunication != null) {
			listViewSessions.getItems().add(session_interClassCommunication);
			setUpListView();
			updateInformations();
		}
	}

	@FXML
	void editSession(ActionEvent event) {
		if (selection != null) {
			SessionController.session_interClassCommunication = selection;
		}

		transitionLoader.openWaitStage("fxml/workoutPlans/Session.fxml", (Stage) listViewSessions.getScene().getWindow(), "Session bearbeiten", 600, 400, false);
		if (session_interClassCommunication != null) {
			listViewSessions.getItems().remove(selection);
			listViewSessions.getItems().add(session_interClassCommunication);
			session_interClassCommunication = null;
			setUpListView();
			updateInformations();
		}
	}

	@FXML
	void deleteSession(ActionEvent event) {
		List<TrainingsSession> data = listViewSessions.getItems();

		if (selection != null) {
			listViewSessions.getSelectionModel().clearSelection();
			data.remove(data.indexOf(selection));
			updateInformations();
		}
	}

	public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
		this.trainingsplanService = trainingsplanService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
