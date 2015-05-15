package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;
import sepm.ss15.grp16.service.impl.UserServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Author: Lukas
 * Date: 15.05.2015
 */
public class SetController extends Controller implements Initializable {
	private UserServiceImpl userService;
	private ExerciseServiceImpl exerciseService;

	private Exercise selection;

	public static TrainingsSession session_interClassCommunication;

	@FXML
	private Button btnShow;

	@FXML
	private RadioButton rdRepeats;

	@FXML
	private TextField txtRepeat;

	@FXML
	private ToggleGroup tglGrType;

	@FXML
	private RadioButton rdMinutes;

	@FXML
	private TableView<Exercise> tblvExercises;

	@FXML
	private TableColumn<Exercise, String> tblcName;

	@FXML
	private TableColumn<Exercise, Integer> tblcCalo;

	@FXML
	private TableColumn<Exercise, String> tblcCat;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {

			tblcName.setCellValueFactory(new PropertyValueFactory<>("name"));
			tblcCalo.setCellValueFactory(new PropertyValueFactory<>("calories"));

			ObservableList<Exercise> data = FXCollections.observableArrayList(
					exerciseService.findAll()
			);

			tblvExercises.setItems(data);

			tblvExercises.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
				if (newValue != null) {
					selection = newValue;
					btnShow.setDisable(false);
				}
			});

		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickShow(ActionEvent event) {

	}

	@FXML
	void onClickSave(ActionEvent event) {
		ExerciseSet set = createValidSet();

		if (set != null) {
			SessionController.set_interClassCommunication = set;
			this.stage.close();
		}
	}

	private ExerciseSet createValidSet() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Fahler");
		alert.setHeaderText("Falsche Daten!");
		boolean error = false;
		String errormessage = "";

		String repeat = txtRepeat.getText();

		if (repeat == null || repeat.equals("")) {
			error = true;
			errormessage = "Keine Wiederholungen oder Zeit angegeben!";
		}

		if (error) {
			alert.setContentText(errormessage);
			alert.showAndWait();
			return null;
		} else {

			Integer repeat_int = Integer.parseInt(repeat);

			return new ExerciseSet(session_interClassCommunication.getId(), selection, userService.getLoggedInUser(),
					repeat_int, session_interClassCommunication.getExerciseSets().size()+1, false);
		}
	}

	@FXML
	void onClickCancle(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Änderungen verwerfen");
		alert.setHeaderText("Wollen Sie wirklich abbrechen?");
		alert.setContentText("Alle Änderungen würden verlorgen gehen!");
		ButtonType yes = new ButtonType("Ja");
		ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(yes, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yes) {
			this.stage.close();
		}
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public void setExerciseService(ExerciseServiceImpl exerciseService) {
		this.exerciseService = exerciseService;
	}
}
