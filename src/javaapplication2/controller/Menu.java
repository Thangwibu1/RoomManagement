package javaapplication2.controller;


public class Menu {
    public int showMenu() {
        System.out.println("------------------------Menu-------------------------");
        System.out.println("|1. Import Room Data from Text File                 |");
        System.out.println("|2. Display Available Room List                     |");
        System.out.println("|3. Enter Guest Information                         |");
        System.out.println("|4. Update Guest Stay Information                   |");
        System.out.println("|5. Search Guest by National ID                     |");
        System.out.println("|6. Delete Guest Reservation                        |");
        System.out.println("|7. List Vacant Rooms                               |");
        System.out.println("|8. Monthly Revenue Report                          |");
        System.out.println("|9. Revenue Report by Room Type                     |");
        System.out.println("|10. Save Guest Information                         |");
        System.out.println("|0. Exit                                            |");
        System.out.println("-----------------------------------------------------");
        System.out.print("Please choose an option: ");

        return Inputer.inputInt("^([0-9]|10)$", "");
    }
}