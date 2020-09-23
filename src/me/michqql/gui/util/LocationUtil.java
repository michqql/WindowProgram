package me.michqql.gui.util;

import me.michqql.gui.Child;

public class LocationUtil {

    /**
     * Checks whether the (x, y) coordinate is within the bounds of the object
     * @param child the object to check
     * @param mouseX the location of the mouse
     * @param mouseY the location of the mouse
     * @return {@code true} if in bounds, {@code false} otherwise
     */
    public static boolean withinBounds(Child child, int mouseX, int mouseY) {
        return (child.getX() <= mouseX && mouseX < child.getX() + child.getWidth() &&
                child.getY() <= mouseY && mouseY < child.getY() + child.getHeight());
    }

    /**
     * Scales the dimension down based on the scale
     * @param length the width/height
     * @param scale the scale
     * @return the scaled length (width/height)
     */
    public static int getScaledDimension(int length, float scale) {
        return length * ((int) scale);
    }
}
