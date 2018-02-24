package ru.otus.hw031;

import java.util.*;

/**
 * Created by rel on 17.02.2018.
 */
public class MyHashSet<E> implements Set<E>{
    private float h = 0.75f; //load factor
    private int capacity = 10; //size of the hash table
    private int currentVolume = 0; //how many elements we have now
    private List<E>[] table = new LinkedList[capacity];
    private boolean needToResize() {
        float currentLoadFactor = (float)currentVolume / (float)capacity;
        if(currentLoadFactor > h) {
            return true;
        }
        return false;
    }
    private void resize() {
        int oldCapacity = capacity;
        capacity = capacity * 2;
        List<E>[] oldTable = table;
        table = new LinkedList[capacity];
        currentVolume = 0;
        ListIterator<E> iterator;
        E element;
        for(int i = 0; i < oldCapacity; i++) { //we need to go over the whole old table
            if(oldTable[i] != null) {
                iterator = oldTable[i].listIterator();
                while(iterator.hasNext()) {
                    element = iterator.next();
                    add(element); //and to re-hash all elements to the new table
                }
            }
        }
    }
    public boolean add(E e) {
        int pos = e.hashCode() % capacity;
        if(pos < 0) pos = -pos; //we need a positive array position
        if(table[pos] == null) {
            table[pos] = new LinkedList<>();
        } else if(table[pos].contains(e)) {
            return false;  //in case we already have this element
        }
        table[pos].add(e);
        currentVolume ++;
        if(needToResize()) resize();
        return true;
    }
    public Object[] toArray() {
        Object[] array = new Object[currentVolume];
        Iterator<E> iter = iterator();
        for(int i = 0; i < currentVolume; i++) {
           array[i] = iter.next();
        }
        return array;
    }
    public boolean containsAll(Collection<?> c) {
        Iterator<?> iter = c.iterator();
        while(iter.hasNext()) {
            if(!contains(iter.next())) {
                return false;
            }
        }
        return true;
    }
    public boolean addAll(Collection<? extends E> c) {
        Iterator<? extends E> iter = c.iterator();
        while(iter.hasNext()) {
            add(iter.next());
        }
        return true;
    }
    public boolean isEmpty() {
        return currentVolume == 0;
    }
    public int size() {
        return currentVolume;
    }
    public boolean contains(Object o) {
        if(o == null) return  false;
        int pos = o.hashCode() % capacity;
        if(pos < 0) pos = -pos; //we need a positive array position
        if(table[pos] == null) return false;
        if(!table[pos].contains(o)) return false;
        return true;
    }
    public boolean remove(Object o) {
        if(o == null) return  false;
        int pos = o.hashCode() % capacity;
        if(pos < 0) pos = -pos; //we need a positive array position
        if(table[pos] == null) return false;
        if(table[pos].remove(o)) {
            if(table[pos].size() == 0) table[pos] = null;
            currentVolume --;
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        StringBuilder builder2;
        builder.append("[");
        Iterator<E> iter = iterator();
        while(iter.hasNext()) {
            builder.append(iter.next() + ", ");
        }
        if(builder.length() > 1) { //deletes last comma and space
            builder2 = new StringBuilder(builder.subSequence(0, builder.length() - 2));
            builder = builder2;
        }
        builder.append("]");
        return builder.toString();
    }
    public Iterator<E> iterator() {
        return new MyIterator<E>(table, capacity);
    }
    public void clear() {
        table = new LinkedList[capacity];
        currentVolume = 0;
    }
    public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
