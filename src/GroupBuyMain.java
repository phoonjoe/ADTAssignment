
import DatabaseClass.Database;
import EntityClass.*;
import ImplementationClass.*;
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
public class GroupBuyMain {

    private Customer loginCustomer;
    private Database database;
    private Scanner scan;
    private final String BORDER = "========================================================================================================================================================================================================";
    private final String SHORT_LINE = "=========================";

    public void groupBuyMain(int menu, Database paraDatabase, Customer paraLoginCustomer) {
        scan = new Scanner(System.in);
        this.loginCustomer = paraLoginCustomer;
        this.database = paraDatabase;
        boolean backToMenu = false;
        boolean backToSelectItemGroups = false;
        boolean backToInvolvedGroups = false;
        do {
            checkTimeLeft();
            switch (menu) {
                case 1:
                    do {
                        System.out.printf("\n%s\n", SHORT_LINE);
                        System.out.print("Search item group name: ");
                        String keyword = scan.nextLine();

                        ItemGroup itemGroup = selectFoundItemGroups(keyword);
                        if (itemGroup != null) {
                            backToMenu = selectOrders(itemGroup);
                        } else {
                            backToSelectItemGroups = false;
                            backToMenu = true;
                        }
                    } while (backToSelectItemGroups);
                    break;
                case 2:
                    do {
                        int involvedGroupMenu = selectInvolvedGroupMenu();
                        switch (involvedGroupMenu) {
                            case 0:
                                backToInvolvedGroups = false;
                                backToMenu = true;
                                break;
                            case 1:
                                backToInvolvedGroups = displayCreatedGroups();
                                break;
                            case 2:
                                backToInvolvedGroups = displayJoinedGroups();
                                break;
                            default:
                                backToInvolvedGroups = true;
                                System.out.printf("\n******************************************************\n");
                                System.out.printf("* Invalid number! Please enter number between 0 to 2 *\n");
                                System.out.printf("******************************************************\n");
                        }
                    } while (backToInvolvedGroups);
            }
        } while (!backToMenu);
    }

    private void checkTimeLeft() {
        OrderArrayList<Order> orderList = database.getOrderList();
        for (int position = 1; position <= database.getOrderList().size(); position++) {
            Order order = database.getOrderList().viewElement(position);
            if (order.calculateTimeLeft() <= 0 && !order.getStatus().equals("Completed")) {
                database.getOrderList().removeElement(position);
            }
        }
    }

