/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 */
public class UniqueWords {

    // **************************** Fields ****************************

    private BookReader book;
    private long startTime;
    private long endTime = 0;

    // ************************** Constructors ************************

    /**
     * This is the default constructor.
     */
    public UniqueWords() {
        book = new BookReader("WarAndPeace.txt");
        addUniqueWordsToBST();
    }

    // **************************** Methods ***************************


    /**
     * This method will use a BST to find
     * all the unique words in the text.
     */
    public void addUniqueWordsToBST() {

        // Properties
        MyBinarySearchTree<String> list = new MyBinarySearchTree<>();

        // Ensure the books current node is set to first.
        book.words.first();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.words.size(); i++) {

            String word = book.words.current();

            if(list.find(word) == null) {
                list.add(word);
            }

            book.words.next();

        }

        // Get the time
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Adding unique words to an Binary search tree...  in " + endTime / 1000 + " seconds.");
        System.out.println(list.size() + " unique words");
        System.out.println("The binary search tree had a height of " + list.height() + " and made " + list.comparisons + " comparisons");
        System.out.println("Traversing the binary tree search tree... in " + endTime + " milliseconds");

    }

}
