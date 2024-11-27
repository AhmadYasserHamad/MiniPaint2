/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg8.BackEnd;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Map;

/**
 *
 * @author ahmadyasserhamad
 */
public class Circle extends Common implements Serializable {

    public Circle(Point point, Map<String, Double> properties, Color color, Color fillColor) {
        super(point, properties, color, fillColor);
    }

    @Override
    public void draw(java.awt.Graphics canvas) {
        RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D graphics2D = (Graphics2D) canvas;
        Ellipse2D.Double circle = new Ellipse2D.Double(getPosition().x, getPosition().y, 2 * getProperties().get("radius"), 2 * getProperties().get("radius"));
        if (getFillColor() != null) {
            graphics2D.setColor(getFillColor());
            graphics2D.fill(circle);
        } else {
            graphics2D.setColor(getColor());
        }
        graphics2D.draw(circle);
        graphics2D.setRenderingHints(render);
    }
}
