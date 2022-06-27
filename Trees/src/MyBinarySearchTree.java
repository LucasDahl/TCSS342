/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 */
public class MyBinarySearchTree <Type extends Comparable<Type>> {

    // **************************** Fields ****************************

    private Node root;
    private int size;
    public long comparisons;
    private int height;
    private boolean redBlack;
    public long rotations;
    private Node isNewNode;

    // ************************** Constructors ************************

    /**
     * This is the default constructor.
     */
    public MyBinarySearchTree() {
        this(false);
    }

    /**
     * This constructor will make an AVL id true is passed in.
     *
     * @param redBlack determines if the tree is an AVL or not.
     */
    public MyBinarySearchTree(boolean redBlack) {
        root = null;
        size = 0;
        comparisons = 0;
        rotations = 0;
        height = 0;
        this.redBlack = redBlack;
        isNewNode = null;
    }


    // ************************** Inner Class **************************

    /**
     *  This is the node for the tree.
     */
    private class Node {

        // Properties
        public Type item;
        public Node left;
        public Node right;
        public int height;
        public int balanceFactor;
        public String color;

        public Node() {
            this(null);
        }

        public Node(Type item) {
            this.item = item;
            left = null;
            right = null;
            height = 0;
            balanceFactor = 0;
            color = "Red";
        }

        @Override
        public String toString() {
            return item + ":H" + height +  ":" + color;
        }

    }

    // **************************** Methods ***************************

    /**
     * This method will add a new item and
     * place it where it belongs.
     *
     * @param item the item to be added.
     */
    public void add(Type item) {
        root = add(item, root);
        // Ensure the root black after rebalancing.
        root.color = "Black";
    }

    /**
     * This method will remove an item from
     * the tree and fix the tree.
     *
     * @param item this is the item to be removed.
     */
    public void remove(Type item) {
        root = remove(item, root);
        if(redBlack) {
            root = rebalance(root);
        }
        // Ensure the root black after rebalancing.
        root.color = "Black";
    }

    /**
     * This method will find an item in the tree if it exists
     * and null if it doesn't.
     *
     * @param item This is the item to find in the tree.
     * @return thi is the item that was found.
     */
    public Type find(Type item) {
        return find(item, root);
    }

    /**
     * This is the height of the tree.
     *
     * @return the height of the tree.
     */
    public int height() {
        return height(root);
    }

    /**
     *  This is the size of the tree.
     *
     * @return this is the size of the tree.
     */
    public int size() {
        return size;
    }

    /**
     *  This method indicates if the tree is empty
     *  or not.
     *
     * @return indicates if the tree is empty.
     */
    public boolean isEmpty() {
        return root == null;
    }


    @Override
    public String toString() {

        if(isEmpty()) {
            return "[]";
        }

        StringBuilder str = new StringBuilder();
        printInAscendingOrder(str, root);

        return "[" + str.substring(0, str.length() - 2) + "]";
    }

    // **************************** Private Methods ***************************

    // This method will add an item to the
    // tree and maintain the order.
    private Node add(Type item, Node subtree) {

        if(subtree == null) {
            size++;
            isNewNode = new Node(item);
            subtree = isNewNode;
        } else {

            if(subtree.item.compareTo(item) > 0) {

                subtree.left = add(item, subtree.left);
                updateHeight(subtree);

                if(subtree.left.left == isNewNode || subtree.left.right == isNewNode) {
                    Node parent = subtree.left;
                    Node uncle = subtree.right;

                    if(parent.color.equals("Red") && uncle != null && uncle.color.equals("Red")) {

                        parent.color = "Black";
                        uncle.color = "Black";
                        subtree.color = "Red";
                        isNewNode = subtree;

                    } else if(parent.color.equals("Red") && (uncle == null || uncle.color.equals("Black"))) {

                        if(isNewNode == parent.right) {
                            subtree.left = rotateLeft(parent);
                            parent = subtree.left;
                        }

                        subtree.color = "Red";
                        parent.color = "Black";
                        subtree = rotateRight(subtree);
                        isNewNode = null;

                    }
                }

            } else {

                subtree.right = add(item, subtree.right);
                updateHeight(subtree);

                if(subtree.right.left == isNewNode || subtree.right.right == isNewNode) {
                    Node parent = subtree.right;
                    Node uncle = subtree.left;

                    if(parent.color.equals("Red") && uncle != null && uncle.color.equals("Red")) {

                        parent.color = "Black";
                        uncle.color = "Black";
                        subtree.color = "Red";
                        isNewNode = subtree;

                    } else if(parent.color.equals("Red") && (uncle == null || uncle.color.equals("Black"))) {

                        if(isNewNode == parent.left) {
                            subtree.right = rotateRight(parent);
                            parent = subtree.right;
                        }

                        subtree.color = "Red";
                        parent.color = "Black";
                        subtree = rotateLeft(subtree);
                        isNewNode = null;

                    }
                }
            }
        }

        return subtree;
    }

