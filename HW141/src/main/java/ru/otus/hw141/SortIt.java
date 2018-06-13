package ru.otus.hw141;

import java.util.Arrays;

/**
 * Created by rel on 10.06.2018.
 */
public class SortIt {
    public static int[] sort(int[] mass, int threadsCount) {
        Section[] sections = prepareSections(mass.length, threadsCount);

        sortParts(mass, threadsCount, sections);

        return mergeParts(mass, threadsCount, sections);
    }

    private static Section[] prepareSections(int arrayLength, int threadsCount) {
        int partSize = arrayLength / threadsCount;

        int currentBegin = 0;
        Section[] sections = new Section[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            sections[i] = new Section(currentBegin, currentBegin + partSize);
            currentBegin += partSize;
        }
        return sections;
    }

    private static void sortParts(int[] mass, int threadsCount, Section[] sections) {
        Thread[] threads = new Thread[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(new SortSection(mass, sections[i]));
            threads[i].start();
        }

        try {
            for(int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {System.out.println("Joining to sorting threads is interrupted!");}
    }

    private static int[] mergeParts(int[] mass, int threadsCount, Section[] sections) {
        int arrayLength = mass.length;
        int[] result = new int[arrayLength];

        Integer minElem;
        for(int j = 0; j < arrayLength; j++) {
            minElem = null;
            for(int i = 0; i < threadsCount; i++) {
                if(sections[i].getCurrentPos() != null) {
                    if(minElem != null) {
                        minElem = (minElem < mass[sections[i].getCurrentPos()] ? minElem : mass[sections[i].getCurrentPos()]);
                    } else {
                        minElem = mass[sections[i].getCurrentPos()];
                    }
                }
            }

            for(int i = 0; i < threadsCount; i++) {
                if(sections[i].getCurrentPos() != null) {
                    if(mass[sections[i].getCurrentPos()] == minElem) {
                        sections[i].getAndIncrementCurrentPos();
                        break;
                    }
                }
            }
            result[j] = minElem;
        }
        return result;
    }
}

class SortSection implements Runnable {
    private int[] array;
    private Section section;

    public SortSection(int[] array, Section section) {
        this.array = array;
        this.section = section;
    }

    @Override
    public void run() {
        Arrays.sort(array, section.getBegin(), section.getEnd());
    }

}
