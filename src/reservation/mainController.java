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
	private Parent parent2;
	private String ab;
	private reservationService reservationService;
	int day;



	
	
	public void setParent(Parent parent) {
		this.parent=parent;
	}
	public void setParent2(Parent parent,Parent parent2,int day) {
		this.parent=parent;
		this.parent2=parent2;
		this.day=day;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reservationService=new reservationService();
	}
	public void click() {
		System.out.println("1");
		day=1;
	}
	public void click2() {
		System.out.println("2");
		day=2;
	}
	public void insert() {
		System.out.println("insert"+ab);
		reservationService.insert(parent,"kim@kim.com","kim",parent2,day);
	}
	public void show() {
		System.out.println("show");
		System.out.println(day);
		reservationService.showTimePage(parent,day);
	}



}
