import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

// Class for managing the soccer league
public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    
    // Your code here!
    // Creating and intitializing variables and collections 
    Scanner scanner = new Scanner(System.in);
    String choice = "go";
    String stopLoop;
    int teamChoice;
    int playerChoice;
    int maxTeams = players.length / 11; 
    List<Team> teams = new ArrayList<Team>();
    List<Player> kids = new ArrayList<Player>();    
    
    // I'm moving the players into an arraylist becaue it is easier to use than an array
    for(Player kid : players){
      kids.add(kid);
    }
    Collections.sort(kids);
       
    // Putting the thrust of the program in a do-while loop because we want to run it at least once
    do {      
      // Returns the users menu choice here so we can switch on it
      choice = menuDisplay();
      
      // Using a switch to handle the program. Cleaner than if blocks and easier to read.
      // I know most of the choices are numbers but using toLowerCase for the quit part at the end
      switch(choice.toLowerCase()){
        // Case 1 is the add team option from the menu function. More on that there.
        case "1":
          /* Setting the loop condition variable here in case people move from option to option.
           * I don't want to use multiple loop condition variables. I also don't want it to be
           * stuck on the exit condition either */
          stopLoop = "go";
          // I'm using a do-while loop in case the administrator wants to create multiple teams at once
          do {
            // A check to see if there is room to create teams. If not, not more teams can be made.
            if(teams.size() < maxTeams) {
              System.out.println("What is the name of the new team? ");
              String teamName = scanner.nextLine();
              // This loop checks for team name uniqueness. I know I could be using a set here but
              // I am already using a list and this way works too.
              for(Team squad : teams){
                  if(squad.getTeamName().equalsIgnoreCase(teamName)){
                      System.out.println("I'm sorry but that team name already exits.");
                      System.out.println("Please choose another team name;");
                      break;
                  } // End If
              } // End for loop
              System.out.println("Who is the coach of the new team? ");
              String coachName = scanner.nextLine();
              Team team = new Team(teamName, coachName);
              teams.add(team);
              System.out.println("Do you want to add another team? Please enter 1 for yes or 2 for no.");
              stopLoop = scanner.nextLine();
            }
            else {
              System.out.println("I'm sorry but the league has the maximum amount of teams already.");
              break;
            }   
          } while(!stopLoop.equals("2")); // End do-while loop
        
          break; // End case "1" - Add Team Option
        // Case 2 is the add player option from the menu function. 
        case "2":
            // Resetting the loop condition variable for the same reasons as above.
            stopLoop = "go";
            // Using a do-while loop here in case the administrator wants to add more than on player at a time
            do {               
                // This is a check to see if there are any more kids to add to a team. If not, moving on.
                if(kids.size() == 0){
                  System.out.println("All players assigned to teams. Returing to main menu \n");
                  break;
                }
                // This function displays the remaining players available.
                showPlayers(kids);
                System.out.println("Please choose player to add to the team using the number next to their name: ");
                playerChoice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Add player to which team?");
                // This functions shows the created teams in alphabetical order.
                showTeams(teams);
                teamChoice = scanner.nextInt();
                scanner.nextLine();
                // Another check to make sure there are kids to add to teams
                if(kids.size() > 0){
                    // There function teams.addPlayer has a check in it against maximum team size.
                    // If there are the maximum amount of players we can skip this.
                    if(teams.get(teamChoice - 1).addPlayer(kids.get(playerChoice - 1))){
                      // Removing the player from the available pool so we don't accidentally put the same kid on multiple teams
                      kids.remove(playerChoice - 1);
                      System.out.println("Do you want to add another player? Please enter 1 for yes or 2 for no.");
                      stopLoop = scanner.nextLine();
                    }  
                    else{
                      // if there are the maximum amount of players on a team we can skip back to the menu.
                      break;
                    } // End Inner If
                }
                else {
                  // If all available players have been added, this message shows and we go back to the menu.
                  System.out.println("All of the players have been assigned to teams.");
                  break;
                } // End Outer If
             
            } while(!stopLoop.equals("2")); // End do-while loop
        
        break; // End case "2" - Add Player Option
        // Case 3 is the remove player option from the menu function. 
        case "3":
            // Resetting the loop condition variable for the same reasons as above.
            stopLoop = "go";
            // Using a do-while loop here in case the administrator wants to remove more than on player at a time
            do {              
                System.out.println("Which team would you like to remove a player from?");
                // This function displays the team in alphabetical order
                showTeams(teams);
                teamChoice = scanner.nextInt();
                scanner.nextLine();
                // This is a check to see if the team has any more players. If not, back to the menu.
                if(teams.get(teamChoice -1).getTeamSize() == 0){
                  System.out.println("No players remaining on the team");
                  break;
                } // End IF
                // Team.showRoster() shows the players assigned to the team
                teams.get(teamChoice - 1).showRoster();
                System.out.println("Choose which player to remove by the number next to their name: ");
                playerChoice = scanner.nextInt();
                scanner.nextLine();
                /* This if checks to see if there are any players left of the team.
                 * If there are, we can remove the chosen player
                 * If not, back to the menu */
                if(teams.get(teamChoice - 1).getTeamSize() > 0){
                  // Putting the player back into the available pool.
                  kids.add(teams.get(teamChoice - 1).getPlayer(playerChoice - 1));
                  // Removing the player from the team
                  teams.get(teamChoice - 1).removePlayer(playerChoice - 1);                
                  System.out.println("Do you want to remove another player? Please enter 1 for yes or 2 for no.");
                  stopLoop = scanner.nextLine();
                }
                else {
                  // Back to the menu because we must have run out of players to remove
                  break;
                } // End If
                
            } while(!stopLoop.equals("2")); // End do-while
        
        break; // End case "3" - Remove Player Option
        // Case 4 is the height report option from the menu function. 
        case "4":
            // This function displays the team in alphabetical order
            stopLoop = "go";
            // Using a do-while loop in case the administrator wants to see multiple height reports 
            do {
                System.out.println("Whose team would you like to see the height report for?");
                // This function displays the team in alphabetical order
                showTeams(teams);
                teamChoice = scanner.nextInt();
                scanner.nextLine();
                // Team.getHeightReport() shows the heights of the team and the players that are that tall
                teams.get(teamChoice - 1).getHeightReport();       
                System.out.println("Do you want to see another teams height report? Please enter 1 for yes or 2 for no.");
                stopLoop = scanner.nextLine();
              
            } while(!stopLoop.equals("2"));  // End do-while
            break; // End case "4" - Height Report Option
            // Case 5 is the League Experience Report option from the menu function.
            case "5":
            System.out.println("League Experience Report: ");
            for(Team squad : teams){
              // Team.getExperienceReport() shows % of experience and numerical breakdown of experience
              squad.getExperienceReport();
			  // Team.getHeightReport() shows how many players are at each height and their names
              squad.getHeightReport();
            } // End for loop
            System.out.println("Press enter to return to the menu");
            stopLoop = scanner.nextLine();
        break; // End case "5" - League Experience Report Option
        // Case 6 is the Print Roster option from the menu function.
        case "6":
            // Setting the loop conditional variable for the same reasons as above
            stopLoop = "go";
            // Using a do-while loop here in case the administrator wants to see multiple rosters 
            do {
                System.out.println("Which team's roster would you like to see?");
                // This function shows the teams in alphabetical order
                showTeams(teams);
                teamChoice = scanner.nextInt();
                scanner.nextLine();
                // Team.showRoster() shows the players assigned to the team
                teams.get(teamChoice - 1).showRoster();
                System.out.println("Do you want to see another teams roster? Please enter 1 for yes or 2 for no.");
                stopLoop = scanner.nextLine();
            } while(!stopLoop.equals("2")); // End do-while
        break; // End case "6" - The Print Roster Option
      }  
    } while(!choice.equalsIgnoreCase("quit") ); // End outer do-while loop
    
    System.out.println("Thank you for using the Treehouse Soccer League Manager");
  } // End Class Main
  
  // This function shows the available player pool
  public static void showPlayers(List<Player> kids){
    Collections.sort(kids);
    int kidCount = 1;
    for(Player player : kids){
      System.out.printf(" %-2d Player name: %-10s  %-11s height: %-2d inches experience: %-10s %n", kidCount++,
                        player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
    } // End for loop
  }  // End showPlayers
  
  // Menu function that allows user to choice options from user stories
  public static String menuDisplay(){
    Scanner scanner = new Scanner(System.in);
    String menuOption;
    // I used numbers in the menu to prevent typos and case issues
    System.out.println("To create a new team             please type 1");
    System.out.println("To add a player to a team        please type 2");
    System.out.println("To remove a player from a team   please type 3");
    System.out.println("To see a team's height report    please type 4");
    System.out.println("To see the League Balance Report please type 5");
    System.out.println("To see a team's complete roster  please type 6");
    System.out.println("To exit this program             please type quit");
    menuOption = scanner.nextLine();
    return menuOption;
  } // End menuDisplay
  
  // Function that shows created teams in alphabetical order
  public static void showTeams(List<Team> teams){
    Collections.sort(teams);
    int teamNum = 1;
    for(Team squads : teams){
      System.out.printf("%d %s%n",teamNum++,squads.getTeamName());
    } // End for loop
  } // End showTeams
}  // End Class LeagueManager - End Program

