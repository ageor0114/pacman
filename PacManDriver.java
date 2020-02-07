//***********************************************
//   PacManDriver.java   Author: Austin George
//   Drives the entire program
//***********************************************

import javax.swing.*;

public class PacManDriver
{
  //**************************
  //   main(String[] args)
  //   Main Method
  //**************************
  public static void main(String[] args)
  {
    try
    { 
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    
    //Establish JFrame
    JFrame frame = new JFrame ("PAC MAN");
    frame.add(new GameBoard());
    frame.pack();
    frame.setSize(565,820);
    frame.setResizable(false);
    frame.setVisible(true);
  }
}