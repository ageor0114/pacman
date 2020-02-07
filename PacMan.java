//*****************************************
//   PacMan.java   Author: Austin George
//*****************************************
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PacMan
{
  private String direction; 
  private int x;
  private int y;
  private ImageIcon left, right, up, down, solid;
  private ImageIcon smallLeft, smallRight, smallUp, smallDown, smallSolid;
  private JLabel label;
  private boolean open;
  private int vx, vxP, vy, vyP, speed;
  private GameBoard board;
  
  //****************************************
  //   PacMan(GameBoard board, int speed)
  //   Constructor
  //****************************************
  public PacMan(GameBoard board, int speed)
  {
    this.board = board;
    direction = "";
    //Coordinates & Velocities
    x = 260;
    y = 430;
    vx = 0;
    vxP = 0;
    vy = 0;
    vyP = 0;
    this.speed = speed;
    
    //Image Icons
    left = new ImageIcon("pacmanLeft.png");
    smallLeft = new ImageIcon(left.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    right = new ImageIcon("pacmanRight.png");
    smallRight = new ImageIcon(right.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    up = new ImageIcon("pacmanUp.png");
    smallUp = new ImageIcon(up.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    down = new ImageIcon("pacmanDown.png");
    smallDown = new ImageIcon(down.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    solid = new ImageIcon("pacmanSolid.png");
    smallSolid = new ImageIcon(solid.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
    
    //Label - what gets used in the GameBoard
    label = new JLabel(smallLeft, JLabel.CENTER);
    label.setIconTextGap(0);
    
    //Key Listener
    label.addKeyListener(new KeyManager());
    label.setFocusable(true);
  }
  
  public PacMan(GameBoard board)
  {
    this(board,1);
  }
  
  //**********************
  //   move()
  //   Moves the PacMan
  //**********************
  public void move()
  {
    board.isPellet(x,y);
    board.isDot(x,y);
    board.isGhost(x,y);
    //Portal Doors
    if(x == -40 && y == 370 && vx < 0) x = 560;
    if(x == 560 && y == 370 && vx > 0) x = -40;
    
    //Potential Velocities
    if ((vxP != 0 || vyP != 0) && !(board.isWall(x+vxP,y+vyP)))
    {
      vx = vxP;
      vy = vyP;
      //They served their purpose, now they can go ...
      vxP = 0;
      vyP = 0;
    }
    
    if(!(board.isWall(x+vx,y+vy)))
    {
      x+= vx;
      y+= vy;
    }
    else
    {
      //Note: The hope is that this line will never be reached,
      //      but it exists as a failsafe if the ghost believes it has nowhere to go ...
      vx = 0;
      vy = 0;
    }
    
    //Updates icon
    updateIcon();
  }
  
  //***********************
  //   alternateMouth()
  //   Alternates mouth
  //***********************
  public void alternateMouth()
  { open ^= true; }
  
  public void updateIcon()
  {
    if (open)
    {
      if (vx < 0 & vy == 0) label.setIcon(smallLeft);
      if (vx > 0 & vy == 0) label.setIcon(smallRight);
      if (vy < 0 & vx == 0) label.setIcon(smallUp);
      if (vy > 0 & vx == 0) label.setIcon(smallDown);
    }
    else
    {
      label.setIcon(smallSolid);
    }
  }
  
  //**********************
  //   openMouth()
  //   Opens mouth
  //**********************
  public void openMouth()
  { open = true; }
  
  //**********************
  //   closeMouth()
  //   Closes mouth
  //**********************
  public void closeMouth()
  { open = false; }
  
  //***************
  //   getX()
  //   Gets x
  //***************
  public int getX()
  { return x; }
  
  //*******************
  //   getLabel()
  //   Gets label
  //*******************
  public JLabel getLabel()
  { return label; }
  
  //********************************
  //   setLocation(int x , int y)
  //   Sets location
  //********************************
  public void setLocation(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  //************************
  //   setX(int x)
  //   Sets x
  //************************
  public void setX(int x)
  { this.x = x; }
  
  //************************
  //   getY(i)
  //   Gets x
  //************************
  public int getY()
  { return y; }
    
  //************************
  //   setY(int y)
  //   Sets y
  //************************
  public void setY(int y)
  { this.y = y; }
  
  //************************
  //   setVX(int vx)
  //   Sets vx
  //************************
  public void setVX(int vx)
  { this.vx = vx; }
  
  //************************
  //   setVY(int vy)
  //   Sets vy
  //************************
  public void setVY(int vy)
  { this.vy = vy; }
  
  //************************
  //   setVX()
  //   Gets vx
  //************************
  public int getVX()
  { return vx; }
  
  //************************
  //   setX(int x)
  //   Sets x
  //************************
  public int getVY()
  { return vy; }
  
  //*************************************************
  //   KeyManager implements KeyListener
  //   Class that handles all key presses
  //*************************************************
  private class KeyManager implements KeyListener
  {
    //*********************************************************
    //   keyReleased(KeyEvent event)
    //   Registers key released (INHERITED from KeyListener)
    //*********************************************************
    public void keyReleased(KeyEvent e)
    {
      int key = e.getKeyCode();
      
      //Left Button
      if (key == KeyEvent.VK_LEFT)
      {
        vxP = -1*speed;
        vyP = 0;
      }
      
      //Right Button
      if (key == KeyEvent.VK_RIGHT)
      {
        vxP = speed;
        vyP = 0;
      }
      
      //Up Button
      if (key == KeyEvent.VK_UP)
      {
        vyP = -1*speed;
        vxP = 0;
      }
      
      //Down Button
      if (key == KeyEvent.VK_DOWN)
      {
        vyP = speed;
        vxP = 0;
      }
    }
    
    //Not Used
    public void keyTyped(KeyEvent e)
    {
    }
     
    //Not Used
    public void keyPressed(KeyEvent e)
    {
    }
  }
}