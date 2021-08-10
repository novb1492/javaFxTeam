package reservation;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.Parent;



public class mainController  implements Initializable  {
	
	private Parent parent;
	private Parent parent2;
	private reservationService reservationService;
	private cancleService cancleService;
	private int plusMonth;
	private int day;
	private String tempEmail="kim@kim.com";
	private String tempName="kim";
	
	public void setParent(Parent parent) {
		this.parent=parent;
	}
	public void setinsert(Parent parent,Parent parent2,int day) {
		this.parent=parent;
		this.parent2=parent2;
		this.day=day;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		reservationService=new reservationService();
		cancleService=new cancleService();
	}
	public void click() {
		System.out.println("click1");
		day=1;
	}
	public void click2() {
		System.out.println("clcik2");
		day=2;
	}public void click3() {
		System.out.println("click3");
		day=3;
	}
	public void click4() {
		System.out.println("click4");
		day=4;
	}
	public void click5() {
		System.out.println("click5");
		day=5;
	}
	public void click6() {
		System.out.println("click6");
		day=6;
	}
	public void click7() {
		System.out.println("click7");
		day=7;
	}
	public void click8() {
		System.out.println("click8");
		day=8;
	}
	public void click9() {
		System.out.println("click9");
		day=9;
	}
	public void click10() {
		System.out.println("click10");
		day=10;
	}
	public void click11() {
		System.out.println("click11");
		day=11;
	}
	public void click12() {
		System.out.println("click12");
		LocalDate date = LocalDate.of(2021,8,1);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		System.out.println(dayOfWeek.getValue());
		day=12-dayOfWeek.getValue();
	}
	public void click13() {
		System.out.println("click13");
		day=13;
	}
	public void click14() {
		System.out.println("click14");
		day=14;
	}
	public void click15() {
		System.out.println("click15");
		day=15;
	}
	public void click16() {
		System.out.println("click16");
		day=16;
	}
	public void click17() {
		System.out.println("click17");
		day=17;
	}
	public void click18() {
		System.out.println("click18");
		LocalDate date = LocalDate.of(2021,8,1);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		System.out.println(dayOfWeek.getValue());
		day=18-dayOfWeek.getValue()+1;
	}
	public void click19() {
		System.out.println("click19");
		day=19;
	}
	public void click20() {
		System.out.println("click20");
		day=20;
	}
	public void click21() {
		System.out.println("click21");
		day=21;
	}
	public void click22() {
		System.out.println("click22");
		day=22;
	}
	public void click23() {
		System.out.println("click23");
		day=23;
	}
	public void click24() {
		System.out.println("click24");
		day=24;
	}
	public void click25() {
		System.out.println("click25");
		day=25;
	}
	public void click26() {
		System.out.println("click26");
		day=26;
	}
	public void click27() {
		System.out.println("click27");
		day=27;
	}
	public void click28() {
		System.out.println("click28");
		day=28;
	}
	public void click29() {
		System.out.println("click29");
		day=29;
	}
	public void click30() {
		System.out.println("click30");
		day=30;
	}
	public void click31() {
		System.out.println("click31");
		day=31;
	}
	public void click32() {
		System.out.println("click32");
		day=32;
	}
	public void click33() {
		System.out.println("click33");
		day=33;
	}
	public void click34() {
		System.out.println("click34");
		day=34;
	}
	public void click35() {
		System.out.println("click35");
		day=35;
	}
	public void click36() {
		System.out.println("click36");
		day=36;
	}
	public void click37() {
		System.out.println("click37");
		LocalDate date = LocalDate.of(2021,8,1);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		System.out.println(dayOfWeek.getValue());
		day=37-dayOfWeek.getValue()+1;
	}
	public void insert() {
		reservationService.insert(parent,"kim@kim.com","kim",parent2,day);
	}
	public void show() {
		reservationService.showTimePage(parent,day);
	}
	public void goToShowRerservationPage() {
		System.out.println("여기입니다");
	}
	public void showDatePage() {
		reservationService.showDatePage(parent,tempEmail,tempName,plusMonth);
	}
	public void CancelProc(ActionEvent actionEvent) {
		cancleService.windowClose(actionEvent);
	}

}
