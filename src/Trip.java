import java.util.HashMap;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class Trip {

	private int blockID;
	private int directionID;
	private int routeID;
	private String serviceID;
	private String shapeID;
	private HashMap<Integer, StopTime> StopTimes = new HashMap<>();
	private String tripHeadSign;
	private String tripID;

	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}

	public void setDirectionID(int directionID) {
		this.directionID = directionID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public void setShapeID(String shapeID) {
		this.shapeID = shapeID;
	}

	public void setTripHeadSign(String tripHeadSign) {
		this.tripHeadSign = tripHeadSign;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public int getBlockID() {
		return blockID;
	}

	public int getDirectionID() {
		return directionID;
	}

	public int getRouteID() {
		return routeID;
	}

	public String getServiceID() {
		return serviceID;
	}

	public String getShapeID() {
		return shapeID;
	}

	public HashMap<Integer, StopTime> getStopTimes() {
		return StopTimes;
	}

	public String getTripHeadSign() {
		return tripHeadSign;
	}

	public String getTripID() {
		return tripID;
	}

	public Trip(int routeID, String serviceID, String tripID, String tripHeadSign, int directionID, int blockID, String shapeID) {
		this.blockID = blockID;
		this.directionID = directionID;
		this.routeID = routeID;
		this.serviceID = serviceID;
		this.shapeID = shapeID;
		this.tripHeadSign = tripHeadSign;
		this.tripID = tripID;
	}

	/**
	 * 
	 * @param newTrip
	 */
	public boolean update(Trip newTrip){
		return false;
	}

}