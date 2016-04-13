package railway;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>
 * A mutable class representing the layout of a railway track.
 * </p>
 *
 * <p>
 * A railway track is made up of a number of sections. A junction is on the track if and only if the
 * junction is at one of the end-points of a section in the track.
 * 
 * Each junction on the track has between one and three branches that connect it to sections of the
 * track, and it can have at most one branch of each type. (I.e. a junction may have not have two or
 * more branches of type Branch.FACING.)
 * </p>
 * 
 * @author Addison Gourluck
 */
public class Track implements Iterable<Section> {
	
	Set<Section> sections = new HashSet<>();
	
	/**
	 * Creates a new track with no sections.
	 */
	public Track() {
		
	}
	
	/**
	 * <p>
	 * Adds the given section to the track, unless the addition of the section would result in the
	 * track becoming invalid.
	 * </p>
	 * 
	 * <p>
	 * If the section is null, then a NullPointerException is thrown and the track is not modified.
	 * </p>
	 * 
	 * <p>
	 * If the track already contains an equivalent section, then the track is not modified by this
	 * operation.
	 * </p>
	 * 
	 * <p>
	 * If the section is not null, and the track does not already contain an equivalent section, but
	 * the addition of the section would result in one of the track junctions being connected to
	 * more than one section on the same branch, then an InvalidTrackException is thrown, and the
	 * track is not modified.
	 * </p>
	 * 
	 * <p>
	 * Otherwise, the section is added to the track.
	 * </p>
	 * 
	 * @param section - The section to be added to the track.
	 * @throws NullPointerException If section is null.
	 * @throws InvalidTrackException If the track does not already contain an equivalent section,
	 *         but it already contains a section that is connected to one of the same end-points as
	 *         the given section. (Recall that a junction can only be connected to one section on a
	 *         given branch.) Two end-points are the considered to be the same if they are
	 *         equivalent according to the equals method of the JunctionBranch class.
	 */
	public void addSection(Section section) throws NullPointerException, IllegalArgumentException {
		if (section == null) {
			throw new NullPointerException("Cannot add a null section to the track.");
		}
		
		// TODO - 1337 haxxor checking to make sure ain't nothing being violated
		
		sections.add(section);
	}
	
	/**
	 * If the track contains a section that is equivalent to this one, then it is removed from the
	 * layout of the railway, otherwise this method does not alter the railway layout in any way.
	 * 
	 * @param section - The section to be removed from the track.
	 */
	public void removeSection(Section section) {
		sections.remove(section);
	}
	
	/**
	 * Returns true if the track contains the given section and false otherwise.
	 * 
	 * @param section - The section whose presence in the track is to be checked.
	 * @return True iff the track contains a section that is equivalent to the given parameter.
	 */
	public boolean contains(Section section) {
		return sections.contains(section);
	}
	
	/**
	 * Returns a set of all the junctions in the track that are connected to at least one section of
	 * the track.
	 * 
	 * @return The set of all junctions in this track.
	 */
	public Set<Junction> getJunctions() {
		// Because optimization isn't important
		Set<Junction> junctions = new HashSet<>();
		for (Section section : sections) {
			junctions.addAll(section.getJunctions());
		}
		return junctions;
	}
	
	/**
	 * If the track contains a section that is connected to the given junction on the given branch,
	 * then it returns that section, otherwise it returns null.
	 * 
	 * @param junction - The junction for which the section will be returned.
	 * @param branch - The branch of the junction for which the section will be returned.
	 * @return the section of track that is connected to the junction on the given branch, if there
	 *         is one, otherwise null
	 */
	public Section getTrackSection(Junction junction, Branch branch) {
		for (Section section : sections) {
			// For each section...
			if (section.getJunctions().contains(junction)) {
				// If it is connected to the junction in question...
				for (JunctionBranch junctionBranch : section.getEndPoints()) {
					// Check each of this sections endpoints...
					if (junctionBranch.getBranch().equals(branch)) {
						// And return it if it is on that branch
						return section;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns an iterator over the sections in the track. (The iterator can return the sections on
	 * the track in any order.)
	 */
	@Override
	public Iterator<Section> iterator() {
		return sections.iterator();
	}
	
	/**
	 * The string representation of a track contains a line-separated concatenation of the string
	 * representations of the sections that make up the track. The sections can appear in any order.
	 * 
	 * The line separator string used to separate the sections should be retrieved in a
	 * machine-independent way by calling the function System.getProperty("line.separator").
	 */
	@Override
	public String toString() {
		StringBuilder stringy = new StringBuilder();
		
		Iterator<Section> itsy = iterator();
		while (itsy.hasNext()) {
			Section section = itsy.next();
			stringy.append(section.toString());
			if (itsy.hasNext()) {
				stringy.append(System.getProperty("line.separator"));
			} else {
				break;
			}
		}
		return stringy.toString();
	}
	
	/**
	 * Determines whether this class is internally consistent (i.e. it satisfies its class
	 * invariant).
	 * 
	 * This method is only intended for testing purposes.
	 * 
	 * @return true if this class is internally consistent, and false otherwise.
	 */
	public boolean checkInvariant() {
		return true; // TODO - REMOVE THIS LINE AND WRITE THIS METHOD
	}
}