package main;
import javax.swing.*;

import control.*;
import model.*;
import view.Affichage;

/** La classe principale de ce projet */
public class Main {
/** La méthode de lancement du programme */
    public static void main(String [] args) {
        JFrame maFenetre = new JFrame("Exercice 1");
        /*empeche la fenetre de changer de dimension */
        maFenetre.setResizable(false);
        
        Position p = new Position(); 
        Parcours ligne = new Parcours(p);
        Background fond = new Background(p);
        Score sc = new Score();
        Affichage a = new Affichage(p, ligne, fond,sc);
        ReactionClic mouse = new ReactionClic(p);
        
        maFenetre.add(a);
        maFenetre.pack();
        maFenetre.setVisible(true);
        maFenetre.addMouseListener(mouse);
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //on lance les threads qui vont gerer l'affichage
        Thread dessin = new Redessine(a);
        dessin.start();
        Thread descente = new Descendre(a);
        descente.start();
        Thread l = new Ligne(a);
        l.start();
        Thread plAvant = new PlanAvant(a);
        plAvant.start();
        Thread plMilieu = new PlanMilieu(a);
        plMilieu.start();
        Thread plArriere = new PlanArriere(a);
        plArriere.start();
        Thread score = new IncreaseScore(a);
        score.start();
        (new Perdu(a)).start();
    }
}