package ui;
import model.BoardController;
import java.util.Scanner;


public class Main {
    public static Scanner sc;
    public static BoardController controller;

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
            System.out.println("WELCOME TO THE MENU");
            System.out.println("--Select an option-");
            System.out.println("1: New game........");
            System.out.println("2: View score......");
            System.out.println("0: Exit............");
            System.out.println("-------------------");
            option = sc.nextInt();
            sc.nextLine();
            switch(option){
                case 1:
                    newGame();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 2:
                    viewScore();
                    System.out.println("Press Enter to return to the menu...");
                    sc.nextLine();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
            }
        } while (option!=0);
    }

    public void newGame(){
        System.out.println("Enter nickname:");
        String nickname = sc.nextLine();
        String msg = controller.newGame(nickname);
        if (!msg.equals("Error: nickname already exists.")){
            gameOptions();
        }
    }

    public void gameOptions(){
        int option;
        do{
            String board = controller.printBoard();
            System.out.println(board);
            System.out.println("-Select an option-");
            System.out.println("1: Add pipe.......");
            System.out.println("2: Simulate.......");
            System.out.println("0: Exit...........");
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
                case 0:
                    break;
                default:
                    System.out.println("Invalid option, try again!");
                    sc.nextLine();
            }
        } while (option != 0);
    }

    public void addPipe(){
        System.out.println("Enter row:");
        int row = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter column:");
        int col = sc.nextInt();
        sc.nextLine();
        System.out.println("Select pipe type: \n 1. = \n 2. || \n 3. o");
        int type = sc.nextInt();
        sc.nextLine();
        String pipeType = switch (type) {
            case 1 -> "=";
            case 2 -> "||";
            default -> "o";
        };
        String msg = controller.addPipe(row, col, pipeType);
        System.out.println(msg);
    }

    public void simulate(){

    }

    public void viewScore(){

    }

}