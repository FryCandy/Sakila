package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByStore;
public class SalesByStoreDao {
	public List <SalesByStore> selectSalesByStore(){
		List<SalesByStore> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "select store,manager,total_sales totalSales from sales_by_store";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				SalesByStore s = new SalesByStore();
				s.setStore(rs.getString("store"));
				s.setManager(rs.getString("manager"));
				s.setTotalSales(rs.getDouble("totalSales"));
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
