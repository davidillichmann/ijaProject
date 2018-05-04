package ija.ija2017.controllers;

import ija.ija2017.interfaces.ExecuteControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by davidillichmann on 19.04.18.
 */
public class Main extends Application {

    Stage primaryStage;
    Scene scene1, scene2;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IJA Project");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

//    public void changeWindowTitle(String title) {
//        this.primaryStage.setTitle(this.primaryStage.getTitle() + title);
//    }


}
