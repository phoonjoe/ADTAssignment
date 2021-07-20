/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplementationClass;

import InterfaceClass.OrderListInterface;

/**
 *
 * @author Joe Phoon
 */
public class OrderArrayList<T> implements OrderListInterface<T> {

    private T[] arrayList;
    private int totalElements;
    private static final int DEFAULT_CAPACITY = 5;

    public OrderArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public OrderArrayList(int capacity) {
        totalElements = 0;
        arrayList = (T[]) new Object[capacity];
    }

    @Override
    public boolean add(T object) {
        if (isFull()) {
            doubleSize();
        }
        arrayList[totalElements] = object;
        totalElements++;
        return true;
    }

    @Override
    public boolean add(T object, int position) throws IndexOutOfBoundsException {

        if ((position >= 1) && (position <= totalElements + 1)) {
            makeRoom(position);
            arrayList[position - 1] = object;
            totalElements++;
        } else {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing) or %d(New).", position, totalElements, totalElements + 1));
        }

        return true;
    }

    @Override
    public int getCapacity() {
        return arrayList.length;
    }

    @Override
    public boolean isEmpty() {
        return totalElements == 0;
    }

    @Override
    public boolean isFull() {
        return totalElements == arrayList.length;
    }

    @Override
    public void removeAll() {
        for (int index = 0; index < totalElements; index++) {
            arrayList[index] = null;
        }
        totalElements = 0;
    }

    @Override
    public T removeElement() throws NullPointerException {
        T object = null;
        if (!isEmpty()) {
            object = arrayList[0];

            removeGap(1);

            totalElements--;
        } else {
            throw new NullPointerException(String.format("Array list is empty!"));
        }

        return object;
    }

    @Override
    public T removeElement(int position) throws IndexOutOfBoundsException, NullPointerException {
        T object = null;

        if (!isEmpty()) {
            if ((position >= 1) && (position <= totalElements)) {
                object = arrayList[position - 1];

                if (position < totalElements) {
                    removeGap(position);
                }
                totalElements--;
            } else {
                throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, totalElements));
            }
        } else {
            throw new NullPointerException("The list is empty!");
        }

        return object;
    }

    @Override
    public void replace(T object, int position) throws IndexOutOfBoundsException {

        if ((position >= 1) && (position <= totalElements)) {
            arrayList[position - 1] = object;
        } else {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, totalElements));
        }

    }

    @Override
    public int size() {
        return totalElements;
    }

    @Override
    public T viewElement() throws NullPointerException {

        if (!isEmpty()) {
            return arrayList[0];
        } else {
            throw new NullPointerException("The list is empty!");
        }

    }

    @Override
    public T viewElement(int position) throws IndexOutOfBoundsException, NullPointerException {
        T object = null;

        if (!isEmpty()) {
            if ((position >= 1) && (position <= totalElements)) {
                object = arrayList[position - 1];
            } else {
                throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, totalElements));
            }
        } else {
            throw new NullPointerException("The list is empty!");
        }

        return object;
    }

    private void doubleSize() {
        T[] oldList = arrayList;
        int oldSize = arrayList.length;

        arrayList = (T[]) new Object[2 * oldSize];
        for (int i = 0; i < oldSize; i++) {
            arrayList[i] = oldList[i];
        }
    }

    private void makeRoom(int position) {
        int newIndex = position - 1;
        int lastIndex = totalElements - 1;
        if (isFull()) {
            doubleSize();
        }
        for (int index = lastIndex; index >= newIndex; index--) {
            arrayList[index + 1] = arrayList[index];
        }
    }

    private void removeGap(int position) {
        int removedIndex = position - 1;
        int lastIndex = totalElements - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            arrayList[index] = arrayList[index + 1];
        }
        arrayList[lastIndex] = null;
    }

}
