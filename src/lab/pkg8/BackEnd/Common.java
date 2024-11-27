/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg8.BackEnd;

import java.awt.*;
import java.io.*;
import java.util.Map;

/**
 *
 * @author ahmadyasserhamad
 */
public abstract class Common implements Shape, Serializable{
    private Point point;
    private Map<String,Double> properties;
    private Color color;
    private Color fillColor;

    public Common(Point point, Map<String, Double> properties, Color color, Color fillColor) {
        this.point = point;
        this.properties = properties;
        this.color = color;
        this.fillColor = fillColor;
    }
    
    @Override
    public void setPosition(Point position){
        this.point = position;
    }
    
    @Override
    public Point getPosition(){
        return point;
    }
    
    @Override
    public void setProperties(Map<String,Double> properties){
        this.properties = properties;
    }
    
    @Override
    public Map<String,Double> getProperties(){
        return properties;
    }
    @Override
    public void setColor(Color color){
        this.color = color;
    }
    @Override
    public Color getColor(){
        return color;
    }
    @Override
    public void setFillColor(Color color){
        this.fillColor = color;
    }
    @Override
    public Color getFillColor(){
        return fillColor;
    }
}
