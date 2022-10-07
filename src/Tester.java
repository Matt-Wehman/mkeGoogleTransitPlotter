import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class Tester {
    @Test
    public void test() {
        Controller c = new Controller();
        ArrayList<File> files = new ArrayList<>();
        files.add(new File("./GTFSFiles/routes.txt"));
        files.add(new File("./GTFSFiles/stops.txt"));
        files.add(new File("./GTFSFiles/trips.txt"));
        files.add(new File("./GTFSFiles/stop_times.txt"));
        c.import_files(files);
    }
}