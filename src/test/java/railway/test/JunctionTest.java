package railway.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import railway.Junction;

/**
 * Basic tests for the {@link Junction} implementation class.
 */
public class JunctionTest {
	
	@Test
	public void junctionEqualsBasicTest() {
		Junction junction = new Junction("A");
		
		assertEquals("Drop the course", junction, junction);
		assertEquals(junction.getJunctionId(), "A");
	}
	
	@Test
	public void junctionEqualsSwapTest() {
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
	public void junctionEqualsNegativeTest() {
		Junction alpha = new Junction("A");
		Junction beta = new Junction("B");
		
		assertNotEquals(alpha, beta);
	}
	
	@Test
	public void junctionHashcodeTest() {
		Junction alpha = new Junction("AD");
		Junction beta = new Junction("BC");
		Junction delta = new Junction("BE");
		Junction gamma = new Junction("DA");
		
		String message = "You don't need to make a custom hashcode. Just String's default hash.";
		assertNotEquals(message, alpha.hashCode(), beta.hashCode());
		assertNotEquals(message, alpha.hashCode(), delta.hashCode());
		assertNotEquals(message, alpha.hashCode(), gamma.hashCode());
	}
	
	@Test
	public void junctionInvariantTest() {
		// Create a mock junction
		Junction nullJunction = Mockito.mock(Junction.class);
		// Make the "getJunctionId" method of the mocked junction always return null
		when(nullJunction.getJunctionId()).thenReturn(null);
		
		Junction normalJunction = new Junction("A");
		Junction weirdJunction = new Junction("");
		
		assertTrue(normalJunction.checkInvariant());
		assertTrue("Nothing wrong with having an empty string as an id.", weirdJunction.checkInvariant());
		assertFalse("If a junction (somehow) has a null id, it is invalid.", nullJunction.checkInvariant());
	}
	
	@Test
	public void junctionToStringTest() {
		Junction normalJunction = new Junction("A");
		Junction weirdJunction = new Junction("!\n^-LeadingLinux\r\n^-WindozeNewline.");
		
		assertEquals(normalJunction.toString(), "A");
		assertEquals(weirdJunction.toString(), "!\n^-LeadingLinux\r\n^-WindozeNewline.");
	}
	
	@Test(expected = NullPointerException.class)
	public void junctionConstructorTest() {
		try {
			new Junction("Whatever");
			new Junction("");
		} catch (Exception e) {
			fail("Threw an exception when you shouldn't have.");
		}
		new Junction(null);
	}
}