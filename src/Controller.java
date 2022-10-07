import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
public class Controller {

    private HashMap<Integer, Stop> allStops = new HashMap<>();
    private HashMap<String, Route> routes = new HashMap<>();
    private HashMap<String, Trip> trips = new HashMap<>();

    public Controller() {

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

    private void importStopTimes(File stopTimesFile) {
        int index = 0;
        try (Stream<String> lines = Files.lines(stopTimesFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
                    "stop_headsign,pickup_type,drop_off_type")){

                System.out.println("Unknown formatting encountered");
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

                } catch (CSVReader.EndOfStringException | NumberFormatException | ParseException e){
                    System.out.println("Line " + index + " (StopTimes) is not formatted correctly, skipping\n"+e.getLocalizedMessage());
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Trips were imported");
        }
    }

    private void importTrips(File tripFile) {
        int index = 0;
        try (Stream<String> lines = Files.lines(tripFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("route_id,agency_id,route_short_name,route_long_name," +
                    "route_desc,route_type,route_url,route_color,route_text_color")){

                System.out.println("Unknown formatting encountered");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    Trip trip = new Trip(
                            reader.nextInt(), reader.next(), reader.next(),
                            reader.next(), reader.nextInt(), reader.nextInt(),
                            reader.next());
                    // Add trip to corresponding route
                    routes.get(trip.getRouteID()).getTrips().put(trip.getTripID(), trip);
                    // Add trip to HashMap of all trips
                    trips.put(trip.getTripID(), trip);
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Trips) is not formatted correctly, skipping");
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Trips were imported");
        }
    }

    private void importStops(File stopFile) {
        int index = 1;
        try (Stream<String> lines = Files.lines(stopFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon")){
                System.out.println("Unknown formatting encountered");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    index++;
                    Stop stop = new Stop(
                            reader.nextInt(), reader.next(), reader.next(),
                            reader.nextInt(), reader.nextInt());
                    allStops.put(stop.getStopID(), stop);
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Stops) is not formatted correctly, skipping");
                }
            }
        } catch (IOException e){
            System.out.println("Error finding file, no Stops were imported");
        }
    }

    private void importRoutes(File routeFile) {
        int index = 1;
        try (Stream<String> lines = Files.lines(routeFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("route_id,agency_id,route_short_name,route_long_name," +
                    "route_desc,route_type,route_url,route_color,route_text_colorroute_id," +
                    "agency_id,route_short_name,route_long_name,route_desc,route_type," +
                    "route_url,route_color,route_text_color")){
                System.out.println("Unknown formatting encountered");
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
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line " + index + " (Routes) is not formatted correctly, skipping");
                }

            }
        } catch (IOException e){
            System.out.println("Error finding file, no Routes were imported");
        }

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