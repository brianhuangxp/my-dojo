import java.util.HashMap;
import java.util.Map;

public class LRUCache implements LRUCacheInterface {

    private class Node {
        int key, value;
        Node next, prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, Node> cacheMap;
    private final int CAPACITY;
    private int currSize;
    Node head, tail;

    public LRUCache(int capacity) {
        CAPACITY = capacity;
        cacheMap = new HashMap<Integer, Node>();
        currSize = 0;
    }

    /**
     * Get Operation must return the value if it is present in the cache else return
     * -1 Regardless, it must remove the node from its current position in the
     * linkedList and put it to the start.
     *
     * @param key
     * @return
     */

    public int get(int key) {
        Node n = cacheMap.get(key);

        if (n != null) {
            updateList(n);
            return n.value;
        }

        return -1;
    }

    /**
     * Put Operation must First check if the key is already present , in that case
     * update it with the new value. and update the position in the doubly
     * linkedList, put the node at the front. Otherwise, create a new Node and add
     * the key and value at the beginning of the linkedList.
     * <p>
     * Regardless, check if the currSize has overFlown the capacity of the cache, in
     * this case remove the last Node.
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {

        if (this.CAPACITY == 0)
            return;

        Node node = cacheMap.get(key);
        if (node == null) {
            node = new Node(key, value);
            cacheMap.put(key, node);

            if (head == null) {
                head = node;
                tail = node;
            }

            insertFront(node);
            currSize++;
        } else {
            node.value = value;
            updateList(node);
        }

        if (currSize > CAPACITY) {
            currSize--;
            cacheMap.remove(tail.key);
            remove(tail);
        }
    }

    private void updateList(Node node) {
        remove(node);
        insertFront(node);

    }

    private void insertFront(Node node) {
        if (node == head)
            return;

        Node firstNode = head;
        node.next = firstNode;
        firstNode.prev = node;
        head = node;
    }

    private void remove(Node node) {
        if (head == node)
            return;

        else if (tail == node) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node before = node.prev;
            Node after = node.next;
            before.next = after;
            after.prev = before;
        }

    }
}
