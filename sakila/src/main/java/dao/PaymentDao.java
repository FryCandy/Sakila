package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.Payment;
public class PaymentDao {
	//payment 테이블 정보 출력
	public List<Payment> selectPaymentList(int beginRow, int rowPerPage){
		List<Payment> list = new ArrayList<>();
		//데이터베이스 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//DB연결
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql = "SELECT payment_id paymentId,"
				+ "		customer_id customerId,"
				+ "		staff_id staffId,"
				+ "		rental_id rentalId,"
				+ "		amount,"
				+ "		payment_date paymentDate,"
				+ "		last_update lastUpdate"
				+ "	FROM payment"
				+ "	LIMIT ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Payment p = new Payment();
				p.setPaymentId(rs.getInt("paymentId"));
				p.setCustomerId(rs.getInt("customerId"));
				p.setStaffId(rs.getInt("staffId"));
				p.setRentalId(rs.getInt("rentalId"));
				p.setAmount(rs.getDouble("amount"));
				p.setPaymentDate(rs.getString("paymentDate"));
				p.setLastUpdate(rs.getString("lastUpdate"));
				list.add(p);
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
	
	//payment 테이블의 전체 행의 수 출력
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
				+ "	FROM payment";
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
