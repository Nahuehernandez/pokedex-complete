package com.example.DB;

import java.sql.*;

public class ConnectionDb {
    private Connection connection;
    private Statement statement;

    public void getConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokedex", "root", "certantsa");
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
