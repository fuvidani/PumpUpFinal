package sepm.ss15.grp16.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by lukas on 01.05.2015.
 */
public class Main extends Application{


    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Exercise.fxml"));
        primaryStage.setScene(new Scene(root, 1300, 600));
        primaryStage.show();


    }

    public static void main(String[] args){
        try {
            launch(args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
