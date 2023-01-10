package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private int modValue = 4;
    private double maxLoad;
    private Collection<Node>[] table;


    /** Constructors */
    public MyHashMap() {
        table = createTable(16);
        size = 0;
        maxLoad = 0.75;

    }

    public MyHashMap(int initialSize) {
        table = createTable(initialSize);
        size = 0;
        maxLoad = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        table = createTable(initialSize);
        size = 0;
        maxLoad = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear(){
        for(int i = 0; i < table.length;  i++){
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), table.length - 1);
        Collection<Node> bucket = table[index];
        if(bucket != null){
            for(Node k : bucket){
                if(key.equals(k.key)){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), table.length - 1);
        Collection<Node> bucket = table[index];
        if(bucket != null){
            for(Node k : bucket){
                if(key.equals(k.key)){
                    return k.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), table.length - 1);
        Collection<Node> bucket = table[index];
        Node node = new Node(key, value);
        if(bucket == null){
            bucket = createBucket();
            table[index] = bucket;

        }
        for(Node k : bucket){
            if(node.key.equals(k.key)){
                k.value = node.value;
                return;
            }
        }
        bucket.add(node);
        size++;
        if(size > (int)(table.length * maxLoad) ){
            resize();
        }

    }
    public void resize(){
        Collection<Node>[] t = createTable(table.length * 2);
        size = 0;
        for(int i = 0; i < table.length; i ++){
            if(table[i] != null){
                for(Node n: table[i]){
                    int index = Math.floorMod(n.key.hashCode(), t.length - 1);
                    Collection<Node> bucket = t[index];
                    if(bucket == null){
                        bucket = createBucket();
                        t[index] = bucket;

                    }
                    bucket.add(n);
                    size++;
                }
            }
        }
        table = t;

    }
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap<String, String>();
        map.put("1", "2");
        map.put("2", "5");
        map.put("3", "2");
        map.put("4", "2");
        map.put("5", "2");
        map.put("6", "2");
        map.put("7", "2");
        map.put("8", "2");
        map.put("9", "2");
        map.put("10", "2");
        map.put("11", "2");
        map.put("12", "2");
        map.put("13", "2");
        System.out.print(map);
    }
}
