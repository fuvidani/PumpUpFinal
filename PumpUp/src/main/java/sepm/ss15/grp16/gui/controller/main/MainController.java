package sepm.ss15.grp16.gui.controller.main;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.user.UserEditController;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 * Controller of the main stage.
 */
public class MainController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    Label genderTextField;
    @FXML
    Label ageTextField;
    @FXML
    Label heightTextField;
    @FXML
    Label weightTextField;
    @FXML
    Label bodyfatTextField;
    @FXML
    Label emailTextField;
    private UserService userService;
    private CalendarService calendarService;
    private WeightHistoryService weightHistoryService;
    private BodyfatHistoryService bodyfatHistoryService;
    private PictureHistoryService pictureHistoryService;
    private StageTransitionLoader transitionLoader;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    private ImageView userImgView;
    @FXML
    private Label usernameLabel;
    @FXML
    private  LineChart<String, Number> userChart;
    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;


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

    @Override
    public void initController() {

        this.updateUserData();

        /**
         * #######      CALENDAR - don't touch this      #######
         */
        engine = webView.getEngine();
        String path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += "/src/main/java/sepm/ss15/grp16/gui/controller/Calendar/html/maincalendar.html";
        engine.load("file:///" + path);

        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                // JS to Java
                JSObject script = (JSObject) engine.executeScript("window");
                script.setMember("drag", calendarService);

                LOGGER.debug("Execute javascript: addEvent..");
                // Java to JS, function to create single event
                engine.executeScript("function addEvent(id, title, start, sets) {\n" +
                        "var eventData = {\n" +
                        "   id: id,\n" +
                        "   title: title,\n" +
                        "   start: start,\n" +
                        "   allDay: true,\n" +
                        "   url: sets\n" +
                        "};\n" +
                        "$('#calendar').fullCalendar('renderEvent', eventData, true);\n" +
                        "}");
            }

            LOGGER.debug("Execute javascript addListEvents..");
            // Java to JS, send JSON list
            engine.executeScript("function addListEvents(result) {\n" +
                    "for(var i=0; i<result.length; i++){\n" +
                    "   addEvent(result[i].appointment_id, result[i].sessionName, result[i].datum, result[i].setNames);" +
                    "};\n" +
                    "}");

            refreshCalendar();

        });
        /**
         * #######      END CALENDAR      #######
         */
        this.makeUserChart();

    }

    private void makeUserChart(){
        try {

            LineChart.Series<String, Number> weightSeries = new LineChart.Series<String, Number>();
            LineChart.Series<String, Number> bodyFatSeries = new LineChart.Series<String, Number>();
            List<WeightHistory> weightHistoryList = weightHistoryService.findAll();
            List<BodyfatHistory> bodyfatHistoryList = bodyfatHistoryService.findAll();

            for(WeightHistory w : weightHistoryList){
                if(w.getUser_id()==userService.getLoggedInUser().getUser_id()){
                    weightSeries.getData().add(new LineChart.Data<>(""+w.getDate(), w.getWeight()));
                }            }
            int counter = 0;
            for(BodyfatHistory b : bodyfatHistoryList){
                if(b.getUser_id()==userService.getLoggedInUser().getUser_id()) {

                    int bodyFatTOKG = weightHistoryList.get(counter).getWeight() * b.getBodyfat() / 100;
                    bodyFatSeries.getData().add(new LineChart.Data("" + b.getDate(), bodyFatTOKG));
                    counter++;
                }
            }
            weightSeries.setName("Körpergewicht");
            bodyFatSeries.setName("Körperfettanteil");
            userChart.getData().add(weightSeries);
            userChart.getData().add(bodyFatSeries);
        }catch (ServiceException e){
            e.printStackTrace();
            LOGGER.error(e);
        }
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
        //transitionLoader.openStage("fxml/workoutPlans/Workoutplans.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingspläne", 1000, 620, true);
        mainFrame.navigateToChild(PageEnum.Workoutplan);
    }

    @FXML
    void viewAllWorkoutPlansClicked(ActionEvent event) {
        //transitionLoader.openWaitStage("fxml/workoutPlans/Workoutplans.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingspläne", 1000, 620, true);
        mainFrame.navigateToChild(PageEnum.Workoutplan);
        refreshCalendar();
    }

    @FXML
    void exercisesButtonClicked(ActionEvent event) {
        mainFrame.navigateToChild(PageEnum.Exercises);
    }

    @FXML
    void calendarClicked(ActionEvent event) {
        transitionLoader.openWaitStage("fxml/calendar/Calendar.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingskalender", 1000, 500, true);
        refreshCalendar();
    }

    @FXML
    void trainingClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/workout/Workoutstart.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingsvorbereitung", 800, 600, false);
    }

    @FXML
    void editBodyDataClicked(ActionEvent event) {
        LOGGER.debug("Edit user button clicked");
        try {
            mainFrame.openDialog(PageEnum.UserEdit);
        } catch (Exception e) {
            LOGGER.error("Couldn't open useredit-window");
            e.printStackTrace();
        }
    }

    @FXML
    void manageBodyPhotosClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/user/BodyPhotos.fxml", (Stage) usernameLabel.getScene().getWindow(), "Fotos", 1000, 600, false);
    }

    @FXML
    void logoutClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Abmelden");
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
    }


    @FXML
    void exercisesMenuClicked(ActionEvent event) {
        mainFrame.navigateToChild(PageEnum.Exercises);
    }

    @FXML
    void openCalendarMenuClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/calendar/Calendar.fxml", (Stage) usernameLabel.getScene().getWindow(), "Trainingskalender", 800, 600, false);

    }

    @FXML
    void aboutMenuClicked(ActionEvent event) {
        transitionLoader.openWaitStage("fxml/main/About.fxml", (Stage) usernameLabel.getScene().getWindow(), "Information", 400, 400, false);
    }

    public void updateUserData() {
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
            if (actualWeighthistory != null) {
                weight = actualWeighthistory.getWeight();
            }

            BodyfatHistory actualBodyfathistory = bodyfatHistoryService.getActualBodyfat(user_id);
            if (actualBodyfathistory != null) {
                bodyfat = actualBodyfathistory.getBodyfat();
            }

            PictureHistory actualPictureHistory = pictureHistoryService.getActualPicture(user_id);

            if (actualPictureHistory != null) {
                String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
                LOGGER.debug("Loading from resources: " + pathToResource);
                String pathOfNewImage = pathToResource + actualPictureHistory.getLocation();
                LOGGER.debug("Loading image with path: " + pathOfNewImage);
                File picture = new File(pathOfNewImage);
                userImgView.setImage(new Image(picture.toURI().toString()));
            }

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        usernameLabel.setText("Willkommen, " + username + "!");
        ageTextField.setText(Integer.toString(age));
        heightTextField.setText(Integer.toString(height));
        genderTextField.setText(gender ? "Männlich" : "Weiblich");

        if (weight != null) {
            weightTextField.setText(Integer.toString(weight));
        }else {
            weightTextField.setText("Keine Angabe");
        }

        if (bodyfat != null) {
            bodyfatTextField.setText(Integer.toString(bodyfat));
        } else {
            bodyfatTextField.setText("Keine Angabe");
        }

        if (email == null || email.isEmpty()) {
            emailTextField.setText("Keine Angabe");
        } else {
            emailTextField.setText(email);
        }
    }

    public void refreshCalendar() {
        engine.executeScript("$('#calendar').fullCalendar('removeEvents');");

        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(calendarService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        LOGGER.debug(json);
        engine.executeScript("addListEvents(" + json + ");");

    }

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public void addAppointmentList(Appointment appointment) {
        this.appointmentList.add(appointment);
    }
}
