package sepm.ss15.grp16.gui.controller.main;

import javafx.fxml.FXML;
import sepm.ss15.grp16.gui.controller.Controller;

/**
 * Created by Daniel Fuevesi on 15.06.15.
 * This controller is responsisble for window which displays a helping description for how to measure the own bodyfat percentage.
 */
public class BodyfatHelpController extends Controller{


    @Override
    public void initController(){
       // Nothing to initialize.
    }

    /**
     * This method is called whenever the user hits the "Fertig" button.
     * The dialog automatically closes and navigates back to the parent.
     */
    @FXML
    public void doneClicked(){
        mainFrame.navigateToParent();
    }
}
