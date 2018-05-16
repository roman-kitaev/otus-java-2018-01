package ru.otus.hw081.testobjects;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by rel on 10.05.2018.
 */
public class TestObject {
    private int iValue;
    private Integer intValue;
    private boolean bValue;
    private double fValue;
    private char chValue;
    private String sValue;
    private int[] iMass;
    private double[] dMass;
    private String[] sMass;
    private List<Integer> list;
    private Map<Integer, String> map;
    private Composed composed;
    private Composed[] composeds;
    private List<Composed> composedsList;

    public TestObject() {

    }

    public TestObject(int iValue, Integer intValue, boolean bValue, double fValue, char chValue, String sValue,
                      int[] iMass, double[] dMass, String[] sMass, List<Integer> list, Map<Integer, String> map,
                      Composed composed, Composed[] composeds, List<Composed> composedsList) {
        this.iValue = iValue;
        this.intValue = intValue;
        this.bValue = bValue;
        this.fValue = fValue;
        this.chValue = chValue;
        this.sValue = sValue;
        this.iMass = iMass;
        this.dMass = dMass;
        this.sMass = sMass;
        this.list = list;
        this.map = map;
        this.composed = composed;
        this.composeds = composeds;
        this.composedsList = composedsList;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "iValue=" + iValue +
                ", intValue=" + intValue +
                ", bValue=" + bValue +
                ", fValue=" + fValue +
                ", chValue=" + chValue +
                ", sValue='" + sValue + '\'' +
                ", iMass=" + Arrays.toString(iMass) +
                ", dMass=" + Arrays.toString(dMass) +
                ", sMass=" + Arrays.toString(sMass) +
                ", list=" + list +
                ", map=" + map +
                ", composed=" + composed +
                ", composeds=" + Arrays.toString(composeds) +
                ", composedsList=" + composedsList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return iValue == that.iValue &&
                bValue == that.bValue &&
                Double.compare(that.fValue, fValue) == 0 &&
                chValue == that.chValue &&
                Objects.equals(intValue, that.intValue) &&
                Objects.equals(sValue, that.sValue) &&
                Arrays.equals(iMass, that.iMass) &&
                Arrays.equals(dMass, that.dMass) &&
                Arrays.equals(sMass, that.sMass) &&
                Objects.equals(list, that.list) &&
                Objects.equals(map, that.map) &&
                Objects.equals(composed, that.composed) &&
                Arrays.equals(composeds, that.composeds) &&
                Objects.equals(composedsList, that.composedsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iValue, intValue, bValue, fValue, chValue, sValue, iMass, dMass, sMass, list, map, composed, composeds, composedsList);
    }
}
