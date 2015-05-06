package sepm.ss15.grp16.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by lukas on 01.05.2015.
 */
public class ExerciseController implements Initializable{



    @FXML
    private TextField tf_name;

    @FXML
    private TextArea ta_description;

    @FXML
    private TextField tf_calories;

    @FXML
    private TextField tf_videoLink;

    @FXML
    private TextField tf_picture;

    @FXML
    private TableView<Exercise> tableView = new TableView<Exercise>();

    @FXML
    private ListView<String> picList = new ListView<>();


    @FXML
    private TableColumn<Exercise, String> exerciseNameColumn;
    @FXML
    private TableColumn<Exercise, String > descriptionColumn;
    @FXML
    private TableColumn<Exercise, Double> caloriesColumn;
    @FXML
    private TableColumn<Exercise, String> videoLinkColumn;



    @FXML
    private Button btn_durchsuchen;

    @FXML
    private ImageView image_view;

    private String filename;
    private String oldFileName;
    private Integer exerciseID;

    private static final Logger LOGGER = LogManager.getLogger();

    private ObservableList<Exercise> masterData = FXCollections.observableArrayList();
    private ObservableList<String> picData = FXCollections.observableArrayList();
    private List<String> pictureData = new ArrayList<>();

    private ExerciseService exerciseService;


    public void setExerciseService(ExerciseService exerciseService)
    {
        this.exerciseService = exerciseService;
    }

    public ExerciseService getExerciseService()
    {
        return exerciseService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exerciseNameColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String >("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String >("description"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Double>("calories"));
        videoLinkColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String >("videolink"));


        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercise>() {

            @Override
            public void changed(ObservableValue<? extends Exercise> observable,
                                Exercise oldValue, Exercise newValue) {
                showExercise(newValue, oldValue);
            }
        });

        picList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showPic(newValue, oldValue);
            }
        });

      try {
            List<Exercise> data = exerciseService.findAll();
            if (data == null) {
                tableView.setPlaceholder(new javafx.scene.control.Label("Keine Uebungen gespeichert"));
            } else {
                for (Exercise e : data) {
                    masterData.add(e);
                }

                tableView.setItems(masterData); //initial fxml of data
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
}

    private void showPic(String pic, String old){
        try {

            File file;
            InputStream inputStream;
            if(pic.contains("img_")){
                String storingPath = getClass().getClassLoader().getResource("img").toString().substring(6);
                file = new File(storingPath+pic);
                inputStream = new FileInputStream(file);
            }else{
                file = new File(pic);
                inputStream = new FileInputStream(file);
            }
            javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
            image_view.setImage(img);
            inputStream.close();
            filename = pic;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showExercise(Exercise exercise, Exercise old){
        this.clear();
        if (exercise == null && old != null) {
            exercise = old;
        }

        tf_name.setText(exercise.getName());
        ta_description.setText((exercise.getDescription()));
        tf_calories.setText("" + exercise.getCalories());
        tf_videoLink.setText(""+exercise.getVideolink());
        this.exerciseID = exercise.getId();
        ObservableList<String> temp = FXCollections.observableArrayList();
        LOGGER.debug(exercise.getGifLinks().size());
        for(String s : exercise.getGifLinks()){
            LOGGER.debug(s);
            temp.add(s);
        }
        picList.setItems(temp);
        picList.setVisible(false);
        picList.setVisible(true);

    }

    @FXML
    private void btn_removePictureClicked(){
        LOGGER.debug(filename);
        int i = 0;
        for(String s : picData){
            if(s.equals(filename))
                break;

            i++;
        }
        picData.remove(i);
        //no more pictures to show
        if(picData.isEmpty()){
            image_view.setImage(null);
        }
        picList.setItems(picData);
        picList.setVisible(false);
        picList.setVisible(true);
    }

    @FXML
    private void btnSaveclicked() {
        try {
            Exercise exercise = this.extractExercise();
            for(int i = 0; i < pictureData.size(); i++){
                pictureData.remove(i);
            }
            for(String s : picData){
                pictureData.add(s);
            }
            exercise.setGifLinks(this.pictureData);
            Exercise updated = exerciseService.create(exercise);
            LOGGER.debug(updated.getGifLinks());
            masterData.add(updated);
            pictureData = updated.getGifLinks();
            tableView.setVisible(false);
            tableView.setVisible(true);
            this.clear();

        }catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnNeuClicked() {
        this.clear();
    }

    @FXML
    private void btnDeleteClicked() {
        try {
            Exercise ex = this.extractExercise();
            ex.setId(this.exerciseID);

            exerciseService.delete(ex);
            masterData.remove(ex);
            this.clear();

        }catch (ServiceException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnUpdateClicked() {
        try {
            Exercise exercise = this.extractExercise();
            exercise.setId(this.exerciseID);
            Exercise updated = exerciseService.update(exercise);
            int i = 0;
            for(Exercise e : masterData){
                if(e.equals(exercise))
                    break;

                i++;
            }

            masterData.remove(i);
            masterData.add(i, updated);
            tableView.getColumns().get(0).setVisible(false);
            tableView.getColumns().get(0).setVisible(true);
        }catch (ServiceException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void searchPicBtnClicked() {
        btn_durchsuchen.setVisible(false);
        this.oldFileName = tf_picture.getText();
        try {

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.getExtensionFilters().add(extFilterPng);

            //Show open file dialog and save picture into file reference
            List<File> files = null;
            files =fileChooser.showOpenMultipleDialog(null);
            btn_durchsuchen.setVisible(true);


            for(File f : files){
                picData.add(f.toString());
            }
            picList.setItems(picData);

            //no preview available so need to store picture

            filename = picData.get(0).toString();
            File file = new File(filename);
            InputStream inputStream = new FileInputStream(file);
            javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
            image_view.setImage(img);
            inputStream.close();

            picList.setVisible(false);
            picList.setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Exercise extractExercise() throws ValidationException{
        validate();
        return new Exercise(tf_name.getText(), ta_description.getText(), Double.parseDouble(tf_calories.getText()),
                tf_videoLink.getText(), false);

    }

    private void clear() {
        tf_name.setText("");
        ta_description.setText("");
        tf_videoLink.setText("");
        tf_calories.setText("");
        tf_picture.setText("");
        picData.remove(0, picData.size());

//        for(int i = 0; i < pictureData.size(); i++){
//            pictureData.remove(i);
//        }

//        picList.setItems(null);
//        picList.setVisible(false);
//        picList.setVisible(true);
        image_view.setImage(null);
        tableView.getColumns().get(0).setVisible(false);
        tableView.getColumns().get(0).setVisible(true);
    }

    private void validate() throws ValidationException{
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warnung");
        alert.setContentText("Da ist wohl etwas schief gegangen. UPS!");

        String name = tf_name.getText();
        String description = ta_description.getText();
        String errors = "";
        Double calories = -1.0;
        boolean display = false;

        String videoLink =  tf_videoLink.getText();


        if(name.isEmpty() || name==null){
            display=true;
            errors+="name not set!\n";
        }

        try {
            calories=  Double.parseDouble(tf_calories.getText());
        }catch (NumberFormatException e){
            display = true;
            errors+="no valid data for calories!\n";
        }

        if(calories<=0){
            display = true;
            errors+="calories are below 0\n";
        }

        if(display){
            alert.setHeaderText(errors);
            alert.showAndWait();
            throw new ValidationException("");
        }

    }

}
