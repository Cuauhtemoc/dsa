package bstmap;

import java.security.Key;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V>  implements Map61B<K, V>{

    private class Node{
        K key;
        V value;
        int size;
        Node left;
        Node right;
        public Node(K k, V v, int s){
            key = k;
            value = v;
            size = s;

        }
    }

    private Node root;

    public BSTMap(){}
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node g = get(root, key);
        if(g != null){
            return g.value;
        }
        return null;
    }
    private Node get(Node node, K key){
        if(node == null){
            return null;
        }
        if(key.equals(node.key)){
            return node;
        }
        int compareValue = key.compareTo(node.key);
        if(compareValue < 0)  return get(node.left, key);
        else if(compareValue > 0 ) return get(node.right, key);
        else return node;
    }
    @Override
    public int size() {
        return size(root);
    }
    private int size(Node x){
        if(x == null) return 0;
        return x.size;
    }
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private Node put(Node node, K key, V value){
        if(node == null) return new Node(key, value, 1);
        int compareValue = key.compareTo(node.key);
        if(compareValue < 0) node.left = put(node.left, key, value);
        else if(compareValue > 0) node.right = put(node.right, key, value);
        else node.value = value;
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }
    @Override
    public Set keySet() {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }

    @Override
    public V remove(K key) {
        Node n = remove(root, key);
        if(n != null){
            return n.value;
        }
        return null;
    }
    private Node remove(Node node, K key){
        if(node == null) return null;
        int compareValue = key.compareTo(node.key);
        if(compareValue < 0) node.left = remove(node.left, key);
        if(compareValue > 0) node.right = remove(node.right, key);
        else {
             if(node.left == null) return node.right;
             if(node.right == null) return node.left;
        }
        return node;
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Invalid operation for sorted list.");

    }

    public static void main(String[] args) {
        BSTMap<String, Integer> map = new BSTMap<>();
        map.put("a", null);
        map.put("b", null);
        map.put("c", null);
        map.put("d", null);
        map.put("e", null);
        map.put("f", null);
        map.remove("e");


    }
}
