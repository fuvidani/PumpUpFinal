package sepm.ss15.grp16.gui;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Maximilian on 22.05.2015.
 * This is the main entry point of the application.
 * Initializes the context and opens up the login window.
 */
public class NewMain extends Application {

    private static final Logger                         LOGGER  = LogManager.getLogger(NewMain.class);
    private static       ClassPathXmlApplicationContext context = null;

    /**
     * Main entry point of the application.
     * @param args program arguments
     */
    public static void main(String[] args) {
        try {
            Webcam.getDefault();
            launch(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            context.close();

        }

    }

    /**
     * Starts the application by opening the login window.
     * @param primaryStage primary stage of the application
     * @throws Exception
     */
    public void start(final Stage primaryStage) throws Exception {
        LOGGER.info("starting application");
        context = new ClassPathXmlApplicationContext("spring-config.xml");


        FrameWindow mainFrame = new FrameWindow(context, primaryStage, PageEnum.Login);

        primaryStage.setOnCloseRequest(e -> {
            context.close();
            primaryStage.close();
        });


        LOGGER.info("configuration successful");
        primaryStage.show();


    }


}
