/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 *
 *
 * @param <Type> the type of the list.
 */
public class MyOrderedList <Type extends Comparable<Type>> {

    // **************************** Fields ****************************

    private MyArrayList<Type> list;
    public int comparisons;

    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public MyOrderedList() {
        list = new MyArrayList<>();
        comparisons = 0;
    }

    // **************************** Methods ***************************

    /**
     * This method will add an item to
     * the list and then sort it.
     *
     * @param item this is the item to add.
     */
    public void add(Type item) {

        // Add the item
        list.insert(item, list.size());

        // Sort the list
        for (int index = list.size() - 1; index > 0 && list.get(index).compareTo(list.get(index - 1)) < 0; index--) {

            comparisons++;

            Type temp = list.get(index);
            list.set(index, list.get(index - 1));
            list.set(index - 1, temp);

        }
    }


    /**
     * This method will remove an item if
     * it exists.
     *
     * @param item the item to be removed
     * @return the item that is removed, null if the item is not in the list.
     */
    public Type remove(Type item) {
        return list.remove(list.indexOf(item));
    }

    /**
     * This method will use binary search for a given
     * item and return true if it finds it.
     *
     * @param item the item to search for.
     * @return indicate weather the item was found or not.
     */
    public boolean binarySearch(Type item) {

        int start = 0;
        int finish = size() - 1;

        if (finish < start) {
            return false;
        }

        while (start <= finish) {
            comparisons++;
            int middle = start + (finish - start) / 2;
            int result = item.compareTo(list.get(middle));

            if (result == 0) {
                return true;
            }

            if (result > 0) {
                start = middle + 1;
            } else {
                finish = middle - 1;
            }

        }

        return false;

    }

    /**
     *  This method will indicate the size of the OrderedList
     *
     * @return this is the size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     *  This method indicates if the list is empty or not.
     *
     * @return true if the list is empty, false if it is not.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }

    // **************************** Private Methods ***************************


}

