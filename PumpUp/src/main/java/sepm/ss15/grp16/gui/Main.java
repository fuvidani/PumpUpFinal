package main.java.sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sepm.ss15.grp16.gui.controller.ExerciseController;
import sepm.ss15.grp16.persistence.ExerciseDAO;
import sepm.ss15.grp16.persistence.h2.H2ExerciseDAOImpl;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;

/**
 * Created by lukas on 01.05.2015.
 */
public class Main extends Application{


    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Exercise.fxml"));
        primaryStage.setScene(new Scene(root, 1300, 600));
        primaryStage.show();
        primaryStage.setMaximized(true);


    }

    public static void main(String[] args){
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
