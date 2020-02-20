package app;

import app.controllers.MainController;
import app.helpers.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/main.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("Attendance Management System");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
