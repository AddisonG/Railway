package railway;

/**
 * <p>
 * An immutable class representing a location on a railway track.
 * </p>
 * 
 * <p>
 * A location on a railway track may be at either at a junction in the track, or on a section of the
 * track between the two end-points of the section.
 * </p>
 * 
 * <p>
 * Note: A location at a junction lies on all of the sections of track that are connected to the
 * junction; a location that is not at a junction, lies on only one section of track.
 * </p>
 * 
 * <p>
 * A location can be identified by its offset with respect to an end-point of a section of the
 * track. However, the same location can be identified by different descriptions. For example, given
 * a section s of length 10 and with end-points (j1, FACING) and (j2, REVERSE), the same location
 * may equivalently be described as being either 3 meters from junction j1 along its FACING branch,
 * or 7 meters from junction j2 along its REVERSE branch. A location which is to be found at a
 * junction, is represented as having a 0 offset from the junction along any branch of the junction.
 * For this reason, the equivalence method of this class is more complex than usual.
 * </p>
 * 
 * @author Addison Gourluck
 */
public class Location {
	
	private final Section section;
	private final int offset;
	private final JunctionBranch endPoint;
	
	/**
	 * Creates a new location that lies on the given section at a distance of offset meters from
	 * endPoint.getJunction() along endPoint.getBranch().
	 * 
	 * @param section - A section that the location lies on.
	 * @param endPoint - An end-point of the given section.
	 * @param offset - The distance of the location from the given end-point of the section that it
	 *        lies on. The distance must be greater than or equal to zero, and strictly less than
	 *        the length of the section.
	 * @throws NullPointerException If either parameter section or endPoint is null.
	 * @throws IllegalArgumentException If (i) offset is either a negative value or if it is greater
	 *         than or equal to the length of the given section, or (ii) section and endPoint are
	 *         not null, but parameter endPoint is not equivalent to an end-point of the given
	 *         section.
	 */
	public Location(Section section, JunctionBranch endPoint, int offset) {
		if (section == null || endPoint == null) {
			throw new NullPointerException("Parameters may not be null.");
		}
		if (offset < 0 || offset >= section.getLength()) {
			throw new IllegalArgumentException("The length must a positive integer, not longer than"
					+ " the length of the section (" + section.getLength() + ").");
		}
		if (!section.getEndPoints().contains(endPoint)) {
			throw new IllegalArgumentException("The section must contain the endPoint.");
		}
		this.section = section;
		this.endPoint = endPoint;
		this.offset = offset;
	}
	
	/**
	 * <p>
	 * Returns a section of the track that this location lies on. Note that a location at a junction
	 * may lie on multiple sections, and this method only returns one of them: the section that this
	 * method was constructed with.
	 * </p>
	 * 
	 * <p>
	 * Note: sections that are equivalent according to the equals method of this class may have
	 * different return-values of this method.
	 * </p>
	 * 
	 * @return a section that this location lies on
	 */
	public Section getSection() {
		return section;
	}
	
	/**
	 * <p>
	 * This method returns an end-point of the section this.getSection(), that this location lies
	 * on. The end-point that is returned is the one that this class was constructed with.
	 * </p>
	 * 
	 * <p>
	 * Note: sections that are equivalent according to the equals method of this class may have
	 * different return-values of this method.
	 * </p>
	 * 
	 * @return an end-point of this.getSection()
	 */
	public JunctionBranch getEndPoint() {
		return endPoint;
	}
	
	/**
	 * <p>
	 * This method returns the offset of this location with respect to the end-point
	 * this.getEndPoint() that lies on the track section this.getSection().
	 * </p>
	 * 
	 * <p>
	 * Equivalent locations can be equivalently described by different offsets and end-points.
	 * Methods getOffset() and getEndPoint() return the offset and end-point that this location was
	 * constructed with.
	 * </p>
	 * 
	 * <p>
	 * Note: sections that are equivalent according to the equals method of this class may have
	 * different return-values of this method.
	 * </p>
	 * 
	 * @return the offset of this location with respect to this.getEndPoint().
	 * 
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Returns true if this location is at a junction (i.e. it has an offset of zero with respect to
	 * the end-point of a section).
	 * 
	 * @return Whether or not this location is at a junction.
	 */
	public boolean atAJunction() {
		return offset == 0;// || section.getLength() == offset; // I wish
	}
	
	/**
	 * <p>
	 * Returns true if this location lies on the given section.
	 * </p>
	 * 
	 * <p>
	 * This location lies on a section if and only if either:
	 * <ol>
	 * <li>It is at a junction on an end-point of the given section, or;
	 * <li>It is not at a junction, and the given section is equivalent to this.getSection().
	 * </ol>
	 * </p>
	 * 
	 * @param alternate - The section to check.
	 * @return True iff this location lies on the given section.
	 */
	public boolean onSection(Section alternate) {
		if (section.equals(alternate)) {
			return true;
		}
		
		if (atAJunction()) {
			for (JunctionBranch jb : alternate.getEndPoints()) {
				if (endPoint.getJunction().equals(jb.getJunction())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * <p>
	 * If this location is at a junction (i.e. it has an offset of zero from the end-point of a
	 * section), then this method returns the toString() representation of the junction where this
	 * location lies.
	 * 
	 * Otherwise, if the location is not at a junction, this method returns a string of the form:
	 * <br>
	 * <br>
	 * 
	 * "Distance OFFSET from JUNCTION along the BRANCH branch" <br>
	 * <br>
	 * 
	 * where OFFSET is this.getOffset(), JUNCTION is the toString() representation of the junction
	 * of this.getEndPoint(), and BRANCH is the toString() representation of the branch of
	 * this.getEndPoint().
	 * </p>
	 * 
	 * <p>
	 * Note: sections that are equivalent according to the equals method of this class may have
	 * different return-values of this method.
	 * </p>
	 */
	@Override
	public String toString() {
		return String.format("Distance %d from %s along the %s branch", getOffset(),
				getEndPoint().getJunction(), getEndPoint().getBranch());
	}
	
	/**
	 * <p>
	 * Two locations are equivalent if either: <br>
	 * <br>
	 * 
	 * (i) their offsets are both zero and their end-points are at the same junction (two junctions
	 * are considered to be the same if they are equivalent according to the equals method of the
	 * Junction class) or <br>
	 * <br>
	 * 
	 * (ii) if their end-points are equivalent and their offsets are equal, or <br>
	 * <br>
	 * 
	 * (iii) if their end-points are not equivalent, but they lie on the same section, and the sum
	 * of the length of their offsets equals the length of the section that they lie on. (Two
	 * sections are considered to be the same if they are equal according to the equals method of
	 * the Section class.) <br>
	 * <br>
	 * 
	 * and they are not equivalent otherwise.
	 * </p>
	 * 
	 * <p>
	 * This method returns true if and only if the given object is an instance of the class
	 * Location, and the locations are equivalent according to the above definition.
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
		
		Location other = (Location) obj;
		if (!section.equals(other.section)) {
			// The locations are on different sections
			if (offset != other.offset ||
					!endPoint.getJunction().equals(other.getEndPoint().getJunction())) {
				// For the locations to be equal, both must be offset by 0 from the same endPoint
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 41;
		int result = prime + endPoint.getJunction().hashCode();
		result = prime * result + offset;
		result = prime * result + section.hashCode();
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
		return (section != null && endPoint != null && offset > 0 && offset < section.getLength()
				&& section.getEndPoints().contains(endPoint));
	}
}