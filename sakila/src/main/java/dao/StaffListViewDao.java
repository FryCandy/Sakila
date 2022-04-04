package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.StaffListView;
public class StaffListViewDao{
	public List <StaffListView> selectStaffListView(){
		List<StaffListView> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql="select ID,name,address,`zip code` zipCode,phone,city,country,SID from staff_list ";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				StaffListView s = new StaffListView();
				s.setID(rs.getInt("ID"));
				s.setName(rs.getString("name"));
				s.setAddress(rs.getString("address"));
				s.setZipCode(rs.getString("zipCode"));
				s.setPhone(rs.getLong("phone"));
				s.setCity(rs.getString("city"));
				s.setCountry(rs.getString("country"));
				s.setSID(rs.getInt("SID"));
				list.add(s);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
