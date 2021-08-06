package reservation;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class reservation extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationPage.fxml"));
		Parent root = loader.load();
		
		LocalDate today=LocalDate.now();
		
		YearMonth yearMonth=YearMonth.from(today);
		int lastDay=yearMonth.lengthOfMonth();
		
		for(int i=1;i<=lastDay;i++) {
			Button button=(Button) root.lookup("#day"+i);
			button.setText(Integer.toString(i));
		}
		
		if(lastDay==30) {
			Button button=(Button) root.lookup("#day31");
			button.setText("x");
		}

		primaryStage.setTitle("reservationPage");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		

	}
}
