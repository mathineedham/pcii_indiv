package control;
import view.Affichage;

public class Ligne extends Thread {
    Affichage monAffichage;
    public final static int DELAY = 100;
    
    public Ligne(Affichage a){
        this.monAffichage = a;
    }

    @Override
    public void run() {
        /* tout les DELAY ms notre ligne brisee avance*/
        while(!monAffichage.getJeuEstFinie()){ 
            /*avance tout nos points de la ligne */
            monAffichage.getLigne().move();
            /*verifie si il faut ajouter un point */
            monAffichage.getLigne().addNewPoint();
            /*verifie si il faut retirer les premiers points */
            monAffichage.getLigne().remove();

            /*mise a jour des etoile */
            //monAffichage.getFond().updateEtoile();
            
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
