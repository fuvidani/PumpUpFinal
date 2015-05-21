package sepm.ss15.grp16.gui.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents the controller for a registration gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class RegistrationController extends Controller implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    Pane registrationPane;
    @FXML
    Button picture_Button;
    @FXML
    TextField username_textField;
    @FXML
    TextField age_textField;
    @FXML
    TextField height_textField;
    @FXML
    TextField weight_textField;
    @FXML
    TextField bodyfat_textField;
    @FXML
    TextField email_textField;
    @FXML
    RadioButton male_radioButton;
    @FXML
    RadioButton female_radioButton;
    @FXML
    ImageView picture_imageView;
    private StageTransitionLoader transitionLoader;
    @FXML
    private ToggleGroup group;
    private UserService userService;
    private LoginController loginController;
    private WeightHistoryService weightHistoryService;
    private BodyfatHistoryService bodyfatHistoryService;
    private PictureHistoryService pictureHistoryService;
    private String filePath;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setWeightHistoryService(WeightHistoryService weightHistoryService) {
        this.weightHistoryService = weightHistoryService;
    }

    public void setBodyfatHistoryService(BodyfatHistoryService bodyfatHistoryService) {
        this.bodyfatHistoryService = bodyfatHistoryService;
    }

    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
    }

    @FXML
    public void registerClicked() {

        LOGGER.debug("Register clicked");
        String username;
        Integer age = null;
        Integer weight = null;
        Integer height = null;
        Integer bodyfat = null;
        String email = null;
        Boolean gender;
        String error = "";

        username = username_textField.getText();

        if (!email_textField.getText().isEmpty()) {
            email = email_textField.getText();
        }

        try {
            age = Integer.parseInt(age_textField.getText());
        } catch (NumberFormatException e) {
            error += "Das Alter muss eine gültige Zahl größer 0 sein.\n";
        }

        try {
            weight = Integer.parseInt(weight_textField.getText());
        } catch (NumberFormatException e) {
            error += "Das Gewicht muss eine gültige Zahl größer 0 sein.\n";
        }

        try {
            height = Integer.parseInt(height_textField.getText());
        } catch (NumberFormatException e) {
            error += "Die Größe muss eine gültige Zahl größer 0 sein.\n";
        }

        if (!bodyfat_textField.getText().isEmpty()) {
            try {
                bodyfat = Integer.parseInt(bodyfat_textField.getText());
                if (bodyfat < 0 || bodyfat > 100) {
                    error += "Der Körperfettanteil muss eine gültige Zahl zwischen 0 und 100 sein.\n";
                }
            } catch (NumberFormatException e) {
                error += "Der Körperfettanteil muss eine gültige Zahl zwischen 0 und 100 sein.\n";
            }
        }

        if (!error.isEmpty()) {
            LOGGER.error("Tried to create user with invalid data");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(error);
            alert.showAndWait();
            return;
        }

        String genderString = ((RadioButton) group.getSelectedToggle()).getText();
        gender = genderString.equals("Männlich");

        User user = new User(null, username, gender, age, height, email, null, false);

        try {

            loginController.insertUserInTable(userService.create(user));

            WeightHistory weightHistory = new WeightHistory(null, user.getUser_id(), weight, null);
            weightHistoryService.create(weightHistory);

            if (bodyfat != null) {
                BodyfatHistory bodyfatHistory = new BodyfatHistory(null, user.getUser_id(), bodyfat, null);
                bodyfatHistoryService.create(bodyfatHistory);
            }

            if (filePath != null) {
                PictureHistory pictureHistory = new PictureHistory(null, user.getUser_id(), filePath, null);
                pictureHistoryService.create(pictureHistory);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Registration-Information");
            alert.setContentText("Sie haben sich erfolgreich registriert.");
            alert.showAndWait();
            Stage stage = (Stage) registrationPane.getScene().getWindow();
            stage.close();
        } catch (ValidationException e) {
            LOGGER.error("Couldn't create User");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getValidationMessage());
            alert.showAndWait();
        } catch (ServiceException e) {
            LOGGER.error("Couldn't create User");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void choosePictureClicked() {
        LOGGER.debug("Choose picture clicked");
        picture_Button.setDisable(true);

        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg", "*.JPEG", "*.jpeg");
        filechooser.getExtensionFilters().add(extFilterJPG);
        filechooser.setTitle("Bild auswählen: ");
        File selectedFile = filechooser.showOpenDialog(null);

        if (selectedFile != null) {
            filePath = selectedFile.getPath();
            Image image = new Image(selectedFile.toURI().toString(), picture_imageView.getFitWidth(), picture_imageView.getFitHeight(), false, false);
            picture_imageView.setImage(image);
        }
        picture_Button.setDisable(false);
    }
}
