/*
 * Course: SE 2030 - 041
 * Fall 22-23 test
 * GTFS Project
 * Created by: Christian Basso, Ian Czerkis, Matt Wehman, Patrick McDonald.
 * Created on: 09/10/22
 */

import java.awt.*;
import java.io.*;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @FXML
    Button webButton;

    @FXML
    WebView webView;

    @FXML
    MapView mapView;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    /** some coordinates from around town. */
    private static final Coordinate coordKarlsruheCastle = new Coordinate(49.013517, 8.404435);
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);
    private static final Coordinate coordKarlsruheStation = new Coordinate(48.993284, 8.402186);
    private static final Coordinate coordKarlsruheSoccer = new Coordinate(49.020035, 8.412975);
    private static final Coordinate coordKarlsruheUniversity = new Coordinate(49.011809, 8.413639);
    private static final Extent extentAllLocations = Extent.forCoordinates(coordKarlsruheCastle, coordKarlsruheHarbour, coordKarlsruheStation, coordKarlsruheSoccer);

    private static final Coordinate coordGermanyNorth = new Coordinate(55.05863889, 8.417527778);
    private static final Coordinate coordGermanySouth = new Coordinate(47.27166667, 10.17405556);
    private static final Coordinate coordGermanyWest = new Coordinate(51.0525, 5.866944444);
    private static final Coordinate coordGermanyEast = new Coordinate(51.27277778, 15.04361111);
    private static final Extent extentGermany = Extent.forCoordinates(coordGermanyNorth, coordGermanySouth, coordGermanyWest, coordGermanyEast);

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 14;

    /** the markers. */
    public Marker markerKaHarbour;
    public Marker markerKaCastle;
    private Marker markerKaStation;
    private Marker markerKaSoccer;
    private Marker markerClick;

    /** the labels. */
    private MapLabel labelKaUniversity;
    private MapLabel labelKaCastle;
    private MapLabel labelKaStation;
    private MapLabel labelClick;

    // a circle around the castle
    private MapCircle circleCastle;

    @FXML
    /** button to set the map's zoom. */
    private Button buttonZoom;

    public List<Marker> markers;


    /** the box containing the top controls, must be enabled when mapView is initialized */
    @FXML
    private HBox topControls;

    /** Slider to change the zoom value */
    @FXML
    private Slider sliderZoom;

    /** Accordion for all the different options */
    @FXML
    private Accordion leftControls;

    /** section containing the location button */
    @FXML
    private TitledPane optionsLocations;

    /** button to set the map's center */
    @FXML
    private Button buttonKaHarbour;

    /** button to set the map's center */
    @FXML
    private Button buttonKaCastle;

    /** button to set the map's center */
    @FXML
    private Button buttonKaStation;

    /** button to set the map's center */
    @FXML
    private Button buttonKaSoccer;

    /** button to set the map's extent. */
    @FXML
    private Button buttonAllLocations;

    /** for editing the animation duration */
    @FXML
    private TextField animationDuration;

    /** the BIng Maps API Key. */
    @FXML
    private TextField bingMapsApiKey;

    /** Label to display the current center */
    @FXML
    private Label labelCenter;

    /** Label to display the current extent */
    @FXML
    private Label labelExtent;

    /** Label to display the current zoom */
    @FXML
    private Label labelZoom;

    /** label to display the last event. */
    @FXML
    private Label labelEvent;

    /** RadioButton for MapStyle OSM */
    @FXML
    private RadioButton radioMsOSM;

    /** the first CoordinateLine */
    private CoordinateLine trackMagenta;
    /** Check button for first track */
    @FXML
    private CheckBox checkTrackMagenta;

    /** the second CoordinateLine */
    private CoordinateLine trackCyan;
    /** Check button for first track */
    @FXML
    private CheckBox checkTrackCyan;

    /** Coordinateline for polygon drawing. */
    private CoordinateLine polygonLine;
    /** Check Button for polygon drawing mode. */
    @FXML
    private CheckBox checkDrawPolygon;

    /** Check Button for constraining th extent. */
    @FXML
    private CheckBox checkConstrainGermany;

    /** params for the WMS server. */
    private WMSParam wmsParam = new WMSParam()
            .setUrl("http://ows.terrestris.de/osm/service?")
            .addParam("layers", "OSM-WMS");

    private XYZParam xyzParams = new XYZParam()
            .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
            .withAttributions(
                    "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    //Will delete trips eventually, just need it so the program will run
    protected HashMap<String, Trip> trips = new HashMap<>();

    protected HashMap<String, ArrayList<Stop>> allStopsList = new HashMap<>();
    protected HashMap<String, ArrayList<Trip>> tripsList = new HashMap<>();
    protected HashMap<String, ArrayList<Route>> routesList = new HashMap<>();


    /**
     * Creates Controller instance
     */
    public Controller() {

    }

//    public void testTripsInRoutes() {
//        Set<Map.Entry<String, ArrayList<Route>>> routeSet = routesList.entrySet();
//
//        Iterator<Map.Entry<String, ArrayList<Route>>> it = routeSet.iterator();
//
//        while (it.hasNext()) {
//            Map.Entry<String, ArrayList<Route>> routess = it.next();
//            for (Route r : routess.getValue()) {
//                System.out.println(r.getStopsList());
//            }
//        }
//    }

    /**
     * This adds Trips to an arrayList. This method will handle the chaining for the
     * Trips in tripList
     * @param key
     * @param val
     * @author Patrick
     */
    public void addTrip(String key, Trip val) {
        if (tripsList.containsKey(key)) {
            tripsList.get(key).add(val);
        } else {
            tripsList.put(key, new ArrayList<>());
            tripsList.get(key).add(val);
        }
    }

    /**
     * This adds Routes to an arrayList. This method will handle the chaining for the
     * Routes in RouteList
     * @param key
     * @param val
     * @author Patrick
     */
    public void addRoute(String key, Route val) {
        if (routesList.containsKey(key)) {
            routesList.get(key).add(val);
        } else {
            routesList.put(key, new ArrayList<>());
            routesList.get(key).add(val);
        }
    }

    /**
     * This adds Stops to an arrayList. This method will handle the chaining for the
     * stops in allStopsList
     * @param key
     * @param val
     * @author Patrick
     */
    public void addStops(String key, Stop val) {
        if (allStopsList.containsKey(key)) {
            allStopsList.get(key).add(val);
        } else {
            allStopsList.put(key, new ArrayList<>());
            allStopsList.get(key).add(val);
        }
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
        if (allStopsList.containsKey(stopId)) {
            stopController.setTripsText(String.valueOf(tripsPerStop(stopId)));
            ArrayList<Stop> selectedStopId = allStopsList.get(stopId);
            for(Stop s: selectedStopId){
                if(s.getStopID().equals(stopId)){
                    stopController.setStopID(s.getStopName());
                }
            }
            ArrayList<String> routesContaining = routesContainingStop(stopId);
            String routesString = "";
            for (int i = 0; i < routesContaining.size() - 1; i++) {
                routesString += routesContaining.get(i) + ", ";
            }
            if (routesContaining.size() > 0) {
                routesString += routesContaining.get(routesContaining.size() - 1);
            } else {
                routesString = "No routes service this stop";
            }
            stopController.setRoutesText(routesString);
            stopDisplay.show();
            stopController.setTripsText(String.valueOf(tripsPerStop(stopId)));
            stopController.setStopID(stopId);
            Time currentTime = java.sql.Time.valueOf(LocalTime.now());
            stopController.setNextTrip(nextTripAtStop(stopId, currentTime));
        } else {
            errorAlert("Stop Not Found", "Ensure the GTFS files have been imported");
        }

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
        if (routesList.size() > 0 && allStopsList.size() > 0) {
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
        Set<Map.Entry<String, ArrayList<Route>>> routeSet = routesList.entrySet();

        Iterator<Map.Entry<String, ArrayList<Route>>> it = routeSet.iterator();
        try {
            writer = new FileWriter(routeFile);
            writer.write("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
            while (it.hasNext()) {
                Map.Entry<String, ArrayList<Route>> routes = it.next();
                for (Route r : routes.getValue()) {
                    writer.write("\n" + r.toString());
                }
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
        Set<Map.Entry<String, ArrayList<Stop>>> stopSet = allStopsList.entrySet();
        Iterator<Map.Entry<String, ArrayList<Stop>>> it = stopSet.iterator();
        try {
            writer = new FileWriter(stopFile);
            writer.write("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
            while (it.hasNext()) {
                Map.Entry<String, ArrayList<Stop>> stops = it.next();
                for (Stop s : stops.getValue()) {
                    writer.write("\n" + s.toString());
                }

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
        Set<Map.Entry<String, ArrayList<Trip>>> tripSet = tripsList.entrySet();
        Iterator<Map.Entry<String, ArrayList<Trip>>> it = tripSet.iterator();
        try {
            writer = new FileWriter(tripFile);
            writer.write("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
            while (it.hasNext()) {
                Map.Entry<String, ArrayList<Trip>> tripss = it.next();
                for (Trip t : tripss.getValue()) {
                    writer.write("\n" + t.toString());
                }

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
            Set<String> keys = tripsList.keySet();
            for (String key : keys) {
                ArrayList<Trip> tripss = tripsList.get(key);
                for (Trip t : tripss) {
                    for (Map.Entry<String, ArrayList<StopTime>> stops : t.getStopTimes().entrySet()) {
                        ArrayList<StopTime> stopList = stops.getValue();
                        for (StopTime stop : stopList) {
                            writer.write("\n" + stop.toString());
                        }

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
            int incorrectLines = 0;
            try {
                FXMLLoader importLoader = new FXMLLoader();
                Parent importRoot = importLoader.load(Objects.requireNonNull(getClass()
                        .getResource("ImportingFilesDisplay.fxml")).openStream());
                Stage importStage = new Stage();
                importStage.setTitle("Importing...");
                importStage.setScene(new Scene(importRoot));
                importStage.show();

                incorrectLines += importFilesNoStage(listOfFiles);

                importStage.hide();


                if (incorrectLines > 0) {
                    errorAlert("Success, But Incorrectly Formatted Lines",
                            incorrectLines + " incorrectly formatted lines were skipped.");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    Image image = new Image("https://img.icons8.com/fluency/48/000000/checked.png");
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.setTitle("Successful Import");
                    alert.setHeaderText("Import Successful");
                    alert.setContentText("All files were imported successfully");
                    alert.showAndWait();
                }
                return true;
            } catch (InvalidHeaderException e) {
                errorAlert("Invalid Header", "The header for " + e.filename +
                        " is formatted incorrectly. No files were imported");
            } catch (IOException e) {
                errorAlert("File not found", "A required file was not found");
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
                        ArrayList<Trip> tripss = tripsList.get(stopTime.getTripID());
                        if (tripss != null) {
                            for (Trip t : tripss) {
                                if (t.getTripID().equals(stopTime.getTripID())) {
                                    t.addStopTime(stopTime.getStopID(), stopTime);
                                }
                            }
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
                    if (routesList.containsKey(trip.getRouteID())) {
                        ArrayList<Route> routes = routesList.get(trip.getRouteID());
                        for (Route r : routes) {
                            if (r.getRouteID().equals(trip.getRouteID())) {
                                r.addTrip(trip.getTripID(), trip);
                            }
                        }
                        addTrip(trip.getTripID(), trip);


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
                        addStops(stop.getStopID(), stop);
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
                        addRoute(route.getRouteID(), route);
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
        String ret = "";
        SortedMap<Time, StopTime> map = new TreeMap<>();
        TreeMap<Time, StopTime> nextDayTimes = new TreeMap<>();
        for (Map.Entry<String, Trip> mapEntry : trips.entrySet()) {
            Trip trip = mapEntry.getValue();
            if (trip.getStopTimes().containsKey(stopID)) {
                ArrayList<StopTime> stopTimes = trip.getStopTimes().get(stopID);
                for (StopTime stopTime : stopTimes) {
                    Time stopTimeArr = null;
                    if (!stopTime.getIsNextDay()) {
                        stopTimeArr = stopTime.getArrivalTime();
                        if (currentTime.compareTo(stopTimeArr) < 0) {
                            map.put(stopTimeArr, stopTime);
                        }
                    } else{
                        nextDayTimes.put(stopTime.getArrivalTime(), stopTime);
                    }
                }

                if (!map.isEmpty()) {
                    ret = map.get(map.firstKey()).getTripID();
                } else if (!nextDayTimes.isEmpty()){
                    ret = nextDayTimes.get(nextDayTimes.firstKey()).getTripID();
                }

            }
        }
        return ret;
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


    @FXML
    public void setWebView(ActionEvent actionEvent) {
        URL url = this.getClass().getResource("googlemap.html");
        WebEngine engine = webView.getEngine();
////        Set<Map.Entry<String, ArrayList<Route>>> routeSet = routesList.entrySet();
////        Iterator<Map.Entry<String, ArrayList<Route>>> it = routeSet.iterator();
//        engine.load(getMapURL(it.next().getValue().get(0)));
        engine.load(url.toString());
        //Routes file not filled with stops
    }

    private String getMapURL(Route route) {
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=Milwaukee,wi&zoom=13&size=600x300&maptype=roadmap\n";
        HashMap<String, ArrayList<Stop>> stops = route.getStopsList();
        Set<Map.Entry<String, ArrayList<Stop>>> stopSet = stops.entrySet();
        Iterator<Map.Entry<String, ArrayList<Stop>>> it = stopSet.iterator();

        while(it.hasNext()) {
            Stop cur = it.next().getValue().get(0);
            url += makeMarker(cur.getStopLat(), cur.getStopLong(), "red", "A");
        }
        url +="&key=AIzaSyAvMUBHz0Ny9mZ9gzSrexxQuvyBF7e3mk4";
        return url;
    }

    private String makeMarker(double lat, double longi, String color, String letter) {
        return "&markers=color:" + color + "%7Clabel:" + letter + "%7C" + lat + longi + "\n";
    }

    /**
     * called after the fxml is loaded and all objects are created. This is not called initialize any more,
     * because we need to pass in the projection before initializing.
     *
     * @param projection
     *     the projection to use in the map.
     */
    public void initMapAndControls(Projection projection) {
        logger.trace("begin initialize");
        mapView.setMapType(MapType.OSM);
        mapView.setCenter(new Coordinate(43.0453675,-87.9109152));
        mapView.setZoom(15);
        markers = new ArrayList<>();
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        logger.trace("start map initialization");
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
        logger.debug("initialization finished");
    }

    /**
     * initializes the event handlers.
     */
    @FXML
    public void setMarker(ActionEvent event){
        URL url = this.getClass().getResource("check.png");
        assert url != null;
        Coordinate coord = new Coordinate(43.0453675,-87.9109152);
        Coordinate coord2 = new Coordinate(43.1045379,-87.9397422);
        List<Coordinate> coords = List.of(coord, coord2);

        Marker marker = Marker.createProvided(Marker.Provided.ORANGE).setPosition(coord).setVisible(true);
        markers.add(marker);
        Extent extent = Extent.forCoordinates(coords);
        mapView.setExtent(extent);
        mapView.setCenter(coord);
        mapView.addMarker(marker);
    }
    private void setupEventHandlers() {
        // add an event handler for singleclicks, set the click marker to the new position when it's visible
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            final Coordinate newPosition = event.getCoordinate().normalize();
            labelEvent.setText("Event: map clicked at: " + newPosition);
            if (checkDrawPolygon.isSelected()) {
                handlePolygonClick(event);
            }
            if (markerClick.getVisible()) {
                final Coordinate oldPosition = markerClick.getPosition();
                if (oldPosition != null) {
                    animateClickMarker(oldPosition, newPosition);
                } else {
                    markerClick.setPosition(newPosition);
                    // adding can only be done after coordinate is set
                    mapView.addMarker(markerClick);
                }
            }
        });

        // add an event handler for MapViewEvent#MAP_EXTENT and set the extent in the map
        mapView.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
            event.consume();
            mapView.setExtent(event.getExtent());
        });

        // add an event handler for extent changes and display them in the status label
        mapView.addEventHandler(MapViewEvent.MAP_BOUNDING_EXTENT, event -> {
            event.consume();
            labelExtent.setText(event.getExtent().toString());
        });

        mapView.addEventHandler(MapViewEvent.MAP_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: map right clicked at: " + event.getCoordinate());
        });
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: marker clicked: " + event.getMarker().getId());
        });
        mapView.addEventHandler(MarkerEvent.MARKER_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: marker right clicked: " + event.getMarker().getId());
        });
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: label clicked: " + event.getMapLabel().getText());
        });
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: label right clicked: " + event.getMapLabel().getText());
        });

        mapView.addEventHandler(MapViewEvent.MAP_POINTER_MOVED, event -> {
            logger.debug("pointer moved to " + event.getCoordinate());
        });

        logger.trace("map handlers initialized");
    }

    private void animateClickMarker(Coordinate oldPosition, Coordinate newPosition) {
        // animate the marker to the new position
        final Transition transition = new Transition() {
            private final Double oldPositionLongitude = oldPosition.getLongitude();
            private final Double oldPositionLatitude = oldPosition.getLatitude();
            private final double deltaLatitude = newPosition.getLatitude() - oldPositionLatitude;
            private final double deltaLongitude = newPosition.getLongitude() - oldPositionLongitude;

            {
                setCycleDuration(Duration.seconds(1.0));
                setOnFinished(evt -> markerClick.setPosition(newPosition));
            }

            @Override
            protected void interpolate(double v) {
                final double latitude = oldPosition.getLatitude() + v * deltaLatitude;
                final double longitude = oldPosition.getLongitude() + v * deltaLongitude;
                markerClick.setPosition(new Coordinate(latitude, longitude));
            }
        };
        transition.play();
    }

    /**
     * shows a new polygon with the coordinate from the added.
     *
     * @param event
     *     event with coordinates
     */
    private void handlePolygonClick(MapViewEvent event) {
        final List<Coordinate> coordinates = new ArrayList<>();
        if (polygonLine != null) {
            polygonLine.getCoordinateStream().forEach(coordinates::add);
            mapView.removeCoordinateLine(polygonLine);
            polygonLine = null;
        }
        coordinates.add(event.getCoordinate());
        polygonLine = new CoordinateLine(coordinates)
                .setColor(javafx.scene.paint.Color.DODGERBLUE)
                .setFillColor(Color.web("lawngreen", 0.4))
                .setClosed(true);
        mapView.addCoordinateLine(polygonLine);
        polygonLine.setVisible(true);
    }

    /**
     * enables / disables the different controls
     *
     * @param flag
     *     if true the controls are disabled
     */
//    private void setControlsDisable(boolean flag) {
//        topControls.setDisable(flag);
//        leftControls.setDisable(flag);
//    }

    /**
     * finishes setup after the mpa is initialzed
     */
    private void afterMapIsInitialized() {

//        logger.trace("map intialized");
//        logger.debug("setting center and enabling controls...");
//        // start at the harbour with default zoom
//        mapView.setZoom(ZOOM_DEFAULT);
//        mapView.setCenter(coordKarlsruheHarbour);
//        // add the markers to the map - they are still invisible
//        mapView.addMarker(markerKaHarbour);
//        mapView.addMarker(markerKaCastle);
//        mapView.addMarker(markerKaStation);
//        mapView.addMarker(markerKaSoccer);
//        // can't add the markerClick at this moment, it has no position, so it would not be added to the map
//
//        // add the fix label, the other's are attached to markers.
//        mapView.addLabel(labelKaUniversity);
//
//        // add the tracks
//        mapView.addCoordinateLine(trackMagenta);
//        mapView.addCoordinateLine(trackCyan);
//
//        // add the circle
//        mapView.addMapCircle(circleCastle);

        // now enable the controls
//        setControlsDisable(false);
    }

    /**
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @param url
     *     url where to load from
     * @return optional CoordinateLine object
     * @throws java.lang.NullPointerException
     *     if uri is null
     */
    private Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (
                Stream<String> lines = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines()
        ) {
            return Optional.of(new CoordinateLine(
                    lines.map(line -> line.split(";")).filter(array -> array.length == 2)
                            .map(values -> new Coordinate(Double.valueOf(values[0]), Double.valueOf(values[1])))
                            .collect(Collectors.toList())));
        } catch (IOException | NumberFormatException e) {
            logger.error("load {}", url, e);
        }
        return Optional.empty();
    }
}
