package sepm.ss15.grp16.gui.controller.user;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.controller.Controller;

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

    private static final Dimension RESOLUTION = new Dimension(640,480);
    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    ComboBox<WebCamDetails> cameraComboBox;
    @FXML
    BorderPane webCamBorderPane;
    @FXML
    FlowPane webCamFooterFlowPane;
    @FXML
    ImageView webCamImageView;
    private BufferedImage takenImage;
    private Webcam selectedWebcam = null;
    private boolean stopWebcam = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    @Override
    public void initController() {

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
                initializeWebCam(newValue.getWebCamIndex());
            }
        });
    }

    private void initializeWebCam(final int webCamIndex){

        Task<Void> webCamInitializer = new Task<Void>() {

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

        new Thread(webCamInitializer).start();
        webCamFooterFlowPane.setDisable(false);
    }

    private void destroySelectedWebCam(){
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

    private void startShowingImages() {

        stopWebcam = false;
        Task<Void> showImagesTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopWebcam) {
                    try {
                        if ((takenImage = selectedWebcam.getImage()) != null) {

                            Platform.runLater(() -> {
                                final Image mainImage = SwingFXUtils.toFXImage(takenImage, null);
                                imageProperty.set(mainImage);
                            });

                            takenImage.flush();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    @FXML
    public void takePicture() {
        LOGGER.info("Taking image with webcam...");
        try {
            if (selectedWebcam != null) {
                BufferedImage image = selectedWebcam.getImage();
                ImageIO.write(image, "JPG", new File("webcamaufnahme.jpg"));
                destroySelectedWebCam();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Foto-Information");
                alert.setContentText("Das Foto wurde erfolgreich gespeichert.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Image successfully taken");
    }

    /**
     * This class encapsulates the details of a webcam device
     *
     * @author Michael Sober
     * @version 1.0
     */
    private class WebCamDetails {

        private String webCamName;
        private int webCamIndex;

        public WebCamDetails(String webCamName, int webCamIndex) {
            this.webCamName = webCamName;
            this.webCamIndex = webCamIndex;
        }

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }
}
