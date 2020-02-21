package org.potehin.linear;

public enum RatioType {
    LESS_EQUAL(1),
    EQUAL(0),
    GREATER_EQUAL(-1);

    private final float additionalValue;

    public float getAdditionalValue() {
        return additionalValue;
    }

    RatioType(float additionalValue) {
        this.additionalValue = additionalValue;
    }
}
