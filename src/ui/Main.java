package ui;
import model.BoardController;
import java.util.Scanner;

public class Main {

    public static Scanner sc;
    public static BoardController controller;
    private boolean gameOver = false;

    public Main() {
        controller = new BoardController();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main objMain = new Main();
        objMain.menu();
    }

    public void menu(){
        int option;
        do{
            System.out.println("WELCOME TO PIPE MANIA!");
            System.out.println("---Select an option---");
            System.out.println("1: New game...........");
            System.out.println("2: View scores........");
            System.out.println("3: Exit...............");
            System.out.println("----------------------");
            option = sc.nextInt();
            sc.nextLine();
            switch(option){
                case 1:
                    newGame();
                    break;
                case 2:
                    viewScores();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
            }
        } while (option!=3);
    }

    public void newGame(){
        gameOver = false;
        System.out.println("Enter nickname:");
        String nickname = sc.nextLine();
        controller.newGame(nickname);
        gameOptions();
    }

    public void gameOptions(){
        int option;
        do{
            String board = controller.printBoard();
            System.out.println(board);
            System.out.println("-Select an option-");
            System.out.println("1: Add/remove pipe");
            System.out.println("2: Simulate.......");
            System.out.println("3: Back...........");
            System.out.println("------------------");
            option = sc.nextInt();
            sc.nextLine();
            switch(option){
                case 1:
                    addPipe();
                    System.out.println("Press Enter to go back...");
                    sc.nextLine();
                    break;
                case 2:
                    simulate();
                    System.out.println("Press Enter to go back...");
                    sc.nextLine();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
            }
        } while (option != 3 && !gameOver);
    }

    public void addPipe(){
        boolean error;
        int row, col;
        do {
            error = false;
            System.out.println("Enter row:");
            row = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter column:");
            col = sc.nextInt();
            sc.nextLine();
            if ((row < 0) || (col < 0) || (row > 7) || (col > 7)) {
                System.out.println("Error: Enter a valid position.");
                error = true;
            }
        } while (error);
        String pipeType = "";
        do {
            error = false;
            System.out.println("Select pipe type: \n 1. = \n 2. || \n 3. o  \n 4. X");
            int type = sc.nextInt();
            sc.nextLine();
            switch (type) {
                case 1 -> pipeType = "=";
                case 2 -> pipeType = "||";
                case 3 -> pipeType = "o";
                case 4 -> pipeType = "X";
                default -> {
                    error = true;
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
                }
            } 
        } while (error);
        String msg = controller.addPipe(row, col, pipeType);
        System.out.println(msg);
    }

    public void simulate(){
        String msg = controller.simulate();
        if (!msg.equals("The solution is incorrect.")){
            gameOver = true;
        }
        System.out.println(msg);
    }

    public void viewScores(){
        String msg = controller.viewScores();
        System.out.println(msg);
    }

}