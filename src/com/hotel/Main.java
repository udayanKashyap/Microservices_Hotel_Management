package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	static final String URL = "jdbc:mysql://localhost:3306/cognizant";
    static final String USER = "root";
    static final String PASS = "root";
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Scanner sc = new Scanner(System.in);
        
        // Object for Classes to be called
        
        while (true) {
            System.out.println("---- Hotel Management System ----");
            System.out.println("--------------------------------");
            System.out.println("Options : ");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("3. Option 3");
            System.out.println("4. Option 4");
            System.out.println("5. Option 5");
            System.out.println("6. Exit");
            System.out.println("--------------------------------");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
         
            switch (choice) 
            {
                case 1:
                    
                    break;
                case 2:
                   
                    break;
                case 3:
                    
                    break;
                case 4:
                   
                    break;
                case 5:
                    
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    con.close();
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }

	}
}
