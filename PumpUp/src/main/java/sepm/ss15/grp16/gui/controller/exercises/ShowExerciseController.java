package sepm.ss15.grp16.gui.controller.exercises;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.*;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.impl.CategoryServiceImpl;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;
import sepm.ss15.grp16.service.impl.UserServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowExerciseController extends Controller implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(ShowExerciseController.class);

	private StageTransitionLoader transitionLoader;

	private ExercisesController exerciseController;
	private ExerciseServiceImpl exerciseService;
	private CategoryServiceImpl categoryService;
	private UserServiceImpl userService;

	public static Exercise exercise_interClassCommunication;

	private List<CheckBox> allCheckboxes = new ArrayList<>();
	private ObservableList<CheckBox> checkboxes = FXCollections.observableArrayList();
	private List<String> exerciseGifList = new ArrayList<>();
	private ObservableList<String> observablePicListData = FXCollections.observableArrayList();
	private String picture;

	@FXML
	private VBox vboxMuscle;

	@FXML
	private TextArea descriptionArea;

	@FXML
	private Button btn_durchsuchen;

	@FXML
	private WebView webViewVideo;

	@FXML
	private VBox vboxType;

	@FXML
	private TextField caloriesField;

	@FXML
	private ListView<String> imagesListView;

	@FXML
	private TextField exerciseNameField;

	@FXML
	private Button btn_entfernen;

	@FXML
	private ImageView imageView;

	@FXML
	private TextField videoLinkField;

	@FXML
	private VBox vboxEquipment;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//dynamisches laden von checkboxen
		try {
			webViewVideo.setVisible(false);
			for (TrainingsCategory t : categoryService.getAllTrainingstype()) {
				CheckBox box = new CheckBox(t.getName());
				box.setId("" + t.getId());
				LOGGER.debug("trainingsboxID: " + box.getId());
				box.setDisable(true);
				checkboxes.add(box);
				allCheckboxes.add(box);

			}
			vboxType.getChildren().addAll(checkboxes);
			checkboxes.clear();

			for (MusclegroupCategory m : categoryService.getAllMusclegroup()) {
				CheckBox box = new CheckBox(m.getName());
				box.setId("" + m.getId());
				LOGGER.debug("muscleboxID: " + box.getId());
				box.setDisable(true);
				checkboxes.add(box);
				allCheckboxes.add(box);
			}
			vboxMuscle.getChildren().addAll(checkboxes);
			checkboxes.clear();


			for (EquipmentCategory e : categoryService.getAllEquipment()) {
				CheckBox box = new CheckBox(e.getName());
				box.setId("" + e.getId());
				LOGGER.debug("equipmentsboxID: " + box.getId());
				box.setDisable(true);
				checkboxes.add(box);
				allCheckboxes.add(box);

			}
			vboxEquipment.getChildren().addAll(checkboxes);
			checkboxes.clear();

		} catch (ServiceException e) {
			LOGGER.error(e);
			e.printStackTrace();
		}
		imagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			showPic(oldValue, newValue);
		});
		setContent();
	}

	private void showPic(String oldValue, String newValue) {
		try {
			File file;
			InputStream inputStream;

			if (newValue == null && oldValue != null) {
				newValue = oldValue;
			}

			if (newValue == null)
				return;

			if (observablePicListData.isEmpty())
				return;

			String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
			if (newValue.contains("img_ex")) {
				file = new File(pathToResource + "/" + newValue);
				picture = file.getName();
			} else {
				file = new File(newValue);
			}

			LOGGER.debug(picture);

			inputStream = new FileInputStream(file);
			Image img = new Image(inputStream);
			imageView.setImage(img);
			inputStream.close();
		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
		}
	}

	private void setContent() {
		if (exercise_interClassCommunication != null) { //update called
			observablePicListData.removeAll();
			caloriesField.setText("" + exercise_interClassCommunication.getCalories());
			videoLinkField.setText(exercise_interClassCommunication.getVideolink());
			exerciseNameField.setText(exercise_interClassCommunication.getName());
			descriptionArea.setText(exercise_interClassCommunication.getDescription());
			exerciseGifList = exercise_interClassCommunication.getGifLinks();
			observablePicListData.addAll(exerciseGifList);
			imagesListView.setItems(observablePicListData);

			if (!observablePicListData.isEmpty()) {
				showPic(observablePicListData.get(0), observablePicListData.get(0));
			}

			for (AbsractCategory c : exercise_interClassCommunication.getCategories()) {
				allCheckboxes.get(c.getId()).setSelected(true);
			}
		}
	}

	@FXML
	void finishedClicked(ActionEvent event) {
		exercise_interClassCommunication = null;
		this.stage.close();
	}

	@FXML
	void playVideo(ActionEvent event) {
		webViewVideo.getEngine().load(videoLinkField.getText());
	}

	public void setExerciseController(ExercisesController exerciseController) {
		this.exerciseController = exerciseController;
	}

	public void setExerciseService(ExerciseServiceImpl exerciseService) {
		this.exerciseService = exerciseService;
	}

	public void setCategoryService(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
}
