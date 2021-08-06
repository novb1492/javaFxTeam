package reservation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class mainController  implements Initializable  {
	
	private Parent parent;
	private reservationService reservationService;

	@FXML private Button day1;
	@FXML private Button day2;


	
	
	public void setParent(Parent parent) {
		this.parent=parent;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reservationService=new reservationService();
		day1.setOnMouseClicked((event)->{
			reservationService.setDate(1);
		});
		day2.setOnMouseClicked((event)->{
			reservationService.setDate(2);
		});
	}
	
	public void onClick() {


	
	}


}
