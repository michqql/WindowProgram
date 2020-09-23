package me.michqql.test;

import me.michqql.gui.ParentGUI;
import me.michqql.gui.widgets.Button;

import java.awt.*;

public class Test {

    public static void main(String[] args) {
        ParentGUI gui = new ParentGUI("GUI", 1280, 720, 1f);
        gui.addChild(new Button(gui, "button", 0, 0, 100, 100) {
            @Override
            public void mouseMove(int x, int y) {
                System.out.println("Mouse Moved: (" + x + ", " + y + ")");
            }

            @Override
            public void mouseClick(int button, int x, int y) {
                System.out.println("Mouse Clicked: (" + x + ", " + y + "), Button: " + button);
                this.selected = !this.selected;
            }

            @Override
            public void tick() {

            }

            @Override
            public void render(Graphics graphics) {
                graphics.setColor((this.selected) ? Color.RED : Color.GREEN);
                graphics.drawRect(this.x, this.y, this.width, this.height);
            }
        });
    }
}
