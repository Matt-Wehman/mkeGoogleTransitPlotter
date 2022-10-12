import java.sql.Time;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class StopTime {

    private Time arrivalTime;
    private Time departureTime;
    private int dropOffType;
    private int pickupType;
    private String stopHeadSign;
    private int stopID;
    private int stopSequence;
    private String tripID;

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public int getDropOffType() {
        return dropOffType;
    }

    public void setDropOfType(int dropOffType) {
        this.dropOffType = dropOffType;
    }

    public int getPickupType() {
        return pickupType;
    }

    public void setPickupType(int pickupType) {
        this.pickupType = pickupType;
    }

    public String getStopHeadSign() {
        return stopHeadSign;
    }

    public void setStopHeadSign(String stopHeadSign) {
        this.stopHeadSign = stopHeadSign;
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    /**
     * Creates an instance of a StopTime Object
     * @param tripID
     * @param arrivalTime
     * @param departureTime
     * @param stopID
     * @param stopSequence
     * @param stopHeadSign
     * @param pickupType
     * @param dropOffType
     */
    public StopTime(String tripID, Time arrivalTime, Time departureTime, int stopID, int stopSequence, String stopHeadSign, int pickupType, int dropOffType) {
        this.tripID = tripID;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopID = stopID;
        this.stopSequence = stopSequence;
        this.stopHeadSign = stopHeadSign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;




    }

    /**
     * Replaces StopTime object in a Trip with a new StopTime object. This essentially updates it.
     * This method has not been implemented yet
     * @param newStopTime
     */
    public boolean update(StopTime newStopTime) {
        return false;
    }

}