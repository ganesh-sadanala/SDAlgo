package src;

import com.sun.source.tree.Tree;

import java.util.*;

/*
The self-balancing binary search tree (BST) data structure is used to store the positions of the nodes on the hash ring. The BST offers logarithmic O(log n) time complexity for search, insert, and delete operations.
The keys of the BST contain the positions of the nodes on the hash ring.
The BST data structure is stored on a centralized highly available service. As an alternative, the BST data structure is stored on each node, and the state information between the nodes is synchronized through the gossip protocol7.

Insertion of a data object (key)
In the diagram, suppose the hash of an arbitrary key ‘xyz’ yields the hash code output 5. The successor BST node is 6 and the data object with the key ‘xyz’ is stored on the node that is at position 6.
In general, the following operations are executed to insert a key (data object):

Hash the key of the data object
Search the BST in logarithmic time to find the BST node immediately greater than the hashed output
Store the data object in the successor node

Insertion of a node
The insertion of a new node results in the movement of data objects that fall within the range of the new node from the successor node. Each node might store an internal or an external BST to track the keys allocated in the node.
The following operations are executed to insert a node on the hash ring:

Insert the hash of the node ID in BST in logarithmic time
Identify the keys that fall within the subrange of the new node from the successor node on BST
Move the keys to the new node

Deletion of a node
The deletion of a node results in the movement of data objects that fall within the range of the decommissioned node to the successor node. An additional external BST can be used to track the keys allocated in the node.
The following operations are executed to delete a node on the hash ring:

Delete the hash of the decommissioned node ID in BST in logarithmic time
Identify the keys that fall within the range of the decommissioned node
Move the keys to the successor node

What is the asymptotic complexity of consistent hashing?
The asymptotic complexity of consistent hashing operations are the following:
OperationTime ComplexityDescriptionAdd a nodeO(k/n + logn)O(k/n) for redistribution of keys O(logn) for binary search tree traversalRemove a nodeO(k/n + logn)O(k/n) for redistribution of keys O(logn) for
binary search tree traversalAdd a keyO(logn)O(logn) for binary search tree traversalRemove a key
O(logn)O(logn) for binary search tree traversal
where k = total number of keys, n = total number of nodes 2, 7. Use the treemap as a BST
 */
class ConsistentHashingAlgo<T>{
    int numberOfReplicas;
  private final TreeMap<Integer, T> circle = new TreeMap<>();
  private final Map<T, TreeMap<Integer, String>> nodeToKeys = new HashMap<>();

  public ConsistentHashingAlgo(int numberOfReplicas, Collection<T> nodes) {
      this.numberOfReplicas = numberOfReplicas;

      for (T node: nodes) {
          add(node);
      }
  }

  public void add(T node){
    int hash = hash(node.toString() + i);
    circle.put(hash, node);
    nodeToKeys.computeIfAbsent(node, k -> new TreeMap<>()).put(hash, "");
    redistributeKeys(hash);
  }

  public void remove(T node) {
      int hash = hash(node.toString() + i);
      circle.remove(hash);
      redistributeKeys(node);
      nodeToKeys.remove(node);
  }

  private T getNode(int hash) {
      if (circle.isEmpty()) {
          return null;
      }
      Integer ceilHash = circle.ceilingKey(hash);
      if(ceilHash == null) ceilHash=circle.firstKey();
      return circle.get(ceilHash);
  }

  public void addKey(String key) {
      int hash = hash(key);
      T node = getNode(hash);
      nodeToKeys.get(node).put(hash, key);
  }

  public void removeKey(String key) {
      int hash = hash(key);
      T node = getNode(hash);
      nodeToKeys.get(node).remove(hash);
  }

  // redistribution when node is removed
  private void redistributeKeys(T node) {
      TreeMap<Integer, String> keyMap = nodeToKeys.remove(node);
      if (keyMap != null) {
          for (Map.Entry<Integer, String> entry : keyMap.entrySet()) {
              int hash = entry.getKey();
              String key = entry.getValue();
              T newNode = getNode(hash);
              nodeToKeys.get(newNode).put(hash, key);
          }
      }
  }

  // redistribution when a node is added
    private void redistributeKeys(int newNodeHash){
      Map.Entry<Integer, T> higherEntry = circle.higherEntry(newNodeHash);
      if(higherEntry != null){
          T lowerNode = higherEntry.getValue();
          TreeMap<Integer, String> higherNodeKeys = nodeToKeys.get(lowerNode);
          TreeMap<Integer, String> newNodeKeys = new TreeMap<>();
          for (Map.Entry<Integer, String> entry : higherNodeKeys.entrySet()) {
              int keyHash = entry.getKey();
              if (keyHash <= newNodeHash || keyHash > higherEntry.getKey()) {
                  newNodeKeys.put(keyHash, entry.getValue());
              }
          }

          higherNodeKeys.keySet().removeAll(newNodeKeys.keySet());
          nodeToKeys.get(getNode(newNodeHash)).putAll(newNodeKeys);
      }
    }
}





















