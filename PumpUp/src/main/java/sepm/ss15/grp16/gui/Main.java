package sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.gui.controller.ExercisesController;


/**
 * Created by lukas on 01.05.2015.
 * Edited by Daniel Fuevesi
 */
public class Main extends Application{
    private static final Logger LOGGER = LogManager.getLogger(Main.class);


    public void start(final Stage primaryStage) throws Exception {
        LOGGER.info("starting application");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        FXMLLoader fxmlLoader = new FXMLLoader();

        //add spring context to JavaFX (http://koenserneels.blogspot.co.at/2012/11/javafx-2-with-spring.html)
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> clazz) {
                return context.getBean(clazz);
            }
        });

        LOGGER.info("configuration successful");

        Pane pane = (Pane) fxmlLoader.load(ExercisesController.class.getClassLoader().getResourceAsStream("fxml/Exercises.fxml"));
        ExercisesController exercisesController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(pane, 1300, 600));
        primaryStage.show();
    }

    public static void main(String[] args){
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
