import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DelayPredictorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test0() {

		// in this test, we have a perfect match for all three and no others
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);
		FlightRecord flight1 = new FlightRecord(true, true, true, 30);
		FlightRecord flight2 = new FlightRecord(true, true, true, 80);

		FlightRecord[] records = { flight0, flight1, flight2 };
		
		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorPass.predict(records, true, true, true));
	}

	@Test
	public void test1() {

		// in this test, we have a perfect match for all three
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);
		FlightRecord flight1 = new FlightRecord(true, true, true, 30);
		FlightRecord flight2 = new FlightRecord(true, true, true, 80);
		
		// and then some others that should not be considered as close
		FlightRecord flight3 = new FlightRecord(false, true, true, 1);
		FlightRecord flight4 = new FlightRecord(true, false, true, 3);
		FlightRecord flight5 = new FlightRecord(true, true, false, 8);
		
		FlightRecord[] records = { flight0, flight1, flight2, flight3, flight4, flight5 };
		
		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorPass.predict(records, true, true, true));
	}
	
	@Test
	public void test2() {

		// in this test, all are opposite (i.e., have scores of 0), so return should be 0
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);
		FlightRecord flight1 = new FlightRecord(true, true, true, 30);
		FlightRecord flight2 = new FlightRecord(true, true, true, 80);
		
		FlightRecord[] records = { flight0, flight1, flight2 };
		
		assertEquals(0, DelayPredictorPass.predict(records, false, false, false));
	}

	@Test
	public void test3() {

		// only two records with scores above 0
		
		// this has a score of 3
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);

		// this has a score of 2
		FlightRecord flight1 = new FlightRecord(true, true, false, 30);

		// and these should have a score of 0
		FlightRecord flight2 = new FlightRecord(false, false, false, 1);
		FlightRecord flight3 = new FlightRecord(false, false, false, 2);
		
		FlightRecord[] records = { flight0, flight1, flight2, flight3 };

		int expectedAverage = (10 + 30) / 2;
		
		assertEquals(expectedAverage, DelayPredictorFail.predict(records, true, true, true));
	}

	@Test
	public void test4() {

		// in this test, none are perfect matches, but these three have a score of 2
		FlightRecord flight0 = new FlightRecord(false, true, false, 10);
		FlightRecord flight1 = new FlightRecord(true, false, false, 30);
		FlightRecord flight2 = new FlightRecord(true, true, true, 80);

		// and these should have a score of 1
		FlightRecord flight3 = new FlightRecord(true, false, true, 1);
		FlightRecord flight4 = new FlightRecord(false, true, true, 3);
		FlightRecord flight5 = new FlightRecord(false, false, false, 8);
		
		FlightRecord[] records = { flight0, flight1, flight2, flight3, flight4, flight5 };

		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorPass.predict(records, true, true, false));
	}
	
	@Test
	public void test5() {

		// this has a score of 3
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);

		// these have a score of 2
		FlightRecord flight1 = new FlightRecord(true, true, false, 30);
		FlightRecord flight2 = new FlightRecord(true, false, true, 80);

		// and these should have a score of 1
		FlightRecord flight3 = new FlightRecord(true, false, false, 1);
		FlightRecord flight4 = new FlightRecord(false, true, false, 3);
		FlightRecord flight5 = new FlightRecord(false, false, true, 8);
		
		FlightRecord[] records = { flight0, flight1, flight2, flight3, flight4, flight5 };

		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorPass.predict(records, true, true, true));
	}
	
	@Test
	public void test6() {

		// same FlightRecords as test #5, but in backwards order
		
		// this has a score of 3
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);

		// these have a score of 2
		FlightRecord flight1 = new FlightRecord(true, true, false, 30);
		FlightRecord flight2 = new FlightRecord(true, false, true, 80);

		// and these should have a score of 1
		FlightRecord flight3 = new FlightRecord(true, false, false, 1);
		FlightRecord flight4 = new FlightRecord(false, true, false, 3);
		FlightRecord flight5 = new FlightRecord(false, false, true, 8);
		
		FlightRecord[] records = { flight5, flight4, flight3, flight2, flight1, flight0 };

		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorFail.predict(records, true, true, true));
	}

	@Test
	public void test7() {

		// same FlightRecords as test #5 and #6, but in a random order
		
		// this has a score of 3
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);

		// these have a score of 2
		FlightRecord flight1 = new FlightRecord(true, true, false, 30);
		FlightRecord flight2 = new FlightRecord(true, false, true, 80);

		// and these should have a score of 1
		FlightRecord flight3 = new FlightRecord(true, false, false, 1);
		FlightRecord flight4 = new FlightRecord(false, true, false, 3);
		FlightRecord flight5 = new FlightRecord(false, false, true, 8);
		
		FlightRecord[] records = { flight3, flight0, flight2, flight4, flight1, flight5 };

		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorPass.predict(records, true, true, true));
	}

	@Test
	public void test8() {
		// same FlightRecords as tests #5-7, but in a (different) random order
		
		// this has a score of 3
		FlightRecord flight0 = new FlightRecord(true, true, true, 10);

		// these have a score of 2
		FlightRecord flight1 = new FlightRecord(true, true, false, 30);
		FlightRecord flight2 = new FlightRecord(true, false, true, 80);

		// and these should have a score of 1
		FlightRecord flight3 = new FlightRecord(true, false, false, 1);
		FlightRecord flight4 = new FlightRecord(false, true, false, 3);
		FlightRecord flight5 = new FlightRecord(false, false, true, 8);
		
		FlightRecord[] records = { flight1, flight4, flight3, flight2, flight5, flight0 };

		int expectedAverage = (10 + 30 + 80) / 3;
		
		assertEquals(expectedAverage, DelayPredictorFail.predict(records, true, true, true));
	}

}
