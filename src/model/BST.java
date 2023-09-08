package model;

public class BST {

    private BSTNode root;

    public BST() {
        this.root = null;
    }

    public void addNode(User user){
        addNode(root, user);
    }

    private void addNode(BSTNode pointer, User user){
        BSTNode node = new BSTNode(user);
        if(pointer == null){
            root = node;
        } else if (user.getScore() < pointer.getUser().getScore()) {
            if(pointer.getLeft() == null){
                pointer.setLeft(node);
            } else {
                addNode(pointer.getLeft(), user);
            }
        } else {
            if (pointer.getRight() == null){
                pointer.setRight(node);
            } else {
                addNode(pointer.getRight(), user);
            }
        }
    }

    public String getScores() {
        StringBuilder sb = new StringBuilder();
        sb.append("Score table:").append("\n");
        getScores(root, sb);
        return sb.toString();
    }

    private void getScores(BSTNode pointer, StringBuilder sb) {
        if (pointer != null) {
            getScores(pointer.getRight(), sb);
            sb.append(pointer.getUser().getNickname()).append(": ").append(pointer.getUser().getScore()).append("\n");
            getScores(pointer.getLeft(), sb);
        }
    }

    //CORREGIR ESTE MÉTODO
    public boolean searchUserInBST(String nickname){
        return searchUserInBST(root, nickname);
    }

    private boolean searchUserInBST(BSTNode pointer, String nickname){
        if(pointer == null){
            return false;
        }
        if (pointer.getUser().getNickname().compareTo(nickname) > 0) {
            return searchUserInBST(pointer.getLeft(), nickname);
        } else if (pointer.getUser().getNickname().compareTo(nickname)< 0){
            return searchUserInBST(pointer.getRight(), nickname);
        } else {
            return true;
        }
    }

    public void deleteNode(String nickname){
        if (root.getLeft() == null && root.getRight() == null && root.getUser().getNickname().equals(nickname)){
            root = null;
        } else {
            deleteNode(null, root, nickname);
        }
    }

    private void deleteNode(BSTNode parent, BSTNode pointer, String nickname){
        if(pointer==null){
            return;
        }
        if (pointer.getUser().getNickname().compareTo(nickname) > 0) {
            deleteNode(pointer, pointer.getLeft(), nickname);
        } else if (pointer.getUser().getNickname().compareTo(nickname) < 0){
            deleteNode(pointer, pointer.getRight(), nickname);
        }
        else {
            if (pointer.getLeft()==null && pointer.getRight()==null){
                if(parent.getLeft()==pointer){
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            }
            else if(pointer.getRight()==null){
            parent.setRight(pointer.getLeft());
            pointer.setLeft(null);
            }
            else if (pointer.getLeft()==null) {
                parent.setLeft(pointer.getRight());
                pointer.setRight(null);
            }
            else {
                BSTNode successor = getSuccessor(nickname);
                pointer.setUser(successor.getUser());
                deleteNode(pointer, pointer.getRight(), successor.getUser().getNickname());
            }
        }
    }

    public BSTNode getSuccessor(String value){
        return getSuccessor(root, null, value);
    }

    private BSTNode getSuccessor(BSTNode pointer, BSTNode parent, String nickname){
        if(pointer==null){
            return null;
        }
        if(pointer.getUser().getNickname().compareTo(nickname) == 0){
            if (pointer.getRight()!=null) return getMin(pointer.getRight());
            else return parent;
        }
        if(pointer.getUser().getNickname().compareTo(nickname) > 0) {
            return getSuccessor(pointer.getLeft(), pointer, nickname);
        } else {
            return getSuccessor(pointer.getRight(), parent, nickname);
        }

    }

    private BSTNode getMin(BSTNode pointer) {
        if (pointer.getLeft()==null){
            return pointer;
        } else {
            return getMin(pointer.getLeft());
        }
    }

    public BSTNode getRoot() {
        return root;
    }

}