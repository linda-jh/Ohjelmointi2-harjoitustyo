package fxPentu;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import pentu.Pentu;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Pääohjelma Penturekisterin käynnistämiseksi.
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 4.2.2020
 *
 */
public class Main extends Application {
    
	@Override
	public void start(Stage primaryStage) {
	    
	    primaryStage.setResizable(false);
	    
		try {

		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PentuGUI.fxml"));
            final Pane root = (Pane)ldr.load();
            final PentuGUIController pentuCtrl = (PentuGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("pentu.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Penturekisteri");
            
            Pentu pentu = new Pentu();
            pentuCtrl.setPentu(pentu);

            primaryStage.setOnCloseRequest((event) -> {
                    if ( !pentuCtrl.voikoSulkea() ) event.consume();
                });
            
            primaryStage.show();
            if ( !pentuCtrl.avaa() ) Platform.exit();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
