package model;
import java.util.*;
public class BoardController {

    private ArrayList<User> users;
    private LinkedList list;

    public BoardController() {
        users= new ArrayList<>();
        list = new LinkedList();
    }

    public String newGame(String nickname){
        String msg = "Error: nickname already exists.";
        if(searchUser(nickname) == null){
            User user = new User(nickname);
            users.add(user);
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    int[] pos = new int[2];
                    pos[0] = i;
                    pos[1] = j;
                    Data data = new Data(pos);
                    list.addNodeAtTail(data);
                }
            }
            //generate fountain and drain:
            int[] pos = new int[2];
            pos[0] = (int)(Math.random() * 8);
            pos[1] = (int)(Math.random() * 8);
            Node fountain = list.findNode(pos);
            Node drain;
            do {
                pos[0] = (int)(Math.random() * 8);
                pos[1] = (int)(Math.random() * 8);
                drain = list.findNode(pos);
            } while (fountain == drain);
            fountain.getData().setCharacter("F");
            drain.getData().setCharacter("D");
            msg = printBoard();
        }
        return msg;
    }

    public String printBoard(){
        return printBoard(list.getHead());
    }

    private String printBoard(Node pointer) {
        StringBuilder boardString = new StringBuilder();
        if (pointer != null) {
            if (pointer.getData().getPosition()[1] == 7) {
                boardString.append(pointer.getData().getCharacter()).append("\n");
            } else {
                boardString.append(pointer.getData().getCharacter()).append("  ");
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
            Data data = node.getData();
            if (data.getCharacter().equals("D") || data.getCharacter().equals("F")){
                msg = "Error: You can't change this item";
            } else if (data.getCharacter().equals("X")){
                data.setCharacter(pipeType);
                msg = "Added pipe!";
            }
        }
        return msg;
    }

    public void simulate(){

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
