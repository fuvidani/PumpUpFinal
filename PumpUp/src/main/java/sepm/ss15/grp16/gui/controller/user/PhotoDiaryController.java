package sepm.ss15.grp16.gui.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import sepm.ss15.grp16.gui.controller.main.MainController;
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
    private MainController mainController;
    private boolean notADiaryPictureInImageView = false;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @Override
    public void initController() {

        mainController = (MainController) parentController;
        User loggedInUser = userService.getLoggedInUser();
        try {
            pictureHistoryList = pictureHistoryService.searchByUserID(loggedInUser.getUser_id());
            indexOfCurrentPicture = 0;
            if (!pictureHistoryList.isEmpty()) {
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                notADiaryPictureInImageView = false;
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
                notADiaryPictureInImageView = false;
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go forward in picturehistory");
            showAlert("Fehler", "Fehler beim durchbl\u00e4ttern der Fotos", "Das n\u00e4chste Foto konnte nicht geladen werden.", AlertType.ERROR);
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
                notADiaryPictureInImageView = false;
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go backward in picturehistory");
            showAlert("Fehler", "Fehler beim durchbl\u00e4ttern der Fotos", "Das vorherige Foto konnte nicht geladen werden.", AlertType.ERROR);
        }
    }

    @FXML
    public void addButtonClicked() {
        try {
            if (selectedPicturePath != null && notADiaryPictureInImageView) {
                PictureHistory pictureHistory = new PictureHistory(null, userService.getLoggedInUser().getUser_id(), selectedPicturePath, null);
                pictureHistoryService.create(pictureHistory);
                reloadImages();
                selectedPicturePath = null;
                showAlert("Information", "Foto-Information", "Das Foto wurde erfolgreich zu ihrem Tagebuch hinzugef\u00fcgt.", AlertType.INFORMATION);
            } else {
                LOGGER.error("Couldn't create picturehistory");
                showAlert("Fehler", "Fehlerhafte Angaben", "Es wurde kein Bild ausgew\u00e4hlt.", AlertType.ERROR);
            }
        } catch (ValidationException e) {
            LOGGER.error("Couldn't create picturehistory");
            showAlert("Fehler", "Fehlerhafte Angaben", e.getValidationMessage(), AlertType.ERROR);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't create picturehistory");
            showAlert("Fehler", "Fehlerhafte Angaben", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void deleteButtonClicked() {
        LOGGER.info("Deleting current picture from diary...");
        try {
            if (notADiaryPictureInImageView) {
                showAlert("Fehler", "Fehlerhafte Angaben", "Es wurde kein Bild aus ihrem Fototagebuch ausgewählt", AlertType.ERROR);
            } else {
                if (!pictureHistoryList.isEmpty()) {
                    pictureHistoryService.delete(pictureHistoryList.get(indexOfCurrentPicture));
                    reloadImages();
                    showAlert("Information", "Foto-Information", "Das Foto wurde erfolgreich aus ihrem Tagebuch gel\u00f6scht.", AlertType.INFORMATION);
                } else {
                    showAlert("Fehler", "Fehlerhafte Angaben", "Es wurde kein Bild ausgewählt.", AlertType.ERROR);
                }
            }
        } catch (ValidationException e) {
            LOGGER.error("Couldn't delete picturehistory");
            showAlert("Fehler", "Fehlerhafte Angaben", e.getValidationMessage(), AlertType.ERROR);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't delete picturehistory");
            showAlert("Fehler", "Fehlerhafte Angaben", e.getMessage(), AlertType.ERROR);
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
        filechooser.setTitle("Bild ausw\u00e4hlen: ");
        File selectedFile = filechooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedPicturePath = selectedFile.getPath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            notADiaryPictureInImageView = true;
        }
        selectPictureButton.setDisable(false);
    }

    @FXML
    public void webCamButtonClicked() {
        LOGGER.debug("Open webCam window");
        try {
            mainFrame.openDialog(PageEnum.Webcam);
        } catch (Exception e) {
            LOGGER.error("Couldn't open webcam-window");
            showAlert("Fehler", "Fehlerhafter Dialog", "Webcam-Dialog konnte nicht geoeffnet werden", AlertType.ERROR);
        }
    }

    public void reloadImages() {
        LOGGER.info("Reloading images in photodiary");
        mainController.updateImage();
        User loggedInUser = userService.getLoggedInUser();
        try {
            pictureHistoryList = pictureHistoryService.searchByUserID(loggedInUser.getUser_id());
            indexOfCurrentPicture = 0;
            if (!pictureHistoryList.isEmpty()) {
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
            notADiaryPictureInImageView = false;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void showAlert(String title, String headerText, String contentText, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