    private boolean displayCreatedGroups() {
        int no = 0;
        OrderArrayList<Order> databaseOrderList = database.getOrderList();
        OrderArrayList<Order> createdOrderListByLoginAccount = new OrderArrayList<>(databaseOrderList.size());
        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   CREATED GROUPS   #\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        msg += String.format("%3s %15s %18s %49s %42s %18s %18s %18s %10s\n", "No.", "OrderID", "Product Name", "Collect Address", "Min Buyer", "Buyer Joined", "Total/Buyer(RM)", "Time Left(hour)", "Status");
        msg += String.format("%3s %15s %18s %72s %19s %18s %18s %18s %11s\n", "---", "-------", "------------", "----------------------------------------------------------", "---------", "------------", "---------------", "---------------", "--------");

        for (int position = 1; position <= databaseOrderList.size(); position++) {
            Order order = databaseOrderList.viewElement(position);
            ItemGroup itemGroup = order.getItemGroup();
            Customer leader = order.getOrderMembers().getMember();
            if (leader.getId().equals(loginCustomer.getId())) {
                createdOrderListByLoginAccount.add(order);
                msg += String.format("%d. %15s %18s %71s %16d %15d/100 %17s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembers().getMemberAmount(), itemGroup.getPrice() + ((order.getShipping() == null) ? 0 : order.getShipping().getFee()), order.calculateTimeLeft(), order.getStatus());
            }
        }
        if (no == 0) {
            System.out.printf("\n");
            System.out.printf("****************************\n");
            System.out.printf("* Created group not found! *\n");
            System.out.printf("****************************\n");
            return true;
        } else {
            System.out.printf(msg);
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n");
            if (no == 1) {
                System.out.printf("# 1      (Select one group for details view)\n");
            } else {
                System.out.printf("# 1->%-3d (Select one group for details view)\n", no);
            }
            System.out.printf("# Others (Back)\n> ");
            int position = scan.nextInt();
            scan.nextLine();
            if (position <= no && position > 0) {
                Order selectedOrder = createdOrderListByLoginAccount.viewElement(position);
                displayDetailsOrder(selectedOrder);
                if (selectedOrder.getStatus().contains("Active")) {
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.printf("Enter a number:\n"
                            + "-------------------------\n");
                    System.out.printf("# 1      (Complete order)\n");
                    System.out.printf("# Others (Back)\n> ");
                    int complete = scan.nextInt();
                    scan.nextLine();
                    if (complete == 1) {
                        System.out.printf("\nAre you sure you want to complete this order, confirm(Y/N)? ");
                        if (scan.nextLine().toUpperCase().charAt(0) == 'Y') {

                            selectedOrder.completeOrder(selectCourierService());

                            displayDetailsOrder(selectedOrder);
                            System.out.printf("Press ANY key to continue...");
                            scan.nextLine();
                            return false;
                        }
                    }

                } else {
                    System.out.printf("Press ANY key back to continue...");
                    scan.nextLine();
                }

            }
        }
        return true;
    }

    private Shipping selectCourierService() {
        int no = 0;
        boolean wrongPosition = true;
        ShippingArrayList<Shipping> databaseShippingList = database.getShippingList();
        System.out.printf("%s\n", BORDER);
        System.out.printf("%3s %18s %18s %18s\n", "No.", "Courier Service", "Estimated Delivery Day(s)", "Calculated Fee");
        System.out.printf("%3s %18s %18s %18s\n", "---", "---------------", "-------------------------", "--------------");
        for (int position = 1; position <= databaseShippingList.getNumberOfShipping(); position++) {
            Shipping shipping = databaseShippingList.readShipping(position);
            System.out.printf("%d. %13s %20s %25.2f\n", ++no, shipping.getName(), shipping.getDeliveryTime(), shipping.getFee());
        }
        do {
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n");
            if (no == 1) {
                System.out.printf("# 1      (Select courier service)\n>");
            } else {
                System.out.printf("# 1->%-3d (Select courier service)\n>", no);
            }
            int position = scan.nextInt();
            scan.nextLine();
            if (position <= no && position > 0) {
                System.out.printf("\nYou selected '%s' as your courier service, confirm(Y/N)? ", databaseShippingList.readShipping(position).getName());
                if (scan.nextLine().toUpperCase().charAt(0) == 'Y') {
                    wrongPosition = false;
                    return databaseShippingList.readShipping(position);
                }
            } else {
                System.out.printf("******************************************************\n");
                System.out.printf("* Invalid number! Please enter again between 1 to %d *\n", no);
                System.out.printf("******************************************************\n");
            }
        } while (wrongPosition);
        return null;
    }

    private boolean displayJoinedGroups() {
        int no = 0;
        OrderArrayList<Order> databaseOrderList = database.getOrderList();
        OrderArrayList<Order> joinedOrderListByLoginAccount = new OrderArrayList<>(databaseOrderList.size());
        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   JOINED GROUPS   #\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        msg += String.format("%3s %15s %18s %46s %42s %18s %17s %20s %11s\n", "No.", "OrderID", "Product Name", "Collect Address", "Buyer Joined", "Total/Buyer(RM)", "Created date", "Time Left(hour)", "Status");
        msg += String.format("%3s %15s %18s %68s %20s %18s %19s %18s %13s\n", "---", "-------", "------------", "----------------------------------------------------------", "-------------", "---------------", "----------------", "----------------", "---------");
        for (int position = 1; position <= databaseOrderList.size(); position++) {
            Order order = databaseOrderList.viewElement(position);
            CustomerArrayList<Customer> orderMembers = order.getOrderMembers();
            ItemGroup itemGroup = order.getItemGroup();
            Customer leader = order.getOrderMembers().getMember();
            for (int membersPosition = 2; membersPosition <= orderMembers.getMemberAmount(); membersPosition++) {
                if (orderMembers.getMember(membersPosition).getId().equals(loginCustomer.getId())) {
                    joinedOrderListByLoginAccount.add(order);
                    msg += String.format("%d. %15s %18s %68s %14d/100 %16.2f %24s %12s %19s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), order.getOrderMembers().getMemberAmount(), itemGroup.getPrice() + ((order.getShipping() == null) ? 0 : order.getShipping().getFee()), order.getDateInFormat(order.getCreateDate()), order.calculateTimeLeft(), order.getStatus());
                }
            }
        }
        if (no == 0) {
            System.out.printf("\n");
            System.out.printf("***************************\n");
            System.out.printf("* Joined group not found! *\n");
            System.out.printf("***************************\n");
            return true;
        } else {
            System.out.print(msg);
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n");

            if (no == 1) {
                System.out.printf("# 1      (Select one group for details view)\n");
            } else {
                System.out.printf("# 1->%-3d (Select one group for details view)\n", no);
            }
            System.out.printf("# Others (Back)\n> ");
            int position = scan.nextInt();
            scan.nextLine();
            if (position <= no && position > 0) {
                displayDetailsOrder(joinedOrderListByLoginAccount.viewElement(position));
                System.out.printf("Press ANY key to continue...");
                scan.nextLine();
                return false;
            } else {
                return true;
            }
        }
    }

    private int selectInvolvedGroupMenu() {
        System.out.printf("\n%s\n", SHORT_LINE);
        System.out.printf("Select Related Groups:\n"
                + "-------------------------\n"
                + "1. Created Groups/Orders\n"
                + "2. Joined Groups/Orders\n"
                + "0. Back\n"
                + "> ");
        return scan.nextInt();
    }

    private ItemGroup selectFoundItemGroups(String keyword) {
        ItemGroupList<ItemGroup> databaseItemGroupList = database.getItemGroupList();
        ItemGroupList<ItemGroup> itemGroupListByKeyword = new ItemGroupList<>(databaseItemGroupList.getTotalNumOfIndex());

        int numItemGroup = databaseItemGroupList.getTotalNumOfIndex();
        int no = 0;

        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   SEARCH ITEM   #\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        msg += String.format("%3s %16s %20s %16s \n", "No.", "Min Buyer", "Product Name", "Price(RM)");
        msg += String.format("%3s %16s %20s %16s \n", "---", "---------", "------------", "---------");

        for (int position = 1; position <= numItemGroup; position++) {
            ItemGroup itemGroup = databaseItemGroupList.view(position);
            if (itemGroup.getName().toLowerCase().contains(keyword.toLowerCase())) {
                itemGroupListByKeyword.add(itemGroup);
                msg += String.format("%d. %13d %24s %16.2f\n", ++no, itemGroup.getMinimuimBuyer(), itemGroup.getName(), itemGroup.getPrice());
            }
        }

        if (no == 0) {
            System.out.printf("\n");
            System.out.printf("*******************\n");
            System.out.printf("* Item not found! *\n");
            System.out.printf("*******************\n");
            return null;
        } else {
            System.out.print(msg);
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n"
                    + "# 1->%-3d (Select item group)\n"
                    + "# Others (Back)\n"
                    + "> ", no);
            int position = scan.nextInt();
            if (position <= no && position > 0) {
                return databaseItemGroupList.view(position);
            } else {
                return null;
            }
        }

    }

    private boolean selectOrders(ItemGroup itemGroup) {
        int no = 0;
        OrderArrayList<Order> databaseOrderList = database.getOrderList();
        OrderArrayList<Order> orderListByItemGroupId = new OrderArrayList<>(databaseOrderList.size());
        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   ITEM: %-15s   #\n", "Tarkov POE Foever".toUpperCase());
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        msg += String.format("%3s %16s %50s %45s %18s\n", "No.", "Leader", "Collect Address", "Time Left(hour)", "Buyer Joined");
        msg += String.format("%3s %18s %70s %23s %20s\n", "---", "-----------", "----------------------------------------------------------", "---------------", "---------------");

        for (int position = 1; position <= databaseOrderList.size(); position++) {
            Order order = databaseOrderList.viewElement(position);
            if (order.getItemGroup().getId().equals(itemGroup.getId()) && !order.getStatus().contains("Completed") && order.getOrderMembers().getMemberAmount() < 100) {
                boolean foundMembers = false;
                for (int membersPosition = 1; membersPosition <= order.getOrderMembers().getMemberAmount(); membersPosition++) {
                    if (order.getOrderMembers().getMember(membersPosition).getId().equals(loginCustomer.getId())) {
                        foundMembers = true;
                    }
                }
                if (!foundMembers) {
                    orderListByItemGroupId.add(order);
                    Customer leader = order.getOrderMembers().getMember();
                    msg += String.format("%d. %18s %70s %17d %18d/100\n", ++no, leader.getName(), leader.getAddress(), order.calculateTimeLeft(), order.getOrderMembers().getMemberAmount());
                }
            }
        }

        if (no == 0) {
            System.out.printf("\n");
            System.out.printf("****************************\n");
            System.out.printf("* No Active Group To Join! *\n");
            System.out.printf("****************************\n");
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n"
                    + "# %-6d (Create new group)\n"
                    + "# Others (Back)\n"
                    + "> ", -1);
        } else {
            System.out.print(msg);
            System.out.printf("\n%s\n", SHORT_LINE);
            System.out.printf("Enter a number:\n"
                    + "-------------------------\n");
            if (no == 1) {
                System.out.printf("# 1      (Join current group)\n");
            } else {
                System.out.printf("# 1->%-3d (Join current group)\n", no);
            }
            System.out.printf("# %-6d (Create new group)\n"
                    + "# Others (Back)\n"
                    + "> ", -1);
        }
        int position = scan.nextInt();
        scan.nextLine();
        if (position <= no && position > 0) {
            database.updateOrderByAddNewMember(orderListByItemGroupId.viewElement(position).getId(), loginCustomer);
            displayDetailsOrder(orderListByItemGroupId.viewElement(position));
        } else if (position == -1) {
            Order newOrder = new Order(itemGroup, loginCustomer);
            database.addIntoOrderList(newOrder);
            displayDetailsOrder(newOrder);
        }

        if (position > 0 && position <= no || position == -1) {
            System.out.printf("Press ANY key to continue...");
            scan.nextLine();
        }

        return true;
    }

