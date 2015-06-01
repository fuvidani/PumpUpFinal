package sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.gui.controller.exercises.ExercisesController;
import sepm.ss15.grp16.gui.controller.user.LoginController;

import java.util.Optional;


/**
 * Created by lukas on 01.05.2015.
 * Edited by Daniel Fuevesi
 */
public class Main extends Application {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(final Stage primaryStage) throws Exception {
        LOGGER.info("starting application");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        FXMLLoader fxmlLoader = new FXMLLoader();



        //add spring context to JavaFX (http://koenserneels.blogspot.co.at/2012/11/javafx-2-with-spring.html)
        fxmlLoader.setControllerFactory(context::getBean);

        fxmlLoader.setLocation(LoginController.class.getClassLoader().getResource("fxml/exercise/Exercises.fxml"));


        Pane pane = (Pane) fxmlLoader.load(LoginController.class.getClassLoader().getResourceAsStream("fxml/exercise/Exercises.fxml"));
        // Pane pane = (Pane) fxmlLoader.load(getClass().getResource("/fxml/Main.fxml"));
//        LoginController loginController = fxmlLoader.getController();
        ExercisesController exercisesController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(pane, 1900, 1000));
//        primaryStage.setMinWidth(350);
//        primaryStage.setMinHeight(210);

//        primaryStage.setOnCloseRequest(e -> {
//            e.consume();
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Programm schließen");
//            alert.setHeaderText("Das Programm wird beendet.");
//            alert.setContentText("Möchten Sie das Programm wirklich beenden?");
//            ButtonType yes = new ButtonType("Ja");
//            ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
//            alert.getButtonTypes().setAll(yes, cancel);
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == yes) {
//                // TODO: For cleaning purposes: close DB-connection
//                context.close();
//                primaryStage.close();
//
//            } else {
//                primaryStage.show();
//            }
//        });
//        LOGGER.info("configuration successful");

        primaryStage.show();

    }
}
