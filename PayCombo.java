package com.company;

import java.util.Arrays;

public class PayCombo {
    private final int [] combination ;
    private final int value;

    public PayCombo(int[] combination, int value) {
        this.combination = Arrays.copyOf(combination,combination.length);
        this.value = value;
    }

    public int[] getCombination() {
        return combination;
    }

    public int getValue() {
        return value;
    }
}
