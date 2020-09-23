package me.michqql.gui.widgets;

import me.michqql.gui.Child;
import me.michqql.gui.interfaces.MouseInputEvent;

public abstract class Button extends Child implements MouseInputEvent {

    public Button(Container container, String key, int x, int y, int width, int height) {
        super(container, key, x, y, width, height);
    }
}
