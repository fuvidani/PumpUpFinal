package sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.gui.controller.ExerciseController;


/**
 * Created by lukas on 01.05.2015.
 * Edited by Daniel Fuevesi
 */
public class Main extends Application{


    public void start(final Stage primaryStage) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        FXMLLoader fxmlLoader = new FXMLLoader();

        //add spring context to JavaFX (http://koenserneels.blogspot.co.at/2012/11/javafx-2-with-spring.html)
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> clazz) {
                return context.getBean(clazz);
            }
        });

        Pane pane = (Pane) fxmlLoader.load(ExerciseController.class.getClassLoader().getResourceAsStream("fxml/Exercise.fxml"));
        ExerciseController exerciseController = fxmlLoader.getController();
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
