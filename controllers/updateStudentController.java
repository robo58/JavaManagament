package app.controllers;

import app.helpers.dbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class updateStudentController implements Initializable {

    @FXML
    private TextField tf_firstname1;
    @FXML
    private TextField tf_lastname1;
    @FXML
    private TextField tf_email1;
    @FXML
    private TextField tf_index1;
    @FXML
    private AnchorPane rootEl;
    @FXML
    void updateStudent(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());
        Connection conn = dbConnect.getInstance().getConnection();
        try {

            String first = tf_firstname1.getText();
            String last = tf_lastname1.getText();
            String email = tf_email1.getText();
            String br_index = tf_index1.getText();

            Statement st = conn.createStatement();
            int status = st.executeUpdate("update users set firstname='"+first+"',lastname='"+last+"',email='"+email+"',br_indexa='"+br_index+"' where users.id="+id+"");
            if(status>0){
                System.out.println("Student updated!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setValues(MouseEvent event){
        Connection conn=dbConnect.getInstance().getConnection();
        Stage stage = new Stage();
        stage = (Stage) tf_firstname1.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id="+id+"");
            while(rs.next()){
                tf_firstname1.setText(rs.getString("firstname"));
                tf_lastname1.setText(rs.getString("lastname"));
                tf_email1.setText(rs.getString("email"));
                tf_index1.setText(rs.getString("br_indexa"));
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
