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
        addUniqueWordsToLinkedList();
        addUniqueWordsToArrayList();
        addUniqueWordsToOrderedList();
    }

    // **************************** Methods ***************************

    /**
     * This method will find unique words using
     * a linked list
     */
    public void addUniqueWordsToLinkedList() {

        // Properties
        MyLinkedList<String> list = new MyLinkedList<>();

        // Ensure the books current node is set to first.
        book.words.first();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.words.size(); i++) {

            String word = book.words.current();

            if(!list.contains(word)) {

                list.addToEnd(word);

            }

            list.comparisons++;
            book.words.next();

        }


        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Adding unique words to an Linked List...  in " + endTime / 1000 + " seconds.");
        System.out.println(list.size() + " unique words");
        System.out.println(list.comparisons + " comparisons");

        startTime = System.currentTimeMillis();
        list.sort();
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Merge sorting Linked List... in " + endTime / 1000 + " seconds.\n");

    }

    /**
     * This method will find unique words using
     * an array list
     */
    public void addUniqueWordsToArrayList() {

        // Properties
        MyArrayList<String> list = new MyArrayList<>();

        // Ensure the books current node is set to first.
        book.words.first();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.words.size(); i++) {

            String word = book.words.current();

            if(!list.contains(word)) {
                list.insert(word, list.size());
            }

            list.comparisons++;
            book.words.next();

        }

        // Get the time
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Adding unique words to an Array List...  in " + endTime / 1000 + " seconds.");
        System.out.println(list.size() + " unique words");
        System.out.println(list.comparisons + " comparisons");

        // Get the time
        startTime = System.currentTimeMillis();
        list.sort();
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Merge sorting Array List... in " + endTime / 1000  + " seconds.\n");

    }

    /**
     * This method will use the ordered list to
     *  find unique words.
     */
    public void addUniqueWordsToOrderedList() {

        // Properties
        MyOrderedList<String> list = new MyOrderedList<>();

        // Ensure the books current node is set to first.
        book.words.first();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.words.size(); i++) {

            String word = book.words.current();

            if(!list.binarySearch(word)) {
                list.add(word);

            }

            list.comparisons++;
            book.words.next();

        }

        // Get the time
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Adding unique words to an Ordered List...  in " + endTime / 1000 + " seconds.");
        System.out.println(list.size() + " unique words");
        System.out.println(list.comparisons + " comparisons");

    }

}


