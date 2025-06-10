package com.hotel;

import java.sql.*;
import java.util.Scanner;

public class Database {
    public void testDatabase(Connection conn) throws SQLException{
        String testQuery = "SHOW TABLES";
        Statement statement =conn.createStatement();
        ResultSet results =statement.executeQuery(testQuery);

        System.out.println("--- List of All Tables ---");
        while (results.next()) {
            // column 1 always holds the table name
            System.out.println("Table: " + results.getString(1));
        }
    }
}
