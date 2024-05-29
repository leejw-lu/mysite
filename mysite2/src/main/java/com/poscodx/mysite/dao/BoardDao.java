package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestbookVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn=null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url="jdbc:mariadb://192.168.0.207:3306/webdb?charset=utf-8"; 
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return conn;
	}
	
	public int insert(BoardVo vo) {
	      int result = 0;
	      
	      try (
	    	Connection conn = getConnection();
	        PreparedStatement pstmt1 = conn.prepareStatement("insert into board select null, ?, ?, ?, now(), max(g_no)+1 , ?, ?, ? from board;");
	        PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
	      ){
	    	 //바인딩
	         pstmt1.setString(1, vo.getTitle());
	         pstmt1.setString(2, vo.getContents());
	         pstmt1.setInt(3, vo.getHit());		//0
	         pstmt1.setInt(4, vo.getoNo());		//1
	         pstmt1.setInt(5, vo.getDepth());	//0
	         pstmt1.setLong(6, vo.getUserNo());
	         result = pstmt1.executeUpdate();
	         
	         ResultSet rs = pstmt2.executeQuery();
	         vo.setNo(rs.next() ? rs.getLong(1) : null);
	         rs.close();
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	      
	      return result;
	   }

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn= getConnection();
			PreparedStatement pstmt= conn.prepareStatement("select a.name, b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.g_no, b.o_no, b.depth from user a, board b where a.no=b.user_no order by b.g_no desc, b.o_no asc");
			ResultSet rs= pstmt.executeQuery();
		) { 
		
			while(rs.next()) {

				String name= rs.getString(1);
				Long no= rs.getLong(2);
				String title=rs.getString(3);
				String contents= rs.getString(4);
				int hit=rs.getInt(5);
				String regDate=rs.getString(6);
				int groupNo=rs.getInt(7);
				int orderNo=rs.getInt(8);
				int depth=rs.getInt(9);
				
				BoardVo vo= new BoardVo(name,no, title, contents,hit, regDate, groupNo, orderNo, depth);				
				result.add(vo);
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	
		return result;
	}

	public BoardVo findByNo(Long no) {
		BoardVo vo = null;
		
	    try (
	    	Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("select no, title, contents, user_no from board where no= ?");
	      ){
	    	pstmt.setLong(1, no);
	    	ResultSet rs = pstmt.executeQuery();

	    	if (rs.next()) {
	    		vo= new BoardVo();
				Long boardNo= rs.getLong(1);
				String title=rs.getString(2);
				String contents= rs.getString(3);
				Long userNo= rs.getLong(4);
				
				vo.setNo(boardNo);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(userNo);
	    	}
	    	rs.close();
	    	
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	      
	   return vo;
	}

}
