package me.michqql.gui.widgets;

import me.michqql.gui.Child;
import me.michqql.gui.util.LocationUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Container extends Child {

    protected final List<Child> children = new ArrayList<>();

    public Container(Container container, String key) {
        super(container, key, container.x, container.y, container.width, container.height);
    }

    public Container(Container container, String key, int x, int y, int width, int height) {
        super(container, key, x, y, width, height);
    }

    @Override
    public void tick() {
        for(Child c : children) {
            c.tick();
        }
    }

    @Override
    public void render(Graphics graphics) {
        for(Child c : children) {
            c.render(graphics);
        }
    }

    public void addChild(Child child) {
        if(child == null || child == this) return;
        this.children.add(child);
    }

    public Child getChildByKey(String key) {
        if(this.key.equals(key))
            return this;

        for(Child c : children) {
            if(c.getKey().equals(key)) {
                return c;
            } else if(c instanceof Container) {
                ((Container) c).getChildByKey(key);
            }
        }
        return null;
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Child> getChildrenByLocation(int x, int y) {
        List<Child> results = new ArrayList<>();
        if(LocationUtil.withinBounds(this, x, y))
            results.add(this);

        for(Child c : children) {
            if(LocationUtil.withinBounds(c, x, y)) {
                if(!results.contains(c))
                    results.add(c);
            }

            if(c instanceof Container)
                results.addAll(((Container) c).getChildrenByLocation(x, y));
        }
        return results;
    }

    public List<Child> getSelectedChildren() {
        List<Child> results = new ArrayList<>();
        if(selected)
            results.add(this);

        for(Child c : children) {
            if(c.isSelected())
                results.add(c);

            if(c instanceof Container)
                results.addAll(((Container) c).getSelectedChildren());
        }
        return results;
    }
}
