import java.util.ArrayList;
import java.util.Scanner;


public class UserInterface {

	public static final String DATAFILE = "WorldSeries.csv";
	private static final DataStore ds = new DataStore(DATAFILE);
	public static final ArrayList<WorldSeriesInstance> instanceList = ds.getAllInstances();
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to the World Series database!");

		String choice = null; // the thing that the user chooses to do
		
		while (!"q".equalsIgnoreCase(choice)){
			printIntroduction();
			
			Scanner in = new Scanner(System.in);
			choice = in.nextLine();
			
			try {
				int choiceNum = Integer.parseInt(choice);

				switch (choiceNum) {
				case 1:
					searchByYear(in);
					break;
				case 2:
					searchByTeam(in);
					break;
				case 3:
					searchByRange(in);
					break;
				case 4:
					showAll();
					break;
				default:
					System.out.println("That is not a valid selection.");
				}
			} catch (NumberFormatException e) {
				if (!choice.equalsIgnoreCase("q")) {
					System.out.println("That is not a valid selection.");
				}
			} catch (Exception e) {
				System.out.println("That is not a valid year.");
			}
		}
		
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
		
		return generateEntryString(filteredList).trim();
	}

	protected static String showDataForRange(int start, int end) {
		if (end < start) return "Invalid year range";
		
		Filter filter = new Filter().setStartYear(start).setEndYear(end);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		if (filteredList.isEmpty()) return "No World Series held between " + start + " and " + end;
		
		return generateEntryString(filteredList);
	}

	protected static String showDataForTeam(String team, String choice) {
		Filter filter = new Filter().setTeam(team).setCondition(choice);
		ArrayList<WorldSeriesInstance> filteredList = filter.filter(instanceList);
		
		StringBuffer result = new StringBuffer();
		
		Integer [] score = {filteredList.size(), 0};
		
		if (choice.equalsIgnoreCase("A")) {
			Filter winCountFilter = new Filter().setTeam(team).setCondition("W");
			score[0] = winCountFilter.filter(instanceList).size();
			score[1] = filteredList.size() - score[0];
		}
		
		result.append(generateEntryString(filteredList, team));
		result.append(generateSummaryString(choice, team, score));
		
		return result.toString();
	}
	
	/**
	 * Takes in a filtered ArrayList of WorldSeriesInstance and generates the string entry for each instance
	 * @param filtered - The filtered arraylist of WorldSeriesInstance
	 * @param team - The team we're interested in (only required for "A" choice in ShowDataForTeam)
	 * @return The string output for all the entries in the filtered list
	 */
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
	
	/**
	 * Generates the summary string for the "A" choice in ShowDataForTeam
	 * @param condition - The choice from ShowDataForTeam
	 * @param team - The team we're interested in
	 * @param score - The final score for the team we're interested in. If we only care for
	 * wins or losses, we only take in the first entry. If we want both, we need two elements in score.
	 * @return The summary string output for ShowDataForTeam
	 */
	private static String generateSummaryString(String condition, String team, Integer ...score){
		if ("W".equalsIgnoreCase(condition)){
			if (score[0] == 0)
				return "The " + team + " have not won any World Series\n";
			else
				return "The " + team + " have won " + score[0] + " World Series\n";
		}
		if ("L".equalsIgnoreCase(condition)){
			if (score[0] == 0)
				return "The " + team + " have not lost any World Series\n";
			else
				return "The " + team + " have lost " + score[0] + " World Series\n";
		}
		if ("A".equalsIgnoreCase(condition)){
			if (score[0] + score[1] == 0)
				return "The " + team + " have not played in any World Series\n";
			else
				return "The " + team + " have won " + score[0] + " World Series and lost " + score[1] + "\n";
		}
		return "\"" + condition + "\" is not a valid entry.";
	}
}
