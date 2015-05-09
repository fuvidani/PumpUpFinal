package sepm.ss15.grp16.gui.controller.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents the controller for a login gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class LoginController implements Initializable {

    @FXML
    Pane loginPane;
    @FXML
    Button login_button;
    @FXML
    Button registrieren_button;

    @FXML
    private TableView<User> user_tableView = new TableView<>();
    @FXML
    private TableColumn<User, Integer> userIDCol;
    @FXML
    private TableColumn<User, String> usernameCol;

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
        try {
            masterData = FXCollections.observableList(userService.findAll());
            user_tableView.setItems(masterData);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerClicked() {
        LOGGER.debug("Register button clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> clazz) {
                    return context.getBean(clazz);
                }
            });
            Stage stage = new Stage();
            Pane pane = fxmlLoader.load(RegistrationController.class.getClassLoader().getResourceAsStream("fxml/Registration.fxml"));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(loginPane.getScene().getWindow());
            RegistrationController registrationController = fxmlLoader.getController();
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            LOGGER.error("Couldn't open registration-window");
            e.printStackTrace();
        }
    }
}
