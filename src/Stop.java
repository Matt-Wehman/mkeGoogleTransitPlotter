
/**
 * This is the stop class and represents the places at which the buses stop
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Stop {

    private String stopDesc;
    private String stopID;
    private double stopLat;
    private double stopLong;
    private String stopName;

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public double getStopLat() {
        return stopLat;
    }

    public void setStopLat(int stopLat) {
        this.stopLat = stopLat;
    }

    public double getStopLong() {
        return stopLong;
    }

    public void setStopLong(int stopLong) {
        this.stopLong = stopLong;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

	/**
	 * Creates an instance of the Stop Object
	 * @param stopID
	 * @param stopName
	 * @param stopDesc
	 * @param stopLat
	 * @param stopLong
	 */
    public Stop(String stopID, String stopName, String stopDesc, double stopLat, double stopLong) {
        try {
            this.stopDesc = stopDesc;
            this.stopID = stopID;
            this.stopLat = stopLat;
            this.stopLong = stopLong;
            this.stopName = stopName;
        } catch (NumberFormatException e) {
            throw new RuntimeException("File is not formatted correctly");
        }
    }

    /**
	 * Changes the Longitude and Latitude of a Stop
	 * This method has not been implemented yet
     * @param longitude
     * @param latitude
	 * @return boolean
     */
    public boolean changeLocation(int longitude, int latitude) {
        return false;
    }

    /**
	 * Takes in a new Stop object in place of the old Stop, this will update the certain
	 * attributes that need tobe updated
	 * This method has not been implemented yet
     * @param newStop
	 * @return boolean
     */
    public boolean update(Stop newStop) {
        return false;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "stopID='" + stopID +
                ", stopName=" + stopName +
                ", stopDesc=" + stopDesc +
                ", stopLat=" + stopLat +
                ", stopLong='" + stopLong +
                '}';
    }
}