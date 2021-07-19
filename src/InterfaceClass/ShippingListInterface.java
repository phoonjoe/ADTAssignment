package InterfaceClass;

/**
 * * * @author Lee Jun Sian
 */
public interface ShippingListInterface<T> {

    public T readShipping();

    public T readShipping(int givenPosition);

    public void addNewShipping(T newShipping);

    public boolean addNewShipping(int newPosition, T newShipping);

    public T deleteShipping(int givenPosition);

    public void clear();

    public int getNumberOfShipping();

    public boolean replaceShipping(int givenPosition, T newShipping);

    public boolean isShippingListEmpty();

    public boolean isShippingListFull();
}
