/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg8.BackEnd;

import java.io.*;
import java.util.*;

/**
 *
 * @author ahmadyasserhamad
 */
public class Canvas implements DrawingEngine, Serializable {

    private ArrayList<Shape> shapes = new ArrayList();

    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    @Override
    public void removeShape(Shape shape) {
        shape.setFillColor(null);
        shape.setColor(null);
        shapes.set(shapes.indexOf(shape), null);
    }

    @Override
    public Shape[] getShapes() {
        return shapes.toArray(new Shape[shapes.size()]);
    }

    @Override
    public void refresh(java.awt.Graphics canvas) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i) == null) {
            } else {
                shapes.get(i).draw(canvas);
            }
        }

    }
}
