package sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.gui.controller.Main.MainController;
import sepm.ss15.grp16.gui.controller.User.LoginController;

import java.util.Optional;


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

        fxmlLoader.setLocation(LoginController.class.getClassLoader().getResource("fxml/Login.fxml"));
         Pane pane = (Pane) fxmlLoader.load(LoginController.class.getClassLoader().getResourceAsStream("fxml/Login.fxml"));
       // Pane pane = (Pane) fxmlLoader.load(getClass().getResource("/fxml/Main.fxml"));
        LoginController loginController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(pane));

        LOGGER.info("configuration successful");

        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                e.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Programm schließen");
                alert.setHeaderText("Das Programm wird beendet.");
                alert.setContentText("Möchten Sie das Programm wirklich beenden?");
                ButtonType yes = new ButtonType("Ja");
                ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(yes, cancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == yes) {
                    primaryStage.close();
                } else {
                    primaryStage.show();
                }
            }
        });

    }

    public static void main(String[] args){
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
