

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
	public Trip m_Trip;
	public Stop m_Stop;

	public StopTime(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param newStopTime
	 */
	public boolean update(StopTime newStopTime){
		return false;
	}

}