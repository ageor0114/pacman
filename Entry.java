//****************************************
//   Entry.java   Author: Austin George
//   Acts as an entry in Leaderboards
//****************************************
public class Entry implements Comparable
{
  private String name;
  private int score;
  public Entry()
  {
    name="";
    score=0;
  }
  
  public Entry(String name, int score)
  {
    this.name = name;
    this.score = score;
  }
  
  public String getName()
  { return name; }
  
  public int getScore()
  { return score; }
  
  public int compareTo(Object obj)
  {
    Entry entry = (Entry)obj;
    if(score == entry.getScore()) return 0;
    else if (score > entry.getScore()) return 1;
    else return -1;
  }
}