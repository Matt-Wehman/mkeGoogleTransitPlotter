

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:07:09 PM
 */
public class Stop {

	private String stopDesc;
	private int stopID;
	private int stopLat;
	private int stopLong;
	private String stopName;
	public Route m_Route;
	public Controller m_Controller;

	public Stop(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param long
	 * @param lat
	 */
	public boolean changeLocation(int long, int lat){
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