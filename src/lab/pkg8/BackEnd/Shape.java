/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg8.BackEnd;

import java.awt.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author ahmadyasserhamad
 */
public interface Shape extends Serializable{
    //Set position
    public void setPosition(Point position);
    public Point getPosition();
    //Update Shape specific properties
    public void setProperties(Map<String,Double> properties);
    public Map<String,Double> getProperties();
    //Colorize
    public void setColor(Color color);
    public Color getColor();
    public void setFillColor(Color color);
    public Color getFillColor();
    //Redraw the shape on the canvas 
    public void draw(java.awt.Graphics canvas);   
}
