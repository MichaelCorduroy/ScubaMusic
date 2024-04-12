package com.example.scubamusic0_0_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.google.firebase.FirebaseApp;

public class databaseChecker {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Replace these values with your actual database details



        try{
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Replace with your driver class
            System.out.println("this part worked");
        }
        catch (Exception e){
            System.out.println(e);
        }


        // Connect to the database



        // Execute SQL statements (omitted for brevity)

        // Close the connection

    }
}
