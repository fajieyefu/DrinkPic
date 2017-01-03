package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class WaterInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cardId = request.getParameter("cardId");
		int type = Integer.parseInt(request.getParameter("type"));
		int count = Integer.parseInt(request.getParameter("count"));
		System.out.println("cardId is:" + cardId);
		Service serv = new Service();
		String waterInfo = serv.getWaterInfo(cardId, type, count);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(waterInfo);
		out.flush();
		out.close();
	}

}
