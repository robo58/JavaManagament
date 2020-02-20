package app.controllers;

import app.helpers.dbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @FXML
    private TextField tf_email;

    @FXML
    private PasswordField pf_password;

    @FXML
    private PasswordField pf_confirm;

    @FXML
    void login(MouseEvent event){

    }

    @FXML
    void register(MouseEvent event){
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            String first = tf_firstname.getText();
            String last = tf_lastname.getText();
            String email = tf_email.getText();
            String pass = pf_password.getText();
            String conf = pf_confirm.getText();
            if(pass.equals(conf)) {
                Statement st = conn.createStatement();
                int status = st.executeUpdate("insert into users(firstname,lastname,email,password)"+
                        " values('"+first+"','"+last+"','"+email+"','"+pass+"')");
                if(status>0){
                    System.out.println("Registered!");
                }
            }else{
                System.out.println("Password and confirm dont match");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
