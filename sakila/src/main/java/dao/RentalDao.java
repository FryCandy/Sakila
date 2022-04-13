package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;
import vo.Rental;
public class RentalDao {
	//대여 상세 검색 후 리스트 요청 메서드
	public List<Map<String,Object>> selectRentalSearchList(int storeId, String customerName, String beginDate, String endDate,int beginRow,int rowPerPage){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//db 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		try {
		//기본 검색 쿼리 입력
		String sql="SELECT "
					+ "	r.*, "
					+ "	CONCAT(c.first_name,' ',c.last_name) cName, "
					+ "	s.store_id storeId, "
					+ "	f.film_id, "
					+ "	f.title "
					+ "FROM rental r INNER JOIN customer c "
					+ "ON r.customer_id = c.customer_id "
					+ "	INNER JOIN staff s "
					+ "	ON r.staff_id = s.staff_id "
					+ "		INNER JOIN inventory i "
					+ "		ON i.inventory_id = r.inventory_id "
					+ "			INNER JOIN film f "
					+ "			ON f.film_id = i.film_id "
					+ "WHERE CONCAT(c.first_name,' ',c.last_name) LIKE ? ";
		//분기하여 검색 하는 쿼리
		if(storeId==-1&&beginDate.equals("")&&endDate.equals("")){ // 스토어 ID 체크 안함, 대여 일자 선택 암함
			sql = sql + " ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
		}else if(storeId==-1&&beginDate.equals("")&&!endDate.equals("")) { //스토어 ID 체크 안함, 시작날짜 선택안함,마지막날짜 선택
			sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setString(2, endDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		}else if(storeId==-1&&!beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 안함, 시작날짜 선택,마지막날짜 선택안함
			sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setString(2, beginDate);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		}else if(storeId==-1&&!beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 안함, 시작날짜 선택,마지막날짜 선택
			sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setString(2, beginDate);
			stmt.setString(3, endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}else if(storeId!=-1&&beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택안함,마지막날짜 선택안함
			sql = sql + " AND s.store_id=? ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setInt(2, storeId);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
		}else if(storeId!=-1&&beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택안함,마지막날짜 선택함
			sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setInt(2, storeId);
			stmt.setString(3,endDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}else if(storeId!=-1&&!beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택,마지막날짜 선택안함
			sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setInt(4, beginRow);
			stmt.setInt(5, rowPerPage);
		}else if(storeId!=-1&&!beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택,마지막날짜 선택
			sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id limit ?,?";
			stmt= conn.prepareStatement(sql);
			stmt.setString(1, "%"+customerName+"%");
			stmt.setInt(2, storeId);
			stmt.setString(3, beginDate);
			stmt.setString(4, endDate);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
		}
		//디버깅
		System.out.println(sql);
		//결과값 요청
		rs = stmt.executeQuery();
		while(rs.next()) {
			Map<String,Object> map = new HashMap<>();
			map.put("rentalId", rs.getInt(1));
			map.put("rentalDate", rs.getString(2));
			map.put("inventoryId", rs.getInt(3));
			map.put("customerId",rs.getInt(4));
			map.put("returnDate", rs.getString(5));
			map.put("staffId", rs.getInt(6));
			map.put("lastUpdate", rs.getString(7));
			map.put("customerName", rs.getString(8));
			map.put("storeId", rs.getInt(9));
			map.put("filmId", rs.getInt(10));
			map.put("title", rs.getString(11));
			list.add(map);
		}
		} catch(SQLException e) {
					e.printStackTrace();
		}finally {//DB자원 반납
			try {
			rs.close();
			stmt.close();
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//대여 상세 검색 후 totalRow 요청 메서드
	public int totalRow (int storeId, String customerName, String beginDate, String endDate){
		int totalRow = 0; //totalRow 변수 초기화
		//db 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		try {
			//기본 검색 쿼리 입력
			String sql="SELECT "
					+ "	count(*) cnt "
					+ "FROM rental r INNER JOIN customer c "
					+ "ON r.customer_id = c.customer_id "
					+ "	INNER JOIN staff s "
					+ "	ON r.staff_id = s.staff_id "
					+ "		INNER JOIN inventory i "
					+ "		ON i.inventory_id = r.inventory_id "
					+ "			INNER JOIN film f "
					+ "			ON f.film_id = i.film_id "
					+ "WHERE CONCAT(c.first_name,' ',c.last_name) LIKE ? ";
			//검색쿼리 분기
			if(storeId==-1&&beginDate.equals("")&&endDate.equals("")){ // 스토어 ID 체크 안함, 대여 일자 선택 암함
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
			}else if(storeId==-1&&beginDate.equals("")&&!endDate.equals("")) { //스토어 ID 체크 안함, 시작날짜 선택안함,마지막날짜 선택
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, endDate);
			}else if(storeId==-1&&!beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 안함, 시작날짜 선택,마지막날짜 선택안함
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, beginDate);
			}else if(storeId==-1&&!beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 안함, 시작날짜 선택,마지막날짜 선택
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, beginDate);
				stmt.setString(3, endDate);
			}else if(storeId!=-1&&beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택안함,마지막날짜 선택안함
				sql = sql + " AND s.store_id=? ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
			}else if(storeId!=-1&&beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택안함,마지막날짜 선택함
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3, endDate);
			}else if(storeId!=-1&&!beginDate.equals("")&&endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택,마지막날짜 선택안함
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3, beginDate);
			}else if(storeId!=-1&&!beginDate.equals("")&&!endDate.equals("")) {//스토어 ID 체크 함, 시작날짜 선택,마지막날짜 선택
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3, beginDate);
				stmt.setString(4, endDate);
			}
			//디버깅
			System.out.println(sql);
			//결과값 요청
			rs = stmt.executeQuery();
			if(rs.next()) {
				totalRow=rs.getInt("cnt");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {//DB자원 반납
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalRow;
	}
	
	//rental 테이블 자료 요청, rentalList.jsp에서 호출
	public List<Rental> selectRentalList(int beginRow, int rowPerPage){
		List<Rental> list = new ArrayList<>();
		//데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "SELECT rental_id rentalId,"
				+ "		 rental_date rentalDate,"
				+ "		inventory_id inventoryId,"
				+ "		customer_id customerId,"
				+ "		return_date returnDate,"
				+ "		staff_id staffId,"
				+ "		last_update lastUpdate"
				+ "	FROM rental"
				+ "	LIMIT ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Rental r = new Rental();
				r.setRentalId(rs.getInt("rentalId"));
				r.setRentalDate(rs.getString("rentalDate"));
				r.setInventoryId(rs.getInt("inventoryId"));
				r.setCustomerId(rs.getInt("customerId"));
				r.setReturnDate(rs.getString("returnDate"));
				r.setStaffId(rs.getInt("staffId"));
				r.setLastUpdate(rs.getString("lastUpdate"));
				list.add(r);
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
	//film 테이블 전체 행의 수 출력
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
				+ "	FROM rental";
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
