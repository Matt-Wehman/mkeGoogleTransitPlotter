

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class Trip {

	private int blockID;
	private int directionID;
	private int routeID;
	private int serviceID;
	private int shapeID;
	private HashMap<Integer, StopTime> StopTimes;
	private String tripHeadSign;
	private int tripID;
	public Route m_Route;

	public Trip(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param newTrip
	 */
	public boolean update(Trip newTrip){
		return false;
	}

}