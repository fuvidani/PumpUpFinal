package sepm.ss15.grp16.gui.controller.main;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.workout.WorkoutstartController;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    @FXML
    private Label currentTrainingTypeLabel;
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

    private  Appointment executionAppointment;


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
        path = path.replace("\\", "/");
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

    }

    @FXML
    void trainingClicked(ActionEvent event) {
        try {
            executionAppointment = calendarService.getCurrentAppointment();

            if(executionAppointment != null) {
                mainFrame.openDialog(PageEnum.Workoutstart);
                WorkoutstartController workoutstartController = (WorkoutstartController) getChildController();

                if(workoutstartController.started()) {
                    mainFrame.navigateToChild(PageEnum.LiveMode);
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Keine Übung zur Ausführung!");
                alert.setContentText("Keine Übung zur Ausführung!");
                ButtonType ok = new ButtonType("OK");
                alert.getButtonTypes().setAll(ok);
                alert.showAndWait().get();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public Appointment getExecutionAppointment()
    {
        return  executionAppointment;
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
        genderTextField.setText(gender ? "M\u00e4nnlich" : "Weiblich");

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
        makeUserChart();
    }

    private void makeUserChart(){
        try {
            int loggedInUserID = userService.getLoggedInUser().getUser_id();
            userChart.getData().clear();
            LineChart.Series<String, Number> weightSeries = new LineChart.Series<String, Number>();
            LineChart.Series<String, Number> bodyFatSeries = new LineChart.Series<String, Number>();
            List<WeightHistory> weightHistoryList = weightHistoryService.searchByUserID(loggedInUserID);
            List<BodyfatHistory> bodyfatHistoryList = bodyfatHistoryService.searchByUserID(loggedInUserID);

            for(WeightHistory w : weightHistoryList){
                    weightSeries.getData().add(new LineChart.Data<>(""+w.getDate(), w.getWeight()));
            }

            int counter = 0;

            for(BodyfatHistory b : bodyfatHistoryList){
                int bodyFatTOKG = weightHistoryList.get(counter).getWeight() * b.getBodyfat() / 100;
                bodyFatSeries.getData().add(new LineChart.Data("" + b.getDate(), bodyFatTOKG));
                counter++;
            }
            weightSeries.setName("K\u00f6rpergewicht");
            bodyFatSeries.setName("K\u00f6rperfettanteil");
            userChart.getData().add(weightSeries);
            userChart.getData().add(bodyFatSeries);
        }catch (ServiceException e){
            e.printStackTrace();
            LOGGER.error(e);
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
