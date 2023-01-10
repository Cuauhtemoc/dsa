package gh2;


// TODO: uncomment the following import once you're ready to start this portion
// TODO: maybe more imports

import deque.ArrayDeque;
import deque.Deque;
import jdk.jfr.Frequency;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */

     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {

        int cap = (int) Math.round(SR/ frequency);
        buffer = new ArrayDeque<>(cap);
        for (int i = 0; i < cap; i++) {
            buffer.addFirst((double) 0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.

        for (int i = 0; i < buffer.size(); i++) {
            double d = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addLast(d);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {

        double d = buffer.removeFirst();
        double first = buffer.get(0);
        double newDouble = (d + first) * 0.5 * DECAY;
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {

        return buffer.get(0);
    }
}
