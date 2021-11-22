package com.example.fitnesstracker.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    Connection con;
    Statement st;
    public DBConnection() {

        //1. Charger le pilote
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452971?serverTimezone=UTC&useLegacyDatetimeCode=false";
            con= DriverManager.getConnection(url,"sql11452971","WAYSFm5dqX");
            st=con.createStatement();
        }
        catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }

    }
    public Connection getCon() {
        return con;
    }
    public void setCon(Connection con) {
        this.con = con;
    }
    public Statement getSt() {
        return st;
    }
    public void setSt(Statement st) {
        this.st = st;
    }
}
