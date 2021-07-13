package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.Util.WebUtil;
import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

/**
 * Servlet implementation class GuestController
 */
@WebServlet("/gbc")
public class GuestController extends HttpServlet {       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	System.out.println("컨크롤러");
	
	request.setCharacterEncoding("UTF-8");
	
	String action = request.getParameter("action");
	
	if("addList".equals(action)) {
		
		System.out.println("[리스트]");
		
		//addList
		GuestDao guestDao = new GuestDao();
		List<GuestVo> guestbookList =  guestDao.getGuestbookList();
		
		//데이터 넣기 --request 어트리뷰트에 데이터를 넣어준다
		request.setAttribute("addList", guestbookList);
		
		//html작업 ---> jsp에게 시킨다 ==>forword 한다   포워드
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
//		rd.forward(request, response);
		
		WebUtil.forward(request, response, "/WEB-INF/addList.jsp");
		
	}else if("deleteForm".equals(action)) {
		
		System.out.println("[삭제폼]");
		
		//html작업 ---> jsp에게 시킨다 ==>forword 한다   포워드
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
		rd.forward(request, response);
		
	}else if("add".equals(action)) {
		
		//add
		GuestDao guestDao = new GuestDao();
		
		String name = request.getParameter("name");
		String password = request.getParameter("pass.w");
		String content = request.getParameter("content");
		System.out.println(name + "," + password + "," + content);
		
		GuestVo guestVo = new GuestVo(name, password, content);
		
		guestDao.guestbookInsert(guestVo);
		
		
		//deleteForm
//		response.sendRedirect("/guestbook2/gbc?action=addList");
		
		WebUtil.redirect(request, response, "/guestbook2/gbc?action=addList");
		
	}else if("delete".equals(action)) {
		
		System.out.println("[삭제]");
		
		//delete
		GuestDao guestDao = new GuestDao();

		int count = Integer.parseInt(request.getParameter("no"));
		
		String password = request.getParameter("pass.w");
		
		guestDao.guestDelete(count, password);
		
		response.sendRedirect("/guestbook2/gbc?action=addList");
	}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
