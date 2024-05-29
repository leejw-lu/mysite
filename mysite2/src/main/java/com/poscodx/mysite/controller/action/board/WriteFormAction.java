package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no= request.getParameter("no");
		BoardVo vo= null;
		if (no != null) {
			vo= new BoardDao().findByNo(Long.parseLong(no));
		}

		request.setAttribute("vo", vo);
		request
			.getRequestDispatcher("/WEB-INF/views/board/write.jsp")
			.forward(request, response);
	}

}
