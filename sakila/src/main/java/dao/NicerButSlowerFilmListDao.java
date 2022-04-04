package dao;

	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.NicerButSlowerFilmList;

	public class NicerButSlowerFilmListDao {
		public List<NicerButSlowerFilmList> selectNiceButSlowerFilmListByPage (int beginRow, int rowPerPage){
			List<NicerButSlowerFilmList> list = new ArrayList();
			//DB자원준비
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				conn = DBUtil.getConnection();
				//쿼리문 작성
				String sql ="select FID, title, description, category, price, length, rating, actors from nicer_but_slower_film_list order by FID limit ?,?";
				try {
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, beginRow);
					stmt.setInt(2, rowPerPage);
					rs = stmt.executeQuery();
					while(rs.next()) {
						NicerButSlowerFilmList f = new NicerButSlowerFilmList();
						f.setFID(rs.getInt("FID"));
						f.setTitle(rs.getString("title"));
						f.setDescription(rs.getString("description"));
						f.setCategory(rs.getString("category"));
						f.setPrice(rs.getDouble("price"));
						f.setLength(rs.getInt("length"));
						f.setRating(rs.getString("rating"));
						f.setActors(rs.getString("actors"));
						list.add(f);
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
		public int totalRow() {
			int totalRow = 0;
			//DB자원준비
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection();
			//쿼리문 작성
			String sql ="select count(*) cnt from nicer_but_slower_film_list";
			try {
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				if(rs.next()) {
					totalRow = rs.getInt("cnt");
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
			
			return totalRow;
		}
	}

