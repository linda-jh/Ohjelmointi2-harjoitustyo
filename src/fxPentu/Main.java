package fxPentu;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author linda
 * @version 4.2.2020
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/**BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PentuGUI.fxml"));
			BorderPane root2 = (BorderPane)FXMLLoader.load(getClass().getResource("PentuNimiView.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("pentu.css").toExternalForm());
			Scene scene2 = new Scene(root2);
            scene.getStylesheets().add(getClass().getResource("pentu.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setScene(scene2);
            primaryStage.show();*/
            
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PentuGUI.fxml"));
            final BorderPane root = (BorderPane)ldr.load();
            final PentuGUIController pentuCtrl = (PentuGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("pentu.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pentu");
            
            // Platform.setImplicitExit(false); // tätä ei kai saa laittaa

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