    public void displayDetailsOrder(Order order) {
        Customer leader = order.getOrderMembers().getMember();
        ItemGroup itemGroup = order.getItemGroup();
        System.out.printf("%s\n", BORDER);
        System.out.printf("\t\t\t\t\t\t\t\t\t       Group Buying Details                                     Order No\t: %s\n", order.getId());
        System.out.printf("\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        String detailMsg = String.format("Leader\t\t\t: %-95s \t\tStatus\t\t: %s\n"
                + "Collect Address\t\t: %-95s \t\tTime Left\t: %02d hour(s)\n"
                + "Contact Number\t\t: %-95s \t\tEnd Date\t: ", leader.getName(), order.getStatus(), leader.getAddress(), order.calculateTimeLeft(), leader.getPhoneNumber());

        if (order.getStatus().contains("Completed")) {
            detailMsg += String.format("%s\n", order.getDateInFormat(order.getEndDate()));
        } else {
            detailMsg += String.format("%s\n", "-");
        }

        detailMsg += String.format("Product Name\t\t: %-95s \t\tCreate Date\t: %s\n"
                + "Minimum Buyer Needed\t: %d\n"
                + "Buyer Joined\t\t: %d/100\n", itemGroup.getName(), order.getDateInFormat(order.getCreateDate()), itemGroup.getMinimuimBuyer(), order.getOrderMembers().getMemberAmount());

        detailMsg += (String.format("%s\n"
                + "SubTotal\t\t: RM%.2f (%.2f*%d)\n", "--------------------------------------------------", itemGroup.getPrice() * order.getQuantity(), itemGroup.getPrice(), order.getQuantity()));

        if (order.getShipping() != null) {
            detailMsg += (String.format("Shipping Fee\t\t: RM%.2f\n"
                    + "Total\t\t\t: RM%.2f\n"
                    + "Total/Buyer\t\t: RM%.2f\n", order.getShipping().calculateFee(order.getQuantity()), order.getTotal(), itemGroup.getPrice() + order.getShipping().getFee()));
        }

        System.out.print(detailMsg);
    }
}
