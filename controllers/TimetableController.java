package app.controllers;

import app.helpers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
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
import java.time.Year;
import java.util.ResourceBundle;

public class TimetableController implements Initializable {

    @FXML
    private TableView<Timetable> table;
    @FXML
    private TableColumn<Timetable, String> col_hour;
    @FXML
    private TableColumn<Timetable, String> col_mon;
    @FXML
    private TableColumn<Timetable, String> col_tue;
    @FXML
    private TableColumn<Timetable, String>col_wed;
    @FXML
    private TableColumn<Timetable, String> col_thur;
    @FXML
    private TableColumn<Timetable, String> col_fri;

    @FXML
    private ChoiceBox<String> week_drop;

    @FXML
    private ChoiceBox<String> year_drop;



    @FXML
    void home(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
    }

    @FXML
    void setWeek(){
        week_drop.setDisable(false);
        week_drop.getItems().clear();
        String year = (String) year_drop.getValue();
        Connection conn = dbConnect.getInstance().getConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet week = st.executeQuery("select * from week join year on week.year_id = year.id where year.name = '"+year+"'");
            while(week.next()){
                week_drop.getItems().add(week.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ObservableList<Timetable> List = FXCollections.observableArrayList();

    @FXML
    void fillTable(){
        Connection conn = dbConnect.getInstance().getConnection();
        table.getItems().clear();
        try {
            String week = (String) week_drop.getValue();
            String year = (String) year_drop.getValue();
            String[] mon = new String[255];
            String[] tue = new String[255];
            String[] wed = new String[255];
            String[] thur = new String[255];
            String[] fri = new String[255];
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from timetable_classes as tc join week on week.id = tc.week_id join classes as c on c.id = tc.classes_id join year on week.year_id = year.id where week.name = '"+week+"' and year.name = '"+year+"'");
            for(int j=8;j<20;j++){
                mon[j] = " ";
                tue[j] = " ";
                wed[j] = " ";
                thur[j] = " ";
                fri[j] = " ";
            }
                while(rs.next()){
                    switch (rs.getString("day")){
                        case "mon":
                            for(int j=8;j<20;j++) {
                                if (j == rs.getInt("start")) {
                                    for (int i = rs.getInt("start"); i < rs.getInt("end"); i++) {
                                        mon[i] = rs.getString("c.name");
                                        j++;
                                    }
                                }
                            }
                            break;
                        case "tue":
                            for(int j=8;j<20;j++) {
                                if (j == rs.getInt("start")) {
                                    for (int i = rs.getInt("start"); i < rs.getInt("end"); i++) {
                                        tue[i] = rs.getString("c.name");
                                        j++;
                                    }
                                }
                            }
                            break;
                        case "wed":
                            for(int j=8;j<20;j++) {
                                if (j == rs.getInt("start")) {
                                    for (int i = rs.getInt("start"); i < rs.getInt("end"); i++) {
                                        wed[i] = rs.getString("c.name");
                                    }
                                }
                            }
                            break;
                        case "thur":
                            for(int j=8;j<20;j++) {
                                if (j == rs.getInt("start")) {
                                    for (int i = rs.getInt("start"); i < rs.getInt("end"); i++) {
                                        thur[i] = rs.getString("c.name");
                                    }
                                }
                            }
                            break;
                        case "fri":
                            for(int j=8;j<20;j++) {
                                if (j == rs.getInt("start")) {
                                    for (int i = rs.getInt("start"); i < rs.getInt("end"); i++) {
                                        fri[i] = rs.getString("c.name");
                                    }
                                }
                            }
                            break;
                    }
                }
                for (int i = 8; i <= 20; i++) {
                    List.add(new Timetable(i + ":00", mon[i], tue[i], wed[i], thur[i], fri[i]));
                }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_hour.setCellValueFactory(new PropertyValueFactory<>("hour"));
        col_mon.setCellValueFactory(new PropertyValueFactory<>("mon"));
        col_tue.setCellValueFactory(new PropertyValueFactory<>("tue"));
        col_wed.setCellValueFactory(new PropertyValueFactory<>("wed"));
        col_thur.setCellValueFactory(new PropertyValueFactory<>("thur"));
        col_fri.setCellValueFactory(new PropertyValueFactory<>("fri"));

        table.setItems(List);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        try {

            Statement st = conn.createStatement();
            ResultSet years = st.executeQuery("select * from year");
            while(years.next()){
                year_drop.getItems().add(years.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
