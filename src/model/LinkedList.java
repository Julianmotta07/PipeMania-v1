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

    public Node findNode(int[] pos) {
        Node pointer = head;
        while (pointer != null) {
            int[] nodePos = pointer.getPosition();
            if (nodePos[0] == pos[0] && nodePos[1] == pos[1]) {
                return pointer;
            }
            pointer = pointer.getNext();
        }
        return null;
    }


    public void clear() {
        while (head != null) {
            Node next = head.getNext();
            head.setNext(null);
            head = next;
        }
        tail = null;
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
