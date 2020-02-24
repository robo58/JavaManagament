package app.controllers;

import app.helpers.dbConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class newStudentController implements Initializable {
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_index;
    @FXML
    void addStudent(MouseEvent event){
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            String first = tf_firstname.getText();
            String last = tf_lastname.getText();
            String email = tf_email.getText();
            String br_index = tf_index.getText();

            Statement st = conn.createStatement();
            int status = st.executeUpdate("insert into users(firstname,lastname,email,br_indexa,type_id)"+
                    " values('"+first+"','"+last+"','"+email+"','"+br_index+"','"+2+"')");
            if(status>0){
                System.out.println("Student Added!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
