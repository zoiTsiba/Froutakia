package com.company;


import java.util.HashMap;
import java.util.Map;

public class Trie {


    private Node root;      // root of trie
    private int n;          // number of keys in trie
    private int max;

    // R-way trie node
    private static class Node {
        private int key;
        private int val;
        private Map<Integer, Node> next = new HashMap<>();
    }

    /**
     * Initializes an empty string symbol table.
     */
    public Trie() {
    }


    /**
     * Returns the int associated with the given key.
     * @param key the key
     * @return the int associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int get(int[] key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        max = 0;
        Node x = get(root, key, 0);
        if (x == null) return 0;
        return max;
    }

    private Node get(Node x, int[] key, int d) {
        if (x == null) return null;
        if (d == key.length) return x;
        int symbol = key[d];
//        if (x.key == symbol) {
        if (x.next.containsKey(symbol)) {
//            System.out.println(" symbol " + symbol);
            Node nextNode = x.next.get(symbol);
            if (nextNode != null && nextNode.val > max) {
//                System.out.println("true for " + Arrays.toString(key));
                max = nextNode.val;
            }
        }
        else {
            return x;
        }
        return get(x.next.get(symbol), key, d+1);
    }



//        if c in x.next:
//            return self._get(x.next[c], key, d + 1)
//            else:
//            return None

    /**
     * Inserts the key-int pair into the symbol table, overwriting the old int
     * with the new int if the key is already in the symbol table.
     * If the int is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the int
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(int[] key, int val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node x, int[] key, int val, int d) {
        if (x == null) x = new Node();
        if (d == key.length) {
            if (x.val == 0) n++;
            x.val = val;
            return x;
        }

        int symbol = key[d];
        Node node = new Node();
        x.next.put(symbol, put(x.next.get(symbol), key, val, d+1));
        return x;
    }




    /**
     * Returns the number of key-int pairs in this symbol table.
     * @return the number of key-int pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

//    /**
//     * Returns all keys in the symbol table as an {@code Iterable}.
//     * To iterate over all of the keys in the symbol table named {@code st},
//     * use the foreach notation: {@code for (Key key : st.keys())}.
//     * @return all keys in the symbol table as an {@code Iterable}
//     */
//    public Iterable<String> keys() {
//        return keysWithPrefix("");
//    }
//
//    /**
//     * Returns all of the keys in the set that start with {@code prefix}.
//     * @param prefix the prefix
//     * @return all of the keys in the set that start with {@code prefix},
//     *     as an iterable
//     */
//    public Iterable<String> keysWithPrefix(String prefix) {
//        Queue<String> results = new Queue<String>();
//        Node x = get(root, prefix, 0);
//        collect(x, new StringBuilder(prefix), results);
//        return results;
//    }
//
//    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
//        if (x == null) return;
//        if (x.val != null) results.enqueue(prefix.toString());
//        for (char c = 0; c < R; c++) {
//            prefix.append(c);
//            collect(x.next[c], prefix, results);
//            prefix.deleteCharAt(prefix.length() - 1);
//        }
//    }


    /**
     * Unit tests the {@code TrieST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // build symbol table from standard input
        Trie trie = new Trie();

        int[] line1 = {1,1};
        int val1 = 50;

        trie.put(line1, val1);
        System.out.println(trie.get(line1));

    }
}