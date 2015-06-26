package sepm.ss15.grp16.gui.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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
    private ImageView imageView;
    @FXML
    private Label dateLabel;
    @FXML
    private Button selectPictureButton;
    @FXML
    private Button forwardButton;
    @FXML
    private Button backButton;
    private UserService userService;
    private PictureHistoryService pictureHistoryService;
    private List<PictureHistory> pictureHistoryList;
    private int indexOfCurrentPicture;
    private String selectedPicturePath;
    private MainController mainController;

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
            indexOfCurrentPicture = pictureHistoryList.size()-1;
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
        forwardButton.setDisable(true);
        try {
            if (indexOfCurrentPicture < pictureHistoryList.size() - 1) {
                indexOfCurrentPicture++;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                final SequentialTransition sequentialTransition = createTransition(imageView, image);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(arg0 -> forwardButton.setDisable(false));
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            } else {
                indexOfCurrentPicture = 0;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                final SequentialTransition sequentialTransition = createTransition(imageView, image);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(arg0 -> forwardButton.setDisable(false));
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go forward in picturehistory");
            forwardButton.setDisable(false);
            showAlert("Fehler", "Fehler beim durchbl\u00e4ttern der Fotos", "Das n\u00e4chste Foto konnte nicht geladen werden.", AlertType.ERROR);
        }
    }

    @FXML
    public void backwardButtonClicked() {
        LOGGER.info("Going backwards in diary");
        backButton.setDisable(true);
        try {
            if (indexOfCurrentPicture > 0) {
                indexOfCurrentPicture--;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                final SequentialTransition sequentialTransition = createTransition(imageView, image);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(arg0 -> backButton.setDisable(false));
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            } else {
                indexOfCurrentPicture = pictureHistoryList.size() - 1;
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                final SequentialTransition sequentialTransition = createTransition(imageView, image);
                sequentialTransition.play();
                sequentialTransition.setOnFinished(arg0 -> backButton.setDisable(false));
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            }
        } catch (Exception e) {
            LOGGER.error("Couldn't go backward in picturehistory");
            backButton.setDisable(false);
            showAlert("Fehler", "Fehler beim durchbl\u00e4ttern der Fotos", "Das vorherige Foto konnte nicht geladen werden.", AlertType.ERROR);
        }
    }

    private void addPictureToDiary() {
        try {
            if (selectedPicturePath != null) {
                PictureHistory pictureHistory = new PictureHistory(null, userService.getLoggedInUser().getUser_id(), selectedPicturePath, null);
                pictureHistoryService.create(pictureHistory);
                reloadImages();
                selectedPicturePath = null;
                showAlert("Information", "Foto-Information", "Das Foto wurde erfolgreich zu ihrem Tagebuch hinzugef\u00fcgt.", AlertType.INFORMATION);
            } else {
                LOGGER.error("Couldn't create picturehistory");
                showAlert("Fehler", "Fehlerhafte Angaben", "Das ausgewaehlte Bild konnte leider nicht hinzugefuegt werden.", AlertType.ERROR);
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
            if (pictureHistoryList.isEmpty()) {
                showAlert("Fehler", "Fehlerhafte Angaben", "Es wurde kein Bild aus ihrem Fototagebuch ausgewählt", AlertType.ERROR);
            } else {
                pictureHistoryService.delete(pictureHistoryList.get(indexOfCurrentPicture));
                reloadImages();
                showAlert("Information", "Foto-Information", "Das Foto wurde erfolgreich aus ihrem Tagebuch gel\u00f6scht.", AlertType.INFORMATION);
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
        }
        selectPictureButton.setDisable(false);

        this.addPictureToDiary();
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
            indexOfCurrentPicture = pictureHistoryList.size()-1;
            if (!pictureHistoryList.isEmpty()) {
                Image image = ImageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
                dateLabel.setText("Foto vom " + pictureHistoryList.get(indexOfCurrentPicture).getDate().toString());
            } else {
                Image standardImage = ImageLoader.loadImage(this.getClass(), "fat_to_muscle.png");
                imageView.setImage(standardImage);
            }
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

    private SequentialTransition createTransition(final ImageView iv, final Image img) {
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), iv);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished(arg0 -> iv.setImage(img));

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), iv);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeOutTransition, fadeInTransition);

        return sequentialTransition;
    }
}
