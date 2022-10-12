import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

/**
 * This class handles the methods from the GUI
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Controller {

    @FXML
     Button butt;

    protected HashMap<Integer, Stop> allStops = new HashMap<>();
    protected HashMap<String, Route> routes = new HashMap<>();
    protected HashMap<String, Trip> trips = new HashMap<>();

    /**
     * Creates Controller instance
     */
    public Controller() {

    }

    /**
     * Gets all the stops in a route by searching the routeID
     * This method has not been implemented
     * @param routeID
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> allStopsInRoute(int routeID) {
        return null;
    }

    /**
     * Finds the average speed of a trip if given the tripID
     * This method has not been implemented
     * @param tripID
     * @return int
     */
    public int avgSpeed(int tripID) {
        return 0;
    }

    /**
     * Changes the time of a Stop's Arrival/Departure time
     * This method has not been implemented
     * @return boolean
     */
    public boolean changeStopArivalDeparture() {
        return false;
    }

    /**
     * Changes the location of a stop given stopID
     * This method has not been implemented
     * @param latitude
     * @param longitude
     * @param stopID
     * @return boolean
     */
    public boolean changeStopLocation(int latitude, int longitude, int stopID) {
        return false;
    }

    /**
     * Displays the total distance of a route
     * This method has not been implemented
     * @param routeID
     * @return double
     */
    public double displayDistance(int routeID) {
        return 0;
    }

    /**
     * Exports the GTFS files to the desired place
     * This method has not been implemented
     * @return
     */
    public boolean export() {
        return false;
    }

    /**
     * Imports the GTFS files and calls helper methods to populate entity objects
     * @param listOfFiles
     */
    public boolean importFiles(ArrayList<File> listOfFiles) {
        List<File> routeFile = listOfFiles.stream()
                                          .filter(file -> file.getName().equals("routes.txt"))
                                          .toList();
        if (routeFile.size() > 0){
            importRoutes(routeFile.get(0));
        } else {
            System.out.println("No route file");
        }

        List<File> stopFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("stops.txt"))
                .toList();
        if (stopFile.size() > 0){
            importStops(stopFile.get(0));
        } else {
            System.out.println("No stop file");
        }

        List<File> tripFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("trips.txt"))
                .toList();
        if (stopFile.size() > 0){
            importTrips(tripFile.get(0));
        } else {
            System.out.println("No trip file");
        }

        List<File> stopTimesFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("stop_times.txt"))
                .toList();
        if (stopFile.size() > 0){
            importStopTimes(stopTimesFile.get(0));
        } else {
            System.out.println("No stop time file");
        }

        System.out.println("all valid files imported");

        /*for (File file: listOfFiles){
            if (file.getName().equals())
        }
        for (int i = 0; i < listOfFiles.size(); i++){

            ArrayList<String> lines = new ArrayList<>();


            try {
                scanner = new Scanner(listOfFiles.get(i));
                while (scanner.hasNextLine()){

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }*/
        return true;
    }

    /**
     * Populates the StopTimes
     * @param stopTimesFile
     */
    private void importStopTimes(File stopTimesFile) {
        int index = 0;
        try (Stream<String> lines = Files.lines(stopTimesFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
                    "stop_headsign,pickup_type,drop_off_type")){

                System.out.println("Unknown formatting encountered: StopTimes");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    StopTime stopTime = new StopTime(
                            reader.next(), reader.nextTime(), reader.nextTime(),
                            reader.nextInt(), reader.nextInt(), reader.nextInt(),
                            reader.nextInt(), reader.nextInt());

                    Trip trip = trips.get(stopTime.getTripID());
                    trip.getStopTimes().put(stopTime.getStopID(), stopTime);
                    reader.checkEndOfLine();
                } catch (CSVReader.EndOfStringException | NumberFormatException | ParseException e){
                    System.out.println("Line " + index + " (StopTimes) is not formatted correctly, skipping");
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Trips were imported");
        }
    }

    /**
     * Populates the Trips
     * @param tripFile
     */
    private void importTrips(File tripFile) {
        int index = 0;
        try (Stream<String> lines = Files.lines(tripFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id")){

                System.out.println("Unknown formatting encountered: Trips");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    Trip trip = new Trip(
                            reader.next(), reader.next(), reader.next(),
                            reader.next(), reader.nextInt(), reader.nextInt(),
                            reader.next());
                    // Add trip to corresponding route
                    if(!routes.containsKey(trip.getRouteID())){
                        System.out.println("Route " + trip.getRouteID()+ " was mentioned on line: "+index + " but not found");
                    } else {
                        routes.get(trip.getRouteID()).getTrips().put(trip.getTripID(), trip);
                    }
                    // Add trip to HashMap of all trips
                    trips.put(trip.getTripID(), trip);
                    reader.checkEndOfLine();
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Trips) is not formatted correctly, skipping");
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Trips were imported");
        }
    }

    /**
     * Populates the Stops
     * @param stopFile
     */
    private void importStops(File stopFile) {
        int index = 1;
        try (Stream<String> lines = Files.lines(stopFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon")){
                System.out.println("Unknown formatting encountered: Stops");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    Stop stop = new Stop(
                            reader.nextInt(), reader.next(), reader.next(),
                            reader.nextDouble(), reader.nextDouble());
                    allStops.put(stop.getStopID(), stop);
                    reader.checkEndOfLine();
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Stops) is not formatted correctly, skipping");
                    System.out.println(e.getLocalizedMessage());
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Stops were imported");
        }
    }

    /**
     * Populates the routes
     * @param routeFile
     */
    private void importRoutes(File routeFile) {
        int index = 1;
        try (Stream<String> lines = Files.lines(routeFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("route_id,agency_id,route_short_name,route_long_name," +
                    "route_desc,route_type,route_url,route_color,route_text_color")){
                System.out.println("Unknown formatting encountered: Routes");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    Route route = new Route(
                            reader.next(), reader.next(), reader.next(),
                            reader.next(), reader.next(), reader.nextInt(),
                            reader.nextInt(), reader.nextInt(), reader.nextInt());
                    routes.put(route.getRouteID(), route);
                    reader.checkEndOfLine();
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Routes) is not formatted correctly, skipping");
                }

            }
        } catch (IOException e){
            System.out.println("Error finding file, no Routes were imported");
        }

    }


    /**
     * Allows user to select multiple files
     * @param actionEvent
     */
    @FXML
    public void importHelper(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile;
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GSTF Files", "*.txt"));
        List<File> f = fileChooser.showOpenMultipleDialog(null);
        ArrayList<File> files = new ArrayList<>();
        for(File file : f){
            files.add(file);
            System.out.println(files);
        }
        importFiles(files);
        //epic
    }


     /**
     * Finds the next trip at a certain stop given the time
     * This method has not been implemented
     * @param stopID
     * @param currentTime
     */
    public LinkedList<Integer> nextTripAtStop(int stopID, int currentTime) {
        return null;
    }

    /**
     * Plots the current trajectory of the bus
     * This method has not been implemented
     * @param tripID
     * @return boolean
     */
    public boolean plotBus(int tripID) {
        return false;
    }

    /**
     * Plots the stops on a given route
     * This method has not been implemented
     * @param routeID
     * @return boolean
     */
    public boolean plotStops(int routeID) {
        return false;
    }

    /**
     * finds all routes that contain a certian stop
     * This method has not been implemented
     * @param stopID
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> routesContainingStop(int stopID) {
        return null;
    }

    /**
     * finds all the future trips given a routeID
     * This method has not been implemented
     * @param routeID
     * @return LinkedList<Integer>
     */
    public LinkedList<Integer> tripsInFuture(int routeID) {
        return null;
    }

    /**
     * Finds all the trips at a stop
     * This method has not been implemented
     * @param stopID
     * @return int
     */
    public int tripsPerStop(int stopID) {
        return 0;
    }

    /**
     * Updates all stoptimes in a trip
     * This method has not been implemented
     * @param stopID
     * @return boolean
     */
    public boolean updateAllStopTimes(int stopID) {
        return false;
    }

    /**
     * Updates a group of stoptimes
     * This method has not been implemented
     * @param stopTime
     * @param attribute
     * @param data
     * @return boolean
     */
    public boolean updateGroupStopTime(StopTime stopTime, String attribute, int data) {
        return false;
    }

    /**
     * Updates multiple stoptimes given an List of stoptimes
     * This method has not been implemented
     * @param stopTimes
     * @return boolean
     */
    public boolean updateMultipleStopTimes(ArrayList<StopTime> stopTimes) {
        return false;
    }

    /**
     * Updates a route, certain attributes of the route may be different
     * This method has not been implemented
     * @param route
     * @return boolean
     */
    public boolean updateRoute(Route route) {
        return false;
    }

    /**
     * Updates a stop, certain attributes of the stops may be different
     * This method has not been implemented
     * @param stop
     * @return boolean
     */
    public boolean updateStop(Stop stop) {
        return false;
    }

    /**
     * Updates a StopTime, certain attributes of the StopTime may be different
     * This method has not been implemented
     * @param stopTime
     * @return boolean
     */
    public boolean updateStopTime(StopTime stopTime) {
        return false;
    }

    /**
     * Updates a Trip, certain attributes of the Trip may be different
     * This method has not been implemented
     * @param trip
     * @return boolean
     */
    public boolean updateTrip(Trip trip) {
        return false;
    }

}