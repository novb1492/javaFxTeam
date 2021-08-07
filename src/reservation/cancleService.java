package reservation;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class cancleService {

	public void windowClose(ActionEvent actionEvent) {
		System.out.println("windowClose");
		Parent parent= (Parent) actionEvent.getSource();
		Stage stage = (Stage) parent.getScene().getWindow();
		stage.close();
	}
}
