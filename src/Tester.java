

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

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