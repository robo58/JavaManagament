package app.helpers;

import java.sql.*;

public class dbConnect {

    private dbConnect(){

    }
    public static dbConnect getInstance() {
        return  new dbConnect();
    }

    public Connection getConnection(){

        Connection myconn = null;
        try {
           myconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myconn;
    }
}
