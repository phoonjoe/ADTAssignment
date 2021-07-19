/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClass;

/**
 *
 * @author Au Kah Jun
 */
public class Staff {
    private static int count =0;
    private String id;
    private String name;
    private char gender;
    private String address;
    
    public Staff(String name, char gender, String address) {
        this.id = String.format("CU%03d", ++count);
        this.name = name;
        this.gender = gender;
        this.address = address;
       
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
}
