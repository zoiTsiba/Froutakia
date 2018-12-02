package com.company;

import java.util.*;


public class Basegame {

    //Paylines
    public List<Integer> symbols;
    public List<Integer> sequenceWinnings;
    public List<Integer> paytable;
    public int hits = 0;


    public Basegame() {
        symbols = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        sequenceWinnings = Arrays.asList(2, 3, 4, 5);
        paytable = Arrays.asList(2, 5, 25, 100);
    }

    public void sequence() {
        for (int i = 0; i < symbols.size(); i++) {
            Trie trie = new Trie();
            for (Integer temp : sequenceWinnings) {
                int[] currentLine = new int[temp];
                Arrays.fill(currentLine, symbols.get(i));
                System.out.println("arrays = " + Arrays.toString(currentLine));
                trie.put(currentLine, 111111111);
                System.out.println("trie value = " + trie.get(currentLine));
            }
        }
    }

    public int sethit() {
        hits += 1;
        return hits;
    }

    public static void main(String[] args) {

        Basegame spin = new Basegame();
        spin.sequence();


    }

}