//*****************************************
//   Blinky.java   Author: Austin George
//   The Red Ghost in Pac Man
//*****************************************
public class Clyde extends Ghost
{
  //****************************************************
  //   Clyde(GameBoard board, int delay, String file)
  //   Full Constructor
  //****************************************************
  public Clyde(GameBoard board, int delay, String file)
  {
    super(board,delay,file);
  }
  
  //***********************************
  //   Clyde(GameBoard board)
  //   Partial Constructor
  //***********************************
  public Clyde(GameBoard board)
  {
    this(board,4600);
  }
  
  //***************************************
  //   Clyde(GameBoard board, int delay)
  //   Partial Constructor
  //***************************************
  public Clyde(GameBoard board, int delay)
  {
    super(board,delay,"clyde.png");
  }
  //************************************************************
  //   chooseDirection()
  //   Determines direction based on the following algorithm:
  //   - Target PacMan's Location
  //   - Determine Euclidean (Straight-Line) Path to Target
  //   - Take the proper navigation along that route
  //   Personality: Ignorant
  //************************************************************
  public void chooseDirection()
  {
    //Scatter Mode 
    if(board.getIndex() < delay+scatter)
    {
      int targetX = 150;//510
      int targetY = 650;//60;
      pursueTarget(targetX,targetY);
    }
    //Pursuit Mode -- or lack thereof
    else randomizeDirection(); 
  }
}