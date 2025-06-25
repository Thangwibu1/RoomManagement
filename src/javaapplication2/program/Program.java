package javaapplication2.program;

import javaapplication2.controller.Menu;
import javaapplication2.function.Function;

import java.io.IOException;


public class Program {

    public static void main(String[] args) throws IOException {
        
        //inialize variables
        Menu menu = new Menu();
        Function function = new Function();
        function.importGuestData();
        function.importReservationData();
        while (true) {
            int choose = menu.showMenu();
            switch (choose) {
                case 1:
                    // Import Room Data from Text File
                    function.importRoomData();
                    break;

                case 2:
                    function.displayRooms();

                    break;
                case 3:
                    function.enterGuestInformation();
                    break;
                case 4:
                    function.updateReservation();

                    break;
                case 5:
                    function.searchReservationById();
                    break;
                case 6:
                    function.deleteReversionBeforeArrival();

                    break;
                case 7:

                    function.showAvailableRooms();
                    break;
                case 8:
                    function.monthlyRevenueReport();
                    // Display Customer or Order lists
                    break;
                case 9:
                    function.reportRevuneByRoomType();
                    break;
                case 10:
                    function.saveDataToFile();
                    break;
                case 0:
                    // Exit

                    return;
            }
        }

    }
    
}
