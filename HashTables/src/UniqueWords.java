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
        addUniqueWordsToHashTable();
    }

    // **************************** Methods ***************************


    /**
     * This method will use a BST to find
     * unique words in the text.
     */
    public void addUniqueWordsToHashTable() {

        // Properties
        MyHashTable<String,Integer> map = new MyHashTable<>(32768);

        // Ensure the books current node is set to first.
        book.words.first();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.words.size(); i++) {

            String word = book.words.current();

            if(map.get(word) == null) {
                map.put(word, 1);
            }

            book.words.next();

        }

        // Get the time
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Adding unique words to an hash table...  in " + endTime + " milliseconds.");
        System.out.println(map.size() + " unique words");
        System.out.println(map.comparisons + " comparisons");
        System.out.println(map.maxProbe + " max probe");
        startTime = System.currentTimeMillis();
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

    }

}
