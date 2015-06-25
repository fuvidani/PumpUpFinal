package sepm.ss15.grp16.gui.controller.calendar;

import com.google.gson.Gson;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.calendar.helper.EventScriptRunner;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * Created by david molnar on 08.05.15.
 * <p/>
 * This class represents a controller for calendar.fxml frame.
 * Its task is to handle the interactions with the user and notify the service layer about the user requests.
 */
public class CalendarController extends Controller {

    private final Logger LOGGER = LogManager.getLogger(CalendarController.class);
    private CalendarService calendarService;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine engine;

    @Override
    public void initController() {
        LOGGER.info("Initialising CalendarController..");

        engine = webView.getEngine();
        try {
            String path = getClass().getClassLoader().getResource("calendar/html/selectable.html").toURI().getPath();
            engine.load("file:///" + path);
        } catch (URISyntaxException e) {
            LOGGER.error("Exception in CalendarController while initializing it. - " + e.getMessage());
        }


        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

                // JS to Java
                JSObject script = (JSObject) engine.executeScript("window");
                script.setMember("drag", calendarService);

                //executes JS functions, that can be used in order to add new events.
                EventScriptRunner scripts = new EventScriptRunner(engine);
                scripts.runScripts();
            }

            refreshCalendar();
        });

    }

    /**
     * Initialises the local service.
     *
     * @param calendarService the new service.
     */
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    /**
     * This method will be called after clicking on 'exportToGoogleButton'.
     * Redirects this request for the service layer and shows authorize site in browser.
     */
    @FXML
    public void exportToGoogleClicked() {

        try {
            if (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0) { //OS is mac -> disable this functionality

                LOGGER.error("Tried to export to google with mac.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Google Export nicht möglich.");
                alert.setContentText("Google Export ist unter MAC OSX nicht unterstützt.");
                alert.showAndWait();
            } else if (calendarService.findAll().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Keine Termine zu exportieren.");
                alert.setContentText("Es sind keine Events im Kalendar.");
                alert.showAndWait();
            } else {

                URL url = new URL("http://www.google.com");
                URLConnection connection = url.openConnection();
                connection.connect();

                calendarService.exportToGoogle();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Termine exportiert.");
                alert.setContentText("Die Termine sind erfolgreich in Google Calendar exportiert.");
                alert.showAndWait();
                LOGGER.info("Events successfully exported to Google.");

            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in CalendarController while exportToGoogleClicked(). - " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Internal Fehler.");
            alert.setContentText("Es ist leider ein Fehler aufgetreten.");
            alert.showAndWait();
        } catch (IOException e) {
            LOGGER.info("Failed to open a connection, reason: " + e.getMessage());
            ;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Öffnen des Browsers");
            alert.setContentText("Es konnte keine Internetverbindung hergestellt werden, somit können Sie leider derzeit Google Calendar nicht erreichen.");
            alert.showAndWait();
        }
    }

    /**
     * Method, that should be run if 'goBackButton' gets hit by the user.
     * Navigates back to the previous frame.
     */
    @FXML
    public void zuruckClicked() {
        LOGGER.info("Back to previous frame from calendar.");
        mainFrame.navigateToMain();
    }

    /**
     * This method will be called if the 'deleteButton' got hit by the user.
     * Notifies the service layer to delete all events from the calendar.
     */
    @FXML
    public void deleteAllAppointmentsClicked() {
        try {

            if (calendarService.findAll().isEmpty()) { //nothing to delete
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Keine Termine vorhanden.");
                alert.setContentText("Sie haben keine Termine im Kalendar.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Termine löschen.");
                alert.setHeaderText("Wollen Sie wirklich die Termine löschen?");
                alert.setContentText("Das Löschen der Termine kann nicht rückgängig gemacht werden.");
                ButtonType yes = new ButtonType("Ja");
                ButtonType cancel = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(yes, cancel);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == yes) {
                    calendarService.deleteAllAppointments();
                    refreshCalendar();
                } else {
                    alert.close();
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception in CalendarController while deleteAllAppointmentsClicked(). - " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Internal Fehler.");
            alert.setContentText("Es ist leider ein Fehler aufgetreten.");
            alert.showAndWait();
        }

    }

    /**
     * Removes the events from the calendar and renders it again.
     * This method will be called after opening the calendar frame.
     */
    public void refreshCalendar() {
        try {
            engine.executeScript("$('#calendar').fullCalendar('removeEvents');");
        } catch (JSException e) {
        }


        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(calendarService.findAll());
            LOGGER.debug("JSON object successfully created.");
        } catch (ServiceException e) {
            LOGGER.error("Exception in CalendarController while refreshCalendar(). - " + e.getMessage());
        }

        try {
            engine.executeScript("addListEvents(" + json + ");");
        } catch (JSException e) {
        }

        LOGGER.info("Calendar successfully refreshed.");
    }
}
