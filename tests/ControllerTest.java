
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
//    public void validateStopBodyLines(){
//        Assertions.assertEquals(,Controller.validateLinesInStop(
//                "1801,S92 & ORCHARD #1801,,43.0138967,-88.0272162"));
//        Assertions.assertNull(Controller.validateLinesInStop(
//                "1801,S92 & ORCHARD #1801,,43.0138967,"));
//    }


}