    // This method will remove a node from the tree
    private Node remove(Type item, Node subtree) {

        if (subtree == null) {
            return null;
        }

        if (item.compareTo(subtree.item) < 0) {
            subtree.left = remove(item, subtree.left);
        } else if (item.compareTo(subtree.item) > 0) {
            subtree.right = remove(item, subtree.right);
        } else {

            if (subtree.left == null) {
                return subtree.right;
            } else if (subtree.right == null) {
                return subtree.left;
            }

            // Move nodes around
            subtree.item = find(findMinValue(subtree.right));
            subtree.right = remove(subtree.item, subtree.right);

        }

        updateHeight(subtree);


        return subtree;
    }

    // This method will search the tree for an item.
    private Type find(Type item, Node subTree) {

        // Fix comparisons
        comparisons++;

        // Cannot go any deeper
        if(subTree == null) {
            return null;
        }


        if(item.compareTo(subTree.item) == 0) {
            return item;
        } else if(item.compareTo(subTree.item) < 0) {
            return find(item, subTree.left);
        } else {
            return find(item, subTree.right);
        }
    }

    // This method will find the min value.
    private Type findMinValue(Node root) {

        Type minValue = root.item;

        while (root.left != null) {
            minValue = root.left.item;
            root = root.left;
        }

        return minValue;
    }

