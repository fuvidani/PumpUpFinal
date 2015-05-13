package sepm.ss15.grp16.gui.controller.Exercises;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.CategoryService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the stage where the user can create
 * m new exercise or edit an existing one.
 */
public class ManageExerciseController extends Controller implements Initializable{

    @FXML
    private AnchorPane pane;

    @FXML
    private CheckBox punchBagCheck;

    @FXML
    private ImageView imageView;

    @FXML
    private CheckBox chestCheck;

    @FXML
    private TextField videoLinkField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private CheckBox tricepCheck;

    @FXML
    private TextField caloriesField;

    @FXML
    private CheckBox legsCheck;

    @FXML
    private TextField durationOfQuantityField;

    @FXML
    private CheckBox abWheelCheck;

    @FXML
    private RadioButton quantityTypeRadio;

    @FXML
    private CheckBox balanceCheck;

    @FXML
    private CheckBox shoulderCheck;

    @FXML
    private CheckBox jumpRopeCheck;

    @FXML
    private RadioButton secondsTypeRadio;

    @FXML
    private CheckBox enduranceCheck;

    @FXML
    private CheckBox strengthCheck;

    @FXML
    private CheckBox flexibilityCheck;

    @FXML
    private CheckBox backCheck;

    @FXML
    private ListView<String> imagesListView;

    @FXML
    private CheckBox exerciseBallCheck;

    @FXML
    private CheckBox barCheck;

    @FXML
    private CheckBox dumbbellCheck;

    @FXML
    private CheckBox expanderCheck;

    @FXML
    private CheckBox abdominalCheck;

    @FXML
    private TextField exerciseNameField;

    @FXML
    private CheckBox bicepCheck;

    @FXML
    private TextField durationField;

    @FXML
    private Button btn_durchsuchen;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vboxType;

    @FXML
    private VBox vboxMuscle;

    @FXML
    private VBox vboxEquipment;


    private Service<Exercise> exerciseService;
    private CategoryService categoryService;
    private static final Logger LOGGER = LogManager.getLogger();
    private List<String> exerciseGifList = new ArrayList<>();
    private ObservableList<String> observablePicListData = FXCollections.observableArrayList();

    private ExercisesController exerciseController;
    private Exercise exercise = null;
    private String picture;

    private ObservableList<CheckBox> checkboxes = FXCollections.observableArrayList();
    private List<CheckBox> allCheckboxes = new ArrayList<>();

