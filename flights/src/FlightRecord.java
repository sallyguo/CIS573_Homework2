
public class FlightRecord {
	
	private boolean _windy;
	private boolean _rainy;
	private boolean _cloudy;
	private int _delay;
	
	public FlightRecord(boolean windy, boolean rainy, boolean cloudy, int delay) {
		_windy = windy;
		_rainy = rainy;
		_cloudy = cloudy;
		_delay = delay;
	}
	
	public boolean windy() { return _windy; }
	public boolean rainy() { return _rainy; }
	public boolean cloudy() { return _cloudy; }
	public int delay() { return _delay; }
	
}
