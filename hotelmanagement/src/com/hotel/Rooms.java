package com.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Rooms {
    private Connection con;
    private Scanner sc;

    public Rooms(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void manageRooms() {
        displayRooms();

        System.out.println("Do you want to (add/delete/edit) a room? Type 'no' to skip:");
        String action = sc.nextLine().trim().toLowerCase();

        switch (action) {
            case "add":
                addRoom();
                break;
            case "delete":
                deleteRoom();
                break;
            case "edit":
                editRoom();
                break;
            case "no":
                break;
            default:
                System.out.println("Invalid option!");
        }

        // Show updated room list
        System.out.println("\nUpdated Room List:");
        displayRooms();
    }

    private void displayRooms() {
        System.out.println("\n--- Available Rooms ---");
        String query = "SELECT * FROM rooms";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-10s %-15s %-10s %-15s%n", "Room ID", "Type", "Price", "Status");
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String roomType = rs.getString("room_type");
                double price = rs.getDouble("price");
                String status = rs.getString("status");

                System.out.printf("%-10d %-15s %-10.2f %-15s%n", roomId, roomType, price, status);
            }

        } catch (SQLException e) {
            System.out.println("Error displaying rooms: " + e.getMessage());
        }
    }

    private void addRoom() {
        try {
            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = sc.nextLine();
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // consume newline
            System.out.print("Enter status (Available/Occupied): ");
            String status = sc.nextLine();

            String query = "INSERT INTO rooms (room_type, price, status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, roomType);
            ps.setDouble(2, price);
            ps.setString(3, status);

            int rows = ps.executeUpdate();
            System.out.println(rows + " room(s) added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    private void deleteRoom() {
        try {
            System.out.print("Enter room ID to delete: ");
            int roomId = sc.nextInt();
            sc.nextLine(); // consume newline

            String query = "DELETE FROM rooms WHERE room_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Room deleted successfully!");
            } else {
                System.out.println("Room ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
        }
    }

    private void editRoom() {
        try {
            System.out.print("Enter room ID to edit: ");
            int roomId = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter new room type: ");
            String roomType = sc.nextLine();
            System.out.print("Enter new price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // consume newline
            System.out.print("Enter new status: ");
            String status = sc.nextLine();

            String query = "UPDATE rooms SET room_type = ?, price = ?, status = ? WHERE room_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, roomType);
            ps.setDouble(2, price);
            ps.setString(3, status);
            ps.setInt(4, roomId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("Room ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }
}
