package sepm.ss15.grp16.gui.controller;

import javafx.stage.Stage;
import sepm.ss15.grp16.gui.FrameWindow;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 09.05.15.
 * This class is the supertype of all controllers.
 */
public abstract class Controller {

    public void initController()
    {

    }

    protected FrameWindow mainFrame;

    protected Controller parentController;

    protected Controller childController;

    protected Stage stage;

    public void setChildController(Controller childController) {
        this.childController = childController;
    }

    public void setMainFrame(FrameWindow mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }

    public FrameWindow getMainFrame() {
        return mainFrame;
    }

    public Controller getParentController() {
        return parentController;
    }

    public Controller getChildController() {
        return childController;
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}
