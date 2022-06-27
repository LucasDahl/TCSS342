/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * This class will make an ArrayLIst to function like the built in ArrayList.
 *
 * @param <Type> the type of element in the ArrayList.
 */
public class MyArrayList <Type> {

    // **************************** Fields ****************************

    private Type[] list ;
    private int capacity;
    private int size;


    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public MyArrayList() {
        capacity = 16;
        size = 0;
        list = (Type[]) new Object[capacity];
    }

    // **************************** Methods ***************************

    /**
     *This method will insert an element at a given index,
     * and shift everything the other elements right.
     *
     * @param item is the item to be added
     * @param index is the index at which to add the element.
     */
    public void insert(Type item, int index) {

        if(index > size + 1) {
            return;
        }

        if((size + 1) == capacity) {
            resize();
        }

        // Shift all the elements right one of the index.
        for(int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }

        // Add the element.
        list[index] = item;

        // Increase the size
        size++;

    }

    /**
     * This method will return and remove the element at
     * a given index.
     *
     * @param index The index of the element being removed.
     * @return the element removed from the ArrayList.
     */
    public Type remove(int index) {

        // Return null if the index is out of range.
        if(index >= size || size < 0) {
            return null;
        }

        // Get the element that is being removed
        Type temp = list[index];

        // Shift the elements to the left
        for(int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        // Decrease the size
        size--;

        return temp;
    }

    /**
     *This method will search the ArrayList for an element
     * and return true if it finds it, or false if it does not.
     *
     * @param item the element the user wishes to find
     * @return returns true if the element is in the ArrayList and false if it is not.
     */
    public boolean contains(Type item) {

        // See if the element is in the ArrayList
        for(int i = 0; i < size; i++) {
            if(item == list[i]) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method will search the ArrayList to find
     * if the element exists in the arraylist and returns
     * the index if it finds it, and -1 if it does not.
     *
     * @param item the item the user wants to see if its in the ArrayList.
     * @return the index of the item, of -1 if the item is not in the ArrayList
     */
    public int indexOf(Type item) {

        // Find the index of the element in the ArrayList
        for(int i = 0; i < size; i++) {
            if(item == list[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     *This method will take an index and return the element at that location
     * if the index is out of bounds the method will return null.
     *
     * @param index the user wants to get the element from.
     * @return the element at the given index, or null if it doesn't exist.
     */
    public Type get(int index) {

        // Return null if the index is out of bounds.
        if(index > size || index < 0) {
            return null;
        }

        return list[index];
    }

    /**
     * This method will update the aArrayList at the given index.
     *
     * @param index this is the index to update.
     * @param item this is the item to update the ArrayList with.
     */
    public void set(int index, Type item) {

        // Set the element at the given index.
        if(index <= size && index >= 0) {
            list[index] = item;
        }
    }

    /**
     * This method will return the size of the ArrayList.
     *
     * @return the size of the array list.
     */
    public int size() {
        return size;
    }

    /**
     * This method returns false is the ArrayList is not empty
     * and true if it is.
     *
     * @return true if the ArrayList is empty and false otherwise.
     */
    public boolean isEmpty() {

        if(size > 0) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        if(isEmpty()) {
            return "[]";
        }

        // Properties
        String str = "";

        // Build the string
        for(int i = 0; i < size; i++) {
            if(i != 0) {
                str +=  ", " + list[i].toString();
            } else {
                str +=  "," + list[i].toString();
            }
        }

        return "[" + str.substring(1) + "]";
    }

    // **************************** Private Methods ***************************

    // This method will resize the ArrayList once it is at capacity.
    private void resize() {

        // Properties
        Type[] temp = (Type[]) new Object[capacity * 2];

        // Fill the temp list
        for(int i = 0; i < size; i++) {
            temp[i] = list[i];
        }

        // Set the list to the temp list.
        list = temp;

    }


}
