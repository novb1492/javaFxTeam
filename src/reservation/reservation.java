package reservation;


import java.time.LocalDate;
import java.time.YearMonth;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class reservation extends Application{
	private reservationService reservationService=new reservationService();
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		reservationService.showDatePage();
		
	}
}
