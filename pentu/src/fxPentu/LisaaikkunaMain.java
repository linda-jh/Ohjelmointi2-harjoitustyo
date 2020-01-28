package fxPentu;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author linda
 * @version 28.1.2020
 *
 */
public class LisaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaikkunaGUIView.fxml"));
            final Pane root = ldr.load();
            //final LisaaikkunaGUIController lisaaikkunaCtrl = (LisaaikkunaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lisaaikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("lisaaikkuna");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}