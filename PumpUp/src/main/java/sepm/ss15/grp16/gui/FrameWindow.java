package sepm.ss15.grp16.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import sepm.ss15.grp16.gui.controller.Controller;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by Maximilian on 21.05.2015.
 */
public class FrameWindow extends BorderPane {

    private ApplicationContext context;
    private Stage stage;

    private Controller activeController = null;
    private Stack<String> fxmlStack = new Stack<>();

    public FrameWindow(ApplicationContext context, Stage stage, PageEnum mainPage) {

        init(context, stage, mainPage);
    }

    private FrameWindow(ApplicationContext context, Stage stage, PageEnum mainPage, Controller parent) {

        activeController = parent;
        init(context, stage, mainPage);
    }

    private void init(ApplicationContext context, Stage stage, PageEnum mainPage)
    {
        this.context = context;
        this.stage = stage;

        stage.setScene(new Scene(this));
        stage.setTitle(mainPage.getTitle());

        navigateToChild(mainPage);
    }

    /**
     * Opens a new site in the frame as a child of the actual site.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     * @param mainPage The site to navigate to.
     */
    public void navigateToChild(PageEnum mainPage) {
        try {
            Controller controller = load(mainPage.getFxml());
            controller.setMainFrame(this);
            fxmlStack.push(mainPage.getFxml());
            controller.setParentController(activeController);
            if(activeController != null) {
                activeController.setChildController(controller); //????
            }
            activeController = controller;
            controller.initController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reopens the parent site in the frame as the actual site.
     * The parent and child Controllers get re-referenced to each other
     * and afterwords initController in the new Controller is is called.
     * If the actual site is the main site of the frame the stage gets closed.
     * @param mainPage The site to navigate to.
     */
    public void navigateToPerent() {
        if(fxmlStack.size() == 1)
        {
            stage.close();
            activeController = null;
        }
        else {
            try {
                fxmlStack.pop();
                Controller controller = load(fxmlStack.peek());
                controller.setMainFrame(this);
                controller.setParentController(activeController.getParentController().getParentController());
                controller.setChildController(activeController);
                activeController.setParentController(controller); ///???
                activeController = controller;
                controller.initController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected void navigateToMain()
    {
        while(fxmlStack.size() > 1)
        {
            navigateToPerent();
        }
    }

    /**
     * Opens a new site in a new frame as a child of the actual site and waits while the Dialog is closed.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     * @param mainPage The site to navigate to.
     */
    public void openDialog(PageEnum mainPage) {
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setMinWidth(mainPage.getMinWidth());
        dialogStage.setMinHeight(mainPage.getMinHeight());

        FrameWindow frame = new FrameWindow(context, dialogStage, mainPage, activeController);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    public void openMainWindowFrame() {
        stage.hide();
        openDialog(PageEnum.Main);
        stage.show();
    }

    private Controller load(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(this.getClass().getClassLoader().getResource(fxml));
        setCenter(loader.load());
        return loader.getController();
    }
}
