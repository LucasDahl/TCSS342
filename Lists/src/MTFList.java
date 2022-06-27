import javax.xml.soap.Node;

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * This program will make the MyLinkedList more self organizing.
 *
 * @param <Type> the type of element in the list.
 */
public class MTFList <Type extends Comparable<Type>> {

    // **************************** Fields ****************************

    private MyLinkedList<Type> list;

    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public MTFList() {
        list =  new MyLinkedList<>();
    }

    // **************************** Methods ***************************

    /**
     * This method will add an item at the front of the list.
     *
     * @param item the item to be added.
     */
    public void add(Type item) {

        // Add the item.
        list.addBefore(item);

        // Make sure the current is set to the front.
        list.first();


    }

    /**
     * THis method will remove an item from the list.
     *
     * @param item to be removed
     * @return this is the item that was removed
     */
    public Type remove(Type item) {

        // Make sure current at the front of the list
        list.first();

        for(int i = 0; i <  list.size(); i++) {

            // Remove the item if found
            if(list.current().compareTo(item) == 0) {
                list.remove();
                return item;
            }

            list.next();

        }

        return null;

    }

    /**
     * This method searches the list to see if it contains an
     * item.
     *
     * @param item the item to find in the list.
     * @return the item or null
     */
    public Type find(Type item) {

        if(list.first() == null) {
            return null;
        }

        list.first();

        for(int i = 0; i <  list.size(); i++) {

            // Remove the item if found
            if(list.current().compareTo(item) == 0) {

                remove(list.current());
                // Set the current node to the front.
                list.first();

                // Add the item to the front.
                list.addBefore(item);

                return item;
            }

            list.next();
        }

        return null;

    }


    /**
     * This method returns the size of the list.
     *
     * @return the sie of the list.
     */
    public int size() {
        return list.size();
    }

    /**
     * This method returns true if the list is empty
     * and false if it is not.
     *
     * @return true or false depending on if the list is empty or not
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
