package app.controllers;

import app.helpers.dbConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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

public class updateClassController implements Initializable {

    @FXML
    private TextField tf_ects;

    @FXML
    private Label lab_teacher;

    @FXML
    private ChoiceBox<Integer> drop_teacher;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_semester;

    @FXML
    void updateClass(MouseEvent event){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());
        Connection conn = dbConnect.getInstance().getConnection();
        try {

            String name = tf_name.getText();
            String ects =tf_ects.getText();
            String semestar = tf_semester.getText();
            int teacher_id = drop_teacher.getValue();
                Statement st = conn.createStatement();
                int status = st.executeUpdate("update classes set name='" + name+ "',ects='" + ects + "',semestar='" + semestar + "',teacher_id=" + teacher_id + " where classes.id=" + id + "");
                if (status > 0) {
                    System.out.println("Class updated!");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setValues(MouseEvent event){
        Connection conn=dbConnect.getInstance().getConnection();
        Stage stage = new Stage();
        stage = (Stage) tf_ects.getScene().getWindow();
        int id = Integer.parseInt(stage.getTitle());

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from classes where id="+id+"");
            while(rs.next()){
                tf_name.setText(rs.getString("name"));
                tf_ects.setText(rs.getString("ects"));
                tf_semester.setText(rs.getString("semestar"));
                drop_teacher.setValue(rs.getInt("teacher_id"));
                setLabel();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void setLabel(){
        String name="";
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id="+drop_teacher.getValue()+"");
            while(rs.next()){
                name=rs.getString("firstname")+" "+rs.getString("lastname");
                lab_teacher.setText(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where type_id=1");
            while(rs.next()){
                drop_teacher.getItems().add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
