package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;
import vo.Film;
import vo.FilmList;
public class FilmDao {
	//film_in_stock 프로시저
	public Map<String,Object> filmInStockCall(int filmId,int storeId){
		Map<String,Object> map = new HashMap<String,Object>();
		
		//데이터베이스 자원준비
		Connection conn =null;
		//preparedStatement : 쿼리를 실행
		//CallableStatement : 프로시저를 실행
		CallableStatement stmt = null;
		ResultSet rs = null;
		//select inventory_id...
		List<Integer> list = new ArrayList<>();
		//select count(inventroy_id)....
		Integer count = 0;
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{call film_in_stock(?,?,?)}");
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
			stmt.registerOutParameter(3,Types.INTEGER);
			rs= stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); //rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); //프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	//film_not_in_stock 프로시저
	public Map<String,Object> filmNotInStockCall(int filmId,int storeId){
		Map<String,Object> map = new HashMap<String,Object>();
		
		//데이터베이스 자원준비
		Connection conn =null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		//select inventory_id
		List<Integer> list = new ArrayList<>();
		//select count(inventroy_id)
		Integer count = 0;
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{call film_not_in_stock(?,?,?)}");
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
			stmt.registerOutParameter(3,Types.INTEGER);
			rs= stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); //rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); //프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	public static void main (String[] args) {
		FilmDao fd = new FilmDao();
		int filmId = 1;
		int storeId = 1;
		Map<String,Object> map = fd.filmInStockCall(filmId, storeId);
		List<Integer> list = (List<Integer>)map.get("list");
		int count = (Integer)map.get("count");
		System.out.println(filmId+"번 영화가"+storeId+"번 가게에"+count+"개 남음");
		for(int id : list) {
			System.out.println(id);
		}
	}
	public List<Double> selectFilmPriceList(){
		List<Double> list =new ArrayList<Double>();
		//데이터베이스 자원준비
				Connection conn =null;
				PreparedStatement stmt =null;
				ResultSet rs = null;
				conn = DBUtil.getConnection();
				String sql ="SELECT price FROM film_list GROUP BY price";
				try {
					stmt = conn.prepareStatement(sql);
					rs= stmt.executeQuery();
					while(rs.next()) {
						list.add(rs.getDouble(1)); //rs.getDouble("price")
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return list;
	}
	//filmSearchform에서 호출, 검색 메서드
	public List<FilmList> selectFilmListSearch(String category,String rating,double price, int minLength,int maxLength,String title,String actors,int beginRow,int rowPerPage){
		List<FilmList> list = new ArrayList<FilmList>(); //filmlist값을 넣을 ArrayList
		//db 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//검색 쿼리 입력
			List<String> setObject = new ArrayList<>(); // ? 값 넣을 ArrayList<String>
		String sql="SELECT * FROM film_list WHERE title LIKE ? AND actors LIKE ?";
		setObject.add("%"+title+"%"); //title ?에 들어갈 값
		setObject.add("%"+actors+"%"); //actors ?에 들어갈 값
		if(!category.equals("")){ // category에 ""이 아니라면
			sql = sql + " AND category = ?";
			setObject.add(category);// ?에 category 값 입력
		}
		//rating 검색
		if(!rating.equals("")) {
			sql = sql + " AND rating = ?";
			setObject.add(rating);
		}
		//price 검색
		if(price!=-1){
			sql = sql + " AND price = ?";
			setObject.add(String.valueOf(price));// ?에 pirce를 String으로 변경하여 값 입력
		}
		//length 검색 부분
			sql = sql + " AND length >= ?";
			setObject.add(String.valueOf(minLength));// ?에 minLength 값 입력
			sql = sql + " AND length < ?";
			setObject.add(String.valueOf(maxLength));// ?에 maxLength 값 입력
		//마지막으로 orderBY값 입력		
		sql = sql + " ORDER BY fid limit ?,?"; 
		System.out.println(String.valueOf(rowPerPage));
		System.out.println(sql);
		for(int i =0;i<setObject.size();i=i+1) {
			System.out.println(i);
			System.out.println(setObject.get(i));
		}
		try {
			stmt=conn.prepareStatement(sql);
		for(int i =0;i<setObject.size();i=i+1) {
			stmt.setString(i+1, setObject.get(i));
		}
			stmt.setInt(setObject.size()+1, beginRow);
			stmt.setInt(setObject.size()+2, rowPerPage);
		rs=stmt.executeQuery();
		while(rs.next()) {
			FilmList f = new FilmList();
			f.setFID(rs.getInt("fid"));
			f.setTitle(rs.getString("title"));
			f.setDescription(rs.getString("description"));
			f.setCategory(rs.getString("category"));
			f.setPrice(rs.getDouble("price"));
			f.setLength(rs.getInt("length"));
			f.setRating(rs.getString("rating"));
			f.setActors(rs.getString("actors"));
			list.add(f);
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//filmLIst상세검색 총 행의 수 구하기
	public int totalRowFilmListSearch(String category,String rating,double price, int minLength,int maxLength,String title,String actors){
		int totalRow = 0;
		//db 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//검색 쿼리 입력
		List<String> setObject = new ArrayList<>(); // ? 값 넣을 ArrayList<String>
		String sql="SELECT count(*) cnt FROM film_list WHERE title LIKE ? AND actors LIKE ?";
		setObject.add("%"+title+"%"); //title ?에 들어갈 값
		setObject.add("%"+actors+"%"); //actors ?에 들어갈 값
		if(!category.equals("")){ // category에 ""이 아니라면
			sql = sql + " AND category = ?";
			setObject.add(category);// ?에 category 값 입력
		}
		//rating 검색
		if(!rating.equals("")){
			sql = sql + " AND rating = ?";
			setObject.add(rating);// ?에 category 값 입력
		}
		//price 검색
		if(price!=-1){
			sql = sql + " AND price = ?";
			setObject.add(String.valueOf(price));// ?에 pirce를 String으로 변경하여 값 입력
		}
		//length 검색 부분
		sql = sql + " AND length >= ?";
		setObject.add(String.valueOf(minLength));// ?에 minLength 값 입력
		sql = sql + " AND length < ?";
		setObject.add(String.valueOf(maxLength));// ?에 maxLength 값 입력
		
		System.out.println(sql);
		for(int i =0;i<setObject.size();i=i+1) {
			System.out.println(i);
			System.out.println(setObject.get(i));
		}
		try {
			stmt=conn.prepareStatement(sql);
			for(int i =0;i<setObject.size();i=i+1) {
				stmt.setString(i+1, setObject.get(i));
			}
			rs=stmt.executeQuery();
			if(rs.next()) {
				totalRow = rs.getInt("cnt");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return totalRow;
	}
	
	//film 테이블 정보 출력
	public List<Film> selectFilmList(int beginRow, int rowPerPage){
		List<Film> list = new ArrayList<>();
		//데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "SELECT film_id filmId,"
				+ "		 title,"
				+ "		description,"
				+ "		release_year releaseYear,"
				+ "		language_id languageId,"
				+ "		original_language_id originalLanguageId,"
				+ "		rental_duration rentalDuration,"
				+ "		rental_rate rentalRate,"
				+ "		length,"
				+ "		replacement_cost replacementCost,"
				+ "		rating,"
				+ "		special_features specialFeatures,"
				+ "		last_update lastUpdate"
				+ "	FROM film"
				+ "	LIMIT ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setReleaseYear(rs.getString("releaseYear"));
				f.setLanguageId(rs.getInt("languageId"));
				f.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				f.setRentalDuration(rs.getInt("rentalDuration"));
				f.setRentalRate(rs.getDouble("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setReplacementCost(rs.getDouble("replacementCost"));
				f.setRating(rs.getString("rating"));
				f.setSpecialFeatures(rs.getString("specialFeatures"));
				f.setLastUpdate(rs.getString("lastUpdate"));
				list.add(f);
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
				+ "	FROM film";
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
