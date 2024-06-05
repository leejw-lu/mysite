package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String index(@RequestParam(value="p", required=true, defaultValue="1") int pageNo, Model model) {		
		Map<String, Object> map= boardService.getContentsList(pageNo);
		model.addAllAttributes(map);
		
		return "board/list";
	}
	
//	@RequestMapping(value="/insert/{no}", method=RequestMethod.GET)
//	public String add(HttpSession session, @PathVariable("no") Long no, Model model) {
//		//access control
//		UserVo authUser= (UserVo) session.getAttribute("authUser");
//		if(authUser==null) {
//			return "redirect:/board";
//		}
//		
//		BoardVo vo=null;
//		if (no!=null) {
//			vo= boardService.getContents(no);	
//		}
//	
//		model.addAttribute("vo", vo);	
//		return "board/write";
//	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String add(HttpSession session) {
		//access control
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}

		return "board/write";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String add(HttpSession session, BoardVo vo) {
		//access control
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	
	@RequestMapping("/view/{no}")
	public String view(HttpSession session, @PathVariable("no") Long no, Model model) {
		//access control
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		BoardVo vo= null;
		if(authUser==null) {
			vo=boardService.getContents(no);
		} else {
			vo=boardService.getContents(no, authUser.getNo());
		}
		
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long no) {
		//access control
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(HttpSession session, @PathVariable("no") Long no, Model model) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("vo", vo);
		
		return "board/moldify";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, BoardVo vo) {
		// access control
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/board";
		}
		
		vo.setNo(authUser.getNo());
		boardService.updateContents(vo);
		
		return "redirect:/board";
	}
	
	
	
}
