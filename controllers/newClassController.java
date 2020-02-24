package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import app.helpers.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class newClassController implements Initializable {

    @FXML
    private TextField tf_ects;
    @FXML
    private ChoiceBox<Integer> drop_teacher;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_semester;
    @FXML
    private Label lab_teacher;

    @FXML
    void addClass(ActionEvent event) {
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st2 = conn.createStatement();
            ResultSet rs = st2.executeQuery("select * from users where type_id=1 and");

            Statement st = conn.createStatement();
            int status = st.executeUpdate("insert into classes(name,ects,semestar,teacher_id) values('"+tf_name.getText()+"','"+tf_ects.getText()+"','"+tf_semester.getText()+"',"+drop_teacher.getValue()+")");
            if(status>0){
                System.out.println("Added!");
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
