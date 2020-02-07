//***************************************
//   Inky.java   Author: Austin George
//   The Blue Ghost in Pac Man
//***************************************

public class Inky extends Ghost
{
  //***************************************************
  //   Inky(GameBoard board, int delay, String file)
  //   Complete Constructor
  //***************************************************
  public Inky(GameBoard board, int delay, String file)
  {
    super(board,delay,file);
  }
  
  //***************************
  //   Inky(GameBoard board)
  //   Partial Constructor
  //***************************
  public Inky(GameBoard board)
  {
    this(board,3800);
  }
  
  //**************************************
  //   Inky(GameBoard board, int delay)
  //   Partial Constructor
  //**************************************
  public Inky(GameBoard board, int delay)
  {
    super(board,delay,"inky.png");
  }
  
  //************************************************************
  //   chooseDirection()
  //   Determines direction based on the following algorithm:
  //   - If far away from PacMan, pursue him
  //   - Once he gets close to PacMan, randomize movement
  //   Personality: Almost Smart
  //************************************************************
  public void chooseDirection()
  {
    //Determine Straight-Line Distance
    int targetX = board.getPacman().getX();
    int targetY = board.getPacman().getY();
    double distance = Math.sqrt(Math.pow(x+targetX,2)+Math.pow(y+targetY,2));
    
    //Scatter Mode
    if(board.getIndex() < delay+scatter)
    {
      targetX = 390;
      targetY = 650;
      pursueTarget(targetX,targetY);
    }
    //If Far Away: Pursue Pacman
    else if (distance > 600) pursueTarget(targetX,targetY);
    //If Near: Be Stupid
    else randomizeDirection(); 
  }
}