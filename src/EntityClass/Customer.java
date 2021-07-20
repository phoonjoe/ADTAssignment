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
public class Customer {

    private static int count = 0;
    private String id;
    private String name;
    private char gender;  //M=Male, F=Female
    private String address;
    private String username;
    private String password;
    private String phoneNumber;

    public Customer(String name, char gender, String address, String username, String password, String phoneNumber) {
        this.id = String.format("CU%03d", ++count);
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Customer.count = count;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
