package railway.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import railway.Branch;
import railway.Junction;
import railway.JunctionBranch;
import railway.Section;

/**
 * Basic tests for the {@link Section} implementation class.
 */
public class SectionTest {
	private static final double EPSILON = 0.0001;
	
	// If you're reading this and wondering wtf, please look for a pattern here.
	private static final Junction A = new Junction("A");
	private static final JunctionBranch A1 = new JunctionBranch(A, Branch.NORMAL);
	private static final JunctionBranch AA = new JunctionBranch(A, Branch.NORMAL);
	private static final JunctionBranch A2 = new JunctionBranch(A, Branch.FACING);
	private static final JunctionBranch AB = new JunctionBranch(A, Branch.FACING);
//	private static final JunctionBranch A3 = new JunctionBranch(A, Branch.REVERSE);
//	private static final JunctionBranch AC = new JunctionBranch(A, Branch.REVERSE);
	
	private static final Junction B = new Junction("B");
	private static final JunctionBranch B1 = new JunctionBranch(B, Branch.NORMAL);
	private static final JunctionBranch BA = new JunctionBranch(B, Branch.NORMAL);
	private static final JunctionBranch B2 = new JunctionBranch(B, Branch.FACING);
	private static final JunctionBranch BB = new JunctionBranch(B, Branch.FACING);
//	private static final JunctionBranch B3 = new JunctionBranch(B, Branch.REVERSE);
//	private static final JunctionBranch BC = new JunctionBranch(B, Branch.REVERSE);
	
	/**
	 * Tests that two different section objects between the same two points ARE equal when:<br>
	 * They have their junction branches swapped.
	 */
	@Test
	public void sectionSwapTest() {
		Section original = new Section(1, A1, B1);
		Section swapped = new Section(1, B1, A1);
		
		assertEquals("It's the same section if it's between the same JunctionBranches.", original, swapped);
		assertEquals("Your hashcode should not discriminate against ordering. I suggest adding "
				+ "stuff.", original.hashCode(), swapped.hashCode());
		
		// We can prevent the terrible collisions that would arise in the following situation...
		assertEquals("B".hashCode() + "C".hashCode(), "A".hashCode() + "D".hashCode()); // OMG EQUAL
		// ... by simply checking the difference between the two hashes and putting that in too.
		// See my example
	}
	
	/**
	 * Tests that two different section objects between the same two points ARE NOT equal when:<br>
	 * They have different lengths.
	 */
	@Test
	public void sectionLengthTest() {
		Section shorter = new Section(1, A1, B1);
		Section longer = new Section(1000, A1, B1);
		
		assertNotEquals("Even if two sections have the same junction branches, they are not the "
				+ "same if their length is different.", shorter, longer);
		assertNotEquals("Remember to factor the length into the hashcode.",
				shorter.hashCode(), longer.hashCode());
	}
	
	/**
	 * Tests that four different section objects between the same two points ARE NOT equal when:<br>
	 * The junction branches have the same junctions, but different branches.
	 */
	@Test
	public void sectionBranchTest() {
		Section first = new Section(1, A1, B1);
		Section second = new Section(1, A2, B1);
		Section third = new Section(1, A1, B2);
		Section fourth = new Section(1, A2, B2);
		
		String message = "Sections are not the same even if they have the same junctions. Both "
				+ "junctions must have the same branches too (i.e: the junctionbranches must "
				+ ".equal() each other).";
		// All combinations, why not
		assertNotEquals(message, first, second);
		assertNotEquals(message, first, third);
		assertNotEquals(message, first, fourth);
		assertNotEquals(message, second, third);
		assertNotEquals(message, second, fourth);
		assertNotEquals(message, third, fourth);
		
		message = "The hashcodes for the Sections are equal when they should not be. Ensure that "
				+ "the hashcode for the section is based on the hashes of both the sectionbranches "
				+ "and the length.";
		assertNotEquals(message, first.hashCode(), second.hashCode());
		assertNotEquals(message, first.hashCode(), third.hashCode());
		assertNotEquals(message, first.hashCode(), fourth.hashCode());
		assertNotEquals(message, second.hashCode(), third.hashCode());
		assertNotEquals(message, second.hashCode(), fourth.hashCode());
		assertNotEquals(message, third.hashCode(), fourth.hashCode());
	}
	
	/**
	 * Tests that two different section objects between the same two points ARE equal when:<br>
	 * The junctions that make up each junction branch are different objects with the same data.
	 */
	@Test
	public void sectionReferenceTest() {
		Section cain = new Section(1, A1, BA);
		Section abel = new Section(1, AA, BB);
		
		assertEquals("Make sure your .equals() methods for junctions and junction branches are "
				+ "sound. These objects are identical, despite having different references.",
				cain, abel);
		assertEquals("You'd better not be using == when you should be using .equals().",
				cain.hashCode(), abel.hashCode());
	}
}