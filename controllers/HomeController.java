package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    void dashboard(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/Dashboard.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void students(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/Students.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void classes(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/class.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void timetable(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/timetable.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void update(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/Update.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void options(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/Options.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
