package com.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Guests {
    private Connection con;
    private Scanner sc;

    public Guests(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void registerGuest() {
        while (true) {
            try {
                System.out.print("Enter name: ");
                String name = sc.nextLine();
                System.out.print("Enter address: ");
                String address = sc.nextLine();
                System.out.print("Enter phone: ");
                String phone = sc.nextLine();
                System.out.print("Enter email: ");
                String email = sc.nextLine();
                System.out.print("Enter age: ");
                int age = sc.nextInt();
                sc.nextLine(); // consume newline
                System.out.print("Enter gender: ");
                String gender = sc.nextLine();
                System.out.print("Enter membership type: ");
                String membership = sc.nextLine();

                String query = "INSERT INTO guest (name, address, phone, email, age, gender, membership) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, phone);
                ps.setString(4, email);
                ps.setInt(5, age);
                ps.setString(6, gender);
                ps.setString(7, membership);

                int rows = ps.executeUpdate();
                System.out.println(rows + " guest(s) registered successfully!");

                System.out.print("Do you want to add another guest? (yes/no): ");
                String answer = sc.nextLine().trim().toLowerCase();
                if (!answer.equals("yes")) {
                    break;
                }

            } catch (SQLException e) {
                System.out.println("Error registering guest: " + e.getMessage());
                break;
            }
        }

        // Show all guests after finishing input
        displayAllGuests();
    }

    public void displayAllGuests() {
        System.out.println("\n--- Current Guests in the System ---");
        String query = "SELECT * FROM guest";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-25s %-15s %-25s %-5s %-10s %-12s%n",
                    "ID", "Name", "Address", "Phone", "Email", "Age", "Gender", "Membership");

            System.out.println("-----------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("guest_id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String membership = rs.getString("membership");

                System.out.printf("%-10d %-20s %-25s %-15s %-25s %-5d %-10s %-12s%n",
                        id, name, address, phone, email, age, gender, membership);
            }

            System.out.println("-----------------------------------------------------------------------------------------------\n");

        } catch (SQLException e) {
            System.out.println("Error displaying guests: " + e.getMessage());
        }
    }
}
