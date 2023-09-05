package model;
import java.util.*;
public class BoardController {

    private ArrayList<User> users;
    private LinkedList list;
    private User currentUser;
    private Node fountain;
    private Node drain;

    public BoardController() {
        users= new ArrayList<>();
        list = new LinkedList();
    }

    public void newGame(String nickname){
        User user = searchUser(nickname);
        if(user != null){
            currentUser = user;
        } else {
            currentUser = new User(nickname);
            users.add(currentUser);
        }
        list.clear();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                Node node = new Node(pos);
                list.addNodeAtTail(node);
            }
        }
        //generate fountain and drain:
        int[] pos = new int[2];
        pos[0] = (int)(Math.random() * 8);
        pos[1] = (int)(Math.random() * 8);
        fountain = list.findNode(pos);
        do {
            pos[0] = (int)(Math.random() * 8);
            pos[1] = (int)(Math.random() * 8);
            drain = list.findNode(pos);
        } while (fountain == drain);
        fountain.setCharacter("F");
        drain.setCharacter("D");
    }

    public String printBoard(){
        return printBoard(list.getHead());
    }

    private String printBoard(Node pointer) {
        StringBuilder boardString = new StringBuilder();
        if (pointer != null) {
            if (pointer.getPosition()[1] == 7) {
                boardString.append(pointer.getCharacter()).append("\n");
            } else {
                boardString.append(pointer.getCharacter()).append("  ");
            }
            boardString.append(printBoard(pointer.getNext()));
        }
        return boardString.toString();
    }

    public String addPipe(int row, int col, String pipeType){
        String msg = "Error: Invalid position";
        int[] pos = {row, col};
        Node node = list.findNode(pos);
        if (node != null){
            if (node.getCharacter().equals("D") || node.getCharacter().equals("F")){
                msg = "Error: You can't change this item";
            } else if (node.getCharacter().equals("X")){
                currentUser.setPipesNumber(currentUser.getPipesNumber()+1);
                node.setCharacter(pipeType);
                msg = "Added pipe!";
            }
        }
        return msg;
    }

    public String simulate() {
        if (findRoute(fountain, fountain.getCharacter())) {
            return "The solution is correct";
        } else {
            return "The solution is incorrect.";
        }
    }

    private boolean findRoute(Node current, String lastPipe) {

        if (current == null) {
            return false;
        }

        String currentPipe = current.getCharacter();

        if (currentPipe.equals("D")) {
            return true;
        }

        if (!isValidPipe(currentPipe, lastPipe)) {
            return false;
        }

        if (current.getNext() != null){
            int[] pos = {current.getPosition()[0], current.getPosition()[1] + 1};
            if (findRoute(list.findNode(pos), currentPipe)){
                return true;
            }
        }

        if (current.getPrevious() != null){
            int[] pos = {current.getPosition()[0], current.getPosition()[1] - 1};
            if (findRoute(list.findNode(pos), currentPipe)) {
                return true;
            }
        }

        if (current.getPosition()[0] > 0) {
            int[] pos = {current.getPosition()[0] - 1, current.getPosition()[1]};
            if (findRoute(list.findNode(pos), currentPipe)) {
                return true;
            }
        }

        if (current.getPosition()[0] < 7) {
            int[] pos = {current.getPosition()[0] + 1, current.getPosition()[1]};
            if (findRoute(list.findNode(pos), currentPipe)) {
                return true;
            }
        }

        return false;

    }

    private boolean isValidPipe(String currentPipe, String lastPipe) {
        if (currentPipe.equals("F") && lastPipe.equals("F")){
            return true;
        } else if (currentPipe.equals("=") && (lastPipe.equals("=") || lastPipe.equals("o") || lastPipe.equals("F"))) {
            return true;
        } else if (currentPipe.equals("||") && (lastPipe.equals("||") || lastPipe.equals("F"))) {
            return true;
        } else if (currentPipe.equals("o") && (lastPipe.equals("||") || lastPipe.equals("="))) {
            return true;
        }
        return false;
    }


    public String calculateScore(){
        return "";
    }

    private User searchUser(String nickname){
        User userFound = null;
        boolean found = false;
        for (int i=0; i < users.size() && !found; i++) {
            User user = users.get(i);
            if (user.getNickname().equals(nickname)){
                userFound = user;
                found = true;
            }
        }
        return userFound;
    }

}
