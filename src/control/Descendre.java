package control;
import view.Affichage;

public class Descendre extends Thread {
    Affichage monAffichage;
    public final static int DELAY = 100;
    
    public Descendre(Affichage a){
        this.monAffichage = a;
    }

    @Override
    public void run() {
        /* tout les DELAY ms on fait descendre notre cercle*/
        while(!monAffichage.getJeuEstFinie()){ 
            monAffichage.getPos().move();; 
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
