package sepm.ss15.grp16.gui.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.ImageLoader;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;

import java.io.File;
import java.util.List;

/**
 * This class represents the controller for a photoDiary gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class PhotoDiaryController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    ImageView imageView;
    @FXML
    Label dateLabel;
    @FXML
    Button selectPictureButton;
    private UserService userService;
    private PictureHistoryService pictureHistoryService;
    private List<PictureHistory> pictureHistoryList;
    private int indexOfCurrentPicture;
    private String selectedPicturePath;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @Override
    public void initController() {

        User loggedInUser = userService.getLoggedInUser();
        try {
            pictureHistoryList = pictureHistoryService.searchByUserID(loggedInUser.getUser_id());
            indexOfCurrentPicture = 0;
            if (!pictureHistoryList.isEmpty()) {
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    @FXML
    public void forwardButtonClicked() {
        LOGGER.info("Going forward in diary");
        try {
            if (indexOfCurrentPicture < pictureHistoryList.size() - 1) {
                indexOfCurrentPicture++;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go forward in picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim durchblättern der Fotos");
            alert.setContentText("Das nächste Foto konnte nicht geladen werden.");
            alert.showAndWait();
        }
    }

    @FXML
    public void backwardButtonClicked() {
        LOGGER.info("Going backwards in diary");
        try {
            if (indexOfCurrentPicture > 0) {
                indexOfCurrentPicture--;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go backward in picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim durchblättern der Fotos");
            alert.setContentText("Das vorherige Foto konnte nicht geladen werden.");
            alert.showAndWait();
        }
    }

    @FXML
    public void addButtonClicked() {
        try {
            if (selectedPicturePath != null) {
                PictureHistory pictureHistory = new PictureHistory(null, userService.getLoggedInUser().getUser_id(), selectedPicturePath, null);
                pictureHistoryService.create(pictureHistory);
                reloadImages();
                selectedPicturePath = null;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Foto-Information");
                alert.setContentText("Das Foto wurde erfolgreich zu ihrem Tagebuch hinzugefügt.");
                alert.showAndWait();
            } else {
                LOGGER.error("Couldn't create picturehistory");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehlerhafte Angaben");
                alert.setContentText("Es wurde kein Bild ausgewählt.");
                alert.showAndWait();
            }
        } catch (ValidationException e) {
            LOGGER.error("Couldn't create picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getValidationMessage());
            alert.showAndWait();
        } catch (ServiceException e) {
            LOGGER.error("Couldn't create picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteButtonClicked() {
        LOGGER.info("Deleting current picture from diary...");
        try {
            if (!pictureHistoryList.isEmpty()) {
                pictureHistoryService.delete(pictureHistoryList.get(indexOfCurrentPicture));
                reloadImages();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Foto-Information");
                alert.setContentText("Das Foto wurde erfolgreich aus ihrem Tagebuch gelöscht.");
                alert.showAndWait();
            }
        } catch (ValidationException e) {
            LOGGER.error("Couldn't delete picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getValidationMessage());
            alert.showAndWait();
        } catch (ServiceException e) {
            LOGGER.error("Couldn't delete picturehistory");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlerhafte Angaben");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        LOGGER.info("Deleted current picture from diary");
    }

    @FXML
    public void selectPictureButtonClicked() {
        LOGGER.debug("Choose picture clicked");
        selectPictureButton.setDisable(true);

        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg", "*.JPEG", "*.jpeg");
        filechooser.getExtensionFilters().add(extFilterJPG);
        filechooser.setTitle("Bild auswählen: ");
        File selectedFile = filechooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedPicturePath = selectedFile.getPath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
        selectPictureButton.setDisable(false);
    }

    @FXML
    public void webCamButtonClicked() {
        LOGGER.debug("Open webCam window");
        try {
            mainFrame.openDialog(PageEnum.Webcam);
        } catch (Exception e) {
            LOGGER.error("Couldn't open useredit-window");
        }
    }

    private void reloadImages() {
        User loggedInUser = userService.getLoggedInUser();
        try {
            pictureHistoryList = pictureHistoryService.searchByUserID(loggedInUser.getUser_id());
            indexOfCurrentPicture = 0;
            if (!pictureHistoryList.isEmpty()) {
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
