import java.util.HashMap;

/**
 * This is the Trip class. Most notably, it has a HashMap of StopTimes.
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class Trip {

    private int blockID;
    private int directionID;
    private String routeID;
    private String serviceID;
    private String shapeID;
    private final HashMap<String, StopTime> stopTimes = new HashMap<>();
    private String tripHeadSign;
    private String tripID;

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public void setDirectionID(int directionID) {
        this.directionID = directionID;
    }

    public void setRouteID(String routeID) {
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

    public String getRouteID() {
        return routeID.replaceAll("\\s", "");
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getShapeID() {
        return shapeID;
    }

    public HashMap<String, StopTime> getStopTimes() {
        return stopTimes;
    }

    public String getTripHeadSign() {
        return tripHeadSign;
    }

    public String getTripID() {
        return tripID;
    }

    /**
     * Creates an instance of Trip Object
     * @param routeID
     * @param serviceID
     * @param tripID
     * @param tripHeadSign
     * @param directionID
     * @param blockID
     * @param shapeID
     */
    public Trip(String routeID, String serviceID, String tripID, String tripHeadSign, int directionID, int blockID, String shapeID) {
        this.blockID = blockID;
        this.directionID = directionID;
        this.routeID = routeID;
        this.serviceID = serviceID;
        this.shapeID = shapeID;
        this.tripHeadSign = tripHeadSign;
        this.tripID = tripID;
    }

    /**
     * Takes in a new trip object and updates the old one
     * This method has not been implemented yet
     * @param newTrip
     * @return boolean
     */
    public boolean update(Trip newTrip) {
        return false;
    }

    /**
     * checks that all required fields are filled
     * @throws CSVReader.MissingRequiredFieldException if a required field is empty
     */
    public void checkRequired() throws CSVReader.MissingRequiredFieldException {
        if (routeID.isEmpty() | serviceID.isEmpty() | tripID.isEmpty()){
            throw new CSVReader.MissingRequiredFieldException("A required field is empty");
        }
    }

    /**
     * Makes trip into a string using trip's variables
     * @return string
     * @author wehman
     */
    public String toString(){
        String string = routeID + "," + serviceID + "," + tripID + "," + tripHeadSign + "," + directionID + "," + blockID + ","+ shapeID;
        return string;
    }

}