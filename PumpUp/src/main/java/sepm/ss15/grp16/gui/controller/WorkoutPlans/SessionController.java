package sepm.ss15.grp16.gui.controller.WorkoutPlans;

/**
 * Author: Lukas
 * Date: 15.05.2015
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.Exercises.ShowExerciseController;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.impl.UserServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionController extends Controller implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

	private StageTransitionLoader transitionLoader;

	private UserServiceImpl userService;

	public static TrainingsSession session_interClassCommunication;
	public static ExerciseSet set_interClassCommunication;

	private static ExerciseSet selection;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnShow;

	@FXML
	private TextField txtName;

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
	private Button btnCancle;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.transitionLoader = new StageTransitionLoader(this);

		tblcOrder.setCellValueFactory(new PropertyValueFactory<>("order_nr"));
		tblcExercise.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getExercise().getName()));
		tblcType.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRepeat() +
				((p.getValue().getType() == ExerciseSet.SetType.repeat) ? " x" : " min")));

		tblvExerciseTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue != null) {
				selection = new ExerciseSet(newValue);
				btnShow.setDisable(false);
				btnDelete.setDisable(false);
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
	}

	@FXML
	void onClickSave(ActionEvent event) {
		TrainingsSession session = createValidSession();
		if (session != null) {
			Create_Edit_WorkoutPlanController.session_interClassCommunication = session;
			this.stage.close();
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

	private void clearSelection() {
		selection = null;

		btnShow.setDisable(true);
		btnDelete.setDisable(true);

		tblvExerciseTable.getSelectionModel().clearSelection();

	}

	@FXML
	void onClickCancel(ActionEvent event) {
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
			this.stage.close();
		}
	}

	@FXML
	void onClickShow(ActionEvent event) {
		ShowExerciseController.exercise_interClassCommunication = selection.getExercise();
		transitionLoader.openWaitStage("fxml/ShowExercise.fxml", (Stage) tblvExerciseTable.getScene().getWindow(), selection.getExercise().getName(), 500, 500, true);
	}

	@FXML
	void onClickAdd(ActionEvent event) {
		SetController.session_interClassCommunication = session_interClassCommunication;
		transitionLoader.openWaitStage("fxml/ExerciseSet.fxml", (Stage) tblvExerciseTable.getScene().getWindow(), "\u00dcbung hinzuf\u00fcgen", 500, 500, false);

		if (set_interClassCommunication != null) {
			tblvExerciseTable.getItems().add(set_interClassCommunication);
			List<ExerciseSet> sets = tblvExerciseTable.getItems();
			if (sets != null) {
				for (int i = 0; i < sets.size(); i++) {
					ExerciseSet set = sets.get(i);
					set.setOrder_nr(i + 1);
				}
			}
		}
		set_interClassCommunication = null;
	}

	@FXML
	void onClickEdit(ActionEvent event) {
		transitionLoader.openWaitStage("fxml/ExerciseSet.fxml", (Stage) tblvExerciseTable.getScene().getWindow(), "\u00dcbung bearbeiten", 500, 500, false);

		if (set_interClassCommunication != null) {
			tblvExerciseTable.getItems().remove(selection);
			tblvExerciseTable.getItems().add(set_interClassCommunication);
		}
		set_interClassCommunication = null;
		clearSelection();
	}

	@FXML
	void onClickDelete(ActionEvent event) {
		tblvExerciseTable.getItems().remove(selection);

		List<ExerciseSet> sets = tblvExerciseTable.getItems();
		if (sets != null) {
			for (int i = 0; i < sets.size(); i++) {
				ExerciseSet set = sets.get(i);
				set.setOrder_nr(i + 1);
			}
		}

		clearSelection();
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public Exercise getExercise() {
		return selection.getExercise();
	}
}
