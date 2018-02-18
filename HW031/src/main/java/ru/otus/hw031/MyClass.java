package ru.otus.hw031;

/**
 * Created by rel on 17.02.2018.
 */
public class MyClass {
    private int iValue;
    private boolean bValue;
    private String stringValue;

    public MyClass(int iValue, boolean bValue, String stringValue) {
        this.iValue = iValue;
        this.bValue = bValue;
        this.stringValue = stringValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + iValue;
        result = prime * result + (bValue ? 1 : 0);
        result = prime * result + (stringValue == null ? 0 : stringValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof MyClass))
            return false;
        MyClass pn = (MyClass)obj;
        return pn.iValue == iValue && pn.bValue == bValue
                && pn.stringValue.equals(stringValue);
    }

    @Override
    public String toString() {
        return "{" + iValue + " " + bValue + " " + stringValue + "}";
    }
}
