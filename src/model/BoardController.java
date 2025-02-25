package model;
import java.util.*;

public class BoardController {

    private ArrayList<User> users;
    private LinkedList board;
    private User currentUser;
    private Node fountain;
    private Calendar startTime;
    private Calendar finalTime;
    private BST scoreTable;

    public BoardController() {
        users= new ArrayList<>();
        board = new LinkedList();
        scoreTable = new BST();
    }

    public void newGame(String nickname){
        User user = searchUser(nickname);
        if(user != null){
            currentUser = user;
        } else {
            currentUser = new User(nickname);
            users.add(currentUser);
        }
        board.clear();
        currentUser.setPipesNumber(0);
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                int[] pos = new int[2];
                pos[0] = i;
                pos[1] = j;
                Node node = new Node(pos);
                board.addNodeAtTail(node);
            }
        }
        //Initialize time
        startTime = Calendar.getInstance();
        //generate fountain and drain:
        int[] pos = new int[2];
        pos[0] = (int)(Math.random() * 8);
        pos[1] = (int)(Math.random() * 8);
        fountain = board.findNode(pos);
        Node drain;
        do {
            pos[0] = (int)(Math.random() * 8);
            pos[1] = (int)(Math.random() * 8);
            drain = board.findNode(pos);
        } while (fountain == drain);
        fountain.setCharacter("F");
        drain.setCharacter("D");
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder();
        return printBoard(board.getHead(), sb);
    }

    private String printBoard(Node pointer, StringBuilder boardString) {
        if (pointer != null) {
            boardString.append(pointer.getCharacter());
            if (pointer.getPosition()[1] == 7) {
                boardString.append("\n");
            } else {
                boardString.append("  ");
            }
            printBoard(pointer.getNext(), boardString);
        }
        return boardString.toString();
    }

    public String addPipe(int row, int col, String pipeType){
        String msg = "Added pipe!";
        int[] pos = {row, col};
        Node node = board.findNode(pos);
        if (node != null){
            if (node.getCharacter().equals("D") || node.getCharacter().equals("F")){
                msg = "Error: You can't change this item.";
            } else {
                if (node.getCharacter().equals("X")){
                    if (!pipeType.equals("X")){
                        currentUser.setPipesNumber(currentUser.getPipesNumber() + 1);
                    } else {
                        msg = "Error: you can't remove this item.";
                    }
                } else if (pipeType.equals("X")){
                    currentUser.setPipesNumber(currentUser.getPipesNumber() - 1);
                    msg = "Removed pipe!";
                }
                node.setCharacter(pipeType);
            }
        }
        return msg;
    }

    public String simulate() {
        String msg = "The solution is incorrect.";
        ArrayList<String> exploredDirections = new ArrayList<>();
        if (findRoute(fountain, fountain.getCharacter(), exploredDirections, "")) {
            finalTime = Calendar.getInstance();
            int seconds = calculateTime(finalTime);
            int score = calculateScore(seconds, currentUser.getPipesNumber());
            User user = scoreTable.searchUserInBST(currentUser.getScore());
            if (user == null){
                currentUser.setScore(score);
                scoreTable.addNode(currentUser);
            } else {
                double temp = user.getScore();
                if (score > currentUser.getScore()){
                    scoreTable.deleteNode(temp);
                    currentUser.setScore(score);
                    scoreTable.addNode(currentUser);
                }
            }
            msg = "The solution is correct!\n";
            msg += "Number of pipes used: " + currentUser.getPipesNumber();
            msg += "\nTime: " + seconds + " sec.";
            msg += "\n" + currentUser.getNickname() + ", your score was " + score;
        }
        return msg;
    }

    private boolean findRoute(Node current, String lastPipe, ArrayList<String> exploredDirections, String currentDirection) {
        if (current == null) {
            return false;
        }

        String currentPipe = current.getCharacter();

        if (currentPipe.equals("D")) {
            if ((currentDirection.equals("up") || currentDirection.equals("down")) && (lastPipe.equals("||"))) {
                return true;
            } else if ((currentDirection.equals("left") || currentDirection.equals("right")) && (lastPipe.equals("="))) {
                return true;
            }
        }

        if (!isValidPipe(currentPipe, lastPipe, currentDirection)) {
            return false;
        }

        String currentPos = current.getPosition()[0] + "," + current.getPosition()[1];
        exploredDirections.add(currentPos);

        boolean found = false;

        if (current.getNext() != null && !currentDirection.equals("left")) {
            int[] pos = {current.getPosition()[0], current.getPosition()[1] + 1};
            String rightDirection = pos[0] + "," + pos[1];
            if (!exploredDirections.contains(rightDirection)) {
                found = findRoute(board.findNode(pos), currentPipe, exploredDirections, "right");
            }
        }

        if (current.getPrevious() != null && !found && !currentDirection.equals("right")) {
            int[] pos = {current.getPosition()[0], current.getPosition()[1] - 1};
            String leftDirection = pos[0] + "," + pos[1];
            if (!exploredDirections.contains(leftDirection)) {
                found = findRoute(board.findNode(pos), currentPipe, exploredDirections, "left");
            }
        }

        if (current.getPosition()[0] > 0 && !found && !currentDirection.equals("down")) {
            int[] pos = {current.getPosition()[0] - 1, current.getPosition()[1]};
            String upDirection = pos[0] + "," + pos[1];
            if (!exploredDirections.contains(upDirection)) {
                found = findRoute(board.findNode(pos), currentPipe, exploredDirections, "up");
            }
        }

        if (current.getPosition()[0] < 7 && !found && !currentDirection.equals("up")) {
            int[] pos = {current.getPosition()[0] + 1, current.getPosition()[1]};
            String downDirection = pos[0] + "," + pos[1];
            if (!exploredDirections.contains(downDirection)) {
                found = findRoute(board.findNode(pos), currentPipe, exploredDirections, "down");
            }
        }

        return found;
    }

    private boolean isValidPipe(String currentPipe, String lastPipe, String currentDirection) {
        if (currentPipe.equals("F") && lastPipe.equals("F")) {
            return true;
        } else if (currentPipe.equals("=")) {
            if ((lastPipe.equals("=") || lastPipe.equals("o") || lastPipe.equals("F")) && !currentDirection.equals("up") && !currentDirection.equals("down")) {
                return true;
            }
        } else if (currentPipe.equals("||")) {
            if ((lastPipe.equals("||") || lastPipe.equals("o") || lastPipe.equals("F")) && !currentDirection.equals("right") && !currentDirection.equals("left")) {
                return true;
            }
        } else if (currentPipe.equals("o")) {
            if (currentDirection.equals("right") && (lastPipe.equals("="))) {
                return true;
            } else if (currentDirection.equals("left") && (lastPipe.equals("="))) {
                return true;
            } else if (currentDirection.equals("up") && (lastPipe.equals("||"))) {
                return true;
            } else if (currentDirection.equals("down") && (lastPipe.equals("||"))) {
                return true;
            }
        }
        return false;
    }

    private int calculateScore(int seconds, int pipesNumber){
        return (100 - pipesNumber) * 10 - seconds;
    }

    private int calculateTime(Calendar finalTime){
        return (int) ((finalTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000);
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

    public String viewScores(){
        if (scoreTable.getRoot()!= null){
            return scoreTable.getScores();
        } else {
            return "There are no players in the score table yet.";
        }
    }

}
