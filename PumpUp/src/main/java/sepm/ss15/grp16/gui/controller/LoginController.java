package sepm.ss15.grp16.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by michaelsober on 07.05.15.
 */
public class LoginController implements Initializable {

    @FXML Button login_button;
    @FXML Button registrieren_button;

    @FXML private TableView<User> user_tableView = new TableView<>();
    @FXML private TableColumn<User, Integer> userIDCol;
    @FXML private TableColumn<User, String> usernameCol;

    private UserService userService;
    private ObservableList<User> masterData;
    private static final Logger LOGGER = LogManager.getLogger();

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.debug("Initialize LoginController");
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        try{
            masterData = FXCollections.observableList(userService.findAll());
            user_tableView.setItems(masterData);
        }catch(ServiceException e){
            e.printStackTrace();
        }
    }
}
