import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataStore {
	
	private final String _fileName;
	private boolean _initialized = false;
	private ArrayList<WorldSeriesInstance> _list;
	
	int year;
	String winner, loser, score;
	
	public DataStore(String fileName) {
		_fileName = fileName;
		_list = new ArrayList<WorldSeriesInstance>();
		readDataFile();
	}
	
	public ArrayList<WorldSeriesInstance> allInstances() { return _list; }
	
	protected void readDataFile() {
		
		try {
			Scanner in = new Scanner(new File(_fileName));
			// since it's a comma-separated file
			in.useDelimiter(",");
			
			// read each line of the file one at a time
			while (in.hasNext()) {
				year = in.nextInt();
				winner = in.next();
				score = in.next();
				loser = in.nextLine();
				// the loser still has the leading comma attached, so get rid of it
				loser = loser.substring(1, loser.length());
				//System.out.println(year + ": " + winner + " beat " + loser + " by " + score);
				
				// create a WorldSeriesInstance
				WorldSeriesInstance wsi = new WorldSeriesInstance(year, winner, loser, score);
				
				// add it to the ArrayList
				_list.add(wsi);
			}
			
			// if we made it here, we successfully initialized
			_initialized = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
}
