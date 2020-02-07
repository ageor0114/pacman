//***************************************
//   Pinky.java   Author: Austin George
//   The Pink Ghost in Pac Man
//***************************************
public class Pinky extends Ghost
{
  //****************************************************
  //   Pinky(GameBoard board, int delay, String file)
  //   Full Constructor
  //****************************************************
  public Pinky(GameBoard board, int delay, String file)
  {
    super(board,delay,file);
  }
  
  //**********************************
  //   Pinky(Gameboard board)
  //   Partial Constructor
  //**********************************
  public Pinky(GameBoard board)
  {
    this(board,3000);
  }
  
  //***************************************
  //   Pinky(Gameboard board, int delay)
  //   Partial Constructor
  //***************************************
  public Pinky(GameBoard board, int delay)
  {
    super(board,delay,"pinky.png");
  }
  
  //******************************************************************
  //   chooseDirection()
  //   Determines direction based on the following algorithm:
  //   - Determine PacMan's Location
  //   - Predit PacMan's tile 4 spots ahead 
  //   - Determine Euclidean (Straight-Line) Path to the Prediction
  //   - Take the proper navigation along that route
  //   Personality: Ambusher
  //   Goal: Cut off PacMan so Blinky can finish the job!
  //******************************************************************
  public void chooseDirection()
  {
    int targetX, targetY;
    
    //Scatter Mode - Scurry to top left corner
    if(board.getIndex() < delay+scatter)
    {
      targetX = 70;
      targetY = 160;
    }
    //Pursuit Mode
    else
    {
      PacMan pacman = board.getPacman();
      targetX = pacman.getX() + pacman.getVX()*40; //4 tiles ahead
      targetY = pacman.getY() + pacman.getVY()*40; //4 tiles ahead
    }
    pursueTarget(targetX,targetY);
  }
}