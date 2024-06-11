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

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
	
	@Auth
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String add(@AuthUser UserVo authUser, BoardVo vo) {

//		UserVo authUser= (UserVo) session.getAttribute("authUser");
//		if(authUser==null) {
//			return "redirect:/board";
//		}
		
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String addReply(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {

		BoardVo vo=null;
		if (no!=null) {
			vo= boardService.getContents(no);	
		}
	
		model.addAttribute("vo", vo);	
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.POST)
	public String addReply(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		
		return "redirect:/board";
	}
	
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo= boardService.getContents(no);
	
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no) {	
		boardService.deleteContents(no, authUser.getNo());
		
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, @PathVariable("no") Long no, BoardVo vo) {
		vo.setUserNo(authUser.getNo());
		boardService.updateContents(vo);
		
		return "redirect:/board/view/"+no;
	}
	
}
