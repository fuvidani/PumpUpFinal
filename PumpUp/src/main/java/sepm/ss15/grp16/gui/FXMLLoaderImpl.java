package sepm.ss15.grp16.gui;

import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Author: Lukas
 * Date: 16.06.2015
 */
public class FXMLLoaderImpl extends FXMLLoader {
    private static final Logger LOGGER = LogManager.getLogger(FXMLLoaderImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    public FXMLLoaderImpl() {
    }

    public void init() {
        this.setControllerFactory(applicationContext::getBean);
        LOGGER.info("FXMLLoader configuration successful");
    }
}
