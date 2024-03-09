package org.example.jsfprojetbinome.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private final String databaseUser = "chaymae";
    private final String databasePassword = "AVNS_A9geXzOZzob0i9OCz71";

    private final String url = "jdbc:mysql://mysql-337f9109-soukaina-e199.a.aivencloud.com:19094/jsfemployee";
    public Connection conn;

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
