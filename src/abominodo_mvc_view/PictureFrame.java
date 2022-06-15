package abominodo_mvc_view;

/**
 * This class serves as the Commands gatekeeper
 * it is going to work commands sent by the model
 * and keep track of them for undo purposes and operations that must be redone
 * 
 * @author Oshni M
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import abominodo_mvc_controller.*;
import abominodo_mvc_model.*;

public class PictureFrame {
public int[] reroll = null;
Aardvark master = null;

public class DominoPanel extends JPanel {
  private static final long serialVersionUID = 4190229282411119364L;

  public void drawGrid(Graphics g) {
  	
    for (int are = 0; are < 7; are++) {
      for (int see = 0; see < 8; see++) {
        drawDigitGivenCentre(g, 30 + see * 20, 30 + are * 20, 20, master.grid[are][see], Color.BLACK);
      }
    }
  }
  
  
 

  public void drawGridLines(Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    for (int are = 0; are <= 7; are++) {
      g.drawLine(20, 20 + are * 20, 180, 20 + are * 20);
    }
    for (int see = 0; see <= 8; see++) {
      g.drawLine(20 + see * 20, 20, 20 + see * 20, 160);
    }
  }

  public void drawHeadings(Graphics g) {
    for (int are = 0; are < 7; are++) {
      fillDigitGivenCentre(g, 10, 30 + are * 20, 20, are+1);
    }

    for (int see = 0; see < 8; see++) {
      fillDigitGivenCentre(g, 30 + see * 20, 10, 20, see+1);
    }
  }

  public void drawDomino(Graphics g, Domino d) 
  {
    if (d.placed) 
    {
      int y = Math.min(d.ly, d.hy);
      int x = Math.min(d.lx, d.hx);
      int w = Math.abs(d.lx - d.hx) + 1;
      int h = Math.abs(d.ly - d.hy) + 1;
      g.setColor(Color.WHITE);
      //g.setColor(c);
      g.fillRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
      g.setColor(Color.RED);
      g.drawRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
      drawDigitGivenCentre(g, 30 + d.hx * 20, 30 + d.hy * 20, 20, d.high, Color.BLUE);
      drawDigitGivenCentre(g, 30 + d.lx * 20, 30 + d.ly * 20, 20, d.low,  Color.BLUE);
    }
  }

 /* void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
   // int radius = diameter / 2;	// The value of the local variable radius has not being used - this is a bad smell- Temporary Field and this is fixed like this
    g.setColor(Color.BLACK);
    // g.drawOval(x - radius, y - radius, diameter, diameter);
    FontMetrics fm = g.getFontMetrics();
    String txt = Integer.toString(n);
    g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
  }*/

  void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n, Color c) {
    // int radius = diameter / 2; // The value of the local variable radius has not being used - this is a bad smell- Temporary Field and this is fixed like this
    g.setColor(c);
    // g.drawOval(x - radius, y - radius, diameter, diameter);
    FontMetrics fm = g.getFontMetrics();
    String txt = Integer.toString(n);
    g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
  }

  void fillDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
    int radius = diameter / 2;
    g.setColor(Color.GREEN);
    g.fillOval(x - radius, y - radius, diameter, diameter);
    g.setColor(Color.BLACK);
    g.drawOval(x - radius, y - radius, diameter, diameter);
    FontMetrics fm = g.getFontMetrics();
    String txt = Integer.toString(n);
    g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
  }

  protected void paintComponent(Graphics g) {
    g.setColor(Color.YELLOW);
    g.fillRect(0, 0, getWidth(), getHeight());

    if (master.mode == 1) {
      drawGridLines(g);
      drawHeadings(g);
      drawGrid(g);
      master.drawGuesses(g);
    }
    if (master.mode == 0) {
      drawGridLines(g);
      drawHeadings(g);
      drawGrid(g);
      master.drawDominoes(g);
    }
  }

  public Dimension getPreferredSize() {
    return new Dimension(202, 182);
  }
}

public DominoPanel dp;
public DominoPanel myDp=new DominoPanel();
//public void pictureFrame(Aardvark sf) { // The constructor and method names are not the same - this is indeed a bad smell (Data Clumps) This is how it was fixed.
public void pictureFrame(Aardvark sf) {
  master = sf;
  if (dp == null) {
    JFrame f = new JFrame("Abominodo");
    dp = new DominoPanel();
    f.setContentPane(dp);
    f.pack();
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    f.setVisible(true);
    setUpDifficulties();
    setUpMainMenu();
    setUpProfile();
    
  }
}