    public void setExerciseService(Service<Exercise> exerciseService){
        this.exerciseService=exerciseService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setExerciseController(ExercisesController exerciseController){
        this.exerciseController=exerciseController;
        exercise=exerciseController.getExercise();
        //TODO ueber spring mit bean loesen
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //dynamisches laden von checkboxen
        try {
            for (MusclegroupCategory m : categoryService.getAllMusclegroup()){
                CheckBox box = new CheckBox(m.getName());
                box.setId(""+m.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);
            }
            vboxMuscle.getChildren().addAll(checkboxes);
            checkboxes.clear();
            for(TrainingsCategory t : categoryService.getAllTrainingstype()){
                CheckBox box = new CheckBox(t.getName());
                box.setId(""+t.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);

            }
            vboxType.getChildren().addAll(checkboxes);
            checkboxes.clear();

            for(EquipmentCategory e : categoryService.getAllEquipment()){
                CheckBox box = new CheckBox(e.getName());
                box.setId(""+e.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);

            }
            vboxEquipment.getChildren().addAll(checkboxes);
            checkboxes.clear();
        }catch (ServiceException e){
            LOGGER.error(e);
            e.printStackTrace();
        }
        imagesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showPic(oldValue, newValue);
            }
        });
        setContent();
    }

    private void setContent(){
        if(exercise!=null) { //update called
            observablePicListData.removeAll();
            caloriesField.setText("" + exercise.getCalories());
            videoLinkField.setText(exercise.getVideolink());
            exerciseNameField.setText(exercise.getName());
            descriptionArea.setText(exercise.getDescription());
            exerciseGifList = exercise.getGifLinks();
            observablePicListData.addAll(exerciseGifList);
            imagesListView.setItems(observablePicListData);
            for(AbsractCategory c : exercise.getCategories()){
                allCheckboxes.get(c.getId()).setSelected(true);
            }
        }
    }


    private void showPic(String oldValue, String newValue){
        try {
            File file;
            InputStream inputStream;

            if(newValue == null && oldValue !=null){
                newValue = oldValue;
            }

            if(newValue == null && oldValue == null)
                return;

            if(observablePicListData.isEmpty())
                return;

            //exercise got set from other controller
            if(this.exercise!=null && !observablePicListData.isEmpty()){
                String storingPath = getClass().getClassLoader().getResource("img").toString().substring(6);
                file =new File(storingPath + "\\"+newValue);
                picture = file.getName();
            }else{
                file =  new File(newValue);
                picture = file.toString();
            }

            inputStream = new FileInputStream(file);
            Image img = new Image(inputStream);
            imageView.setImage(img);
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }

    }


    @FXML
    void browseClicked(ActionEvent event) {
        btn_durchsuchen.setVisible(false);
        try {

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.getExtensionFilters().add(extFilterPng);

            //Show open file dialog and save picture into file reference
            List<File> files;
            files =fileChooser.showOpenMultipleDialog(null);

            btn_durchsuchen.setVisible(true);

            //no preview available so need to store picture
            if (imageView.getImage() == null) {


                String filename = files.get(0).toString();
                InputStream inputStream = new FileInputStream(files.get(0));
                Image img = new Image(inputStream);
                imageView.setImage(img);
                inputStream.close();

                for(File f : files){
                    exerciseGifList.add(f.getAbsolutePath());
                }
                observablePicListData.addAll(exerciseGifList);


                imagesListView.setItems(observablePicListData);
                imagesListView.setVisible(false);
                imagesListView.setVisible(true);

            } else {//picture in preview

               /* filename = file.toString();
                //new picture to update

                GregorianCalendar calendar = new GregorianCalendar();

                String ownName = "img_" + (calendar.getTimeInMillis()) + Math.abs(filename.hashCode());


                //for the preview in the image fxml

                InputStream inputStream = new FileInputStream(file);
                javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
                imageView.setImage(img);
                inputStream.close();
                pictureData.add(ownName);
                picData.add(ownName);
                picList.setItems(picData);
                picList.setVisible(false);
                picList.setVisible(true);*/
            }

        } catch (IOException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }

    }

    @FXML
    void cancelClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uebung verlassen");
        alert.setHeaderText("Das Uebungsfenster schliessen und alle Aenderungen verwerfen?");
        alert.setContentText("Moechten Sie die Uebungsuebersicht wirklich beenden?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            stage.close();
        } else {
            stage.show();
        }
    }

    @FXML
    void saveClicked(ActionEvent event) {
        try{
            if(exercise==null){
            exerciseService.create(this.extractExercise());
            }else{
                Exercise update = this.extractExercise();
                update.setId(exercise.getId());
                exerciseService.update(update);
            }
            stage.close();
        }catch (Exception e){
            LOGGER.error(e);
            e.printStackTrace();
        }

    }

    @FXML
    private void removeClicked(){
        if(picture!=null && !picture.isEmpty()) {
            observablePicListData.remove(picture);
            if (observablePicListData.isEmpty()) {
                imageView.setImage(null);
            }
        }
    }

    private Exercise extractExercise(){
        Double calories =0.0;
        try{
             calories = Double.parseDouble(caloriesField.getText());
        }catch (NumberFormatException e){
            calories = 1.0;
        }
        //TODO set logged in user

        List<AbsractCategory> temp = new ArrayList<>();
        for(CheckBox c : allCheckboxes){
            if(c.isSelected())
                temp.add(new TrainingsCategory(Integer.parseInt(c.getId()), c.getText()));
        }

        return new Exercise(null, exerciseNameField.getText(), descriptionArea.getText(), calories, videoLinkField.getText(),exerciseGifList, false, null, temp);
    }

}
