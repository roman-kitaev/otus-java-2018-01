package ru.otus.hw031;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Created by rel on 24.02.2018.
 */
public class MyIterator<E> implements Iterator<E> {
    private ListIterator<E> iter = null; //iterator for elements in lists (buckets)
    private List<E>[] table;
    private int capacity;
    private Integer currBucket; //current non-null bucket
    public MyIterator(List<E>[] table, int capacity) {
        this.table = table;
        this.capacity = capacity;
        currBucket = getNextBucket(0);
        if(currBucket != null) iter = table[currBucket].listIterator();
    }
    @Override
    public boolean hasNext() {
        if(iter == null) return false;
        //if there is no elements in current bucket and there are no filled buckets
        //in the table
        if(!iter.hasNext() && getNextBucket(currBucket + 1) == null) {
            return false;
        }
        return true;
    }
    @Override
    public E next() {
        try {
            return iter.next();
        } catch (NullPointerException e) { //in case iter == null
            throw new NoSuchElementException(); //to be like a standard HashSet
        } catch (NoSuchElementException e) { //in case current bucket is empty
            currBucket = getNextBucket(currBucket + 1); //searching fot the next bucket
            if(currBucket != null) { //go to the next bucket
                iter = table[currBucket].listIterator();
                return next(); //next again in the new bucket
            } else throw e; //if there is no any filled bucket at all
        }
    }
    //returns number of the next filled bucket (from startPos):
    private Integer getNextBucket(int startPos) {
        for(int i = startPos; i < capacity; i++) {
            if(table[i] != null) return i;
        }
        return null;
    }
}
