package app.controllers;

import app.helpers.dbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class updateTeacherController implements Initializable {

    @FXML
    private AnchorPane rootEl;
    @FXML
    private PasswordField pf_confirm;
    @FXML
    private PasswordField pf_password;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_email;

    @FXML
    void updateTeacher(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());
        Connection conn = dbConnect.getInstance().getConnection();
        try {

            String first = tf_firstname.getText();
            String last = tf_lastname.getText();
            String email = tf_email.getText();
            String pass = pf_password.getText();
            String conf = pf_confirm.getText();
            if(pass.equals(conf)) {
                Statement st = conn.createStatement();
                int status = st.executeUpdate("update users set firstname='" + first + "',lastname='" + last + "',email='" + email + "',password='" + pass + "' where users.id=" + id + "");
                if (status > 0) {
                    System.out.println("Teacher updated!");
                }
            }else{
                System.out.println("Pass and confirm dont match");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setValues(MouseEvent event){
        Connection conn=dbConnect.getInstance().getConnection();
        Stage stage = new Stage();
        stage = (Stage) tf_firstname.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id="+id+"");
            while(rs.next()){
                tf_firstname.setText(rs.getString("firstname"));
                tf_lastname.setText(rs.getString("lastname"));
                tf_email.setText(rs.getString("email"));
                pf_password.setText(rs.getString("password"));
                pf_confirm.setText(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rootEl.removeEventHandler(event.ANY,this::setValues);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
