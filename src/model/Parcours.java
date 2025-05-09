package model;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Exception;

public class Parcours {
    /*Deux constantes qui diffinissent le minimum et le maximum des hauteurs des points de la ligne brisee */
    public final int HAUTEUR_MIN;
    public final int HAUTEUR_MAX;

    /*Deuxt constantes qui definissent le minimum er la maximum des difference entre les x de deux points consecutifs */
    public final int X_MIN;
    public final int X_MAX;

    /*Point de contact */
    /*L'ordonnee de la ligne courament a x=BEFORE, le meme x que notre cercle */
    private int point_contact =-1000;
    public int getPointContact(){
        return this.point_contact;
    }

    public Position pos;

    /*Un ArrayList represent la liste des points de la ligne brisee*/
    /*chaque point est une paire de deux nombres */
    /*qui represente le coordonnnes de ce point dans mon modele*/
    private ArrayList<Point> points;
    public ArrayList<Point> getPoints(){
        return this.points;
    }

    /*Constructeur de la classe Package */
    public Parcours(Position p){
        /*initialisation de la position */
        this.pos = p;
        this.HAUTEUR_MIN = Position.H_MIN + this.pos.getH();
        this.HAUTEUR_MAX = Position.H_MAX - this.pos.getH();
        this.X_MIN = Position.BEFORE;
        this.X_MAX = this.X_MIN*3;

        /*initialisation de la liste des points */
        this.points = new ArrayList<Point>();
        
        /*le premier point a un x avant BEFORE, pour que notre ligne commence en dehors de la fentre */
        Point p1 = new Point(-3, 180); 
        /*le deuxieme point a un x apres le x initial de notre cercle, donc 2*BEFORE */
        Point p2 = new Point(Position.BEFORE+ (new Random().nextInt(X_MAX-50)), 180);
        this.points.add(p1);
        this.points.add(p2);
        /*tant que le x du dernier point est plus petit que la largeur de notre fentre, on ajoute des points */
        while(this.points.getLast().getX() < Position.AFTER+Position.BEFORE){
            /*ajoute un point dont le x est entre X_MIN et X_MAX que le point d'avant et dont le y est entre HAUTEUR_MIN ET HAUTEURE_MAx */
            int x = (this.points.getLast().getX()) + new Random().nextInt(X_MAX-X_MIN)+X_MIN;
            int y = new Random().nextInt(HAUTEUR_MAX-HAUTEUR_MIN)+HAUTEUR_MIN;
            Point new_p = new Point(x,y);
            this.points.add(new_p);
        }

    }

    /*Methode qui permet de deplacer la ligne brisee  */
    public void move(){
        /*on deplace chaque point de la ligne brisee */
        for(Point p : this.points){
            p.setX(p.getX()-Position.avancement);
        }

    }   

    /*Fonction qui supprime les premier element de notre ligne si sont deuxieme point est sortie de la fenetre */
    public void remove(){
        if(this.points.get(1).getX() < 0){
            this.points.remove(0);
        }
    }

    /*Fonction qui regarde si le dernier point a une abscisse plus petite que la largeur de la fentre*/
    /*si c'est le cas, elle ajoute un nouveau point a notres liste */
    public void addNewPoint(){
        if(this.points.getLast().getX() < Position.AFTER+Position.BEFORE){
            int x = (this.points.getLast().getX()) + new Random().nextInt(X_MAX-X_MIN)+X_MIN;
            int y = new Random().nextInt(HAUTEUR_MAX-HAUTEUR_MIN)+HAUTEUR_MIN;
            Point new_p = new Point(x,y);
            this.points.add(new_p);
        }
    }

    /*Fonction qui calcul le point de contact possible entre la ligne et le cercle */
    public void calcul_contact() throws Exception{
        Point point1 = new Point(0, 0);
        Point point2 = new Point(0, 0);
        boolean trouver = false;
        /*on recupere les deux points qui entourent notre cercle, soit le 1er et le deuxieme soit le 2eme et les 3ene */
        for(int i=0;i<2;i++){
        if(this.getPoints().get(i).getX() <= Position.BEFORE && this.getPoints().get(i+1).getX() >= Position.BEFORE){
                point1 = getPoints().get(i);
                point2 = getPoints().get(i+1);
                trouver= true;
                break;
            }
        }
        if(trouver){
            /*on recupere les coordonnees de ces deux points */
            int x1 = point1.getX();
            int y1 = point1.getY();
            int x2 = point2.getX();
            int y2 = point2.getY();
            /*on calcule la pente de la droite entre cest deux points */
            double pente = ((double)(y2-y1))/(x2-x1);
            /*on calcule le pont de contacte de cette partie de la ligne brisee au niveau de x=Position.Before */
            /*car c'est l'abscisse de notre cercle */
            this.point_contact= (int)(pente*(Position.BEFORE-x1)+y1);
        }
        /*on regarde si il y a collision avec notre cercle */
        /*c'est a dire au extremite y du cercle */
        if(this.point_contact+2>(pos.getHauteur()+pos.getH()/2) || point_contact-2<(pos.getHauteur()-pos.getH()/2)){
            throw new Exception(); 
        }
    }
}
