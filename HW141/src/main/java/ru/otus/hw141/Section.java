package ru.otus.hw141;

/**
 * Created by rel on 10.06.2018.
 */
public class Section {
    private final Integer begin;
    private final Integer end;
    private Integer currentPos;

    public Section(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
        this.currentPos = begin;
    }

    public Integer getBegin() {
        return begin;
    }

    public Integer getEnd() {
        return end;
    }

    public Integer getCurrentPos() {
        return isAvailable() ? currentPos : null;
    }

    public Integer getAndIncrementCurrentPos() {
        return isAvailable() ? currentPos++ : null;
    }

    private boolean isAvailable() {
        if (currentPos >= end) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Section{" +
                "begin=" + begin +
                ", end=" + end +
                ", currentPos=" + currentPos +
                '}';
    }
}
