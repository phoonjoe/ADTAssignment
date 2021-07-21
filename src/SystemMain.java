
import DatabaseClass.Database;
import EntityClass.*;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Joe Phoon
 */
public class SystemMain {

    private static Customer loginCustomer;
    private static Staff loginStaff;
    private static Database database;
    private static Scanner scan;
    private static GroupBuyMain groupBuyMain;

    private static final String SHORT_LINE = "=========================";

    public static void main(String[] args) {
        groupBuyMain = new GroupBuyMain();
        scan = new Scanner(System.in);
        database = new Database();

        boolean loop = true;
        do {
            Object accountType = login();
            if (accountType == null) {
                loop = false;
            } else if (accountType.getClass() == Customer.class) {
                loginCustomer = (Customer) accountType;
                customerLogin();

            } else if (accountType.getClass() == Staff.class) {
                loginStaff = (Staff) accountType;
                staffLogin();

            }

        } while (loop);

    }

    public static Object login() {
        char loginAgain = 'N';
        Scanner scan = new Scanner(System.in);
        String username;
        String password;

        do {
            System.out.print("Username : ");
            username = scan.nextLine();
            Customer matchedCustomer = database.getCustomerByUsername(username);
            Staff matchedStaff = database.getStaffByUsername(username);
            if (matchedCustomer == null && matchedStaff == null) {
                System.out.println("Username does not exist!");
            } else {
                System.out.print("Enter your password : ");
                password = scan.nextLine();
                if (matchedCustomer != null) {
                    if (password.equals(matchedCustomer.getPassword())) {
                        return matchedCustomer;
                    }
                } else if (matchedStaff != null) {
                    if (password.equals(matchedStaff.getPassword())) {
                        return matchedStaff;
                    }
                }
                System.out.println("Invalid password!");
            }
            System.out.print("Continue login? (Y/N): ");
            loginAgain = scan.nextLine().toUpperCase().charAt(0);

        } while (loginAgain == 'Y');

        return null;
    }

    private static void customerLogin() {
        boolean loop = true;
        do {
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Select Menu:\n"
                    + "-------------------------\n"
                    + "1. Purchase Item\n"
                    + "2. Involved Groups Buying\n"
                    + "0. Logout\n"
                    + "> ");
            int menu = scan.nextInt();
            scan.nextLine();
            switch (menu) {
                case 0:
                    System.out.printf("\n[ LOGGED OUT ]\n\n");
                    loop = false;
                    break;
                case 1:
                case 2:
                    groupBuyMain.groupBuyMain(menu, database, loginCustomer);
                    break;
                default:
                    System.out.printf("\n******************************************************\n");
                    System.out.printf("* Invalid number! Please enter number between 0 to 2 *\n");
                    System.out.printf("******************************************************\n");
            }
        } while (loop);

    }

    private static void staffLogin() {
        boolean loop = true;
        do {
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Select Menu:\n"
                    + "-------------------------\n"
                    + "1. You Gay\n"
                    + "2. Generate Sales Report By Date\n"
                    + "0. Logout\n"
                    + "> ");
            int menu = scan.nextInt();
            scan.nextLine();
            switch (menu) {
                case 0:
                    System.out.printf("\n[ LOGGED OUT ]\n\n");
                    loop = false;
                    break;
                case 1:
                    break;
                case 2:
                    groupBuyMain.generateSalesReport(database);
                    break;

            }
        } while (loop);

    }

}
