package sepm.ss15.grp16.gui.controller.workoutPlans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.WorkoutplanExport;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.TrainingsplanService;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 */
public class WorkoutPlanToCalendarController extends Controller implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(WorkoutPlanToCalendarController.class);
    public static Trainingsplan plan_interClassCommunication;
    private CalendarService calendarService;
    private TrainingsplanService trainingsplanService;
    private UserService userService;
    private StageTransitionLoader transitionLoader;


    @FXML
    private CheckBox thursdayCheck;

    @FXML
    private CheckBox tuesdayCheck;

    @FXML
    private DatePicker dateField;

    @FXML
    private Text txtName;

    @FXML
    private CheckBox mondayCheck;

    @FXML
    private CheckBox wednesdayCheck;

    @FXML
    private CheckBox sundayCheck;

    @FXML
    private CheckBox fridayCheck;

    @FXML
    private CheckBox saturdayCheck;

    @FXML
    private ListView<TrainingsSession> listviewSessions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        setUpListView();

        if (plan_interClassCommunication != null) {
            txtName.setText(plan_interClassCommunication.getName());
            if (plan_interClassCommunication.getTrainingsSessions() != null) {

                ObservableList<TrainingsSession> data =
                        FXCollections.observableArrayList(
                                plan_interClassCommunication.getTrainingsSessions()
                        );
                listviewSessions.setItems(data);
            }
        }

    }

    private void setUpListView() {
        listviewSessions.setCellFactory(p -> new ListCell<TrainingsSession>() {
            @Override
            protected void updateItem(TrainingsSession t, boolean bln) {
                setText(t != null ? t.getName() : "");
            }

        });
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00c4nderungen verwerfen");
        alert.setHeaderText("Wollen Sie wirklich abbrechen?");
        alert.setContentText("Alle \u00c4nderungen w\u00fcrden verlorgen gehen!");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            plan_interClassCommunication = null;
            this.stage.close();
        }
    }

    @FXML
    void generateButtonClicked(ActionEvent event) {

        WorkoutplanExport export = createValidExport();
        if (export != null) {
            try {
                calendarService.exportToCalendar(export);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            transitionLoader.openWaitStage("fxml/calendar/Calendar.fxml", (Stage) listviewSessions.getScene().getWindow(), "Trainingskalender", 1000, 500, true);
            plan_interClassCommunication = null;

            this.stage.close();
        }
    }

    private WorkoutplanExport createValidExport() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Falsche Daten!");
        boolean error = false;
        String errormessage = "";
        DayOfWeek[] dayOfWeeks = new DayOfWeek[0];

        if (mondayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.MONDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (tuesdayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.TUESDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (wednesdayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.WEDNESDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (thursdayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.THURSDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (fridayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.FRIDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (saturdayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.SATURDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }
        if (sundayCheck.isSelected()) {
            DayOfWeek[] dayOfWeeks_temp = new DayOfWeek[dayOfWeeks.length + 1];
            System.arraycopy(dayOfWeeks, 0, dayOfWeeks_temp, 0, dayOfWeeks.length);
            dayOfWeeks_temp[dayOfWeeks.length] = DayOfWeek.SUNDAY;
            dayOfWeeks = dayOfWeeks_temp;
        }

        if (dayOfWeeks.length < 1) {
            error = true;
            errormessage = "Bitte mindestens einen Wochentag wählen!";
        }

        LocalDate localDate = dateField.getValue();
        Date date = null;

        if (localDate != null) {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
        } else {
            error = true;
            errormessage = "Bitte ein Startdatum wählen!";
        }

        if (error) {
            alert.setContentText(errormessage);
            alert.showAndWait();
            return null;
        } else {
            return new WorkoutplanExport(plan_interClassCommunication, dayOfWeeks, date);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTrainingsplanService(TrainingsplanService trainingsplanService) {
        this.trainingsplanService = trainingsplanService;
    }

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
}
