/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * @param <Type> the type of element in the list.
 */
public class MyLinkedList <Type extends Comparable<Type>> {

    // **************************** Fields ****************************

    private Node first;
    private Node current;
    private Node previous;
    private Node end;
    private int size;
    public int comparisons;

    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public MyLinkedList() {
        first = null;
        current = null;
        previous = null;
        size = 0;
        comparisons = 0;
    }

    // ************************** Inner Class **************************

    private class Node {

        // Properties
        public Type item;
        public Node next;

        public Node(Type item) {
            this.item = item;
            next = null;
        }

        @Override
        public String toString() {
            return item.toString();
        }

    }

    // **************************** Methods ***************************

    /**
     * This method will add an element before the current node.
     *
     * @param item this is the item to be added.
     */
    public void addBefore(Type item) {

        // If the list is empty add the item to the first node
        // if not empty add the node in the correct position
        if(first == null || first == current) {
            first = new Node(item);
            first.next = current;
            previous = first;

        } else {

            // Create the node
            Node tempNode = new Node(item);

            // Set the temp node's next ot be the current(to add before)
            tempNode.next = current;

            if(previous != null) {
                // Link the previous node to the temp node(to connect the list)
                previous.next = tempNode;
            }

            // Set the previous node to be behind the current node.
            previous = tempNode;

        }

        // Increase the size.
        size++;

    }

    /**
     * This method will add a new item after the current
     *
     * @param item this is the item to add into the linked list
     */
    public void addAfter(Type item) {

        if(current != null) {

            // add the node
            Node tempNode = new Node(item);
            tempNode.next = current.next;
            current.next = tempNode;

            // Increase the size.
            size++;

        }

    }

    /**
     *This method will return the item on the current node.
     *
     * @return the item of the current node
     */
    public Type current() {

        if(current == null) {
            return null;
        }
        return current.item;
    }

    /**
     *This method sets the current node to the first
     * and then returns the item.
     *
     * @return the item of the first node
     */
    public Type first() {
        current = first;
        previous = null;
        return current.item;
    }

    /**
     * This method will return null if there is no next
     * and if there is it will set the current node to the next
     * node and return the item.
     *
     * @return the next node's item.
     */
    public Type next() {

        // If the next node exists set current to the next node
        // then return the item
        if(current.next != null) {
            previous = current;
            current = current.next;
            return current.item;
        }

        return null;
    }

    /**
     * This method will remove the current node
     * and return the item of that node.
     *
     * @return this is the item of the current node.
     */
    public Type remove() {

        if(current == null || first == null) {
            return null;
        }

        // Get a reference tto the current node
        Type temp = current.item;

        if(previous != null) {
            // Remove the current node
            previous.next = current.next;
            current = current.next;
        } else if(current == first) {
            first = current.next;
            current = first;
        }


        // Decrease the size
        size--;


        return temp;
    }


    /**
     * This method checks if an item is in the list.
     *
     * @param item this is the item that will be checked for in the list.
     * @return weather the item is in the list or not.
     */
    public boolean contains(Type item) {

        if (first == null || isEmpty()) {
            return false;
        }

        // Properties
        Node traversalNode = first;

        // See if the list contains the node
        while (traversalNode != null) {

            comparisons++;

            if(traversalNode.item.compareTo(item) == 0) {
                return true;
            }

            // Move to the next node.
            traversalNode = traversalNode.next;

        }

        return false;
    }

    /**
     *This method returns the size of the linked list
     *
     * @return the size of the linked list
     */
    public int size() {
        return size;
    }

    /**
     * This method will let the user know if the list
     * is empty or not.
     *
     * @return indicates if the list is empty or not.
     */
    public boolean isEmpty() {

        if (size > 0) {
            return false;
        }

        return true;
    }

    /**
     * This method will add a node at the end of the list.
     *
     * @param item the item to add
     */
    public void addToEnd(Type item) {

        if(size == 0) {
            first = new Node(item);
            first();
            end = first;
        } else {

            // Ensure the node is at the end
            while(end.next != null) {
                end = end.next;
            }

            Node temp = new Node(item);

            end.next = temp;
        }

        size++;

    }

    /**
     *  This method will swap the current and previous
     *  items.
     */
    public void swapWithPrevious() {

        Type temp = current.item;
        current.item = previous.item;
        previous.item = temp;

    }

    @Override
    public String toString() {

        if(isEmpty()) {
            return "[]";
        }

        // Properties
        String listStr = "";

        Node traversalNode = first;

        while(traversalNode != null) {

            // Create the string
            listStr += "," + traversalNode.item;

            // Move to the next node
            traversalNode = traversalNode.next;

        }

        return "[" + listStr.substring(1) + "]";
    }

    /**
     * This method will merge and sort the list.
     */
    public void sort() {
        first = sort(first);
    }

    // **************************** Private Methods ***************************

    // This method will sort the nodes
    private Node sort(Node first) {

        if(first == null || first.next == null) {
            return first;
        }

        Node middle = getMiddle(first);
        Node afterMiddle = middle.next;

        middle.next = null;

        Node leftSide = sort(first);
        Node rightSide = sort(afterMiddle);

        Node sortedNodes = merge(leftSide, rightSide);

        return sortedNodes;


    }

    // This method will get the middle node
    private Node getMiddle(Node first) {

        if (first == null)
            return first;

        Node slow = first, fast = first;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    // This method will merge the two linked lists.
    private Node merge(Node left, Node right) {

        Node tempNode = null;

        if(left == null) {
            return right;
        }

        if(right == null) {
            return left;
        }

        if(left.item.compareTo(right.item) < 0) {
            tempNode = left;
            tempNode.next = merge(left.next, right);
        } else {
            tempNode = right;
            tempNode.next = merge(left, right.next);
        }

        return tempNode;

    }



}