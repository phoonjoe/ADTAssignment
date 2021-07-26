package ClientClass;

import DatabaseClass.Database;
import java.util.Scanner;

/**
 *
 * @author Lee Jun Sian
 */
public class Shipping {

    private Database database;
    private Scanner scan;
    private final String BORDER = "========================================================================================================================================================================================================";
    private final String SHORT_LINE = "=========================";

    public void shipping(Database paraDatabase) {
        scan = new Scanner(System.in);
        this.database = paraDatabase;
    }

    private void shippingMenu() {
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("Maintain Item Group");
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("1. Shipping List\n");
        System.out.printf("2. Add new shipping\n");
        System.out.printf("3. Modify shipping\n");
        System.out.printf("4. Remove shipping\n");
        System.out.printf("5. Generate Report\n");
        System.out.printf("0. Back to Main Menu\n");
        System.out.printf("\n%s\n", SHORT_LINE);
    }
}
