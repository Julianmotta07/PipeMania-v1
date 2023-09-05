package model;

public class Data {
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


}
