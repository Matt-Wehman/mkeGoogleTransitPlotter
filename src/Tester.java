/**
 * This class handles the methods from the GUI
 * @author czerkisi
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * This is the Testing class that is responsible for checking whether or not the import
 * methods worked correctly
 */
public class Tester {
    @Test
    public void test() {
        Controller c = new Controller();
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("./se-lab2030/GTFSFiles/routes.txt"));
        files.add(new File("./se-lab2030/GTFSFiles/stops.txt"));
        files.add(new File("./se-lab2030/GTFSFiles/trips.txt"));
        files.add(new File("./se-lab2030/GTFSFiles/stop_times.txt"));
        c.importFiles(files);
        Assert.assertEquals(c.allStops.size(), 5392);
        Assert.assertEquals(c.routes.size(), 62);
        Assert.assertEquals(c.trips.size(), 9300);
        System.out.println("done");
    }
}