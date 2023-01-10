package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    @Override
    public Iterator<T> iterator() {
        return new ALLinkedListDequeIterator();
    }
    private class ALLinkedListDequeIterator implements  Iterator<T> {
        private  int pos = 0;
        public ALLinkedListDequeIterator(){
            pos = 0;
        }
        public boolean hasNext(){
              return pos < size;
        }
        public T next(){
                T item = get(pos);
                pos += 1;
                return item;
        }
    }
    private class Node{
        T item;
        Node next;
        Node prev;
        public Node(T i, Node n, Node p){
            item = i;
            next = n;
            prev = p;
        }
    }
    private int size;
    private Node sentinel;

    public LinkedListDeque(T i){
        sentinel = new Node(null, null, null);
        Node node = new Node(i, null, null);
        node.next = sentinel;
        node.prev = sentinel;
        sentinel.next = node;
        sentinel.prev = node;
        size++;

    }
    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    @Override
    public void addFirst(T i){
        Node node = new Node(i, null, null);
        if(isEmpty()){
            node.prev = sentinel;
            node.next = sentinel;
            sentinel.next = node;
            sentinel.prev = node;
        }
        else {
            node.next = sentinel.next;
            node.prev = sentinel;
            node.next.prev = node;
            sentinel.next = node;

        }
        size++;


    }
    @Override
    public void addLast(T i){
        Node node = new Node(i, null, null);
        if(isEmpty()){
            node.prev = sentinel;
            node.next = sentinel;
            sentinel.next = node;
            sentinel.prev = node;
        }
        else {
            node.prev = sentinel.prev;
            node.next = sentinel;
            node.prev.next = node;
            sentinel.prev = node;
        }

        size++;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        Node node = sentinel.next;
        T item = node.item;
        sentinel.next = node.next;
        node.next.prev = sentinel;
        node = null;
        size--;
        return item;
    }
    @Override
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        Node node = sentinel.prev;
        T item = node.item;
        sentinel.prev = node.prev;
        node.prev.next = sentinel;
        node = null;
        size--;
        return item;
    }
    @Override
    public T get(int index){
        if(!isValidIndex(index)){
            return null;
        }
        Node p = sentinel.next;
        while (index > 0) {
            p = p.next;
            --index;
        }
        return p.item;
    }
    public T getRecursive(int index){
        if(!isValidIndex(index)){
            return null;
        }
        return this._getRecursive(index, sentinel.next);
    }
    private T _getRecursive(int index, Node next){
        if(index == 0){
            return next.item;
        }
        return  this._getRecursive(index - 1, next.next);
    }
    @Override
    public void printDeque(){

        int nodes = size;
        Node p = sentinel;
        while (nodes > 0) {
            p = p.next;
            --nodes;
            System.out.print(p.item + " ");
        }
    }
    public boolean contains(T x){
        for(T y: this){
            if(x.equals(y)){
                return true;
            }
        }
        return false;
    }
//    @Override
//    public boolean equals(Object o){
//        if(o instanceof  LinkedListDeque ad){
//            if(ad.size != this.size){
//                return false;
//            }
//            for(T x: this){
//                if(!ad.contains(x)){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
    public boolean isValidIndex(int i){
        return i <= size && i >= 0;
    }

}
