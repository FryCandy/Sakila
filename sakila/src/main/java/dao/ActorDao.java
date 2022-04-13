package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.Actor;
public class ActorDao {
	//actor 테이블 정보 출력
	public List<Actor> selectActorList(int beginRow, int rowPerPage){
		List<Actor> list = new ArrayList<>();
		//데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "SELECT actor_id actorId,"
				+ "		 first_name firstName,"
				+ "		last_name lastName,"
				+ "		last_update lastUpdate"
				+ "	FROM actor"
				+ "	LIMIT ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Actor a = new Actor();
				a.setActorId(rs.getInt("actorId"));
				a.setFirstName(rs.getString("firstName"));
				a.setLastName(rs.getString("lastName"));
				a.setLastUpdate(rs.getString("lastUpdate"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				//데이터베이스 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	//전체 행의 수 출력
	public int totalRow (){
		int totalRow = 0; //전체행이 들어갈 변수 초기화
		//데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "SELECT count(*) cnt "
				+ "	FROM actor";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalRow = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				//데이터베이스 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return totalRow;
	}
}
