package model;


public class Score {
    private int score;
    public int getScore(){
        return this.score;
    }

    /*Constructeur*/
    public Score(){
        this.score = 0; // prendre la date de l'ordi
    }

    /* Methode qui incremente le score */
    public void increment(){
        this.score++;
    }
    
}
