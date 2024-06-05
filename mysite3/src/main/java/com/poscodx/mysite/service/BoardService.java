package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.Page;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public Map<String, Object> getContentsList(int currentPage) { // String keyword
		int pageSize=6;
		Map<String, Object> map= new HashMap<String, Object>();
		
		List<BoardVo> list= boardRepository.findAll(currentPage, pageSize);
		Page page= boardRepository.findPage(currentPage, pageSize);

		if (currentPage==1 || currentPage==2) {
			page.setBeginPage(1);
		} else {
			page.setBeginPage(currentPage-2);
		}
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		map.put("list", list);
		map.put("page", page);
		
		return map;
	}
	
	public void addContents(BoardVo vo) {
		if((Integer) vo.getGroupNo() != null) {
			boardRepository.updateOrderNo(vo.getGroupNo(), vo.getOrderNo()); // 이거 분리하기...
		}
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {	//그냥 view
		BoardVo vo= boardRepository.findByNo(no);
		if(vo!=null) {
			boardRepository.updateHit(no);
		}
		return vo;
	}
	
	public BoardVo getContents(Long boardNo, Long userNo) {	//수정할 때 필요.
		BoardVo vo= boardRepository.findByNo(boardNo);
		
		if (vo.getUserNo()!=userNo) {
			return null;
		}
		return vo;
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long boardNo, Long userNo) {
		boardRepository.deleteByNo(boardNo, userNo);
	}
	
}
