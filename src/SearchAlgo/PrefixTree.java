package src.SearchAlgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

class PrefixTree {
    private TrieNode root;

    public PrefixTree() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> suggestions = new ArrayList<>();
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return suggestions;
            }
            node = node.children.get(ch);
        }
        collectSuggestions(node, prefix, suggestions);
        return suggestions;
    }

    private void collectSuggestions(TrieNode node, String prefix, List<String> suggestions) {
        if (node.isEndOfWord) {
            suggestions.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectSuggestions(entry.getValue(), prefix + entry.getKey(), suggestions);
        }
    }

    /*
    // Efficient way of suggesting by storing words in each node
    // Priority can be applied many to show top 5 related words based
    // on: lexicographical, rating/popularity, most visited by counter
    class TrieNode{
        TrieNode []children;
        PriorityQueue<String> pq;

        public TrieNode(){
            children=new TrieNode[26];
            pq=new PriorityQueue<>((a, b) -> b.compareTo(a));
        }

        void addToPQ(String word){
            pq.add(word);
            if(pq.size() > 3) pq.poll();
        }

        List<String> getTopThree(){
            List<String> topThree=new ArrayList<>();
            while(!pq.isEmpty()) topThree.add(pq.poll());
            Collections.reverse(topThree);
            return topThree;
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TrieNode root=new TrieNode();
        for(String product:products) insert(root, product);

        List<List<String>> results=new ArrayList<>();
        for(char c:searchWord.toCharArray()){
            if((root=root.children[c-'a']) == null) break;
            results.add(root.getTopThree());
        }

        while(results.size() < searchWord.length())
            results.add(new ArrayList<>());

        return results;
    }

    void insert(TrieNode root, String word){
        for(char c:word.toCharArray()){
            if(root.children[c-'a']==null) root.children[c-'a']=new TrieNode();
            root=root.children[c-'a'];
            root.addToPQ(word);
        }
    }
     */
}
