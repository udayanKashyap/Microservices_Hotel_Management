package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    static final String USER = "root";
    static final String PASS = "jobin123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Scanner sc = new Scanner(System.in);

        // Create object instances for each class
        Guests guest = new Guests(con, sc);
        Bookings booking = new Bookings(con, sc);
//        FoodOrders foodOrder = new FoodOrders(con, sc);
//        Payments payment = new Payments(con, sc);
//        Staff staff = new Staff(con, sc);
//        FoodItems foodItem = new FoodItems(con, sc);
        Rooms room = new Rooms(con, sc);

        while (true) {
            System.out.println("\n---- Hotel Management System ----");
            System.out.println("1. Register Guests");
            System.out.println("2. Book Rooms");
            System.out.println("3. Order Food");
            System.out.println("4. Payments");
            System.out.println("5. Staff Management");
            System.out.println("6. Food Management");
            System.out.println("7. Room Management");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    guest.registerGuest();
                    break;
                case 2:
                	booking.manageBookings();
                    break;
//                case 3:
//                    foodOrder.placeOrder();
//                    break;
//                case 4:
//                    payment.processPayment();
//                    break;
//                case 5:
//                    staff.manageStaff();
//                    break;
//                case 6:
//                    foodItem.manageFoodItems();
//                    break;
                case 7:
                    room.manageRooms();
                    break;
                case 8:
                    System.out.println("Exiting... Thank you!");
                    con.close();
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}
