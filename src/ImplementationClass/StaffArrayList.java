package ImplementationClass;

import InterfaceClass.*;
/**
 *
 * @author Au Kah Jun
 */
public class StaffArrayList<T> implements staffListInterface<T> {

    private T[] staffArray;
    private int indexSum;
    private static final int DEFAULT_CAPACITY = 10;

    public StaffArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public StaffArrayList(int capacity) {
        indexSum = 0;
        staffArray = (T[]) new Object[capacity];
    }

    @Override
    public T view() {
        T lastStaff = null;
        if (isEmpty() == false) {
            lastStaff = staffArray[0];
        } else{
            System.out.println("Error!There are no staff here!");
        }
        return lastStaff;
    }

    @Override
    public T view(int specificIndex) {
        T staff = null;
        if (isEmpty() == false) {

            if ((specificIndex >= 1) && (specificIndex <= indexSum + 1)) {
                staff = staffArray[specificIndex - 1];

            } else {
                System.out.println("Error! Specified Index Does Not Exist.");
            }
        } else {
            System.out.println("Error! This List is empty!");
        }
        return staff;

    }

    @Override
    public void add(T newStaff) {
        if (isFull() == true) {
            doubleCapacity();
        }
        staffArray[indexSum] = newStaff;
        indexSum++;

    }

    @Override
    public boolean add(int specificPosition, T newStaff) {

        if (isFull() == true) {
            doubleCapacity();
        }
        if ((specificPosition >= 1) && (specificPosition <= indexSum + 1)) {
            makeRoom(specificPosition);
            staffArray[specificPosition - 1] = newStaff;
            indexSum++;
        } else {
            System.out.println("Failed to add staff.");
            return false;
        }

        return true;
    }

    @Override
    public T remove() {
        T staff = null;
        if (isEmpty() == false) {

            staff = staffArray[indexSum - 1];

            indexSum--;
        } else {
            System.out.println("Error! This List is empty!");
        }

        return staff;
    }

    @Override
    public T remove(int specificIndex) {
        T staff = null;
        if (isEmpty() == false) {
            if ((specificIndex >= 1) && (specificIndex <= indexSum)) {
                staff = staffArray[specificIndex - 1];

                if (specificIndex < indexSum) {
                    removeGap(specificIndex);
                }

                indexSum--;
            } else {
                System.out.println("Error! Specified Index Does Not Exist.");
            }
        } else {
            System.out.println("Error! This List is empty!");
        }
        return staff;
    }

    @Override
    public void clear() {
        indexSum = 0;
    }

    @Override
    public boolean replace(int specificIndex, T newStaff) {

        if ((specificIndex >= 1) && (specificIndex <= indexSum)) {
            staffArray[specificIndex - 1] = newStaff;
        } else {
            System.out.println("Specified Index Does Not Exist! Failed To Replace With New Staff.");
            return false;
        }

        return true;

    }

    @Override
    public boolean check(T staff) {

        for (int index = 0; (index < indexSum); index++) {
            if (staff.equals(staffArray[index])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTotalNumOfIndex() {
        return indexSum;
    }

    @Override
    public boolean isEmpty() {
        return indexSum == 0;
    }

    @Override
    public boolean isFull() {
        return indexSum == staffArray.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < indexSum; ++index) {
            outputStr += staffArray[index] + "\n";
        }

        return outputStr;
    }

    private void doubleCapacity() {
        T[] oldList = staffArray;
        int oldSize = staffArray.length;

        staffArray = (T[]) new Object[2 * oldSize];
        for (int i = 0; i < oldSize; i++) {
            staffArray[i] = oldList[i];
        }
    }

    private void makeRoom(int specificPosition) {
        int newIndex = specificPosition - 1;
        int lastIndex = indexSum - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            staffArray[index + 1] = staffArray[index];
        }
    }

    private void removeGap(int specificIndex) {

        int removedIndex = specificIndex - 1;
        int lastIndex = indexSum - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            staffArray[index] = staffArray[index + 1];
        }
    }

    @Override
    public T readStaff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T readStaff(int givenPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addNewStaff(T newStaff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addNewStaff(int newPosition, T newStaff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T deleteStaff(int givenPosition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfStaff() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean replaceStaff(int givenPosition, T newStaff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean staffEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean staffFull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}