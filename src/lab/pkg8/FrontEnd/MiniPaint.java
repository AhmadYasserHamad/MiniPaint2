/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab.pkg8.FrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.*;
import java.lang.reflect.Array;
import javax.swing.*;
import javax.swing.filechooser.*;
import lab.pkg8.BackEnd.*;

/**
 *
 * @author ahmadyasserhamad
 */
public class MiniPaint extends javax.swing.JFrame {

    /**
     * Creates new form MiniPaint
     */
    // Acts as an object that is representative of the ongoing application that allows external classes to interact with the application. 
    public static MiniPaint miniPaint;
    // Arraylist containing all shapes 
    public static lab.pkg8.BackEnd.Canvas canvas = new Canvas();
    // Graphics component needed while drawing the shapes
    public static Graphics g;
    // Indicates, through a string, the shape added - either a circle, line segment, a square, or a rectangle
    public static String shapeAdded;
    // Keeps track of the shapes added to the comboBox so as not to repeat them
    private int shapesCounted = 0;
    // Enables the shapes to be saved into the comboBox in the required format (i.e. 01, 04)
    private String counter = "";
    // Helps the counter in formatting the required string
    private int shapesAdded = 0;
    // Represents the drawing panel
    private JPanel drawingPanel;

    public MiniPaint() {
        miniPaint = this;
        initComponents();
        setTitle("Vector Drawing Application");
        jPanel1.setBackground(Color.WHITE);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setPreferredSize(new Dimension(491, 316));
        drawingPanel = new JPanel() {
            @Override
            // Repaint method calls upon the paintComponent method to redraw the shapes in the arraylist
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.clearRect(0, 0, getWidth(), getHeight());
                lab.pkg8.BackEnd.Shape shape;
                for (lab.pkg8.BackEnd.Shape shapeToBeDrawn : canvas.getShapes()) {
                    if (!(shapeToBeDrawn == null)) {
                        // Draws the shape if it is not null (deleted) in the arraylist
                        shapeToBeDrawn.draw(g);
                    }
                    for (int i = shapesCounted; i < canvas.getShapes().length; i++) {
                        shape = canvas.getShapes()[i];
                        // Each newly added shape is added into the comboBox  
                        if (shape instanceof Circle) {
                            addIntoComboBox("circle");
                        } else if (shape instanceof LineSegment) {
                            addIntoComboBox("line");
                        } else if (shape instanceof Square) {
                            addIntoComboBox("square");
                        } else {
                            addIntoComboBox("rectangle");
                        }
                        shapesCounted++;
                    }
                }
            }
        };
        drawingPanel.setBackground(Color.WHITE);
        drawingPanel.setPreferredSize(new Dimension(491, 316));
        jPanel1.add(drawingPanel, BorderLayout.CENTER);
        jPanel1.revalidate();
        jPanel1.repaint();
        g = drawingPanel.getGraphics();
        jComboBox1.addItem("Choose Shape");
    }

    public void addIntoComboBox(String shapeAdded) {
        // Formats the string as required and adds it into the comboBox
        counter = String.format("%02d", shapesAdded + 1);
        shapesAdded++;
        jComboBox1.addItem(shapeAdded + counter);
    }

    public boolean isNumeric(String str) {
        // Makes sure the choosen option in the gui for the deletion/ colorization/ resizing/ moving of any objects is a numberic object and not the "Choose Shape" option
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Stores the chosen color by the user to colorize the object
    private Color chosenColor;

    public void fillColor(Common shape) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Stored chosenColor by the user
        chosenColor = JColorChooser.showDialog(null, "Colorize", chosenColor);
        if (chosenColor != null) {
            // If a color is chosen, the shape is then filled with the color and the border color is set to null
            shape.setFillColor(chosenColor);
            shape.setColor(null);
        }
        // Redraws the shapes after the edits done
        canvas.refresh(drawingPanel.getGraphics());
    }

    public void saveToFile() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Displays new fileChooser window
        JFileChooser fileSave = new JFileChooser();
        fileSave.setDialogTitle("Save Shapes");
        // Accepts only files of a certain extension
        fileSave.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileSave.addChoosableFileFilter(filter);
        // Stores the save button option
        int saved = fileSave.showSaveDialog(null);
        if (saved == JFileChooser.APPROVE_OPTION) {
            try {
                // Retreives the selected file
                File file = fileSave.getSelectedFile();
                // Creates a FileOutputStream, which enables the compiler to write bytes into the selected file
                FileOutputStream fileOutput = new FileOutputStream(file);
                // Creates a ObjectOutputStream, which enables the compiler to write objects into the selected FileOutputStream through a serialized form
                ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
                // Writes the canvas (object containing an arraylist of the shapes) into the objectOutput, to be stored in the file
                objectOutput.writeObject(canvas);
                objectOutput.close();
                fileOutput.close();
                JOptionPane.showMessageDialog(this, "Shapes saved successfully!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void loadFromFile() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Displays new fileChooser window
        JFileChooser fileLoad = new JFileChooser();
        fileLoad.setDialogTitle("Load Existing Shapes");
        // Accepts only files of a certain extension, the same one specified by the load save function
        fileLoad.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileLoad.addChoosableFileFilter(filter);
        // Stores the load button option
        int loaded = fileLoad.showSaveDialog(null);
        if (loaded == JFileChooser.APPROVE_OPTION) {
            try {
                // Retreives the loaded file
                File file = fileLoad.getSelectedFile();
                // Creates a FileInputStream, which enables the compiler to read the bytes stored in the selected file
                FileInputStream fileInput = new FileInputStream(file);
                // Creates a ObjectInputStream, which enables the compiler to read objects from the selected FileInputStream 
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                // A new arraylist is made to store the output of the objectInput object
                Canvas newCanvas = (Canvas) objectInput.readObject();
                for (lab.pkg8.BackEnd.Shape shape : newCanvas.getShapes()) {
                    // Stores each loaded shape into the exisiting arraylist
                    canvas.addShape(shape);
                }
                // Redraws the shapes in the arraylist
                drawingPanel.repaint();
                JOptionPane.showMessageDialog(this, "Shapes loaded successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void remove(Common shape) {
        // Removes shape from the arraylist
        canvas.removeShape(shape);
        drawingPanel.repaint();
    }

    public void repaintObjects() {
        // Public method to allow other shapes to be drawn in the canvas once they have been created externally from other classes
        drawingPanel.revalidate();
        drawingPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Select Shape");

        jButton1.setText("Colorize");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Circle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Line Segment");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Square");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Rectangle");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton7.setText("Resize");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Move");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Load");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Save");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, 250, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new CreateLineSegment().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new CreateSquare().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new CreateRectangle().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new CreateCircle().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String choice = jComboBox1.getSelectedItem().toString();
        String strIndex = choice.substring(choice.length() - 2);
        if (isNumeric(strIndex)) {
            int index = Integer.parseInt(strIndex) - 1;
            Common shape = (Common) Array.get(canvas.getShapes(), index);
            fillColor(shape);
        } else {
            ImageIcon image = new ImageIcon("warning.png");
            JOptionPane.showMessageDialog(this, "Choosen option is not numeric, please try again!", "Message", JOptionPane.PLAIN_MESSAGE, image);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String choice = jComboBox1.getSelectedItem().toString();
        String strIndex = choice.substring(choice.length() - 2);
        if (isNumeric(strIndex)) {
            jComboBox1.removeItem(choice);
            int index = Integer.parseInt(strIndex) - 1;
            Common shape = (Common) Array.get(canvas.getShapes(), index);
            canvas.removeShape(shape);
            drawingPanel.repaint();
        } else {
            ImageIcon image = new ImageIcon("warning.png");
            JOptionPane.showMessageDialog(this, "Choosen option is not numeric, please try again!", "Message", JOptionPane.PLAIN_MESSAGE, image);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String choice = jComboBox1.getSelectedItem().toString();
        String strIndex = choice.substring(choice.length() - 2);
        if (isNumeric(strIndex)) {
            int index = Integer.parseInt(strIndex) - 1;
            Common shape = (Common) Array.get(canvas.getShapes(), index);
            if (shape instanceof Circle) {
                new ResizeCircle((Circle) shape).setVisible(true);
            } else if (shape instanceof LineSegment) {
                new ResizeLineSegment((LineSegment) shape).setVisible(true);
            } else if (shape instanceof Square) {
                new ResizeSquare((Square) shape).setVisible(true);
            } else if (shape instanceof Rectangle) {
                new ResizeRectangle((Rectangle) shape).setVisible(true);
            }
        } else {
            ImageIcon image = new ImageIcon("warning.png");
            JOptionPane.showMessageDialog(this, "Choosen option is not numeric, please try again!", "Message", JOptionPane.PLAIN_MESSAGE, image);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String choice = jComboBox1.getSelectedItem().toString();
        String strIndex = choice.substring(choice.length() - 2);
        if (isNumeric(strIndex)) {
            int index = Integer.parseInt(strIndex) - 1;
            Common shape = (Common) Array.get(canvas.getShapes(), index);
            new MoveShape(shape).setVisible(true);
        } else {
            ImageIcon image = new ImageIcon("warning.png");
            JOptionPane.showMessageDialog(this, "Choosen option is not numeric, please try again!", "Message", JOptionPane.PLAIN_MESSAGE, image);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        loadFromFile();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        saveToFile();
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniPaint.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiniPaint().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables
}
