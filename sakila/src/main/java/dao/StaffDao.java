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
public class StaffDao {
	public List <Map<String,Object>> selectStaffList(){
		List <Map<String,Object>> list = new ArrayList<>();
		//데이터베이스 자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {//예외 처리
			conn = DBUtil.getConnection();// DBUtil 내에 DB연결 호출
			String sql ="SELECT  "
					+ "	st1.staff_id staffId, "
					+ "	concat(st1.first_name,' ',st1.last_name) staffName, "
					+ "	CONCAT(a.address, IFNULL(a.address2, ' '), district) staffAddress, "
					+ "	st1.picture picture,  "
					+ "	st1.email, "
					+ "	st1.store_id storeId, "
					+ "	IF(st1.active, _utf8mb4'active',_utf8mb4' ') notes, "
					+ "	st1.username username, "
					+ "	st1.last_update lastUpdate "
					+ "FROM staff st1  "
					+ "INNER JOIN store st2 "
					+ "ON st1.store_id = st1.store_id "
					+ "INNER JOIN address a "
					+ "ON st1.address_id = a.address_id "
					+ "GROUP BY staffId";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("staffId",rs.getInt("staffId"));
				map.put("staffName", rs.getString("staffName"));
				map.put("staffAddress", rs.getString("staffAddress"));
				map.put("picture", rs.getString("picture"));
				map.put("email", rs.getString("email"));
				map.put("storeId", rs.getString("storeId"));
				map.put("notes", rs.getString("notes"));
				map.put("username", rs.getString("username"));
				map.put("lastUpdate", rs.getString("lastUpdate"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("예외발생");
		}finally {
			try {//데이터 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
