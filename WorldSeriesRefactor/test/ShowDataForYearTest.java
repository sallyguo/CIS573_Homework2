import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ShowDataForYearTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test2004() {
		String result = UserInterface.showDataForYear(2004);
		assertEquals("In 2004 the Boston Red Sox defeated the St. Louis Cardinals by 4-0", result);
	}

	@Test
	public void test2007() {
		String result = UserInterface.showDataForYear(2007);
		assertEquals("In 2007 the Boston Red Sox defeated the Colorado Rockies by 4-0", result);
	}

	@Test
	public void test2008() {
		String result = UserInterface.showDataForYear(2008);
		assertEquals("In 2008 the Philadelphia Phillies defeated the Tampa Bay Rays by 4-1", result);
	}

	@Test
	public void test1994() {
		String result = UserInterface.showDataForYear(1994);
		assertEquals("No World Series was held in 1994", result);
	}
}
