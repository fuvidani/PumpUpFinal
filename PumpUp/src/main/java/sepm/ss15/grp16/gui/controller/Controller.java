package sepm.ss15.grp16.gui.controller;

import javafx.stage.Stage;
import sepm.ss15.grp16.gui.FrameWindow;

/**
 * Created by Daniel Fuevesi on 09.05.15.
 * This class is the supertype of all controllers.
 */
public abstract class Controller {

    protected FrameWindow mainFrame;
    protected Controller parentController;
    protected Controller childController;
    protected Stage stage;

    public void initController() {

    }

    public FrameWindow getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(FrameWindow mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Controller getParentController() {
        return parentController;
    }

    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }

    public Controller getChildController() {
        return childController;
    }

    public void setChildController(Controller childController) {
        this.childController = childController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
