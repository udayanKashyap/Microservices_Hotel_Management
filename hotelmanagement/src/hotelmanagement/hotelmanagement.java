package hotelmanagement;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Interface
interface Discountable {
 boolean isEligibleForDiscount();
}

//Abstract class
abstract class Booking {
 protected int numberOfRooms;
 protected int numberOfDays;

 public Booking(int rooms, int days) {
     this.numberOfRooms = rooms;
     this.numberOfDays = days;
 }

 public abstract int calculateTotalCost();
}

//Inheritance + Polymorphism
class RoomBooking extends Booking implements Discountable {
 private final int costPerRoom = 2000;
 private boolean isRegularCustomer;

 public RoomBooking(int rooms, int days, boolean isRegular) {
     super(rooms, days);
     this.isRegularCustomer = isRegular;
 }

 @Override
 public int calculateTotalCost() {
     int total = numberOfRooms * numberOfDays * costPerRoom;
     if (isEligibleForDiscount()) {
         total -= total * 0.1; // 10% discount
     }
     return total;
 }

 @Override
 public boolean isEligibleForDiscount() {
     return isRegularCustomer && numberOfDays > 3;
 }
}

//Encapsulation
class Customer {
 private String name;
 private String date;
 private boolean isRegular;

 public Customer(String name, String date, boolean isRegular) {
     this.name = name;
     this.date = date;
     this.isRegular = isRegular;
 }

 public String getName() {
     return name;
 }

 public String getDate() {
     return date;
 }

 public boolean isRegular() {
     return isRegular;
 }
}

public class hotelmanagement {

