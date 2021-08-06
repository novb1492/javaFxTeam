package reservation;



import java.sql.Timestamp;
import java.time.LocalDateTime;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class reservationService  {
	private int choiceDay;
	private reservationDao reservationDao=new reservationDao();
	public void setDate(int choiceDay) {
		this.choiceDay=choiceDay;
	}
	public void insert(Parent parent,String email,String name) {
		if(choiceDay==0&&choiceDay>31) {
			System.out.println("잘못된 날짜 선택입니다");
			return;
		}
		Label month=(Label)parent.lookup("#month");
		System.out.println(month.getText());
		System.out.println(choiceDay);
		
		int time=5;

		
		Timestamp rDate=Timestamp.valueOf("2021-"+month.getText()+"-"+choiceDay+" "+time+":00:00");
		System.out.println(rDate);
		
		Timestamp created=Timestamp.valueOf(LocalDateTime.now());
		System.out.println(created);
		reservationDto reservationDto=new reservationDto();
		reservationDto.setEmail(email);
		reservationDto.setName(name);
		reservationDto.setCreated(created);
		reservationDto.setTime(time);
		reservationDto.setrDate(rDate);
		reservationDao.insert(reservationDto);
	}
	public void showTimePage() {
		System.out.println("showTimePage");
		FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("showTimePage.fxml"));
		try {
			Parent parent=fxmlLoader.load();
			Stage stage=new Stage();
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
