package ija.ija2017.controllers;

import ija.ija2017.interfaces.ExecuteControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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
        BorderPane root = new BorderPane();

        try {
            Scene scene = new Scene(root,640,480);
            scene.getStylesheets().add(getClass().getResource("../resources/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
        root.setCenter(new Root());

    }

//    public void changeWindowTitle(String title) {
//        this.primaryStage.setTitle(this.primaryStage.getTitle() + title);
//    }


}