    static int availableRooms = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- HOTEL MANAGEMENT SYSTEM ---");
            System.out.println("1. Book Room");
            System.out.println("2. Food Menu");
            System.out.println("3. Check Discount Eligibility");
            System.out.println("4. Date Change Simulation (Unary)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> handleBooking(sc);     // Booking + Availability
                    case 2 -> foodMenu(sc);          // New food menu
                    case 3 -> discountCheck(sc);     // Already done
                    case 4 -> simulateDateChange();  // Already done
                    case 5 -> System.out.println("Thank you for using the system!");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter a number.");
                sc.nextLine(); // clear buffer
                choice = -1;
            }
        } while (choice != 5);
    }

    // Booking with encapsulation, abstraction, inheritance
    public static void handleBooking(Scanner sc) {
        String name = "";
        String date = "";
        boolean isRegular = false;
        int rooms = 0;
        int days = 0;

        // Name
        System.out.print("Enter Customer Name: ");
        name = sc.nextLine();

        // Date
        while (true) {
            System.out.print("Enter Booking Date (e.g., 2025-06-09): ");
            date = sc.nextLine();
            try {
                LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate today = LocalDate.now();

                if (inputDate.isAfter(today)) {
                    break; // valid input
                } else {
                    System.out.println("Booking date must be after today's date.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please enter in yyyy-MM-dd format.");
            }
        }

        discountCheck(sc);

        // Number of Rooms (loop until valid)
        while (true) {
            System.out.println("\n--- Room Availability ---");
            System.out.println("Currently available rooms: " + availableRooms);
            System.out.print("Enter number of rooms to book: ");
            try {
                rooms = sc.nextInt();
                if (rooms <= 0) {
                    System.out.println("Rooms must be greater than 0.");
                    continue;
                }
                if (rooms > availableRooms) {
                    System.out.println("Only " + availableRooms + " rooms are available.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }
        }


        // Number of Days (loop until valid)
        while (true) {
            System.out.print("Enter number of days: ");
            try {
                days = sc.nextInt();
                if (days <= 0) {
                    System.out.println("Days must be greater than 0.");
                    continue;
                }
                if (days > 20) {
                    System.out.println("Stay cannot exceed 20 days.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); // clear invalid input
            }
        }


        // Booking logic
        Customer customer = new Customer(name, date, isRegular);
        RoomBooking booking = new RoomBooking(rooms, days, isRegular);
        int total = booking.calculateTotalCost();

        availableRooms -= rooms; // update availability

        System.out.println("\n Booking Confirmed for " + customer.getName());
        System.out.println("Date: " + customer.getDate());
        System.out.println("Total Cost: ₹" + total);
    }

    public static void foodMenu(Scanner sc) {
    	String[] foodItems = {
    		    "1. Roti - ₹30",
    		    "2. Daal - ₹40",
    		    "3. Fried Rice - ₹90",
    		    "4. Chicken Curry - ₹150",
    		    "5. Coffee - ₹20",
    		    "6. Paneer Butter Masala - ₹130",
    		    "7. Veg Biryani - ₹110",
    		    "8. Chicken Biryani - ₹160",
    		    "9. Masala Dosa - ₹50",
    		    "10. Sambhar Vada - ₹45",
    		    "11. Butter Naan - ₹35",
    		    "12. Mutton Curry - ₹180",
    		    "13. Ice Cream - ₹60",
    		    "14. Tea - ₹15",
    		    "15. Soft Drink - ₹25",
    		    "16. Gobi Manchurian - ₹80",
    		    "17. Egg Curry - ₹100",
    		    "18. Rasgulla - ₹40",
    		    "19. French Fries - ₹70",
    		    "20. Mixed Veg Curry - ₹95"
    		};

    		int[] prices = {
    		    30, 40, 90, 150, 20,
    		    130, 110, 160, 50, 45,
    		    35, 180, 60, 15, 25,
    		    80, 100, 40, 70, 95
    		};
        System.out.println("\n--- FOOD MENU ---");
        for (String item : foodItems) {
            System.out.println(item);
        }

        int total = 0;
        List<Integer> selectedItems = new ArrayList<>();

        while (true) {
            System.out.print("Enter item number to order (0 to finish): ");
            try {
                int item = sc.nextInt();
                if (item == 0) {
                    while (true) {
                        System.out.println("\n Order Summary:");
                        int orderTotal = 0;
                        for (int i = 0; i < selectedItems.size(); i++) {
                            int index = selectedItems.get(i) - 1;
                            System.out.println("- " + foodItems[index]);
                            orderTotal += prices[index];
                        }

                        System.out.println("Subtotal (before room service): ₹" + orderTotal);
                        System.out.print("\nDo you want to edit your order? (yes/no): ");
                        sc.nextLine(); // clear leftover newline
                        String editChoice = sc.nextLine().toLowerCase();

                        if (editChoice.equals("no")) {
                            total = orderTotal;
                            break; // ✅ proceed to room service
                        } else if (editChoice.equals("yes")) {
                            selectedItems.clear();
                            total = 0;

                            // ✅ Restart the ENTIRE ordering loop from scratch
                            System.out.println("\nStart entering your order again:");
                            for (String itemStr : foodItems) {
                                System.out.println(itemStr);
                            }

                            while (true) {
                                System.out.print("Enter item number to order (0 to finish): ");
                                try {
                                    int newItem = sc.nextInt();
                                    if (newItem == 0) break;
                                    if (newItem < 1 || newItem > foodItems.length) {
                                        System.out.println(" Invalid item number.");
                                        continue;
                                    }
                                    selectedItems.add(newItem);
                                    total += prices[newItem - 1];
                                } catch (InputMismatchException e) {
                                    System.out.println(" Enter a valid item number.");
                                    sc.nextLine(); // clear invalid input
                                }
                            }

                        } else {
                            System.out.println("Please enter 'yes' or 'no'.");
                            continue;
                        }

                        // show updated summary after re-selection
                        System.out.println("\n🧾 Updated Order Summary:");
                        int finalTotal = 0;
                        for (int i = 0; i < selectedItems.size(); i++) {
                            int index = selectedItems.get(i) - 1;
                            System.out.println("- " + foodItems[index]);
                            finalTotal += prices[index];
                        }
                        System.out.println("Subtotal (before room service): ₹" + finalTotal);
                        total = finalTotal;
                        break;
                    }
                    break;
                }

                if (item < 1 || item > foodItems.length) {
                    System.out.println(" Invalid item number.");
                    continue;
                }
                selectedItems.add(item);
                total += prices[item - 1];
            } catch (InputMismatchException e) {
                System.out.println(" Enter a valid item number.");
                sc.nextLine();
            }
        }

        sc.nextLine(); // clear buffer
        boolean roomService = false;
        while (true) {
            System.out.print("Do you want room service? (yes/no): ");
            String response = sc.nextLine().toLowerCase();
            if (response.equals("yes")) {
                roomService = true;
                break;
            } else if (response.equals("no")) {
                break;
            } else {
                System.out.println(" Please type 'yes' or 'no'");
            }
        }

        if (roomService) {
            int serviceCharge = 50;
            total += serviceCharge;
            System.out.println("Room service charge of ₹50 added.");
        }

        System.out.println("\n Order placed successfully.");
        System.out.println("Total amount to pay: ₹" + total);
    }

    // Relational Operators
    public static void checkAvailability(Scanner sc) {
        System.out.println("Currently available rooms: " + availableRooms);
        System.out.print("Enter number of rooms needed: ");
        int requested = sc.nextInt();
        if (requested <= availableRooms) {
            System.out.println("Rooms are available.");
        } else {
            System.out.println("Not enough rooms available.");
        }
    }

    // Logical Operators
    public static void discountCheck(Scanner sc) {
        System.out.print("Enter customer's age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        System.out.print("Enter membership card ID (leave empty if none): ");
        String memberId = sc.nextLine().trim();

        boolean eligible = (age >= 60) || (!memberId.isEmpty());
        System.out.println("Discount Eligibility: " + (eligible ? "Yes" : "No"));
    }

    // Unary Operator Example
    public static void simulateDateChange() {
        int day = 1;
        System.out.println("Booking is on Day: " + day);
        day++; // unary increment
        System.out.println("Date updated to Day: " + day);
    }
}
