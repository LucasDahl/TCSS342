import java.util.HashSet;
import java.util.Set;

/**
 * @author Lucas D. Dahl
 * @version TCSS 342 B Spring 2022
 *
 */
public class MyHashTable <Key, Value>{

    // **************************** Fields ****************************

    private Integer capacity;
    private Key[] keyBuckets;
    private Value[] valueBuckets;
    private Integer size;
  //  public Set<Integer> set = new HashSet<>();

    public MyArrayList<Key> keys;
    public Integer comparisons;
    public Integer maxProbe;


    // ************************** Constructors ************************

    /**
     * This is the default constructor.
     */
    public MyHashTable(Integer capacity) {
        this.capacity = capacity;
        keyBuckets = (Key[]) new Object[capacity];
        valueBuckets = (Value[])  new Object[capacity];
        size = 0;
        keys = new MyArrayList<>();
        comparisons = 0;
        maxProbe = 0;
    }

    // **************************** Methods ***************************

    /**
     * This method will retrieve a value from a
     * given key, or null if the key doesn't exist.
     *
     * @param key the key to look for a value.
     * @return the value from the given key.
     */
    public Value get(Key key) {

        int index = hash(key);

        while(keyBuckets[index] != null) {

            if(keyBuckets[index].equals(key)) {
                return valueBuckets[index];
            }
            index = (index + 1) % capacity;
        }

        return null;
    }

    /**
     * This method will put a value based off a given key.
     *
     * @param key the unique key
     * @param value the value to store.
     */
    public void put(Key key, Value value) {

        // Get the index
//        int index = hash(key);
//        int tempProbe = 0;
//        int updateValue = 0;
//
//        do {
//
//            // Increment
//            tempProbe++;
//            comparisons++;
//            updateValue++;
//
//            // Add the new value
//            if(keyBuckets[index] == null) {
//
//                // Set the key and the value
//                keyBuckets[index] = key;
//                valueBuckets[index] = value;
//
//                // Increase the size
//                size++;
//
//                // Add the key to the array list
//                keys.insert(key, size - 1);
//
//                return;
//            }
//
//            // Update the value
//            if(keyBuckets[index].equals(key)) {
//                valueBuckets[index] = value;
//                comparisons = comparisons - updateValue;
//                return;
//            }
//
//            index = (index + 1) % capacity;
//
//
//            if(tempProbe >= maxProbe) {
//                maxProbe++;
//            }
//
//
//        } while(index != hash(key));
        // Get the index
        int tempProbe = 1;
        int index = hash(key);
        int updateValue = 0;

        while(keyBuckets[index] != null) {
            if(keyBuckets[index].equals(key)) {
                comparisons = comparisons - updateValue;
                valueBuckets[index] = value;
                break;
            }

            index = (index + 1) % capacity;
            tempProbe++;
            comparisons++;

        }

        if(keyBuckets[index] == null) {
            valueBuckets[index] = value;
            keyBuckets[index] = key;
            size++;
            comparisons++;
            keys.insert(key, size - 1);
        }

        maxProbe = Math.max(maxProbe, tempProbe);


    }

    /**
     *  This method returns the size of the hash table
     *
     * @return the size of the hash table
     */
    public Integer size() {
        return size;
    }

    @Override
    public String toString() {

        if(size <= 0) {
            return "[]";
        }

        String str = "";

        for(int i = 0; i < capacity; i++) {
            if(keyBuckets[i] != null) {
                str += ", " + keyBuckets[i] + ":" + valueBuckets[i];
            }
        }

        return "[" + str.substring(2) + "]";
    }


    // **************************** Private Methods ***************************

    // THis method will hash the key for an index.
    private Integer hash(Key key) {
        return Math.abs(key.hashCode() % capacity);
    }

}
