package com.company;


import java.util.*;

public class FullCycle {

    private List<List<Integer>> elements;  // List of reels
    public List<Integer> firstrow = new ArrayList<>();
    public List<Integer> secondrow = new ArrayList<>();
    public List<Integer> thirdrow = new ArrayList<>();

    Basegame spin = new Basegame();


    public FullCycle(List<List<Integer>> elements) {

        this.elements = Collections.unmodifiableList(elements);

    }


    public List<Integer> get(int index) {

        List<Integer> result = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();


        for (int i = 0; i < elements.size(); i++) {


            List<Integer> counter = elements.get(i);

             int counterSize = counter.size();

            result.add(counter.get(index % counterSize));

            positions.add(index % counterSize);


            index /= counterSize;


        }
        System.out.println("Positions " + " :" + positions);


        getnext(positions,elements.size());
        getprevious(positions,elements.size());


        return result;//Collections.reverse() if you need the original order

    }


    public int cardinalityOfCombinationSpace() {
        int size = 1;

        for (List<Integer> reel : elements)
            size *= reel.size();

        return size;
    }


    public  int  getnext(List<Integer> positions,int N) {
        int x;
        secondrow = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            x = (positions.get(i) + 1) % N;

            secondrow.add(elements.get(i).get(x));

        }
        System.out.println(secondrow);
        return 0;
    }

    public int getprevious(List<Integer> positions,int N) {
        int x;
        thirdrow = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            x = (positions.get(i) - 1 + N) % N;
            thirdrow.add(elements.get(i).get(x));

        }
        System.out.println(thirdrow);
        return 0;

    }
}

