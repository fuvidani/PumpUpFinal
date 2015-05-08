package sepm.ss15.grp16.gui.controller.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 *
 */
public class Main_UserDataController implements Initializable{


    @FXML
    private TableView<?> userDataTableView;

    @FXML
    private TableColumn<?, ?> dataTypeColumn;

    @FXML
    private TableColumn<?, ?> dataValuesColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

