/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

/**
 *
 * @author Gan Hao Xian
 */
public class ItemGroup {

    private static int count = 0;
    private String id;
    private String name;
    private double price;
    private int minimuimBuyer;

    public ItemGroup() {
    }

    public ItemGroup(String name, double price, int minimuimBuyer) {
        this.id = String.format("IG%03d", ++count);
        this.name = name;
        this.price = price;
        this.minimuimBuyer = minimuimBuyer;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinimuimBuyer() {
        return minimuimBuyer;
    }

    public void setMinimuimBuyer(int minimuimBuyer) {
        this.minimuimBuyer = minimuimBuyer;
    }

}
