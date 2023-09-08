package model;

public class LinkedList {

    private Node head;
    private Node tail;

    public void addNodeAtTail(Node node){
        if(head == null){
            head = node;
        } else {
            tail.setNext(node);
            node.setPrevious(tail);
        }
        tail=node;
    }

    public Node findNode(int[] pos){
        return findNode(head, pos);
    }

    private Node findNode(Node pointer, int[] pos) {
        if (pointer != null) {
            int[] nodePos = pointer.getPosition();
            if (nodePos[0] == pos[0] && nodePos[1] == pos[1]) {
                return pointer;
            } else {
                return findNode(pointer.getNext(), pos);
            }
        }
        return null;
    }

    public void clear() {
        head = clear(head);
        tail = null;
    }

    private Node clear(Node current) {
        if (current == null) {
            return null;
        }
        Node next = current.getNext();
        current.setNext(null);
        return clear(next);
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }
}
