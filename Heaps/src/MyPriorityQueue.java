public class MyPriorityQueue <Type extends Comparable<Type>> {

    // **************************** Fields ****************************

    private MyArrayList<Type> heap;

    // ************************** Constructors ************************

    /**
     *  This is the default constructor
     */
    public MyPriorityQueue() {
        heap = new MyArrayList<>();
    }

    // **************************** Methods ***************************

    /**
     * This method will add an item to the back of the heap
     * and then correct the order.
     *
     * @param item the item to be added.
     */
    public void insert(Type item) {

        if(item == null) {
            return;
        }

        // Add the item at the back.
        heap.insert(item, heap.size());

        // Fix the invariant
        bubbleUp();
    }

    /**
     * This method will remove the first element in the heap.
     *
     * @return the first element in the heap.
     */
    public Type removeMin() {

        if(isEmpty()) {
            return null;
        }

        // Swap the first and last element.
        Type temp = min();
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        // Fix the invariant
        sinkDown();

        return temp;
    }

    /**
     * This method will return the first element in the heap.
     *
     * @return the first element in the heap.
     */
    public Type min() {

        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    /**
     * This method will indicate the size of
     * the heap.
     *
     * @return indicates the size.
     */
    public int size() {
        return heap.size();
    }

    /**
     *  This method will  indicate if the
     *  heap is empty.
     *
     * @return indicates if the heap is empty.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    // **************************** Private Methods ***************************

    // This method will bubble up the last element
    // to fix the invariant
    private void bubbleUp() {

        int parentIndex = parent(heap.size() - 1);
        int bubbleIndex = heap.size() - 1;

        while(heap.get(bubbleIndex).compareTo(heap.get(parentIndex)) < 0) {

            // Swap the elements
            swap(parentIndex, bubbleIndex);

            bubbleIndex = parentIndex;
            parentIndex = parent(parentIndex);

        }

    }

    // This method will sink down the first element
    // to fix the invariant
    private void sinkDown() {

        if(isEmpty()) {
            return;
        }

        int sinkIndex = 0;

        while(hasLeftLeaf(sinkIndex)) {

            Type current = heap.get(sinkIndex);
            Type left = heap.get(left(sinkIndex));
            Type right = heap.get(right(sinkIndex));

            if (current.compareTo(left) > 0 || current.compareTo(right) > 0) {

                int child;
                if (hasRightLeaf(sinkIndex) && right.compareTo(left) <= 0) {
                    child = right(sinkIndex);
                } else {
                    child = left(sinkIndex);
                }

                swap(sinkIndex, child);
                sinkIndex = child;
            } else {
                break;
            }
        }
    }

    // Swaps two elements
    private void swap(int n, int m) {
        Type temp = heap.get(n);
        heap.set(n, heap.get(m));
        heap.set(m , temp);
    }

    private boolean hasRightLeaf(int index) {
        return right(index) < heap.size();
    }

    private boolean hasLeftLeaf(int index) {
        return left(index) < heap.size();
    }


    // This is the index of the parent
    // of a given element by index
    private int parent(int index) {
        if(isEmpty()) {
            return 0;
        }

        return (int) Math.floor((index - 1) / 2);
    }

    // This is the right element of
    // a given index
    private int right(int index) {
        if(isEmpty()) {
            return 0;
        }

        return (2 * index) + 2;
    }

    // This is the left element of
    // a given index
    private int left(int index) {
        if(isEmpty()) {
            return 0;
        }

        return (2 * index) + 1;
    }

}
