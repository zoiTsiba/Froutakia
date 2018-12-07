package com.company;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class PayNode {
      private final int [] payments;
      private final int [] tuples;
      private final int numericSymbol;

    public PayNode(int [] payments,int [] tuples,int numericSymbol) {
            this.payments = Arrays.copyOf(payments,payments.length);
            this.tuples = Arrays.copyOf(tuples,tuples.length);
            this.numericSymbol = numericSymbol;
    }

    public Iterator<PayCombo> combinations (boolean wild) {

            Queue<PayCombo> queue = new LinkedList<>();


        return queue.iterator();
    }
}
