/*
 * Course: SE 2030 - 041
 * Fall 22-23
 * GTFS Project
 * Created by: Christian Basso, Ian Czerkis, Matt Wehman, Patrick McDonald.
 * Created on: 09/10/22
 * Copyright 2022 Ian Czerkis, Matthew Wehman, Patrick McDonald, Christian Basso

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * tests methods in the controller class
 */
public class ControllerTest {
    Controller controller;

    /**
     * instantiates all objects in controllers
     *
     * @author Matthew Wehman
     */
    @BeforeEach
    private void setUp() throws IOException, Controller.InvalidHeaderException {
        controller = new Controller();
        ArrayList<File> listOfFiles = new ArrayList<>();
        listOfFiles.add(new File("./GTFSFiles/routes.txt"));
        listOfFiles.add(new File("./GTFSFiles/stop_times.txt"));
        listOfFiles.add(new File("./GTFSFiles/stops.txt"));
        listOfFiles.add(new File("./GTFSFiles/trips.txt"));
        controller.importFilesNoStage(listOfFiles);
    }



    /**
     * Tests distance calculations for a trip (Feature 2)
     *
     * @author Christian B
     */

    @Test
    public void testDistance() {
        int correctDistance1 = 100;
        int correctDistance2 = 100;
        int incorrectDistance1 = 99;

        Trip testTrip1 = Controller.validateTripLines("64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23");
        Trip testTrip2 = Controller.validateTripLines("64,17-SEP_SUN,21736573_551,SOUTHRIDGE,1,64102,17-SEP_64_1_19");

        Assertions.assertEquals(correctDistance1, testTrip1.distance());
        Assertions.assertEquals(correctDistance2, testTrip2.distance());
        Assertions.assertNotEquals(incorrectDistance1, testTrip1.distance());

    }

    /**
     * feature 8 tests
     *
     * @author Ian Czerkis
     */
    @Test
    public void testNextTripAtStop() {
        String stopID = "1661";


        Time currentTime = new Time(8, 37, 0);
        String nextTripAtStop = controller.nextTripAtStop(stopID, currentTime);
        Assertions.assertEquals("21794626_1570", nextTripAtStop);
        Assertions.assertNotEquals("21794626_212123570", nextTripAtStop);

        stopID = "6037";
        currentTime = new Time(18, 32, 0);
        nextTripAtStop = controller.nextTripAtStop(stopID, currentTime);
        Assertions.assertEquals("21850870_756", nextTripAtStop);
        Assertions.assertNotEquals("21850870_75dsadsa6", nextTripAtStop);

        stopID = "3878";
        currentTime = new Time(13, 21, 0);
        nextTripAtStop = controller.nextTripAtStop(stopID, currentTime);
        Assertions.assertEquals("21794234_1711", nextTripAtStop);
        Assertions.assertNotEquals("21794234_1712", nextTripAtStop);
    }


    /**
     * This method tests the speed of a trip give the distance and time of a trip
     *
     * @author Patrick
     */
    @Test
    public void testSpeed() {
        //Time for trips1 is 32, and for trip2 33 mins
        int correctSpeed1 = 100 / 32;
        int correctSpeed2 = 100 / 33;
        int incorrectSpeed1 = 100 / 50;

        Trip testTrip1 = Controller.validateTripLines("64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23");
        Trip testTrip2 = Controller.validateTripLines("64,17-SEP_SUN,21736573_551,SOUTHRIDGE,1,64102,17-SEP_64_1_19");

        Assertions.assertEquals(correctSpeed1, Controller.avgSpeed(testTrip1.getTripID()));
        Assertions.assertEquals(correctSpeed2, Controller.avgSpeed(testTrip2.getTripID()));
        Assertions.assertNotEquals(incorrectSpeed1, Controller.avgSpeed(testTrip1.getTripID()));

    }


    /**
     * Tests route header validation
     *
     * @author Christian Basso
     */
    @Test
    public void testValidateRouteHeader() {
        String validHeader = "route_id,agency_id,route_short_name,route_long_name," +
                "route_desc,route_type,route_url,route_color,route_text_color";
        String invalidHeader = "INVALIDSTRINGroute_id,agency_id,route_short_name,route_long_name," +
                "route_desc,route_type,route_url,route_color,route_text_color";
        Assertions.assertTrue(Controller.validateRouteHeader(validHeader));
        Assertions.assertFalse(Controller.validateRouteHeader(invalidHeader));
    }

    /**
     * Tests route header lines
     *
     * @author Christian Basso
     */

