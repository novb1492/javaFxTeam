package reservation;



import java.sql.Timestamp;
import java.time.LocalDateTime;

import javafx.scene.Parent;
import javafx.scene.control.Label;


public class reservationService  {
	private int choiceDay;
	private reservationDao reservationDao=new reservationDao();
	public void setDate(int choiceDay) {
		this.choiceDay=choiceDay;
	}
	public void insert(Parent parent,String email,String name) {
		if(choiceDay==0&&choiceDay>31) {
			System.out.println("�߸��� ��¥ �����Դϴ�");
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
	

}
