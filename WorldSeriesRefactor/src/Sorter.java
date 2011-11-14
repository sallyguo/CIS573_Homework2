import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;


public class Sorter {
	
	/*
	 * Show all of the World Series results, sorted by the winning team
	 */
	public String winners() {
		DataStore ds = new DataStore(UserInterface.DATAFILE);
		
		// to hold the return value
		StringBuffer result = new StringBuffer();
		
		ArrayList<WorldSeriesInstance> instancesList = ds.getAllInstances();
		
		//Sorted map to keep teams and their win years
		TreeMap<String, ArrayList<Integer>> teamYearHash = new TreeMap<String, ArrayList<Integer>>();
		
		for (WorldSeriesInstance wsi : instancesList) {
			// see if the winner is already in the list of teams
			if (!teamYearHash.containsKey(wsi.winner())) {
				// add it to the list of teams and create an entry in the wins list
				ArrayList<Integer> newEntry = new ArrayList<Integer>();
				newEntry.add(wsi.year());
				teamYearHash.put(wsi.winner(), newEntry);
			}
			else {
				// so just update the corresponding entry in wins
				teamYearHash.get(wsi.winner()).add(wsi.year());
			}
		}
		
		
		//Generates the result string by iterating through the sorted map
		for(String team : teamYearHash.keySet()){
			result.append(team + ": ");
			ArrayList<Integer> winYears = teamYearHash.get(team);
			Collections.sort(winYears);
			for(Integer year : winYears){
				result.append(year + ", ");
			}
			result.delete(result.length()-2, result.length());
			result.append("\n");
		}
		
		return result.toString();
		
	}


}