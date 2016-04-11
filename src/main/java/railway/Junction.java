package railway;

/**
 * <p>
 * An immutable class representing a junction on a railway track.
 * </p>
 * 
 * <p>
 * In the context of a particular instance of a railway track, each junction has between one and
 * three branches that connect it to sections of the track. It can have at most one branch of each
 * type. (I.e. a junction may have not have two branches of type Branch.FACING.)
 * </p>
 */
public class Junction {
	
	private final String junctionIdentifier;
	
	/**
	 * Creates a new junction with the given identifier.
	 * 
	 * @param jId - The identifier of this Junction.
	 * @throws NullPointerException If jId is null.
	 */
	public Junction(String jId) throws NullPointerException {
		if (jId == null) {
			throw new NullPointerException("The Junction Identifier cannot be null.");
		}
		this.junctionIdentifier = jId;
	}
	/**
	 * Returns the identifier of the junction.
	 * 
	 * @return the junction identifier
	 */
	public String getJunctionId() {
		return junctionIdentifier;
	}
	
	@Override
	public String toString() {
		return junctionIdentifier;
	}
	
	/**
	 * Returns true if and only if the given object is an instance of the class Junction, with an
	 * equivalent identifier string to this.
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
		
		Junction other = (Junction) obj;
		return (junctionIdentifier.equals(other.junctionIdentifier));
	}
	
	@Override
	public int hashCode() {
		return junctionIdentifier.hashCode();
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
		return (junctionIdentifier != null);
	}
}