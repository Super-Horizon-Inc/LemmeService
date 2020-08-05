package com.super_horizon.lemmein.models;

// import com.super_horizon.lemmein.converter.IntEnumConvertable;
import java.util.*;


public enum EDiscountBy {
    VISITING_TIMES(0),
    DOB(1),
    BOTH(2);
    
    private final Integer value;


    EDiscountBy(final Integer newValue) {
        this.value = newValue;
    }

    public Integer getValue() {
        return this.value;
    }

    public static Optional<EDiscountBy> valueOf(int value) {
        return Arrays.stream(values())
            .filter(legNo -> legNo.value == value)
            .findFirst();
    }

}