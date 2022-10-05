import java.util.HashMap;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Route {

    private String agencyID;
    private int longName;
    private int routeColor;
    private String routeDesc;
    private int routeID;
    private String routeLongName;
    private int routeShortName;
    private int routeTextColor;
    private int routeType;
    private int routeURL;
    private HashMap<Integer, Stop> stops;
    private HashMap<Integer, Trip> trips;
    public Controller m_Controller;

    public Route() {

    }

    public void finalize() throws Throwable {

    }

    private int displayDist() {
        return 0;
    }

    /**
     * @param newRoute
     */
    public boolean update(Route newRoute) {
        return false;
    }

}