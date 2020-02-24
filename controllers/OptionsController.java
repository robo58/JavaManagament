package app.controllers;

import app.helpers.User;
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

public class OptionsController implements Initializable {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> col_id;
    @FXML
    private TableColumn<User, String> col_firstname;
    @FXML
    private TableColumn<User, String> col_lastname;
    @FXML
    private TableColumn<User, String> col_email;
    @FXML
    private TableColumn<User, Button> col_delbtn;
    @FXML
    private TableColumn<User, Button> col_updatebtn;

    @FXML
    void addNewTeacher(MouseEvent event) throws IOException {
        Parent log = FXMLLoader.load(getClass().getResource("/app/views/register.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add new Teacher");
        stage.setScene(new Scene(log));
        stage.initModality(Modality.APPLICATION_MODAL);
        Node node = (Node) event.getSource();
        Stage owner = (Stage) node.getScene().getWindow();
        stage.initOwner(owner);
        stage.showAndWait();
    }

    @FXML
    void home(MouseEvent event) throws IOException {
        Parent reg = FXMLLoader.load(getClass().getResource("/app/views/home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(reg));
    }

    ObservableList<User> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = dbConnect.getInstance().getConnection();
        Statement st = null;

        try {
            int count=0;
            st = conn.createStatement();
            ResultSet r = st.executeQuery("select count(*) as count from users where type_id=1");
            while(r.next()){
                count=r.getInt("count");
            }
            Button[] updatebtn = new  Button[count];
            Button[] delbtn = new  Button[count];
            int k=0;
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users where type_id=1");
            while(rs.next()){
                int id = rs.getInt("id");
                updatebtn[k]=new Button("Update");
                updatebtn[k].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Parent log = null;
                        try {
                            log = FXMLLoader.load(getClass().getResource("/app/views/updateTeacher.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Stage stage = new Stage();
                        stage.setTitle(id+"");
                        stage.setScene(new Scene(log));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        Node node = (Node) event.getSource();
                        Stage owner = (Stage) node.getScene().getWindow();
                        stage.initOwner(owner);
                        stage.show();
                    }
                });

                delbtn[k]=new Button("Delete");
                        delbtn[k].setOnAction(new EventHandler<ActionEvent>() {
                                                  @Override
                                                  public void handle(ActionEvent actionEvent) {
                                                      try {
                                                          Statement st = conn.createStatement();
                                                          int status = st.executeUpdate("delete from users where id="+id+"");
                                                      } catch (SQLException e) {
                                                          e.printStackTrace();
                                                      }

                                                  }
                                                });

                oblist.add(new User(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),updatebtn[k],delbtn[k]));
                k++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_updatebtn.setCellValueFactory(new PropertyValueFactory<>("updatebtn"));
        col_delbtn.setCellValueFactory(new PropertyValueFactory<>("deletebtn"));


        tableView.setItems(oblist);
    }
}
