
import DatabaseClass.Database;
import EntityClass.*;
import ImplementationClass.*;
import java.util.Scanner;

/**
 *
 * @author Gan Hao Xian
 */
public class MaintainItemGroup {

    private Staff loginStaff;
    private Database database;
    private Scanner scan;
    private final String BORDER = "========================================================================================================================================================================================================";
    private final String SHORT_LINE = "=========================";

    public void maintainItemGroup(Database paraDatabase, Staff paraLoginStaff) {
        scan = new Scanner(System.in);
        this.loginStaff = paraLoginStaff;
        this.database = paraDatabase;

        boolean itemGroupOperation = false;

        do {
            ItemGroupList<ItemGroup> itemGroupList = database.getItemGroupList();
            MaintainItemGroupMenu();
            System.out.print("Enter (1->5) to move on  >");
            int MaintainItemGroupSelect = scan.nextInt();
            scan.nextLine();
            switch (MaintainItemGroupSelect) {
                case 1:
                    if (itemGroupList.getTotalNumOfIndex() > 0) {
                        itemGroupOperation = viewItemGroup(itemGroupList);
                    } else {
                        System.out.print("Item group list is empty now!");
                        itemGroupOperation = false;
                    }
                    break;
                case 2:
                    itemGroupOperation = addItemGroup(itemGroupList);

                    break;
                case 3:
                    if (itemGroupList.getTotalNumOfIndex() > 0) {
                        itemGroupOperation = modifyItemGroup(itemGroupList);
                    } else {
                        System.out.print("Item group list is empty now!");
                        itemGroupOperation = false;
                    }
                    break;
                case 4:
                    if (itemGroupList.getTotalNumOfIndex() > 0) {
                        itemGroupOperation = removeItemGroup(itemGroupList);
                    } else {
                        System.out.print("Item group list is empty now!");
                        itemGroupOperation = false;
                    }
                    break;
                case 5:
                    itemGroupOperation = false;
                    break;
                default:
                    System.out.print("Invalid number entered");
                    itemGroupOperation = true;
                    break;
            }
        } while (itemGroupOperation);

    }

    private void MaintainItemGroupMenu() {
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("Maintain Item Group");
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("1. Item Group List\n");
        System.out.printf("2. Add Item Group\n");
        System.out.printf("3. Modify Item Group\n");
        System.out.printf("4. Remove Item Group\n");
        System.out.printf("5. Back to Main Menu\n");
        System.out.printf("\n%s\n", SHORT_LINE);
    }

