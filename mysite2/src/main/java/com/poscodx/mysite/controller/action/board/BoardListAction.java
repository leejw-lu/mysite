package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNo= request.getParameter("p");
		if (pageNo==null) {
			pageNo="1";
		}
		
		//한 페이지에 보이는 게시물 개수
		int viewCount= 4;
		
		//해당 page 글 정보 가져오기
		List<BoardVo> list = new BoardDao().findAll(Integer.parseInt(pageNo), viewCount);
		request.setAttribute("list", list);

		//page 정보 세팅하기
		Page page = new BoardDao().findPage(Integer.parseInt(pageNo), viewCount);
		request.setAttribute("page", page);
		
		//System.out.println("b: " + page.getBeginPage() + " c:" + page.getCurrentPage() + " e: "+ page.getEndPage());
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
	}

}
