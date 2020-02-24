package app.controllers;

import app.helpers.dbConnect;
import app.helpers.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField tf_mail;

    @FXML
    private PasswordField pf_pass;


    @FXML
    void login(MouseEvent event){
        String email = tf_mail.getText();
        String password = pf_pass.getText();

        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("select * from users where email" + " = '"+email+"'");
            if(result.next()) {
                if (result.getString("password").equals(password)) {
                    System.out.println("Login Successfull");
                    Parent home = FXMLLoader.load(getClass().getResource("/app/views/home.fxml"));
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setScene(new Scene(home));
                }else{
                    System.out.println("Incorrect password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
