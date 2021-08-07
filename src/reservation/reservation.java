package reservation;





import javafx.application.Application;
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
