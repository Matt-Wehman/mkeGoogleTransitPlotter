import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerTest {


    /**
     * Tests route header validation
     * @author Christian Basso
     */
    public void testValidateRouteHeader() {
    }

    /**
     * Tests route header lines
     * @author Christian Basso
     */
    public void testValidateRouteLine() {
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
        for(int i = 0; i < validBodies.length; i++){
            Assertions.assertNotNull(Controller.validateTripLines(validBodies[i]));
        }
        for(int i = 0; i < invalidBodies.length; i++){
            Assertions.assertNull(Controller.validateTripLines(invalidBodies[i]));
        }
    }

    /**
     * Tests Stop Header Lines
     * @author Patrick McDonald
     */
    @Test
    public void validateStopHeaderLines(){
        Assertions.assertTrue(Controller.validateStopHeader("stop_id,stop_name,stop_desc,stop_lat,stop_lon"));
    }

    /**
     * Validates individual Stop Lines
     * @author Patrick McDonald
     */
    @Test
    public void validateStopBodyLines(){
        //Assertions.assertEquals(,
        Controller.validateLinesInStop(
                "1801,S92 & ORCHARD #1801,,43.0138967,-88.0272162");
//        Assertions.assertNull(Controller.validateLinesInStop(
//                "1801,S92 & ORCHARD #1801,,43.0138967,"));
    }


}