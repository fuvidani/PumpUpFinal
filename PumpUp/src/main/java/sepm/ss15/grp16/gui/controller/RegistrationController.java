package sepm.ss15.grp16.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.entity.PictureHistory;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.service.BodyfatHistoryService;
import sepm.ss15.grp16.service.PictureHistoryService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class represents the controller for a registration gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class RegistrationController implements Initializable {

    @FXML
    Pane registrationPane;
    @FXML
    Button picture_Button;
    @FXML
    Rectangle picture_Rectangle;
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
    RadioButton male_radioButton;
    @FXML
    RadioButton female_radioButton;
    @FXML
    ImageView picture_imageView;
    @FXML
    private ToggleGroup group;

    private UserService userService;
    private WeightHistoryService weightHistoryService;
    private BodyfatHistoryService bodyfatHistoryService;
    private PictureHistoryService pictureHistoryService;
    private String filePath;

    private static final Logger LOGGER = LogManager.getLogger();

    public void setUserService(UserService userService) {
        this.userService = userService;
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
    }

    @FXML
    public void registerClicked() {

        //TODO: Validierung verbessern/aendern

        LOGGER.debug("Register clicked");
        String username;
        Integer age = null;
        Integer weight = null;
        Integer height = null;
        Integer bodyfat = null;
        Boolean gender;
        String error = "";

        username = username_textField.getText();

        if (username.length() > 25 || username.length() < 2) {
            error = "Der Username muss zwischen 2 und 25 Zeichen lang sein.\n";
        }

        try {
            age = Integer.parseInt(age_textField.getText());
            if (age < 0 || age > 120) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error += "Das Alter muss eine gültige Zahl zwischen 0 und 120 sein.\n";
        }

        try {
            weight = Integer.parseInt(weight_textField.getText());
            if (weight < 0 || weight > 500) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error += "Das Gewicht muss eine gültige Zahl zwischen 0 und 500 sein.\n";
        }

        try {
            height = Integer.parseInt(height_textField.getText());
            if (height < 0 || height > 300) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error += "Die Größe muss eine gültige Zahl zwischen 0 und 300 sein.\n";
        }

        try {
            bodyfat = Integer.parseInt(bodyfat_textField.getText());
            if (bodyfat < 0 || bodyfat > 100) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error += "Der Körperfettanteil muss eine gültige Zahl zwischen 0 und 100 sein.\n";
        }

        if (filePath == null) {
            error += "Es wurde kein Bild ausgewaehlt.\n";
        }

        if (!error.isEmpty()) {
            LOGGER.warn("Tried to create user with invalid data");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(error);
            alert.showAndWait();
            return;
        }

        String genderString = ((RadioButton) group.getSelectedToggle()).getText();
        gender = genderString.equals("Male") ? true : false;

        User user = new User(null, username, gender, age, height, false);
        try {
            userService.create(user);
            WeightHistory weightHistory = new WeightHistory(null, user.getUser_id(), weight, null);
            BodyfatHistory bodyfatHistory = new BodyfatHistory(null, user.getUser_id(), bodyfat, null);
            PictureHistory pictureHistory = new PictureHistory(null, user.getUser_id(), filePath, null);
            weightHistoryService.create(weightHistory);
            bodyfatHistoryService.create(bodyfatHistory);
            pictureHistoryService.create(pictureHistory);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Registration-Information");
            alert.setContentText("Sie haben sich erfolgreich registriert.");
            alert.showAndWait();
            Stage stage = (Stage) registrationPane.getScene().getWindow();
            stage.close();

        } catch (ServiceException e) {
            LOGGER.error("Couldn't create User");
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
            picture_Rectangle.setVisible(false);
        }

        picture_Button.setDisable(false);
    }

}
