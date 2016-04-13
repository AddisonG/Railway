package railway;

/**
 * <p>
 * An immutable class used to identify a junction and one of its branches on a railway track.
 * </p>
 * 
 * @author Addison Gourluck
 */
public class JunctionBranch {
	
	private final Junction junction;
	private final Branch branch;
	
	/*
	 * invariant: junction!= null && branch != null
	 */
	
	/**
	 * Creates a new instance of this class representing the given junction and its branch.
	 * 
	 * @param junction - The Junction of this pair
	 * @param branch - The Branch of this pair
	 * @throws NullPointerException If either parameter is null
	 */
	public JunctionBranch(Junction junction, Branch branch) throws NullPointerException {
		if (junction == null || branch == null) {
			throw new NullPointerException("The method paramters cannot be null.");
		}
		this.junction = junction;
		this.branch = branch;
	}
	
	/**
	 * Returns the junction associated with this object.
	 * 
	 * @return The junction of this pair.
	 */
	public Junction getJunction() {
		return junction;
	}
	
	/**
	 * Returns the branch associated with this object.
	 * 
	 * @return The branch associated with this pair.
	 */
	public Branch getBranch() {
		return branch;
	}
	
	/**
	 * Returns a string of the form: "(JUNCTION, BRANCH)"
	 * 
	 * where JUNCTIONSTRING is the string representation of the junction, and
	 * BRANCHSTRING is the string representation of the branch of this pair.
	 */
	@Override
	public String toString() {
		return String.format("(%s, %s)", junction, branch);
	}
	
	/**
	 * Returns true if and only if the given object is an instance of the class
	 * JunctionBranch, with an equivalent junction and branch to this one.
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof JunctionBranch)) {
			return false;
		}
		JunctionBranch other = (JunctionBranch) object; // the pair to compare
		return (junction.equals(other.junction) && branch.equals(other.branch));
	}
	
	@Override
	public int hashCode() {
		final int prime = 43;
		int result = prime + junction.hashCode();
		result = prime * result + branch.hashCode();
		return result;
	}
	
	/**
	 * Determines whether this class is internally consistent (i.e. it satisfies
	 * its class invariant).
	 * 
	 * This method is only intended for testing purposes.
	 * 
	 * @return true if this class is internally consistent, and false otherwise.
	 */
	public boolean checkInvariant() {
		return (junction != null && branch != null);
	}
}