package model;

public class Data {

    private int[] position;
    private String character;

    public Data(int[] position) {
        this.position = position;
        character = "X";
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
