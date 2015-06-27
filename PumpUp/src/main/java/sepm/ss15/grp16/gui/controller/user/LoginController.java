package sepm.ss15.grp16.gui.controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.UserService;

/**
 * This class represents the controller for a login gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class LoginController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    Pane   loginPane;
    @FXML
    Button login_button;
    @FXML
    Button registrieren_button;
    @FXML
    private TableView<User> user_tableView = new TableView<>();
    @FXML
    private TableColumn<User, String> usernameCol;
    private UserService               userService;
    private ObservableList<User>      masterData;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initController() {

        LOGGER.debug("Initialize LoginController");
        try {
            masterData = FXCollections.observableList(userService.findAll());
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            user_tableView.setItems(masterData);
            user_tableView.getSelectionModel().selectFirst();
            user_tableView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    loginButtonClicked();
                    event.consume();
                }
            });
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
        }
        login_button.setTooltip(new Tooltip("anmelden"));
        registrieren_button.setTooltip(new Tooltip("registrieren"));
    }

    @FXML
    public void registerClicked() {
        LOGGER.debug("Register button clicked");
        try {
            mainFrame.openDialog(PageEnum.Registration);
        } catch (Exception e) {
            LOGGER.error("Couldn't open main-window");
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
            alert.setContentText("Es wurde kein user ausgew\u00e4hlt!");
            alert.showAndWait();
            return;
        }
        try {
            mainFrame.openMainWindowFrame();
        } catch (ClassCastException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }

    }

    public void insertUserInTable(User newUser) {
        LOGGER.info("Inserting new user in tableview");
        masterData.add(newUser);
    }
}
