import java.util.HashMap;

/**
 * This is the Route class and it is the path which a bus will take
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Route {

    private String agencyID;
    private String routeColor;
    private String routeDesc;
    private String routeID;
    private String routeLongName;
    private String routeShortName;
    private int routeTextColor;
    private int routeType;
    private int routeURL;
    private HashMap<Integer, Stop> stops = new HashMap<>();
    private HashMap<String, Trip> trips = new HashMap<>();

    /**
     * Creates an instance of a Route
     * @param routeID
     * @param agencyID
     * @param routeShortName
     * @param routeLongName
     * @param routeDesc
     * @param routeType
     * @param routeURL
     * @param routeColor
     * @param routeTextColor
     */
    public Route(String routeID, String agencyID, String routeShortName, String routeLongName, String routeDesc, int routeType, int routeURL, String routeColor, int routeTextColor) {
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

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public void setRouteShortName(String routeShortName) {
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

    public String getRouteColor() {
        return routeColor;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public String getRouteID() {
        return routeID;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public String getRouteShortName() {
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

    public HashMap<String, Trip> getTrips() {
        return trips;
    }

    private int displayDist() {
        return 0;
    }

    /**
     * This takes in a new Route in place of the old one
     * This method has not been implemented yet
     * @param newRoute
     * @return boolean
     */
    public boolean update(Route newRoute) {
        return false;
    }


    public boolean equals(Route r) {
        return
                this.routeID.equals(r.routeID) &&
                        this.routeColor.equals(r.routeColor) &&
                        this.routeDesc.equals((r.routeDesc)) &&
                        this.agencyID.equals(r.agencyID) &&
                        this.routeLongName.equals(r.routeLongName) &&
                        this.routeShortName.equals(r.routeShortName) &&
                        this.routeURL == r.routeURL &&
                        this.routeType == r.routeType;

    }

    @Override
    public String toString() {
        return "Route{" +
                "agencyID='" + agencyID + '\'' +
                ", routeColor=" + routeColor +
                ", routeDesc='" + routeDesc + '\'' +
                ", routeID='" + routeID + '\'' +
                ", routeLongName='" + routeLongName + '\'' +
                ", routeShortName='" + routeShortName + '\'' +
                ", routeTextColor=" + routeTextColor +
                ", routeType=" + routeType +
                ", routeURL=" + routeURL +
                ", stops=" + stops +
                ", trips=" + trips +
                '}';
    }
}