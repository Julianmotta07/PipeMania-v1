package model;

public class User {

    private String nickname;
    private double score;
    private int pipesNumber;
    public User(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPipesNumber() {
        return pipesNumber;
    }

    public void setPipesNumber(int pipesNumber) {
        this.pipesNumber = pipesNumber;
    }
}
