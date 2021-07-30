package ClientClass;

import DatabaseClass.Database;
import EntityClass.*;
import ImplementationClass.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class GroupBuyClient {

    private Customer loginCustomer;
    private Staff loginStaff;
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

    public void readOrderByDate(Database paraDatabase) {
        scan = new Scanner(System.in);
        this.database = paraDatabase;

        OrderArrayList<Order> orderList = database.getOrderList();
        OrderArrayList<Order> foundOrderList = new OrderArrayList<>(orderList.size());

        boolean loop = true;

        try {
            int no = 0;
            Date firstDate = new Date(1);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            System.out.printf("\nEnter start date (dd/MM/yyyy): ");
            String startDateInString = scan.nextLine();
            System.out.printf("Enter end   date (dd/MM/yyyy): ");
            String endDateInString = scan.nextLine();

            Date startDate = format.parse(startDateInString);
            Date endDate = format.parse(endDateInString);
            endDate = new Date(endDate.getTime() + 86399999);

            if (startDate.compareTo(firstDate) < 0 || endDate.compareTo(firstDate) < 0) {
                System.out.printf("\n*******************************************\n");
                System.out.printf("* The date need to start from: %s *\n", format.format(firstDate));
                System.out.printf("*******************************************\n");
            } else {
                System.out.printf("\n[ DIRECTLY PRESS 'ENTER' TO SKIP ]\n");
                System.out.printf("Customer ID: ");
                String customerId = scan.nextLine();

                System.out.printf("\n[ DIRECTLY PRESS 'ENTER' TO SKIP ]\n");
                System.out.printf("Item ID: ");
                String itemId = scan.nextLine();

                String msg = String.format("\n\n%s\n", BORDER);
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  #   ORDER(S)(%10s - %10s)   #\n", format.format(startDate), format.format(endDate));
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                msg += String.format("%s\n", BORDER);
                msg += String.format("%3s %15s %18s %49s %42s %18s %13s %23s %10s\n", "No.", "OrderID", "Product Name", "Collect Address", "Min Buyer", "Buyer Joined", "Total", "Time Left(hour)", "Status");
                msg += String.format("%3s %15s %18s %72s %19s %18s %18s %18s %11s\n", "---", "-------", "------------", "----------------------------------------------------------", "---------", "------------", "---------------", "---------------", "--------");

                for (int orderListPosition = 1; orderListPosition <= orderList.size(); orderListPosition++) {
                    Order order = orderList.viewElement(orderListPosition);
                    if (order.getEndDate().compareTo(startDate) >= 0 && order.getEndDate().compareTo(endDate) <= 0) {
                        ItemGroup itemGroup = order.getItemGroup();
                        Customer leader = order.getOrderMembersList().getMember();
                        foundOrderList.add(order);
                        if (!customerId.isEmpty() && itemId.isEmpty()) {
                            for (int orderMemberPosition = 1; orderMemberPosition <= order.getOrderMembersList().getMemberAmount(); orderMemberPosition++) {
                                Customer member = order.getOrderMembersList().getMember(orderMemberPosition);
                                if (customerId.toUpperCase().equals(member.getId().toUpperCase())) {
                                    msg += String.format("%d. %15s %18s %71s %16d %17d/%-3d %15s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), order.getTotal(), order.calculateTimeLeft(), order.getStatus());
                                }
                            }
                        } else if (customerId.isEmpty() && !itemId.isEmpty()) {
                            if (itemId.toUpperCase().equals(order.getItemGroup().getId().toUpperCase())) {
                                msg += String.format("%d. %15s %18s %71s %16d %17d/%-3d %15s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), order.getTotal(), order.calculateTimeLeft(), order.getStatus());
                            }
                        } else if (!customerId.isEmpty() && !itemId.isEmpty()) {
                            if (itemId.toUpperCase().equals(order.getItemGroup().getId().toUpperCase())) {
                                for (int orderMemberPosition = 1; orderMemberPosition <= order.getOrderMembersList().getMemberAmount(); orderMemberPosition++) {
                                    Customer member = order.getOrderMembersList().getMember(orderMemberPosition);
                                    if (customerId.toUpperCase().equals(member.getId().toUpperCase())) {
                                        msg += String.format("%d. %15s %18s %71s %16d %17d/%-3d %15s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), order.getTotal(), order.calculateTimeLeft(), order.getStatus());
                                    }
                                }
                            }
                        } else {
                            msg += String.format("%d. %15s %18s %71s %16d %17d/%-3d %15s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), order.getTotal(), order.calculateTimeLeft(), order.getStatus());
                        }
                    }
                }

                if (no == 0) {
                    boolean foundCustomer = false;
                    boolean foundItem = false;

                    //===========Check if userId exist=============
                    if (!customerId.isEmpty()) {
                        for (int orderListPosition = 1; orderListPosition <= orderList.size(); orderListPosition++) {
                            CustomerArrayList<Customer> customerList = orderList.viewElement(orderListPosition).getOrderMembersList();
                            for (int orderMemberPosition = 1; orderMemberPosition <= customerList.getMemberAmount(); orderMemberPosition++) {
                                Customer customer = customerList.getMember(orderMemberPosition);
                                if (customerId.toUpperCase().equals(customer.getId().toUpperCase())) {
                                    foundCustomer = true;
                                }
                            }
                        }
                        if (!foundCustomer) {
                            System.out.printf("\n*******************\n");
                            System.out.printf("* Invalid User ID *\n");
                            System.out.printf("*******************\n");
                        }
                    } else {
                        foundCustomer = true;
                    }

                    //===========Check if itemId exist=============
                    if (!itemId.isEmpty()) {
                        for (int orderListPosition = 1; orderListPosition <= orderList.size(); orderListPosition++) {
                            Order order = orderList.viewElement(orderListPosition);
                            ItemGroup itemGroup = order.getItemGroup();
                            if (itemId.toUpperCase().equals(itemGroup.getId().toUpperCase())) {
                                foundItem = true;
                            }

                        }
                        if (!foundItem) {
                            System.out.printf("\n*******************\n");
                            System.out.printf("* Invalid Item ID *\n");
                            System.out.printf("*******************\n");
                        }
                    } else {
                        foundItem = true;
                    }

                    if (foundCustomer && foundItem) {
                        System.out.printf("\n**********************************************************\n");
                        System.out.printf("* No sales order found during %s to %s *\n", format.format(startDate), format.format(endDate));
                        System.out.printf("**********************************************************\n");
                    }
                } else {
                    do {
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
                            Order selectedOrder = foundOrderList.viewElement(position);
                            displayDetailsOrder(selectedOrder);

                            System.out.printf("\n%s\n", SHORT_LINE);
                            System.out.printf("Enter a number:\n"
                                    + "-------------------------\n");
                            System.out.printf("# 1      (View Related User(s) Details)\n");
                            System.out.printf("# Others (Back)\n> ");
                            int reply = scan.nextInt();
                            scan.nextLine();
                            if (reply == 1) {
                                displayJoinedBuyer(selectedOrder.getOrderMembersList());
                                System.out.printf("Press ANY key back to continue...");
                                scan.nextLine();
                            }
                        } else {
                            loop = false;
                        }
                    } while (loop);
                }
            }

        } catch (ParseException ex) {
            System.out.printf("\n******************************************************\n");
            System.out.printf("* %s is a invalid date format *\n", ex.getMessage());
            System.out.printf("******************************************************\n");

        }

    }

    public void generateSalesReport(Database paraDatabase) {
        scan = new Scanner(System.in);
        this.database = paraDatabase;

        OrderArrayList<Order> orderList = database.getOrderList();
        ItemGroupList<ItemGroup> itemGroupList = new ItemGroupList<>(database.getItemGroupList().getTotalNumOfIndex());
        OrderArrayList<Integer> quantitySoldList = new OrderArrayList<>(database.getItemGroupList().getTotalNumOfIndex());

        try {
            int no = 0;
            double total = 0;
            double previousTotal = 0;
            Date firstDate = new Date(1);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            System.out.printf("\nEnter start date (dd/MM/yyyy): ");
            String startDateInString = scan.nextLine();
            System.out.printf("Enter end   date (dd/MM/yyyy): ");
            String endDateInString = scan.nextLine();

            Date startDate = format.parse(startDateInString);
            Date endDate = format.parse(endDateInString);
            endDate = new Date(endDate.getTime() + 86399999);

            long periodLong = endDate.getTime() - startDate.getTime();

            Date previousStartDate = new Date(startDate.getTime() - periodLong);
            Date previousEndDate = new Date(endDate.getTime() - periodLong - 1);

            if (startDate.compareTo(firstDate) < 0 || endDate.compareTo(firstDate) < 0) {
                System.out.printf("\n*******************************************\n");
                System.out.printf("* The date need to start from: %s *\n", format.format(firstDate));
                System.out.printf("*******************************************\n");
            } else {

                String msg = String.format("%s\n", BORDER);
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  #   SALES REPORT(%10s - %10s)   #\n", format.format(startDate), format.format(endDate));
                msg += String.format("\t\t\t\t\t\t\t\t\t\t  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                msg += String.format("%s\n", BORDER);
                msg += String.format("%3s %20s %28s %40s %41s %42s\n", "No.", "ProductID", "Product Name", "Quantity Sold", "Price(RM)", "Subtotal(RM)");
                msg += String.format("%3s %20s %28s %40s %41s %42s\n", "---", "---------", "------------", "-------------", "---------", "------------");

                //==================Calculate previous period sales which have the same period long==================
                for (int position = 1; position <= orderList.size(); position++) {
                    Order order = orderList.viewElement(position);
                    if (order.getStatus().equals("Completed") && order.getEndDate().compareTo(previousStartDate) >= 0 && order.getEndDate().compareTo(previousEndDate) <= 0) {
                        ItemGroup itemGroup = orderList.viewElement(position).getItemGroup();
                        previousTotal += order.getQuantity() * itemGroup.getPrice();
                    }
                }

                //==================Calculate sales within given date==================
                for (int position = 1; position <= orderList.size(); position++) {
                    Order order = orderList.viewElement(position);
                    if (order.getStatus().equals("Completed") && order.getEndDate().compareTo(startDate) >= 0 && order.getEndDate().compareTo(endDate) <= 0) {
                        ItemGroup itemGroup = orderList.viewElement(position).getItemGroup();
                        boolean foundInList = false;
                        for (int itemGroupPosition = 1; itemGroupPosition <= itemGroupList.getTotalNumOfIndex(); itemGroupPosition++) {
                            if (itemGroup.getId().equals(itemGroupList.view(itemGroupPosition).getId())) {
                                int currentQuantity = quantitySoldList.viewElement(itemGroupPosition);
                                currentQuantity += order.getQuantity();
                                quantitySoldList.replace(currentQuantity, itemGroupPosition);
                                foundInList = true;
                                break;
                            }
                        }
                        if (!foundInList) {
                            itemGroupList.add(itemGroup);
                            quantitySoldList.add(order.getQuantity());
                        }
                        total += order.getQuantity() * itemGroup.getPrice();
                    }
                }

                for (int position = 1; position <= itemGroupList.getTotalNumOfIndex(); position++) {
                    ItemGroup itemGroup = itemGroupList.view(position);
                    msg += String.format("%d. %20s %28s %35d %45.2f %41.2f\n", ++no, itemGroup.getId(), itemGroup.getName(), quantitySoldList.viewElement(position), itemGroup.getPrice(), itemGroup.getPrice() * quantitySoldList.viewElement(position));
                }

                if (no == 0) {
                    System.out.printf("\n**********************************************************\n");
                    System.out.printf("* No sales order found during %s to %s *\n", format.format(startDate), format.format(endDate));
                    System.out.printf("**********************************************************\n");
                } else {
                    msg += String.format("%s\n"
                            + "%161s: RM %10.2f (%+.2f%%)\n", "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
                            + "", "Total", total, previousTotal == 0 ? 100 : (total - previousTotal) / previousTotal * 100);

                    System.out.print(msg);
                }
                System.out.printf("Press ANY key back to continue...");
                scan.nextLine();
            }
        } catch (ParseException ex) {
            System.out.printf("\n******************************************************\n");
            System.out.printf("* %s is a invalid date format *\n", ex.getMessage());
            System.out.printf("******************************************************\n");

        }

    }

    private void checkTimeLeft() {
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
            Customer leader = order.getOrderMembersList().getMember();
            if (leader.getId().equals(loginCustomer.getId())) {
                createdOrderListByLoginAccount.add(order);
                msg += String.format("%d. %15s %18s %71s %16d %17d/%-3d %15s %17d %17s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), itemGroup.getPrice() + ((order.getShipping() == null) ? 0 : order.getShipping().getFee()), order.calculateTimeLeft(), order.getStatus());
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
                        System.out.printf("\nAre you sure you want to complete this order?(Y/N) ");
                        if (scan.nextLine().toUpperCase().charAt(0) == 'Y') {
                            selectedOrder.completeOrder(selectCourierService());
                            displayDetailsOrder(selectedOrder);
                            System.out.printf("Press ANY key back to continue...");
                            scan.nextLine();
                            return false;
                        }
                    }

                } else {
                    System.out.printf("\n%s\n", SHORT_LINE);
                    System.out.printf("Enter a number:\n"
                            + "-------------------------\n");
                    System.out.printf("# 1      (View Joined Buyer(s) Details)\n");
                    System.out.printf("# Others (Back)\n> ");
                    int reply = scan.nextInt();
                    scan.nextLine();
                    if (reply == 1) {
                        displayJoinedBuyer(selectedOrder.getOrderMembersList());
                    }
                    System.out.printf("Press ANY key back to continue...");
                    scan.nextLine();
                }

            }
        }
        return true;
    }

    private void displayJoinedBuyer(CustomerArrayList<Customer> orderMembersList) {
        String msg = String.format("%s\n", BORDER);
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   #   ORDER MEMBERS DETAILS   #\n");
        msg += String.format("\t\t\t\t\t\t\t\t\t\t   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        msg += String.format("%s\n", BORDER);
        System.out.print(msg);
        System.out.printf("%s %20s %28s %27s %50s %67s\n", "No.", "AccountID", "Buyer Name", "Gender", "Address", "Phone Number");
        System.out.printf("%s %20s %28s %27s %77s %40s\n", "---", "---------", "----------", "------", "--------------------------------------------------------", "------------");

        for (int position = 1; position <= orderMembersList.getMemberAmount(); position++) {
            Customer member = orderMembersList.getMember(position);
            System.out.printf("%d. %20s %28s %25c %80s %40s\n", position, member.getId(), member.getName(), member.getGender(), member.getAddress(), member.getPhoneNumber());
            System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
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
            CustomerArrayList<Customer> orderMembers = order.getOrderMembersList();
            ItemGroup itemGroup = order.getItemGroup();
            Customer leader = order.getOrderMembersList().getMember();
            for (int membersPosition = 2; membersPosition <= orderMembers.getMemberAmount(); membersPosition++) {
                if (orderMembers.getMember(membersPosition).getId().equals(loginCustomer.getId())) {
                    joinedOrderListByLoginAccount.add(order);
                    msg += String.format("%d. %15s %18s %68s %14d/%-3d %16.2f %24s %12s %19s\n", ++no, order.getId(), itemGroup.getName(), leader.getAddress(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot(), itemGroup.getPrice() + ((order.getShipping() == null) ? 0 : order.getShipping().getFee()), order.getDateInFormat(order.getCreateDate()), order.calculateTimeLeft(), order.getStatus());
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
                return itemGroupListByKeyword.view(position);
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
            if (order.getItemGroup().getId().equals(itemGroup.getId()) && !order.getStatus().contains("Completed") && order.getOrderMembersList().getMemberAmount() < order.getMaxSlot()) {
                boolean foundMembers = false;
                for (int membersPosition = 1; membersPosition <= order.getOrderMembersList().getMemberAmount(); membersPosition++) {
                    if (order.getOrderMembersList().getMember(membersPosition).getId().equals(loginCustomer.getId())) {
                        foundMembers = true;
                    }
                }
                if (!foundMembers) {
                    orderListByItemGroupId.add(order);
                    Customer leader = order.getOrderMembersList().getMember();
                    msg += String.format("%d. %18s %70s %17d %18d/%-3d\n", ++no, leader.getName(), leader.getAddress(), order.calculateTimeLeft(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot());
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
            System.out.printf("\nAre you sure you want to join No.%d group?(Y/N)", position);
            if (scan.nextLine().toUpperCase().charAt(0) == 'Y') {
                database.updateOrderByAddNewMember(orderListByItemGroupId.viewElement(position).getId(), loginCustomer);
                displayDetailsOrder(orderListByItemGroupId.viewElement(position));
                System.out.printf("Press ANY key to continue...");
                scan.nextLine();
            }
        } else if (position == -1) {

            System.out.printf("\nAre you sure you want to create new group?(Y/N)");
            if (scan.nextLine().toUpperCase().charAt(0) == 'Y') {
                int maxSlot;
                do {
                    System.out.printf("\nEnter maximum buyers for your group (Min:%d): ", itemGroup.getMinimuimBuyer());
                    maxSlot = scan.nextInt();
                    scan.nextLine();
                    if (maxSlot >= itemGroup.getMinimuimBuyer()) {
                        Order newOrder = new Order(itemGroup, loginCustomer, maxSlot);
                        database.addIntoOrderList(newOrder);
                        displayDetailsOrder(newOrder);
                        System.out.printf("Press ANY key to continue...");
                        scan.nextLine();
                    } else {
                        System.out.printf("******************************************************************\n");
                        System.out.printf("* Invalid slot number! Please enter number that are %d or above! *\n", itemGroup.getMinimuimBuyer());
                        System.out.printf("******************************************************************\n");
                    }
                } while (maxSlot < itemGroup.getMinimuimBuyer());

            }
        }

        return true;
    }

    public void displayDetailsOrder(Order order) {
        Customer leader = order.getOrderMembersList().getMember();
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
                + "Buyer Joined\t\t: %d/%-3d\n", itemGroup.getName(), order.getDateInFormat(order.getCreateDate()), itemGroup.getMinimuimBuyer(), order.getOrderMembersList().getMemberAmount(), order.getMaxSlot());

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
