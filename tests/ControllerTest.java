import junit.framework.TestCase;
import org.junit.Assert;

public class ControllerTest extends TestCase {


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
     * Tests trip header lines
     */
    public void testValidateTripHeader() {
        assertTrue(Controller.validateTripHeader("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id"));
        assertFalse(Controller.validateTripHeader("route_id,service_id,trip_id,block_id,shape_id"));
    }

    public void testValidateTripLines() {
        Assert.assertNotNull("Failed", Controller.validateTripLines("64,17-SEP_SUN,21736565_2537,60TH-VLIET,0,64102,17-SEP_64_0_23"));
        assertNull(Controller.validateTripLines("64,17-S64102,17-SEP_64_0_23"));
    }
}