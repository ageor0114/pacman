//***************************************
//   Wall.java   Author: Austin George
//   Acts as a boundary for PacMan
//***************************************

import javax.swing.*;
import java.awt.*;

public class Wall extends JPanel
{
  private int x,y,width,height,header;
  private JPanel corner1,corner2,corner3,corner4;
  private boolean ghostDoor;
  
  //*************************
  //   Wall()
  //   Default Constructor
  //*************************
  public Wall()
  {
    x = 0;
    y = 0;
    width = 0;
    height = 0;
    header = 100;
    ghostDoor = false;
    
    setGUI();
  }
  
  //***********************************************
  //   Wall(int x, int y, int width, int height)
  //   Partial Constructor
  //***********************************************
  public Wall(int x, int y, int width, int height)
  {
    this(x,y,width,height,false);
    
  }
  
  //******************************************************************
  //   Wall(int x, int y, int width, int height, boolean ghostDoor)
  //   Complete Constructor
  //******************************************************************
  public Wall(int x, int y, int width, int height, boolean ghostDoor)
  {
    header = 100;
    this.x = x;
    this.y = y + header; //header will offset the coordinates so the board shifts down
    this.width = width;
    this.height = height;
    this.ghostDoor = ghostDoor;
    
    //Creates Curved Corners
    corner1 = new JPanel();
    corner2 = new JPanel();
    corner3 = new JPanel();
    corner4 = new JPanel();
    
    setGUI();
  }
  
  //*******************************************************************************
  //   Wall(int x, int y, int width, int height, int c1, int c2, int c3, int c4)
  //   Complete Constructor
  //*******************************************************************************
  public Wall(int x, int y, int width, int height, int c1, int c2, int c3, int c4)
  {
    this(x,y,width,height);
    
    int br = 4; //border radius
    if(c1 > 0) corner1.setBounds(0,0,br,br);
    if(c2 > 0) corner2.setBounds(width-br,0,br,br);
    if(c3 > 0) corner3.setBounds(width-br,height-br,br,br);
    if(c4 > 0) corner4.setBounds(0,height-br,br,br);
  }
  
  //*****************
  //   setGUI()
  //   Sets GUI
  //*****************
  public void setGUI()
  {
    //setPreferredSize( new Dimension(width,height) );
    setLayout(null);
    if (ghostDoor) setBackground(Color.black); //change whenever you feel like
    else setBackground(Color.blue);//new Color(38,35,238));
    corner1.setBackground(Color.black);
    corner2.setBackground(Color.black);
    corner3.setBackground(Color.black);
    corner4.setBackground(Color.black);
    add(corner1);
    add(corner2);
    add(corner3);
    add(corner4);
  }
  
  //*****************
  //   getX()
  //   Gets x
  //*****************
  public int getX()
  { return x; }
  
  //*****************
  //   getY()
  //   Gets y
  //*****************
  public int getY()
  { return y; }
  
  //*****************
  //   getWidth()
  //   Gets width
  //*****************
  public int getWidth()
  { return width; }
  
  //******************
  //   getHeight()
  //   Gets height
  //******************
  public int getHeight()
  { return height; }
  
  //*************************************************
  //   overlap(int xQ. int yQ)
  //   Determines if a coordinate overlaps the dot
  //*************************************************
  public boolean overlap(int xQ, int yQ)
  {
    //Whole PacMan is Above Wall
    if (yQ + 40 <= y) return false;
    
    //Whole PacMan is Below Wall
    if (yQ >= y+height) return false;
    
    //Whole PacMan is Left of Wall
    if (xQ + 40 <= x) return false;
    
    //Whole PacMan is Right of Wall
    if (xQ >= x+width) return false;
    
    return true;
    
    //Don't return true TOO MUCH
    /*if (xQ <= x || xQ >= x+width) return false;
    if (yQ <= y || yQ >= y+height) return false;
    return true;*/
  }
}