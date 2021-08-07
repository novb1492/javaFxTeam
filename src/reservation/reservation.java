package reservation;






import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class reservation extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//reservationService.showDatePage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
		Parent root = loader.load();
		
		primaryStage.setTitle("EX7");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		mainController control = loader.getController();
		control.setParent(root);
	}
}
