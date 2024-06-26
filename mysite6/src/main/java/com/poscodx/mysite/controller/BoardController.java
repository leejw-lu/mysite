package com.poscodx.mysite.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String add() {
		return "board/write";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String add(Authentication authentication, BoardVo vo) {

//		UserVo authUser= (UserVo) session.getAttribute("authUser");
//		if(authUser==null) {
//			return "redirect:/board";
//		}
		UserVo authUser = (UserVo)authentication.getPrincipal();
		vo.setUserNo(authUser.getNo());
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String addReply(Authentication authentication, @PathVariable("no") Long no, Model model) {

		BoardVo vo=null;
		if (no!=null) {
			vo= boardService.getContents(no);	
		}
	
		model.addAttribute("vo", vo);	
		return "board/write";
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.POST)
	public String addReply(Authentication authentication, BoardVo vo) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
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
	
	@RequestMapping("/delete/{no}")
	public String delete(Authentication authentication, @PathVariable("no") Long no) {	
		UserVo authUser = (UserVo)authentication.getPrincipal();
		boardService.deleteContents(no, authUser.getNo());
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(Authentication authentication, @PathVariable("no") Long no, Model model) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		BoardVo vo = boardService.getContents(no, authUser.getNo());
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(Authentication authentication, @PathVariable("no") Long no, BoardVo vo) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		vo.setUserNo(authUser.getNo());
		boardService.updateContents(vo);
		
		return "redirect:/board/view/"+no;
	}
	
}
