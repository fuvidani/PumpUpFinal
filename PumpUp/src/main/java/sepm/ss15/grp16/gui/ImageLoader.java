package sepm.ss15.grp16.gui;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Maximilian on 20.05.2015.
 */
public class ImageLoader {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Loads a Image from the Resources img folder
     *
     * @param name name of the image
     * @return
     */
    public static Image loadImage(Class myClass, String name) throws URISyntaxException {
        String pathToResource = myClass.getClassLoader().getResource("img").toURI().getPath();
        LOGGER.debug("Loading from resources: " + pathToResource);
        String pathOfNewImage = pathToResource + "/" + name;
        LOGGER.debug("Loading image with path: " + pathOfNewImage);
        File picture = new File(pathOfNewImage);
        return new Image(picture.toURI().toString());
    }
}
