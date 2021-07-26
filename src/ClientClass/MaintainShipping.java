package ClientClass;

import DatabaseClass.Database;
import EntityClass.*;
import ImplementationClass.*;
import java.util.Scanner;

/**
 *
 * @author Lee Jun Sian
 */
public class MaintainShipping {

    private Database database;
    private Scanner scan;
    private final String BORDER = "========================================================================================================================================================================================================";
    private final String SHORT_LINE = "=========================";

    public void maintainShipping(Database paraDatabase) {
        scan = new Scanner(System.in);
        this.database = paraDatabase;
        boolean loop = false;

        do {
            ShippingArrayList<Shipping> shippingList = database.getShippingList();
            shippingMenu();
            System.out.printf("Enter (0->5) to move on  > ");
            int shippingMenuSelect = scan.nextInt();
            scan.nextLine();
            switch (shippingMenuSelect) {
                case 0:
                    loop = false;
                    break;

                case 1:
                    if (shippingList.getNumberOfShipping() > 0) {
                        loop = viewShippingCompany(shippingList);
                    } else {
                        System.out.print("Shipping company list is empty now!");
                        loop = false;
                    }
                    break;

                case 2:
                    loop = viewShippingCompany(shippingList);
                    break;

                case 3:
                    if (shippingList.getNumberOfShipping() > 0) {
                        loop = viewShippingCompany(shippingList);
                    } else {
                        System.out.print("Shipping company list is empty now!");
                        loop = false;
                    }
                    break;

                case 4:
                    if (shippingList.getNumberOfShipping() > 0) {
                        loop = viewShippingCompany(shippingList);
                    } else {
                        System.out.print("Shipping company list is empty now!");
                        loop = false;
                    }
                    break;

                case 5:
                    loop = true;
                    break;

                default:
                    System.out.printf("Invalid number entered!\n");
                    loop = true;
                    break;
            }
        } while (loop);
    }

    private void shippingMenu() {
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("Maintain Shipping company");
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("1. Shipping company List\n");
        System.out.printf("2. Add new shipping company\n");
        System.out.printf("3. Modify shipping company\n");
        System.out.printf("4. Remove shipping company\n");
        System.out.printf("5. Generate Report\n");
        System.out.printf("0. Back to Main Menu\n");
        System.out.printf("\n%s\n", SHORT_LINE);
    }

    private boolean viewShippingCompany(ShippingArrayList<Shipping> shippingList) {

    }
}
