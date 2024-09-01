package br.com.jdbc.studies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    public static void main(String[] args) {

        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");


        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cash_bank", username, password);
            System.out.println("Connected");

            connection.close();
        }catch(SQLException e){
            System.out.println("Connection Failed!" + e.getMessage());
        }

    }
}
