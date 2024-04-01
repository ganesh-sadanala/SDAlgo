import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

class ConsistentHashingImpl<T>{
  private final TreeMap<Integer, T> circle = new TreeMap<>();
  private final Map<T, TreeMap<Integer, String>> nodeToKeys = new HashMap<>();

  public ConsistentHashing(int numberOfReplicas, Collection<T> nodes) {
      this.numberOfReplicas = numberOfReplicas;

      for (T node: nodes) {
          add(node);
      }
  }

  public void add(T node){
    int hash = hash(node.toString() + i);
    circle.put(hash, node);
    nodeToKeys.computeIfAbsent(node, k -> new TreeMap<>()).put(hash, "");
  }

  public void remove(T node) {
      int hash = hash(node.toString() + i);
      circle.remove(hash);
      redistributeKeys(node);
  }

  private T getNode(int hash) {
      if (circle.isEmpty()) {
          return null;
      }
      if (!circle.containsKey(hash)) {
          SortedMap<Integer, T> tailMap = circle.tailMap(hash);
          hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
      }
      return circle.get(hash);
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
}
