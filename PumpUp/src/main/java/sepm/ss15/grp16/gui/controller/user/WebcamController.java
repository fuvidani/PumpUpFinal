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
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.controller.Controller;

import javax.imageio.ImageIO;
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
                System.out.println("WebCam Index: " + newValue.getWebCamIndex() + ": WebCam Name:" + newValue.getWebCamName());
                initializeWebCam(newValue.getWebCamIndex());
            }
        });
        Platform.runLater(this::setImageViewSize);

    }

    private void setImageViewSize() {

        double height = webCamBorderPane.getHeight();
        double width = webCamBorderPane.getWidth();
        webCamImageView.setFitHeight(height);
        webCamImageView.setFitWidth(width);
        webCamImageView.prefHeight(height);
        webCamImageView.prefWidth(width);
        webCamImageView.setPreserveRatio(true);

    }

    private void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selectedWebcam == null) {
                    selectedWebcam = Webcam.getWebcams().get(webCamIndex);
                    selectedWebcam.open();
                } else {
                    closeCamera();
                    selectedWebcam = Webcam.getWebcams().get(webCamIndex);
                    selectedWebcam.open();
                }
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();
        webCamFooterFlowPane.setDisable(false);
    }

    private void startWebCamStream() {

        stopWebcam = false;
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopWebcam) {
                    try {
                        if ((takenImage = selectedWebcam.getImage()) != null) {

                            Platform.runLater(() -> {
                                final Image mainiamge = SwingFXUtils
                                        .toFXImage(takenImage, null);
                                imageProperty.set(mainiamge);
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
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        webCamImageView.imageProperty().bind(imageProperty);

    }

    private void closeCamera() {
        LOGGER.info("Closing webcam...");
        if (selectedWebcam != null) {
            selectedWebcam.close();
        }
        LOGGER.info("Webcam closed");
    }

    private void stopCamera() {
        LOGGER.info("Stopping webcam...");
        stopWebcam = true;
        LOGGER.info("Webcam stopped");
    }

    private void startCamera() {
        LOGGER.info("Starting webcam...");
        stopWebcam = false;
        startWebCamStream();
        LOGGER.info("Webcam started");
    }

    private void disposeCamera() {
        LOGGER.info("Dispose webcam...");
        stopWebcam = true;
        closeCamera();
        LOGGER.info("Disposed webcam");
    }

    @FXML
    public void takePicture() {
        LOGGER.info("Taking image with webcam...");
        try {
            if (selectedWebcam != null) {
                BufferedImage image = selectedWebcam.getImage();
                ImageIO.write(image, "JPG", new File("webcamaufnahme.jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Image successfully taken");
    }

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
            return "WebCamDetails{" +
                    "webCamName='" + webCamName + '\'' +
                    ", webCamIndex=" + webCamIndex +
                    '}';
        }
    }
}
