//*****************************************
//   Blinky.java   Author: Austin George
//   The Red Ghost in Pac Man
//*****************************************
public class Blinky extends Ghost
{ 
  //*****************************************************
  //   Blinky(GameBoard board, int delay, String file)
  //   Full Constructor
  //*****************************************************
  public Blinky(GameBoard board, int delay, String file)
  {
    super(board,delay,file);
  }
  
  //*******************************
  //   Blinky(GameBoard board)
  //   Partial Constructor
  //*******************************
  public Blinky(GameBoard board)
  {
    this(board,2200);
  }
  
  //****************************************
  //   Blinky(GameBoard board, int delay)
  //   Partial Constructor
  //****************************************
  public Blinky(GameBoard board, int delay)
  {
    super(board,delay,"blinky.png");
  }
  
  //************************************************************
  //   chooseDirection()
  //   Determines direction based on the following algorithm:
  //   - Target PacMan's Location
  //   - Determine Euclidean (Straight-Line) Path to Target
  //   - Take the proper navigation along that route
  //   Personality: Chaser
  //************************************************************
  public void chooseDirection()
  {
    int targetX, targetY;
    //Scatter Mode: Scurry to Top Right Corner
    if(board.getIndex() < delay+scatter)
    {
      targetX = 470;
      targetY = 160;
    }
    //Pursuit Mode
    else
    {
      targetX = board.getPacman().getX();
      targetY = board.getPacman().getY();
    }
    pursueTarget(targetX,targetY);
  }
}