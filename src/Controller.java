import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Controller {

    private HashMap<Integer, Stop> allStops;
    private HashMap<Integer, Route> routes;

    public Controller() {

    }

    public void finalize() throws Throwable {

    }

    /**
     * @param routeID
     */
    public ArrayList<Integer> allStopsInRoute(int routeID) {
        return null;
    }

    /**
     * @param tripID
     */
    public int avgSpeed(int tripID) {
        return 0;
    }

    public boolean changeStopArivalDeparture() {
        return false;
    }

    /**
     * @param latitude
     * @param longitude
     * @param stopID
     */
    public boolean changeStopLocation(int latitude, int longitude, int stopID) {
        return false;
    }

    /**
     * @param routeID
     */
    public double displayDistance(int routeID) {
        return 0;
    }

    public boolean export() {
        return false;
    }

    /**
     * @param listOfFiles
     */
    public boolean import_files(ArrayList<File> listOfFiles) {
        Scanner scanner = null;
        for (int i = 0; i < listOfFiles.size(); i++){
            ArrayList<String> lines = new ArrayList<>();
            try {
                scanner = new Scanner(listOfFiles.get(i));
                while (scanner.hasNextLine()){

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * @param stopID
     * @param currentTime
     */
    public LinkedList<Integer> nextTripAtStop(int stopID, int currentTime) {
        return null;
    }

    /**
     * @param tripID
     */
    public boolean plotBus(int tripID) {
        return false;
    }

    /**
     * @param routeID
     */
    public boolean plotStops(int routeID) {
        return false;
    }

    /**
     * @param stopID
     */
    public ArrayList<Integer> routesContainingStop(int stopID) {
        return null;
    }

    /**
     * @param routeID
     */
    public LinkedList<Integer> tripsInFuture(int routeID) {
        return null;
    }

    /**
     * @param stopID
     */
    public int tripsPerStop(int stopID) {
        return 0;
    }

    /**
     * @param stopID
     */
    public boolean updateAllStopTimes(int stopID) {
        return false;
    }

    /**
     * @param stopTime
     * @param attribute
     * @param data
     */
    public boolean updateGroupStopTime(StopTime stopTime, String attribute, int data) {
        return false;
    }

    /**
     * @param stopTimes
     */
    public boolean updateMultipleStopTimes(ArrayList<StopTime> stopTimes) {
        return false;
    }

    /**
     * @param route
     */
    public boolean updateRoute(Route route) {
        return false;
    }

    /**
     * @param stop
     */
    public boolean updateStop(Stop stop) {
        return false;
    }

    /**
     * @param stopTime
     */
    public boolean updateStopTime(StopTime stopTime) {
        return false;
    }

    /**
     * @param trip
     */
    public boolean updateTrip(Trip trip) {
        return false;
    }

}