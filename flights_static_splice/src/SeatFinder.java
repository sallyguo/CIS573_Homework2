import java.util.ArrayList;


public class SeatFinder {

	// track the seats that are available (as Strings representing row and seat number)
	private ArrayList<String> seats = new ArrayList<String>();
	// track the number of window, aisle, and middle seats that are available
	private int windowSeats, aisleSeats, middleSeats;
	
	/**
	 * Determines the number of seats that are available on the flight according
	 * to the search criteria. The current state of the flight is indicated in the
	 * byte array, in which each byte is a bit vector representing that row; within
	 * the bit vector, a 0 means empty, and a 1 means occupied.
	 * Since Java uses signed bytes, we only have 7 bits to play with; so the 
	 * most significant bit (which we'll call bit #0) will not be used.
	 * 
	 *  The booleans indicate that the user wants to consider specific seats.
	 *  Window seats are represented by bits 1 and 7 in the bit vector for the row.
	 *  Aisle seats are represented by bits 2, 3, 5, and 6.
	 *  Middle seats are represented by bit 4.
	 *  
	 * The maxRow is the maximum row that the user wants to consider (counting from 1).
	 */
	public int numSeats(byte[] state, boolean windowOk, boolean aisleOk, boolean middleOk, int maxRow) {
		// make sure that we don't have an invalid entry for maxRow
		if (state == null || maxRow > state.length) return 0;
		
		aisleSeats = 0;
		middleSeats = 0;
		windowSeats = 0;
		
		// loop through the rows, up until (but not including, since we'll start
		// counting at 1) the specified maximum row
		for (int i = 1; i <= maxRow; i++) {
			byte row = state[i-1]; // represents the available seats as a bit vector

			// if they don't want aisle seats, then just consider them occupied
			if (!aisleOk) {
				row = (byte)(row | 0x36); // since 0x36 = 0011 0110 in binary
			}
			// same for window
			if (!windowOk) {
				row = (byte)(row | 0x61);  
			}
			// same for middle seats
			if (!middleOk) {
				row = (byte)(row | 0x08); 
			}

			// now we just need to count the number of 0s to see how many seats are available
			for (int j = 1; j < 8; j++) {
				if (row % 2 == 0) {
					// update the counter for the different types of seats
					if (j == 1 || j == 7) aisleSeats++;
					else if (j == 4) middleSeats++;
					else windowSeats++;
				}
				row = (byte) (row / 2); // get rid of the last digit
			}
			
		}

		// count up the overall number of available seats
		int count = aisleSeats + middleSeats + windowSeats;
		return count;
	}

}
