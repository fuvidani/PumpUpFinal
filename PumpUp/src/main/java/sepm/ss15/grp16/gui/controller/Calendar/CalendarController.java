package sepm.ss15.grp16.gui.controller.Calendar;

import com.google.gson.Gson;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.WorkoutplanExport;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.CalendarService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 *
 */
public class CalendarController extends Controller implements Initializable{

    private  final Logger LOGGER = LogManager.getLogger(CalendarController.class);
    private CalendarService calendarService;
    private StageTransitionLoader transitionLoader;

    private UserService userService;    //TODO remove this line, and also from spring + setter auch

    @FXML private WebView webView;
    @FXML private WebEngine engine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("Initialising CalendarController..");
        this.transitionLoader = new StageTransitionLoader(this);


        engine = webView.getEngine();
        String path = System.getProperty("user.dir");
        path.replace("\\\\", "/");
        path += "/src/main/java/sepm/ss15/grp16/gui/controller/Calendar/html/selectable.html";
        engine.load("file:///" + path);

        engine.getLoadWorker().stateProperty().addListener((ov,oldState, newState)->{
            if(newState== Worker.State.SUCCEEDED){

                // JS to Java
                JSObject script = (JSObject) engine.executeScript("window");
                script.setMember("drag", calendarService);

                LOGGER.debug("Execute javascript: addEvent..");
                // Java to JS, function to create single event
                engine.executeScript("function addEvent(id, title, start) {\n" +
                        "var eventData = {\n" +
                        "   id: id,\n" +
                        "   title: title,\n" +
                        "   start: start,\n" +
                        "   allDay: true\n" +
                        "};\n" +
                        "$('#calendar').fullCalendar('renderEvent', eventData, true);\n" +
                        "}");
            }

            LOGGER.debug("Execute javascript addListEvents..");
            // Java to JS, send JSON list
            engine.executeScript("function addListEvents(result) {\n" +
                    "for(var i=0; i<result.length; i++){\n" +
                    "   addEvent(result[i].appointment_id, result[i].sessionName, result[i].datum);" +
                    "};\n" +
                    "}");

           refreshCalendar();

        });

    }

    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }


    @FXML
    void exportToGoogleClicked() {
        //TODO remove this test from here

        Exercise exercise1 = new Exercise(null,"liegestuetz","description",0.03,"link",null,false,null,null);
        Exercise exercise2 = new Exercise(null,"situp","description",0.02,"link",null,false,null,null);
        ExerciseSet exerciseSet1 = new ExerciseSet(null,exercise1,null,10,null,1,false);
        ExerciseSet exerciseSet2 = new ExerciseSet(null,exercise2,null,20,null,1,false);

        List<ExerciseSet> list1 = new ArrayList<>();
        list1.add(exerciseSet1);
        list1.add(exerciseSet2);
        list1.add(exerciseSet1);

        List<ExerciseSet> list2 = new ArrayList<>();
        list1.add(exerciseSet2);
        list1.add(exerciseSet2);

        TrainingsSession session1 = new TrainingsSession(1,null,"Session1",false,list1);
        TrainingsSession session2 = new TrainingsSession(2,null,"Session2",false,list2);

        List<TrainingsSession> sessionList = new ArrayList<>();
        sessionList.add(session1);
        sessionList.add(session2);

        Trainingsplan trainingsplan = new Trainingsplan(null,userService.getLoggedInUser(),"plan","description",false,sessionList);

        DayOfWeek[] days = {DayOfWeek.FRIDAY};

        WorkoutplanExport export = new WorkoutplanExport(trainingsplan,days,new Date());

        try {
            calendarService.exportToCalendar(export);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.refreshCalendar();
    }

    public void refreshCalendar(){
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

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
