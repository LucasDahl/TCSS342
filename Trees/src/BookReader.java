import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 *
 *
 */
public class BookReader {

    // **************************** Fields ****************************

    public String book;
    public MyLinkedList<String> words;

    // ************************** Constructors ************************

    /**
     * This constructor will read a book
     * from a filename.
     */
    public BookReader(String filename) {
        book = "";
        words = new MyLinkedList<String>();

        try {
            readBook(filename);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        parseWords();

    }

    // **************************** Methods ***************************

    /**
     * This method will read the file by character
     * and put it into the variable book.
     *
     * @param filename the file to read.
     */
    public void readBook(String filename) throws IOException {

        // Properties
        long startTime, endTime = 0;
        File file = new File(filename);
        FileReader fileReader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        int CharNum = 0;

        // Start the time
        startTime = System.currentTimeMillis();

        try{

            while((CharNum = reader.read()) != -1) {
                stringBuilder.append((char)CharNum);

            }

            // Get the total time it took to run.
            endTime = System.currentTimeMillis();
            endTime = endTime - startTime;

            reader.close();

            book = String.valueOf(stringBuilder);
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }

        // Display the info for reading the file.
        System.out.println("Reading input file \"" + filename + "\" ... " + book.length() + " Characters in " + endTime + " milliseconds.\n");

    }

    /**
     * This method will parse the words in the book.
     */
    public void parseWords() {

        // Properties
        long startTime, endTime = 0;
        StringBuilder stringBuilder = new StringBuilder();
        book.trim();

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.length(); i++) {
            Character c = book.charAt(i);

            // Check if the character is part of our defined "What is a word"
            if(c.compareTo('A') >= 0 && c.compareTo('Z') <= 0
                    || c.compareTo('a') >= 0 && c.compareTo('z') <= 0
                    || c.compareTo('0') >= 0 && c.compareTo('9') <= 0
                    || c.equals('\'')) {
                stringBuilder.append(c);

            } else if(stringBuilder.length() > 0) {
                words.addBefore(stringBuilder.toString());
                stringBuilder.setLength(0);
            }


        }

        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        // Display the info for reading the file.
        System.out.println("Finding words and adding them to a linked list.... in " + endTime + " milliseconds");
        System.out.println("The linked list has a length of " + words.size() + ".\n");

    }



}
