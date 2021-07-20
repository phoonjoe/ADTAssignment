/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceClass;

/**
 *
 * @author yuhanyip
 */
public interface CustomerListInterface<T> {
     public boolean addMember(T newMember);

    public boolean addMember(int newPosition, T newMember);

    public T removeMember();

    public T removeMember(int givenPosition);

    public void clearAll();

    public boolean change(int givenPosition, T newMember);

    public T getMember();

    public T getMember(int givenPosition);

    public boolean getMember(T anEntry);

    public int getMemberAmount();

    public boolean memberEmpty();

    public boolean memberFull();
    
    
}
