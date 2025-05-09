package model;

import java.util.ArrayList;
import java.util.Random;



/*Classe dans laquelle on definera nos objects qui apparaissent dans le fond d'ecran de notre jeu */
/*c'est objet vont donc aussi avancer avec le cercle pour donner une illusion de mouvement */
public class Background {

    /*Attribut positiom */
    private final Position pos;
    public Position getPos() {return this.pos;}

    /*Attributs qui designe les limites des coordonnees de nos objects*/
    public final int X_MIN ;
    public final int X_MAX ;
    public final int Y_MIN ;
    public final int Y_MAX ;

    /*Array de Point qui designe les centres des etoiles */
    private ArrayList<Point> etoiles;
    public ArrayList<Point> getEtoiles(){
        return this.etoiles;
    }

    /*Nombre de Montagne avant plan */
    private final int nb_montagnes1 = 6 ;
    /*Count l'avancement des montagnes de l'avant plan, se reinitialise tout les (X_Max-X_Min)/nb_montagnes1 */
    private int count1 = 0;
    /*Array de paires avec un Point et un int*/
    /*Le point qui designe le sommet des montagnes */
    /*Le int designe la moitie de la longueur de la base de la montagne*/
    private ArrayList<Paire<Point,Integer>> montagnes1;
    public ArrayList<Paire<Point,Integer>> getMontagnes1(){
        return this.montagnes1;
    }

     /*Nombre de Montagne mi plan */
     private final int nb_montagnes2 = 6 ;
     /*Count l'avancement des montagnes de mi plan, se reinitialise tout les (X_Max-X_Min)/nb_montagnes2 */
    private int count2 = 0;
     /*Array de montagnes*/
     private ArrayList<Paire<Point,Integer>> montagnes_miplan;
     public ArrayList<Paire<Point,Integer>> getMontagnes_miplan(){
         return this.montagnes_miplan;
     }

     /*Nombre de Montagne arriere plan */
     private final int nb_montagnes3 = 4 ;
    /*Count l'avancement des montagnes de arriere plan, se reinitialise tout les (X_Max-X_Min)/nb_montagnes3 */
    private int count3 = 0;
     /*Array de montagnes d'arriere plan*/
     private ArrayList<Paire<Point,Integer>> montagnes3;
     public ArrayList<Paire<Point,Integer>> getMontagnes3(){
         return this.montagnes3;
     }

    /*Constructeur de notre classe */
    public Background(Position p){
        this.pos = p;
        this.X_MIN = 0;
        this.X_MAX = Position.AFTER+Position.BEFORE;
        this.Y_MIN = Position.H_MIN;
        this.Y_MAX = Position.H_MAX;

        /*On initialise notre tableau de montagne de l'avant plan */
        this.montagnes1 = new ArrayList<Paire<Point,Integer>>();
        int sector = (X_MAX-X_MIN)/nb_montagnes1;
        for(int i = 0; i<nb_montagnes1+2; i++){
            addNewMontagne(X_MIN+i*sector, X_MIN+(i+1)*sector, (Y_MAX-Y_MIN)*3/8,(Y_MAX-Y_MIN)*5/8,1);
        }

        /*On initialise notre tableau de montagne de mi plan */
        this.montagnes_miplan = new ArrayList<Paire<Point,Integer>>();
        int sector2 = (X_MAX-X_MIN)/nb_montagnes2;
        for(int i = 0; i<nb_montagnes2+2; i++){
            addNewMontagne(X_MIN+i*sector2, X_MIN+(i+1)*sector2, (Y_MAX-Y_MIN)/2,(Y_MAX-Y_MIN)*3/4,2);
        }

        /*On initialise notre tableau de montagne d'arriere plan */
        this.montagnes3 = new ArrayList<Paire<Point,Integer>>();
        int sector3 = (X_MAX-X_MIN)/nb_montagnes3;
        for(int i = 0; i<nb_montagnes3+2; i++){
            addNewMontagne(X_MIN+i*sector3, X_MIN+(i+1)*sector3, (Y_MAX-Y_MIN)*5/8,(Y_MAX-Y_MIN)*7/8,3);
        }

    }

