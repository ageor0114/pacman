//***************************************
//   Dot.java   Author: Austin George
//   Exists as a dot for PacMan to eat 
//***************************************

//center label?
//set border none?

//i would prefer to extend JLabel but alas ...

import javax.swing.*;
import java.awt.*;

public class PowerPellet extends JPanel
{
  private int x,y,width,height,header;
  private ImageIcon wholeIcon;//, emptyIcon;
  private ImageIcon wholeIconX;//, emptyIconX;
  private JLabel label;
  private boolean eaten;
  private GameBoard board;
  
  //**************************
  //   PowerPellet()
  //   Default Constructor
  //**************************
  public PowerPellet()
  {
    x = 0;
    y = 0;
    width = 0;
    height = 0;
    header = 100;
    
    setGUI();
  }
  
  //************************************************
  //   PowerPellet(int x, int y, GameBoard board)
  //   Complete Constructor
  //************************************************
  public PowerPellet(int x, int y, GameBoard board)
  {
    header = 100;
    this.x = x;
    this.y = y + header; //header will offset the coordinates so the board shifts down
    width = 20;
    height = 20;
    eaten = false;
    this.board = board;
    
    wholeIcon = new ImageIcon("dot.png");
    //emptyIcon =;
    wholeIconX = new ImageIcon(wholeIcon.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
    //emptyIconX =;
    label = new JLabel(wholeIconX, JLabel.CENTER);
    
    setLayout(null);
    label.setForeground(Color.yellow);
    label.setIconTextGap(0);
    label.setBounds(0,0,20,20);//change from 1,1
    add(label);
    
    setBackground(Color.black);
  }
  
  //**********************
  //   setGUI()
  //   Set GUI
  //**********************
  public void setGUI()
  { setPreferredSize(new Dimension(width,height)); }
  
  //****************
  //   getX()
  //   Get x
  //****************
  public int getX()
  { return x; }
  
  //****************
  //   getY()
  //   Get y
  //****************
  public int getY()
  { return y; }
  
  //*****************
  //   getWidth()
  //   Get width
  //*****************
  public int getWidth()
  { return width; }
  
  //*****************
  //   getHeight()
  //   Gets height
  //*****************
  public int getHeight()
  { return height; }
  
  //*****************************
  //   setEaten(boolean eat)
  //   Sets eaten
  //*****************************
  public void setEaten(boolean eat)
  { 
    eaten = eat; 
    if (eaten)label.setIcon(null);
    else label.setIcon(wholeIconX);
  }
  
  //*****************************************
  //   isEaten()
  //   Determines if power pellet is eaten
  //*****************************************
  public boolean isEaten()
  { return eaten; }
  
  //*************************************************
  //   overlap(int xQ. int yQ)
  //   Determines if a coordinate overlaps the dot
  //*************************************************
  public boolean overlap(int xQ, int yQ)
  {
    //Whole PacMan is Above Wall
    if (yQ + 20 <= y) return false; //20
    
    //Whole PacMan is Below Wall
    if (yQ + 20 >= y+height) return false; //-20
    
    //Whole PacMan is Left of Wall
    if (xQ + 20 <= x) return false; //40
    
    //Whole PacMan is Right of Wall
    if (xQ + 20 >= x+width) return false; //-20
    
    if (eaten == false)
    {
      eaten = true;
      label.setIcon(null);
      setOpaque(true);
      return true;
    }
    return false;
  }
}