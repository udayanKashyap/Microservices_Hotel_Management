package com.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bookings {
    private Connection con;
    private Scanner sc;

    public Bookings(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void manageBookings() {
        displayBookings();

        System.out.println("Do you want to (add/delete/edit) a booking? Type 'no' to skip:");
        String action = sc.nextLine().trim().toLowerCase();

        switch (action) {
            case "add":
                addBooking();
                break;
            case "delete":
                deleteBooking();
                break;
            case "edit":
                editBooking();
                break;
            case "no":
                break;
            default:
                System.out.println("Invalid option!");
        }

        // Show updated booking list
        System.out.println("\nUpdated Bookings List:");
        displayBookings();
    }

    private void displayBookings() {
        System.out.println("\n--- All Bookings ---");
        String query = "SELECT * FROM bookings";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-10s %-10s %-10s %-15s %-15s %-12s %-18s %-25s %-12s%n",
                "BookingID", "GuestID", "RoomID", "CheckIn", "CheckOut", "Days", "Rooms", "Reviews", "Amount");
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int guestId = rs.getInt("guest_id");
                int roomId = rs.getInt("room_id");
                String checkIn = rs.getString("check_in");   // ✅ fixed
                String checkOut = rs.getString("check_out"); // ✅ fixed
                int days = rs.getInt("total_days");
                int numRooms = rs.getInt("number_of_rooms");
                String reviews = rs.getString("reviews");
                double amount = rs.getDouble("TotalAmount");

                System.out.printf("%-10d %-10d %-10d %-15s %-15s %-12d %-18d %-25s %-12.2f%n",
                    bookingId, guestId, roomId, checkIn, checkOut, days, numRooms, reviews, amount);
            }

        } catch (SQLException e) {
            System.out.println("Error displaying bookings: " + e.getMessage());
        }
    }


    private void addBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            int bookingId = sc.nextInt();

            System.out.print("Enter Guest ID: ");
            int guestId = sc.nextInt();

            // Check if guest exists
            PreparedStatement guestCheck = con.prepareStatement("SELECT * FROM guest WHERE guest_id = ?");
            guestCheck.setInt(1, guestId);
            ResultSet guestRs = guestCheck.executeQuery();
            if (!guestRs.next()) {
                System.out.println("❌ Guest with ID " + guestId + " not found in the database.");
                return;
            }

            System.out.print("Enter Room ID: ");
            int roomId = sc.nextInt();

            // Check if room exists
            PreparedStatement roomCheck = con.prepareStatement("SELECT * FROM rooms WHERE room_id = ?");
            roomCheck.setInt(1, roomId);
            ResultSet roomRs = roomCheck.executeQuery();
            if (!roomRs.next()) {
                System.out.println("❌ Room with ID " + roomId + " not found in the database.");
                return;
            }

            System.out.print("Enter Staff ID: ");
            int staffId = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
            String checkIn = sc.nextLine();

            System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
            String checkOut = sc.nextLine();

            System.out.print("Enter total days: ");
            int totalDays = sc.nextInt();

            System.out.print("Enter number of rooms: ");
            int numRooms = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter review: ");
            String review = sc.nextLine();

            System.out.print("Enter total amount: ");
            double totalAmount = sc.nextDouble();

            String query = "INSERT INTO bookings (booking_id, guest_id, room_id, staff_id, check_in, check_out, total_days, number_of_rooms, reviews, TotalAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookingId);
            ps.setInt(2, guestId);
            ps.setInt(3, roomId);
            ps.setInt(4, staffId);
            ps.setString(5, checkIn);
            ps.setString(6, checkOut);
            ps.setInt(7, totalDays);
            ps.setInt(8, numRooms);
            ps.setString(9, review);
            ps.setDouble(10, totalAmount);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Booking added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }


    private void deleteBooking() {
        try {
            System.out.print("Enter Booking ID to delete: ");
            int bookingId = sc.nextInt();
            sc.nextLine(); // consume newline

            String query = "DELETE FROM bookings WHERE booking_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookingId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Booking deleted successfully!");
            } else {
                System.out.println("Booking ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
        }
    }

    private void editBooking() {
        try {
            System.out.print("Enter Booking ID to edit: ");
            int bookingId = sc.nextInt();

            System.out.print("Enter new Guest ID: ");
            int guestId = sc.nextInt();

            System.out.print("Enter new Room ID: ");
            int roomId = sc.nextInt();

            sc.nextLine();  // consume leftover newline

            System.out.print("Enter new Check-in Date (YYYY-MM-DD): ");
            String checkIn = sc.nextLine();

            System.out.print("Enter new Check-out Date (YYYY-MM-DD): ");
            String checkOut = sc.nextLine();

            System.out.print("Enter total days: ");
            int totalDays = sc.nextInt();

            System.out.print("Enter number of rooms: ");
            int numRooms = sc.nextInt();

            sc.nextLine();  // consume newline again

            System.out.print("Enter review: ");
            String review = sc.nextLine();

            System.out.print("Enter total amount: ");
            double amount = sc.nextDouble();

            String query = "UPDATE bookings SET guest_id=?, room_id=?, check_in=?, check_out=?, total_days=?, number_of_rooms=?, reviews=?, TotalAmount=? WHERE booking_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, guestId);
            ps.setInt(2, roomId);
            ps.setString(3, checkIn);
            ps.setString(4, checkOut);
            ps.setInt(5, totalDays);
            ps.setInt(6, numRooms);
            ps.setString(7, review);
            ps.setDouble(8, amount);
            ps.setInt(9, bookingId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Booking updated successfully!");
            } else {
                System.out.println("Booking ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error editing booking: " + e.getMessage());
        }
    }

}