    private boolean viewItemGroup(ItemGroupList<ItemGroup> itemGroupList) {
        int num = 0;
        int page = 1;
        int maxpage = 0;
        boolean back = false;
        boolean searchLoop = false;
        boolean pageLoop = false;

        System.out.printf("\n%s\n\n", BORDER);
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.print("1. View Item Group List\n");
        System.out.print("2. Search Specific Item Group by keyword\n");
        System.out.print("3. Back \n");
        System.out.printf("%s\n", SHORT_LINE);
        System.out.printf("Which way to view Item Group List > ");
        int viewOption = scan.nextInt();
        scan.nextLine();
        switch (viewOption) {
            case 1:
                do {
                    pageLoop = false;
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.print("How many item groups you want to check per page?(3/6/9) >");
                    int limit = scan.nextInt();
                    scan.nextLine();
                    switch (limit) {
                        case 3:
                        case 6:
                        case 9:
                            boolean pageLoop2 = false;
                            do {
                                itemGroupListHeader();
                                if (itemGroupList.getTotalNumOfIndex() % limit != 0) {
                                    maxpage = (itemGroupList.getTotalNumOfIndex() / limit) + 1;
                                } else {
                                    maxpage = itemGroupList.getTotalNumOfIndex() / limit;
                                }

                                for (int i = 1; i <= itemGroupList.getTotalNumOfIndex(); i++) {
                                    ItemGroup itemGroup = itemGroupList.view(i);
                                    System.out.printf("%d. %8s %17s %20.2f %12d \n", ++num, itemGroup.getId(), itemGroup.getName(), itemGroup.getPrice(), itemGroup.getMinimuimBuyer());
                                    if (i % limit == 0 || i == itemGroupList.getTotalNumOfIndex()) {
                                        System.out.printf("\n\t\t\t\t\t\tPg%d / %d", page, maxpage);
                                    } else if (i == itemGroupList.getTotalNumOfIndex() && i < limit) {
                                        System.out.printf("\n\t\t\t\t\t\tPg%d / %d", page, maxpage);
                                    }
                                    if (i % limit == 0 || i == itemGroupList.getTotalNumOfIndex()) {
                                        System.out.printf("\n%s\n", SHORT_LINE);
                                        if (page > 1 && i != itemGroupList.getTotalNumOfIndex()) {
                                            System.out.print("1. Next Page\n");
                                            System.out.print("2. Previous Page\n");
                                            System.out.print("0. Back\n");
                                        } else if (i == itemGroupList.getTotalNumOfIndex()) {
                                            if (page == 1) {
                                                System.out.print("0. Back\n");
                                            } else {
                                                System.out.print("1. Previous Page\n");
                                                System.out.print("0. Back\n");
                                            }

                                        } else {
                                            System.out.print("1. Next Page\n");
                                            System.out.print("0. Back\n");
                                        }

                                        System.out.print("Enter your option >");
                                        int nextPage = scan.nextInt();
                                        scan.nextLine();
                                        switch (nextPage) {
                                            case 0:
                                                pageLoop2 = false;
                                                i = itemGroupList.getTotalNumOfIndex();
                                                break;
                                            case 1:
                                                if (maxpage > 1) {
                                                    if (i == itemGroupList.getTotalNumOfIndex()) {
                                                        num = num - previousPage(i, limit);;
                                                        i = i - previousPage(i, limit);
                                                        itemGroupListHeader();
                                                        page--;
                                                        break;
                                                    } else {
                                                        itemGroupListHeader();
                                                        page++;
                                                        break;
                                                    }
                                                } else {
                                                    System.out.print("\n************************\n");
                                                    System.out.print("Invalid number entered!\n");
                                                    System.out.print("************************\n\n");
                                                    pageLoop2 = true;
                                                    i = itemGroupList.getTotalNumOfIndex();
                                                    page = 1;
                                                    num = 0;
                                                    break;
                                                }
                                            case 2:
                                                if (page > 1 && page != maxpage) {
                                                    num = num - previousPage(i, limit);
                                                    i = i - previousPage(i, limit);
                                                    itemGroupListHeader();
                                                    page--;
                                                    break;
                                                } else {
                                                    System.out.print("\n************************\n");
                                                    System.out.print("Invalid number entered!\n");
                                                    System.out.print("************************\n\n");
                                                    pageLoop2 = true;
                                                    i = itemGroupList.getTotalNumOfIndex();
                                                    page = 1;
                                                    num = 0;
                                                    break;
                                                }
                                            default:
                                                System.out.print("\n************************\n");
                                                System.out.print("Invalid number entered!\n");
                                                System.out.print("************************\n\n");
                                                pageLoop2 = true;
                                                i = itemGroupList.getTotalNumOfIndex();
                                                page = 1;
                                                num = 0;
                                                break;
                                        }
                                    }

                                }
                            } while (pageLoop2);

                            break;
                        default:
                            System.out.print("\n************************\n");
                            System.out.print("Invalid number entered!\n");
                            System.out.print("************************\n\n");
                            pageLoop = true;
                    }

                } while (pageLoop);
                back = true;
                break;
            case 2:
                do {
                    System.out.printf("\n\n%s\n", SHORT_LINE);
                    System.out.print("Enter keyword to search item group > ");
                    String keyword = scan.nextLine();
                    searchItemGroup(keyword, itemGroupList, 'V');
                    System.out.printf("\n\n%s\n", SHORT_LINE);
                    System.out.print("Search Again? (Y/N)> ");
                    char searchAgain = scan.nextLine().toUpperCase().charAt(0);
                    if (searchAgain == 'Y') {
                        searchLoop = true;
                    } else if (searchAgain == 'N') {
                        searchLoop = false;
                    } else {
                        System.out.printf("\n********************************************\n");
                        System.out.print("Invalid input! Back to maintain item group menu.\n");
                        System.out.printf("********************************************\n");
                        searchLoop = false;
                    }

                } while (searchLoop);
                back = true;
                break;
            case 3:
                back = true;
                break;
            default:
                System.out.print("\n********************************************\n");
                System.out.print("Invalid number entered! Back to the maintain item group menu.\n");
                System.out.print("********************************************\n");
                back = true;
                break;
        }
        return back;

    }

