package sepm.ss15.grp16.gui.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.main.MainController;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

/**
 * This class represents the controller for a user-edit gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class UserEditController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    Pane userEditPane;
    @FXML
    TextField ageTextField;
    @FXML
    TextField heightTextField;
    @FXML
    TextField weightTextField;
    @FXML
    TextField bodyfatTextField;
    @FXML
    TextField emailTextField;
    private UserService userService;
    private WeightHistoryService weightHistoryService;
    private BodyfatHistoryService bodyfatHistoryService;
    private MainController mainController;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setBodyfatHistoryService(BodyfatHistoryService bodyfatHistoryService) {
        this.bodyfatHistoryService = bodyfatHistoryService;
    }

    public void setWeightHistoryService(WeightHistoryService weightHistoryService) {
        this.weightHistoryService = weightHistoryService;
    }

    @Override
    public void initController() {

        mainController = (MainController) parentController;
        Integer user_id = userService.getLoggedInUser().getUser_id();
        Integer age = userService.getLoggedInUser().getAge();
        Integer height = userService.getLoggedInUser().getHeight();
        String email = userService.getLoggedInUser().getEmail();
        Integer weight = null;
        Integer bodyfat = null;

        try {
            WeightHistory actualWeighthistory = weightHistoryService.getActualWeight(user_id);
            if (actualWeighthistory != null) {
                weight = actualWeighthistory.getWeight();
            }

            BodyfatHistory actualBodyfathistory = bodyfatHistoryService.getActualBodyfat(user_id);
            if (actualBodyfathistory != null) {
                bodyfat = actualBodyfathistory.getBodyfat();
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
        }

        ageTextField.setText(Integer.toString(age));
        heightTextField.setText(Integer.toString(height));

        if (weight != null) {
            weightTextField.setText(Integer.toString(weight));
        }

        if (bodyfat != null) {
            bodyfatTextField.setText(Integer.toString(bodyfat));
        } else {
            bodyfatTextField.setPromptText("Keine Angabe");
        }

        if (email == null || email.isEmpty()) {
            emailTextField.setPromptText("Keine Angabe");
        } else {
            emailTextField.setText(email);
        }
    }

    @FXML
    public void saveChangesButtonClicked() {
        LOGGER.debug("Save changes clicked");

        Integer user_id = userService.getLoggedInUser().getUser_id();
        Integer age = null;
        Integer weight = null;
        Integer height = null;
        Integer bodyfat = null;
        String email = null;
        String error = "";

        if (!emailTextField.getText().isEmpty()) {
            email = emailTextField.getText();
        }

        try {
            age = Integer.parseInt(ageTextField.getText());
        } catch (NumberFormatException e) {
            error += "Das Alter muss eine g\u00fcltige Zahl gr\u00f6\u00dfer 0 sein.\n";
        }

        try {
            weight = Integer.parseInt(weightTextField.getText());
        } catch (NumberFormatException e) {
            error += "Das Gewicht muss eine g\u00fcltige Zahl gr\u00f6\u00dfer 0 sein.\n";
        }

        try {
            height = Integer.parseInt(heightTextField.getText());
        } catch (NumberFormatException e) {
            error += "Die Gr\u00f6\u00dfe muss eine g\u00fcltige Zahl gr\u00f6\u00dfer 0 sein.\n";
        }

        if (!bodyfatTextField.getText().isEmpty()) {
            try {
                bodyfat = Integer.parseInt(bodyfatTextField.getText());
                if (bodyfat < 0 || bodyfat > 100) {
                    error += "Der K\u00f6rperfettanteil muss eine g\u00fcltige Zahl zwischen 0 und 100 sein.\n";
                }
            } catch (NumberFormatException e) {
                error += "Der K\u00f6rperfettanteil muss eine g\u00fctige Zahl zwischen 0 und 100 sein.\n";
            }
        }

        if (!error.isEmpty()) {
            LOGGER.error("Tried to update user with invalid data");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(error);
            alert.showAndWait();
            return;
        }

        User updatedUser = userService.getLoggedInUser();
        updatedUser.setAge(age);
        updatedUser.setHeight(height);
        updatedUser.setEmail(email);

        try {

            userService.update(updatedUser);

            WeightHistory weightHistory = new WeightHistory(null, user_id, weight, null);
            weightHistoryService.create(weightHistory);

            if (bodyfat != null) {
                BodyfatHistory bodyfatHistory = new BodyfatHistory(null, user_id, bodyfat, null);
                bodyfatHistoryService.create(bodyfatHistory);
            }

            mainController.updateUserData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Update-Information");
            alert.setContentText("Sie haben ihre Daten erfolgreich aktualisiert.");
            alert.showAndWait();
            mainFrame.navigateToParent();
        } catch (ValidationException e) {
            LOGGER.error("Couldn't update user");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getValidationMessage());
            alert.showAndWait();
        } catch (ServiceException e) {
            LOGGER.error("Couldn't update user");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
