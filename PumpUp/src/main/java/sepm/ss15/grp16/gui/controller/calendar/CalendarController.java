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
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.calendar.helper.EventScriptRunner;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 */
public class CalendarController extends Controller implements Initializable {

    private final Logger LOGGER = LogManager.getLogger(CalendarController.class);
    private CalendarService calendarService;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;
    @FXML
    private Button exportButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("Initialising CalendarController..");

        engine = webView.getEngine();
        try {
            String path = getClass().getClassLoader().getResource("calendar/html/selectable.html").toURI().getPath();
            engine.load("file:///" + path);
        } catch (URISyntaxException e) {
            LOGGER.error(e);
            e.printStackTrace(); //TODO
        }


        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                // JS to Java
                JSObject script = (JSObject) engine.executeScript("window");
                script.setMember("drag", calendarService);

                EventScriptRunner scripts = new EventScriptRunner(engine);
                scripts.runScripts();
            }

            refreshCalendar();
        });

    }

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @FXML
    public void exportToGoogleClicked() {
        try {
            calendarService.exportToGoogle();
        } catch (ServiceException e) {
            e.printStackTrace(); //TODO change
        }
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

        LOGGER.debug("JSON: " + json);
        engine.executeScript("addListEvents(" + json + ");");

    }


}
