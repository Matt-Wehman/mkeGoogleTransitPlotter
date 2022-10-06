

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Stop {

	private String stopDesc;
	private int stopID;
	private int stopLat;
	private int stopLong;
	private String stopName;

	public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public int getStopID() {
		return stopID;
	}

	public void setStopID(int stopID) {
		this.stopID = stopID;
	}

	public int getStopLat() {
		return stopLat;
	}

	public void setStopLat(int stopLat) {
		this.stopLat = stopLat;
	}

	public int getStopLong() {
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

	public Stop(int stopID, String stopName, String stopDesc, int stopLat, int stopLong) {
		try {
			this.stopDesc = stopDesc;
			this.stopID = stopID;
			this.stopLat = stopLat;
			this.stopLong = stopLong;
			this.stopName = stopName;
		} catch (NumberFormatException e){
			throw new RuntimeException("File is not formatted correctly");
		}
	}

	/**
	 * 
	 * @param longitude
	 * @param latitude
	 */
	public boolean changeLocation(int longitude, int latitude){
		return false;
	}

	/**
	 * 
	 * @param newStop
	 */
	public boolean update(Stop newStop){
		return false;
	}

}