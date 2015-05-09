package sepm.ss15.grp16.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Daniel Fuevesi on 09.05.15.
 * This class encapsulates the transition action between stages.
 */
public class StageTransitionLoader {

    private static final Logger LOGGER = LogManager.getLogger();
    private Controller from;

    public StageTransitionLoader(Controller from){
        this.from = from;
    }

    /**
     * This method opens the desired stage of the calling controller.
     * This is just a prototype: for DTO traffic the method must be upgraded (transmit the transitionLoader itself for example)
     * @param fxmlResource the fxml resource of the stage
     * @param primStage the owner of the new stage (stage of the calling controller)
     * @param title title of the new stage
     * @param minWidth minimum width of the new stage
     * @param minHeight minimum height of the new stage
     * @param maximized the new stage will automatically maximized if this flag is set to true
     */
    public void openStage(String fxmlResource, Stage primStage, String title, double minWidth, double minHeight, boolean maximized){

        try {
            FXMLLoader loader = new FXMLLoader();
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> clazz) {
                    return context.getBean(clazz);
                }
            });


            loader.setLocation(this.from.getClass().getClassLoader().getResource(fxmlResource));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            Controller to = loader.getController();
            to.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.setMinWidth(minWidth);
            dialogStage.setMinHeight(minHeight);
            if(maximized){
                dialogStage.setMaximized(true);
            }
            LOGGER.info("New stage successfully launched!");
            dialogStage.show();
            // user closed dialog
        }catch (IOException e){
            LOGGER.info("Error on opening new stage, reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
