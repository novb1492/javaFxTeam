package reservation;



import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class reservationService  {
	
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
				System.out.println(i+"일은 6예약이 다 찼거나 지난 요일입니다");
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
		System.out.println(array.size()+"예약개수");
		if(array.size()>=6) {
			return true;
		}
		return false;
	}
	public List<reservationDto> findByDate(Timestamp timestamp) {
		return reservationDao.findAllByDate(timestamp);
	}
	public void showTimePage(Parent parent,int day) {
		System.out.println("showTimePage");
		FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("showTimePage.fxml"));
		try {
			Parent parent2=fxmlLoader.load();
			Stage stage=new Stage();
			stage.setScene(new Scene(parent2));
			stage.show();
			
			mainController mainController=fxmlLoader.getController();
			mainController.setParent2(parent,parent2,day);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public void insert(Parent parent,String email,String name,Parent parent2,int day) {
		try {
			if(day==0&&day>31) {
				System.out.println("잘못된 날짜 선택입니다");
				return;
			}
			Label month=(Label)parent.lookup("#month");
			System.out.println("예약월"+month.getText());
			System.out.println("예약일"+day);
			
			int time=5;
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
		} catch (Exception e) {
			System.out.println("예약중 오류 발생");
			e.printStackTrace();
		}
			
	}
	public Timestamp stringToTimestamp(String month,int day) {
		return Timestamp.valueOf("2021-"+month+"-"+day+" 00:00:00");
	}
	

}
