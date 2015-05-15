package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.Training.TrainingsplanService;
import sepm.ss15.grp16.service.Training.impl.TrainingsPlanServiceImpl;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Create_Edit_WorkoutPlanController extends Controller implements Initializable {
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
	private TextArea txtDescr;

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

		listViewSessions.setOrientation(Orientation.HORIZONTAL);
		setUpListView();

		if (plan_interClassCommunication != null) {
			txtName.setText(plan_interClassCommunication.getName());
			txtDescr.setText((plan_interClassCommunication.getDescr()));

			if (plan_interClassCommunication.getTrainingsSessions() != null) {
				ObservableList<TrainingsSession> data =
						FXCollections.observableArrayList(
								plan_interClassCommunication.getTrainingsSessions()
						);
				listViewSessions.setItems(data);
			}
		}

		listViewSessions.getSelectionModel().selectedItemProperty().addListener(
				(ov, old_val, new_val) -> {
					if (listViewSessions.getSelectionModel().getSelectedItems() != null && new_val != null) {
						selection = new TrainingsSession(new_val);
					}
				});

	}

	@FXML
	void saveWorkoutClicked(ActionEvent event) {

		Trainingsplan trainingsplan = createValidPlan();
		if (trainingsplan != null) {
			try {
				if (plan_interClassCommunication != null) {
					trainingsplanService.update(trainingsplan);
				} else {
					trainingsplanService.create(trainingsplan);
				}
				plan_interClassCommunication = null;
				session_interClassCommunication = null;

				this.stage.close();

			} catch (ServiceException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Fahler");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}

	private Trainingsplan createValidPlan() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Fahler");
		alert.setHeaderText("Falsche Daten!");
		boolean error = false;
		String errormessage = "";

		String name = txtName.getText();
		String descr = txtDescr.getText();

		if (name == null || name.equals("")) {
			error = true;
			errormessage = "Name ist leer!";
		}

		List<TrainingsSession> data = listViewSessions.getItems();

		if (data == null || data.isEmpty()) {
			error = true;
			errormessage = "Keine Sessions hinzugefügt!";
		}

		if (error) {
			alert.setContentText(errormessage);
			alert.showAndWait();
			return null;
		} else {

			if (plan_interClassCommunication != null) {
				return new Trainingsplan(plan_interClassCommunication.getId(), userService.getLoggedInUser(), name, descr, false, data);
			} else {
				return new Trainingsplan(null, userService.getLoggedInUser(), name, descr, false, data);
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
						if (t != null) {
							String value = t.getName() + "\n\n";
							for (ExerciseSet set : t.getExerciseSets()) {
								value += set.getOrder_nr() + ": " + set.getRepeat() + " " + set.getExercise().getName() + "\n";
							}
							setText(value);
						} else {
							setText("");
						}
					}

				};
			}
		});
	}

	@FXML
	void cancelClicked(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Änderungen verwerfen");
		alert.setHeaderText("Wollen Sie wirklich abbrechen?");
		alert.setContentText("Alle Änderungen würden verlorgen gehen!");
		ButtonType yes = new ButtonType("Ja");
		ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(yes, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes) {
			plan_interClassCommunication = null;
			this.stage.close();
		}
	}

	@FXML
	void addSession(ActionEvent event) {
		transitionLoader.openWaitStage("fxml/Session.fxml", (Stage) listViewSessions.getScene().getWindow(), "Session hinzufügen", 600, 400, false);
		listViewSessions.getItems().add(session_interClassCommunication);
		setUpListView();
	}

	@FXML
	void editSession(ActionEvent event) {
		if (selection != null) {
			SessionController.session_interClassCommunication = selection;
		}

		transitionLoader.openWaitStage("fxml/Session.fxml", (Stage) listViewSessions.getScene().getWindow(), "Session bearbeiten", 600, 400, false);
		if (session_interClassCommunication != null) {
			listViewSessions.getItems().remove(selection);
			listViewSessions.getItems().add(session_interClassCommunication);
			session_interClassCommunication = null;
			setUpListView();
		}
	}

	@FXML
	void deleteSession(ActionEvent event) {
		List<TrainingsSession> data = listViewSessions.getItems();

		if (selection != null) {
			listViewSessions.getSelectionModel().clearSelection();
			data.remove(data.indexOf(selection));
		}
	}

	public void setTrainingsplanService(TrainingsPlanServiceImpl trainingsplanService) {
		this.trainingsplanService = trainingsplanService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
