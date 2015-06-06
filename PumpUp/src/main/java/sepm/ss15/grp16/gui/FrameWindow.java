package sepm.ss15.grp16.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import sepm.ss15.grp16.gui.controller.Controller;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by Maximilian on 21.05.2015.
 */
public class FrameWindow extends BorderPane {

    private ApplicationContext context;
    private Stage stage;

    private Controller activeController = null;
    private Stack<PageEnum> fxmlStack = new Stack<>();

    private MenuBar menuBar;
    private Menu personalMenu;

    public FrameWindow(ApplicationContext context, Stage stage, PageEnum mainPage) {

        init(context, stage, mainPage);
    }

    private FrameWindow(ApplicationContext context, Stage stage, PageEnum mainPage, Controller parent) {

        activeController = parent;
        init(context, stage, mainPage);
    }

    private void init(ApplicationContext context, Stage stage, PageEnum mainPage)
    {
        this.context = context;
        this.stage = stage;

        stage.setScene(new Scene(this));
        stage.setTitle(mainPage.getTitle());

        initMenu();

        navigateToChild(mainPage);
    }

    private void initMenu()
    {
        Menu user = new Menu("User");
        addNavigationItemToMenu(user, "Körperdaten ändern", null);
        addNavigationItemToMenu(user, "Eigene Fotos verwalten", null);
        addItemToMenu(user, "Abmelden", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                navigateToPerent();
            }
        });
        Menu view = new Menu("View");
        addNavigationItemToMenu(view, "Trainingskalender", null);
        addNavigationItemToMenu(view, "Trainingspläne", null);
        addNavigationItemToMenu(view, "Übungen", null);

        personalMenu = new Menu();

        Menu help = new Menu("Help");
        addNavigationItemToMenu(view, "About", null);

        menuBar = new MenuBar(user, view, personalMenu, help);
    }

    /**
     * Opens a new site in the frame as a child of the actual site.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     * @param mainPage The site to navigate to.
     */
    public void navigateToChild(PageEnum mainPage) {
        try {
            Controller controller = load(mainPage.getFxml());
            controller.setMainFrame(this);
            fxmlStack.push(mainPage);
            controller.setParentController(activeController);
            if(activeController != null) {
                activeController.setChildController(controller); //????
            }
            activeController = controller;
            controller.initController();

            reloadMenu(mainPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reopens the parent site in the frame as the actual site.
     * The parent and child Controllers get re-referenced to each other
     * and afterwords initController in the new Controller is is called.
     * If the actual site is the main site of the frame the stage gets closed.
     */
    public void navigateToParent() {
        if(fxmlStack.size() == 1)
        {
            stage.close();
            activeController = null;
        }
        else {
            try {
                fxmlStack.pop();
                PageEnum page = fxmlStack.peek();
                Controller controller = load(page.getFxml());
                controller.setMainFrame(this);
                controller.setParentController(activeController.getParentController().getParentController());
                controller.setChildController(activeController);
                activeController.setParentController(controller); ///???
                activeController = controller;
                controller.initController();

                reloadMenu(page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


<<<<<<< HEAD
    protected void navigateToMain()
    {
        while(fxmlStack.size() > 1)
        {
            navigateToParent();
        }
    }

=======
>>>>>>> LoaderBrunch
    /**
     * Opens a new site in a new frame as a child of the actual site and waits while the Dialog is closed.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     * @param mainPage The site to navigate to.
     */
    public void openDialog(PageEnum mainPage) {
        // Create the dialog Stage.
        Stage dialogStage = openStage(mainPage);
        dialogStage.setResizable(false);
        FrameWindow frame = new FrameWindow(context, dialogStage, mainPage, activeController);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    public void openMainWindowFrame() {
        stage.hide();
        Stage dialogStage = openStage(PageEnum.Main);
        dialogStage.setMaximized(true);
        FrameWindow frame = new FrameWindow(context, dialogStage, PageEnum.Main, activeController);
        frame.activeteMenuBar();

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        stage.show();
    }

    public void addPageManeItem(String titel, EventHandler<ActionEvent> event)
    {
        if(personalMenu.getItems().size() == 0)
        {
            personalMenu.setVisible(true);
        }
        MenuItem item = new MenuItem(titel);
        item.setOnAction(event);
        personalMenu.getItems().add(item);
    }

    private void activeteMenuBar()
    {
        setTop(menuBar);
    }

    private void deactiveteMenuBar()
    {
        setTop(null);
    }

    private void addItemToMenu(Menu menu, String titel, EventHandler<ActionEvent> eventHandler)
    {
        MenuItem item = new MenuItem(titel);
        item.setOnAction(eventHandler);
        menu.getItems().add(item);
    }

    public void addNavigationItemToMenu(Menu menu, String titel, PageEnum page)
    {
        addItemToMenu(menu, titel, event -> {
            navigateToMain();
            navigateToChild(page);
        });
    }

    private void navigateToMain()
    {
        while(fxmlStack.size() > 1)
        {
            navigateToPerent();
        }
    }

    private Stage openStage(PageEnum mainPage)
    {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setMinWidth(mainPage.getMinWidth());
        dialogStage.setMinHeight(mainPage.getMinHeight());

        return stage;
    }

    private void reloadMenu(PageEnum mainPage) {
        personalMenu.setVisible(false);
        personalMenu.setText(mainPage.getTitle());
        personalMenu.getItems().clear();
    }

    private Controller load(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(this.getClass().getClassLoader().getResource(fxml));
        setCenter(loader.load());
        return loader.getController();
    }
}
