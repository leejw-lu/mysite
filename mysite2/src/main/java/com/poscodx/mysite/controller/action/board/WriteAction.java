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

public class WriteAction implements Action {

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
		
		// 새 글 작성 & 답글 작성 공통
		String title= request.getParameter("title");
		String content= request.getParameter("content");
		
		// 답글 작성 only
		String groupNo= request.getParameter("groupNo");
		String orderNo= request.getParameter("orderNo");
		String depth= request.getParameter("depth");
		
		// System.out.println("WriteAction: " + groupNo + " " + orderNo+ " " + depth);
		
		if (groupNo == null && orderNo == null && depth ==null) {
			// 새글 작성
			System.out.println("새글 작성");
			BoardVo vo=new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setHit(0);
			vo.setoNo(1);
			vo.setDepth(0);
			vo.setUserNo(authUser.getNo());
			
			new BoardDao().insert(vo);
		} else {
			// 답글 작성
			System.out.println("답글 작성");
			BoardVo vo=new BoardVo();
			// 생성자 만들까,,,
			vo.setTitle(title);
			vo.setContents(content);
			vo.setHit(0);
			vo.setoNo(1);
			vo.setDepth(0);
			vo.setUserNo(authUser.getNo());
			
			vo.setgNo(Integer.parseInt(groupNo));
			vo.setoNo(Integer.parseInt(orderNo));
			vo.setDepth(Integer.parseInt(depth));
			
			new BoardDao().insertReply(vo);
		}
		
		response.sendRedirect(request.getContextPath()+ "/board?p=1");
	}

}
