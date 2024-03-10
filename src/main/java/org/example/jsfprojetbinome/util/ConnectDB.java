package org.example.jsfprojetbinome.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    public Connection conn;
    
    private final String databaseUser = "***";
    private final String databasePassword = "***";
    private final String url = "***";


    public ConnectDB() {
        super();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, databaseUser, databasePassword);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            e.getCause();
        }
        return this.conn;
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
