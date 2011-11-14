import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Sorter {
	
	/**
	 * Show all of the World Series results, sorted by the winning team
	 */
	public String winners() {
		DataStore ds = new DataStore(UserInterface.DATAFILE);
		
		// StringBuffer to hold the return value
		StringBuffer result = new StringBuffer();
		
		//Sorted map to map Teams to their Win Years
		TreeMap<String, ArrayList<Integer>> teamYearHash = new TreeMap<String, ArrayList<Integer>>();
		
		//Fills map with entries from the DataStore
		for (WorldSeriesInstance wsi : ds.getAllInstances()) {
			if (!teamYearHash.containsKey(wsi.winner())) {
				ArrayList<Integer> newEntry = new ArrayList<Integer>();
				newEntry.add(wsi.year());
				teamYearHash.put(wsi.winner(), newEntry);
			}
			else {
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