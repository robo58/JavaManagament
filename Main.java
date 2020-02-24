package app;

import app.helpers.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("Attendance Management System");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        ArrayList<User> users=User.getUsers();

        launch(args);

    }
}
