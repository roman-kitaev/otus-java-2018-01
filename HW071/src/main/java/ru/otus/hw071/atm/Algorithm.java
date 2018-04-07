package ru.otus.hw071.atm;

import java.util.Map;

/**
 * Created by rel on 02.04.2018.
 */
public interface Algorithm {
    Map<Nominal, Integer> getSum(int sumToGet, Map<Nominal, Integer> currencyAmount);
}
