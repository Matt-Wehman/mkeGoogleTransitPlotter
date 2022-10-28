import java.sql.Time;
import java.text.ParseException;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:53 PM
 */
public class StopTime {

    private Time arrivalTime;
    private Time departureTime;
    private String arrivalTimeString;
    private String departureTimeString;
    private String dropOffType;
    private String pickupType;
    private String stopHeadSign;
    private String stopID;
    private String stopSequence;
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

    public String getDropOffType() {
        return dropOffType;
    }

    public void setDropOfType(String dropOffType) {
        this.dropOffType = dropOffType;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public String getStopHeadSign() {
        return stopHeadSign;
    }

    public void setStopHeadSign(String stopHeadSign) {
        this.stopHeadSign = stopHeadSign;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(String stopSequence) {
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
     * @param arrivalTimeString
     * @param departureTimeString
     * @param stopID
     * @param stopSequence
     * @param stopHeadSign
     * @param pickupType
     * @param dropOffType
     */
    public StopTime(String tripID, String arrivalTimeString, String departureTimeString, String stopID, String stopSequence, String stopHeadSign, String pickupType, String dropOffType) throws CSVReader.EndOfStringException, ParseException {
        this.tripID = tripID;
        this.arrivalTimeString = arrivalTimeString;
        this.departureTimeString = departureTimeString;
        this.stopID = stopID;
        this.stopSequence = stopSequence;
        this.stopHeadSign = stopHeadSign;
        this.pickupType = pickupType;
        this.dropOffType = dropOffType;
        this.arrivalTime = new CSVReader(arrivalTimeString).nextTime();
        this.departureTime = new CSVReader(departureTimeString).nextTime();
    }

    @Override
    public String toString(){
        return tripID + ',' + arrivalTimeString + ',' + departureTimeString + ','
                + stopID + ',' + stopSequence + ',' + stopHeadSign  + ','
                + pickupType  + ',' + dropOffType;
    }

    /**
     * Replaces StopTime object in a Trip with a new StopTime object. This essentially updates it.
     * This method has not been implemented yet
     * @param newStopTime
     */
    public boolean update(StopTime newStopTime) {
        return false;
    }


    /**
     * checks that all required fields are filled
     * @throws CSVReader.MissingRequiredFieldException if any required field is empty
     */
    public void checkRequired() throws CSVReader.MissingRequiredFieldException {
        if (tripID.isEmpty() || arrivalTime.toString().isEmpty() ||
                departureTime.toString().isEmpty() || stopID.isEmpty()
                || stopSequence.isEmpty()){
            throw new CSVReader.MissingRequiredFieldException("A required field is missing");
        }
    }
}

