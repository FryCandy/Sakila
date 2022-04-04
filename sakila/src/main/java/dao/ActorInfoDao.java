package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.ActorInfo;
public class ActorInfoDao {
	public List<ActorInfo> selectActorInfoListByPage(int beginRow, int rowPerPage){
		List<ActorInfo> list = new ArrayList<>();
		//DB 자원 준비
		Connection conn =null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//static 으로 바꿨기때문에 DBUtil = dbutil(); 객체 생성하지 않아도 된다.
		conn = DBUtil.getConnection(); // DBUtil 내에 DB연결 호출
		String sql = "select actor_id actorId, first_name firstName, last_name lastName, film_info filmInfo  from actor_info order By actor_id limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ActorInfo a = new ActorInfo();
				a.setActorId(rs.getInt("actorId"));
				a.setFirstName(rs.getString("firstName"));
				a.setLastName(rs.getString("lastName"));
				a.setFilmInfo(rs.getString("filmInfo"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
			rs.close();
			stmt.close();
			conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//전체행을 구하는 메서드
	public int totalRow() {
		int totalRow =0; //전체 행의 수 변수 초기화
		//DB자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		//쿼리문 작성
		String sql ="select count(*) cnt from actor_info";
		//DB연결
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				totalRow = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				//DB자원반납
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return totalRow; //전체 행의 수 리턴
	}
	//배우이름검색기능메서드
	public List<ActorInfo> searchActorInfoListByName(String name,int beginRow, int rowPerPage){
	List<ActorInfo> list = new ArrayList<>();
	//DB 자원 준비
	Connection conn =null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	conn = DBUtil.getConnection(); // DBUtil 내에 DB연결 호출
	String sql = "select actor_id actorId, first_name firstName, last_name lastName, film_info filmInfo  from actor_info WHERE first_name like ? OR last_name like ? order By actor_id limit ?,?";
	try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%"+name+"%");
		stmt.setString(2, "%"+name+"%");
		stmt.setInt(3, beginRow);
		stmt.setInt(4, rowPerPage);
		rs = stmt.executeQuery();
		while(rs.next()) {
			ActorInfo a = new ActorInfo();
			
			
			a.setActorId(rs.getInt("actorId"));
			a.setFirstName(rs.getString("firstName"));
			a.setLastName(rs.getString("lastName"));
			a.setFilmInfo(rs.getString("filmInfo"));
			list.add(a);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		try {
		rs.close();
		stmt.close();
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return list;
}
	//영화정보검색기능메서드
	public List<ActorInfo> searchActorInfoListByfilmInfo(String filmInfo,int beginRow, int rowPerPage){
		List<ActorInfo> list = new ArrayList<>();
		//DB 자원 준비
		Connection conn =null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection(); // DBUtil 내에 DB연결 호출
		String sql = "select actor_id actorId, first_name firstName, last_name lastName, film_info filmInfo  from actor_info WHERE film_info like ?  order By actor_id limit ?,?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+filmInfo+"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ActorInfo a = new ActorInfo();
				
				
				a.setActorId(rs.getInt("actorId"));
				a.setFirstName(rs.getString("firstName"));
				a.setLastName(rs.getString("lastName"));
				a.setFilmInfo(rs.getString("filmInfo"));
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
