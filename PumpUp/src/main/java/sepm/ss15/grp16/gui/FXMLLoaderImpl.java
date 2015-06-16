package sepm.ss15.grp16.gui;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Author: Lukas
 * Date: 16.06.2015
 */
public class FXMLLoaderImpl extends FXMLLoader {

    @Autowired
    private ApplicationContext applicationContext;

    public FXMLLoaderImpl() {

    }

    public void init() {
        this.setControllerFactory(applicationContext::getBean);
    }

    //@Override
    //public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //   this.applicationContext = applicationContext;
    // }
}
