package control;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Position;

public class ReactionClic implements MouseListener{

    public Position pos ;

    public ReactionClic(Position p){
        this.pos = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        pos.jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
