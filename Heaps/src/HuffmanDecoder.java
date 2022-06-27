import java.io.*;

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 * This class will make an ArrayLIst to function like the built in ArrayList.
 *
 */
public class HuffmanDecoder {

    // **************************** Fields ****************************

    private String compressedFileName;
    private String outputFileName;
    private String codesFileName;
    private MyOrderedList<CodeNode> codes;
    private byte[] encodedText;
    private String codesString;
    private String book;
    private long startTime;

    // ************************** Constructors ************************

    /**
     * This is the default constructor
     */
    public HuffmanDecoder() {

        // init Values
        compressedFileName = "WarAndPeace-compressed.bin";
        outputFileName = "WarAndPeace-decompressed.txt";
        codesFileName = "WarAndPeace-codes.txt";
        codes = new MyOrderedList<CodeNode>();
        book = "";

        // Call Methods
        readFiles();
        buildCodes();
        rebuildText();
        writeFile();
    }

    // ************************** Inner Class **************************
    private class CodeNode implements Comparable<CodeNode> {

        // **************************** CodeNode Fields ****************************

        private Character character;
        private String code;

        // **************************** CodeNode Constructors ****************************

        /**
         *  Default constructor
         */
        public CodeNode() {
            character = '\0';
            code = "";
        }

        /**
         * This constructor takes in a character
         *  and a code
         * @param character the character of the node
         * @param code the code of the node
         */
        public CodeNode(Character character, String code) {
            this.character = character;
            this.code = code;
        }

        // **************************** CodeNode Methods ***************************

        @Override
        public int compareTo(CodeNode other) {
            return (code.compareTo(other.code));
        }

        @Override
        public String toString(){
            return character + ":" +code;
        }
    }

    // **************************** Private Methods ***************************

    // This method will read the files.
    public void readFiles() {

        // Properties
        int c = 0;
        startTime = System.currentTimeMillis();

        try{

            InputStream f = new FileInputStream(compressedFileName);
            FileReader fileReader = new FileReader(codesFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            int i;


            encodedText = new byte[f.available()];
            f.read(encodedText);

            while ((i = bufferedReader.read()) != -1) {
                c++;
                stringBuilder.append((char) i);
            }
            codesString = String.valueOf(stringBuilder);
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // get the time
        startTime = System.currentTimeMillis() - startTime;

        // Print the details.
        System.out.println("Reading compressed file and codes file...  in " + startTime + " milliseconds");

    }


    // This method will build the codes from the given file.
    public void buildCodes() {

        // Properties
        startTime = System.currentTimeMillis();
        String biteStr = codesString.substring(1,codesString.length()-1);
        String[] split = biteStr.split(", ");
        String[] codesStringArr = new String[2];
        CodeNode node;

        // Split up the code string.
        for (int i = 0; i < split.length; i++){
            codesStringArr[0] = split[i].substring(0, 1);
            codesStringArr[1] = split[i].substring(2);
            node = new CodeNode(codesStringArr[0].charAt(0), codesStringArr[1]);
            codes.add(node);
        }

        // get the time
        startTime = System.currentTimeMillis() - startTime;

        // Print the details.
        System.out.println("Rebuilding codes list...  " + codes.size() + " codes found in " + startTime + " milliseconds");

    }


    // his is an optional method that extracts the bit in position pos from the byte array.
    public String getBit(int pos) {

        // Properties
        int i = 7 - pos % 8;
        byte bit = encodedText[pos / 8];

        if((bit & (1 << i)) != 0) {
            return "1";
        } else {
            return "0";
        }
    }

    // This method will use the codes to rebuild the text
    public void rebuildText() {

        // Properties
        StringBuilder stringBuilder = new StringBuilder();
        String bits = "";
        CodeNode tempNode;
        CodeNode codeNode;
        startTime = System.currentTimeMillis();

        for(int i = 0; i < encodedText.length ; i++) {

            byte bt = encodedText[i];

            for (int j = 7; j >= 0; j--) {

                if((bt & (1 << j)) != 0) {
                    bits += '1';
                } else {
                    bits += '0';
                }

                if (bits.length() >= 2) {
                    tempNode = new CodeNode('\0',bits);
                    codeNode = codes.binarySearch(tempNode);
                    if (codeNode != null) {
                        stringBuilder.append(codeNode.character);
                        bits = "";
                    }
                }
            }
        }

        // Get the value of the stringBuilder
        book = stringBuilder.toString();

        // get the time
        startTime = System.currentTimeMillis() - startTime;

        // Print the details.
        System.out.println("Decoding text... " + book.length() + " characters decoded in " + startTime + " milliseconds");

    }

    // This method will write the files.
    public void writeFile() {

        try {

            // Get the time
            startTime = System.currentTimeMillis();
            FileOutputStream outPutCode = new FileOutputStream(outputFileName);
            outPutCode.write(book.getBytes());

            // Close the file.
            outPutCode.close();

            // Get the time.
            startTime = System.currentTimeMillis() - startTime;

            // Print out the details
            System.out.println("Writing decompressed file... " + book.length() + " bytes written in " + startTime + " milliseconds");

        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
}