package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.Page;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	public int updateOrderNo(int groupNo, int orderNo) {
		return sqlSession.update("board.updateOrderNo", Map.of("gNo", groupNo, "oNo", orderNo));
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public List<BoardVo> findAll(int pageNo, int pageSize) {
		return sqlSession.selectList("board.findAll", Map.of("start", (pageNo-1) * pageSize, "size", pageSize));
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}
	
	public void updateHit(Long no) {
		sqlSession.update("board.updateHit", no);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	public void deleteByNo(Long boardNo, Long userNo) {
		sqlSession.delete("board.deleteByNo", Map.of("boardNo", boardNo, "userNo", userNo));
	}

//	public int insertReply(BoardVo vo) {
//	      int result = 0;
//	      
//	      try (
//	    	Connection conn = getConnection();
//	    	PreparedStatement pstmt1= conn.prepareStatement("update board set o_no=o_no+1 where g_no= ? and o_no >= ? ");
//	        PreparedStatement pstmt2 = conn.prepareStatement("insert into board values(null, ?, ?, ?, now(), ?, ?, ?, ?)");
//	        PreparedStatement pstmt3 = conn.prepareStatement("select last_insert_id() from dual");
//	      ){
//	    	 //1. 증가
//	    	 int oNo=vo.getoNo()+1;
//	    	 int depth=vo.getDepth()+1;
//	    	 
//	    	 System.out.println("oNo: " +oNo);
//	    	 System.out.println("depth: " +depth);
//	    	 
//	    	 //2. update
//	    	 pstmt1.setInt(1, vo.getgNo());
//	    	 pstmt1.setInt(2, oNo);
//	    	 pstmt1.executeUpdate();
//	    	 
//	    	 //3. insert
//	         pstmt2.setString(1, vo.getTitle());
//	         pstmt2.setString(2, vo.getContents());
//	         pstmt2.setInt(3, vo.getHit());		
//	         pstmt2.setInt(4, vo.getgNo());
//	         pstmt2.setInt(5, oNo);		
//	         pstmt2.setInt(6, depth);	
//	         pstmt2.setLong(7, vo.getUserNo());
//	         
//	         ResultSet rs = pstmt2.executeQuery();
//	         vo.setNo(rs.next() ? rs.getLong(1) : null);
//	         rs.close();
//	      } catch (SQLException e) {
//	         System.out.println("error:"+e);
//	      }
//	      
//	      return result;
//	}

	public Page findPage(int pageNo, int pageSize) {
		return sqlSession.selectOne("board.findPage", pageSize);
	}





}
