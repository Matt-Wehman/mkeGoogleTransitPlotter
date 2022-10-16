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
 * Starts the GUI
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainDisplay.fxml")));
        FXMLLoader primaryLoader = new FXMLLoader();

        stage.setTitle("GTSF APP");

        stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));

        stage.show();

        FXMLLoader routeLoader = new FXMLLoader();

        Parent routeRoot = routeLoader.load(getClass()
                .getResource("routeDisplay.fxml").openStream());

        //Create secondary stage (Instantiation)
        Stage routeStage = new Stage();

        //Secondary Stage/Window
        routeStage.setTitle("Route info");
        routeStage.setScene(new Scene(routeRoot));
        routeStage.hide();


        FXMLLoader tripLoader = new FXMLLoader();

        Parent tripRoot = routeLoader.load(getClass()
                .getResource("tripDisplay.fxml").openStream());

        //Create secondary stage (Instantiation)
        Stage tripStage = new Stage();

        //Secondary Stage/Window
        routeStage.setTitle("Route info");
        routeStage.setScene(new Scene(tripRoot));
        routeStage.hide();


        FXMLLoader stopLoader = new FXMLLoader();

        Parent stopRoot = routeLoader.load(getClass()
                .getResource("stopDisplay.fxml").openStream());

        //Create secondary stage (Instantiation)
        Stage stopStage = new Stage();

        //Secondary Stage/Window
        routeStage.setTitle("Route info");
        routeStage.setScene(new Scene(tripRoot));
        routeStage.hide();

        Controller primaryController = primaryLoader.getController();

        primaryController.setRouteStage(routeStage);

        primaryController.setTripStage(tripStage);

        primaryController.setStopStage(stopStage);



    }
}