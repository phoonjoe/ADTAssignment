/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplementationClass;

import InterfaceClass.JoeListInterface;

/**
 *
 * @author Joe Phoon
 */
public class JoeLinkedList<T> implements JoeListInterface<T> {

    private Node firstNode;
    private int numberOfEntries;

    public JoeLinkedList() {
        removeAll();
    }

    @Override
    public final void removeAll() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T object) {

        Node newNode = new Node(object);

        if (isEmpty()) {
            firstNode = newNode;
        } else {
            Node currentNode = firstNode;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }

        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(T object, int position) throws IndexOutOfBoundsException {

        if (position < 1 || position > numberOfEntries + 1) {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing) or %d(New).", position, numberOfEntries, numberOfEntries + 1));
        }

        Node newNode = new Node(object);

        if (isEmpty() || position == 1) {
            newNode.next = firstNode;
            firstNode = newNode;
        } else {
            Node nodeBefore = firstNode;
            for (int i = 1; i < position - 1; i++) {
                nodeBefore = nodeBefore.next;
            }

            newNode.next = nodeBefore.next;
            nodeBefore.next = newNode;
        }

        numberOfEntries++;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public T removeElement() {

        if (isEmpty()) {
            throw new NullPointerException(String.format("Linked list is empty!"));
        }

        T result = firstNode.data;
        firstNode = firstNode.next;

        numberOfEntries--;
        return result;
    }

    @Override
    public T removeElement(int position) throws IndexOutOfBoundsException {
        T result = null;

        if (position < 1 || position > numberOfEntries) {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, numberOfEntries));
        } else if (position == 1) {
            result = firstNode.data;
            firstNode = firstNode.next;
        } else {
            Node nodeBefore = firstNode;
            for (int i = 1; i < position - 1; i++) {
                nodeBefore = nodeBefore.next;
            }
            result = nodeBefore.next.data;
            nodeBefore.next = nodeBefore.next.next;
        }

        numberOfEntries--;
        return result;
    }

    @Override
    public boolean replace(T object, int position) throws IndexOutOfBoundsException {

        if (position < 1 || position > numberOfEntries) {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, numberOfEntries));
        }

        Node currentNode = firstNode;

        for (int i = 0; i < position - 1; ++i) {
            currentNode = currentNode.next;
        }
        currentNode.data = object;

        return true;
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public T viewElement() throws NullPointerException {

        if (isEmpty()) {
            throw new NullPointerException(String.format("Linked list is empty!"));
        }

        return firstNode.data;
    }

    @Override
    public T viewElement(int position) {

        if (position < 1 || position > numberOfEntries) {
            throw new IndexOutOfBoundsException(String.format("Invalid position(%d)!Please enter valid position between 1-%d(Existing).", position, numberOfEntries));
        } else if (position == 1) {
            return firstNode.data;
        }

        Node nodeBefore = firstNode;

        for (int i = 1; i < position - 1; i++) {
            nodeBefore = nodeBefore.next;
        }

        return nodeBefore.next.data;
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            this.next = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
