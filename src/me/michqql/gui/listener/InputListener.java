package me.michqql.gui.listener;

import me.michqql.gui.Child;
import me.michqql.gui.ParentGUI;
import me.michqql.gui.interfaces.KeyInputEvent;
import me.michqql.gui.interfaces.MouseInputEvent;

import java.awt.event.*;

public class InputListener implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private ParentGUI parent;

    public InputListener(ParentGUI parent) {
        this.parent = parent;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(Child c : parent.getChildrenByLocation(e.getX(), e.getY())) {
            if(c instanceof MouseInputEvent)
                ((MouseInputEvent) c).mouseMove(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Child c : parent.getChildrenByLocation(e.getX(), e.getY())) {
            if(c instanceof MouseInputEvent)
                ((MouseInputEvent) c).mouseClick(e.getButton(), e.getX(), e.getY());
        }
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(Child c : parent.getChildren()) {
            if(c instanceof KeyInputEvent)
                ((KeyInputEvent) c).keyPressed(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}
