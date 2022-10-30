/**
 * @author bassoc wehman
 * @version 1.0
 * @created 05-Oct-2022 12:59:52 PM
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Starts the GUI 2
 */
public class Main extends Application {
    /**
     * Width for JavaFX Scene
     */
    public static final int SCENE_WIDTH = 600;
    /**
     * Height for JavaFX Scene
     */
    public static final int SCENE_HEIGHT = 400;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader primaryLoader = new FXMLLoader();

        Parent root = primaryLoader.load(Objects.requireNonNull(getClass().getResource("MainDisplay.fxml")).openStream());

        stage.setTitle("GTFS APP");

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));

        stage.show();

        FXMLLoader routeLoader = new FXMLLoader();

        Parent routeRoot = routeLoader.load(getClass()
                .getResource("routeDisplay.fxml").openStream());

        //Create route stage (Instantiation)
        Stage routeStage = new Stage();

        //Route Stage/Window
        routeStage.setTitle("Route info");
        routeStage.setScene(new Scene(routeRoot));
        routeStage.hide();


        FXMLLoader tripLoader = new FXMLLoader();

        Parent tripRoot = tripLoader.load(getClass()
                .getResource("tripDisplay.fxml").openStream());

        //Create trip stage (Instantiation)
        Stage tripStage = new Stage();

        //Trip Stage/Window
        tripStage.setTitle("Route info");
        tripStage.setScene(new Scene(tripRoot));
        tripStage.hide();


        FXMLLoader stopLoader = new FXMLLoader();

        Parent stopRoot = stopLoader.load(getClass()
                .getResource("stopDisplay.fxml").openStream());

        //Create Stop stage (Instantiation)
        Stage stopStage = new Stage();

        // Stop Stage/Window
        stopStage.setTitle("Route info");
        stopStage.setScene(new Scene(stopRoot));
        stopStage.hide();

        Controller primaryController = primaryLoader.getController();

        StopController stopController = stopLoader.getController();

        primaryController.setRouteStage(routeStage);

        primaryController.setTripStage(tripStage);

        primaryController.setStopStage(stopStage);

        stopController.setController(primaryController);

        primaryController.setStopController(stopController);
    }
}