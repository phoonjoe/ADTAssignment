/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceClass;

/**
 *
 * @author Gan Hao Xian
 * @param <T>
 */
public interface ItemGroupListInterface<T> {

    public T view();

    public T view(int specificIndex);

    public void add(T newItemGroup);

    public boolean add(int specificPosition, T newItemGroup);

    public T remove();

    public T remove(int specificIndex);

    public void clear();

    public boolean replace(int specificIndex, T newItemGroup);

    public boolean check(T itemGroup);

    public int getTotalNumOfIndex();

    public boolean isEmpty();

    public boolean isFull();
}
