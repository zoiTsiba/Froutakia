package com.company;


import java.util.ArrayList;
import java.util.List;

public class Koko {

    private final int N; // number of reels
    private final List<Integer>[] elements; // array of reels
    private final int[] result; // result window

    public List<Integer> nextrow; // next row
    public List<Integer> previousrow;  // previous row

    // constructor for the arraylist of reels .
    public Koko(List<List<Integer>> list) {
        N = list.size();
        elements = new ArrayList[N];
        //For every reel add the elements.
        for (int i = 0; i < N; ++i) {
            elements[i] = new ArrayList<>();
            for (int elem : list.get(i)) {
                elements[i].add(elem);
            }
        }
        result = new int[N];
    }

    public List<Integer> get(int index) {

        List<Integer> result = new ArrayList<>();//the result combination
        List<Integer> positions = new ArrayList<>();//the positions(from the list) of the combination


        for (int i = 0; i < elements.length; i++) {

            List<Integer> counter = elements[i];

            int reelSize = counter.size(); // the size of every reel
            result.add(counter.get(index % reelSize)); // the positions of every combination
            positions.add(index % reelSize); //adding the position for later
            //System.out.println("index : " + index);

            index /= reelSize; //index will be divided with the reelSize for cycling through the reel.
        }
        //System.out.println("Positions " + " :" + positions);
        getnext(positions);// get the next row
        getprevious(positions); // get the previous row

        return result;//Collections.reverse() if you need the original order
    }

    // All possible combinations
    public int cardinalityOfCombinationSpace() {
        int size = 1;

        for (List<Integer> reel : elements)
            size *= reel.size();

        return size;
    }

    //Next row from every combination
    public List<Integer> getnext(List<Integer> positions) {
        int x;

        nextrow = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            int currentsize = elements[i].size();
            x = (positions.get(i) + 1) % currentsize; // ( Position + 1 )% N=size of reel
            nextrow.add(elements[i].get(x));
        }
        System.out.println("Next Row :" + nextrow);
        return nextrow;
    }

    //Previous row from every combination
    public List<Integer> getprevious(List<Integer> positions) {
        int x;
        previousrow = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            int currentsize = elements[i].size();
            x = (positions.get(i) - 1 + currentsize) % currentsize;//( Position + 1 + N )% N=size of reel
            previousrow.add(elements[i].get(x));

        }
        System.out.println("Previous Row :" + previousrow);
        return previousrow;
    }


}