    /*Fonction update pour les montagnes du dernier plan, fait le decalement des x*/
    /*Verifie aussi si toute les montagnes dans le tableau sont visibles, sinon supprime la premiere et en creer une nouvelle */
    public void updateMontagneArriere(){
        /*update du x des montagnes de arriere plan */
        for(Paire<Point,Integer> p : this.getMontagnes3()){
            p.fst().setX(p.fst().getX()-(Position.avancement-3));
            count3+=(Position.avancement-3);
        }
        /*Verification que la 1ere montagne et encore visible sinon on la supprime*/
        Paire<Point,Integer> montagne3 = this.getMontagnes3().getFirst();
        if(montagne3.fst().getX() + montagne3.snd() < 0 ){
            this.montagnes3.remove(0);
        }
        /*Ajout d'une nouvelle montagne */
        if(this.count3>=(X_MAX-X_MIN)/nb_montagnes3){
            this.count3 = 0;
            this.addNewMontagne(X_MAX+(X_MAX-X_MIN)/nb_montagnes3, X_MAX+2*(X_MAX-X_MIN)/nb_montagnes3,(Y_MAX-Y_MIN)*5/8,(Y_MAX-Y_MIN)*7/8,3);
        }
    }

    /*Fonction de update pour nos montagnes de 1er plan*/
    public void updateMontagneAvant(){
        /*update du x des montagnes 1er plan */
        for(int i = 0; i<this.montagnes1.size(); i++){
            Point p = this.montagnes1.get(i).fst();
            p.setX(p.getX()-(Position.avancement-1));
            count1+=(Position.avancement-1);
        }

        //on regarde si la premiere montagne est encore visible
        // i.e le point de droite de la base du triangle est inferieur a 0
        //si elle ne l'est pas on la supprime 
        Paire<Point,Integer> montagne = this.getMontagnes1().getFirst();
        if(montagne.fst().getX() + montagne.snd() < 0 ){
            this.montagnes1.remove(0);
        }
        //on regarde si on ajoute un nouvealle montagne au premier plan
        if(this.count1>=(X_MAX-X_MIN)/nb_montagnes1){
            this.count1 = 0;
            this.addNewMontagne(X_MAX+(X_MAX-X_MIN)/nb_montagnes1, X_MAX+2*(X_MAX-X_MIN)/nb_montagnes1,(Y_MAX-Y_MIN)*3/8,(Y_MAX-Y_MIN)*5/8,1);

        }
    }

    /*Fonction de update des montagnes de mi plan  */
    public void updateMontagneMi(){
        /*update du x des montagnes de mi plan */
        for(Paire<Point,Integer> p : this.getMontagnes_miplan()){
            p.fst().setX(p.fst().getX()-(Position.avancement-2));
            count2+=(Position.avancement-2);
        }

        //on verifie la premiewre montagne du mi plan
        Paire<Point,Integer> montagne2 = this.getMontagnes_miplan().getFirst();
        if(montagne2.fst().getX() + montagne2.snd() < 0 ){
            this.montagnes_miplan.remove(0);
        }
        //ajout d'une montagne au mi plan
        if(this.count2>=(X_MAX-X_MIN)/nb_montagnes2){
            this.addNewMontagne(X_MAX, X_MAX+(X_MAX-X_MIN)/nb_montagnes2+2*(X_MAX-X_MIN)/nb_montagnes2,(Y_MAX-Y_MIN)/2,(Y_MAX-Y_MIN)*3/4,2);
            this.count2 = 0;
        }

    }

    //Fonction qui cree une nouvelle montagne en fonction des limites du x 
    public void addNewMontagne(int xmin, int xmax, int ymin, int ymax, int m){

        /*On choisi aleatoirement les coordonees du sommet de la montagne dans les limites */
        int x = new Random().nextInt(xmax-xmin)+xmin;
        int y = new Random().nextInt(ymax-ymin)+ymin;
        Point p = new Point(x, y);

        /*On choisi aleatoirement une longeueur de demi base */
        Integer b = new Random().nextInt(xmax-xmin)+(xmax-xmin);

        Paire<Point,Integer> montagne = new Paire<Point,Integer>(p,b);

        if(m==1){
            this.montagnes1.add(montagne);
        }else if (m==2) {
            this.montagnes_miplan.add(montagne);
        }else {
            this.montagnes3.add(montagne);
        }
    }

}
