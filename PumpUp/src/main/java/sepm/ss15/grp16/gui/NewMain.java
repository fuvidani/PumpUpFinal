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
    private static final Logger                         LOGGER  = LogManager.getLogger(NewMain.class);
    private static       ClassPathXmlApplicationContext context = null;

    public static void main(String[] args) {
        try {
            Webcam.getDefault();
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
            context.close();

        }

    }

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
