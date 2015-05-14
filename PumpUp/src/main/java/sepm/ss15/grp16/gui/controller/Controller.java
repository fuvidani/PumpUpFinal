package sepm.ss15.grp16.gui.controller;

import javafx.stage.Stage;

/**
 * Created by Daniel Fuevesi on 09.05.15.
 * This class is the supertype of all controllers.
 */
public abstract class Controller {

    protected Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public Stage getStage(){
        return this.stage;
    }
}
