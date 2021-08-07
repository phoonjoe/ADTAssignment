package DatabaseClass;

import EntityClass.*;
import ImplementationClass.*;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Joe Phoon
 */
public class Database {

    private final String DATE_FORMAT = ("dd-MM-yyyy HH:mm");
    private final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private final long MILLIS_IN_A_HOUR = 1000 * 60 * 60;

    private ItemGroupList<ItemGroup> itemGroupList;
    private CustomerArrayList<Customer> customerList;
    private ShippingArrayList<Shipping> shippingList;
    private JoeLinkedList<Order> orderList;
    private StaffArrayList<Staff> staffList;

    public Database() {
        Date currentDate = new Date();

        // ============Item Group============
        itemGroupList = new ItemGroupList<>();
        itemGroupList.add(new ItemGroup("Nice Test LOL", 30.00, 2));
        itemGroupList.add(new ItemGroup("Tarkov GG", 24.00, 3));
        itemGroupList.add(new ItemGroup("POE Lets Go", 534.00, 2));
        itemGroupList.add(new ItemGroup("MEME", 30.00, 2));
        itemGroupList.add(new ItemGroup("WANG ZHE", 24.00, 3));
        itemGroupList.add(new ItemGroup("SHANG GUAN", 534.00, 2));
        itemGroupList.add(new ItemGroup("GENSHIN", 30.00, 2));

        // ============Customer============
        customerList = new CustomerArrayList<>();
        customerList.addMember(new Customer("Gan Hao Xian", 'F', "123 Jalan Mewa 3C,Prima Beruntung, 48300 Rawang Selangor", "joe123", "123", "012-3455678"));
        customerList.addMember(new Customer("Billy Tan", 'F', "435 Jalan Anggerik,Prima Beruntung, 57400 Sun Earth", "tan123", "123", "012-3455678"));
        customerList.addMember(new Customer("Haha Hoho", 'M', "76 Jalan Mewa 3C,Prima Beruntung, 48300 Rawang Selangor", "haha123", "123", "012-3455678"));

        // ============Staff============
        staffList = new StaffArrayList<>();
        staffList.add(new Staff("Staff A", 'M', "st123", "123", "123 Jalan Mewa 3C,Prima Beruntung, 48300 Rawang Selangor"));
        staffList.add(new Staff("Staff B", 'F', "st354", "123", "435 Jalan Anggerik,Prima Beruntung, 57400 Sun Earth"));
        staffList.add(new Staff("Staff C", 'M', "st533", "123", "76 Jalan Mewa 3C,Prima Beruntung, 48300 Rawang Selangor"));

        // ============Shipping============
        shippingList = new ShippingArrayList<>();
        shippingList.addNewShipping(new Shipping("Pos Laju", 8, "1 Day"));
        shippingList.addNewShipping(new Shipping("J&T Express", 10, "3 Days"));
        shippingList.addNewShipping(new Shipping("DHL Express", 12, "5 Days"));

        // ============Order============
        orderList = new JoeLinkedList<>();
        orderList.add(new Order(itemGroupList.view(1), customerList.getMember(1), new Date(currentDate.getTime() - MILLIS_IN_A_HOUR), 4));         //OR001
        orderList.add(new Order(itemGroupList.view(1), customerList.getMember(2), new Date(currentDate.getTime() - MILLIS_IN_A_HOUR * 10), 3));    //OR002
        orderList.add(new Order(itemGroupList.view(2), customerList.getMember(3), new Date(currentDate.getTime() - MILLIS_IN_A_HOUR * 23), 5));    //OR003
        orderList.add(new Order(itemGroupList.view(2), customerList.getMember(3), new Date(currentDate.getTime() - MILLIS_IN_A_HOUR * 24), 5));    //OR004

        //Complete OR001 at current time
        orderList.viewElement(1).addIntoOrderMembersList(customerList.getMember(3));
        orderList.viewElement(1).completeOrder(shippingList.readShipping(1));

        //Add 2 more member join into OR003
        orderList.viewElement(3).addIntoOrderMembersList(customerList.getMember(1));
        orderList.viewElement(3).addIntoOrderMembersList(customerList.getMember(2));

        //Complete OR004 at 21 hour ago
        orderList.viewElement(4).addIntoOrderMembersList(customerList.getMember(1));
        orderList.viewElement(4).addIntoOrderMembersList(customerList.getMember(2));
        orderList.viewElement(4).completeOrder(shippingList.readShipping(1));
        orderList.viewElement(4).setEndDate(new Date(currentDate.getTime() - MILLIS_IN_A_HOUR * 21));

    }

    public ItemGroupList<ItemGroup> getItemGroupList() {
        return itemGroupList;
    }

    public void addIntoItemGroupList(ItemGroup itemGroup) {
        itemGroupList.add(itemGroup);
    }

    public void modifyItemGroupList(int specificPosition, ItemGroup itemGroup) {
        itemGroupList.replace(specificPosition, itemGroup);
    }

    public void removeFromItemGroupList(int specificPosition) {
        itemGroupList.remove(specificPosition);
    }

    public JoeLinkedList<Order> getOrderList() {
        return orderList;
    }

    public void addIntoOrderList(Order order) {
        orderList.add(order);
    }

    public void updateOrderByAddNewMember(String orderId, Customer customer) {
        for (int position = 1; position <= orderList.size(); position++) {
            if (orderList.viewElement(position).getId().contains(orderId)) {
                orderList.viewElement(position).addIntoOrderMembersList(customer);
            }
        }
    }

    public CustomerArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void addIntoCustomerList(Customer customer) {
        customerList.addMember(customer);
    }

    public Customer getCustomerByUsername(String username) {
        for (int position = 1; position <= customerList.getMemberAmount(); position++) {
            if (username.equals(customerList.getMember(position).getUsername())) {
                return customerList.getMember(position);
            }
        }
        return null;
    }

    public Staff getStaffByUsername(String username) {
        for (int position = 1; position <= staffList.getTotalNumOfIndex(); position++) {
            if (username.equals(staffList.view(position).getUsername())) {
                return staffList.view(position);
            }
        }
        return null;
    }

    public ShippingArrayList<Shipping> getShippingList() {
        return shippingList;
    }

    public void addIntoShippingList(Shipping shipping) {
        shippingList.addNewShipping(shipping);
    }

}
