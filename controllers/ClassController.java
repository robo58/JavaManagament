package app.controllers;

import app.helpers.User;
import app.helpers.Class;
import app.helpers.dbConnect;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ClassController implements Initializable {

    @FXML
    private TableView<Class> tableView;
    @FXML
    private TableColumn<Class, Integer> col_id;
    @FXML
    private TableColumn<Class, String> col_name;
    @FXML
    private TableColumn<Class, String> col_ects;
    @FXML
    private TableColumn<Class, String> col_semester;
    @FXML
    private TableColumn<Class, Button> col_delbtn;
    @FXML
    private TableColumn<Class, Button> col_updatebtn;



    @FXML
    void home(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
    }

    ObservableList<Class> oblist = FXCollections.observableArrayList();

    @FXML
    void addClass(MouseEvent event) throws IOException {
        Parent log = FXMLLoader.load(getClass().getResource("/app/views/addClass.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add new Class");
        stage.setScene(new Scene(log));
        stage.initModality(Modality.APPLICATION_MODAL);
        Node node = (Node) event.getSource();
        Stage owner = (Stage) node.getScene().getWindow();
        stage.initOwner(owner);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        Statement st = null;
        int i=0;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) as count from classes");
            int count=0;
            while(rs.next()){
                count = rs.getInt("count");
            }
            Button[] delbtn = new Button[count];
            Button[] updatebtn = new Button[count];
            ResultSet rs2 = st.executeQuery("select * from classes");

            while(rs2.next()){
                int classID=rs2.getInt("id");
                updatebtn[i]=new Button("Update");
                updatebtn[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Parent log = null;
                        try {
                            log = FXMLLoader.load(getClass().getResource("/app/views/updateClass.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage stage = new Stage();
                        stage.setTitle(classID+"");
                        stage.setScene(new Scene(log));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        Node node = (Node) event.getSource();
                        Stage owner = (Stage) node.getScene().getWindow();
                        stage.initOwner(owner);
                        stage.show();
                    }
                });

                delbtn[i] = new Button("Delete");
                delbtn[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Statement st = conn.createStatement();
                            int status = st.executeUpdate("delete from classes where id="+classID+"");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                oblist.add(new Class(rs2.getInt("id"),rs2.getString("name"),rs2.getInt("ects"),rs2.getInt("semestar"),updatebtn[i],delbtn[i]));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_ects.setCellValueFactory(new PropertyValueFactory<>("ects"));
        col_semester.setCellValueFactory(new PropertyValueFactory<>("semestar"));
        col_updatebtn.setCellValueFactory(new PropertyValueFactory<>("updatebtn"));
        col_delbtn.setCellValueFactory(new PropertyValueFactory<>("delbtn"));
        tableView.setItems(oblist);
    }
}
