package me.michqql.gui;

import me.michqql.gui.interfaces.IUpdate;
import me.michqql.gui.util.NamespaceKey;
import me.michqql.gui.widgets.Container;

public abstract class Child implements IUpdate {

    protected final NamespaceKey key;
    protected final Container parent;
    protected int layer, x, y, width, height;

    protected boolean selected;

    public Child(Container container, String key, int x, int y, int width, int height) {
        this.key = new NamespaceKey(key);
        this.parent = container;
        this.layer = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        if(container != null) {
            container.addChild(this);
        }
    }

    public NamespaceKey getKey() {
        return key;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSelected() {
        return selected;
    }
}
