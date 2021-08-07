package reservation;



import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.rmi.CORBA.Tie;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


public class reservationService  {
	
	private final int openTime=9;
	private final int closeTime=18;
	private final int maxOfDay=60;
	private final int maxPeopleByTime=6;
	private reservationDao reservationDao=new reservationDao();
	
	public void showDatePage() {
		System.out.println("showDatePage");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationPage.fxml"));
		Parent root=loadPageAndGetParent(loader);
		LocalDate today=LocalDate.now();
		YearMonth yearMonth=YearMonth.from(today);
		int lastDay=yearMonth.lengthOfMonth();

		getMonth(root,today);
		getDays(root, lastDay);
		showStage(root, "reservationPage");

		mainController mainController=loader.getController();
		mainController.setParent(root);
		
	}
	private Parent loadPageAndGetParent(FXMLLoader loader) {
		System.out.println("loadPageAndGetParent");
		try {
		Parent	root = loader.load();
		return root;
		} catch (IOException e) {
			System.out.println("parent �ε��� �����߻�");
			e.printStackTrace();
		}
		return null;
	}
	private void getMonth(Parent root,LocalDate today) {
		System.out.println("getMonth");
		Label month=(Label) root.lookup("#month");
		month.setText(Integer.toString(today.getMonthValue()));
	}
	private void getDays(Parent root,int lastDay) {
		System.out.println("getDays");
		Label month=(Label) root.lookup("#month");
		
		for(int i=1;i<=lastDay;i++) {
			Button button=(Button) root.lookup("#day"+i);
			button.setText(Integer.toString(i));
			if(checkFullDay(stringToTimestamp(month.getText(),i))||compareDate(stringToTimestamp(month.getText(), i),LocalDateTime.now())) {
				System.out.println(i+"���� 6������ �� á�ų� ���� �����Դϴ�");
				button.setDisable(true);
			}
		}
		
		if(lastDay==30) {
			Button button=(Button) root.lookup("#day31");
			button.setText("x");
		}
	}
	private void showStage(Parent root,String pageName ) {
		System.out.println("showStage");
		Stage stage=new Stage(); 
		stage.setTitle(pageName);
		stage.setScene(new Scene(root));
		stage.show();
	}
	public boolean checkFullDay(Timestamp timestamp) {
		System.out.println("checkFullDay"+timestamp);
		List<reservationDto>array=findByDate(timestamp);
		System.out.println(array.size()+"���ళ��");
		if(array.size()>=maxOfDay) {
			return true;
		}
		return false;
	}
	public List<reservationDto> findByDate(Timestamp timestamp) {
		System.out.println("findByDate");
		return reservationDao.findAllByDate(timestamp);
	}
	public boolean compareDate(Timestamp timestamp,LocalDateTime localDateTime) {
		LocalDateTime loaDateTime2=timestamp.toLocalDateTime();
		
		if(localDateTime.getDayOfMonth()==loaDateTime2.getDayOfMonth()){
			return false;
		}
		else if(localDateTime.isAfter(loaDateTime2)){
           return true;
        }
        return false;
	}
	public void showTimePage(Parent parent,int day) {
		System.out.println("showTimePage");
		FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("showTimePage.fxml"));
		try {
			Parent parent2=fxmlLoader.load();
			Stage stage=new Stage();
			
			getTimes(parent2,parent,day);
			stage.setScene(new Scene(parent2));
			stage.show();
			
			mainController mainController=fxmlLoader.getController();
			mainController.setParent2(parent,parent2,day);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void getTimes(Parent parent2,Parent parent,int day) {
		System.out.println("getTimes"+day);
		LocalDateTime localDateTime=LocalDateTime.now();
		Label month=(Label)parent.lookup("#month");
	
		
		for(int i=openTime;i<=closeTime;i++) {
			RadioButton radioButton=(RadioButton) parent2.lookup("#rdaio"+i);
			radioButton.setText(i+"��~"+(i+1)+"��");
			if(i<=localDateTime.getHour()||compareTime(month, day,i)) {
				radioButton.setDisable(true);
			}
		}
	}
	public boolean compareTime(Label month,int day,int time) {
		List<reservationDto>array= findByDate(stringToTimestamp(month.getText(),day));
		int temp=0;
		for(reservationDto r:array) {
			System.out.println(r.getTime()+"�ð�"+time);
			System.out.println(temp);
			if(r.getTime()==time) {
				temp++;
			}
			if(temp==maxPeopleByTime) {
				System.out.println(time+"�� ����ο��� ��á���ϴ�");
				temp=0;
				return true;
			}
		}
		temp=0;
		return false;
	}
	public void insert(Parent parent,String email,String name,Parent parent2,int day) {
		try {
			if(day==0&&day>31) {
				System.out.println("�߸��� ��¥ �����Դϴ�");
				return;
			}
			Label month=(Label)parent.lookup("#month");
			System.out.println("�����"+month.getText());
			System.out.println("������"+day);
		
			Timestamp rDate=stringToTimestamp(month.getText(),day);
			System.out.println("������� "+rDate);
			
			Timestamp created=Timestamp.valueOf(LocalDateTime.now());
			System.out.println("�������� "+created);
			
			reservationDto reservationDto=new reservationDto();
			reservationDto.setEmail(email);
			reservationDto.setName(name);
			reservationDto.setCreated(created);
			reservationDto.setTime(getSelectTime(parent2));
			reservationDto.setrDate(rDate);
			reservationDao.insert(reservationDto);
		} catch (Exception e) {
			System.out.println("������ ���� �߻�");
			e.printStackTrace();
		}
			
	}
	public int getSelectTime(Parent parent2) {
		List<RadioButton>array=new ArrayList<RadioButton>();
		LocalDateTime localDateTime=LocalDateTime.now();
		int time=0;
		for(int i=localDateTime.getHour();i<=closeTime;i++) {
			array.add((RadioButton)parent2.lookup("#rdaio"+i));
		}
		for(RadioButton r:array) {
			if(r.isSelected()) {
				String sTime=r.getText();
				time=Integer.parseInt(sTime.split("~")[0].replace("��",""));
			}
		}
		System.out.println("��� �ð� "+time);
		return time;
	}
	public Timestamp stringToTimestamp(String month,int day) {
		return Timestamp.valueOf("2021-"+month+"-"+day+" 00:00:00");
	}
	

}
