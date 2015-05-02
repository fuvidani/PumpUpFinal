package sepm.ss15.grp16.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.gui.exception.ValidationException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;


import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

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


    private ObservableList<Exercise> masterData = FXCollections.observableArrayList();
    private ObservableList<String> picData = FXCollections.observableArrayList();
    private List<String> pictureData = new ArrayList<>();

    private ExerciseService exerciseService;

    public ExerciseController(){


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
          exerciseService  = new ExerciseServiceImpl();
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

        }

    }

    private void showPic(String pic, String old){
        try {

            String path = "" + (this.getClass().getResource("img/").toString().substring(6)).concat(pic);
            FileInputStream reading = new FileInputStream(path);
            Image img = new Image(reading);
            image_view.setImage(img);
        } catch (FileNotFoundException e) {


        }
    }

    private void showExercise(Exercise exercise, Exercise old){

        if (exercise == null && old != null) {
            exercise = old;
        }

        tf_name.setText(exercise.getName());
        ta_description.setText((exercise.getDescription()));
        tf_calories.setText(""+exercise.getCalories());
        tf_videoLink.setText(""+exercise.getVideolink());
        this.exerciseID = exercise.getId();
        ObservableList<String> temp = FXCollections.observableArrayList();
        for(String s : exercise.getGifLinks()){
           temp.add(s);
        }
        picList.setItems(temp);

    }

    @FXML
    private void btnSaveclicked() {
        try {
            Exercise exercise = this.extractExercise();
            exercise.setGifLinks(this.pictureData);
            masterData.add( exerciseService.create(exercise));
            this.clear();
        }catch (ServiceException e){
            e.printStackTrace();
        }catch(ValidationException e){
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
       }catch(ValidationException e){
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
        }catch(ValidationException e){
            e.printStackTrace();
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
            File file = fileChooser.showOpenDialog(null);
            btn_durchsuchen.setVisible(true);

            //no preview available so need to store picture
            if (image_view.getImage() == null) {

                if (file == null) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warnung");
                    alert.setContentText("Da ist wohl etwas schief gegangen. UPS!");
                    alert.setHeaderText("Bild nicht gewaehlt");
                    alert.showAndWait();
                    return;
                }

                filename = file.toString();

                GregorianCalendar calendar = new GregorianCalendar();

                String ownName = "img_" + (calendar.getTimeInMillis()) + Math.abs(filename.hashCode());
                tf_picture.setText(ownName.concat(".jpg"));
                InputStream inputStream = new FileInputStream(file);
                javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
                image_view.setImage(img);
                inputStream.close();
                pictureData.add(ownName);
                picData.add(ownName);
                picList.setItems(picData);
                picList.setVisible(false);
                picList.setVisible(true);

            } else {//picture in preview

                if (file == null) {//keep old picture
                    oldFileName = null;
                    return;
                }


                filename = file.toString();
                //new picture to update

                GregorianCalendar calendar = new GregorianCalendar();

                String ownName = "img_" + (calendar.getTimeInMillis()) + Math.abs(filename.hashCode());

                //preview in textfield
                tf_picture.setText(ownName.concat(".jpg"));

                //for the preview in the image fxml
                InputStream inputStream = new FileInputStream(file);
                javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
                image_view.setImage(img);
                inputStream.close();
                pictureData.add(ownName);
                picData.add(ownName);
                picList.setItems(picData);
                picList.setVisible(false);
                picList.setVisible(true);
            }

        } catch (IOException e) {

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
        picList.setItems(null);
        tf_calories.setText("");
        tf_picture.setText(" ");
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
