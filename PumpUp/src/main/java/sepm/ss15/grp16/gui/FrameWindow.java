package sepm.ss15.grp16.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import sepm.ss15.grp16.gui.controller.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
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

    private void init(ApplicationContext context, Stage stage, PageEnum mainPage) {
        this.context = context;
        this.stage = stage;

        stage.setScene(new Scene(this));
        stage.setTitle(mainPage.getTitle());
        try {
            String pathToResource = getClass().getClassLoader().getResource("icons").toURI().toString();
            this.stage.getIcons().add(new Image(pathToResource.concat("/logo.png")));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Scene scene = stage.getScene();
        try {
            scene.getStylesheets().add(getClass().getClassLoader().getResource("css").toURI().toString().concat("/mainStyle.css"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initMenu();
        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                deactiveteMenuBar();
            } else {
                activeteMenuBar();
            }
        });

        navigateToChild(mainPage);
    }

    private void initMenu() {
        Menu user = new Menu("Benutzer");
        addNavigationDialogItemToMenu(user, "K\u00f6rperdaten \u00e4ndern", PageEnum.UserEdit);
        addNavigationDialogItemToMenu(user, "Eigene Fotos verwalten", PageEnum.PhotoDiary);
        addItemToMenu(user, "Abmelden", event -> navigateToParent());
        Menu view = new Menu("Ansicht");
        addNavigationItemToMenu(view, "Trainingskalender", PageEnum.Calendar);
        addNavigationItemToMenu(view, "Trainingspl\u00e4ne", PageEnum.Workoutplan);
        addNavigationItemToMenu(view, "\u00dcbungen", PageEnum.Exercises);

        personalMenu = new Menu();

        Menu help = new Menu("Hilfe");
        addNavigationDialogItemToMenu(help, "Impressum", PageEnum.About);

        menuBar = new MenuBar(user, view, help);
    }

    /**
     * Opens a new site in the frame as a child of the actual site.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     *
     * @param mainPage The site to navigate to.
     */
    public void navigateToChild(PageEnum mainPage) {
        try {
            Controller controller = load(mainPage.getFxml());
            controller.setMainFrame(this);
            fxmlStack.push(mainPage);
            controller.setParentController(activeController);
            if (activeController != null) {
                activeController.setChildController(controller); //????
            }
            activeController = controller;
            reloadMenu(mainPage);

            controller.initController();

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
        if (fxmlStack.size() == 1) {
            stage.close();
            activeController = null;
        } else {
            try {
                fxmlStack.pop();
                PageEnum page = fxmlStack.peek();
                Controller controller = load(page.getFxml());
                controller.setMainFrame(this);
                controller.setParentController(activeController.getParentController().getParentController());
                controller.setChildController(activeController);
                activeController.setParentController(controller); ///???
                activeController = controller;
                reloadMenu(page);

                controller.initController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void navigateToMain() {
        while (fxmlStack.size() > 1) {
            navigateToParent();
        }
    }

    /**
     * Opens a new site in a new frame as a child of the actual site and waits while the Dialog is closed.
     * The Controllers get referenced to each other
     * and afterwords initController in the new Controller is is called.
     *
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

    public void addPageManeItem(String titel, EventHandler<ActionEvent> event) {
        if (personalMenu.getItems().size() == 0) {
            menuBar.getMenus().add(2, personalMenu);
        }
        MenuItem item = new MenuItem(titel);
        item.setOnAction(event);
        personalMenu.getItems().add(item);
    }

    public void openFullScreenMode() {
        stage.setFullScreen(true);
    }

    private void activeteMenuBar() {
        setTop(menuBar);
    }

    private void deactiveteMenuBar() {
        setTop(null);
    }

    public void close() {
        stage.close();
    }

    private void addItemToMenu(Menu menu, String titel, EventHandler<ActionEvent> eventHandler) {
        MenuItem item = new MenuItem(titel);
        item.setOnAction(eventHandler);
        menu.getItems().add(item);
    }

    private void addNavigationItemToMenu(Menu menu, String titel, PageEnum page) {
        addItemToMenu(menu, titel, event -> {
            navigateToMain();
            navigateToChild(page);
        });
    }

    private void addNavigationDialogItemToMenu(Menu menu, String titel, PageEnum page) {
        addItemToMenu(menu, titel, event -> {
            navigateToMain();
            openDialog(page);
        });
    }

    private Stage openStage(PageEnum mainPage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setMinWidth(mainPage.getMinWidth());
        dialogStage.setMinHeight(mainPage.getMinHeight());

        return dialogStage;
    }

    private void reloadMenu(PageEnum mainPage) {
        menuBar.getMenus().remove(personalMenu);
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
