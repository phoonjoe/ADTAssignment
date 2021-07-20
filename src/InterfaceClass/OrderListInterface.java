/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceClass;

/**
 *
 * @author Joe Phoon
 */
public interface OrderListInterface<T> {

    public boolean add(T object);

    public boolean add(T object, int position);

    public int getCapacity();

    public boolean isEmpty();

    public boolean isFull();

    public void removeAll();

    public T removeElement();

    public T removeElement(int position);

    public void replace(T object, int position);

    public int size();

    public T viewElement();

    public T viewElement(int position);

}
