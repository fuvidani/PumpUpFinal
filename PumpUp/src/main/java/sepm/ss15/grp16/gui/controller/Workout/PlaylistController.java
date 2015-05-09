package sepm.ss15.grp16.gui.controller.Workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 *
 */
public class PlaylistController implements Initializable{


    @FXML
    private TableColumn<?, ?> artistColumn;

    @FXML
    private TableView<?> playlist;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> lengthColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void editButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }


}
