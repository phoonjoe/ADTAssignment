/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplementationClass;

import InterfaceClass.*;

/**
 *
 * @author Gan Hao Xian
 * @param <T>
 */
public class ItemGroupList<T> implements ItemGroupListInterface<T> {

    private T[] array;
    private int totalNumOfIndex;
    private static final int DEFAULT_CAPACITY = 10;

    public ItemGroupList() {
        this(DEFAULT_CAPACITY);
    }

    public ItemGroupList(int capacity) {
        totalNumOfIndex = 0;
        array = (T[]) new Object[capacity];
    }

    @Override
    public T view() {
        T firstItemGroup = null;
        if (isEmpty() == false) {

            firstItemGroup = array[0];
        } else {
            System.out.println("Error! This List is empty!");
        }
        return firstItemGroup;
    }

    @Override
    public T view(int specificPosition) {
        T itemGroup = null;
        if (isEmpty() == false) {

            if ((specificPosition >= 1) && (specificPosition <= totalNumOfIndex + 1)) {
                itemGroup = array[specificPosition - 1];

            } else {
                System.out.println("Error! Specified Index Does Not Exist.");
            }
        } else {
            System.out.println("Error! This List is empty!");
        }
        return itemGroup;

    }

    @Override
    public void add(T newItemGroup) {
        if (isFull() == true) {
            doubleCapacity();
        }
        array[totalNumOfIndex] = newItemGroup;
        totalNumOfIndex++;

    }

    @Override
    public boolean add(int specificPosition, T newItemGroup) {

        if (isFull() == true) {
            doubleCapacity();
        }
        if ((specificPosition >= 1) && (specificPosition <= totalNumOfIndex + 1)) {
            makeRoom(specificPosition);
            array[specificPosition - 1] = newItemGroup;
            totalNumOfIndex++;
        } else {
            System.out.println("Failed to add item group.");
            return false;
        }

        return true;
    }

    @Override
    public T remove() {
        T itemGroup = null;
        if (isEmpty() == false) {

            itemGroup = array[totalNumOfIndex - 1];

            totalNumOfIndex--;
        } else {
            System.out.println("Error! This List is empty!");
        }

        return itemGroup;
    }

    @Override
    public T remove(int specificPosition) {
        T itemGroup = null;
        if (isEmpty() == false) {
            if ((specificPosition >= 1) && (specificPosition <= totalNumOfIndex)) {
                itemGroup = array[specificPosition - 1];

                if (specificPosition < totalNumOfIndex) {
                    removeGap(specificPosition);
                }

                totalNumOfIndex--;
            } else {
                System.out.println("Error! Specified Index Does Not Exist.");
            }
        } else {
            System.out.println("Error! This List is empty!");
        }
        return itemGroup;
    }

    @Override
    public boolean replace(int specificPosition, T newItemGroup) {

        if ((specificPosition >= 1) && (specificPosition <= totalNumOfIndex)) {
            array[specificPosition - 1] = newItemGroup;
        } else {
            System.out.println("Specified Index Does Not Exist! Failed To Replace With New Item.");
            return false;
        }

        return true;

    }

    @Override
    public int getTotalNumOfIndex() {
        return totalNumOfIndex;
    }

    @Override
    public int getCapacity() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return totalNumOfIndex == 0;
    }

    @Override
    public boolean isFull() {
        return totalNumOfIndex == array.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < totalNumOfIndex; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    private void doubleCapacity() {
        T[] oldList = array;
        int oldSize = array.length;

        array = (T[]) new Object[2 * oldSize];
        for (int i = 0; i < oldSize; i++) {
            array[i] = oldList[i];
        }
    }

    private void makeRoom(int specificPosition) {
        int newIndex = specificPosition - 1;
        int lastIndex = totalNumOfIndex - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    private void removeGap(int specificPosition) {

        int removedIndex = specificPosition - 1;
        int lastIndex = totalNumOfIndex - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }
}
