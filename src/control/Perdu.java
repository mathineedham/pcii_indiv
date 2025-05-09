package control;

import view.Affichage;

public class Perdu extends Thread {
    Affichage monAffichage;
    public final static int DELAY = 75;

    /*constructeur */
    public Perdu(Affichage a){
        this.monAffichage = a;
    }


    @Override
    public void run() {
        while(true){ 
            try {
                monAffichage.getLigne().calcul_contact();
            }catch(Exception e){
                this.monAffichage.estFinie();
                monAffichage.dessineFin(monAffichage.getGraphics());
            }
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
