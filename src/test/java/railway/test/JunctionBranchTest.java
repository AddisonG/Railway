package railway.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import railway.Branch;
import railway.Junction;
import railway.JunctionBranch;

/**
 * Basic tests for the {@link JunctionBranch} implementation class.
 * 
 * @author Addison Gourluck
 */
public class JunctionBranchTest {
	
	private Junction A = new Junction("A");
	private Junction B = new Junction("B");
	
	@Test
	public void junctionBranchEqualsBasicTest() {
		JunctionBranch jb = new JunctionBranch(A, Branch.NORMAL);
		JunctionBranch jb2 = new JunctionBranch(new Junction("A"), Branch.NORMAL);
		
		assertEquals(jb.getBranch(), jb2.getBranch());
		assertEquals(jb.getJunction(), jb2.getJunction());
		assertEquals(jb.getJunction().getJunctionId(), jb2.getJunction().getJunctionId());
		
		assertEquals(jb, jb2);
	}
	
	@Test
	public void junctionBranchEqualsNegativeTest() {
		JunctionBranch alpha = new JunctionBranch(A, Branch.NORMAL);
		JunctionBranch beta = new JunctionBranch(A, Branch.REVERSE);
		JunctionBranch delta = new JunctionBranch(B, Branch.NORMAL);
		JunctionBranch gamma = new JunctionBranch(B, Branch.REVERSE);
		
		assertNotEquals(alpha, beta);
		assertNotEquals(alpha, delta);
		assertNotEquals(beta, gamma);
		assertNotEquals(delta, gamma);
	}
	
	@Test
	public void junctionBranchHashcodeTest() {
		JunctionBranch alpha = new JunctionBranch(A, Branch.NORMAL);
		JunctionBranch beta = new JunctionBranch(A, Branch.REVERSE);
		JunctionBranch delta = new JunctionBranch(B, Branch.NORMAL);
		JunctionBranch gamma = new JunctionBranch(B, Branch.REVERSE);
		
		assertNotEquals(alpha.hashCode(), beta.hashCode());
		assertNotEquals(alpha.hashCode(), delta.hashCode());
		assertNotEquals(beta.hashCode(), gamma.hashCode());
		assertNotEquals(delta.hashCode(), gamma.hashCode());

		JunctionBranch alpha2 = new JunctionBranch(new Junction("A"), Branch.NORMAL);
		assertEquals(alpha.hashCode(), alpha2.hashCode());
	}
	
	/*// no work done - copy past atm
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
	}*/
	
	@Test
	public void junctionBranchToStringTest() {
		JunctionBranch alpha = new JunctionBranch(A, Branch.FACING);
		JunctionBranch beta = new JunctionBranch(B, Branch.REVERSE);
		
		assertEquals("(A, FACING)", alpha.toString());
		assertEquals("(B, REVERSE)", beta.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void junctionBranchConstructorTest1() {
		new JunctionBranch(A, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void junctionBranchConstructorTest2() {
		new JunctionBranch(null, Branch.FACING);
	}
}