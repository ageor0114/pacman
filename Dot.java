//***************************************
//   Dot.java   Author: Austin George
//   Exists as a dot for PacMan to eat 
//***************************************
import javax.swing.*;
import java.awt.*;

public class Dot extends JPanel
{
  private int x,y,width,height,header;
  private ImageIcon wholeIcon;
  private ImageIcon wholeIconX;
  private JLabel label;
  private boolean eaten;
  private GameBoard board;
  
  //*************************
  //   Dot()
  //   Default Constructor
  //*************************
  public Dot()
  {
    x = 0;
    y = 0;
    width = 0;
    height = 0;
    header = 100;
    setGUI();
  }
  
  //****************************************
  //   Dot(int x, int y, GameBoard board)
  //   Complete Constructor
  //****************************************
  public Dot(int x, int y, GameBoard board)
  {
    header = 100;
    this.x = x;
    this.y = y + header; //header will offset the coordinates so the board shifts down
    width = 20;
    height = 20;
    eaten = false;
    this.board = board;
    
    wholeIcon = new ImageIcon("dot.png");
    wholeIconX = new ImageIcon(wholeIcon.getImage().getScaledInstance(6,6,Image.SCALE_SMOOTH));
    label = new JLabel(wholeIconX, JLabel.CENTER);
    
    setLayout(null);
    label.setForeground(Color.yellow);
    label.setIconTextGap(0);
    label.setBounds(7,7,6,6);//10,10
    add(label);
    
    setBackground(Color.black);
  }
  
  //***********************
  //   setGUI()
  //   Set GUI
  //***********************
  public void setGUI()
  { setPreferredSize(new Dimension(width,height)); }
  
  //***********************
  //   getX()
  //   Gets x
  //***********************
  public int getX()
  { return x; }
  
  //***********************
  //   getY()
  //   Gets y
  //***********************
  public int getY()
  { return y; }
  
  //***********************
  //   getWidth()
  //   Gets width
  //***********************
  public int getWidth()
  { return width; }
  
  //***********************
  //   getHeight()
  //   Gets height
  //***********************
  public int getHeight()
  { return height; }
  
  //**************************
  //   setEaten(boolean eat)
  //   Sets eat
  //**************************
  public void setEaten(boolean eat)
  { 
    eaten = eat; 
    if (eaten)label.setIcon(null);
    else label.setIcon(wholeIconX);
  }
  
  //**************************
  //   isEaten()
  //   Gets eaten
  //**************************
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
