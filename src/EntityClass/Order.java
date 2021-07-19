/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

import ImplementationClass.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Joe Phoon
 */
public class Order {

    private final String DATE_FORMAT = ("dd-MM-yyyy HH:mm");
    private final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private static int count = 0;
    private String id;
    private ItemGroup itemGroup;
    private Shipping shipping;
    private OrderArrayList<Customer> orderMembersList;
    private Date createDate;
    private Date endDate;
    private int quantity;
    private double total;
    private String status;    //Waiting, active, completed

    public Order(ItemGroup itemGroup, Customer leader) {
        this.id = String.format("OR%03d", ++count);
        this.itemGroup = itemGroup;
        this.orderMembersList = new OrderArrayList<>(100);
        this.orderMembersList.add(leader);
        this.createDate = new Date();
        this.endDate = new Date(createDate.getTime() + MILLIS_IN_A_DAY);
        this.quantity = 1;
        this.total = itemGroup.getPrice();
        this.status = "Waiting";
    }

    public long calculateTimeLeft() {

        long diffInMillies = endDate.getTime() - createDate.getTime();
        long diffenceInMinute = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

        long hours = diffenceInMinute / 60;
        if (diffenceInMinute % 60 != 0) {
            hours++;
        }
        return hours;
    }

    public String getDateInFormat(Date date) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateInString = format.format(date.getTime());

        return dateInString;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Order.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public OrderArrayList<Customer> getOrderMembers() {
        return orderMembersList;
    }

    public void setOrderMembers(OrderArrayList<Customer> orderMembersList) {
        this.orderMembersList = orderMembersList;
    }

    public void addIntoOrderMembersList(Customer customer) {
        orderMembersList.add(customer);
        quantity++;
        total = itemGroup.getPrice() * quantity;
        if (quantity >= itemGroup.getMinimuimBuyer()) {
            status = "Active";
        }
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
