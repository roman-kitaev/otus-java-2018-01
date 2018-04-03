package ru.otus.hw061.algorithm;

import java.util.Map;

/**
 * Created by rel on 02.04.2018.
 */
public interface Algorithm {
    Map<Integer, Integer> getSum(int sumToGet, Map<Integer, Integer> currencyAmount);
}
