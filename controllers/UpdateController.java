package app.controllers;

import app.helpers.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    private ChoiceBox<String> drop_day;
    @FXML
    private ChoiceBox<String> drop_class;
    @FXML
    private ChoiceBox<String> drop_week;
    @FXML
    private ChoiceBox<Integer> drop_starts;
    @FXML
    private ChoiceBox<Integer> drop_ends;
    @FXML
    private ChoiceBox<String> drop_year;
    @FXML
    private Button btn_update;
    @FXML
    private ChoiceBox<String> drop_day1;
    @FXML
    private ChoiceBox<String> drop_class1;
    @FXML
    private ChoiceBox<String> drop_week1;
    @FXML
    private ChoiceBox<Integer> drop_starts1;
    @FXML
    private ChoiceBox<Integer> drop_ends1;
    @FXML
    private ChoiceBox<String> drop_year1;
    @FXML
    private Button btn_update1;
    @FXML
    private Button btn_delete;
    @FXML
    private ChoiceBox<Integer> drop_timetable;

    @FXML
    void deleteTimetable(){
        int tcid =(int) drop_timetable.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            int status = st.executeUpdate("delete from timetable_classes where id="+tcid+"");
            if(status>0){
                System.out.println("deleted");
                drop_year1.getItems().clear();
                drop_week1.getItems().clear();
                drop_day1.getItems().clear();
                drop_starts1.getItems().clear();
                drop_ends1.getItems().clear();
                btn_delete.setDisable(true);
                btn_update1.setDisable(true);
                drop_timetable.getItems().clear();
                ResultSet tt = st2.executeQuery("select * from timetable_classes");
                while(tt.next()){
                    drop_timetable.getItems().add(tt.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addTimetable(MouseEvent event) {
        String day = (String) drop_day.getValue();
        int week_id = 0;
        int classes_id = 0;
        int start = (int) drop_starts.getValue();
        int end = (int) drop_ends.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs_class = st.executeQuery("select * from classes where name = '"+(String)drop_class.getValue()+"'");
            while(rs_class.next()){
                classes_id = rs_class.getInt("id");
            }
            ResultSet rs_week = st.executeQuery("select * from week where name = '"+(String)drop_week.getValue()+"'");
            while(rs_week.next()){
                week_id = rs_week.getInt("id");
            }
            int status = st.executeUpdate("insert into timetable_classes(day,week_id,classes_id,start,end) " +
                    "values('"+day+"','"+week_id+"','"+classes_id+"','"+start+"','"+end+"')");
            if(status>0){
                System.out.println("Updated!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chooseTT(){
        int id = drop_timetable.getValue();
        drop_year1.setDisable(false);
        btn_update1.setDisable(false);
        btn_delete.setDisable(false);
        drop_year1.getItems().clear();
        Connection conn = dbConnect.getInstance().getConnection();
        String day = null,week = null,classes = null,year = null;
        int start = 0,end = 0;

        try {
            Statement st = conn.createStatement();
            ResultSet years = st.executeQuery("select * from year");
            while(years.next()){
                drop_year1.getItems().add(years.getString("name"));
            }
        Statement st1 = conn.createStatement();
            ResultSet rs = st1.executeQuery("select * from timetable_classes as tc join week on week.id = tc.week_id join classes on classes.id=tc.classes_id join year on year.id = week.year_id where tc.id='"+id+"'");
            while(rs.next()){
                day = rs.getString("tc.day");
                year = rs.getString("year.name");
                week=rs.getString("week.name");
                classes = rs.getString("classes.name");
                start = rs.getInt("tc.start");
                end = rs.getInt("tc.end");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        drop_year1.setValue(year);
        Weekset1();
        drop_week1.setValue(week);
        Dayset1();
        drop_day1.setValue(day);
        ClassSet1();
        drop_class1.setValue(classes);
        hourStartSet1();
        drop_starts1.setValue(start);
        hourEndSet1();
        drop_ends1.setValue(end);
    }

    @FXML
    void updateTimetable(MouseEvent event) {
        int tcid = (int) drop_timetable.getValue();
        String day = (String) drop_day1.getValue();
        int week_id = 0;
        int classes_id = 0;
        int start = (int) drop_starts1.getValue();
        int end = (int) drop_ends1.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs_class = st.executeQuery("select * from classes where name = '"+(String)drop_class1.getValue()+"'");
            while(rs_class.next()){
                classes_id = rs_class.getInt("id");
            }
            ResultSet rs_week = st.executeQuery("select * from week where name = '"+(String)drop_week1.getValue()+"'");
            while(rs_week.next()){
                week_id = rs_week.getInt("id");
            }
            int status = st.executeUpdate("update timetable_classes set day='"+day+"', week_id='"+week_id+"', classes_id='"+classes_id+"', start="+start+", end="+end+" where timetable_classes.id="+tcid+"");
            if(status>0){
                System.out.println("Updated!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void home(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void Weekset(){
        drop_week.setDisable(false);
        drop_week.getItems().clear();
        String year = (String) drop_year.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet week = st.executeQuery("select * from week join year on week.year_id = year.id where year.name = '"+year+"'");
            while(week.next()){
                drop_week.getItems().add(week.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Weekset1(){
        drop_week1.setDisable(false);
        drop_week1.getItems().clear();
        String year = (String) drop_year1.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet week = st.executeQuery("select * from week join year on week.year_id = year.id where year.name = '"+year+"'");
            while(week.next()){
                drop_week1.getItems().add(week.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Dayset(){
        drop_day.setDisable(false);
        drop_day.getItems().clear();
        String[] days = new String[5];
        days[0]="mon";
        days[1]="tue";
        days[2]="wed";
        days[3]="thur";
        days[4]="fri";
        for(int i=0;i<5;i++){
            drop_day.getItems().add(days[i]);
        }
    }
    @FXML
    void Dayset1(){
        drop_day1.setDisable(false);
        drop_day1.getItems().clear();
        String[] days = new String[5];
        days[0]="mon";
        days[1]="tue";
        days[2]="wed";
        days[3]="thur";
        days[4]="fri";
        for(int i=0;i<5;i++){
            drop_day1.getItems().add(days[i]);
        }
    }

    @FXML
    void ClassSet(){
        drop_class.setDisable(false);
        drop_class.getItems().clear();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from classes");
            while(rs.next()){
                drop_class.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ClassSet1(){
        drop_class1.setDisable(false);
        drop_class1.getItems().clear();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from classes");
            while(rs.next()){
                drop_class1.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void hourStartSet(){
        drop_starts.setDisable(false);
        drop_starts.getItems().clear();
        for(int i=8;i<20;i++){
            drop_starts.getItems().add(i);
        }

    }

    @FXML
    void hourStartSet1(){
        drop_starts1.setDisable(false);
        drop_starts1.getItems().clear();
        for(int i=8;i<20;i++){
            drop_starts1.getItems().add(i);
        }

    }

    @FXML
    void hourEndSet(){
        drop_ends.setDisable(false);
        drop_ends.getItems().clear();
        int starts = (int)drop_starts.getValue();
        for(int i=starts;i<20;i++){
            drop_ends.getItems().add(i);
        }

    }

    @FXML
    void hourEndSet1(){
        drop_ends1.setDisable(false);
        drop_ends1.getItems().clear();
        int starts = (int)drop_starts1.getValue();
        for(int i=starts;i<20;i++){
            drop_ends1.getItems().add(i);
        }

    }

    @FXML
    void btnEnable() {
        btn_update.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet years = st.executeQuery("select * from year");
            while(years.next()){
                drop_year.getItems().add(years.getString("name"));
            }
            ResultSet tt = st.executeQuery("select * from timetable_classes");
            while(tt.next()){
                drop_timetable.getItems().add(tt.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
