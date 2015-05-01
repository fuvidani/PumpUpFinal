package main.java.sepm.ss15.grp16.gui.controller;

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
import main.java.sepm.ss15.grp16.entity.Exercise;
import main.java.sepm.ss15.grp16.service.ExerciseService;
import main.java.sepm.ss15.grp16.service.exception.ServiceException;
import main.java.sepm.ss15.grp16.service.impl.ExerciseServiceImpl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by lukas on 01.05.2015.
 */
public class ExerciseController implements Initializable{


    @FXML
    private TextField tf_exerciseID;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_description;

    @FXML
    private TextField tf_calories;

    @FXML
    private TextField tf_videoLink;

    @FXML
    private TextField tf_picture;

    @FXML
    private TableView<Exercise> tableView = new TableView<Exercise>();

    @FXML
    public TableColumn<Exercise, Integer> exerciseIDColumn;

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


    private ObservableList<Exercise> masterData = FXCollections.observableArrayList();


    private ExerciseService exerciseService;

    public ExerciseController(){


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exerciseIDColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("id"));
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

        try {
          exerciseService  = new ExerciseServiceImpl();
          List<Exercise> data = exerciseService.findAll();
            if (data == null) {
                tableView.setPlaceholder(new javafx.scene.control.Label("Keine Uebungen gespeichert"));
            } else {
                for (Exercise e : data) {
                    masterData.add(e);
                }

                tableView.setItems(masterData); //initial view of data
            }
        } catch (ServiceException e) {

        }

    }

    private void showExercise(Exercise exercise, Exercise old){

        if (exercise == null && old != null) {
            exercise = old;
        }

        tf_exerciseID.setText(""+exercise.getId());
        tf_name.setText(exercise.getName());
        tf_description.setText((exercise.getDescription()));
        tf_calories.setText(""+exercise.getCalories());
        tf_videoLink.setText(""+exercise.getVideolink());

    }

    @FXML
    private void btnSaveclicked() {
        try {
            Exercise exercise = this.extractExercise();
            exerciseService.create(exercise);
            this.clear();
            masterData.add(exercise);
        }catch (ServiceException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnNeuClicked() {
        this.clear();
        tableView.getColumns().get(0).setVisible(false);
        tableView.getColumns().get(0).setVisible(true);

    }

    @FXML
    private void btnDeleteClicked() {

    }

    @FXML
    private void btnUpdateClicked() {
        try {
            Exercise exercise = this.extractExercise();
            exerciseService.update(exercise);
        }catch (ServiceException e){
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

                //for the preview in the image view
                InputStream inputStream = new FileInputStream(file);
                javafx.scene.image.Image img = new javafx.scene.image.Image(inputStream);
                image_view.setImage(img);
                inputStream.close();
            }

        } catch (IOException e) {

        }
    }

    private Exercise extractExercise() {

        return new Exercise(Integer.parseInt(tf_exerciseID.getText()), tf_name.getText(),tf_description.getText() ,Double.parseDouble(tf_calories.getText()),
               tf_videoLink.getText());
    }

    private void clear() {
        tf_exerciseID.setText("");
        tf_name.setText("");
        tf_description.setText("");
        tf_videoLink.setText("");
        tf_calories.setText("");
        tf_picture.setText(" ");
        image_view.setImage(null);
    }

}
