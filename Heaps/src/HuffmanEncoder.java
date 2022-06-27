import java.io.*;

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * This class will make an ArrayList to function like the built in ArrayList.
 *
 */
public class HuffmanEncoder {

    // **************************** Fields ****************************

    private String inputFileName;
    private String outputFileName;
    private String codesFileName;
    private BookReader book;
    private MyOrderedList<FrequencyNode> frequencies;
    private HuffmanNode huffmanTree;
    private MyOrderedList<CodeNode> codes;
    private byte[] encodedText;
    private long startTime;
    private long endTime = 0;

    // ************************** Constructors ************************

    /**
     *  This is the default constructor.
     */
    public HuffmanEncoder() {

        // Init fields
        inputFileName = "./WarAndPeace.txt";
        outputFileName = "./WarAndPeace-compressed.bin";
        codesFileName = "./WarAndPeace-codes.txt";
        book = new BookReader(inputFileName);
        frequencies = new MyOrderedList<>();
        huffmanTree = null;
        codes = new MyOrderedList<>();
        encodedText = new byte[10];

        // Call methods
        countFrequency();
        buildTree();
        encode();
        writeFiles();

    }

    // ************************** Inner Class **************************

    // This is the frequency node
    private class FrequencyNode implements Comparable<FrequencyNode> {

        // **************************** FrequencyNode Fields ****************************
        public Character character;
        public Integer count;

        // ************************** FrequencyNode Constructors ************************

        /**
         * This is the default constructor
         */
        public FrequencyNode(Character character) {
            this.character = character;
            count = 1;
        }

        // **************************** FrequencyNode Methods ***************************

        @Override
        public int compareTo(FrequencyNode other) {
            return character.compareTo(other.character);
        }

        @Override
        public String toString() {
            return character + ":" + count;
        }
    }

    // This is the Huffman node.
    private class HuffmanNode implements Comparable<HuffmanNode> {

        // **************************** HuffmanNode Fields ****************************

        public Character character;
        public Integer weight;
        public HuffmanNode left;
        public HuffmanNode right;

        // ************************** HuffmanNode Constructors ************************

        /**
         * This constructor sets the weight and char
         */
        public HuffmanNode(Character ch, Integer wt) {
            character = ch;
            weight = wt;
            left = null;
            right = null;
        }

        /**
         * This constructor sets the left and right nodes.
         */
        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            character = null;
            weight = left.weight + right.weight;
            this.left = left;
            this.right = right;
        }

        // **************************** HuffmanNode Methods ***************************

        @Override
        public int compareTo(HuffmanNode other) {
            return weight.compareTo(other.weight);
        }

        @Override
        public String toString() {
            return character + ":" + weight;
        }
    }

    // THis is the code node
    private class CodeNode implements Comparable<CodeNode> {

        // **************************** CodeNode Fields ****************************

        public Character character;
        public String code;

        // **************************** CodeNode Constructors ****************************

        /**
         * This is the constructor that adds a string and a character
         */
        public CodeNode(Character character, String code) {
            this.character = character;
            this.code = code;
        }

        public CodeNode(Character character) {
            this.character = character;
            code = "";
        }

        // **************************** CodeNode Methods ***************************

        @Override
        public int compareTo(CodeNode other) {
            return character.compareTo(other.character);
        }

        @Override
        public String toString() {
            return character + ":" + code;
        }
    }


    // **************************** Private Methods ***************************

    // This method will count the frequency of
    // each character in the book.
    private void countFrequency() {

        // Start the time
        startTime = System.currentTimeMillis();

        for(int i = 0; i < book.book.length() - 1; i++) {

            Character c = book.book.charAt(i);
            FrequencyNode node = new FrequencyNode(c);

            // If the character is already in the list.
            if(frequencies.binarySearch(node) != null) {

                // Set the node to the current frequency of the same character
                node = frequencies.binarySearch(node);

                // Take the node out with its count
                node = frequencies.remove(node);

                // Increase the count by one
                node.count++;

                // Add the node back in.
                frequencies.add(node);

            } else {
                frequencies.add(node);
            }

        }

        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        // Display how long it took
        System.out.println("Counting frequencies of characters... " + frequencies.size() + " characters found in " + endTime  + " milliseconds.\n");

    }

    // This method will build the Huffman tree
    private void buildTree() {

        // Properties
        MyPriorityQueue<HuffmanNode> huffmanQueue = new MyPriorityQueue<>();

        // Start the time
        startTime = System.currentTimeMillis();

        // Add each frequency node into a HUffman node and into
        // a priority queue
        for(int i = 0; i < frequencies.size(); i++) {
            HuffmanNode temp = new HuffmanNode(frequencies.get(i).character,frequencies.get(i).count);
            huffmanQueue.insert(temp);
        }

        // Merge all the nodes together.
        while(huffmanQueue.size() > 1) {
            HuffmanNode temp = new HuffmanNode(huffmanQueue.removeMin(), huffmanQueue.removeMin());
            huffmanQueue.insert(temp);
        }


        // Set the root node to HuffmanTree
        huffmanTree = huffmanQueue.min();

        // Extract the codes from the tree
        extractCodes(huffmanTree, "");

        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Building a Huffman tree and reading codes...  in " + endTime + " milliseconds.\n");

    }

    // This method will extract the
    // codes from teh Huffman tree.
    private void extractCodes(HuffmanNode root, String code) {

        // Write the code.
        if(root.left == null && root.right == null) {
            codes.add(new CodeNode(root.character, code));
        } else {
            // Go down
            extractCodes(root.left, code + "0");
            extractCodes(root.right, code + "1");
        }
    }

    // This method will encode the text
    private void encode() {

        // Properties
        StringBuilder byteString = new StringBuilder();
        String bitString = "";

        // Start the time
        startTime = System.currentTimeMillis();

        // Convert the book to the code.
        try {

            File f = new File(inputFileName);
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            int c = 0;

            while((c = reader.read()) != -1) {
                Character character = (char) c;
                byteString.append(codes.binarySearch(new CodeNode(character)).code);
            }

            // Convert it to the string
            bitString = byteString.toString();

            // Split up the string and set the byte array.
            String[] bytes = bitString.split("(?<=\\G.{" + 8 + "})");
            encodedText = new byte[bytes.length];

            for(int i = 0; i < bytes.length; i += 8 ) {
                byte b = (byte) Integer.parseInt(bytes[i], 2);
                encodedText[i] = b;
            }

            // Close the reader
            reader.close();

        } catch(IOException e) {
            System.out.println("ERROR: " + e);
        }

        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Encoding message...  in " + endTime + " milliseconds.");

    }

    // This method will write the code files.
    private void writeFiles() {

        // Start the time
        startTime = System.currentTimeMillis();

        try {

            // Write the encodedText file.
            FileWriter myWriter = new FileWriter(outputFileName);

            for(int i = 0; i < encodedText.length; i++) {
                myWriter.write(encodedText[i]);
            }

            // Write the code files
            myWriter = new FileWriter(codesFileName);

            myWriter.write(codes.toString());

            // Close the file
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }

        // Get the total time it took to run.
        endTime = System.currentTimeMillis();
        endTime = endTime - startTime;

        System.out.println("Writing compressed file... " + encodedText.length + " bytes written in " + endTime + " milliseconds.\n");

    }

}
