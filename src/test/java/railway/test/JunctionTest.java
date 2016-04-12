package railway.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import railway.Junction;

/**
 * Basic tests for the {@link Junction} implementation class.
 */
public class JunctionTest {
	
	/**
	 * Tests that the same section object equals itself. Literally just to make you feel good.
	 */
	@Test
	public void junctionBasicTest() {
		Junction junction = new Junction("A");
		
		assertEquals("Drop the course", junction, junction);
	}
	
	@Test
	public void junctionSwapTest() {
		Junction pink = new Junction("A");
		Junction lightishRed = new Junction("A");
		
		assertEquals("Remember to use the .equals() method, and never == unless you are using "
				+ "primitive types (int, char, boolean, etc), or you want to compare the "
				+ "MEMORY LOCATION of two objects.", pink, lightishRed);
		
		assertEquals("Remember to make your hash using .equals(). Protip: you can autogenerate a "
				+ "good starting point by going \"Source -> Generate hashCode() and equals()\" in "
				+ "Eclipse.", pink.hashCode(), lightishRed.hashCode());
	}
	
	@Test
	public void junctionNegativeTest() {
		Junction alpha = new Junction("A");
		Junction beta = new Junction("B");
		
		assertNotEquals(alpha, beta);
	}
}