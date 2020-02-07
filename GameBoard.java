//**********************************************************
//   GameBoard.java   Author: Austin George
//   Serves as the primary hub for all components of game
//**********************************************************

/*ASK ZINN:
 * Will I get penalized for having protected variables that aren't actually being used in children*/

/*Tidy List:
 * 2) Clean setGUI
 * 4) Eliminate lives
 * */

/*Consider List:
 * 2) White Ghost
 * */


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.*;

public class GameBoard extends JPanel
{
  private int score, highScore, lives;
  private int[][] dotMap;
  private long delay, period;
  private boolean sickoMode, pause;
  private JPanel startPanel, overPanel, leaderPanel;
  private JTextField textField;
  private JTextPane textPane;
  private JLabel scoreLabel, logoLabel, leaderTitle, overLabel, overScoreLabel, highScoreLabel, overHSLabel, promptLabel, bannerLabel;
  private JButton startButton, okButton, leaderButton, exitButton, pauseButton;
  private Font scoreFont;
  private ImageIcon logo, banner, pauseIcon, playIcon, pauseIconX, playIconX;
  private java.util.Timer masterTimer;
  private PacMan pacman; 
  private int mouthIndex, sickoIndex, index, eatIndex, ghostEaten, dotsEaten, pelletsEaten;
  private Wall[] walls;
  private Dot[][] dots;
  private PowerPellet[] pellets;
  private Ghost[] ghosts;
  private ArrayList<Entry> entries;
  private Robot robot;
  
