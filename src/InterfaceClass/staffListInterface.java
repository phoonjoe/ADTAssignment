/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceClass;

/**
 *
 * @author Au Kah Jun
 */
public interface staffListInterface<T> {
    public T readStaff();

    public T readStaff(int givenPosition);

    public void addNewStaff(T newStaff);

    public boolean addNewStaff(int newPosition, T newStaff);

    public T deleteStaff(int givenPosition);

    public void clear();

    public int getNumberOfStaff();

    public boolean replaceStaff(int givenPosition, T newStaff);

    public boolean staffEmpty();

    public boolean staffFull();
}
