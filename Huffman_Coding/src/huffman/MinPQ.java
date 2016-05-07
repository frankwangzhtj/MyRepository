package huffman;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class MinPQ<Key>{
    private Key[] pq;
    private int N; 
    private Comparator<Key> comparator; 

	public MinPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity+1];
        N = 0;
    }

    public MinPQ() {
        this(1);
    }

    public MinPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    public MinPQ(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < N; i++)
            pq[i+1] = keys[i];
        for (int k = N/2; k >= 1; k--)
            heapDown(k);
    }

    public boolean isEmpty5345() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key min() {
        if (isEmpty5345()) throw new NoSuchElementException("The priority queue is empty!");
        return pq[1];
    }

    private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= N; i++) 
        	temp[i] = pq[i];
        pq = temp;
    }
    public void insert(Key x) {
        if (N == pq.length - 1) 
        	resize(2 * pq.length);
        N++;
        pq[N] = x;
        heap(N);
    }

    public Key delMin() {
        if (isEmpty5345()) throw new NoSuchElementException("The priority queue is empty!");
        swap(1, N);
        Key min = pq[N];
        pq[N] = null;
        N--;
        heapDown(1);
        if ((N > 0) && (N == (pq.length - 1) / 4)) 
        	resize(pq.length  / 2);
        return min;
    }

    private void heap(int k) {
        while (k > 1 && greater(k/2, k)) {
            swap(k, k/2);
            k = k/2;
        }
    }
    private void heapDown(int k) {
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void swap(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    private boolean isMinHeap(int k) {
        if (k > N) 
        	return true;
        int left = 2*k, right = 2*k + 1;
        if (left  <= N && greater(k, left))  return false;
        if (right <= N && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }
    private boolean isMinHeap() {
        return isMinHeap(1);
    }

   
}