package ImplementationClass;

import InterfaceClass.*;

/**
 *
 * @author Lee Jun Sian
 */
public class ShippingArrayList<T> implements ShippingListInterface<T> {

    private T[] shippingArray;
    private int numberOfShipping;
    private static final int DEFAULT_CAPACITY = 2;

    public ShippingArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ShippingArrayList(int initialCapacity) {
        numberOfShipping = 0;
        shippingArray = (T[]) new Object[initialCapacity];
    }

    @Override
    public T readShipping() {
        T result = null;

        result = shippingArray[0];

        return result;
    }

    @Override
    public T readShipping(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfShipping)) {
            result = shippingArray[givenPosition - 1];
        }
        return result;
    }

    @Override
    public void addNewShipping(T newShipping) {
        if (isShippingListFull()) {
            doubleArray();
        }
        shippingArray[numberOfShipping] = newShipping;
        numberOfShipping++;
    }

    @Override
    public boolean addNewShipping(int newPosition, T newShipping) {
        boolean isFinish = true;
        if (isShippingListFull()) {
            doubleArray();
        }
        if ((newPosition >= 1) && (newPosition <= numberOfShipping + 1)) {
            makeRoom(newPosition);
            shippingArray[newPosition - 1] = newShipping;
            numberOfShipping++;
        } else {
            isFinish = false;
        }
        return isFinish;
    }

    @Override
    public T deleteShipping(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfShipping)) {
            result = shippingArray[givenPosition - 1];

            if (givenPosition < numberOfShipping) {
                removeGap(givenPosition);
            }
            numberOfShipping--;
        }
        return result;
    }

    @Override
    public void clear() {
        numberOfShipping = 0;
    }

    @Override
    public int getNumberOfShipping() {
        return numberOfShipping;
    }

    @Override
    public boolean replaceShipping(int givenPosition, T newShipping) {
        boolean isFinish = true;

        if ((givenPosition >= 1) && (givenPosition <= numberOfShipping)) {
            shippingArray[givenPosition - 1] = newShipping;
        } else {
            isFinish = false;
        }
        return isFinish;
    }

    @Override
    public boolean isShippingListEmpty() {
        return numberOfShipping == 0;
    }

    @Override
    public boolean isShippingListFull() {
        return numberOfShipping == shippingArray.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfShipping; ++index) {
            outputStr += shippingArray[index] + "\n";
        }

        return outputStr;
    }

    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = numberOfShipping - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            shippingArray[index + 1] = shippingArray[index];
        }
    }

    private void removeGap(int givenPosition) {

        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfShipping - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            shippingArray[index] = shippingArray[index + 1];
        }
    }

    private void doubleArray() {
        T[] oldList = shippingArray;
        int oldSize = shippingArray.length;

        shippingArray = (T[]) new Object[2 * oldSize];
        for (int i = 0; i < oldSize; i++) {
            shippingArray[i] = oldList[i];
        }
    }
}
