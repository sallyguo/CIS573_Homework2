import java.util.ArrayList;
import java.util.Scanner;


public class UserInterface {

	public static final String DATAFILE = "WorldSeries.csv";
	private static final DataStore ds = new DataStore(DATAFILE);
	public static final ArrayList<WorldSeriesInstance> instanceList = ds.getAllInstances();
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to the World Series database!");

		String choice = null; // the thing that the user chooses to do
		
		do {
			printIntroduction();
			
			Scanner in = new Scanner(System.in);
			
			choice = in.nextLine();
			
			try {
				int choiceNum = Integer.parseInt(choice);

				switch (choiceNum) {
				case 1:
					// if they want to search by year
					searchByYear(in);
					break;
				case 2:
					// search by team
					searchByTeam(in);
					break;
				case 3:
					// for a range of years
					searchByRange(in);
					break;
				case 4:
					// show all the teams and the years they won a World Series
					showAll();
					break;
				default:
					// they entered a number but not a valid one
					System.out.println("That is not a valid selection.");
				}
			} catch (NumberFormatException e) {
				// they didn't enter a number
				if (!choice.equalsIgnoreCase("q")) {
					System.out.println("That is not a valid selection.");
				}
			} catch (Exception e) {
				System.out.println("That is not a valid year.");
			}

		}
		while (!choice.equalsIgnoreCase("q"));
		System.out.println("Good bye");
	}
	
	private static void printIntroduction() {
		System.out.println("");
		System.out.println("Please make your selection:");
		System.out.println("1: Search for World Series info by year");
		System.out.println("2: Search for World Series info by team");
		System.out.println("3: Show all World Series for a range of years");
		System.out.println("4: Show all teams' World Series wins");
		System.out.println("Q: Quit");
		System.out.print("> ");
	}

	private static void searchByYear(Scanner in) {
		System.out.print("Please enter the year: ");
		int year = in.nextInt();
		String yearData = showDataForYear(year);
		System.out.println(yearData);
	}
	
	private static void searchByTeam(Scanner in) {
		System.out.print("Please enter the team name: ");
		String team = in.nextLine();
		System.out.print("Do you want to see World Series the team has (W)on, (L)ost, or (A)ll? ");
		String which = in.next();
		String teamData = showDataForTeam(team, which);
		System.out.println(teamData);
	}
	
	private static void searchByRange(Scanner in) {
		int startYear, endYear;
		System.out.print("Please enter the starting year: ");
		startYear = in.nextInt();
		System.out.print("Please enter the ending year: ");
		endYear = in.nextInt();
		String yearData = showDataForRange(startYear, endYear);
		System.out.println(yearData);
	}
	
	private static void showAll() {
		String result = new Sorter().winners();
		System.out.println(result);
	}
	
	protected static String showDataForYear(int year) {
		Filter filter = new Filter().setYear(year);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		if (filteredList.isEmpty()) return "No World Series was held in " + year;
		
		return generateString(filteredList).trim();
	}

	protected static String showDataForRange(int start, int end) {
		// make sure we have valid data
		if (end < start) return "Invalid year range";
		
		Filter filter = new Filter().setStartYear(start).setEndYear(end);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		if (filteredList.isEmpty()) return "No World Series held between " + start + " and " + end;
		
		return generateString(filteredList);
	}
	
	private static String generateString(ArrayList<WorldSeriesInstance> filtered, String ... team){
	    StringBuffer result = new StringBuffer();
        for (WorldSeriesInstance wsi : filtered) {
            if ((team.length == 0) || wsi.winner().equalsIgnoreCase(team[0]))
                result.append("In " + wsi.year() + " the " + wsi.winner() + " defeated the " + wsi.loser() + " by " + wsi.score() + "\n");
            else
                result.append("In " + wsi.year() + " the " + wsi.winner() + " lost to the " + wsi.loser() + " by " + wsi.score() + "\n");
        }
        return result.toString();
	}

	protected static String showDataForTeam(String team, String choice) {
	    // load the data
        DataStore ds = new DataStore(DATAFILE);
        Filter filter = new Filter().setTeam(team).setCondition(choice);
        ArrayList<WorldSeriesInstance> filteredList = filter.filter(ds.getAllInstances());
        
        // to hold the result
        StringBuffer result = new StringBuffer();
        
        if (choice.equalsIgnoreCase("W")) {
            // keep track of the number of wins for the team
            int m = 0;
            
            // look through all the instances
            ArrayList<WorldSeriesInstance> list = ds.getAllInstances();
            for (WorldSeriesInstance wsi : list) {
                // convert to uppercase and use "contains" for partial matching
                if (wsi.winner().toUpperCase().contains(team.toUpperCase())) {
                    // we found an instance when the team won
                    result.append("In " + wsi.year() + " the " + wsi.winner() + " defeated the " + wsi.loser() + " by " + wsi.score());
                    result.append("\n");
                    m++;
                }
            }
            // if none found, print a message
            if (m == 0) {
                result.append("The " + team + " have not won any World Series");
                result.append("\n");
            }
            else {
                result.append("The " + team + " have won " + m + " World Series");
                result.append("\n");
            }
            
        }
        else if (choice.equalsIgnoreCase("L")) {
            // keep track of the number of losses for the team
            int m = 0;
            
            // look through all the instances
            ArrayList<WorldSeriesInstance> list = ds.getAllInstances();
            for (WorldSeriesInstance wsi : list) {
                // convert to uppercase and use "contains" for partial matching
                if (wsi.loser().toUpperCase().contains(team.toUpperCase())) {
                    result.append("In " + wsi.year() + " the " + wsi.loser() + " lost to the " + wsi.winner() + " by " + wsi.score());
                    result.append("\n");
                    m++;
                }
            }
            // if none found, print a message
            if (m == 0) {
                result.append("The " + team + " have not lost any World Series");
                result.append("\n");
            }
            else {
                result.append("The " + team + " have lost " + m + " World Series");
                result.append("\n");
            }
            
        }
        else if (choice.equalsIgnoreCase("A")) {
            
            // keep track of the number of wins and losses for the team
            int a = 0, b = 0;
            
            // look through all the instances
            ArrayList<WorldSeriesInstance> list = ds.getAllInstances();
            for (WorldSeriesInstance wsi : list) {
                // convert to uppercase and use "contains" for partial matching
                if (wsi.winner().toUpperCase().contains(team.toUpperCase())) {
                    // we found an instance when the team won
                    result.append("In " + wsi.year() + " the " + wsi.winner() + " defeated the " + wsi.loser() + " by " + wsi.score());
                    result.append("\n");
                    a++;
                }
                else if (wsi.loser().toUpperCase().contains(team.toUpperCase())) {
                    result.append("In " + wsi.year() + " the " + wsi.loser() + " lost to the " + wsi.winner() + " by " + wsi.score());
                    result.append("\n");
                    b++;
                }
            }
            // if none found, print a message
            if (a + b == 0) {
                result.append("The " + team + " have not played in any World Series");
                result.append("\n");
            }
            else {
                result.append("The " + team + " have won " + a + " World Series and lost " + b);
                result.append("\n");
            }
            
            
        }
        else {
            result.append("\"" + choice + "\" is not a valid entry.");
        }
        
        return result.toString();
		
	}
	
}
