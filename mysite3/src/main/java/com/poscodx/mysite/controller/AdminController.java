package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;


@Controller
@Auth(role="ADMIN")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ServletContext servletContext;
	
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(SiteVo siteVo, @RequestParam(value="file") MultipartFile file) {
	
		String profile= fileuploadService.restore(file);	//url
		if (profile !=null) {
			siteVo.setProfile(profile);
		}		
		siteService.updateSite(siteVo);
		servletContext.setAttribute("sitevo", siteVo);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
