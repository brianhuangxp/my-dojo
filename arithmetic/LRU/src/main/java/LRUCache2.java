import java.util.HashMap;
import java.util.Map;

class LRUCache2 implements LRUCacheInterface {

    private class Node {
        int key, value;
        Node pre, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.pre = null;
            this.next = null;
        }
    }

    private int capacity;
    private Map<Integer, Node> hash;
    private Node head, tail;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        this.hash = new HashMap<Integer, Node>();
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        this.head.next = this.tail;
        this.tail.pre = this.head;
    }

    public int get(int key) {
        if (!hash.containsKey(key)) return -1;

        Node node = hash.get(key);

        //if(tail.pre == node) return node.value;

        //remove node from original place

        //node.pre.next = node.next;
        //node.next.pre = node.pre;

        moveNodeToTail(node);

        return node.value;
    }

    public void put(int key, int value) {
        if (hash.containsKey(key)) {
            Node node = hash.get(key);
            moveNodeToTail(node);
            node.value = value;
            return;
        }

        if (hash.size() == capacity) {
            //remove the first node
            hash.remove(head.next.key);
            head.next = head.next.next;
            head.next.pre = head;
        }

        //add new node to tail
        Node cur = new Node(key, value);
        hash.put(key, cur);
        tail.pre.next = cur;
        cur.pre = tail.pre;
        tail.pre = cur;
        cur.next = tail;
    }

    private void moveNodeToTail(Node cur) {
        if (tail.pre == cur) return;

        //remove cur from original place
        cur.pre.next = cur.next;
        cur.next.pre = cur.pre;
        //add to tail
        tail.pre.next = cur;
        cur.pre = tail.pre;
        tail.pre = cur;
        cur.next = tail;

    }
}