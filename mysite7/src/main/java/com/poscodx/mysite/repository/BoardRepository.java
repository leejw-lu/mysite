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

	public Page findPage(int pageNo, int pageSize) {
		return sqlSession.selectOne("board.findPage", pageSize);
	}

}
