package sepm.ss15.grp16.gui.controller.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.Main.MainController;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class represents the controller for a login gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class LoginController  extends Controller implements Initializable {

    private StageTransitionLoader transitionLoader;

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
        this.transitionLoader = new StageTransitionLoader(this);
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
        transitionLoader.openStage("fxml/Registration.fxml", (Stage) loginPane.getScene().getWindow(), "Registrierung", 360, 430, false);
    }

    @FXML
    public void loginButtonClicked() {
        LOGGER.debug("Login button clicked");
        try {
            FXMLLoader loader = new FXMLLoader();

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> clazz) {
                    return context.getBean(clazz);
                }
            });

            loader.setLocation(LoginController.class.getClassLoader().getResource("fxml/Main.fxml"));
            Pane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("PumpUp!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(loginPane.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            Controller to = loader.getController();
            to.setStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.setMinWidth(500);
            dialogStage.setMinHeight(500);
            dialogStage.setMaximized(true);
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    e.consume();
                }
            });

            LOGGER.info("New stage successfully launched!");
            Stage stage = (Stage)this.loginPane.getScene().getWindow();
            stage.hide();
            dialogStage.showAndWait();
            stage.show();
            // user closed dialog
        }catch (IOException e){
            LOGGER.info("Error on opening new stage, reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
