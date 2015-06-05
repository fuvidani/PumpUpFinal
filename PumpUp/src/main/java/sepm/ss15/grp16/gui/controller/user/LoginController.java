package sepm.ss15.grp16.gui.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.user.UserService;
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
public class LoginController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    Pane loginPane;
    @FXML
    Button login_button;
    @FXML
    Button registrieren_button;
    private StageTransitionLoader transitionLoader;
    @FXML
    private TableView<User> user_tableView = new TableView<>();
    @FXML
    private TableColumn<User, String> usernameCol;
    private UserService userService;
    private ObservableList<User> masterData;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initController() {
        this.transitionLoader = new StageTransitionLoader(this);
        LOGGER.debug("Initialize LoginController");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        try {
            masterData = FXCollections.observableList(userService.findAll());
            user_tableView.setItems(masterData);
            user_tableView.getSelectionModel().selectFirst();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerClicked() {
        LOGGER.debug("Register button clicked");
        try {
            mainFrame.openDialog(PageEnum.Registration);


        } catch (Exception e) {
            LOGGER.error("Couldn't open main-window");
            e.printStackTrace();
        }
    }

    @FXML
    public void loginButtonClicked() {
        LOGGER.debug("Login button clicked");

        User loggedInUser = user_tableView.getSelectionModel().getSelectedItem();
        userService.setLoggedInUser(loggedInUser);

        if (loggedInUser == null) {
            LOGGER.error("Tried to login without selecting a user.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Anmelden");
            alert.setContentText("Es wurde kein user ausgewählt!");
            alert.showAndWait();
            return;
        }

/*        try {
            FXMLLoader loader = new FXMLLoader();

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

            loader.setControllerFactory(context::getBean);

            loader.setLocation(LoginController.class.getClassLoader().getResource("fxml/main/Main.fxml"));
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


            LOGGER.info("New stage successfully launched!");
            Stage stage = (Stage) this.loginPane.getScene().getWindow();
            stage.hide();
            dialogStage.showAndWait();

            stage.show();
            // user closed dialog
        } catch (IOException e) {
            LOGGER.info("Error on opening new stage, reason: " + e.getMessage());
            e.printStackTrace();
        }*/
        mainFrame.openMainWindowFrame();
    }

    public void insertUserInTable(User newUser) {
        LOGGER.info("Inserting new user in tableview");
        masterData.add(newUser);
    }
}
