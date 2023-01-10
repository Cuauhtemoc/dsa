package deque;

import java.util.Comparator;

public class MaxComparator implements Comparator<Integer> {

    public int compare(Integer a, Integer b) {
        return a - b;

    }
}
