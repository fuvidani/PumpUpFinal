package sepm.ss15.grp16.gui.controller.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.entity.PictureHistory;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.User.UserEditController;
import sepm.ss15.grp16.service.BodyfatHistoryService;
import sepm.ss15.grp16.service.PictureHistoryService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 * Controller of the main stage.
 *
 */
public class MainController extends Controller implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger();
    private UserService userService;
    private WeightHistoryService weightHistoryService;
    private BodyfatHistoryService bodyfatHistoryService;
    private PictureHistoryService pictureHistoryService;
    private StageTransitionLoader transitionLoader;

    @FXML
    private Label currentTrainingTypeLabel;

    @FXML
    private ImageView userImgView;

    @FXML
    private Label usernameLabel;

    @FXML
    private LineChart<?, ?> userChart;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setWeightHistoryService(WeightHistoryService weightHistoryService) {
        this.weightHistoryService = weightHistoryService;
    }

    public void setBodyfatHistoryService(BodyfatHistoryService bodyfatHistoryService) {
        this.bodyfatHistoryService = bodyfatHistoryService;
    }

    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @FXML
    TextField genderTextField;
    @FXML
    TextField ageTextField;
    @FXML
    TextField heightTextField;
    @FXML
    TextField weightTextField;
    @FXML
    TextField bodyfatTextField;
    @FXML
    TextField emailTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        this.updateUserData();
    }

    @FXML
    void editUserDataButtonClicked(ActionEvent event) {
        // TODO: Either small dialog window for editing data or live editing into the table itself.
    }

    @FXML
    void statisicsButtonClicked(ActionEvent event) {
        // TODO: Michi's Statistiken
    }

    @FXML
    void viewCurrentWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutplans.fxml",(Stage)usernameLabel.getScene().getWindow(),"Trainingspläne",1000,620, true);
    }

    @FXML
    void viewAllWorkoutPlansClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutplans.fxml",(Stage)usernameLabel.getScene().getWindow(),"Trainingspläne",1000,620, true);
    }

    @FXML
    void exercisesButtonClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Exercises.fxml", (Stage) usernameLabel.getScene().getWindow(), "Übungen", 1100, 750, true);
    }

    @FXML
    void calendarClicked(ActionEvent event) {

        transitionLoader.openStage("fxml/Calendar.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingskalender", 1000, 500, true);

    }

    @FXML
    void trainingClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workoutstart.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingsvorbereitung", 800, 600, false);
    }

    @FXML
    void editBodyDataClicked(ActionEvent event) {
        LOGGER.debug("Edit user button clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            fxmlLoader.setControllerFactory(context::getBean);
            Stage stage = new Stage();
            fxmlLoader.setLocation(UserEditController.class.getClassLoader().getResource("fxml/UserEdit.fxml"));
            Pane pane = fxmlLoader.load(UserEditController.class.getClassLoader().getResourceAsStream("fxml/UserEdit.fxml"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(usernameLabel.getScene().getWindow());
            UserEditController userEditController = fxmlLoader.getController();
            userEditController.setMainController(this);
            stage.setResizable(false);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            LOGGER.error("Couldn't open useredit-window");
            e.printStackTrace();
        }
    }

    @FXML
    void manageBodyPhotosClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/BodyPhotos.fxml", (Stage) usernameLabel.getScene().getWindow(), "Fotos", 1000, 600, false);
    }

    @FXML
    void logoutClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Abmelde");
        alert.setHeaderText("");
        alert.setContentText("Möchten Sie sich wirklich abmelden?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            // TODO: For cleaning purposes: close DB-connection
            stage.close();
        } else {
            stage.show();
        }
        stage.close();
    }


    @FXML
    void exercisesMenuClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Exercises.fxml", (Stage) usernameLabel.getScene().getWindow(), "Übungen", 800, 600, true);
    }


    @FXML
    void aboutMenuClicked(ActionEvent event) {
        // About the developer, contact + Help
    }

    public void updateUserData(){
        Integer user_id = userService.getLoggedInUser().getUser_id();
        String username = userService.getLoggedInUser().getUsername();
        Boolean gender = userService.getLoggedInUser().isGender();
        Integer age = userService.getLoggedInUser().getAge();
        Integer height = userService.getLoggedInUser().getHeight();
        String email = userService.getLoggedInUser().getEmail();
        Integer weight = null;
        Integer bodyfat = null;

        try {

            WeightHistory actualWeighthistory = weightHistoryService.getActualWeight(user_id);
            if(actualWeighthistory != null){
                weight = actualWeighthistory.getWeight();
            }

            BodyfatHistory actualBodyfathistory = bodyfatHistoryService.getActualBodyfat(user_id);
            if(actualBodyfathistory != null){
                bodyfat = actualBodyfathistory.getBodyfat();
            }

            PictureHistory actualPictureHistory = pictureHistoryService.getActualPicture(user_id);
            if(actualBodyfathistory != null){
                String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
                LOGGER.debug("Loading from resources: " + pathToResource);
                String pathOfNewImage = pathToResource + actualPictureHistory.getLocation();
                LOGGER.debug("Loading image with path: " + pathOfNewImage);
                File picture = new File(pathOfNewImage);
                userImgView.setImage(new Image(picture.toURI().toString()));
            }

        }catch(ServiceException e){
            e.printStackTrace();
        }catch(URISyntaxException e){
            e.printStackTrace();
        }

        usernameLabel.setText("Willkommen, " + username + "!");
        ageTextField.setText(Integer.toString(age));
        heightTextField.setText(Integer.toString(height));
        genderTextField.setText(gender ? "Männlich" : "Weiblich");

        if(weight != null){
            weightTextField.setText(Integer.toString(weight));
        }

        if(bodyfat != null){
            bodyfatTextField.setText(Integer.toString(bodyfat));
        }else{
            bodyfatTextField.setPromptText("Keine Angabe");
        }

        if(email == null || email.isEmpty()){
            emailTextField.setPromptText("Keine Angabe");
        }else {
            emailTextField.setText(email);
        }
    }

}
