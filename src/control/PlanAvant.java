package control;
import view.Affichage;

public class PlanAvant extends Thread{
    Affichage monAffichage;
    public final static int DELAY = 100;
    
    public PlanAvant(Affichage a){
        this.monAffichage = a;
    }

    @Override
    public void run() {
        while(!monAffichage.getJeuEstFinie()){ 
            /*avance nos planete et met a jour celles qui sont visibles*/
            monAffichage.getFond().updateMontagneAvant();
            
            try { Thread.sleep(DELAY); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }
}
