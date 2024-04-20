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
}