  //******************************
  //   GameBoard()
  //   Default Constructor
  //******************************
  public GameBoard()
  {
    //Actual Code
    score = 0;
    highScore = 0;
    lives = 1;
    index = -1;
    eatIndex = 0;
    mouthIndex = 0;
    sickoIndex = 0;
    delay = 0;
    period = 2;
    ghostEaten = 0;
    dotsEaten = 0;
    pelletsEaten = 0;
    sickoMode = false;
    pause = false;
    startPanel = new JPanel();
    overPanel = new JPanel();
    leaderPanel = new JPanel();
    textField = new JTextField(3);
    textPane = new JTextPane();
    overLabel = new JLabel("GAME OVER");
    scoreLabel = new JLabel("SCORE: " + score);
    overScoreLabel = new JLabel("SCORE: " + score);
    highScoreLabel = new JLabel("HIGHSCORE: " + highScore);
    overHSLabel = new JLabel("HIGHSCORE: " + highScore);
    leaderTitle = new JLabel("LEADERBOARDS");
    promptLabel = new JLabel("Please enter your name:");
    startButton = new JButton("START");
    okButton = new JButton("OK");
    leaderButton = new JButton("SEE LEADERBOARDS");
    exitButton = new JButton("EXIT");
    scoreFont = new Font("Dialog",Font.BOLD,18);
    logo = new ImageIcon("logo.jpg");
    pauseIcon = new ImageIcon("pause.png");
    playIcon = new ImageIcon("play.png");
    pauseIconX = new ImageIcon(pauseIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
    playIconX = new ImageIcon(playIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
    pauseButton = new JButton(pauseIconX);
    banner = new ImageIcon("arcadeBanner.jpg");
    logoLabel = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(200,50,Image.SCALE_SMOOTH)),JLabel.CENTER);
    bannerLabel = new JLabel(new ImageIcon(banner.getImage().getScaledInstance(575,100,Image.SCALE_SMOOTH)),JLabel.CENTER);
    pacman = new PacMan(this);
    ghosts = new Ghost[4];
    ghosts[0] = new Blinky(this);
    ghosts[1] = new Pinky(this);
    ghosts[2] = new Inky(this);
    ghosts[3] = new Clyde(this);
    walls = new Wall[56]; 
    pellets = new PowerPellet[4];
    entries = new ArrayList<Entry>();
    try
    { robot = new Robot(); }
    catch (AWTException e)
    { System.out.println("Error Loading Robot"); }
    
    dotMap = new int[][] 
    {
      //=================   GAME BOARD   ==================
      //----------  Used to create/place dots  ------------
      {1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
      {0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0},
      {1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
      {1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
      {1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
      {1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
      {1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1},
      {1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1},
      {0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0},
      {0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0},
      {1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
      {1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
      {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };
    dots = new Dot[dotMap.length][dotMap[0].length];
    masterTimer = new java.util.Timer();
    masterTimer.scheduleAtFixedRate(new TaskManager(), delay, period);
    setGUI();
  }
  
  //****************************************
  //   setWalls()
  //   Establishes walls
  //****************************************
  public void setWalls()
  {
    //Create Walls
    walls[0] = new Wall(0,0,560,10);
    walls[1] = new Wall(0,0,10,200);
    walls[2] = new Wall(50,50,60,40,1,1,1,1);
    walls[3] = new Wall(150,50,80,40,1,1,1,1);
    walls[4] = new Wall(270,0,20,90,0,0,1,1);
    walls[5] = new Wall(330,50,80,40,1,1,1,1);
    walls[6] = new Wall(450,50,60,40,1,1,1,1);
    walls[7] = new Wall(550,0,10,200);
    walls[8] = new Wall(50,130,60,20,1,1,1,1);
    walls[9] = new Wall(150,130,20,140,1,1,1,1);
    walls[10] = new Wall(210,130,140,20,1,1,1,1);
    walls[11] = new Wall(390,130,20,140,1,1,1,1);
    walls[12] = new Wall(450,130,60,20,1,1,1,1);
    walls[13] = new Wall(270,150,20,60,0,0,1,1);
    walls[14] = new Wall(0,190,110,10);
    walls[15] = new Wall(150,190,80,20,0,1,1,0);
    walls[16] = new Wall(330,190,80,20,1,0,0,1);
    walls[17] = new Wall(450,190,110,10);
    walls[18] = new Wall(100,200,10,60);
    walls[19] = new Wall(450,200,10,60);
    walls[20] = new Wall(210,250,50,10);
    walls[21] = new Wall(300,250,50,10);
    walls[22] = new Wall(-50,260,160,10);
    walls[23] = new Wall(210,250,10,80);
    walls[24] = new Wall(340,250,10,80);
    walls[25] = new Wall(450,260,160,10);
    walls[26] = new Wall(-50,310,160,10);
    walls[27] = new Wall(150,310,20,80,1,1,1,1);
    walls[28] = new Wall(210,320,140,10);
    walls[29] = new Wall(390,310,20,80,1,1,1,1);
    walls[30] = new Wall(450,310,160,10);
    walls[31] = new Wall(100,310,10,80);
    walls[32] = new Wall(210,370,140,20,1,1,1,1);
    walls[33] = new Wall(450,310,10,80);
    walls[34] = new Wall(0,380,110,10);
    walls[35] = new Wall(450,380,110,10);
    walls[36] = new Wall(0,380,10,240);
    walls[37] = new Wall(50,430,60,20,1,1,0,1);
    walls[38] = new Wall(150,430,80,20,1,1,1,1);
    walls[39] = new Wall(270,370,20,80,0,0,1,1);
    walls[40] = new Wall(330,430,80,20,1,1,1,1);
    walls[41] = new Wall(450,430,60,20,1,1,1,0);
    walls[42] = new Wall(550,380,10,240);
    walls[43] = new Wall(0,490,50,20,0,1,1,0);
    walls[44] = new Wall(90,430,20,80,0,0,1,1);
    walls[45] = new Wall(150,490,20,60,1,1,0,0);
    walls[46] = new Wall(210,490,140,20,1,1,1,1);
    walls[47] = new Wall(390,490,20,60,1,1,0,0);
    walls[48] = new Wall(450,430,20,80,0,0,1,1);
    walls[49] = new Wall(510,490,50,20,1,0,0,1);
    walls[50] = new Wall(50,550,180,20,1,1,1,1);
    walls[51] = new Wall(270,490,20,80,0,0,1,1);
    walls[52] = new Wall(330,550,180,20,1,1,1,1);
    walls[53] = new Wall(0,610,560,10);
    
    //Ghost House Walls
    walls[54] = new Wall(220,260,40,60,true);
    walls[55] = new Wall(300,260,40,60,true);
    
    //Add Walls
    for (int i = 0; i < walls.length; i++)
    {
      walls[i].setBounds(walls[i].getX(),walls[i].getY(),walls[i].getWidth(),walls[i].getHeight());
      add(walls[i]);
    }
  }
  
  //*************************************
  //   setPellets()
  //   Establishes the Power Pellets
  //*************************************
  public void setPellets()
  {
    pellets[0] = new PowerPellet(20,60,this);
    pellets[1] = new PowerPellet(520,60,this);
    pellets[2] = new PowerPellet(20,460,this);
    pellets[3] = new PowerPellet(520,460,this);
    
    for (int i = 0; i < pellets.length; i++)
    {
      pellets[i].setBounds(pellets[i].getX(),pellets[i].getY(),pellets[i].getWidth(),pellets[i].getHeight());
      add(pellets[i]);
    }
  }
  
  //***********************
  //   setGUI()
  //   Establishes GUI
  //***********************
  public void setGUI()
  {
    //Start Panel
    startPanel.setLayout(null);
    startPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(175,33,38)));
    startButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(175,33,38)));
    leaderButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(175,33,38)));
    startPanel.setBackground(Color.black);
    startButton.setBackground(new Color(242,134,37));
    leaderButton.setBackground(new Color(242,134,37));
    startButton.setForeground(Color.black);
    leaderButton.setForeground(Color.black);
    startPanel.setBounds(160,300,240,180);
    logoLabel.setBounds(20,20,200,50);
    startButton.setBounds(20,80,200,35);
    leaderButton.setBounds(20,125,200,35);
    startButton.addActionListener(new ActionManager());
    leaderButton.addActionListener(new ActionManager());
    startPanel.add(startButton);
    startPanel.add(leaderButton);
    startPanel.add(logoLabel);
    startPanel.setVisible(true);
    add(startPanel);
    
    //Leader Panel
    leaderPanel.setLayout(null);
    leaderPanel.setBorder(BorderFactory.createMatteBorder(5,5,5,5, Color.yellow));
    exitButton.setBorder(BorderFactory.createMatteBorder(3,3,3,3, new Color(255, 153, 51)));
    leaderPanel.setBounds(160,170,240,460);
    leaderTitle.setBounds(45,20,170,20);
    exitButton.setBounds(20,405,200,35);
    textPane.setBounds(20,60,200,330);
    leaderPanel.setBackground(Color.black);
    exitButton.setBackground(Color.yellow);
    textPane.setBackground(Color.black);
    leaderTitle.setForeground(Color.yellow);
    textPane.setForeground(Color.white);
    exitButton.setForeground(Color.black);
    leaderTitle.setFont(scoreFont);
    exitButton.addActionListener(new ActionManager());
    textPane.setContentType("text/html");
    textPane.setText("<html><b>Bruh</b><br/><p>Wassup</p></html>");
    leaderPanel.setVisible(false);
    leaderPanel.add(exitButton);
    leaderPanel.add(leaderTitle);
    leaderPanel.add(textPane); 
    add(leaderPanel);
    
    //Over Panel
    overPanel.setLayout(null);
    overPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(66, 220, 244)));
    overLabel.setForeground(Color.white);
    overScoreLabel.setForeground(Color.white);
    overHSLabel.setForeground(Color.white);
    promptLabel.setForeground(new Color(66, 220, 244));
    okButton.setBackground(new Color(66, 220, 244));
    overLabel.setFont(scoreFont);
    overScoreLabel.setFont(scoreFont);
    overHSLabel.setFont(scoreFont);
    textField.setFont(scoreFont);
    promptLabel.setFont(new Font("Dialog",Font.BOLD,17));
    overLabel.setBounds(65,10,200,30);
    overScoreLabel.setBounds(20,40,200,30);
    overHSLabel.setBounds(20,70,200,30);
    promptLabel.setBounds(20,100,220,30);
    textField.setBounds(60,130,45,30);
    okButton.setBounds(115,130,65,30);
    overPanel.setBounds(160,300,240,180);
    okButton.addActionListener(new ActionManager());
    overPanel.setBackground(Color.black);
    overPanel.setVisible(false);
    overPanel.add(overLabel);
    overPanel.add(overScoreLabel);
    overPanel.add(overHSLabel);
    overPanel.add(promptLabel);
    overPanel.add(textField);
    overPanel.add(okButton);
    add(overPanel);
    
    //Everything Else
    setPreferredSize(new Dimension(565,820)); 
    setLayout(null);
    setBackground(Color.black);
    pauseButton.setBackground(Color.black);
    bannerLabel.setBounds(0,0,560,100);
    scoreLabel.setBounds(10,725,200,30);
    highScoreLabel.setBounds(380,725,250,30);
    pauseButton.setBounds(255,730,50,50);
    scoreLabel.setFont(scoreFont);
    highScoreLabel.setFont(scoreFont);
    scoreLabel.setForeground(Color.white);
    highScoreLabel.setForeground(Color.white);
    pauseButton.addActionListener(new ActionManager());
    pauseButton.setVisible(false);
    pauseButton.setBorder(BorderFactory.createEmptyBorder());
    pacman.getLabel().setBounds(pacman.getX(),pacman.getY(),40,40);
    add(pacman.getLabel());
    add(scoreLabel);
    add(highScoreLabel);
    add(pauseButton);
    add(bannerLabel);

    //Ghosts
    for (int i = 0; i < ghosts.length; i++)
    {
      ghosts[i].getLabel().setBounds(ghosts[i].getX(),ghosts[i].getY(),40,40);
      add(ghosts[i].getLabel());
    }
    
    //Walls
    setWalls();
    
    //Dots & Pellets
    setPellets();
    for (int row = 0; row < dotMap.length; row++)
    {
      for (int col = 0; col < dotMap[row].length; col++)
      {
        if(dotMap[row][col] == 0) dots[row][col] = null;
        else
        {
          dots[row][col] = new Dot((col+1)*20,(row+1)*20,this);
          dots[row][col].setBounds((col+1)*20,(row+1)*20+100,20,20);
          add(dots[row][col]);
        }
      }
    }
    
    //Text Field
    ((AbstractDocument) textField.getDocument()).setDocumentFilter(new UppercaseFilter());
    textField.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent event) 
        { if (textField.getText().length() == 3) event.consume(); } //terminates input beyond 3
    });
  }
  
  //Start Soundtrack
  public void playStart()
  { playSound("start.wav"); }
  
  //Dot Soundtrack
  public void playDot()
  { playSound("badswap.wav"); } //alternate: dot.wav, badswap
  
  //Death Soundtrack
  public void playDeath()
  { playSound("death.wav"); }
  
  //Sicko Soundtrack
  public void playSicko()
  { playSound("eatghost.wav"); }
 
  //*******************************
  //   playSound(String file)
  //   Plays the requested sound
  //*******************************
  public void playSound(String file)
  {
    try
    {
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(file));
    Clip clip = AudioSystem.getClip();
    clip.open(audioIn);
    clip.start();
    } catch(Exception ex) {
      System.out.println("Deaf Mode Activated");
    }
  }
  
  //************************
  //   update()
  //   Call to update GUI
  //************************
  public void update()
  {
    //Update Labels
    scoreLabel.setText("SCORE: " + score);
    highScoreLabel.setText("HIGHSCORE: " + highScore);
    
    //Update Sprites
    pacman.getLabel().setBounds(pacman.getX(),pacman.getY(),40,40);
    for (int i = 0; i < ghosts.length; i++) ghosts[i].getLabel().setBounds(ghosts[i].getX(),ghosts[i].getY(),40,40);
  }
  
  //**********************************
  //   isWall(int x, int y)
  //   Determines if there's a wall
  //**********************************
  public boolean isWall(int x, int y)
  {
    for (int i = 0; i < walls.length; i++)
      if (walls[i].overlap(x,y)) return true;
    return false;
  }
  
  //*********************************
  //   isDot(int x, int y)
  //   Determines if there's a dot
  //*********************************
  public boolean isDot(int x, int y)
  {  
    for (int row = 0; row < dots.length; row++)
    {
      for (int col = 0; col < dots[row].length; col++)
      {
        //Check if a dot exists and overlaps PacMan
        if (dots[row][col] != null && dots[row][col].overlap(x,y))
        {
          score+= 10;
          dotsEaten++;
          playDot();
          if(pelletsEaten == 4 && dotsEaten == 244) ensueVictory();
          return true;
        }
      }
    }
    return false;
  }
  
  //*************************************
  //   isPellet(int x, int y)
  //   Determines if there's a pellet
  //*************************************
  public boolean isPellet(int x, int y)
  {
    for (int i = 0; i < pellets.length; i++)
    {
      //Check if pellet overlaps PacMan
      if (pellets[i].overlap(x,y)) 
      {
        score+= 50;
        pelletsEaten++;
        sickoMode = true;
        if(pelletsEaten == 4 && dotsEaten == 244) ensueVictory();
        else for (int j = 0; j < ghosts.length; j++) ghosts[j].setSickoMode(true);
        return true;
      }
    }
    return false;
  }
  
  //*************************************
  //   isGhost(int x, int y)
  //   Determines if there's a ghost
  //*************************************
  public boolean isGhost(int x, int y)
  {
    for (int i = 0; i < ghosts.length; i++)
    {
      //Ghost & PacMan Overlap
      if (ghosts[i].overlap(x,y))
      {
        //Sicko Mode
        if (sickoMode)
        {
          eatIndex++;
          score+= 200;
          playSicko();
          ghostEaten = i;
        }
        else ensueFailure();
        return true;
      }
    }
    return false;
  }
  
  //************************
  //   ensueVictory()
  //   Ensues Victory
  //************************
  public void ensueVictory()
  {
    index = -1;
    if (score > highScore) highScore = score;
    overScoreLabel.setText("SCORE: " + score);
    overHSLabel.setText("HIGHSCORE: " + highScore);
    overLabel.setText("   VICTORY");
    overPanel.setVisible(true);
    pauseButton.setVisible(false);
  }
  
  //**********************
  //   ensueFialure()
  //   Ensues Failure
  //**********************
  public void ensueFailure()
  {
    index = -1;  
    if (score > highScore) highScore = score;
    overScoreLabel.setText("SCORE: " + score);
    overHSLabel.setText("HIGHSCORE: " + highScore);
    overLabel.setText("GAME OVER");
    playDeath();
    overPanel.setVisible(true);
    pauseButton.setVisible(false);
  }
  
  //********************************************
  //   isIntersection(int x, int y)
  //   Determines if there's an intersection
  //********************************************
  public boolean isIntersection(int x, int y)
  {
    boolean openX = false;
    boolean openY = false;
    
    if (!isWall(x-1,y) || !isWall(x+1,y)) openX = true;
    if (!isWall(x,y-1) || !isWall(x,y+1)) openY = true;
    
    if (openX && openY) return true;
    return false;
  }
  
  //***************************
  //   setScore(int score)
  //   Sets score
  //***************************
  public void setScore(int score)
  {
    this.score = score;
    scoreLabel.setText("SCORE: " + score);
  }
  
  //**************************
  //   getScore()
  //   Gets score
  //**************************
  public int getScore()
  { return score; }
  
  //**************************
  //   getPacman()
  //   Gets PacMan
  //**************************
  public PacMan getPacman()
  { return pacman; }
  
  //**************************
  //   getIndex()
  //   Gets the index
  //**************************
  public int getIndex()
  { return index; }
  
  //*************************
  //   TaskManager
  //   Handles all actions
  //*************************
  private class TaskManager extends java.util.TimerTask
  {
    public void run() {
      if(pause) return;
      
      //Freeze game while eating ghost
      if(eatIndex > 0) 
      {
        eatIndex++;
        if (eatIndex > 250) 
        {
          eatIndex = 0;
          ghosts[ghostEaten].setX(260);
          ghosts[ghostEaten].setY(380);
        }
      }
      else if (index >= 0)
      {
        //Mouth Animation
        if (mouthIndex == 60)
        {
          pacman.alternateMouth();
          mouthIndex = 0;
        }
        
        //Sicko Mode
        if (sickoIndex == 5000)
        {
          sickoMode = false;
          for (int i = 0; i < ghosts.length; i++) ghosts[i].setSickoMode(false);
          sickoIndex = 0;
          eatIndex = 0;
        }
        
        //Move PacMan & Ghosts
        if (index == 2200) pauseButton.setVisible(true); //move?
        if (index > 2200 && index % 3 == 0) pacman.move();
        if (index > 2200 && ((sickoMode && index % 6 == 0) || (!sickoMode && index % 3 == 0)))
          for (int i = 0; i < ghosts.length; i++) ghosts[i].move();
        
        //Adjust High Score
        if (score > highScore) highScore = score;
        
        //Iterate & Update
        update();
        mouthIndex++;
        if(sickoMode) sickoIndex++;
        if (index != -1) index++;
      }
    }
  }
  
  //*************************************************
  //   UppercaseFilter extends DocumentFilter
  //   Ensures that text field is uppercase
  //*************************************************
  private class UppercaseFilter extends DocumentFilter
  {
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException 
    { fb.insertString(offset, text.toUpperCase(), attr); }
    
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
    { fb.replace(offset, length, text.toUpperCase(), attrs); }
  }
  
  //*************************************************
  //   ActionManager implements ActionListener
  //   Class that handles all buttons
  //*************************************************
  private class ActionManager implements ActionListener 
  {
    public void actionPerformed(ActionEvent event) 
    {
      //OK BUTTON
      if (event.getSource() == okButton)
      {
        //Provide warning that name must be entered
        if(textField.getText().equals("")) 
        {
          okButton.setBackground(new Color(255,119,119));
          promptLabel.setForeground(new Color(255,119,119));
          return;
        }
        else 
        {
          okButton.setBackground(new Color(66, 220, 244));
          promptLabel.setForeground(new Color(66, 220, 244));
        }
        
        //Add New Entry
        entries.add(new Entry(textField.getText(),score));
        
        //Restart Logistics
        textField.setText(""); 
        pacman.setLocation(260,430);
        for(int i = 0; i < ghosts.length; i++) ghosts[i].setLocation(260,380); 
        for (int row = 0; row < dots.length; row++)
        {
          for (int col = 0; col < dots[row].length; col++)
          {
            if(dots[row][col] != null) dots[row][col].setEaten(false);
          }
        }
        for (int i = 0; i < pellets.length; i++) pellets[i].setEaten(false);
        index = -1;
        score = 0;
        update();
        
        //Adjust Visibility
        overPanel.setVisible(false);
        startPanel.setVisible(true);
      }
      
      //START BUTTON
      else if (event.getSource() == startButton)
      {
        playStart();
        startPanel.setVisible(false);
        overPanel.setVisible(false);
        score = 0;
        index = 0;
        lives = 1;
        pelletsEaten = 0;
        dotsEaten = 0;
        sickoIndex = 0;
        mouthIndex = 0;
      }
      
      //LEADER BUTTON
      else if (event.getSource() == leaderButton)
      {
        //Sort scores in descending order
        Collections.sort(entries);
        Collections.reverse(entries);
        
        //Format Leaderboards
        String x = "&nbsp;";//space
        String html = "<html><div style='font-family:dialog'><div style='color: white'><b>"+x+x+x+"RANK"+x+x+x+x+"NAME"+x+x+x+x+"SCORE</b></br>";
        for (int i = 0; i < entries.size(); i++)
        {
          String name = entries.get(i).getName();
          int score = entries.get(i).getScore();
          if (name == "") name = x+x+x+x+x+x+x+x+x;//Filler Text
          
          html+= "<p>" +x+x+x;
          if (i == 0) html+= "1st ";
          else if (i == 1) html+= "2nd";
          else if (i == 2) html+= "3rd";
          else html += (i+1) + "th";
          
          html+= x+x+x+x+x+x+x+x + name + x+x+x+x+x+x+x+x + score + "</p>";
        }
        html+= "</div></div></html>";
        textPane.setText(html);
        
        //Adjust Visibility
        leaderPanel.setVisible(true);
        startPanel.setVisible(false);
      }
      
      //EXIT BUTTON
      else if (event.getSource() == exitButton)
      {
        leaderPanel.setVisible(false);
        startPanel.setVisible(true);
      }
      
      else if (event.getSource() == pauseButton)
      {
        if(pause)
        {
          pause = false;
          pauseButton.setIcon(pauseIconX);
        }
        else
        {
          pause = true;
          pauseButton.setIcon(playIconX);
        }
        robot.keyPress(KeyEvent.VK_TAB);
      }
    }
  }
}