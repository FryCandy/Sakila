package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.Category;
public class CategoryDao {
	public List<Category> selectCategoryList(){
		List<Category> list = new ArrayList<>();//자바 최신버전에서는 제네릭 생략가능
		//데이터베이스 자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; //set이다 절대 중복될수 없다, select할때 rowId라는것이 같이 넘어와서 중복되지 않는다.
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리 - 키워드와, 함수는 대문자로 찍자
		String sql ="SELECT category_id categoryId, name, last_update lastUpdate FROM category";
		try {
			stmt = conn.prepareStatement(sql);
			rs =stmt.executeQuery();
			while(rs.next()) {
				Category c = new Category();
				c.setCategoryId(rs.getInt("categoryId"));
				c.setName(rs.getString("name"));
				c.setLastUpdate(rs.getString("lastUpdate"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();//예외에 해당하는 코드를 찍어줄것을 요청
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
}
