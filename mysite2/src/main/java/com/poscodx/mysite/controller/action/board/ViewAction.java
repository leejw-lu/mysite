package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no= request.getParameter("no");
		BoardVo vo = new BoardDao().findByNo(Long.parseLong(no));
	    
		//쿠키
		boolean updateHit=true;
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null) {
			for (Cookie cookie: cookies) {
				if(cookie.getName().equals("visitBoard"+no)){
					System.out.println("visitBoardNo: " + cookie.getName());
					updateHit=false;
					break;
				} 
			}
		}

		if(updateHit) {
			Cookie cookie = new Cookie("visitBoard"+no, no);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24* 60* 60); //1day
			response.addCookie(cookie);
		
			new BoardDao().updateHits(Long.parseLong(no));
		} 
		
		request.setAttribute("vo", vo);
		
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	}

}
