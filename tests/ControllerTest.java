
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerTest {


    /**
     * Tests route header validation
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

        Route validRoute = new Route("23D", "MCTS", "23", "Fond du lac-National (17-SEP) - DETOUR", "This Route is in Detour", 3, -1, "008345", -1);

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

    @Test
    public void validateTripHeader(){
        String validHeader = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
        String invalidHeader =  "route_id,se,block_id,shape_id";
        Assertions.assertTrue(Controller.validateTripHeader(validHeader));
        Assertions.assertFalse(Controller.validateTripHeader(invalidHeader));
    }
    @Test
    public void validateTripBody(){
        String[] validBodies = new String[]{"64,17-SEP_SUN,21736567_2541,60TH-VLIET,0,64102,17-SEP_64_0_23"
                ,"64,17-SEP_SUN,21736569_2545,60TH-VLIET,0,64102,17-SEP_64_0_23",
        "64,17-SEP_SUN,21736573_551,SOUTHRIDGE,1,64102,17-SEP_64_1_19"};
        String[] invalidBodies = new String[]{"64,17-SEP_SUN,21736567_2541," +
                "60TH-VLIET,0,64102,17-SEP_64_0_23,dsoad,dsao,poi", "19", "64,17-SEP_SUN,21736567_2541" };
        for (String validBody : validBodies) {
            Assertions.assertNotNull(Controller.validateTripLines(validBody));
        }
        for (String invalidBody : invalidBodies) {
            Assertions.assertNull(Controller.validateTripLines(invalidBody));
        }

        for (String validBody: validBodies){
            Trip test = Controller.validateTripLines(validBody);
            Assertions.assertEquals(validBody, test.toString());
        }
    }

    /**
     * Tests Stop Header Lines
     * @author Patrick McDonald
     */
    @Test
    public void validateStopHeaderLines(){
        Assertions.assertTrue(Controller.validateStopHeader("stop_id,stop_name,stop_desc,stop_lat,stop_lon"));
        Assertions.assertFalse(Controller.validateStopHeader("fail"));
    }

    /**
     * Validates individual Stop Lines
     * @author Patrick McDonald
     */
    @Test
    public void validateStopBodyLines(){
        Stop correctStop1 = (Controller.validateLinesInStop(
                "1801,S92 & ORCHARD #1801,,43.0138967,-87.8935061"));
        Assertions.assertNotNull(correctStop1);

        Stop correctStop2 = (Controller.validateLinesInStop(
                "1785,NATIONAL & S6 #1785,,43.0231768,-87.9184932"));
        Assertions.assertNotNull(correctStop2);

        Stop badStop1 = Controller.validateLinesInStop(
                "4361,PROSPECT & ALBION #4361,,43.0498663,");
        Assertions.assertNull(badStop1);


        Stop badStop2 = Controller.validateLinesInStop(
                "4361,,,43.0498663,");
        Assertions.assertNull(badStop2);

        Stop badStop3 = Controller.validateLinesInStop(
                "PROSPECT & ALBION #4361,afdasd,43.0498663,");
        Assertions.assertNull(badStop3);

        Assertions.assertEquals(correctStop1.toString(), "1801,S92 & ORCHARD #1801,,43.0138967,-87.8935061");
        Assertions.assertEquals(correctStop2.toString(), "1785,NATIONAL & S6 #1785,,43.0231768,-87.9184932");
    }

    /**
     * Validates a line in the StopTime file
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

        for (String c: correctFormats){
            Assertions.assertNotNull(Controller.validateStopTimeLine(c));
        }
        for (String i: incorrectFormats){
            Assertions.assertNull(Controller.validateStopTimeLine(i));
        }

        for (String c: correctFormats){
            StopTime test = Controller.validateStopTimeLine(c);
            Assertions.assertEquals(c, test.toString());
        }
    }

}