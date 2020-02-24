package app.controllers;

import  app.helpers.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label studentCount;
    @FXML
    private Label hourCount;
    @FXML
    private Label classC;
    @FXML
    private TableColumn<Attendance, String> col_class;
    @FXML
    private TableColumn<Attendance, Button> col_btn;
    @FXML
    private TableColumn<Attendance, String> col_teacher;
    @FXML
    private TableColumn<Attendance, String> col_hour;
    @FXML
    private TableColumn<Attendance, String> col_ywd;
    @FXML
    private TableColumn<Attendance, String> col_tcid;
    @FXML
    private TableView<Attendance> table;
    @FXML
    private TableView<StudentList> stTable;
    @FXML
    private TableColumn<Attendance, String> col_attended;
    @FXML
    private TableColumn<StudentList, String> col_indexnum;
    @FXML
    private TableColumn<StudentList, String> col_studentName;
    @FXML
    private TableColumn<StudentList, Button> col_attBtn;
    @FXML
    private TableColumn<StudentList, String> col_att;
    ObservableList<Attendance> oblist = FXCollections.observableArrayList();
    ObservableList<StudentList> stlist = FXCollections.observableArrayList();

    @FXML
    void home(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/Home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
    }

    @FXML
    void listStudents(int tcid){
        stTable.getItems().clear();
        Connection conn = dbConnect.getInstance().getConnection();
        int broj=0;
        int i=0;
        try {
            int atbr=0;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where type_id = 2");
            Statement st2 = conn.createStatement();
            ResultSet br = st2.executeQuery("select count(*) as count from users where type_id=2");
            Statement br2 = conn.createStatement();
            ResultSet br22 = br2.executeQuery("select count(*) as count from attendance");
            while(br22.next()){
                atbr=br22.getInt("count");
            }
            while(br.next()){
                broj=br.getInt("count");
            }
            String[] studentName = new String[broj];
            String[] indexNum = new String[broj];
            boolean[] att = new boolean[broj];
            Button[] btn = new Button[broj];
            Statement st3 = conn.createStatement();
            while(rs.next()){
                int finalID=rs.getInt("id");
                int finaltcID=tcid;
                studentName[i]= rs.getString("firstname")+" "+rs.getString("lastname");
                indexNum[i]=rs.getString("br_indexa");
                ResultSet rs3 = st3.executeQuery("select * from attendance as a join users on a.student_id=users.id where users.br_indexa='"+indexNum[i]+"' and a.timetable_classes_id = '"+tcid+"'");
                while(rs3.next()){
                    if(att[i]=rs3.getBoolean("attended")){
                        att[i]=rs3.getBoolean("attended");
                    }else{
                        att[i]=false;
                    }
                }
                if(att[i]) {
                    btn[i] = new Button("Not here");
                    btn[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                Statement t = conn.createStatement();
                                int status = t.executeUpdate("update attendance as a set attended=0 where a.student_id='"+finalID+"' and a.timetable_classes_id='"+finaltcID+"'");
                                if(status>0){
                                    System.out.println("Updated");
                                    refresh();
                                    listStudents(finaltcID);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }else{
                    btn[i] = new Button("Here");
                    btn[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                            try {
                                Statement s = conn.createStatement();
                                ResultSet r = s.executeQuery("select * from attendance as a where a.student_id='"+finalID+"' and a.timetable_classes_id='"+finaltcID+"'");
                                int i=0;
                                while(r.next()){
                                    i++;
                                }
                                if(i>0) {
                                    Statement t = conn.createStatement();
                                    int status = t.executeUpdate("update attendance as a set attended=1 where a.student_id='" + finalID + "' and a.timetable_classes_id='" + finaltcID + "'");
                                    if (status > 0) {
                                        System.out.println("Updated");
                                        refresh();
                                        listStudents(finaltcID);
                                    }
                                }else{
                                    Statement t1 = conn.createStatement();
                                    int status = t1.executeUpdate("insert into attendance(timetable_classes_id,student_id,attended)" +
                                            " values('"+finaltcID+"','"+finalID+"','"+1+"')");
                                    if(status>0){
                                        System.out.println("Inserted and updated");
                                        refresh();
                                        listStudents(finaltcID);
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }


                        }
                    });

                }
                i++;
            }
            for(i=0;i<broj;i++){
                stlist.add(new StudentList(studentName[i],indexNum[i],att[i],btn[i]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        col_indexnum.setCellValueFactory(new PropertyValueFactory<>("indexNum"));
        col_att.setCellValueFactory(new PropertyValueFactory<>("attended"));
        col_attBtn.setCellValueFactory(new PropertyValueFactory<>("btn"));
        stTable.setItems(stlist);
    }

    public void refresh(){
        Connection conn = dbConnect.getInstance().getConnection();
        table.getItems().clear();
        int start,end;
        int h = 0;
        int i=0,j=0;
        int total=0;
        int k=0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) as count from users where type_id = 2");
            while(rs.next()){
                studentCount.setText(rs.getString("count"));
            }
            ResultSet hours = st.executeQuery("select * from timetable_classes");
            while(hours.next()){
                start = hours.getInt("start");
                end = hours.getInt("end");
                h += (end-start);
            }
            String hh = String.valueOf(h);
            hourCount.setText(hh);
            ResultSet classCount = st.executeQuery("select count(*) as count from timetable_classes");
            while(classCount.next()){
                total = classCount.getInt("count");
                classC.setText(classCount.getString("count"));
            }
            String[] teacherName = new String[total];
            String[] className = new String[total];
            String[] hour = new String[total];
            String[] attended = new String[total];
            String[] ywd = new String[total];
            Button[] btn = new Button[total];
            int[] tcid = new int[total];
            String stCount="";
            ResultSet studentCount = st.executeQuery("select count(*) as count from users where type_id = 2");
            while(studentCount.next()){
                stCount = studentCount.getString("count");
            }
            Statement st2 = conn.createStatement();
            ResultSet r = st.executeQuery("select * from timetable_classes as tc join classes as c on tc.classes_id=c.id join users on users.id=c.teacher_id" +
                    " join week on tc.week_id=week.id join year on week.year_id=year.id");
            while(r.next()){
                tcid[k] = r.getInt("tc.id");

                teacherName[k]=r.getString("users.firstname")+" "+r.getString("users.lastname");
                className[k]=r.getString("c.name");
                hour[k] = r.getString("tc.start")+":00/"+r.getString("tc.end")+":00";
                ywd[k] = r.getString("year.name") +"/"+ r.getString("week.name") +"/"+ r.getString("tc.day");
                ResultSet at = st2.executeQuery("select count(*) as count from attendance where timetable_classes_id = '"+r.getInt("tc.id")+"' and attended=1");
                if(at.next()){
                    attended[k]=at.getString("count") + "/"+stCount;
                }
                btn[k] = new Button("List Students");
                int finalK = k;
                btn[k].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        listStudents(tcid[finalK]);
                    }
                });
                k++;
            }
            for(k=0;k<total;k++){
                oblist.add(new Attendance(tcid[k],teacherName[k],className[k],hour[k],attended[k],ywd[k],btn[k]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_tcid.setCellValueFactory(new PropertyValueFactory<>("tcid"));
        col_teacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        col_class.setCellValueFactory(new PropertyValueFactory<>("className"));
        col_hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        col_attended.setCellValueFactory(new PropertyValueFactory<>("attended"));
        col_ywd.setCellValueFactory(new PropertyValueFactory<>("ywd"));
        col_btn.setCellValueFactory(new PropertyValueFactory<>("showStudents"));
        table.setItems(oblist);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        int start,end;
        int h = 0;
        int i=0,j=0;
        int total=0;
        int k=0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) as count from users where type_id = 2");
            while(rs.next()){
                studentCount.setText(rs.getString("count"));
            }
            ResultSet hours = st.executeQuery("select * from timetable_classes");
            while(hours.next()){
                start = hours.getInt("start");
                end = hours.getInt("end");
                h += (end-start);
            }
            String hh = String.valueOf(h);
            hourCount.setText(hh);
            ResultSet classCount = st.executeQuery("select count(*) as count from timetable_classes");
            while(classCount.next()){
                total = classCount.getInt("count");
                    classC.setText(classCount.getString("count"));
            }
            String[] teacherName = new String[total];
            String[] className = new String[total];
            String[] hour = new String[total];
            String[] attended = new String[total];
            String[] ywd = new String[total];
            Button[] btn = new Button[total];
            int[] tcid = new int[total];
            String stCount="";
            ResultSet studentCount = st.executeQuery("select count(*) as count from users where type_id = 2");
            while(studentCount.next()){
                stCount = studentCount.getString("count");
            }
            Statement st2 = conn.createStatement();
            ResultSet r = st.executeQuery("select * from timetable_classes as tc join classes as c on tc.classes_id=c.id join users on users.id=c.teacher_id" +
                    " join week on tc.week_id=week.id join year on week.year_id=year.id");
            while(r.next()){
                tcid[k] = r.getInt("tc.id");

                teacherName[k]=r.getString("users.firstname")+" "+r.getString("users.lastname");
                className[k]=r.getString("c.name");
                hour[k] = r.getString("tc.start")+":00/"+r.getString("tc.end")+":00";
                    ywd[k] = r.getString("year.name") +"/"+ r.getString("week.name") +"/"+ r.getString("tc.day");
                ResultSet at = st2.executeQuery("select count(*) as count from attendance where timetable_classes_id = '"+r.getInt("tc.id")+"' and attended=1");
                if(at.next()){
                    attended[k]=at.getString("count") + "/"+stCount;
                }
                btn[k] = new Button("List Students");
                int finalK = k;
                btn[k].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        listStudents(tcid[finalK]);
                    }
                });
            k++;
        }
        for(k=0;k<total;k++){
            oblist.add(new Attendance(tcid[k],teacherName[k],className[k],hour[k],attended[k],ywd[k],btn[k]));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_tcid.setCellValueFactory(new PropertyValueFactory<>("tcid"));
        col_teacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        col_class.setCellValueFactory(new PropertyValueFactory<>("className"));
        col_hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        col_attended.setCellValueFactory(new PropertyValueFactory<>("attended"));
        col_ywd.setCellValueFactory(new PropertyValueFactory<>("ywd"));
        col_btn.setCellValueFactory(new PropertyValueFactory<>("showStudents"));
        table.setItems(oblist);
    }
}
