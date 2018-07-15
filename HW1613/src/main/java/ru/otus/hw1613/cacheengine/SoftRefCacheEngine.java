package ru.otus.hw1613.cacheengine;


import ru.otus.hw1611.app.DataSet;
import ru.otus.hw1611.app.HitMiss;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rel on 30.04.2018.
 */
public class SoftRefCacheEngine<T extends DataSet> {
    private final Map<Long, SoftReference<T>> elements = new LinkedHashMap<>();
    private final int maxElements;
    int hit = 0;
    int miss = 0;

    public SoftRefCacheEngine(int maxElements) {
        this.maxElements = maxElements;
    }

    public void put(T element) {
        System.out.println("Putting element to cache");

        if (elements.size() == maxElements) {
            Long firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        elements.put(element.getId(), new SoftReference<>(element));
    }

    public T get(Long id) {
        SoftReference<T> reference = elements.get(id);
        if(reference == null) {
            miss++;
            return null;
        }
        T element = reference.get();
        if(element == null) {
            miss++;
        } else {
            hit++;
        }
        return element;
    }
    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    public int getMaxElements() {
        return maxElements;
    }

    public int getSize() {
        return elements.size();
    }

    public HitMiss getHitMiss() {
        return new HitMiss(getHitCount(), getMissCount(), getSize(), getMaxElements());
    }
}
