package javaapplication2.controller;

import java.util.Scanner;

public class Inputer {
    private static Scanner sc = new Scanner(System.in);
    //create regex for viettel
    public static final String VIETTEL_VALID_PHONE = "^03[0-9]{8}$";
    public static final String REMAIN_VALID_PHONE1 = "^09[0-9]{8}$";
    public static final String REMAIN_VALID_PHONE2 = "^07[0-9]{8}$";

    //create regex for
    //create method to input data
    public static String inputString(String match, String message) {
        String result = "";
        //create flag to check again1
        int flag = 0;
        do {
            if (flag == 0) {
                System.out.print(message);
                flag++;
            } else {
                System.out.println("Invalid input!");
                System.out.print(message);
            }

            result = sc.nextLine();
        } while (!result.matches(match) || result.trim().isEmpty());
        return result;
    }

    public static String inputPhone(String message) {
        String phone = "";
        while (true) {
            System.out.print(message);
            phone = sc.nextLine();
            if (phone.matches(VIETTEL_VALID_PHONE) || phone.matches(REMAIN_VALID_PHONE1) || phone.matches(REMAIN_VALID_PHONE2)) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter a valid Vienamese phone number.");
            }
        }
        return phone;
    }
    public static int inputInt(String match, String message) {
        int number = 0;
        while (true) {
            try {
                System.out.print(message);
                number = Integer.parseInt(sc.nextLine());
                if (!Integer.toString(number).matches(match)) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return number;
    }

    public static double inputDouble(String match, String message) {
        double number = 0.0;
        while (true) {
            try {
                System.out.print(message);
                number = Double.parseDouble(sc.nextLine());
                if (!Double.toString(number).matches(match)) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return number;
    }

    //create method to input mail
    public static String inputEmail(String message) {
        String email = "";
        while (true) {
            System.out.print(message);
            email = sc.nextLine();
            if (email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$") || email.matches("^[a-zA-Z0-9._%+-]+@yahoo\\.com$") || email.matches("^[a-zA-Z0-9._%+-]+@hotmail\\.com$")
                    || email.matches("^[a-zA-Z0-9._%+-]+@outlook\\.com$") || email.matches("^[a-zA-Z0-9._%+-]+@icloud\\.com$")) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }
        return email;
    }

    //overloading method input integer
    public static int inputInt(int min, int max, String message) {
        int number = 0;
        while (true) {
            try {
                System.out.print(message);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return number;
    }
}