    @Test
    public void testValidateRouteLine() {
        String validRouteLine1 = "23D,MCTS,23,Fond du lac-National (17-SEP) - DETOUR,This Route is in Detour,3,,008345,";
        String validRouteLine2 = "27,MCTS,27,27th Street,,3,,008345,";
        String validRouteLine3 = "42U,,,,,,,008345,";

        String invalidRouteLine1 = "33,MCTS,33,Vliet Street,,3,,008345,,INVALID";
        String invalidRouteLine2 = "33,MCTS,33,Vliet Street,,3,,,";
        String invalidRouteLine3 = ",MCTS,33,Vliet Street,,3,,008345,";

        Route validRoute = new Route("23D", "MCTS", "23", "Fond du lac-National (17-SEP) - DETOUR", "This Route is in Detour", "3", "", "008345", "");

        Assertions.assertTrue(validRoute.equals(Controller.validateRouteLine(validRouteLine1)));

        Route validRoute1 = Controller.validateRouteLine(validRouteLine1);
        Route validRoute2 = Controller.validateRouteLine(validRouteLine2);
        Route validRoute3 = Controller.validateRouteLine(validRouteLine3);

        Assertions.assertNotNull(validRoute1);
        Assertions.assertNotNull(validRoute2);
        Assertions.assertNotNull(validRoute3);

        Assertions.assertNull(Controller.validateRouteLine(invalidRouteLine1));
        Assertions.assertNull(Controller.validateRouteLine(invalidRouteLine2));
        Assertions.assertNull(Controller.validateRouteLine(invalidRouteLine3));

        Assertions.assertEquals(validRoute1.toString(), "23D,MCTS,23,Fond du lac-National (17-SEP) - DETOUR,This Route is in Detour,3,,008345,");
        Assertions.assertEquals(validRoute2.toString(), "27,MCTS,27,27th Street,,3,,008345,");
        Assertions.assertEquals(validRoute3.toString(), "42U,,,,,,,008345,");

    }

    /**
     * Tests trip header format
     *
     * @author Matthew Wehman
     */
    @Test
    public void validateTripHeader() {
        String validHeader = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
        String invalidHeader = "route_id,se,block_id,shape_id";
        Assertions.assertTrue(Controller.validateTripHeader(validHeader));
        Assertions.assertFalse(Controller.validateTripHeader(invalidHeader));
    }

    /**
     * Tests trip body line format
     *
     * @author Matthew Wehman
     */
    @Test
    public void validateTripBody() {
        String[] validBodies = new String[]{"64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23"
                , "64,17-SEP_SUN,21736569_2545,60TH-VLIET,0,64102,17-SEP_64_0_23",
                "64,17-SEP_SUN,21736573_551,SOUTHRIDGE,1,64102,17-SEP_64_1_19"};

        String[] invalidBodies = new String[]{"64,17-SEP_SUN,21736567_2541," +
                "60TH-VLIET,0,64102,17-SEP_64_0_23,dsoad,dsao,poi", "19", "64,17-SEP_SUN,21736567_2541"};

        for (String validBody : validBodies) {
            Assertions.assertNotNull(Controller.validateTripLines(validBody));
        }
        for (String invalidBody : invalidBodies) {
            Assertions.assertNull(Controller.validateTripLines(invalidBody));
        }

        String tripString = "64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23";
        Trip trip = Controller.validateTripLines(tripString);

        Assertions.assertEquals("64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23", trip.toString());


        for (String i : invalidBodies) {
            Assertions.assertNull(Controller.validateTripLines(i));
        }
    }

    /**
     * Tests Stop Header Lines
     *
     * @author Patrick McDonald
     */
    @Test
    public void validateStopHeaderLines() {
        Assertions.assertTrue(Controller.validateStopHeader("stop_id,stop_name,stop_desc,stop_lat,stop_lon"));
    }

    /**
     * Validates individual Stop Lines
     *
     * @author Patrick McDonald
     */
    @Test
    public void validateStopBodyLines() {
        String correctStopBodies[] = {"1785,NATIONAL & S6 #1785,,43.0231768,-87.9184932",
                "1801,S92 & ORCHARD #1801,,43.0138967,-87.8935061",
                "1932,S13 & ABBOTT #1932,,42.9495066,-87.9292056"};

        String incorrectStopBodies[] = {"4361,PROSPECT & ALBION #4361,,43.0498663",
                "14361,,,43.0498663,", "PROSPECT & ALBION #4361,afdasd,43.0498663,",
                "4361,PROSPECT & ALBION #4361,,-91,0.000", "4361,PROSPECT & ALBION #4361,,91,0.000",
                "4361,PROSPECT & ALBION #4361,,0.000,-181", "4361,PROSPECT & ALBION #4361,,0.00,181"
        };

        for (String correctStopBody : correctStopBodies) {
            Assertions.assertNotNull(Controller.validateLinesInStop(correctStopBody));
        }
        for (String incorrectStopBody : incorrectStopBodies) {
            Assertions.assertNull(Controller.validateLinesInStop(incorrectStopBody));
        }

        Assertions.assertEquals(Controller.validateLinesInStop(correctStopBodies[0]).toString(),
                "1785,NATIONAL & S6 #1785,,43.0231768,-87.9184932");
        Assertions.assertEquals(Controller.validateLinesInStop(correctStopBodies[1]).toString(),
                "1801,S92 & ORCHARD #1801,,43.0138967,-87.8935061");
        Assertions.assertEquals(Controller.validateLinesInStop(correctStopBodies[2]).toString(),
                "1932,S13 & ABBOTT #1932,,42.9495066,-87.9292056");
    }

