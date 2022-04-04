package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByFilmCategory;
public class SalesByFilmCategoryDao {
	public List <SalesByFilmCategory> selectSalesByFilmCategory(){
		List<SalesByFilmCategory> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "select category, total_sales totalSales from sales_by_film_category";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				SalesByFilmCategory s = new SalesByFilmCategory();
				s.setCategory(rs.getString("category"));
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
