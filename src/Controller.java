/*
 * Course: SE 2030 - 041
 * Fall 22-23 test
 * GTFS Project
 * Created by: Christian Basso, Ian Czerkis, Matt Wehman, Patrick McDonald.
 * Created on: 09/10/22
 */

import java.awt.*;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class handles the methods from the GUI
 *
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

    public HashMap<String, Trip> getT(){
        return trips;
    }



    /**
     * gets the text from the search bar
     *
     * @return String id
     */
    public String getId() {
        return searchBar.getText();
    }

    public void setStopController(StopController stop) {
        stopController = stop;
    }

    /**
     * Show the stop stage and sets all information inside it
     *
     * @param actionevent when button is clicked
     * @author Matt Wehman
     */
    @FXML
    public void generateStopIdInterface(ActionEvent actionevent) {
        String stopId = getId();
        stopController.setTripsText(String.valueOf(tripsPerStop(stopId)));
        stopController.setStopID(stopId);
        Time currentTime = java.sql.Time.valueOf(LocalTime.now());
        stopController.setNextTrip(nextTripAtStop(stopId, currentTime));
        stopDisplay.show();
    }

    /**
     * Shows the route stage and sets all information inside it
     *
     * @param actionevent when button is clicked
     */
    @FXML
    public void generateRouteIdInterface(ActionEvent actionevent) {
        routeDisplay.show();
    }

    /**
     * Shows the trip stage and sets all information inside it
     *
     * @param actionevent when button is clicked
     */
    @FXML
    public void generateTripIdInterface(ActionEvent actionevent) {
        tripDisplay.show();
    }

    /**
     * Sets the route stage
     *
     * @param stage stage to be set
     */
    protected void setRouteStage(Stage stage) {
        this.routeDisplay = stage;
    }

    /**
     * Sets the trip stage
     *
     * @param stage stage to be set
     */
    protected void setTripStage(Stage stage) {
        this.tripDisplay = stage;
    }

    /**
     * Sets the stop stage
     *
     * @param stage stage to be set
     */
    protected void setStopStage(Stage stage) {
        this.stopDisplay = stage;
    }


    /**
     * Gets all the stops in a route by searching the routeID
     * This method has not been implemented
     *
     * @param routeID
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> allStopsInRoute(int routeID) {
        return null;
    }

    /**
     * Finds the average speed of a trip if given the tripID
     * This method has not been implemented
     *
     * @param tripID
     * @return int
     */
    public static int avgSpeed(String tripID) {
        return 0;
    }

    /**
     * Changes the time of a Stop's Arrival/Departure time
     * This method has not been implemented
     *
     * @return boolean
     */
    public boolean changeStopArrivalDeparture() {
        return false;
    }

    /**
     * Changes the location of a stop given stopID
     * This method has not been implemented
     *
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
     *
     * @param routeID
     * @return double
     */
    public double displayDistance(int routeID) {
        return 0;
    }


    public void exportHelper(ActionEvent actionEvent) {
        if(routes.size() > 0 && allStops.size() > 0) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Save Directory");
            File file = fileChooser.showSaveDialog(null);
            boolean bool = file.mkdir();
            File routeExport = exportRoutes(file.toPath());
            File stopExport = exportStops(file.toPath());
            File tripExport = exportTrips(file.toPath());
            File stopTimeExport = exportStopTimes(file.toPath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Image image = new Image("https://img.icons8.com/fluency/48/000000/checked.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            alert.setTitle("Successful Export");
            alert.setHeaderText("Export Successful");
            alert.setContentText("All files were exported successfully");
            alert.showAndWait();
        } else {
            errorAlert("No files", "Please import files before trying to export files");
        }


    }

    /**
     * Exports the GTFS files to the desired place
     * This method has not been implemented
     *
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
            while (it.hasNext()) {
                writer.write("\n" + it.next().getValue().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Route file could not be found");
        }
        return routeFile;
    }







    /**
     * Creates a file of each imported stop
     *
     * @param path the path of where to save the file
     * @return file of all stops, in correct format.
     */
    public File exportStops(java.nio.file.Path path) {
        File stopFile = new File(path + "/stops.txt");
        FileWriter writer = null;
        Set<Map.Entry<String, Stop>> stopSet = allStops.entrySet();
        Iterator<Map.Entry<String, Stop>> it = stopSet.iterator();
        try {
            writer = new FileWriter(stopFile);
            writer.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
            while (it.hasNext()) {
                writer.write("\n" + it.next().getValue().toString());
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
            while (it.hasNext()) {
                writer.write("\n" + it.next().getValue().toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Trip file could not be found");
        }

        return tripFile;
    }

    public File exportStopTimes(Path path) {
        File stopTimeFile = new File(path + "/stop_times.txt");
        try (FileWriter writer = new FileWriter(stopTimeFile)) {
            writer.write("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
            Set<String> keys = trips.keySet();
            for (String key : keys) {
                Trip trip = trips.get(key);
                for(Map.Entry<String, ArrayList<StopTime>> stoppers: trip.getStopTimes().entrySet()){
                    ArrayList<StopTime> stopList = stoppers.getValue();
                    for(StopTime stop: stopList){
                        writer.write("\n" + stop.toString());
                    }

                }

            }

        } catch (IOException e) {
            System.out.println("stopTime file could not be found");
        }
        return stopTimeFile;
    }

    /**
     * Imports the GTFS files and calls helper methods to populate entity objects
     *
     * @param listOfFiles directories of the files to import
     */
    public boolean importFiles(ArrayList<File> listOfFiles) {
        List<File> routeFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("routes.txt"))
                .toList();

        List<File> stopFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("stops.txt"))
                .toList();

        List<File> tripFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("trips.txt"))
                .toList();

        List<File> stopTimesFile = listOfFiles.stream()
                .filter(file -> file.getName().equals("stop_times.txt"))
                .toList();


        if (routeFile.size() > 0 && stopFile.size() > 0 && tripFile.size() > 0 && stopTimesFile.size() > 0) {
            try {
                Alert alert = infoAlert("Importing files", "All files are being imported...");
                int incorrectLines = 0;
                incorrectLines += importRoutes(routeFile.get(0));
                incorrectLines += importStops(stopFile.get(0));
                incorrectLines += importTrips(tripFile.get(0));
                incorrectLines += importStopTimes(stopTimesFile.get(0));

                if (incorrectLines > 0) {
                    alert.hide();
                    errorAlert("Incorrectly Formatted Lines",
                            "All files were imported successfully, but " + incorrectLines +
                                    " incorrectly formatted lines were skipped.");
                } else {
                    alert.setHeaderText("Success");
                    alert.setContentText("All files have been successfully imported");
                }
                return true;
            } catch (InvalidHeaderException e) {
                errorAlert("Invalid Header", "The header for " + e.filename +
                        " is formatted incorrectly. No files were imported");
            } catch (IOException e) {
                errorAlert("File Not Found", "A specified file was not found");
            }
        } else {
            errorAlert("All four files must be imported at the same time", "Accepted filenames: routes.txt, stops.txt, trips.txt, stop_times.txt");
        }
        return false;
    }

    /**
     * Populates the StopTimes in each Trip
     *
     * @param stopTimesFile the File to read from
     * @return the number of incorrectly formatted lines
     * @throws IOException            if there is a problem reading the file
     * @throws InvalidHeaderException if the header is not formatted correctly
     * @author Ian Czerkis
     */
    private int importStopTimes(File stopTimesFile) throws IOException, InvalidHeaderException {
        int invalidLines = 0;
        try (Stream<String> lines = Files.lines(stopTimesFile.toPath())) {
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (validateFirstStopTimeLine(firstLine)) {
                while (it.hasNext()) {
                    StopTime stopTime = validateStopTimeLine(it.next());
                    if (!Objects.equals(stopTime, null)) {
                        Trip trip = trips.get(stopTime.getTripID());
                        if (trip != null) {
                            trip.addStopTime(stopTime.getStopID(), stopTime);
                        }
                    } else {
                        invalidLines++;
                    }
                }
            } else {
                throw new InvalidHeaderException("Invalid Header Encountered", "stop_times.txt");
            }
        }
        return invalidLines;
    }

    /**
     * creates a StopTime from a single line in the StopTime file
     *
     * @param line the line to parse
     * @return the StopTime object if the file is valid or null if the file is invalid
     * @author Ian Czerkis
     */
    public static StopTime validateStopTimeLine(String line) {
        StopTime stopTime;
        try {
            CSVReader reader = new CSVReader(line);
            stopTime = new StopTime(
                    reader.next(), reader.next(), reader.next(),
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
     *
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
     *
     * @param tripFile the file to import
     * @return the number of incorrectly formatted lines
     * @throws IOException            if there is a problem reading the file
     * @throws InvalidHeaderException if the header is not formatted correctly
     */
    private int importTrips(File tripFile) throws IOException, InvalidHeaderException {
        int invalidLines = 0;
        try (Stream<String> lines = Files.lines(tripFile.toPath())) {
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!validateTripHeader(firstLine)) {
                throw new InvalidHeaderException("Invalid header encountered", "trips.txt");
            }
            while (it.hasNext()) {
                String tripLine = it.next();
                Trip trip = validateTripLines(tripLine);
                // Add trip to corresponding route
                if (!Objects.equals(null, trip)) {
                    if (routes.containsKey(trip.getRouteID())) {
                        routes.get(trip.getRouteID()).getTrips().put(trip.getTripID(), trip);
                        trips.put(trip.getTripID(), trip);
                    } else {
                        invalidLines++;
                    }
                }
            }
        }
        return invalidLines;
    }

    public int importFilesNoStage(ArrayList<File> listOfFiles) throws IOException, InvalidHeaderException {
        int ret = 0;

        ret += importRoutes(listOfFiles.stream()
                .filter(file -> file.getName().equals("routes.txt"))
                .toList().get(0));
        ret += importStops(listOfFiles.stream()
                .filter(file -> file.getName().equals("stops.txt"))
                .toList().get(0));
        ret += importTrips(listOfFiles.stream()
                .filter(file -> file.getName().equals("trips.txt"))
                .toList().get(0));
        ret += importStopTimes(listOfFiles.stream()
                .filter(file -> file.getName().equals("stop_times.txt"))
                .toList().get(0));

        return ret;
    }


    /**
     * Checks if Trip header is valid against known valid header.
     *
     * @param header trip header
     * @return boolean
     * @Author Matt Wehman
     */
    public static boolean validateTripHeader(String header) {
        return header.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
    }

    /**
     * Validates each line of trip file.
     *
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
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e) {
            return null;
        }
    }


    /**
     * Populates the Stops
     *
     * @param stopFile the file to parse
     * @return the number of incorrectly formatted lines
     * @throws IOException            if there is a problem reading the file
     * @throws InvalidHeaderException if the header is not formatted correctly
     */
    private int importStops(File stopFile) throws IOException, InvalidHeaderException {
        int invalidLines = 0;
        try (Stream<String> lines = Files.lines(stopFile.toPath())) {
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!validateStopHeader(firstLine)) {
                throw new InvalidHeaderException("Invalid header encountered", "stops.txt");
            } else {
                while (it.hasNext()) {
                    String stopLine = it.next();
                    Stop stop = validateLinesInStop(stopLine);
                    if (!Objects.equals(null, stop)) {
                        allStops.put(stop.getStopID(), stop);
                    } else {
                        invalidLines++;
                    }
                }
            }
        }
        return invalidLines;
    }

    /**
     * This method validates the Stop headerline and makes sure it follows the correct syntax
     *
     * @param firstLine
     * @return boolean
     * @author Patrick McDonald
     */
    public static boolean validateStopHeader(String firstLine) {
        return firstLine.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
    }

    /**
     * This method validates each individual Stop and makes sure it is formatted correctly
     * or else it returns a null
     *
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
            if (lat == -1 || lon == -1 || (lat < -90.00 || lat > 90.00) ||
                    (lon < -180.00 || lon > 180.00)) {
                throw new NumberFormatException("empty");
            }

            stop = new Stop(stopId, name, description, lat, lon);
            reader.checkEndOfLine();
            stop.checkRequired();
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e) {
            return null;
        }
        return stop;
    }

    /**
     * Populates the routes
     *
     * @param routeFile the file that contains the route lines
     * @return the number of incorrectly formatted lines
     * @throws IOException            if there is a problem reading the file
     * @throws InvalidHeaderException if the header is incorrectly formatted
     * @author Christian B
     */
    private int importRoutes(File routeFile) throws IOException, InvalidHeaderException {
        int incorrectLines = 0;
        try (Stream<String> lines = Files.lines(routeFile.toPath())) {
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (validateRouteHeader(firstLine)) {
                while (it.hasNext()) {
                    Route route = validateRouteLine(it.next());
                    if (!Objects.equals(route, null)) {
                        routes.put(route.getRouteID(), route);
                    } else {
                        incorrectLines++;
                    }
                }
            } else {
                throw new InvalidHeaderException("Invalid header encountered", "routes.txt");
            }
        }
        return incorrectLines;
    }


    /**
     * validates that the header for the route file is formatted correctly
     *
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
     *
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
        } catch (CSVReader.EndOfStringException | CSVReader.MissingRequiredFieldException | NumberFormatException e) {
            return null;
        }
        return route;
    }


    /**
     * Allows user to select multiple files
     *
     * @param actionEvent
     */
    @FXML
    public void importHelper(ActionEvent actionEvent) {
        List<File> f;
        boolean isNull;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(Paths.get("./").toFile());
        File selectedFile;
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GSTF Files", "*.txt"));
        f = fileChooser.showOpenMultipleDialog(null);
        if (f == null) {
            isNull = true;
            Alert alert = errorAlert("Null File Entered", "Can't import" +
                    "null file, only GTFS files are accepted. Please try again");
        } else {
            ArrayList<File> files = new ArrayList<>();
            for (File file : f) {
                files.add(file);
                System.out.println(files);
            }

            importFiles(files);
        }


    }


    //epic


    /**
     * Finds the next trip at a certain stop given the time
     * This method has not been implemented
     *
     * @param stopID the stop being parsed
     * @param currentTime the current time
     */
    public String nextTripAtStop(String stopID, Time currentTime) {
        int counter = 0;
        SortedMap<Time, StopTime> map = new TreeMap<>();
        for (Map.Entry<String, Trip> mapEntry : trips.entrySet()) {
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)) {
                ArrayList<StopTime> stopTimes = trip.getStopTimes().get(stopID);
                for (StopTime stopTime: stopTimes){
                    Time stopTimeArr = stopTime.getArrivalTime();
                    if (currentTime.compareTo(stopTimeArr) < 0) {
                        map.put(stopTimeArr, stopTime);
                    }
                }
            }
        }
        return map.get(map.firstKey()).getTripID();
    }

    /**
     * Plots the current trajectory of the bus
     * This method has not been implemented
     *
     * @param tripID
     * @return boolean
     */
    public boolean plotBus(int tripID) {
        return false;
    }

    /**
     * Plots the stops on a given route
     * This method has not been implemented
     *
     * @param routeID
     * @return boolean
     */
    public boolean plotStops(int routeID) {
        return false;
    }

    /**
     * searches all routes to see if they contain the specified stopID
     *
     * @param stopID the stop ID to search
     * @return ArrayList<String> the list of routeID that contain
     */
    public ArrayList<String> routesContainingStop(String stopID) {
        ArrayList<String> routesContaining = new ArrayList<>();
        for (Map.Entry<String, Trip> mapEntry : trips.entrySet()) {
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)) {
                if (!routesContaining.contains(trip.getRouteID())) {
                    routesContaining.add(trip.getRouteID());
                }
            }
        }
        return routesContaining;
    }

    /**
     * finds all the future trips given a routeID
     * This method has not been implemented
     *
     * @param routeID
     * @return LinkedList<Integer>
     */
    public LinkedList<Integer> tripsInFuture(int routeID) {
        return null;
    }

    /**
     * Counts the number trips that use the specified stop
     *
     * @param stopID the stopID to search for
     * @return the number of occurances of trips containing that stop
     */
    public int tripsPerStop(String stopID) {
        int counter = 0;
        for (Map.Entry<String, Trip> mapEntry : trips.entrySet()) {
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Updates all stoptimes in a trip
     * This method has not been implemented
     *
     * @param stopID
     * @return boolean
     */
    public boolean updateAllStopTimes(int stopID) {
        return false;
    }

    /**
     * Updates a group of stoptimes
     * This method has not been implemented
     *
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
     *
     * @param stopTimes
     * @return boolean
     */
    public boolean updateMultipleStopTimes(ArrayList<StopTime> stopTimes) {
        return false;
    }

    /**
     * Updates a route, certain attributes of the route may be different
     * This method has not been implemented
     *
     * @param route
     * @return boolean
     */
    public boolean updateRoute(Route route) {
        return false;
    }

    /**
     * Updates a stop, certain attributes of the stops may be different
     * This method has not been implemented
     *
     * @param stop
     * @return boolean
     */
    public boolean updateStop(Stop stop) {
        return false;
    }

    /**
     * Updates a StopTime, certain attributes of the StopTime may be different
     * This method has not been implemented
     *
     * @param stopTime
     * @return boolean
     */
    public boolean updateStopTime(StopTime stopTime) {
        return false;
    }

    /**
     * Updates a Trip, certain attributes of the Trip may be different
     * This method has not been implemented
     *
     * @param trip
     * @return boolean
     */
    public boolean updateTrip(Trip trip) {
        return false;
    }

/**
 * Returns all routes that contain a certain stop
 *
 * @param stopId
 * @return Arraylist of routes
 */

    /**
     * an exception to be thrown if
     */
    public static class InvalidHeaderException extends Exception {
        private final String filename;

        InvalidHeaderException(String message, String filename) {
            super(message);
            this.filename = filename;
        }

        public String getFilename() {
            return filename;
        }
    }

    private Alert errorAlert(String header, String context) {
        return alert(header, context, "Error", Alert.AlertType.ERROR);
    }

    private Alert infoAlert(String header, String context) {
        return alert(header, context, "Info", Alert.AlertType.WARNING);
    }

    private Alert alert(String header, String context, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return alert;
    }


}