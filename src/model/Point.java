package model;

public class Point {
    /*attribut x, l'abscisse du point */
    private int x;
    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }

    /*attribut y, l'ordonnee du point */
    private int y;
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }

    /*Constructeur de la classe Point */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
