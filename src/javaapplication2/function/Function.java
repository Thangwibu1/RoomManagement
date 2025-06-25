package javaapplication2.function;

import javaapplication2.controller.Inputer;
import javaapplication2.model.Guest;
import javaapplication2.model.Reservation;
import javaapplication2.model.Room;
import javaapplication2.storage.GuestList;
import javaapplication2.storage.ReservationList;
import javaapplication2.storage.RoomList;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Function {
    RoomList roomList = new RoomList();
    GuestList guestList = new GuestList();
    ReservationList reservationList = new ReservationList();

    public void importRoomData() throws IOException {
        if (roomList.readFromFile()) {
            System.out.println(roomList.size() + " Rooms loaded successfully from file.");
            System.out.println((18 - roomList.size()) + " Failed to load rooms from file.");
        } else {
            System.out.println((18 - roomList.size()) + " Failed to load rooms from file.");
        }
    }

    public void displayRooms() {
        roomList.showAll();
    }

    public void displayGuests() {
        guestList.showAll();
    }

    public void importGuestData() throws IOException {
        guestList.readFromFile();
    }

    public void importReservationData() throws IOException {
        reservationList.readFromFile();
    }

    // CASE 3: Enter Guest Information
    public void enterGuestInformation() throws IOException {
        reservationList.showAll();
        // Search customer by name
        String nationalId = "";
        nationalId = Inputer.inputString("^[0-9]{12}$", "Please enter national ID: ");
        String name = "";
        String gender = "";
        String phoneNumber = "";
        String birthDate = "";
        if (guestList.searchById(nationalId) == null) {
            String firstName = Inputer.inputString("^[a-zA-Z]+$", "Please enter customer's first name: ");
            String lastName = Inputer.inputString("^[a-zA-Z]+$", "Please enter customer's last name: ");

            name = firstName + ", " + lastName;

            while (true) {
                birthDate = Inputer.inputString("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([12][0-9]{3})$",
                        "Please enter customer's birth date dd/mm/yyyy: ");
                LocalDate yearGet = LocalDate.now();
                String[] birthDateParts = birthDate.split("/");
                if (Integer.parseInt(birthDateParts[2]) > yearGet.getYear()
                        || Integer.parseInt(birthDateParts[2]) < 0) {
                    continue;
                }
                break;
            }

            gender = Inputer.inputString("^[MF]$", "Please enter customer's gender (M/F): ");
            phoneNumber = Inputer.inputPhone("Please enter customer's phone number: ");
        } else {
            System.out.println("Customer with national ID " + nationalId + " already exists.");
            System.out.println("Enter Reservation Information");
        }

        int rentalDays = 0;
        while (true) {
            rentalDays = Inputer.inputInt("^[1-9][0-9]*$",
                    "Please enter the number of rental days (positive integer): ");
            if (rentalDays <= 0) {
                System.out.println("Rental days must be a positive integer. Please try again.");
                continue;
            }
            break;
        }
        String startDate = "";
        String endDate = "";
        while (true) {
            startDate = Inputer.inputString("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([12][0-9]{3})$",
                    "Please enter the start date dd/mm/yyyy: ");
            String[] startDateParts = startDate.split("/");
            LocalDate startDateLocal = LocalDate
                    .parse(startDateParts[2] + "-" + startDateParts[1] + "-" + startDateParts[0]);
            if (!startDateLocal.isAfter(LocalDate.now())) {
                System.out.println("Start date must be in future!");
                continue;
            }
            LocalDate endDateLocal = startDateLocal.plusDays(rentalDays);
            endDate = String.format("%02d/%02d/%04d", endDateLocal.getDayOfMonth(), endDateLocal.getMonthValue(),
                    endDateLocal.getYear());
            break;
        }
        // handle end date
        // end part handle end date

        showAvailableRooms(startDate, endDate);
        String roomId = "";
        while (true) {
            roomId = Inputer.inputString("^[R]{1}[0-9]{3}$", "Please enter customer's room ID: ");
            if (roomList.searchById(roomId) == null || !reservationList.isAvailable(roomId, startDate, endDate)) {
                System.out.println(reservationList.isAvailable(roomId, startDate, endDate));
                System.out.println(roomList.searchById(roomId) == null);
                System.out.println("Incorrect room ID. Please enter a valid room ID.");
                continue;
            }
            break;
        }

        String nameOfCotenant = Inputer.inputString("^[a-zA-Z\\s]+$",
                "Please enter the name of co-tenant (if any, if not enter null): ");
        if (nameOfCotenant.equalsIgnoreCase("null")) {
            nameOfCotenant = "";
        }

        String reservationId = "Rs" + (reservationList.size() + 1);
        if (reservationList.addNew(
                new Reservation(reservationId, nationalId, roomId, rentalDays, startDate, endDate, nameOfCotenant))
                && guestList.addNew(new Guest(nationalId, name, birthDate, gender, phoneNumber))) {
            System.out.println("Guest information and reservation successfully added.");
            reservationList.saveToFile();
            guestList.saveToFile();
            new Reservation(reservationId, nationalId, roomId, rentalDays, startDate, endDate, nameOfCotenant)
                    .showInformation();
            new Guest(nationalId, guestList.searchById(nationalId).getFullName(), guestList.searchById(nationalId).getBirthDate(), guestList.searchById(nationalId).getGender(), guestList.searchById(nationalId).getPhoneNumber()).showInformation();
        } else {
            System.out.println("Failed to add guest information or reservation.");
        }
    }

    public void updateReservation() {
        String searchId = Inputer.inputString("^[0-9]{12}$", "Please enter national ID to search: ");
        ArrayList<Reservation> reservations = reservationList.searchByGuestId(searchId);
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for the given national ID.");
            return;
        } else if (reservations.size() == 1) {
            Reservation updateReservaiton = reservations.get(0);
            String phoneUpdate = Inputer.inputPhone("Please enter new phone number: ");
            int rentalDaysUpdate = Inputer.inputInt("^[1-9][0-9]*$",
                    "Please enter the number of rental days (positive integer): ");
            String updateCotenant = Inputer.inputString("^[a-zA-Z\\s]+$",
                    "Please enter the name of co-tenant (if any, if not enter null): ");
            if (updateCotenant.equalsIgnoreCase("null")) {
                updateCotenant = "";
            }
            String endDateUpdate = "";
            if (rentalDaysUpdate > updateReservaiton.getRentalDays()) {
                LocalDate tmpEndDate = LocalDate.parse(updateReservaiton.getStartDate().substring(6) + "-"
                        + updateReservaiton.getStartDate().substring(3, 5) + "-"
                        + updateReservaiton.getStartDate().substring(0, 2));
                tmpEndDate = tmpEndDate.plusDays(rentalDaysUpdate - updateReservaiton.getRentalDays());
                endDateUpdate = String.format("%02d/%02d/%04d", tmpEndDate.getDayOfMonth(), tmpEndDate.getMonthValue(),
                        tmpEndDate.getYear());
            } else {
                LocalDate tmpEndDate = LocalDate.parse(updateReservaiton.getStartDate().substring(6) + "-"
                        + updateReservaiton.getStartDate().substring(3, 5) + "-"
                        + updateReservaiton.getStartDate().substring(0, 2));
                tmpEndDate = tmpEndDate.minusDays(Math.abs(rentalDaysUpdate - updateReservaiton.getRentalDays()));
                endDateUpdate = String.format("%02d/%02d/%04d", tmpEndDate.getDayOfMonth(), tmpEndDate.getMonthValue(),
                        tmpEndDate.getYear());
            }

            reservationList.update(new Reservation(updateReservaiton.getReservedId(),
                    updateReservaiton.getNationalIdNumber(), updateReservaiton.getRoomId(), rentalDaysUpdate,
                    updateReservaiton.getStartDate(), endDateUpdate, updateCotenant));
            guestList.searchById(searchId).setPhoneNumber(phoneUpdate);
        } else {
            for (Reservation r : reservations) {
                System.out.println("Reservation ID: " + r.getReservedId());
                System.out.println("Room ID: " + r.getRoomId());
                System.out.println("Start Date: " + r.getStartDate());
                System.out.println("End Date: " + r.getEndDate());
                System.out.println("Rental Days: " + r.getRentalDays());
                System.out.println("Co-tenant Name: " + r.getNameOfCoTenant());
                System.out.println("--------------------------------------------------");
            }
            String reservationId = Inputer.inputString("^[a-zA-Z0-9]+$", "Please enter the reservation ID to update: ");
            for (Reservation r : reservations) {
                if (r.getReservedId().equals(reservationId)) {
                    String phoneUpdate = Inputer.inputPhone("Please enter new phone number: ");
                    int rentalDaysUpdate = Inputer.inputInt("^[1-9][0-9]*$",
                            "Please enter the number of rental days (positive integer): ");
                    String updateCotenant = Inputer.inputString("^[a-zA-Z\\s]+$",
                            "Please enter the name of co-tenant (if any, if not enter null): ");
                    if (updateCotenant.equalsIgnoreCase("null")) {
                        updateCotenant = "";
                    }
                    String endDateUpdate = "";
                    if (rentalDaysUpdate > r.getRentalDays()) {
                        LocalDate tmpEndDate = LocalDate.parse(r.getStartDate().substring(6) + "-"
                                + r.getStartDate().substring(3, 5) + "-" + r.getStartDate().substring(0, 2));
                        tmpEndDate = tmpEndDate.plusDays(rentalDaysUpdate - r.getRentalDays());
                        endDateUpdate = String.format("%02d/%02d/%04d", tmpEndDate.getDayOfMonth(),
                                tmpEndDate.getMonthValue(), tmpEndDate.getYear());
                    } else {
                        LocalDate tmpEndDate = LocalDate.parse(r.getStartDate().substring(6) + "-"
                                + r.getStartDate().substring(3, 5) + "-" + r.getStartDate().substring(0, 2));
                        tmpEndDate = tmpEndDate.minusDays(Math.abs(rentalDaysUpdate - r.getRentalDays()));
                        endDateUpdate = String.format("%02d/%02d/%04d", tmpEndDate.getDayOfMonth(),
                                tmpEndDate.getMonthValue(), tmpEndDate.getYear());
                    }
                    reservationList.update(new Reservation(r.getReservedId(), r.getNationalIdNumber(), r.getRoomId(),
                            rentalDaysUpdate, r.getStartDate(), endDateUpdate, updateCotenant));
                    guestList.searchById(searchId).setPhoneNumber(phoneUpdate);
                    System.out.println("Reservation updated successfully.");
                }
            }
        }

    }

    public void searchReservationById() throws IOException {
        String searchId = Inputer.inputString("^[0-9]{12}$", "Please enter national ID to search: ");
        ArrayList<Reservation> reservations = reservationList.searchByGuestId(searchId);
        reservations.forEach(r -> {
            try {
                r.showInformation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteReversionBeforeArrival() throws IOException {
        reservationList.showAll();
        String searchId = Inputer.inputString("^[0-9]{12}$", "Please enter national ID to search: ");
        ArrayList<Reservation> reservations = reservationList.searchByGuestId(searchId);
        System.out.println("DEBUG: " + reservations.size());
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for the given national ID.");
            return;
        } else if (reservations.size() == 1) {
            String opinion = Inputer.inputString("^[YyNn]$", "Do you want to delete this reservation? (Y/N): ");
            if (opinion.equalsIgnoreCase("n")) {
                System.out.println("Reservation deletion cancelled.");
                return;
            } else {
                if (!reservations.get(0).availableToDelete(reservations.get(0))) {
                } else {
                    System.out.println("Reservation can be deleted.");
                    reservationList.remove(reservations.get(0));
                    System.out.println("Reservation deleted successfully.");
                }
            }

        } else {
            for (Reservation r : reservations) {
                System.out.println("Reservation ID: " + r.getReservedId());
                System.out.println("Room ID: " + r.getRoomId());
                System.out.println("Start Date: " + r.getStartDate());
                System.out.println("End Date: " + r.getEndDate());
                System.out.println("Rental Days: " + r.getRentalDays());
                System.out.println("Co-tenant Name: " + r.getNameOfCoTenant());
                System.out.println("--------------------------------------------------");
            }
            String reservationId = Inputer.inputString("^[a-zA-Z0-9]+$", "Please enter the reservation ID to delete: ");
            String opinion = Inputer.inputString("^[YyNn]$", "Do you want to delete this reservation? (Y/N): ");
            if (opinion.equalsIgnoreCase("n")) {
                System.out.println("Reservation deletion cancelled.");
                return;
            } else {
                for (Reservation r : reservations) {
                    if (r.getReservedId().equals(reservationId)) {
                        reservationList.remove(r);
                        System.out.println("Reservation deleted successfully.");
                    }
                }
            }
        }
    }

    public void monthlyRevenueReport() {
        int month = Inputer.inputInt("^[1-9][0-9]*$", "Please enter the month (positive integer): ");
        double sum = 0;
        for (Reservation reservation : reservationList) {
            LocalDate tmpDate = LocalDate.parse(reservation.getStartDate().substring(6) + "-"
                    + reservation.getStartDate().substring(3, 5) + "-" + reservation.getStartDate().substring(0, 2));
            if (tmpDate.getMonthValue() == month) {
                Room tmpRoom = roomList.searchById(reservation.getRoomId());
                sum += tmpRoom.getDailyRate();
            }
        }
        System.out.println("Total revenue for the month " + month + " is " + sum + " $");
    }

    public void reportRevuneByRoomType() {
        String roomType = Inputer.inputString("^[a-zA-Z]+$", "Please enter the room type: ");
        double sum = 0;
        for (Reservation reservation : reservationList) {
            Room tmpRoom = roomList.searchById(reservation.getRoomId());
            if (tmpRoom.getRoomType().equalsIgnoreCase(roomType)) {
                sum += tmpRoom.getDailyRate();
            }
        }
        System.out.println("Total revenue for the room type " + roomType + " is " + sum + " $");
    }

    public void saveDataToFile() throws IOException {
        roomList.saveToFile();
        guestList.saveToFile();
        reservationList.saveToFile();
        System.out.println("Data saved successfully.");
    }

    public void showAvailableRooms() {
        List<Room> availableRooms = roomList;
        LocalDate today = LocalDate.now();
        for (Reservation reservation : reservationList) {
            LocalDate startDate = LocalDate.parse(reservation.getStartDate().substring(6) + "-"
                    + reservation.getStartDate().substring(3, 5) + "-" + reservation.getStartDate().substring(0, 2));
            LocalDate endDate = LocalDate.parse(reservation.getEndDate().substring(6) + "-"
                    + reservation.getEndDate().substring(3, 5) + "-" + reservation.getEndDate().substring(0, 2));
            if (today.isBefore(endDate)) {
                if (availableRooms.contains(roomList.searchById(reservation.getRoomId()))) {
                    availableRooms.remove(roomList.searchById(reservation.getRoomId()));
                }
            }
        }
        availableRooms.forEach(room -> {
            try {
                room.showInformation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showAvailableRooms(String startDate, String endDate) {
        List<Room> availableRooms = roomList;
        LocalDate start = LocalDate.parse(startDate.substring(6) + "-" + startDate.substring(3, 5) + "-" + startDate.substring(0, 2));
        LocalDate end = LocalDate.parse(endDate.substring(6) + "-" + endDate.substring(3, 5) + "-" + endDate.substring(0, 2));
        for (Reservation reservation : reservationList) {
            LocalDate resStart = LocalDate.parse(reservation.getStartDate().substring(6) + "-" + reservation.getStartDate().substring(3, 5) + "-" + reservation.getStartDate().substring(0, 2));
            LocalDate resEnd = LocalDate.parse(reservation.getEndDate().substring(6) + "-" + reservation.getEndDate().substring(3, 5) + "-" + reservation.getEndDate().substring(0, 2));
            if ((start.isBefore(resEnd) && resStart.isBefore(start)) || (end.isAfter(resStart) && resEnd.isAfter(end))) {
                if (availableRooms.contains(roomList.searchById(reservation.getRoomId()))) {
                    availableRooms.remove(roomList.searchById(reservation.getRoomId()));
                }
            }
        }
        availableRooms.forEach(room -> {
            try {
                room.showInformation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