public void setUpProfile() {
	  JFrame jf = new JFrame("Setup Profile");
    Container cp = jf.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JLabel label = new JLabel("<html>Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe"
    		+ "<BR>Version 1.0 (c), Kevan Buckley, 2010<BR></html>", SwingConstants.CENTER);
    label.setForeground(Color.RED);
    label.setVerticalAlignment(SwingConstants.TOP);
    label.setPreferredSize(new Dimension(300, 50));
    cp.add(label);
 // Create a JTextField with text and icon and set its appearances
    JTextField textField = new JTextField("Enter Your Name Here", 15);
    textField.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
    textField.setForeground(Color.RED);
   
    textField.setPreferredSize(new Dimension(300, 30));
    textField.setToolTipText("Enter Your Name Here");
    cp.add(textField);

    // Create a JButton with text and icon and set its appearances
    JButton button = new JButton(); 
    button.setText("Setup Profile");
    
    //button.setVerticalAlignment(SwingConstants.BOTTOM);  
   //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    button.setHorizontalTextPosition(SwingConstants.CENTER); 
    button.setVerticalTextPosition(SwingConstants.TOP);    
    button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button.setBackground(new Color(231, 240, 248));
    button.setForeground(Color.BLUE);
    button.setPreferredSize(new Dimension(300, 30));
    button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  // Can activate via the Alt-B (Buttons only)
    cp.add(button);

    

    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jf.setLocationRelativeTo(null); 
    jf.setSize(500, 150);  // or pack()
    jf.setVisible(true);
    jf.toFront();
    

//    // Print description of the JComponents via toString()
//    System.out.println(label);
//    System.out.println(button);
//    System.out.println(textField);
 }
public void setUpMainMenu() {
	  JFrame jf = new JFrame("Main Menu");
    Container cp = jf.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JLabel label = new JLabel("Welcome", SwingConstants.CENTER);
      label.setForeground(Color.RED);
      cp.add(label);
    // Create a JButton with text and icon and set its appearances
    JButton button = new JButton(); 
    button.setText("Play");
    button.setVerticalAlignment(SwingConstants.TOP); 
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER);
    //button.setVerticalTextPosition(SwingConstants.TOP);    
    button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button.setBackground(new Color(231, 240, 248));
    button.setForeground(Color.BLUE);
    button.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  // Can activate using the Alt-B (buttons only)
    cp.add(button);
    
    
    JButton button1 = new JButton(); 
    button1.setText("View High Scores");
    button1.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);   
    button1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button1.setBackground(new Color(231, 240, 248));
    button1.setForeground(Color.BLUE);
    button1.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  // Can activate using the Alt-B (Buttons only)
    cp.add(button1);
    
    
    JButton button2 = new JButton(); 
    button2.setText("View Rules");
    button2.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);    
    button2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button2.setBackground(new Color(231, 240, 248));
    button2.setForeground(Color.BLUE);
    button2.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  // Can activate via the Alt-B (Buttons only)
    cp.add(button2);
    
    JButton button3 = new JButton(); // Should use the setter to set the Text and Icon
    button3.setText("Quit");
    button3.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);    
    button3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button3.setBackground(new Color(231, 240, 248));
    button3.setForeground(Color.BLUE);
    button3.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  // Can activate via the Alt-B (Buttons only)
    cp.add(button3);

    

    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jf.setLocationRelativeTo(null); 
    jf.setSize(500, 350);  // or pack()
    jf.setVisible(true);
    jf.toFront();
    

//    // Print description of the JComponents via toString()
//    System.out.println(label);
//    System.out.println(button);
//    System.out.println(textField);
 }

public void setUpDifficulties() {
	  JFrame jf = new JFrame("Difficulties");
    Container cp = jf.getContentPane();
    cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JLabel label = new JLabel("Choose Difficulties", SwingConstants.CENTER);
      label.setForeground(Color.RED);
      cp.add(label);
    // Create a JButton with text and icon and set its appearances.
    JButton button = new JButton(); // use setter to set text and icon.
    button.setText("Simples");
    button.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);   
    button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button.setBackground(new Color(231, 240, 248));
    button.setForeground(Color.BLUE);
    button.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  
    cp.add(button);
    
    
    JButton button1 = new JButton(); 
    button1.setText("Not-so-simples");
    button1.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);    
    button1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button1.setBackground(new Color(231, 240, 248));
    button1.setForeground(Color.BLUE);
    button1.setPreferredSize(new Dimension(180, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B);  
    cp.add(button1);
    
    
    JButton button2 = new JButton(); 
    button2.setText("Super-duper-shuffled");
    button2.setVerticalAlignment(SwingConstants.TOP);  
    //button.setHorizontalAlignment(SwingConstants.RIGHT); 
    //button.setHorizontalTextPosition(SwingConstants.CENTER); 
    //button.setVerticalTextPosition(SwingConstants.TOP);    
    button2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
    button2.setBackground(new Color(231, 240, 248));
    button2.setForeground(Color.BLUE);
    button2.setPreferredSize(new Dimension(240, 30));
    //button.setToolTipText("This is a JButton");
    //button.setMnemonic(KeyEvent.VK_B); 
    cp.add(button2);
    
   

    

    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    jf.setLocationRelativeTo(null); // The center of the window on the screen
    jf.setSize(500, 350);  // or pack()
    jf.setVisible(true);
    jf.toFront();
    

//    // Print description of the JComponents via toString()
//    System.out.println(label);
//    System.out.println(button);
//    System.out.println(textField);
 }
//This is basically an Unused method - This is a bad smell- Refused Request this is how its fixed 
/*public void reset(Aardvark sf) {
	master = sf;
  if (dp == null) 
  {
    JFrame f = new JFrame("Abominodo");
    dp = new DominoPanel();
    f.setContentPane(dp);
    f.pack();
    f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    f.setVisible(false);
  }
}*/{

}
}
