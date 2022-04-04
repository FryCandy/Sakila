package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.CustomerList;

public class CustomerListDao {
	public List <CustomerList> selectCustomerListByPage(int beginRow,int rowPerPage){
		List<CustomerList> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql ="select ID, name, address, `zip code` zipCode,phone,city,country,notes,SID from customer_list order by ID limit ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				CustomerList c = new CustomerList();
				c.setID(rs.getInt("ID"));
				c.setName(rs.getString("name"));
				c.setAddress(rs.getString("address"));
				c.setZipCode(rs.getInt("zipCode"));
				c.setPhone(rs.getLong("phone"));
				c.setCity(rs.getString("city"));
				c.setCountry(rs.getString("country"));
				c.setNotes(rs.getString("notes"));
				c.setSID(rs.getInt("SID"));
				list.add(c);
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
	//전체 행 수 출력 메서드
	public int totalRow () {
		int totalRow = 0;
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql ="select count(*) cnt from customer_list";
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
	public List <CustomerList> searchCustomerListByname(String name, int beginRow,int rowPerPage){
		List<CustomerList> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql ="select ID, name, address, `zip code` zipCode,phone,city,country,notes,SID from customer_list where name like ? order by ID limit ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+name+"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				CustomerList c = new CustomerList();
				c.setID(rs.getInt("ID"));
				c.setName(rs.getString("name"));
				c.setAddress(rs.getString("address"));
				c.setZipCode(rs.getInt("zipCode"));
				c.setPhone(rs.getLong("phone"));
				c.setCity(rs.getString("city"));
				c.setCountry(rs.getString("country"));
				c.setNotes(rs.getString("notes"));
				c.setSID(rs.getInt("SID"));
				list.add(c);
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
	public List <CustomerList> searchCustomerListByCity(String city,int beginRow,int rowPerPage){
		List<CustomerList> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql ="select ID, name, address, `zip code` zipCode,phone,city,country,notes,SID from customer_list where city like ? order by ID limit ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+city+"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				CustomerList c = new CustomerList();
				c.setID(rs.getInt("ID"));
				c.setName(rs.getString("name"));
				c.setAddress(rs.getString("address"));
				c.setZipCode(rs.getInt("zipCode"));
				c.setPhone(rs.getLong("phone"));
				c.setCity(rs.getString("city"));
				c.setCountry(rs.getString("country"));
				c.setNotes(rs.getString("notes"));
				c.setSID(rs.getInt("SID"));
				list.add(c);
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
	public List <CustomerList> searchCustomerListBycountry(String country,int beginRow,int rowPerPage){
		List<CustomerList> list = new ArrayList<>();
		//DB자원준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		//쿼리문 작성
		String sql ="select ID, name, address, `zip code` zipCode,phone,city,country,notes,SID from customer_list where country like ? order by ID limit ?,? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+country+"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				CustomerList c = new CustomerList();
				c.setID(rs.getInt("ID"));
				c.setName(rs.getString("name"));
				c.setAddress(rs.getString("address"));
				c.setZipCode(rs.getInt("zipCode"));
				c.setPhone(rs.getLong("phone"));
				c.setCity(rs.getString("city"));
				c.setCountry(rs.getString("country"));
				c.setNotes(rs.getString("notes"));
				c.setSID(rs.getInt("SID"));
				list.add(c);
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
