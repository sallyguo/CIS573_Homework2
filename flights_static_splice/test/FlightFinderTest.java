import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class FlightFinderTest {

	FlightFinderPass finderPass;
	FlightFinderFail finderFail;
	
	@Before
	public void setUp() throws Exception {
		finderPass = new FlightFinderPass();
		finderFail = new FlightFinderFail();
	}

	@Test
	public void testPHLtoBOSdirect() {
		// should only be one direct flight
		assertEquals(1, finderPass.numFlights("PHL", "BOS", true, 200));
	}

	@Test
	public void testPHLtoBOSindirect200() {
		// should just be the one direct flight, no indirect under 200
		assertEquals(1, finderFail.numFlights("PHL", "BOS", false, 200));
	}

	@Test
	public void testPHLtoSFOdirect() {
		// no direct flights from PHL to SFO
		assertEquals(0, finderPass.numFlights("PHL", "SFO", true, 200));
	}

	@Test
	public void testPHLtoSFOindirect350() {
		// two indirect routes: PHL-ORD-SFO and PHL-DFW-SFO
		assertEquals(2, finderFail.numFlights("PHL", "SFO", false, 350));
	}

	@Test
	public void testPHLtoSFOindirect300() {
		// only one indirect route under 300: PHL-ORD-SFO
		assertEquals(1, finderFail.numFlights("PHL", "SFO", false, 300));
	}

	@Test
	public void testPHLtoATLindirect240() {
		// one direct and one indirect: PHL-BOS-ATL
		assertEquals(2, finderFail.numFlights("PHL", "ATL", false, 240));
	}
}
