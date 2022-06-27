/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * This program will make the MyLinkedList more self organizing.
 *
 * @param <Type> the type of element in the list.
 */
public class TransposeList <Type extends Comparable<Type>> {
    // **************************** Fields ****************************

    private MyLinkedList<Type> list;

    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public TransposeList() {
        list =  new MyLinkedList<>();
    }

    // **************************** Methods ***************************


    /**
     * This method adds an item at the
     * end of the list.
     *
     * @param item the item to be added.
     */
    public void add(Type item) {

        list.addToEnd(item);
    }

    /**
     * THis method will remove an item from the list.
     *
     * @param item to be removed
     * @return this is the item that was removed
     */
    public Type remove(Type item) {

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
     * This method will find an item
     * and swap it with its previous neighbor.
     *
     * @param item the item to find
     * @return the item that was found.
     */
    public Type find(Type item) {

        list.first();

        for(int i = 0; i <  list.size(); i++) {

            // Remove the item if found
            if(list.current().compareTo(item) == 0) {

                if(i >= 1) {
                    list.swapWithPrevious();
                }

                return item;
            }

            list.next();
        }

        return null;

    }

    /**
     * This method will return the size of the list.
     *
     * @return this is the size of the list.
     */
    public int size() {
        return list.size();
    }

    /**
     * This method lets the user know if the list is
     * empty or not.
     *
     * @return indicates if the list is empty.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}