    /**
     * Validates a line in the StopTime file
     *
     * @author Ian Czerkis
     */
    @Test
    public void validateFirstStopTimeLine() {
        String correctFirstLine = "trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
                "stop_headsign,pickup_type,drop_off_type";
        Assertions.assertFalse(Controller.validateFirstStopTimeLine("fail"));
        Assertions.assertTrue(Controller.validateFirstStopTimeLine(correctFirstLine));
        Assertions.assertFalse(Controller.validateFirstStopTimeLine(correctFirstLine + ",test"));
    }

    /**
     * validates the first line in the StopTime file
     *
     * @author Ian Czerkis
     */
    @Test
    public void validateStopTimeLine() {
        String[] correctFormats = {"21736564_2535,08:51:00,08:51:00,9113,1,,0,0",
                "217312321_1231,09:12:00,12:09:00,1,1,,,",
                "21849620_1284,22:47:00,22:47:00,874,52,,0,0"};
        String[] incorrectFormats = {"fail", "212340_34532,22:47:00",
                "21849620_1284,22:47:00,22:47:00,874,52,0,0",
                "21794282_2306,,11:24:00,10,48,,0,0"};

        for (String c : correctFormats) {
            Assertions.assertNotNull(Controller.validateStopTimeLine(c));
        }
        for (String i : incorrectFormats) {
            Assertions.assertNull(Controller.validateStopTimeLine(i));
        }

        for (String c : correctFormats) {
            StopTime test = Controller.validateStopTimeLine(c);
            Assertions.assertEquals(c, test.toString());
        }
    }

    /**
     * tests the trip per stop method
     *
     * @author Ian Czerkis
     */
    @Test
    public void testTripsPerStop() {
        Assertions.assertEquals(58, controller.tripsPerStop("6712"));
        Assertions.assertEquals(149, controller.tripsPerStop("4628"));
        Assertions.assertEquals(80, controller.tripsPerStop("8298"));
        Assertions.assertEquals(72, controller.tripsPerStop("1557"));
        Assertions.assertEquals(98, controller.tripsPerStop("5224"));
    }

    /**
     * Tests routesContainingStop method against known values
     *
     * @author Matthew Wehman
     */
    @Test
    public void testRoutesContainingStop(){
        String[] stops = new String[]{"1801","5006"};
        ArrayList<String> firstRoutes = new ArrayList<String>(
                List.of("67")
        );
        ArrayList<String> secRoutes = new ArrayList<String>(
                List.of("50")
        );
        ArrayList<String> finalRoutes = new ArrayList<>(
                List.of("57D", "GRE", "143")
        );
        ArrayList<String>[] routes = new ArrayList[]{firstRoutes, secRoutes, finalRoutes};
        for (int i = 0; i < stops.length; i++) {
            ArrayList<String> found = controller.routesContainingStop(stops[i]);
            Assertions.assertEquals(routes[i], found);
        }
    }

    @Test
    public void assertAllLinesEqual() throws IOException {
        System.out.println("start");
        String[] types = {"stops", "trips", "routes", "stop_times"};
        boolean correctExportFiles = true;
        for (String type: types){
            File firstFile = new File("./GTFSFiles/" + type + ".txt");
            File checkFile = new File("./export/" + type + ".txt");
            List<String> checkLines = Files.lines(checkFile.toPath()).toList();
            List<String> firstLines = Files.lines(firstFile.toPath()).toList();
            HashSet<String> set = new HashSet<>(checkLines);
            for (String line: firstLines){
                if (!set.contains(line)){
                    System.out.println("Expected: " + line + ", Not found");
                    correctExportFiles = false;
                }
            }
            System.out.println("Type: " + type + " tested");
        }
        Assertions.assertTrue(correctExportFiles);

    }

}