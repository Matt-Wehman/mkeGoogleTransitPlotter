import java.sql.Time;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class StopTime {

    private Time arrivalTime;
    private Time departureTime;
    private int drop_off_type;
    private int pickup_type;
    private int stopHeadsign;
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

    public int getDrop_off_type() {
        return drop_off_type;
    }

    public void setDrop_off_type(int drop_off_type) {
        this.drop_off_type = drop_off_type;
    }

    public int getPickup_type() {
        return pickup_type;
    }

    public void setPickup_type(int pickup_type) {
        this.pickup_type = pickup_type;
    }

    public int getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(int stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
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

    public StopTime(Time arrivalTime, Time departureTime, int drop_off_type, int pickup_type, int stopHeadsign, int stopID, int stopSequence, String tripID) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.drop_off_type = drop_off_type;
        this.pickup_type = pickup_type;
        this.stopHeadsign = stopHeadsign;
        this.stopID = stopID;
        this.stopSequence = stopSequence;
        this.tripID = tripID;
    }

    /**
     * @param newStopTime
     */
    public boolean update(StopTime newStopTime) {
        return false;
    }

}