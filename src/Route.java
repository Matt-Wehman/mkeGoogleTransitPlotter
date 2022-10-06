import java.util.HashMap;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Route {

    private String agencyID;
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

    public Route(int routeID, String agencyID, int routeShortName, String routeLongName, String routeDesc, int routeType, int routeURL, int routeColor, int routeTextColor) {
        try {
            this.agencyID = agencyID;
            this.routeColor = routeColor;
            this.routeDesc = routeDesc;
            this.routeID = routeID;
            this.routeLongName = routeLongName;
            this.routeShortName = routeShortName;
            this.routeTextColor = routeTextColor;
            this.routeType = routeType;
            this.routeURL = routeURL;
        } catch (NumberFormatException e){
            throw new RuntimeException("File is not formatted correctly");
        }
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public void setRouteColor(int routeColor) {
        this.routeColor = routeColor;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public void setRouteShortName(int routeShortName) {
        this.routeShortName = routeShortName;
    }

    public void setRouteTextColor(int routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public void setRouteURL(int routeURL) {
        this.routeURL = routeURL;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public int getRouteColor() {
        return routeColor;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public int getRouteID() {
        return routeID;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public int getRouteShortName() {
        return routeShortName;
    }

    public int getRouteTextColor() {
        return routeTextColor;
    }

    public int getRouteType() {
        return routeType;
    }

    public int getRouteURL() {
        return routeURL;
    }

    public HashMap<Integer, Stop> getStops() {
        return stops;
    }

    public HashMap<Integer, Trip> getTrips() {
        return trips;
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