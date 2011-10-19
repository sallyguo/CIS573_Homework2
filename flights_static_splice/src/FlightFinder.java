import java.util.ArrayList;


public class FlightFinder {

	private ArrayList<Flight> _directFlights = new ArrayList<Flight>();
	private ArrayList<Flight[]> _indirectFlights = new ArrayList<Flight[]>();
	
	public ArrayList<Flight> directFlights() { return _directFlights; }
	public ArrayList<Flight[]> indirectFlights() { return _indirectFlights; }
	
	/**
	 * Look through the (hard-coded) list of flights and return the number
	 * of flights from the home airport to the destination. If direct is true,
	 * then only consider direct flights; if it is false, count flights that consist
	 * of two segments, too. However, in those cases, only report flights in which
	 * the total time is less than or equal to the specified timeLimit. 
	 */
	public int numFlights(String home, String dest, boolean direct, int timeLimit)
	{
		// keep track of the number of flights
		int count = 0;
		
		// the array of all flights
		Flight[] allFlights = Flight.allFlights();
		
		// first, find direct flights
		for (int i = 0; i < allFlights.length; i++) {
			Flight f = allFlights[i];
			if (f.start().equals(home) && f.end().equals(dest)) {
				count++;
			}
		}
	
		// then, find indirect flights (max two segments)
		if (!direct) {
			for (int i = 0; i < allFlights.length; i++) {
				if (allFlights[i].start().equals(home)) {
					for (int j = 0; j < allFlights.length; j++) {
						if (allFlights[i].end().equals(allFlights[j].start()) && allFlights[i].end().equals(dest) && allFlights[i].time()+allFlights[j].time() < timeLimit) {
							count++;
						}
					}
				}
			}
		}
		
		return count;
		
	}
}
