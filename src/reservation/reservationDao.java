package reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class reservationDao {
	private Connection connection;
	String url="jdbc:oracle:thin:@localhost:1521:xe";
	String user="kim";
	String pwd="1111";
	
	public reservationDao() {
		 try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			this.connection=DriverManager.getConnection(url,user,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void insert(reservationDto reservationDto) {

		String sql="insert into reservation values(?,?,?,?,?,?)";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1,1);
			preparedStatement.setString(2, reservationDto.getEmail());
			preparedStatement.setString(3, reservationDto.getName());
			preparedStatement.setInt(4,reservationDto.getTime());
			preparedStatement.setTimestamp(5, reservationDto.getCreated());
			preparedStatement.setTimestamp(6, reservationDto.getCreated());
			preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
	}
}