    private boolean addItemGroup(ItemGroupList<ItemGroup> itemGroupList) {

        boolean addLoop = false;
        boolean doubleConfirm = false;

        do {
            addLoop = false;
            System.out.printf("\n%s\n\n", BORDER);
            System.out.print("Enter new item group name >");
            String itemGroupName = scan.nextLine();
            for (int i = 1; i <= itemGroupList.getTotalNumOfIndex(); i++) {
                ItemGroup existItemGroup = itemGroupList.view(i);
                if (itemGroupName.toLowerCase().equals(existItemGroup.getName().toLowerCase())) {
                    System.out.printf("  *******************\n");
                    System.out.printf("* Item already exist! *\n");
                    System.out.printf("  *******************\n");
                    addLoop = true;
                    break;
                }
            }
            if (addLoop == false) {
                System.out.print("Enter price of this item group >");
                double itemGroupPrice = scan.nextDouble();
                scan.nextLine();
                System.out.print("Enter minumum buyer of this item group >");
                int itemGroupMinBuyer = scan.nextInt();
                scan.nextLine();

                do {

                    System.out.print("\nBelow is the details of the new item group");
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.printf("%s %17s %20s %16s \n", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
                    System.out.printf("%s %17s %20s %16s \n", "-----", "------------", "---------", "---------");
                    System.out.printf("IG%03d %17s %20.2f %12d \n", itemGroupList.getTotalNumOfIndex() + 1, itemGroupName, itemGroupPrice, itemGroupMinBuyer);
                    System.out.printf("%s\n", SHORT_LINE);
                    System.out.print("Confirm add item group? (Y/N): ");
                    char confirmAdd = scan.nextLine().toUpperCase().charAt(0);
                    if (confirmAdd == 'Y') {
                        ItemGroup newItemGroup = new ItemGroup(itemGroupName, itemGroupPrice, itemGroupMinBuyer);
                        database.addIntoItemGroupList(newItemGroup);
                        doubleConfirm = false;
                    } else if (confirmAdd == 'N') {
                        doubleConfirm = false;
                    } else {
                        System.out.print("************************\n");
                        System.out.print("Invalid input entered!\n");
                        System.out.print("************************\n");
                        doubleConfirm = true;
                    }
                } while (doubleConfirm);
            }
            System.out.print("Continue add item group? (Y/N): ");
            char continueAdd = scan.nextLine().toUpperCase().charAt(0);
            if (continueAdd == 'Y') {
                addLoop = true;
            } else if (continueAdd == 'N') {
                addLoop = false;
            } else {
                System.out.print("\n********************************************\n");
                System.out.print("Invalid input entered! Back to maintain item group menu.\n");
                System.out.print("********************************************\n");
                addLoop = false;

            }

        } while (addLoop);
        return true;
    }

    private boolean modifyItemGroup(ItemGroupList<ItemGroup> itemGroupList) {
        boolean modifyLoop = false;
        boolean confirmLoop = false;

        do {
            int position = 1;
            modifyLoop = false;
            System.out.printf("\n%s\n\n", BORDER);
            System.out.printf("\n\n%s\n", SHORT_LINE);
            System.out.print("Enter keyword to search item group > ");
            String keyword = scan.nextLine();
            ItemGroup itemGroup = searchItemGroup(keyword, itemGroupList, 'M');

            if (itemGroup != null) {
                for (; position <= itemGroupList.getTotalNumOfIndex(); position++) {
                    ItemGroup compareItemGroup = itemGroupList.view(position);
                    if (itemGroup.getId().equals(compareItemGroup.getId())) {
                        break;
                    }
                }

                System.out.print("\nBelow is the details of the existing item group");
                System.out.printf("\n%s\n", SHORT_LINE);
                System.out.printf("%s %17s %20s %16s \n", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
                System.out.printf("%s %17s %20s %16s \n", "-----", "------------", "---------", "---------");
                System.out.printf("%s %17s %20.2f %12d \n", itemGroup.getId(), itemGroup.getName(), itemGroup.getPrice(), itemGroup.getMinimuimBuyer());
                System.out.printf("%s\n", SHORT_LINE);

                System.out.print("Enter new item group name >");
                String newName = scan.nextLine();

                for (int i = 1; i <= itemGroupList.getTotalNumOfIndex(); i++) {
                    modifyLoop = false;
                    ItemGroup existItemGroup = itemGroupList.view(i);
                    if (newName.toLowerCase().equals(existItemGroup.getName().toLowerCase())) {
                        System.out.printf("  ******************\n");
                        System.out.printf("* Item already exist! *\n");
                        System.out.printf("* ******************\n");
                        modifyLoop = true;
                        break;
                    }
                }
                if (modifyLoop == false) {
                    System.out.print("Enter price of this item group >");
                    double newPrice = scan.nextDouble();
                    scan.nextLine();
                    System.out.print("Enter minumum buyer of this item group >");
                    int newMinBuyer = scan.nextInt();
                    scan.nextLine();

                    do {

                        System.out.print("\nBelow is the details of the modified item group");
                        System.out.printf("\n%s\n", SHORT_LINE);
                        System.out.printf("%s %17s %20s %16s \n", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
                        System.out.printf("%s %17s %20s %16s \n", "-----", "------------", "---------", "---------");
                        System.out.printf("%s %17s %20.2f %12d \n", itemGroup.getId(), newName, newPrice, newMinBuyer);
                        System.out.printf("\n%s\n", SHORT_LINE);
                        System.out.print("Confirm apply this modify  to the item group? (Y/N): ");
                        char confirmModify = scan.nextLine().toUpperCase().charAt(0);
                        if (confirmModify == 'Y') {
                            itemGroup.setName(newName);
                            itemGroup.setPrice(newPrice);
                            itemGroup.setMinimuimBuyer(newMinBuyer);
                            database.modifyItemGroupList(position, itemGroup);
                            confirmLoop = false;
                        } else if (confirmModify == 'N') {

                            confirmLoop = false;
                        } else {
                            System.out.print("\n********************************************\n");
                            System.out.print("Invalid input entered!\n");
                            System.out.print("********************************************\n");
                            confirmLoop = true;

                        }
                    } while (confirmLoop);
                }
            }
            System.out.print("Continue modify another item group? (Y/N): ");
            char continueModify = scan.nextLine().toUpperCase().charAt(0);
            if (continueModify == 'Y') {
                modifyLoop = true;
            } else if (continueModify == 'N') {
                modifyLoop = false;
            } else {
                System.out.print("\n********************************************\n");
                System.out.print("Invalid input entered! Back to maintain item group menu.\n");
                System.out.print("********************************************\n");
                modifyLoop = false;

            }

        } while (modifyLoop);
        return true;
    }

    private boolean removeItemGroup(ItemGroupList<ItemGroup> itemGroupList) {
        boolean removeLoop = false;
        boolean confirmLoop = false;
        int position = 1;
        do {
            removeLoop = false;
            System.out.printf("\n%s\n\n", BORDER);
            System.out.printf("\n\n%s\n", SHORT_LINE);
            System.out.print("Enter keyword to search item group > ");
            String keyword = scan.nextLine();
            ItemGroup itemGroup = searchItemGroup(keyword, itemGroupList, 'M');

            if (itemGroup != null) {
                for (; position <= itemGroupList.getTotalNumOfIndex(); position++) {
                    ItemGroup compareItemGroup = itemGroupList.view(position);
                    if (itemGroup.getId().equals(compareItemGroup.getId())) {
                        break;
                    }
                }
                do {

                    System.out.print("\nBelow is the details of the item group that you want to delete");
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.printf("%s %17s %20s %16s \n", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
                    System.out.printf("%s %17s %20s %16s \n", "-----", "------------", "---------", "---------");
                    System.out.printf("%s %17s %20.2f %12d \n", itemGroup.getId(), itemGroup.getName(), itemGroup.getPrice(), itemGroup.getMinimuimBuyer());
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.print("Confirm remove this item group? (Y/N): ");
                    char confirmModify = scan.nextLine().toUpperCase().charAt(0);
                    if (confirmModify == 'Y') {
                        database.removeFromItemGroupList(position);
                        confirmLoop = false;
                    } else if (confirmModify == 'N') {
                        confirmLoop = false;
                    } else {
                        System.out.print("\n********************************************\n");
                        System.out.print("Invalid input entered! Back to maintain item group menu.\n");
                        System.out.print("********************************************\n");
                        confirmLoop = true;

                    }
                } while (confirmLoop);
            }
            System.out.print("Continue remove another item group? (Y/N): ");
            char continueModify = scan.nextLine().toUpperCase().charAt(0);
            if (continueModify == 'Y') {
                removeLoop = true;
            } else if (continueModify == 'N') {
                removeLoop = false;
            } else {
                System.out.print("\n********************************************\n");
                System.out.print("Invalid input entered! Back to maintain item group menu.\n");
                System.out.print("********************************************\n");
                removeLoop = false;

            }

        } while (removeLoop);
        return true;
    }

    private ItemGroup searchItemGroup(String keyword, ItemGroupList<ItemGroup> itemGroupList, char operation) {

        ItemGroupList<ItemGroup> itemGroupListByKeyword = new ItemGroupList<>(itemGroupList.getTotalNumOfIndex());

        int numItemGroup = itemGroupList.getTotalNumOfIndex();
        int no = 0;
        String msg = String.format("%s\n", SHORT_LINE);
        msg += String.format("Item Group List Included Keyword");
        msg += String.format("\n%s\n", SHORT_LINE);
        msg += String.format("%3s %7s %17s %20s %16s \n", "No.", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
        msg += String.format("%3s %7s %17s %20s %16s \n", "---", "-----", "------------", "---------", "---------");

        for (int position = 1; position <= numItemGroup; position++) {
            ItemGroup itemGroup = itemGroupList.view(position);
            if (itemGroup.getName().toLowerCase().contains(keyword.toLowerCase())) {
                itemGroupListByKeyword.add(itemGroup);
                msg += String.format("%d. %8s %17s %20.2f %12d \n", ++no, itemGroup.getId(), itemGroup.getName(), itemGroup.getPrice(), itemGroup.getMinimuimBuyer());
            }
        }

        if (no == 0) {

            System.out.printf("\n*******************\n");
            System.out.printf("* Item not found! *\n");
            System.out.printf("*******************\n");

        } else {
            System.out.print(msg);
        }
        if (operation == 'M' || operation == 'R') {
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n"
                    + "# 1->%-3d (Select item group)\n"
                    + "# Others (Back)\n"
                    + "> ", no);
            int position = scan.nextInt();
            scan.nextLine();
            if (position <= no && position > 0) {

                return itemGroupListByKeyword.view(position);
            } else {
                System.out.printf("\n*******************\n");
                System.out.printf("* Invalid input entered! *\n");
                System.out.printf("*******************\n");
                return null;
            }
        } else {
            return null;
        }

    }

    private void itemGroupListHeader() {
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("Item Group List");
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("%3s %7s %17s %20s %16s \n", "No.", "ID   ", "Product Name", "Price(RM)", "Min Buyer");
        System.out.printf("%3s %7s %17s %20s %16s \n", "---", "-----", "------------", "---------", "---------");
    }

    private int previousPage(int i, int limit) {
        int ans = 0;
        int count = i % limit;
        switch (count) {
            case 0:
                ans = limit * 2;
                break;
            case 1:
                ans = limit + 1;
                break;
            case 2:
                ans = limit + 2;
                break;

        }
        return ans;
    }

}
