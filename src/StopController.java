import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StopController {

    Controller controller;

    @FXML
    Text tripsText;

    @FXML
    Text stopID;

    public void setController(Controller controller){
        this.controller = controller;
    }

    protected void setTripsText(String string){
        tripsText.setText(string);
    }

    protected void setStopID(String string){
        stopID.setText(string);
    }
}
