package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//DBUtil에는 필드가 없다 ---> This라는 메서드를 사용할일이 없다. --> static으로 바꿔주자 -> 호출시에 객체를 만들필요가 없어진다. ->메모리 낭비를 막는다
//Math.random 처럼 함수는 자바에 없으니까 static을 써줘서 비슷하게 만들어준다.
public class DBUtil {
	public static Connection getConnection() {
		Connection conn =null;
		try {
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	return conn;
	}
}
