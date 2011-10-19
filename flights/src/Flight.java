
public class Flight {
	
	// starting point for the flight
	private String _start;
	// end point for the flight
	private String _end;
	// travel time for the flight
	private int _time;
	
	public Flight(String start, String end, int time) {
		_start = start;
		_end = end;
		_time = time;
	}
	
	public String start() { return _start; }
	public String end() { return _end; }
	public int time() { return _time; }
	
	
	public static Flight[] allFlights(){
		
		Flight[] flights = new Flight[20];
		
		flights[0] = new Flight("PHL", "BOS", 95);
		flights[1] = new Flight("BOS", "PHL", 95);
		flights[2] = new Flight("PHL", "IAD", 60);
		flights[3] = new Flight("IAD", "PHL", 60);
		flights[4] = new Flight("PHL", "ORD", 100);
		flights[5] = new Flight("ORD", "PHL", 100);
		flights[6] = new Flight("PHL", "ATL", 120);
		flights[7] = new Flight("ATL", "PHL", 120);
		flights[8] = new Flight("PHL", "DFW", 175);
		flights[9] = new Flight("DFW", "PHL", 175);
		flights[10] = new Flight("DFW", "SFO", 150);
		flights[11] = new Flight("SFO", "DFW", 150);
		flights[12] = new Flight("DET", "SFO", 180);
		flights[13] = new Flight("SFO", "DET", 180);
		flights[14] = new Flight("ORD", "SFO", 190);
		flights[15] = new Flight("SFO", "ORD", 190);
		flights[16] = new Flight("ORD", "SEA", 225);
		flights[17] = new Flight("SEA", "ORD", 225);
		flights[18] = new Flight("ATL", "BOS", 140);
		flights[19] = new Flight("BOS", "ATL", 140);

		return flights;
		
	}


}
