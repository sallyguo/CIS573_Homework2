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
			System.out.println("");
			System.out.println("Please make your selection:");
			System.out.println("1: Search for World Series info by year");
			System.out.println("2: Search for World Series info by team");
			System.out.println("3: Show all World Series for a range of years");
			System.out.println("4: Show all teams' World Series wins");
			System.out.println("Q: Quit");
			System.out.print("> ");
			
			Scanner in = new Scanner(System.in);
			
			choice = in.nextLine();
			
			try {
				int choiceNum = Integer.parseInt(choice);

				switch (choiceNum) {
				case 1:
					// if they want to search by year
					System.out.print("Please enter the year: ");
					int year = in.nextInt();
					String yearData = showDataForYear(year);
					System.out.println(yearData);
					break;
				case 2:
					// search by team
					System.out.print("Please enter the team name: ");
					String team = in.nextLine();
					System.out.print("Do you want to see World Series the team has (W)on, (L)ost, or (A)ll? ");
					String which = in.next();
					String teamData = showDataForTeam(team, which);
					System.out.println(teamData);
					break;
				case 3:
					// for a range of years
					int startYear, endYear;
					System.out.print("Please enter the starting year: ");
					startYear = in.nextInt();
					System.out.print("Please enter the ending year: ");
					endYear = in.nextInt();
					yearData = showDataForRange(startYear, endYear);
					System.out.println(yearData);
					break;
				case 4:
					// show all the teams and the years they won a World Series
					String result = new Sorter().winners();
					System.out.println(result);
					break;
				default:
					// they entered a number but not a valid one
					System.out.println("That is not a valid selection.");
				}
			} catch (NumberFormatException e) {
				// they didn't enter a number
				if (!choice.equals("q") && !choice.equals("Q")) {
					System.out.println("That is not a valid selection.");
				}
			} catch (Exception e) {
				System.out.println("That is not a valid year.");
			}

		}
		while (!choice.equals("Q") && !choice.equals("q"));
		System.out.println("Good bye");
	}

	
	protected static String showDataForYear(int year) {
		Filter filter = new Filter().setYear(year);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		if (filteredList.isEmpty()) return "No World Series was held in " + year;
		
		return generateEntryString(filteredList).trim();
	}

	protected static String showDataForRange(int start, int end) {
		if (end < start) return "Invalid year range";
		
		Filter filter = new Filter().setStartYear(start).setEndYear(end);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		if (filteredList.isEmpty()) return "No World Series held between " + start + " and " + end;
		
		return generateEntryString(filteredList);
	}
	
	private static String generateEntryString(ArrayList<WorldSeriesInstance> filtered, String ... team){
	    StringBuffer result = new StringBuffer();
        for (WorldSeriesInstance wsi : filtered) {
            if ((team.length == 0) || wsi.winner().toUpperCase().contains(team[0].toUpperCase()))
                result.append("In " + wsi.year() + " the " + wsi.winner() + " defeated the " + wsi.loser() + " by " + wsi.score() + "\n");
            else
                result.append("In " + wsi.year() + " the " + wsi.loser() + " lost to the " + wsi.winner() + " by " + wsi.score() + "\n");
        }
        return result.toString();
	}
	
	private static String generateSummaryString(String condition, Integer ...score){
        return condition;
	}

	protected static String showDataForTeam(String team, String choice) {
        Filter filter = new Filter().setTeam(team).setCondition(choice);
        ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
        
        // to hold the result
        StringBuffer result = new StringBuffer();
        result.append(generateEntryString(filteredList, team));
        
        if (choice.equalsIgnoreCase("W")) {
            if (filteredList.isEmpty())
                result.append("The " + team + " have not won any World Series\n");
            else 
                result.append("The " + team + " have won " + filteredList.size() + " World Series\n");
        }
        else if (choice.equalsIgnoreCase("L")) {
            if (filteredList.isEmpty())
                result.append("The " + team + " have not lost any World Series\n");
            else
                result.append("The " + team + " have lost " + filteredList.size() + " World Series\n");
        }
        else if (choice.equalsIgnoreCase("A")) {
            Filter winFilter = new Filter().setTeam(team).setCondition("W");
            int winCount = winFilter.filter(instanceList).size();
            int lossCount = filteredList.size() - winCount;
            // if none found, print a message
            if (filteredList.isEmpty())
                result.append("The " + team + " have not played in any World Series\n");
            else
                result.append("The " + team + " have won " + winCount + " World Series and lost " + lossCount + "\n");
        }
        else {
            result.append("\"" + choice + "\" is not a valid entry.");
        }
        
        return result.toString();
	}
	
}
