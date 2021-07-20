/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplementationClass;
import InterfaceClass.CustomerListInterface;
import java.io.Serializable;

/**
 *
 * @author yuhanyip
 * @param <T>
 */
public class CustomerArrayList<T> implements  CustomerListInterface<T>, Serializable{
    private T[] array;
    private int numberOfEntries;
    private static  final int DEFAULT_CAPACITY = 10;
    
    public CustomerArrayList(){
        this(DEFAULT_CAPACITY);
    }
    
    public CustomerArrayList(int initialCapacity){
        numberOfEntries = 0;
        array = (T[]) new Object[initialCapacity];
    }
   
    @Override
    public boolean addMember(T newEntry) {
        
      if(memberFull()== true){
          increaseSize();
      }else if(newEntry == null){
          throw new NullPointerException("Error!!! It is empty -_-#!");
      }
      
    array[numberOfEntries] = newEntry;
    numberOfEntries++;
    return true;
  }
  
    @Override
    public boolean addMember(int Position, T newEntry) {
    boolean isSuccessful = true;

    if(memberFull() == true){
        increaseSize();
    }else if(newEntry == null ){
          throw new NullPointerException("Error!!! It is empty -_-#!");
    }
    
    if ((Position >= 1) && (Position <= numberOfEntries + 1)) {
        makeRoom(Position);
        array[Position - 1] = newEntry;
        numberOfEntries++;
    } else {
        
      isSuccessful = false;
      System.err.println("Error!!! The position not found -_-#!");
     
      
    }

    return isSuccessful;
  }

    
     public T removeMember(){
         T result = null;
         
         if(memberEmpty()== true){
          throw new NullPointerException("Error!!! Can't be found -_-#!");
      
         }
         
         result = array[0];
         removeGap(0);
         numberOfEntries--;
         
        return result;
     }
             
    @Override
    public T removeMember(int givenPosition) {
    T result = null;
    
    if(memberEmpty()== true){
          throw new NullPointerException("Error!!! Can't be found -_-#!");
      }else if(givenPosition < 0){
          throw new ArrayIndexOutOfBoundsException("The interger can't negative");
      }

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      result = array[givenPosition - 1];

      if (givenPosition < numberOfEntries) {
        removeGap(givenPosition);
      }

      numberOfEntries--;
    }

    return result;
  }

  public void clearAll() {
    numberOfEntries = 0;
  }
  
 
  @Override
  public boolean change(int givenPosition, T newEntry) {
    boolean isSuccessful = true;
    
    if(memberEmpty()== true){
          throw new NullPointerException("Error!!! Can't be found -_-#!");
      }else if(givenPosition < 0){
          throw new ArrayIndexOutOfBoundsException("The interger can't negative");
      }

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      array[givenPosition - 1] = newEntry;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }
  
  @Override
    public T getMember(){
        T member = null;
        
        if(memberEmpty()== false){
            
            member = array[0];
            
        }else{
            throw new NullPointerException("Error!!! Can't be found -_-#!");
        }
        
        return member;
    }
  
  

  @Override
  public T getMember(int givenPosition) {
    T member = null;

    if(memberEmpty() == true){
        throw new NullPointerException("Error!!! Can't be found -_-#!");
    }else if(givenPosition < 0){
          throw new ArrayIndexOutOfBoundsException("The interger can't negative");
    }
    
    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      member = array[givenPosition - 1];
    }else{
        System.err.println("Error!!! It is not exits -_-#!");
    }

    return member;
  }

  
  
  @Override
  public boolean getMember(T anEntry) {
    boolean member = false;
    
    for (int index = 0; !member && (index < numberOfEntries); index++) {
      if (anEntry.equals(array[index])) {
        member = true;
      }
    }
    return member;
  }

  //size
  @Override
  public int getMemberAmount() {
    return numberOfEntries;
  }

  @Override
  public boolean memberEmpty() {
    return numberOfEntries == 0;
  }

  @Override
  public boolean memberFull() {
    return numberOfEntries == array.length;
  }

  @Override
  public String toString() {
    String outputStr = "";
    for (int index = 0; index < numberOfEntries; ++index) {
      outputStr += array[index] + "\n";
    }

    return outputStr;
  }
  
  private void makeRoom(int newPosition) {
    int newIndex = newPosition - 1;
    int lastIndex = numberOfEntries - 1;

    for (int index = lastIndex; index >= newIndex; index--) {
      array[index + 1] = array[index];
    }
  }
  
  private void removeGap(int givenPosition) {
    
    int removedIndex = givenPosition - 1;
    int lastIndex = numberOfEntries - 1;

    for (int index = removedIndex; index < lastIndex; index++) {
      array[index] = array[index + 1];
    }
  }
  
  private void increaseSize() {
        T[] oldList = array;
        int oldSize = array.length;

        array = (T[]) new Object[2 * oldSize];
        for (int i = 0; i < oldSize; i++) {
            array[i] = oldList[i];
        }
    }

    

    
  
  
  
  
  
}
