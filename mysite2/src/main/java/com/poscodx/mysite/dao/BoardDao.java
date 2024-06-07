package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscodx.mysite.controller.action.board.Page;
import com.poscodx.mysite.vo.BoardVo;

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
	        PreparedStatement pstmt1 = conn.prepareStatement("insert into board select null, ?, ?, ?, now(), max(g_no)+1 , ?, ?, ? from board");
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

	public List<BoardVo> findAll(int pageNo, int pageSize) {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn= getConnection();
			PreparedStatement pstmt= conn.prepareStatement("select a.name, b.no, b.title, b.contents, b.hit, date_format(b.reg_date, '%Y-%m-%d %H:%i:%s'), b.g_no, b.o_no, b.depth from user a, board b where a.no=b.user_no order by b.g_no desc, b.o_no asc limit ? , ?");
			
		) { 
			pstmt.setLong(1, (pageNo-1) * pageSize);
			pstmt.setLong(2, pageSize);
			ResultSet rs= pstmt.executeQuery();
			
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
	        PreparedStatement pstmt = conn.prepareStatement("select no, title, contents, user_no, g_no, o_no, depth from board where no= ?");
	    ){
	    	pstmt.setLong(1, no);
	    	ResultSet rs = pstmt.executeQuery();
	    	
	    	if (rs.next()) {
	    		vo= new BoardVo();
	    		//ModifyForm에서 필요
				Long boardNo= rs.getLong(1);
				String title=rs.getString(2);
				String contents= rs.getString(3);
				Long userNo= rs.getLong(4);
				
				//Write(Reply)에서 필요
				int groupNo=rs.getInt(5);
				int orderNo=rs.getInt(6);
				int depth=rs.getInt(7);
				
				vo.setNo(boardNo);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(userNo);
				
				vo.setgNo(groupNo);
				vo.setoNo(orderNo);
				vo.setDepth(depth);
	    	}
	    	rs.close();
	    	
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	      
	   return vo;
	}

	public void deleteByNo(BoardVo vo) {
		try (
			Connection conn= getConnection();
			PreparedStatement pstmt= conn.prepareStatement("delete from board where no = ? and user_no= ?");
			) { 
				pstmt.setLong(1, vo.getNo());
				pstmt.setLong(2, vo.getUserNo());
				pstmt.executeUpdate();			
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
	}

	public void update(BoardVo vo) {
	      try (
	    	Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("update board set title=?, contents=?, reg_date=now() where no=? and user_no=?");
	      ){
	    	 //바인딩
	         pstmt.setString(1, vo.getTitle());
	         pstmt.setString(2, vo.getContents());
	         pstmt.setLong(3, vo.getNo());
	         pstmt.setLong(4, vo.getUserNo());

	         pstmt.executeQuery();
	         
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	}

	public int insertReply(BoardVo vo) {
	      int result = 0;
	      
	      try (
	    	Connection conn = getConnection();
	    	PreparedStatement pstmt1= conn.prepareStatement("update board set o_no=o_no+1 where g_no= ? and o_no >= ? ");
	        PreparedStatement pstmt2 = conn.prepareStatement("insert into board values(null, ?, ?, ?, now(), ?, ?, ?, ?)");
	        PreparedStatement pstmt3 = conn.prepareStatement("select last_insert_id() from dual");
	      ){
	    	 //1. 증가
	    	 int oNo=vo.getoNo()+1;
	    	 int depth=vo.getDepth()+1;
	    	 
	    	 System.out.println("oNo: " +oNo);
	    	 System.out.println("depth: " +depth);
	    	 
	    	 //2. update
	    	 pstmt1.setInt(1, vo.getgNo());
	    	 pstmt1.setInt(2, oNo);
	    	 pstmt1.executeUpdate();
	    	 
	    	 //3. insert
	         pstmt2.setString(1, vo.getTitle());
	         pstmt2.setString(2, vo.getContents());
	         pstmt2.setInt(3, vo.getHit());		
	         pstmt2.setInt(4, vo.getgNo());
	         pstmt2.setInt(5, oNo);		
	         pstmt2.setInt(6, depth);	
	         pstmt2.setLong(7, vo.getUserNo());
	         
	         ResultSet rs = pstmt2.executeQuery();
	         vo.setNo(rs.next() ? rs.getLong(1) : null);
	         rs.close();
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	      
	      return result;
	}

	public Page findPage(int pageNo, int pageSize) {
		Page page=null;
		
	    try (
	    	Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("select count(*), ceil(count(*)/?) from board;");
	    	
	    	){
	    	pstmt.setInt(1, pageSize);
	    	
	    	ResultSet rs = pstmt.executeQuery();
	    	if (rs.next()) {
	    		page= new Page();

	    		int totalCount= rs.getInt(1);
	    		int endPage=rs.getInt(2);
	    		int beginPage;
	    		if (pageNo==1 || pageNo==2) {
	    			beginPage=1;
	    		}else {
	    			beginPage=pageNo-2;
	    		}
	    		
	    		page.setTotalCount(totalCount);
	    		page.setEndPage(endPage);
	    		page.setCurrentPage(pageNo);
	    		page.setBeginPage(beginPage);
	    		page.setPageSize(pageSize);
	    	}
	    	rs.close();
	    	
	      } catch (SQLException e) {
	         System.out.println("error:"+e);
	      }
	    
		return page;
	}

	public void updateHits(Long no) {
		try (
		    Connection conn = getConnection();
			PreparedStatement pstmt= conn.prepareStatement("update board set hit=hit+1 where no= ?");
		    ){
				pstmt.setLong(1, no);
		    	pstmt.executeQuery();

		      } catch (SQLException e) {
		    	  System.out.println("error:"+e);
		 }
	}

}
