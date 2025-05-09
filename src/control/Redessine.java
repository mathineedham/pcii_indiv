package control;

import view.Affichage;

public class Redessine extends Thread {
    Affichage monAffichage;
    public final static int DELAY = 50;
    
    public Redessine(Affichage a){
        this.monAffichage = a;
    }

    @Override
    public void run() {
        /* tout les DELAY ms on relance monAfffichage.repaint()*/
        while(!monAffichage.getJeuEstFinie()){ 
            //on update la position du cercle
            monAffichage.updateY();
            //on redessine notre affichage
            monAffichage.repaint(); 
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
