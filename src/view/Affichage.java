package view;
import javax.swing.JPanel;

import model.*;

import java.awt.*;

import model.Point;

public class Affichage extends JPanel{
    /*Ratios qui permettent de passer des donnees definies dans notre modele a nos donnees ici */
    public final static int RATIO_X = 3;
    public final static int RATIO_Y = 2;

    /*Largeur de la fenetre de l'affichage */
    private int largeur;
    public void setLargeur(int l){
        this.largeur = l;
    }
    public int getLargeur(){
        return this.largeur;
    }
    
    /*Hauteur de la fenetre de l'affichage */
    private int hauteur;
    public void setHauteur(int h){
        this.hauteur = h;
    }
    public int getHauteur(){
        return this.hauteur;
    }
    
    /*Attributs qui definit la hauteur, la largeur, et les coordonnees du cercle dans la vue */
    private final int circleX;
    private final int circleHeight;
    private final int circleWidth;
    private int circleY;

    /*Le modele consiste de une position, une ligne et un fond d'ecran */
    private Position pos ;
    public Position getPos() {return this.pos;}
    private Parcours ligne;
    public Parcours getLigne() {return this.ligne;}

    private Background fond;
    public Background getFond() {return this.fond;}

    private Score score;
    public Score getScore() {return this.score;}

    private boolean jeuEstFinie=false;
    public boolean getJeuEstFinie() {return this.jeuEstFinie;}
    public void estFinie() {this.jeuEstFinie = true;}


    /*Constructeur de notre classe */
    public Affichage(Position p, Parcours ligne, Background fond,Score sc){
        /*On definit la largeur et la hauteur de notre fenetre */
        this.setLargeur((Position.AFTER+Position.BEFORE)*RATIO_X);
        this.setHauteur((Position.H_MAX-Position.H_MIN)*RATIO_Y);
        this.setPreferredSize(new Dimension(this.getLargeur(),this.getHauteur()));
        this.pos=p;
        this.ligne=ligne;
        this.fond=fond;
        this.score = sc;

        /*La largeur, la hauteur et l'abscisse du cercle ne changent pas donc on les definies une fois pour toute ici */
        /*circleHeight et circleWidth sont des longueurs donc a juste a les multiplier par le ratio*/
        /*ici je calcule la largeur en fonction de la hauteur */
        this.circleHeight = (this.pos.getH())*RATIO_Y;
        this.circleWidth = (this.circleHeight/2);
        /*On place le centre du cercle a l'abscisse BEFORE */
        this.circleX = XModeleToXVue(Position.BEFORE)-this.circleWidth/2;

        /*On definie la couleur principale de notre fond d'ecran */
        this.setBackground(new Color(204,229,255));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        /*Dessin du fond d'ecran */
        this.dessineFond(g);

        /*Dessin de la ligne brisee */
        this.dessineLigne(g);

        /*Dessin du cercle*/
        this.dessineCercle(g);

        /*Dessin du score */
        this.dessineScore(g);

    }

    /*Fonction qui dessine le fond d'ecran de notre jeu */
    public void dessineFond(Graphics g){
        /*On dessine les montagnes du mi plan*/
        for(int i = 0; i< this.fond.getMontagnes3().size();i++){
            Paire<Point,Integer> montagne = this.fond.getMontagnes3().get(i);
            g.setColor(new Color(153,204,255));
            this.dessineMontagne(montagne.fst(), montagne.snd(), g,(Position.H_MAX-Position.H_MIN)*3/4);
            g.fillRect(XModeleToXVue(0), YModeleToYVue((Position.H_MAX-Position.H_MIN)*3/4), this.largeur, this.hauteur*3/4);
        }

        /*On dessine les montagnes du mi plan*/
        for(int i = 0; i< this.fond.getMontagnes_miplan().size();i++){
            Paire<Point,Integer> montagne = this.fond.getMontagnes_miplan().get(i);
            g.setColor(new Color(102,178,255));
            this.dessineMontagne(montagne.fst(), montagne.snd(), g,(Position.H_MAX-Position.H_MIN)/2);
            g.fillRect(XModeleToXVue(0), YModeleToYVue((Position.H_MAX-Position.H_MIN)/2), this.largeur, this.hauteur/2);
        }

        /*On dessine les montagnes du premier plan*/
        for(int i = 0; i< this.fond.getMontagnes1().size();i++){
            Paire<Point,Integer> montagne = this.fond.getMontagnes1().get(i);
            g.setColor(new Color(51,153,255));
            this.dessineMontagne(montagne.fst(), montagne.snd(), g,(Position.H_MAX-Position.H_MIN)/4);
            g.fillRect(XModeleToXVue(0), YModeleToYVue((Position.H_MAX-Position.H_MIN)/4), this.largeur, this.hauteur/4);
        }

        
    }

