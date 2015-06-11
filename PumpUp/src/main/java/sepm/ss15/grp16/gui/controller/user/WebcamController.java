package sepm.ss15.grp16.gui.controller.user;

import com.github.sarxos.webcam.Webcam;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * This class represents the controller for a webcam gui
 *
 * @author Michael Sober
 * @version 1.0
 */
public class WebcamController extends Controller {

    private static final Dimension RESOLUTION = new Dimension(640, 480);
    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    ImageView webCamImageView;
    @FXML
    ImageView bodyOutlineImageView;
    @FXML
    private Label countDownLabel;
    @FXML
    private ToggleButton toggleBodyOutline;
    @FXML
    private ToggleButton toggleTimer;
    @FXML
    private ComboBox<WebCamDetails> cameraComboBox;
    @FXML
    private BorderPane webCamBorderPane;
    @FXML
    private FlowPane webCamFooterFlowPane;
    private BufferedImage takenImage;
    private Webcam selectedWebcam = null;
    private boolean stopWebcam = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private IntegerProperty countDownProperty = new SimpleIntegerProperty();
    private int countDownValue;
    private Timeline timeline;
    private UserService userService;
    private PictureHistoryService pictureHistoryService;
    private PhotoDiaryController photoDiaryController;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @Override
    public void initController() {

        photoDiaryController = (PhotoDiaryController) parentController;

        bodyOutlineImageView.setVisible(false);
        countDownLabel.setVisible(false);
        countDownLabel.textProperty().bind(countDownProperty.asString());
        webCamBorderPane.getScene().getWindow().setOnCloseRequest(e -> WebcamController.this.onCloseAction());
        toggleBodyOutline.setOnAction(event -> {
            if (bodyOutlineImageView.isVisible()) {
                bodyOutlineImageView.setVisible(false);
            } else {
                bodyOutlineImageView.setVisible(true);
            }
        });

        webCamFooterFlowPane.setDisable(true);
        ObservableList<WebCamDetails> webCamOptions = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamDetails webCamDetails = new WebCamDetails(webcam.getName(), webCamCounter);
            webCamOptions.add(webCamDetails);
            webCamCounter++;
        }

        cameraComboBox.setItems(webCamOptions);
        cameraComboBox.setPromptText("Kamera auswählen:");
        cameraComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                startWebCam(newValue.getIndex());
            }
        });
    }

    private void startWebCam(final int webCamIndex) {

        Task<Void> webCamStarter = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selectedWebcam == null) {
                    selectedWebcam = Webcam.getWebcams().get(webCamIndex);
                    selectedWebcam.setViewSize(RESOLUTION);
                    selectedWebcam.open();
                } else {
                    closeWebCam();
                    selectedWebcam = Webcam.getWebcams().get(webCamIndex);
                    selectedWebcam.setViewSize(RESOLUTION);
                    selectedWebcam.open();
                }
                startShowingImages();
                return null;
            }

        };

        new Thread(webCamStarter).start();
    }

    private void startShowingImages() {

        webCamFooterFlowPane.setDisable(false);
        stopWebcam = false;

        Task<Void> showImagesTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopWebcam) {
                    try {
                        takenImage = selectedWebcam.getImage();
                        if (takenImage != null) {

                            Platform.runLater(() -> {
                                final Image mainImage = SwingFXUtils.toFXImage(takenImage, null);
                                imageProperty.set(mainImage);
                            });

                            takenImage.flush();

                        }
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                    }
                }

                return null;
            }

        };
        Thread th = new Thread(showImagesTask);
        th.setDaemon(true);
        th.start();
        webCamImageView.imageProperty().bind(imageProperty);
    }


    private void takePictureWithoutTimer() {
        LOGGER.info("Taking image with webcam...");
        stopWebcam = true;
        webCamFooterFlowPane.setDisable(true);
        try {
            if (selectedWebcam != null) {
                BufferedImage image = selectedWebcam.getImage();

                FileChooser filechooser = new FileChooser();
                FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg", "*.JPEG", "*.jpeg");
                filechooser.getExtensionFilters().add(extFilterJPG);
                filechooser.setTitle("Bild speichern: ");

                File file = filechooser.showSaveDialog(null);
                if (file != null) {
                    ImageIO.write(image, "JPG", file);
                    LOGGER.info("Picture saved in: " + file.getAbsolutePath());

                    PictureHistory pictureHistory = new PictureHistory(null, userService.getLoggedInUser().getUser_id(), file.getAbsolutePath(), null);
                    pictureHistoryService.create(pictureHistory);
                    photoDiaryController.reloadImages();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Foto-Information");
                    alert.setContentText("Das Foto wurde erfolgreich gespeichert und ihrem Fototagebuch hinzugefuegt.");
                    alert.showAndWait();
                }
            }
        } catch (IOException e) {
            LOGGER.error("Saving image from webcam failed");
        } catch (ServiceException e) {
            LOGGER.error("Saving image from webcam in db failed");
        }
        webCamFooterFlowPane.setDisable(false);
        stopWebcam = false;
        countDownLabel.setVisible(false);
        startShowingImages();
        LOGGER.info("Image successfully taken");
    }

    private void takePictureWithCountDown() {
        LOGGER.info("Taking image with webcam after 5s countdown...");
        webCamFooterFlowPane.setDisable(true);
        countDownLabel.setVisible(true);
        countDownValue = 5;
        countDownProperty.set(countDownValue);
        countDownValue--;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> countDownProperty.set(countDownValue--)));
        timeline.setOnFinished(event -> takePictureWithoutTimer());
        timeline.setCycleCount(6);
        timeline.playFromStart();
        LOGGER.info("Image successfully taken after 5s");
    }

    @FXML
    public void takePicture() {
        if (toggleTimer.isSelected()) {
            takePictureWithCountDown();
        } else {
            takePictureWithoutTimer();
        }
    }

    private void closeWebCam() {
        LOGGER.info("Closing webcam...");
        if (selectedWebcam != null) {
            selectedWebcam.close();
        }
        LOGGER.info("Webcam closed");
    }

    private void disposeWebCam() {
        LOGGER.info("Dispose webcam...");
        stopWebcam = true;
        closeWebCam();
        LOGGER.info("Disposed webcam");
    }

    private void destroySelectedWebCam() {
        Task<Void> webCamDestroyer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selectedWebcam != null) {
                    disposeWebCam();
                }
                return null;
            }

        };

        new Thread(webCamDestroyer).start();
    }

    private void onCloseAction() {
        if (timeline != null) {
            timeline.stop();
        }
        destroySelectedWebCam();
    }

    /**
     * This class encapsulates the details of a webcam device
     *
     * @author Michael Sober
     * @version 1.0
     */
    private class WebCamDetails {

        private String name;
        private int index;

        public WebCamDetails(String webCamName, int webCamIndex) {
            this.name = webCamName;
            this.index = webCamIndex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
