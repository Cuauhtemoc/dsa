package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{

    private  T[] items;
    public  int size;
    private  int nextFirst;
    private  int nextLast;

    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T>{
        private int cursor;
        private int fence;
        public ArrayDequeIterator(){
            cursor = nextFirst + 1;
            fence = nextLast;
        }
        @Override
        public boolean hasNext(){

            return cursor != fence;
        }
        @Override
        public T next(){
            T item = items[cursor];
            cursor = (cursor + 1) & (items.length - 1);
            return item;
        }
    }
    public ArrayDeque(){
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }
    public ArrayDeque(int size){
        items = (T[]) new Object[size];
        nextFirst = size/2;
        nextLast = size/2 + 1;
    }
    public void resize(int cap) {
        T[] a = (T[]) new Object[cap];
        int index = 0;
        for (int i = nextFirst + 1; i < size; i++) {
            a[index] = items[i];
            index++;
        }
        for (int i = 0; i <= nextLast - 1; i++) {
            a[index] = items[i];
            index++;
        }
        items = a;
        nextFirst = cap - 1;
        nextLast = size;
    }
    @Override
    public void addFirst(T item){
        if(isFull()){
            resize(items.length * 2);
        }
        items[nextFirst = (nextLast - 1 ) & (items.length - 1)] = item;
        size++;
    }
    @Override
    public void addLast(T item){
        if(isFull()){
            resize(items.length * 2);
        }
        items[nextLast = (nextLast + 1) & (items.length - 1)] = item;
        size++;
    }
    @Override
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        int index = (nextFirst + 1) & (items.length - 1);
        T item = items[index];
        items[index] = null;
        nextFirst++;
        if(size < items.length/2){
            resize(items.length / 2);
        }
        if(size == 0){
            nextFirst = 0;
            nextLast = 1;
        }
        if(nextFirst == items.length){
            nextLast = 0;
        }
        size--;
        return item;
    }
    @Override
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        T item = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast--;
        if(size < items.length/2){
            resize(items.length / 2);
        }
        if(size == 0){
            nextFirst = 0;
            nextLast = 1;
        }
        if(nextLast < 0){
            nextLast = items.length - 1;
        }
        size--;
        return item;
    }
    public boolean isFull(){
        return size == items.length;
    }

    public  boolean isEmpty(){
        return size == 0;
    }

    public void printDeque(){
        System.out.print(this);
    }
    @Override
    public String toString() {

        List<String> list = new ArrayList<>();
        for(T x: this){
            list.add(x.toString());
        }
        return "{ " + String.join(", ", list) + " }";
    }
    public boolean contains(T x){
        for(T y : this){
            if(x.equals(y)){
                return true;
            }
        }
        return false;
    }
    public T getFirst(){
        T x = items[(nextFirst + 1) & (items.length - 1)];
        if (x == null) throw new NoSuchElementException();
        return  x;
    }
    public  T getLast(){
        T x = items[(nextLast - 1) & (items.length - 1)];
        if (x == null) throw new NoSuchElementException();
        return  x;
    }
    public boolean isValidIndex(int i){
        return i <= size && i >= 0;

    }
    @Override
    public T get(int index) {
        if(!isValidIndex(index)) throw new NoSuchElementException();
        T x = items[(nextFirst  + (index  + 1 )) & (items.length - 1)];
        if (x == null) throw new NoSuchElementException();
        return  x;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof  Deque d){
            if(d.size() != this.size()){
                return false;
            }
            for(T x: this){
                if(!d.contains(x)){
                    return false;
                }
            }
        }
        return true;

    }
    @Override
    public int size(){
        return  size;
    }
    public static void main(String[] args) {

        System.out.print(127 | 127 >>> 8);

    }

}
