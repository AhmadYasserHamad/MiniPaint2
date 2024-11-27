/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg8.BackEnd;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;
import lab.pkg8.BackEnd.Common;

/**
 *
 * @author ahmadyasserhamad
 */
public class Square extends Common implements Serializable {

    public Square(Point point, Map<String, Double> properties, Color color, Color fillColor) {
        super(point, properties, color, fillColor);
    }


    @Override
    public void draw(java.awt.Graphics canvas) {
        RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Graphics2D graphics2D = (Graphics2D) canvas;
        Rectangle2D.Double square = new Rectangle2D.Double(getPosition().x, getPosition().y, getProperties().get("side"), getProperties().get("side"));
        if (getFillColor() != null) {
            graphics2D.setColor(getFillColor());
            graphics2D.fill(square);
        } else {
            graphics2D.setColor(getColor());
        }        graphics2D.draw(square);
        graphics2D.setRenderingHints(render);
    }
}
