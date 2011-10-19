import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class SeatFinderTest {

	private SeatFinder finder;
	
	@Before
	public void setUp() throws Exception {
		finder = new SeatFinder();
	}

	@Test
	public void test0() {
		byte row1 = (byte)0x00; // all seats available
		byte[] state = { row1 };
		// wants anything, should be 7 seats
		assertEquals(7, finder.numSeats(state, true, true, true, 1));
	}

	@Test
	public void test1() {
		byte row1 = (byte)0x00; // all seats available
		byte[] state = { row1 };
		// wants window only, should be 2 seats
		assertEquals(2, finder.numSeats(state, true, false, false, 1));
	}
	
	@Test
	public void test2() {
		byte row1 = (byte)0x00; // all seats available
		byte[] state = { row1 };
		// wants aisle only, should be 4 seats
		assertEquals(4, finder.numSeats(state, false, true, false, 1));
	}
	
	@Test
	public void test3() {
		byte row1 = (byte)0x00; // all seats available
		byte[] state = { row1 };
		// wants window or aisle, should be 6 seats
		assertEquals(6, finder.numSeats(state, true, true, false, 1));
	}

	@Test
	public void test4() {
		byte row1 = (byte)0x7F; // no seats available 
		// (0x7F = 0111 1111; remember, we're not using the most significant bit!)
		byte[] state = { row1 };
		// wants window or aisle, but there are no seats
		assertEquals(0, finder.numSeats(state, true, true, false, 1));
	}

	@Test
	public void test5() {
		byte row1 = (byte)0x01; // all seats available except window #7
		byte[] state = { row1 };
		// only wants window, so should only be one seat
		assertEquals(1, finder.numSeats(state, true, false, false, 1));
	}

	@Test
	public void test6() {
		byte row1 = (byte)0x02; // all seats available except aisle #6
		byte[] state = { row1 };
		// only wants window, so should two seats
		assertEquals(2, finder.numSeats(state, true, false, false, 1));
	}

	@Test
	public void test7() {
		byte row1 = (byte)0x3E; // only window available (0x3E = 0011 1110)
		byte[] state = { row1 };
		// wants window or aisle, should be 2 seats
		assertEquals(2, finder.numSeats(state, true, true, false, 1));
	}

	@Test
	public void test8() {
		byte row1 = (byte)0x3E; // only window available (0x3E = 0011 1110)
		byte[] state = { row1 };
		// wants only aisle, should be 0 seats
		assertEquals(0, finder.numSeats(state, false, true, false, 1));
	}
	
	@Test
	public void test9() {
		byte row1 = (byte)0x49; // only aisle available 
		byte[] state = { row1 };
		// wants only aisle, should be 0 seats
		assertEquals(4, finder.numSeats(state, false, true, false, 1));
	}

}
