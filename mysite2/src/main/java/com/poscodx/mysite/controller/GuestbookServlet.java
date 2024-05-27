package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.guestbook.DeleteAction;
import com.poscodx.mysite.controller.action.guestbook.DeleteFormAction;
import com.poscodx.mysite.controller.action.guestbook.GuestbookAction;
import com.poscodx.mysite.controller.action.guestbook.insertAction;
import com.poscodx.mysite.controller.action.main.MainAction;
import com.poscodx.mysite.controller.action.user.JoinAction;
import com.poscodx.mysite.controller.action.user.JoinFormAction;
import com.poscodx.mysite.controller.action.user.JoinSuccess;

public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"deleteform", new DeleteFormAction(),
			"delete", new DeleteAction(),
			"insert" , new insertAction()
	);	
		
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new GuestbookAction());
	}
}
