import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * This class handles the method for the stop display
 * @author wehmanm
 * @version 1.0
 */
public class StopController {

    Controller controller;

    @FXML
    Text tripsText;

    @FXML
    Text stopID;

    @FXML
    Text routeList;

    @FXML
    Text nextTrip;


    public void setController(Controller controller){
        this.controller = controller;
    }

    protected void setTripsText(String string){
        tripsText.setText(string);
    }

    protected void setStopID(String string){
        stopID.setText(string);
    }

    protected void setRoutesText(String string) {
        routeList.setText(string);
    }

    protected void setNextTrip(String string){
        nextTrip.setText(string);
    }

}
