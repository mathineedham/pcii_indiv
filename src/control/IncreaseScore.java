package control;

import view.Affichage;

public class IncreaseScore extends Thread{
    Affichage monAffichage;
    public final static int DELAY = 1000;
    
    public IncreaseScore(Affichage a){
        this.monAffichage = a;
    }

    @Override
    public void run() {
        while(!monAffichage.getJeuEstFinie()){ 
            //on incremente le score de 1 toute les secondes
            monAffichage.getScore().increment();
            monAffichage.repaint(); 
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
    
}
