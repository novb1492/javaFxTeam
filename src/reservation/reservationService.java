package reservation;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javafx.scene.Parent;


public class reservationService  {
	private int choiceDay;
	public void setDate(int choiceDay) {
		this.choiceDay=choiceDay;
		System.out.println(this.choiceDay);
	}

}
