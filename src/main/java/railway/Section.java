package railway;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * An immutable class corresponding to a section of a railway track.
 * </p>
 * 
 * <p>
 * A section of track has a positive length (in meters), and lies between two junctions. These
 * junctions may not be distinct, since a section of track may form a loop from one junction back to
 * the same junction.
 * </p>
 * 
 * <p>
 * In the context of a particular railway track, each junction has between one and three branches
 * (of type Branch) that connect it to sections of the track. It can have at most one branch of each
 * type. (I.e. a junction may not have two branches of type Branch.FACING.)
 * </p>
 * 
 * <p>
 * If a section forms a loop from one junction back to itself, then the junction must be connected
 * to the section on two different branches.
 * </p>
 * 
 * <p>
 * A section is uniquely identified by its length and two distinct end-points, where an end-point is
 * a junction and the branch that connects it to the section.
 * </p>
 * 
 * @author Addison Gourluck
 */
public class Section {
	
	private final int length;
	
	private final JunctionBranch endPoint1;
	private final JunctionBranch endPoint2;
	private final Set<JunctionBranch> endPoints = new HashSet<>();
	private final Set<Junction> junctions = new HashSet<>();
	
	public void main(String[] args) {
		if (args.length != 5) {
			throw new IllegalArgumentException(
					"Must be exactly five arguments: length, id1, id2, branch1, branch2");
		}
		
		if (args[1].equals(args[2])) {
			System.out.println("Loop Section created.");
		}
		
		Section section = new Section(Integer.parseInt(args[0]),
				new JunctionBranch(new Junction(args[1]), Branch.valueOf(args[3])),
				new JunctionBranch(new Junction(args[2]), Branch.valueOf(args[4])));
				
	}
	
	/**
	 * Creates a new section with the given length (in meters) and end-points.
	 * 
	 * @param length - A positive integer representing the length of the section in meters
	 * @param endPoint1 - One end-point of the section
	 * @param endPoint2 - The other end-point of the section
	 * @throws NullPointerException If either end-point is null
	 * @throws IllegalArgumentException If either the length is less than or equal to zero, or the end-points are
	 *             equivalent (two end-points are equivalent if they are equal according to the
	 *             equals method of the JunctionBranch class).
	 */
	public Section(int length, JunctionBranch endPoint1, JunctionBranch endPoint2)
			throws NullPointerException, IllegalArgumentException {
			
		if (endPoint1 == null || endPoint2 == null) {
			throw new NullPointerException("Endpoint cannot be null.");
		} else if (length <= 0 || endPoint1.equals(endPoint2)) {
			throw new IllegalArgumentException("Length must be positive.");
		}
		
		this.length = length;
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
		endPoints.add(endPoint1);
		endPoints.add(endPoint2);
		junctions.add(endPoint1.getJunction());
		junctions.add(endPoint2.getJunction());
	}
	
	/**
	 * Returns the length of the section (in meters).
	 * 
	 * @return the length of the section
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Returns the end-points of the section.
	 * 
	 * @return a set of the end-points of the section.
	 */
	public Set<JunctionBranch> getEndPoints() {
		return endPoints;
	}
	
	/**
	 * Returns the junctions of the section.
	 * 
	 * @return a set of the junctions of the section.
	 */
	public Set<Junction> getJunctions() {
		return junctions;
	}
	
	/**
	 * If the given end-point is equivalent to an end-point of the section, then it returns the
	 * end-point at the opposite end of the section. Otherwise this method throws an
	 * IllegalArgumentException.
	 * 
	 * @param endPoint - The given end-point of this section
	 * @throws IllegalArgumentException If the given end-point is not an equivalent to an end-point of the given section.
	 * @return The end-point at the opposite end of the section to endPoint
	 */
	public JunctionBranch otherEndPoint(JunctionBranch endPoint) {
		if (endPoint.equals(endPoint1)) {
			return endPoint2;
		} else if (endPoint.equals(endPoint2)) {
			return endPoint1;
		}
		throw new IllegalArgumentException("Argument is not an endPoint in this Section.");
	}
	
	/**
	 * <p>
	 * Returns the string representation of the section. The string representation consists of the
	 * length, followed by the single space character ' ', followed by the toString() representation
	 * of one of the end-points, followed by the single space character ' ', followed by the
	 * toString() representation of the other end-point.
	 * </p>
	 * 
	 * <p>
	 * The end-points can occur in any order, so that either the string
	 * "9 (j1, FACING) (j2, NORMAL)" or the string "9 (j2, NORMAL) (j1, FACING)" , would be valid
	 * string representations of a section of length 9, with end-points "(j1, FACING)" and
	 * "(j2, NORMAL)".
	 * </p>
	 */
	@Override
	public String toString() {
		return String.format("%d %s %s", length, endPoint1, endPoint2);
	}
	
	/**
	 * <p>
	 * Returns true if and only if the given object is an instance of the class Section with the
	 * same length as this one, and equivalent end-points.
	 * </p>
	 * 
	 * <p>
	 * The end-points of Section a and Section b are equivalent if and only if, for each end-point
	 * of a, there is an equivalent end-point of b. (Two end-points are equivalent if their
	 * junctions and branches are equivalent as per the equals method in the JunctionBranch class).
	 * </p>
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		
		Section other = (Section) obj;
		if (length != other.length) {
			// length isn't the same
			return false;
		}
		
		if (endPoint1.equals(other.endPoint1) && endPoint2.equals(other.endPoint2)) {
			// endPoints are in the same order
			return true;
		}
		if (endPoint1.equals(other.endPoint2) && endPoint2.equals(other.endPoint1)) {
			// endPoints are swapped
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 37;
		// Use difference between hashes so that things like A+D don't equal B+C
		int result = prime * Math.abs(endPoint1.hashCode() - endPoint2.hashCode());
		result = prime * result + endPoint1.hashCode() + endPoint2.hashCode();
		result = prime * result + length;
		return result;
	}
	
	/**
	 * Determines whether this class is internally consistent (i.e. it satisfies its class
	 * invariant).
	 * 
	 * This method is only intended for testing purposes.
	 * 
	 * @return True if this class is internally consistent, and false otherwise.
	 */
	public boolean checkInvariant() {
		if (length <= 0 || endPoint1 == null || endPoint2 == null) {
			return false;
		}
		return true;
	}
}