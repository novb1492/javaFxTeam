package reservation;



import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	public void showDatePage(Parent parent,String email,String name,int plusMonth) {
		System.out.println("showDatePage");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("reservationPage.fxml"));
		Parent root=loadPageAndGetParent(loader);
		Button nextButton=(Button) root.lookup("#nextButton");
		
		nextButton.setOnMouseClicked(event->{
			System.out.println(event.getClickCount());
			int p=2;
			showDatePage(root, email, name,p);
		});
		LocalDate today=LocalDate.now().plusMonths(1);
		YearMonth yearMonth=YearMonth.from(today);
		int lastDay=yearMonth.lengthOfMonth();
		int start=0;
		LocalDate date = LocalDate.of(2021,today.getMonthValue(),1);
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		System.out.println(dayOfWeek.getValue());
	
		int temp=1;
		start=dayOfWeek.getValue();
		System.out.println(temp+"temp");
		for(int i=start;i<lastDay+start;i++) {
			System.out.println(i);
			Button button=(Button) root.lookup("#day"+i);
			button.setText(Integer.toString(temp));
			temp+=1;
		}
		
		showName(root, name);
		getMonth(root,today);
		//getDays(root, lastDay);
		showStage(root, "reservationPage");

		mainController mainController=loader.getController();
		mainController.setParent(root);
		
	}
	public void showName(Parent root,String name) {
		System.out.println("showname");
		Label showEmail=(Label)root.lookup("#name");
		showEmail.setText(name);
	}
	private Parent loadPageAndGetParent(FXMLLoader loader) {
		System.out.println("loadPageAndGetParent");
		try {
		Parent	root = loader.load();
		return root;
		} catch (IOException e) {
			System.out.println("parent 로드중 오류발생");
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
				System.out.println(i+"일은 예약이 다 찼거나 지난 요일입니다");
				button.setDisable(true);
			}
		}
		checkCloseHour(root);
		if(lastDay==30) {
			System.out.println("끝달이 31이 아닙니다");
			Button button=(Button) root.lookup("#day31");
			button.setText("x");
			button.setDisable(true);
		}
		else if(lastDay==29||lastDay==28) {
			System.out.println("끝달이 28/29입니다");
			List<Button>buttons=new ArrayList<Button>();
			for(int i=29;i<=31;i++) {
				buttons.add((Button) root.lookup("#day"+i));
			}
			int temp=0;
			if(lastDay==29) {
				temp=1;
			}
			for(int i=temp;i<buttons.size();i++) {
				Button button=buttons.get(i);
				button.setText("x");
				button.setDisable(true);
			}
		}
	}
	public void checkCloseHour(Parent root) {
		System.out.println("checkCloseHour");
		if(LocalDateTime.now().getHour()>=closeTime) {
			System.out.println("영업이 종료 되었습니다");
			Button button=(Button) root.lookup("#day"+LocalDateTime.now().getDayOfMonth());
			button.setDisable(true);
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
		System.out.println(array.size()+"예약개수");
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
		Parent parent2=loadPageAndGetParent(fxmlLoader);
	
		Label getReservationPageMonth=(Label) parent.lookup("#month");
		System.out.println(getReservationPageMonth.getText()+"달");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Label showSelectDate=(Label) parent2.lookup("#labelDate");
		showSelectDate.setText(sdf.format(stringToTimestamp(getReservationPageMonth.getText(), day)));
		
		getTimes(parent2,parent,day);
		showStage(parent2,"showTimePage");
		
		mainController mainController=fxmlLoader.getController();
		mainController.setinsert(parent,parent2,day);
	}
	private void getTimes(Parent parent2,Parent parent,int day) {
		System.out.println("getTimes"+day);
		LocalDateTime localDateTime=LocalDateTime.now();
		Label month=(Label)parent.lookup("#month");
	
		
		for(int i=openTime;i<=closeTime;i++) {
			RadioButton radioButton=(RadioButton) parent2.lookup("#rdaio"+i);
			radioButton.setText(i+"시~"+(i+1)+"시");
			if(compareTime(month, day,i)) {
				radioButton.setDisable(true);
			}
			if(day==localDateTime.getDayOfMonth()) {
				if(i<=localDateTime.getHour()) {
					radioButton.setDisable(true);
				}
			}
		}
	}
	public boolean compareTime(Label month,int day,int time) {
		System.out.println("compareTime");
		List<reservationDto>array= findByDate(stringToTimestamp(month.getText(),day));
		int temp=0;
		for(reservationDto r:array) {
			if(r.getTime()==time) {
				temp++;
			}
			if(temp==maxPeopleByTime) {
				System.out.println(time+"시는 모든인원이 다찼습니다");
				return true;
			}
		}
		return false;
	}
	public void insert(Parent parent,String email,String name,Parent parent2,int day) {
		Alert alert=new Alert(AlertType.ERROR);
		try {
			Label month=(Label)parent.lookup("#month");
			int time=getSelectTime(parent2);
			LocalDateTime localDateTime=LocalDateTime.now(); 
			if(day==0||day>31) {
				System.out.println("잘못된 날짜 선택입니다");
				return;
			}
			if(checkFullDay(stringToTimestamp(month.getText(),day))||compareDate(stringToTimestamp(month.getText(), day),LocalDateTime.now())) {
				System.out.println(day+"일은 예약이 다 찼거나 지난 요일 예약시도 입니다");
				return;
			}
			if(compareTime(month,day,time)) {
				System.out.println(time+"시는 모든인원이 다찼습니다");
				return;
			}
			if(localDateTime.getDayOfMonth()==day) {
				if(time<=localDateTime.getHour()) {
					System.out.println(time+"시는 지난 시간입니다");
					return;
				}
			}
			if(email==null||name==null||email.isEmpty()||name.isEmpty()) {
				System.out.println("회원정보가 없는 예약 시도 입니다");
				return;
			}
			if(month.getText().isEmpty()||month.getText()==null||time==0) {
				System.out.println("예약 정보가 없는 예약 시도입니다");
				return;
			}

			System.out.println("예약월"+month.getText());
			System.out.println("예약일"+day);
		
			Timestamp rDate=stringToTimestamp(month.getText(),day);
			System.out.println("사용일자 "+rDate);
			
			Timestamp created=Timestamp.valueOf(LocalDateTime.now());
			System.out.println("예약일자 "+created);
			
			reservationDto reservationDto=new reservationDto();
			reservationDto.setEmail(email);
			reservationDto.setName(name);
			reservationDto.setCreated(created);
			reservationDto.setTime(time);
			reservationDto.setrDate(rDate);
			reservationDao.insert(reservationDto);
			System.out.println("예약성공");
			closeWindow(parent2);
		} catch (Exception e) {
			System.out.println("예약중 오류 발생");
			e.printStackTrace();
		}
			
	}
	public int getSelectTime(Parent parent2) {
		List<RadioButton>array=new ArrayList<RadioButton>();
		int time=0;
		for(int i=openTime;i<=closeTime;i++) {
			array.add((RadioButton)parent2.lookup("#rdaio"+i));
		}
		for(RadioButton r:array) {
			if(r.isSelected()) {
				String sTime=r.getText();
				time=Integer.parseInt(sTime.split("~")[0].replace("시",""));
			}
		}
		System.out.println("사용 시간 "+time);
		return time;
	}
	public Timestamp stringToTimestamp(String month,int day) {
		return Timestamp.valueOf("2021-"+month+"-"+day+" 00:00:00");
	}
	public void closeWindow(Parent parent) {
		System.out.println("closeWindow");
		Stage stage=(Stage) parent.getScene().getWindow();
		stage.close();
	}

}