    // This method will update the height field.
    private void updateHeight(Node node) {

        if (node == null) {
            return;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.balanceFactor = height(node.left) - height(node.right);

    }

    // This method will find the height of the tree.
    private int height(Node node) {

        if (node == null) {
            return -1;
        }

        return node.height;

    }

    // Prints the tree in ascending order.
    private void printInAscendingOrder(StringBuilder string, Node node) {

        if(node != null) {
            printInAscendingOrder(string, node.left);
            string.append( node + ", ");
            printInAscendingOrder(string, node.right);
        }

    }

    private Node rotateRight(Node node) {

        // Increment the rotation
        rotations++;

        Node tempNode = node.left;

        // Rotate
        node.left = tempNode.right;
        tempNode.right = node;


        // Update height
        updateHeight(node);
        updateHeight(tempNode);

        return tempNode;
    }

    private Node rotateLeft(Node node) {

        // Increment the rotation
        rotations++;

        Node tempNode = node.right;

        // Rotate
        node.right = tempNode.left;
        tempNode.left = node;


        // Update height
        updateHeight(node);
        updateHeight(tempNode);

        return tempNode;
    }

    private Node rebalance(Node node) {

        if(node == null) {
            return null;
        }


        if(node.balanceFactor < -1) {
            if(node.right.balanceFactor > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }

        if(node.balanceFactor > 1) {
            if(node.left.balanceFactor < 0) {
                node.left = rotateLeft(node.left);
            }
            node =  rotateRight(node);
        }

        return node;

    }
}


// **************************** AVL model ****************************

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 */
//public class MyBinarySearchTree <Type extends Comparable<Type>> {
//
//    // **************************** Fields ****************************
//
//    private Node root;
//    private int size;
//    public long comparisons;
//    private int height;
//    private boolean balancing;
//    public long rotations;
//
//    // ************************** Constructors ************************
//
//    /**
//     * This is the default constructor.
//     */
//    public MyBinarySearchTree() {
//        this(false);
//    }
//
//    /**
//     * This constructor will make an AVL id true is passed in.
//     *
//     * @param balancing determines if the tree is an AVL or not.
//     */
//    public MyBinarySearchTree(boolean balancing) {
//        root = null;
//        size = 0;
//        comparisons = 0;
//        height = 0;
//        this.balancing = balancing;
//    }
//
//    // ************************** Inner Class **************************
//
//    /**
//     *  This is the node for the tree.
//     */
//    private class Node {
//
//        // Properties
//        public Type item;
//        public Node left;
//        public Node right;
//        public int height;
//        public int balanceFactor;
//
//        public Node(Type item) {
//            this.item = item;
//            left = null;
//            right = null;
//            height = 0;
//            balanceFactor = 0;
//        }
//
//        @Override
//        public String toString() {
//            return item + ":H" + height + ":B" + balanceFactor;
//        }
//
//    }
//
//    // **************************** Methods ***************************
//
//    /**
//     * This method will add a new item and
//     * place it where it belongs.
//     *
//     * @param item the item to be added.
//     */
//    public void add(Type item) {
//        root = add(item, root);
//    }
//
//    /**
//     * This method will remove an item from
//     * the tree and fix the tree.
//     *
//     * @param item this is the item to be removed.
//     */
//    public void remove(Type item) {
//        root = remove(item, root);
//        if(balancing) {
//            root = rebalance(root);
//        }
//    }
//
//    /**
//     * This method will find an item in the tree if it exists
//     * and null if it doesn't.
//     *
//     * @param item This is the item to find in the tree.
//     * @return thi is the item that was found.
//     */
//    public Type find(Type item) {
//        return find(item, root);
//    }
//
//    /**
//     * This is the height of the tree.
//     *
//     * @return the height of the tree.
//     */
//    public int height() {
//        return height(root);
//    }
//
//    /**
//     *  This is the size of the tree.
//     *
//     * @return this is the size of the tree.
//     */
//    public int size() {
//        return size;
//    }
//
//    /**
//     *  This method indicates if the tree is empty
//     *  or not.
//     *
//     * @return indicates if the tree is empty.
//     */
//    public boolean isEmpty() {
//        return root == null;
//    }
//
//
//    @Override
//    public String toString() {
//
//        if(isEmpty()) {
//            return "[]";
//        }
//
//        StringBuilder str = new StringBuilder();
//        printInAscendingOrder(str, root);
//
//        return "[" + str.substring(0, str.length() - 2) + "]";
//    }
//
//    // **************************** Private Methods ***************************
//
//    // This method will add an item to the
//    // tree and maintain the order.
//    private Node add(Type item, Node subtree) {
//
//        if (subtree == null) {
//            size++;
//            return new Node(item);
//        }
//
//        if (item.compareTo(subtree.item) < 0) {
//            subtree.left = add(item, subtree.left);
//        } else if (item.compareTo(subtree.item) > 0) {
//            subtree.right = add(item, subtree.right);
//        }
//
//        updateHeight(subtree);
//
//        if(balancing) {
//            subtree = rebalance(subtree);
//        }
//
//
//        return subtree;
//
//    }
//
//    // This method will remove a node from the tree
//    private Node remove(Type item, Node subtree) {
//
//        if (subtree == null) {
//            return null;
//        }
//
//        if (item.compareTo(subtree.item) < 0) {
//            subtree.left = remove(item, subtree.left);
//        } else if (item.compareTo(subtree.item) > 0) {
//            subtree.right = remove(item, subtree.right);
//        } else {
//
//            if (subtree.left == null) {
//                return subtree.right;
//            } else if (subtree.right == null) {
//                return subtree.left;
//            }
//
//            // Move nodes around
//            subtree.item = find(findMinValue(subtree.right));
//            subtree.right = remove(subtree.item, subtree.right);
//
//        }
//
//        updateHeight(subtree);
//
//        if(balancing) {
//            subtree = rebalance(subtree);
//
//        }
//
//        updateHeight(subtree);
//
//        return subtree;
//    }
//
//    // This method will search the tree for an item.
//    private Type find(Type item, Node subTree) {
//
//        // Fix comparisons
//        comparisons++;
//
//        // Cannot go any deeper
//        if(subTree == null) {
//            return null;
//        }
//
//
//        if(item.compareTo(subTree.item) == 0) {
//            return item;
//        } else if(item.compareTo(subTree.item) < 0) {
//            return find(item, subTree.left);
//        } else {
//            return find(item, subTree.right);
//        }
//    }
//
//    // This method will find the min value.
//    private Type findMinValue(Node root) {
//
//        Type minValue = root.item;
//
//        while (root.left != null) {
//            minValue = root.left.item;
//            root = root.left;
//        }
//
//        return minValue;
//    }
//
//    // This method will update the height field.
//    private void updateHeight(Node node) {
//
//        if (node == null) {
//            return;
//        }
//
//        node.height = 1 + Math.max(height(node.left), height(node.right));
//        node.balanceFactor = height(node.left) - height(node.right);
//
//    }
//
//    // This method will find the height of the tree.
//    private int height(Node node) {
//
//        if (node == null) {
//            return -1;
//        }
//
//        return node.height;
//
//    }
//
//    // Prints the tree in ascending order.
//    private void printInAscendingOrder(StringBuilder string, Node node) {
//
//        if(node != null) {
//            printInAscendingOrder(string, node.left);
//            string.append( node + ", ");
//            printInAscendingOrder(string, node.right);
//        }
//
//    }
//
//    private Node rotateRight(Node node) {
//
//        // Increment the rotation
//        rotations++;
//
//        Node tempNode = node.left;
//
//        // Rotate
//        node.left = tempNode.right;
//        tempNode.right = node;
//
//
//        // Update height
//        updateHeight(node);
//        updateHeight(tempNode);
//
//        return tempNode;
//    }
//
//    private Node rotateLeft(Node node) {
//
//        // Increment the rotation
//        rotations++;
//
//        Node tempNode = node.right;
//
//        // Rotate
//        node.right = tempNode.left;
//        tempNode.left = node;
//
//
//        // Update height
//        updateHeight(node);
//        updateHeight(tempNode);
//
//        return tempNode;
//    }
//
//    private Node rebalance(Node node) {
//
//        if(node == null) {
//            return null;
//        }
//
//
//        if(node.balanceFactor < -1) {
//            if(node.right.balanceFactor > 0) {
//                node.right = rotateRight(node.right);
//            }
//            node = rotateLeft(node);
//        }
//
//        if(node.balanceFactor > 1) {
//            if(node.left.balanceFactor < 0) {
//                node.left = rotateLeft(node.left);
//            }
//            node =  rotateRight(node);
//        }
//
//        return node;
//
//    }
//
//}


