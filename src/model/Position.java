package model;
public class Position {
    /*Attribut qui definit la hauteur dans la fenetre de notre cerceau */
    private int hauteur = 180;
    public int getHauteur(){
        return this.hauteur;
    }
    public void setHauteur(int h){
        this.hauteur = h;
    }

    /*Hauteur d'un saut du cercle */
    public static final int HAUTEUR = 10;

    /*Mininimum et maximum de l'attribut hauteur */
    public static final int H_MIN = 20;
    public static final int H_MAX = 340;

    /*Horizon de vant est derriere*/
    public static final int BEFORE = 50;
    public static final int AFTER = BEFORE*4;

    /*Attribut qui definit l'avancement du cercle */
    public static final int avancement = 4;

    /*Attribut hauteur du cercle H */
    private int H = 100;
    public int getH () { return this.H; }
    public void setH (int nouveau_H) { this.H = nouveau_H; }

    /*Attribut de vitesse, va changer a quelle vitesse le cercle descend */
    private int vitesse = 0;
    public int getVitesse() { return this.vitesse; }
    public void setVitesse(int v) { this.vitesse = v; }

    /*Attribut impulsion du saut */
    private final int impulsion = -6;
    public int getImpulsion() { return this.impulsion; }


    /*Fait les changements necessaire pour que mon cercle saute */
    public void jump(){
        this.setVitesse(0);
        if(this.getHauteur() + HAUTEUR + this.getH()/2 <= H_MAX){
            this.setVitesse(this.getImpulsion());
            this.setHauteur(this.getHauteur() + HAUTEUR);
        }else{
            this.setHauteur(H_MAX-this.getH()/2);
        }

    }

    /*Mouvement de descente du cercle si il n'est pas deja tout en bas */
    public void move(){
        if(this.getHauteur() -this.getVitesse()> (H_MIN)+this.getH()/2){
            this.setHauteur(this.getHauteur()-this.getVitesse());
            if(this.getVitesse()<10){ /*on limite la vitesse a 10 */
                /*on augmente la vitesse de descente */
                this.setVitesse(this.getVitesse()+1);
            }
        }else{
            this.setHauteur(H_MIN+this.getH()/2);
            this.setVitesse(0);;
        }
    }
}
