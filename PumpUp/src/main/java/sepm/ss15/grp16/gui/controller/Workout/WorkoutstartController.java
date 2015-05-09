package sepm.ss15.grp16.gui.controller.Workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This controller controls the little pop-up window before the actual training starts.
 *
 */
public class WorkoutstartController implements Initializable{

    @FXML
    private ListView<?> toDoListView;

    @FXML
    private Label musicPathLabel;

    @FXML
    private Label trainingTypeLabel;

    @FXML
    private Button startButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void browseMusicClicked(ActionEvent event) {

    }

    @FXML
    void startButtonClicked(ActionEvent event) {

    }



}
