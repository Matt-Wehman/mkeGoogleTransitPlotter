import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<File> routeFile = listOfFiles.stream()
                                          .filter(file -> file.getName().equals("route.txt"))
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
        return false;
    }

    private void importStops(File stopFile) {
        try (Stream<String> lines = Files.lines(stopFile.toPath())){
            Iterator<String> it = lines.iterator();
            String firstLine = it.next();
            if (!firstLine.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon")){
                System.out.println("Unknown formatting encountered");
            }
            while (it.hasNext()){
                CSVReader reader = new CSVReader(it.next());
                try {
                    Stop stop = new Stop(
                            reader.nextInt(), reader.next(), reader.next(),
                            reader.nextInt(), reader.nextInt());
                    allStops.put(stop.getStopID(), stop);
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line is not formatted correctly, skipping");
                }

            }
        } catch (IOException e){
            System.out.println("Error finding file, no Stops were imported");
        }

    }

    private void importRoutes(File routeFile) {
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
                    Route route = new Route(
                            reader.nextInt(), reader.next(), reader.nextInt(),
                            reader.next(), reader.next(), reader.nextInt(),
                            reader.nextInt(), reader.nextInt(), reader.nextInt());
                    routes.put(route.getRouteID(), route);
                } catch (CSVReader.EndOfStringException | NumberFormatException e){
                    System.out.println("Line is not formatted correctly, skipping");
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