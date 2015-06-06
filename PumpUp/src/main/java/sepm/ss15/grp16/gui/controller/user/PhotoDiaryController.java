package sepm.ss15.grp16.gui.controller.user;

import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.main.MainController;

/**
 * Created by michaelsober on 06.06.15.
 */
public class PhotoDiaryController extends Controller {

    private Controller mainController;

    @Override
    public void initController() {
        mainController = (MainController) parentController;
    }
}
