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
        return getScores(root, sb);
    }

    private String getScores(BSTNode pointer, StringBuilder sb) {
        if (pointer != null) {
            getScores(pointer.getRight(), sb);
            sb.append(pointer.getUser().getNickname()).append(": ").append(pointer.getUser().getScore()).append("\n");
            getScores(pointer.getLeft(), sb);
        }
        return sb.toString();
    }

    public User searchUserInBST(double value){
        return searchUserInBST(root, value);
    }

    private User searchUserInBST(BSTNode pointer, double value){
        if(pointer == null){
            return null;
        }
        if (pointer.getUser().getScore() > value) {
            return searchUserInBST(pointer.getLeft(), value);
        } else if (pointer.getUser().getScore() < value){
            return searchUserInBST(pointer.getRight(), value);
        } else {
            return pointer.getUser();
        }
    }

    public void deleteNode(double value){
        if (root.getUser().getScore() == value) {
            if (root.getLeft() == null) {
                root = root.getRight();
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else {
                BSTNode successor = getSuccessor(value);
                root.setUser(successor.getUser());
                deleteNode(root, root.getRight(), successor.getUser().getScore());
            }
        } else {
            deleteNode(null, root, value);
        }
    }

    private void deleteNode(BSTNode parent, BSTNode pointer, double value){
        if(pointer==null){
            return;
        }
        if (pointer.getUser().getScore() > value) {
            deleteNode(pointer, pointer.getLeft(), value);
        } else if (pointer.getUser().getScore() < value){
            deleteNode(pointer, pointer.getRight(), value);
        }
        else {
            if (pointer.getLeft() == null && pointer.getRight() == null){
                if (parent.getLeft() == pointer){
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
            } else if (pointer.getRight() == null){
                parent.setRight(pointer.getLeft());
                pointer.setLeft(null);
            } else if (pointer.getLeft() == null) {
                parent.setLeft(pointer.getRight());
                pointer.setRight(null);
            } else {
                BSTNode successor = getSuccessor(value);
                pointer.setUser(successor.getUser());
                deleteNode(pointer, pointer.getRight(), successor.getUser().getScore());
            }
        }
    }

    public BSTNode getSuccessor(double value){
        return getSuccessor(root, null, value);
    }

    private BSTNode getSuccessor(BSTNode pointer, BSTNode parent, double value){
        if(pointer==null){
            return null;
        }
        if(pointer.getUser().getScore() == value){
            if (pointer.getRight()!=null) return getMin(pointer.getRight());
            else return parent;
        }
        if(pointer.getUser().getScore() > value) {
            return getSuccessor(pointer.getLeft(), pointer, value);
        } else {
            return getSuccessor(pointer.getRight(), parent, value);
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