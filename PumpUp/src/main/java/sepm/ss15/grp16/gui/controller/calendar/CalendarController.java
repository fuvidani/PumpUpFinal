package sepm.ss15.grp16.gui.controller.calendar;

import com.google.gson.Gson;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 */
public class CalendarController extends Controller implements Initializable {

    private final Logger LOGGER = LogManager.getLogger(CalendarController.class);
    private CalendarService calendarService;
    private StageTransitionLoader transitionLoader;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;
    @FXML
    private Button exportButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("Initialising CalendarController..");
        this.transitionLoader = new StageTransitionLoader(this);

        engine = webView.getEngine();
        String path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += "/src/main/java/sepm/ss15/grp16/gui/controller/Calendar/html/selectable.html";
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

    }

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @FXML
    public void exportToGoogleClicked() {
        calendarService.exportToGoogle();
    }

    @FXML
    public void zuruckClicked() {
        mainFrame.navigateToParent();
    }

    @FXML
    public void deleteAllAppointmentsClicked() {

        try {
            calendarService.deleteAllAppointments();
        } catch (ServiceException e) {
            e.printStackTrace(); //TODO change
        }

        refreshCalendar();
    }

    public void refreshCalendar() {
        engine.executeScript("$('#calendar').fullCalendar('removeEvents');");

        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(calendarService.findAll());
        } catch (ServiceException e) {
            e.printStackTrace(); //TODO change
        }

        LOGGER.debug(json);
        engine.executeScript("addListEvents(" + json + ");");

    }


}
