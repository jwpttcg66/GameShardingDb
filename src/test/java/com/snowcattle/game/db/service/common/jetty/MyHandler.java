package com.snowcattle.game.db.service.common.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MyHandler extends AbstractHandler{

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");  
        response.setStatus(200);  
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>xxxxxxxxxxxxxxxx</h1>");  
        response.getWriter().println("<li>Request url: " + target + "</li>");  
        response.getWriter().println("<li>Server port: " + request.getServerPort() + "</li>");  
	}

}
