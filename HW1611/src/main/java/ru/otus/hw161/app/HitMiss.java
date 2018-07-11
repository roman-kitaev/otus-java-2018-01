package ru.otus.hw161.app;

/**
 * Created by rel on 25.06.2018.
 */
public class HitMiss {
    private String type = "hitmiss";
    private int hit;
    private int miss;
    private int size;
    private int maxSize;

    public HitMiss(int hit, int miss, int size, int maxSize) {
        this.hit = hit;
        this.miss = miss;
        this.size = size;
        this.maxSize = maxSize;
    }

    public HitMiss() {
    }

    @Override
    public String toString() {
        return "HitMiss{" +
                "hit=" + hit +
                ", miss=" + miss +
                ", size=" + size +
                ", maxSize=" + maxSize +
                '}';
    }
}
