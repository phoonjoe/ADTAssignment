/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

/**
 *
 * @author Joe Phoon
 */
public class Shipping {

    private static int count = 0;
    private String id;
    private String name;
    private double fee;
    private String deliveryTime;

    public Shipping(String name, double fee, String deliveryTime) {
        this.id = String.format("SP%03d", ++count);
        this.name = name;
        this.fee = fee;
        this.deliveryTime = deliveryTime;
    }

    public double calculateFee(int quantity) {
        return fee * quantity;
    }

    public String calculateDeliveryTime() {
        int orderToday = 1;
        switch (orderToday) {
            case 3:
            default:
                return deliveryTime;
        }
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Shipping.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

}
