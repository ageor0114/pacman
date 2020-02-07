//*****************************************
//   Blinky.java   Author: Austin George
//   The Red Ghost in Pac Man
//*****************************************
import javax.swing.*;
import java.awt.*;

public abstract class Ghost
{
  //Variables are protected so that they can be accessed by children
  protected int x;
  protected int y;
  protected ImageIcon ghost, sicko;
  protected ImageIcon smallGhost, smallSicko;
  protected JLabel label;
  protected int vx, vy, delay, scatter;
  protected GameBoard board;
  
  //****************************************************
  //   Ghost(GameBoard board, int delay, String file)
  //   Complete Constructor
  //****************************************************
  public Ghost(GameBoard board, int delay, String file)
  {
    x = 260;
    y = 380;
    vx = 0;
    vy = -1;
    scatter = 3000;
    this.board = board;
    this.delay = delay;
    
    ghost = new ImageIcon(file);
    sicko = new ImageIcon("sicko.png");
    smallGhost = new ImageIcon(ghost.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    smallSicko = new ImageIcon(sicko.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    label = new JLabel(smallGhost,JLabel.CENTER);
    label.setIconTextGap(0);
  }
  
  //*****************************************
  //   setSickoMode(boolean mode)
  //   Sets Sicko Mode
  //*****************************************
  public void setSickoMode(boolean mode)
  {
    if (mode) label.setIcon(smallSicko);
    else label.setIcon(smallGhost);
  }
  
  //**********************
  //   getLabel()
  //   Gets label
  //**********************
  public JLabel getLabel()
  { return label; }
  
  //*******************************
  //   setLocation(int x, int y)
  //   Sets location
  //*******************************
  public void setLocation(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
    
  //*******************
  //   setX(int x)
  //   Sets x-value
  //*******************
  public void setX(int x)
  { this.x = x; }
    
  //*******************
  //   getX()
  //   Gets x-value
  //*******************
  public int getX()
  { return x; }
  
  //*******************
  //   getY()
  //   Gets y-value
  //*******************
  public int getY()
  { return y; }
    
  //*******************
  //   setY(int y)
  //   Sets y-value
  //*******************
  public void setY(int y)
  { this.y = y; }
  
  //*********************
  //   setVX(int vx)
  //   Sets velocity
  //*********************
  public void setVX(int vx)
  { 
    this.vx = vx; 
    vy = 0;
  }
  
  //*********************
  //   setVY(int vy)
  //   Sets velocity
  //*********************
  public void setVY(int vy)
  { 
    vx = 0;
    this.vy = vy; 
  }
  
  //**********************
  //   move()
  //   Moves the PacMan
  //**********************
  public void move()
  {
    if(board.getIndex() < delay) return;
    if(x == -40 && y == 370 && vx < 0) x = 560;
    if(x == 560 && y == 370 && vx > 0) x = -40;
    if(x == 260 && y == 380) vy = -1;
    
    //Intersection Found >> Randomize Direction
    if (board.isIntersection(x,y)) chooseDirection();      
    
    //old
    if(!(board.isWall(x+vx,y+vy)))
    {
      x+= vx;
      y+= vy;
    }
    else randomizeDirection();
    
  }
  
  //****************************************************
  //   chooseDirection()
  //   Algorithm to be determined in children classes
  //****************************************************
  public abstract void chooseDirection();
  
  //*******************************************
  //   randomizeDirection()
  //   Determines a random & valid direction
  //*******************************************
  public void randomizeDirection()
  {
    boolean unchosen = true;
    if (vx != 0)
    {
      while(unchosen)
      {
        unchosen = false;
        int random = (int)(Math.floor(Math.random() * 3));
        if (random == 0 && !(board.isWall(x+vx,y))) return; //No Change
        else if (random == 1 && !(board.isWall(x,y-1))) setVY(-1); //Turn Up
        else if (random == 2 && !(board.isWall(x,y+1))) setVY(1); //Turn Down
        else unchosen = true;
      }
    }
    //Currently Moving Vertically
    else if (vy != 0)
    {
      while(unchosen)
      {
        unchosen = false;
        int random = (int)(Math.floor(Math.random() * 3));
        if (random == 0 && !(board.isWall(x,y+vy))) return; //No Change
        else if (random == 1 && !(board.isWall(x-1,y))) setVX(-1); //Turn Left
        else if (random == 2 && !(board.isWall(x+1,y))) setVX(1); //Turn Right
      }
    }
  }
  
  //************************************************
  //   pursueTarget(int tX, int tY)
  //   Takes the proper steps a target coordinate
  //************************************************
  public void pursueTarget(int tX, int tY)
  {
    //Currently Moving Horizontally
    if (vx != 0)
    {
      if (tY < y && !(board.isWall(x,y-1))) setVY(-1); //Move Up
      else if (tY > y && !(board.isWall(x,y+1))) setVY(1); //Move Down
    }
    //Currently Moving Vertically
    else if (vy != 0)
    {
      if (tX < x && !(board.isWall(x-1,y))) setVX(-1); //Move Left
      else if (tX > x && !(board.isWall(x+1,y))) setVX(1); //Move Right
    }
  }
  public boolean overlap(int xQ, int yQ)
  {
    //Whole PacMan is Above Wall
    if (yQ + 20 <= y) return false; //20
    
    //Whole PacMan is Below Wall
    if (yQ + 20 >= y+40) return false; //-20
    
    //Whole PacMan is Left of Wall
    if (xQ + 20 <= x) return false; //40
    
    //Whole PacMan is Right of Wall
    if (xQ + 20 >= x+40) return false; //-20
    
    return true;
  }
}