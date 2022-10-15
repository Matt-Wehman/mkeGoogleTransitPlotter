import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileWriter;
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
     Button importButton;

    @FXML
    Button exportButton;

    protected HashMap<String, Stop> allStops = new HashMap<>();
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
    public boolean changeStopArrivalDeparture() {
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


    public void exportHelper(ActionEvent actionEvent) {
        File routeExport = exportRoutes();
        File stopExport = exportStops();
        File tripExport = exportTrips();

    }

    /**
     * Exports the GTFS files to the desired place
     * This method has not been implemented
     * @return route gtfs file
     */
    public File exportRoutes() {
        File routeFile = new File("routeExport.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(routeFile);
        } catch (IOException e) {
            System.out.println("Route file could not be found");
        }

        Set<Map.Entry<String, Route>> routeSet = routes.entrySet();
        Iterator<Map.Entry<String, Route>> it = routeSet.iterator();
        while(it.hasNext()) {
            try {
                writer.write(it.next().toString());
            } catch (IOException e) {
                System.out.println("Could not write route export file");
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not close File Writer");
        }
        return routeFile;
    }

    public File exportStops() {
        File stopFile = new File("stopExport.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(stopFile);
        } catch (IOException e) {
            System.out.println("Stop file could not be found");
        }

        Set<Map.Entry<String, Stop>> stopSet = allStops.entrySet();
        Iterator<Map.Entry<String, Stop>> it = stopSet.iterator();
        while(it.hasNext()) {
            try {
                assert writer != null;
                writer.write(it.next().toString());
            } catch (IOException e) {
                System.out.println("Could not write stop export file");
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not close File Writer");
        }

        return stopFile;
    }

    public File exportTrips() {
        File tripFile = new File("teipExport.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(tripFile);
        } catch (IOException e) {
            System.out.println("Trip file could not be found");
        }

        Set<Map.Entry<String, Trip>> tripSet = trips.entrySet();
        Iterator<Map.Entry<String, Trip>> it = tripSet.iterator();
        while(it.hasNext()) {
            try {
                assert writer != null;
                writer.write(it.next().toString());
            } catch (IOException e) {
                System.out.println("Could not write trip export file");
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not close File Writer");
        }
        return tripFile;
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
        return true;
    }

    /**
     * Populates the StopTimes in each Trip
     * @param stopTimesFile the File to read from
     * @author Ian Czerkis
     */
    private void importStopTimes(File stopTimesFile) {
        try (Stream<String> lines = Files.lines(stopTimesFile.toPath())) {
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (validateFirstStopTimeLine(firstLine)) {
                while (it.hasNext()) {
                    StopTime stopTime = validateStopTimeLine(it.next());
                    if (!Objects.equals(stopTime, null)) {
                        Trip trip = trips.get(stopTime.getTripID());
                        trip.getStopTimes().put(stopTime.getStopID(), stopTime);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Error finding Stop Time File");
        }
    }

    /**
     * creates a StopTime from a single line in the StopTime file
     * @param line the line to parse
     * @return the StopTime object if the file is valid or null if the file is invalid
     * @author Ian Czerkis
     */
    public static StopTime validateStopTimeLine(String line) {
        StopTime stopTime;
        try {
            CSVReader reader = new CSVReader(line);
            stopTime = new StopTime(
                    reader.next(), reader.nextTime(), reader.nextTime(),
                    reader.next(), reader.nextInt(), reader.next(),
                    reader.nextInt(), reader.nextInt());
                    reader.next(), reader.next(), reader.next(),
                    reader.next(), reader.next());

            reader.checkEndOfLine();
            stopTime.checkRequired();
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException
                 | NumberFormatException | ParseException e) {
            return null;
        }
        return stopTime;
    }

    /**
     * validates the first line of the StopTime file
     * @param firstLine the line to parse
     * @return True if the line is valid False if it is invalid
     * @auther Ian Czerkis
     */
    public static boolean validateFirstStopTimeLine(String firstLine) {
        return firstLine.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
                "stop_headsign,pickup_type,drop_off_type");
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
            if (!validateTripHeader(firstLine)){
                System.out.println("Unknown formatting encountered: Trips");
            }
            while (it.hasNext()) {
                String tripLine = it.next();
                Trip trip = validateTripLines(tripLine);
                // Add trip to corresponding route
                if(!Objects.equals(null, trip)) {
                if(!routes.containsKey(trip.getRouteID())){
                    System.out.println("Route " + trip.getRouteID()+ " was mentioned on line: "+ index + " but not found");
                } else {
                    routes.get(trip.getRouteID()).getTrips().put(trip.getTripID(), trip);
                }
                    trips.put(trip.getTripID(), trip);
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Trips were imported");
        }
    }

    /**
     * Checks if Trip header is valid against known valid header.
     * @param header trip header
     * @return boolean
     * @Author Matt Wehman
     */
    public static boolean validateTripHeader(String header){
        return header.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
    }

    /**
     * Validates each line of trip file.
     * @param tripLine
     * @return trip of no exceptions are thrown and null if line is invalid
     * @Author Matthew Wehman
     */
    public static Trip validateTripLines(String tripLine) {
        try {
        CSVReader reader = new CSVReader(tripLine);
        Trip trip = new Trip(
                reader.next(), reader.next(), reader.next(),
                reader.next(), Integer.parseInt(reader.next()), Integer.parseInt(reader.next()),
                reader.next());
        reader.checkEndOfLine();
            return trip;
        } catch (CSVReader.EndOfStringException | NumberFormatException e){
            return null;
        }
    }


    /**
     * Populates the Stops
     * @param stopFile
     */
    private void importStops(File stopFile) {
        try (Stream<String> lines = Files.lines(stopFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!validateStopHeader(firstLine)){
                System.out.println("Unknown formatting encountered: Stops");
            }
            while (it.hasNext()){
                String stopLine = it.next();
                Stop stop = validateLinesInStop(stopLine);
                if(!Objects.equals(null, stop)) {
                    allStops.put(stop.getStopID() + "", stop);
                }

            }
        } catch (IOException e){
            System.out.println("Error finding file, no Stops were imported");
        }
    }

    /**
     * This method validates the Stop headerline and makes sure it follows the correct syntax
     * @param firstLine
     * @return boolean
     * @author Patrick McDonald
     */
    public static boolean validateStopHeader(String firstLine){
        return firstLine.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
    }

    /**
     * This method validates each individual Stop and makes sure it is formatted correctly
     * or else it returns a null
     * @param stopLine
     * @return stop
     * @author Patrick McDonald
     */
    public static Stop validateLinesInStop(String stopLine) {
        CSVReader reader = new CSVReader(stopLine);
        Stop stop;
        try {
            String stopId = reader.next();
            String name = reader.next();
            String description = reader.next();
            double lat = reader.nextDouble();
            double lon = reader.nextDouble();
            if(lat == -1 || lon == -1){
                throw new NumberFormatException("empty");
            }

            stop = new Stop(stopId, name, description, lat, lon);
            reader.checkEndOfLine();
        } catch (CSVReader.EndOfStringException | NumberFormatException e){
//            System.out.println(e.getLocalizedMessage());
//            System.out.println(e.getMessage());
            return null;
        }
        return stop;
    }

    /**
     * Populates the routes
     * @param routeFile the file that contains the route lines
     */
    private void importRoutes(File routeFile) {
        try (Stream<String> lines = Files.lines(routeFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if(validateRouteHeader(firstLine)){
                while (it.hasNext()){
                    Route route = validateRouteLine(it.next());
                    if(!Objects.equals(route, null)){
                        routes.put(route.getRouteID(), route);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Routes were imported");
        }

    }


    /**
     * validates that the header for the route file is formatted correctly
     * @param header string header
     * @return true if header is valid, false if not
     * @author Chrstian Basso
     */
    public static boolean validateRouteHeader(String header) {
        if (!header.equals("route_id,agency_id,route_short_name,route_long_name," +
                "route_desc,route_type,route_url,route_color,route_text_color")){
            System.out.println("Unknown formatting encountered: Routes");
            return false;
        } else {
            return true;
        }
    }


    /**
     * Validates line that represents a route object
     * @param line string of parameters
     * @return the object created from the parameters, or null if an exception is thrown
     * @author Christian B
     */
    public static Route validateRouteLine(String line) {
        Route route;
        CSVReader reader = new CSVReader(line);
        try {
            route = new Route(
                    reader.next(), reader.next(), reader.next(),
                    reader.next(), reader.next(), reader.nextInt(),
                    reader.nextInt(), reader.next(), reader.nextInt());
            if(route.getRouteID().equals("") || route.getRouteColor().equals("")) {
                throw new IllegalArgumentException();
            }
            reader.checkEndOfLine();
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException
                | NumberFormatException e){
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Route must have a route_id and a route_color");
            return null;
        }
        return route;
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