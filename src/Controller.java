import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class handles the methods from the GUI
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Controller {

    @FXML
    Button butt;

    @FXML
    Button importButton;

    @FXML
    Button exportButton;

    @FXML
    Button stopIdButton;

    @FXML
    Button routeIdButton;

    @FXML
    Button tripIdButton;

    @FXML
    TextField searchBar;

    @FXML
    Label searchBarLabel;

    @FXML
    Stage tripDisplay;

    @FXML
    Stage routeDisplay;

    @FXML
    Stage stopDisplay;

    StopController stopController;

    protected HashMap<String, Stop> allStops = new HashMap<>();
    protected HashMap<String, Route> routes = new HashMap<>();
    protected HashMap<String, Trip> trips = new HashMap<>();

    /**
     * Creates Controller instance
     */
    public Controller() {

    }

    /**
     * gets the text from the search bar
     * @return String id
     */
    public String getId() {
        return searchBar.getText();
    }

    public void setStopController(StopController stop){
        stopController = stop;
    }

    /**
     * Show the stop stage and sets all information inside it
     * @param actionevent when button is clicked
     * @author Matt Wehman
     */
    @FXML
    public void generateStopIdInterface(ActionEvent actionevent){
        String stopId = getId();
        stopController.setTripsText(String.valueOf(tripsPerStop(stopId)));
        stopController.setStopID(stopId);
        stopDisplay.show();
    }
    /**
     * Shows the route stage and sets all information inside it
     * @param actionevent when button is clicked
     */
    @FXML
    public void generateRouteIdInterface(ActionEvent actionevent){
        routeDisplay.show();
    }
    /**
     * Shows the trip stage and sets all information inside it
     * @param actionevent when button is clicked
     */
    @FXML
    public void generateTripIdInterface(ActionEvent actionevent){
        tripDisplay.show();
    }

    /**
     * Sets the route stage
     * @param stage stage to be set
     */
    protected void setRouteStage(Stage stage){
        this.routeDisplay = stage;
    }
    /**
     * Sets the trip stage
     * @param stage stage to be set
     */
    protected void setTripStage(Stage stage){
        this.tripDisplay = stage;
    }
    /**
     * Sets the stop stage
     * @param stage stage to be set
     */
    protected void setStopStage(Stage stage){
        this.stopDisplay = stage;
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
    public static int avgSpeed(String tripID) {
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save Directory");
        File file = fileChooser.showSaveDialog(null);
        boolean bool = file.mkdir();
        File routeExport = exportRoutes(file.toPath());
        File stopExport = exportStops(file.toPath());
        File tripExport = exportTrips(file.toPath());
        File stopTimeExport = exportStopTimes(file.toPath());

    }

    /**
     * Exports the GTFS files to the desired place
     * This method has not been implemented
     * @return route gtfs file
     */
    public File exportRoutes(java.nio.file.Path path) {
        File routeFile = new File(path + "/routes.txt");
        FileWriter writer = null;
        Set<Map.Entry<String, Route>> routeSet = routes.entrySet();
        Iterator<Map.Entry<String, Route>> it = routeSet.iterator();
        try {
            writer = new FileWriter(routeFile);
            writer.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
            while(it.hasNext()) {
                writer.write("\n" + it.next().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Route file could not be found");
        }
        return routeFile;
    }

    public File exportStops(java.nio.file.Path path) {
        File stopFile = new File(path + "/stops.txt");
        FileWriter writer = null;
        Set<Map.Entry<String, Stop>> stopSet = allStops.entrySet();
        Iterator<Map.Entry<String, Stop>> it = stopSet.iterator();
        try {
            writer = new FileWriter(stopFile);
            writer.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
            while(it.hasNext()) {
                writer.write("\n" + it.next().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Stop file could not be found");
        }

        return stopFile;
    }

    public File exportTrips(java.nio.file.Path path) {
        File tripFile = new File(path + "/trips.txt");
        FileWriter writer = null;
        Set<Map.Entry<String, Trip>> tripSet = trips.entrySet();
        Iterator<Map.Entry<String, Trip>> it = tripSet.iterator();
        try {
            writer = new FileWriter(tripFile);
            writer.write("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
            while(it.hasNext()) {
                writer.write("\n" + it.next().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Trip file could not be found");
        }

        return tripFile;
    }

    public File exportStopTimes(Path path) {
        File stopTimeFile = new File(path + "/stop_times.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(stopTimeFile);
            writer.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
            for(Map.Entry<String, Trip> mapEntry : trips.entrySet()) {
                Trip trip = mapEntry.getValue();
                for(Map.Entry<String, StopTime> mapEntry2 : trip.getStopTimes().entrySet()) {
                    StopTime stopTime = mapEntry2.getValue();
                    writer.write("\n" + stopTime.toString());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("stopTime file could not be found");
        }


        return stopTimeFile;
    }

    /**
     * Imports the GTFS files and calls helper methods to populate entity objects
     * @param listOfFiles directories of the files to import
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
     * @author Ian Czerkis
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
        trip.checkRequired();
            return trip;
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e){
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
                    allStops.put(stop.getStopID(), stop);
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
            if(lat == -1 || lon == -1 || (lat < -90.00 || lat > 90.00) ||
                    (lon < -180.00 || lon > 180.00)){
                throw new NumberFormatException("empty");
            }

            stop = new Stop(stopId, name, description, lat, lon);
            reader.checkEndOfLine();
            stop.checkRequired();
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e){
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
        return header.equals("route_id,agency_id,route_short_name,route_long_name," +
                "route_desc,route_type,route_url,route_color,route_text_color");
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
                    reader.next(), reader.next(), reader.next(),
                    reader.next(), reader.next(), reader.next());
            reader.checkEndOfLine();
            route.checkRequired();
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e){
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
    public LinkedList<Integer> nextTripAtStop(String stopID, Time currentTime) {
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
     * searches all routes to see if they contain the specified stopID
     * @param stopID the stop ID to search
     * @return ArrayList<String> the list of routeID that contain
     */
    public ArrayList<String> routesContainingStop(String stopID) {
        ArrayList<String> routesContaining = new ArrayList<>();
        for(Map.Entry<String, Trip> mapEntry: trips.entrySet()){
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)){
                if(!routesContaining.contains(trip.getRouteID())) {
                    routesContaining.add(trip.getRouteID());
                }
            }
        }
        return routesContaining;
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
     * Counts the number trips that use the specified stop
     * @param stopID the stopID to search for
     * @return the number of occurances of trips containing that stop
     */
    public int tripsPerStop(String stopID) {
        int counter = 0;
        for(Map.Entry<String, Trip> mapEntry: trips.entrySet()){
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)){
                counter++;
            }
        }
        return counter;
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

    /**
     * Returns all routes that contain a certain stop
     * @param stopId
     * @return Arraylist of routes
     */


}