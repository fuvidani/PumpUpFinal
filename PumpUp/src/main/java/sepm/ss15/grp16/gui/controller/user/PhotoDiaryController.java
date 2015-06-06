package sepm.ss15.grp16.gui.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.ImageLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.main.MainController;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.user.PictureHistoryService;
import sepm.ss15.grp16.service.user.UserService;

import java.io.File;
import java.util.List;

/**
 * Created by michaelsober on 06.06.15.
 */
public class PhotoDiaryController extends Controller {

    @FXML
    ImageView imageView;

    @FXML
    Button selectPictureButton;

    private Controller mainController;
    private UserService userService;
    private PictureHistoryService pictureHistoryService;
    private List<PictureHistory> pictureHistoryList;
    private int indexOfCurrentPicture;
    private String selectedPicturePath;
    private static final Logger LOGGER = LogManager.getLogger();

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
            if(!pictureHistoryList.isEmpty()) {
                ImageLoader imageLoader = new ImageLoader();
                Image image = imageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    public void forwardButtonClicked(){
        try {
            if (indexOfCurrentPicture < pictureHistoryList.size()) {
                indexOfCurrentPicture++;
                ImageLoader imageLoader = new ImageLoader();
                Image image = imageLoader.loadImage(this.getClass(), pictureHistoryList.get(indexOfCurrentPicture).getLocation());
                imageView.setImage(image);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void backwardButtonClicked(){

    }

    @FXML
    public void addButtonClicked(){
        
    }

    @FXML
    public void deleteButtonClicked(){

    }

    @FXML
    public void selectPictureButtonClicked(){
        LOGGER.debug("Choose picture clicked");
        selectPictureButton.setDisable(true);

        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg", "*.JPEG", "*.jpeg");
        filechooser.getExtensionFilters().add(extFilterJPG);
        filechooser.setTitle("Bild auswählen: ");
        File selectedFile = filechooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedPicturePath = selectedFile.getPath();
            Image image = new Image(selectedFile.toURI().toString(), imageView.getFitWidth(), imageView.getFitHeight(), false, false);
            imageView.setImage(image);
        }
        selectPictureButton.setDisable(false);
    }

    @FXML
    public void webCamButtonClicked(){

    }


}
