package app.helpers;

import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String type;
    private String br_indexa;
    private Button updatebtn,deletebtn;

    public User(int id, String firstname, String lastname, String email, String password,String type) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(int id, String firstname, String lastname, String email, String br_indexa,Button updatebtn,Button deletebtn){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.br_indexa=br_indexa;
        this.updatebtn = updatebtn;
        this.deletebtn = deletebtn;
    }

    public User(int id, String firstname, String lastname, String email,Button updatebtn,Button deletebtn){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.updatebtn=updatebtn;
        this.deletebtn = deletebtn;
    }

    public static ArrayList<User> getUsers(){

        Connection conn=dbConnect.getInstance().getConnection();
        ArrayList<User> users = new ArrayList<User>();

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("select * from users inner join type on users.type_id = type.id");
            while(res.next()){
                users.add(new User(res.getInt("id"),res.getString("firstname"),res.getString("lastname"),res.getString("email"),res.getString("password"),res.getString("type.name")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBr_indexa() {
        return br_indexa;
    }

    public void setBr_indexa(String br_indexa) {
        this.br_indexa = br_indexa;
    }

    public Button getUpdatebtn() {
        return updatebtn;
    }

    public void setUpdatebtn(Button updatebtn) {
        this.updatebtn = updatebtn;
    }

    public Button getDeletebtn() {
        return deletebtn;
    }

    public void setDeletebtn(Button deletebtn) {
        this.deletebtn = deletebtn;
    }
}
