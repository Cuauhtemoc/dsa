package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        comparator = c;

    }
    public T max(){
        if(isEmpty()){
            return null;
        }
        T m = get(0);
        for(T x: this){
            T element = x;
            if(comparator.compare(element, m) > 0){
                m = element;
            }
        }
        return m;
   }
    public static void main(String[] args) {
        MaxArrayDeque<Integer> d = new MaxArrayDeque(new MaxComparator());
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(99);
        d.addFirst(5);
        d.addFirst(6);
        d.addFirst(7);


        System.out.print(d.max());
    }
}


