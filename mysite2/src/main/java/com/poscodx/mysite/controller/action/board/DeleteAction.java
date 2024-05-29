package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser==null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String no= request.getParameter("no");
		
		BoardVo vo=new BoardVo();
		vo.setNo(Long.parseLong(no));
		vo.setUserNo(authUser.getNo());
		
		new BoardDao().deleteByNo(vo);
		
		response.sendRedirect(request.getContextPath()+ "/board");
	}

}