    public void dessineMontagne(Point sommet, int demi_base, Graphics g, int ymin){
        int x = sommet.getX() ;
        int[] xvar = new int[]{XModeleToXVue(x-demi_base),XModeleToXVue(x),XModeleToXVue(x+demi_base)};
        int[] yvar = new int[]{YModeleToYVue(ymin),YModeleToYVue(sommet.getY()),YModeleToYVue(ymin)};
        g.fillPolygon(xvar,yvar,3);
    }   

    /*Fonction qui dessine la ligne brisee */
    public void dessineLigne(Graphics g){
        g.setColor(new Color(0,0,0));
        for(int i = 0; i < this.ligne.getPoints().size()-1; i++){
            /*Prend deux points consecutifs */
            Point p1 = this.ligne.getPoints().get(i);
            Point p2 = this.ligne.getPoints().get(i+1);
            /*Transforme nos coordonees du modele dans leur equivalent pour la vu */
            int x1 = XModeleToXVue(p1.getX());
            int y1 = YModeleToYVue(p1.getY());
            int x2 = XModeleToXVue(p2.getX());
            int y2 = YModeleToYVue(p2.getY());
            /*Dessine la ligne qui connecte les deux points consecutifs */
            g.drawLine(x1, y1, x2, y2);
        }

        //g.fillOval(XModeleToXVue(Position.BEFORE-100), YModeleToYVue(this.ligne.getPointContact()-100), 100*RATIO_X, 100*RATIO_Y);
    }

    /*Fonction qui dessine le cercle */
    public void dessineCercle(Graphics g){
        g.setColor(new Color(204,102,0));
        for(int i=-3; i<4; i++){
            g.drawOval(circleX+i, circleY+2*i, circleWidth-2*i, circleHeight-4*i);
        }
        
    }
    
    /* Fonction qui affiche le score */
    public void dessineScore(Graphics g){
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("Score = "+Integer.toString(this.score.getScore()), (Position.AFTER+Position.BEFORE-50)*RATIO_X, 40);
    }
    /*Fonction qui dessine le bloc Fin avec son score*/
    public void dessineFin(Graphics g){
        // coordonne du centre de notre fentre
        int x = this.getLargeur()/2;
        int y = this.getHauteur()/2; 
        g.setColor(new Color(255,255,255));
        g.fillRect(x-200, y-200, 400, 400);
        g.setColor(new Color(0,0,0));
        g.drawRect(x-200, y-200, 400, 400);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Vous avez", x-160, y-50);
        g.drawString("Score =", x-160, y+50);
        g.setColor(new Color(255,0,0));
        g.setFont(new Font("TimesRoman", Font.BOLD, 40));
        g.drawString("perdu", x+40, y-50);
        g.drawString(Integer.toString(this.score.getScore()), x+50, y+50);

    }

    /*Une fonction qui prend mon x tel qu'il est definit dans mon modele et donne le x correspondant dans ma vue */
    public int XModeleToXVue(int x){
        return x*RATIO_X;
    }

    /*Une fonction qui prend mon y tel qu'il est definit dans mon modele et donne le y correspondant dans ma vue */
    public int YModeleToYVue(int y){
        return (Position.H_MAX-y)*RATIO_Y;
    }

    /*Fonction qui recalcule l'ordonee de notre y en fonction de sa position courante */
    public void updateY(){
        /* pour trouver l'ordonne de notre cercle dans notre vue */
        /* on passe la coordonnee telle qu'on la voudrait dans notre modele a la fonction YModeleToYVue */
        this.circleY = YModeleToYVue(this.pos.getHauteur()+this.pos.getH()/2);
    }
}
