package sepm.ss15.grp16.gui.controller.calendar.helper;

import javafx.scene.web.WebEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by David on 2015.06.14..
 */
public class EventScriptRunner {
    private final Logger LOGGER = LogManager.getLogger(EventScriptRunner.class);

    private WebEngine engine;

    public EventScriptRunner(WebEngine engine) {
        this.engine = engine;
    }

    public void runScripts() {
        LOGGER.debug("Execute javascript: addEvent..");
        // Java to JS, function to create single event
        engine.executeScript("function addEvent(id, title, start, sets, color) {\n" +
                "var eventData = {\n" +
                "   id: id,\n" +
                "   title: title,\n" +
                "   start: start,\n" +
                "   allDay: true,\n" +
                "   url: sets,\n" +
                "   color: color\n" +
                "};\n" +
                "$('#calendar').fullCalendar('renderEvent', eventData, true);\n" +
                "}");

        LOGGER.debug("Execute javascript addListEvents..");
        // Java to JS, send JSON list
        engine.executeScript("function addListEvents(result) {\n" +
                "for(var i=0; i<result.length; i++){\n" +
                "   var color;\n" +
                "   var today = new Date();\n" +
                "   today.setHours(0,0,0,0);\n" +
                "   var dateString = result[i].datum;\n" +
                "   dateString.replace(/,/g,'');\n" +
                "   var date = new Date(dateString);\n" +
                "   date.setHours(0,0,0,0);\n" +
                "   if (date.getTime() < today.getTime() || result[i].isTrained == true){\n" +
                "      color = \"#C5DEEB\";\n" +
                "   } else {color = \"#3A87AD\"}\n" +
                "   addEvent(result[i].appointment_id, result[i].sessionName, result[i].datum, result[i].setNames, color);" +
                "};\n" +
                "}");
    }

}
