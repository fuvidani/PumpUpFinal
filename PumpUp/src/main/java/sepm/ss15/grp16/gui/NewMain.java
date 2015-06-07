package sepm.ss15.grp16.gui;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Maximilian on 22.05.2015.
 */
public class NewMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Webcam.getDefault();
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(final Stage primaryStage) throws Exception {
        LOGGER.info("starting application");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        FrameWindow mainFrame = new FrameWindow(context, primaryStage, PageEnum.Login);

        primaryStage.setOnCloseRequest(e -> {
//                e.consume();
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setTitle("Programm schlie\u00dfen");
//                alert.setHeaderText("Das Programm wird beendet.");
//                alert.setContentText("M\u00f6chten Sie das Programm wirklich beenden?");
//                ButtonType yes = new ButtonType("Ja");
//            ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
//            alert.getButtonTypes().setAll(yes, cancel);
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == yes) {
            context.close();
            primaryStage.close();

//            } else {
//                primaryStage.show();
//            }
        });

        LOGGER.info("configuration successful");

        primaryStage.show();
    }
}
