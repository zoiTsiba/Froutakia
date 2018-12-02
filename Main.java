package com.company;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        int Totalprize = 0;
        double RTP = 0;
        int hits = 0;
        double hitrate;
        DecimalFormat df2 = new DecimalFormat(".##");
        int count = 0;
        Basegame spin = new Basegame();

        Totalprize = 0;


        Koko counter = new Koko(
                Arrays.asList(
//                        Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
//                        Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
//                        Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
//                        Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
//                        Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                        Arrays.asList(1, 2, 3, 4, 5, 6),
                        Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                        Arrays.asList(1, 2, 3, 4, 5, 6),
                        Arrays.asList(1, 2, 3, 4, 5),
                        Arrays.asList(1, 2, 3, 4, 5)


                ));

        for (int i = 0; i < counter.cardinalityOfCombinationSpace(); i++) {

            // System.out.println("card "+counter.cardinalityOfCombinationSpace() );
            List<Integer> result = counter.get(i);

            System.out.println("Combination " + (i + 1) + " :" + result + "\n");


            count += 1;

            //Totalprize = counter.getTotalprize();
            //System.out.println(Totalprize);
        }

        hits = spin.sethit() - 1;
        System.out.println("Hits " + hits);
        hitrate = (double) count / hits;
        System.out.println("Hit Rate " + df2.format(hitrate));
        RTP = (double) Totalprize / (count);
        System.out.println("RTP = " + RTP);
        System.out.println("TotalPrize = " + Totalprize);
        System.out.println("Combinations are :" + count);
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        float elapsedTimeSec = elapsedTimeMillis / 1000F;
        System.out.println("Time :" + elapsedTimeSec + "s");

    }

}

