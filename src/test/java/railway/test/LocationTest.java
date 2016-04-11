package railway.test;

import railway.*;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for the {@link Location} implementation class.
 * 
 * A more extensive test suite will be performed for assessment of your code,
 * but this should get you started writing your own unit tests.
 */
@SuppressWarnings("unused") // FOR NOW...
public class LocationTest {
	
	private static final Junction A = new Junction("A");
	private static final JunctionBranch A1 = new JunctionBranch(A, Branch.NORMAL);
	private static final JunctionBranch A2 = new JunctionBranch(A, Branch.FACING);
	private static final JunctionBranch A3 = new JunctionBranch(A, Branch.REVERSE);
	
	private static final Junction B = new Junction("B");
	private static final JunctionBranch B1 = new JunctionBranch(B, Branch.NORMAL);
	private static final JunctionBranch B2 = new JunctionBranch(B, Branch.FACING);
	private static final JunctionBranch B3 = new JunctionBranch(B, Branch.REVERSE);
	
	private static final Junction C = new Junction("C");
	private static final JunctionBranch C1 = new JunctionBranch(C, Branch.NORMAL);
	private static final JunctionBranch C2 = new JunctionBranch(C, Branch.FACING);
	private static final JunctionBranch C3 = new JunctionBranch(C, Branch.REVERSE);
	
	/** Test construction of a typical location that is not at a junction */
	@Test
	public void testTypicalLocation() {
		// parameters used to construct the location under test
		int length = 9;
		JunctionBranch endPoint1 = new JunctionBranch(new Junction("j1"), Branch.FACING);
		JunctionBranch endPoint2 = new JunctionBranch(new Junction("j2"), Branch.NORMAL);
		
		Section section = new Section(length, endPoint1, endPoint2);
		JunctionBranch endPoint = endPoint1;
		int offset = 3;
		
		// the location under test
		Location location = new Location(section, endPoint, offset);
		
		// check the section, end-point and offset of the location
		assertEquals(section, location.getSection());
		assertEquals(endPoint, location.getEndPoint());
		assertEquals(offset, location.getOffset());
		
		// check whether or not this location is at a junction
		assertFalse(location.atAJunction());
		
		// the location is not at a junction: test that the location is on a
		// section equivalent to location.getSection()
		Section equivalentSection = new Section(length, endPoint1, endPoint2);
		assertTrue(location.onSection(equivalentSection));
		
		// the location is not at a junction: check that it is not on some other section
		Section anotherSection = new Section(10, new JunctionBranch(new Junction("j1"), Branch.NORMAL),
				new JunctionBranch(new Junction("j3"), Branch.FACING));
		assertFalse(location.onSection(anotherSection));
		
		// check that the string representation is correct
		String actualString = location.toString();
		String expectedString = "Distance 3 from j1 along the FACING branch";
		assertEquals(expectedString, actualString);
		
		// check that the class invariant has been established.
		assertTrue("Invariant incorrect", location.checkInvariant());
	}
	
	/**
	 * Check that the appropriate exception is thrown if a location is created
	 * with a null end-point.
	 */
	@Test(expected = NullPointerException.class)
	public void testNullEndPoint() {
		// parameters used to construct the location under test
		int length = 9;
		JunctionBranch endPoint1 = new JunctionBranch(new Junction("j1"), Branch.FACING);
		JunctionBranch endPoint2 = new JunctionBranch(new Junction("j2"), Branch.NORMAL);
		
		Section section = new Section(length, endPoint1, endPoint2);
		JunctionBranch endPoint = null;
		int offset = 3;
		
		// the location under test
		Location location = new Location(section, endPoint, offset);
	}
	
	/**
	 * Tests that two different section objects between the same two points are equal when:<br>
	 * They have their parameters swapped.
	 */
	@Test
	public void sectionSwapTest() {
		Section original = new Section(1, A1, B1);
		Section swapped = new Section(1, B1, A1);
		
		assertEquals("It's the same section if it's between the same JunctionBranches.", original, swapped);
		assertNotEquals(original.hashCode(), swapped.hashCode());
	}
	
	/**
	 * Tests that two different section objects between the same two points are not equal when:<br>
	 * They have different lengths.
	 */
	@Test
	public void sectionLengthTest() {
		// I don't think this is logically possible without a rip in space/time.
		Section shorter = new Section(1, A1, B1);
		Section longer = new Section(1000, A1, B1);
		
		assertNotEquals("Lengths are different == sections are different.", shorter, longer);
		assertNotEquals(shorter.hashCode(), longer.hashCode());
	}
	
	/**
	 * Tests that two different section objects between the same two points are not equal when:<br>
	 * The junctions have different branches.
	 */
	@Test
	public void sectionBranchTest2() {
		Section first = new Section(1, A1, B1);
		Section second = new Section(1, A2, B1);
		Section third = new Section(1, A1, B2);
		Section fourth = new Section(1, A2, B2);
		
		assertNotEquals("The sections are not the same if the .", first, second);
		assertNotEquals("Lengths are different, sections are different.", first, third);
		assertNotEquals("Lengths are different, sections are different.", first, fourth);
		assertNotEquals("Lengths are different, sections are different.", second, third);
		assertNotEquals("Lengths are different, sections are different.", second, fourth);
		assertNotEquals("Lengths are different, sections are different.", third, fourth);
	}
	
	/** Basic check of the equals method */
	@Test
	public void originalTest() { // FIXME totally broken, sorry. butchering into many tests.
		// parameters used to construct the locations under test
		JunctionBranch A1 = new JunctionBranch(new Junction("A"), Branch.FACING);
		JunctionBranch A2 = new JunctionBranch(new Junction("A"), Branch.REVERSE);
		JunctionBranch B = new JunctionBranch(new Junction("B"), Branch.NORMAL);
		JunctionBranch C = new JunctionBranch(new Junction("C"), Branch.FACING);
		
		Section section1 = new Section(5, A1, B);
		Section section2 = new Section(5, A1, B);
		
		// equal case: the locations are both at the same junction
		Location location1 = new Location(section1, A1, 0);
		Location location2 = new Location(section2, A2, 0);
		assertEquals(location1, location2);
		
		// equal case: the locations are not at a junction: but they have
		// equivalent end-points and non-zero offsets.
		location1 = new Location(section1, A1, 3);
		location2 = new Location(section1, new JunctionBranch(new Junction("j1"), Branch.FACING), 3);
		assertEquals(location1, location2);
		
		// equal case: the locations are not at a junction: they are the same
		// location described from opposite end-points of the same section.
		location1 = new Location(section1, A1, 3);
		location2 = new Location(section1, B, 6);
		assertEquals(location1, location2);
		assertEquals(location1.hashCode(), location2.hashCode());
		
		// unequal case: basic case
		location1 = new Location(section1, A1, 3);
		location2 = new Location(section1, B, 4);
		assertNotEquals(location1, location2);
	}
}