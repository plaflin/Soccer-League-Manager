package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// Class to  model a team
public class Team implements Comparable<Team> {
  // Creating variables and collections to manage a team
  private String mTeamName;
  private String mCoachName;
  private List<Player> mPlayers = new ArrayList<Player>();
  private Map<Integer, ArrayList<Player>> heightReport = new TreeMap<Integer, ArrayList<Player>>();
  private Map<Boolean, Integer> experienceReport = new TreeMap<Boolean, Integer>();
    
  // Constructor
  public Team (String teamName, String coachName){
    mTeamName = teamName;
    mCoachName = coachName;
  } // End Team
  
  public int getTeamSize(){
    return mPlayers.size();
  } // End getTeamSize
  
  public String getTeamName(){
    return mTeamName;
  } // End getTeamName
  
  public String getCoachName(){
    return mCoachName;
  } // End getCoachName
  
  // Function to add a player to the team
  public boolean addPlayer(Player player){
    // Internal check to make sure teams don't go above size
    if(mPlayers.size() < 11){
      mPlayers.add(player);
      return true;
    }
    else {
      System.out.println("There are already 11 players on this team.");
      return false;
    } // End If
  } // End addPlayer
  
  public void showRoster(){
    int kidCount = 1;
    for(Player players : mPlayers){
      System.out.printf("%-2d Player name: %-10s  %-11s height: %-2d inches experience: %-10s %n", kidCount++,
                        players.getFirstName(), players.getLastName(), players.getHeightInInches(), players.isPreviousExperience());
    } // End for loop
  } // End showRoster
  
  public void removePlayer(int rosterNo){
    if(mPlayers.size() > 0){
      mPlayers.remove(rosterNo);
    }
    else {
      System.out.println("No players on this team");
    } // End if
  } // End removePlayer
  
  public Player getPlayer(int rosterNo){
    return mPlayers.get(rosterNo);
  } // End getPlayer
  
  // This function fills a map with the keys/values for the height report
  public void getHeightReport(){
    // This loop puts the key height in inches and value player into the map
    for(Player kid : mPlayers){
      if (heightReport.get(kid.getHeightInInches()) == null) { 
         heightReport.put(kid.getHeightInInches(), new ArrayList<Player>()); 
      } // End if
        // This adds a player to the list if their height is already in the map
        heightReport.get(kid.getHeightInInches()).add(kid);   
    } // End for loop
    
    // These loops extract the data from the map and presesnts it to the administrator
    for (Map.Entry<Integer, ArrayList<Player>> entry : heightReport.entrySet()){
        int key = entry.getKey();
        ArrayList<Player> value = entry.getValue();
        System.out.println("The number of players at height "+key+" is "+value.size());
      
        for(Player kids : value){
          System.out.printf("Player name: %-12s %-10s %n", kids.getFirstName(), kids.getLastName());
        }  // End inner for loop
    } // End outer for loop
  }  // End getHeightReport
  
  // This function builds the team experience report
  public void getExperienceReport(){
    int mExperienceCount = 1;
    int mInexperienceCount = 1;
    // This loop fills the map for the player experience report
    for(Player kid : mPlayers){
      if (experienceReport.get(kid.isPreviousExperience()) == null) {
        if(kid.isPreviousExperience() == true){
        experienceReport.put(kid.isPreviousExperience(), mExperienceCount++);
        }
        else {
        experienceReport.put(kid.isPreviousExperience(), mInexperienceCount++);
        } // End inner If
      }
      else {
          if(kid.isPreviousExperience() == true){
             experienceReport.put(kid.isPreviousExperience(), mExperienceCount++);
          }
          else {
              experienceReport.put(kid.isPreviousExperience(), mInexperienceCount++);
          } // End inner If
      } // End outer If      
    } // End for loop
    
    // This block prints the team name then does the percentage calculation and prints it to screen
    System.out.printf("Team name: %s %n", mTeamName);
    int experienced = experienceReport.get(true);
    int inexperienced = experienceReport.get(false);
    double experiencePercentage = ((double)experienced/11) * 100;
    System.out.printf("Percentage of experienced players = %f percent %n", experiencePercentage);
    
    // This loop goes through the keys and prints out a numerical representation of the experience
    for(Map.Entry<Boolean, Integer> entry : experienceReport.entrySet()){
      if(entry.getKey() == true){
        System.out.printf("Number of experienced players: %d %n", entry.getValue());
      }
      else {
        System.out.printf("Number of inexperienced players: %d %n", entry.getValue());
      } // End if
    } // End for loop
  }  // End getExperienceReport
        
  // Function to allow teams to be sorted by team name
  @Override
  public int compareTo(Team tm){  
   return mTeamName.compareTo(tm.getTeamName());
  }  // End compareTo
  
} // End Class Team