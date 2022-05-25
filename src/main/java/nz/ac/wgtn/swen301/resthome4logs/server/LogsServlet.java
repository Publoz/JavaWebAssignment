package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//http://localhost:8080/resthome4logs/logs?level=ALL&limit=2
		//example valid url
		
		String limit = req.getParameter("limit");
		String level = req.getParameter("level");
		
		if(limit == null || level == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int num = -1;
		try {
			num = Integer.parseInt(limit);
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if(num < 0 || num > 2147483647) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		int levelNum = 0;
		try {
			levelNum = getLevel(level);
		} catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		
		
		
		
		PrintWriter out = resp.getWriter();
		
		resp.setContentType("application/json");
		
		out.print(Persistency.getLogs(levelNum, num));

	}
	
	private int getLevel(String level) {
		switch (level) {
		case "ALL":
			return 0;
		case "DEBUG":
			return 1;
		case "INFO":
			return 2;
		case "WARN":
			return 3;
		case "ERROR":
			return 4;
		case "FATAL":
			return 5;
		case "TRACE":
			return 6;
		case "OFF":
			return 7;
		default:
			throw new IllegalArgumentException("Invalid level");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		
		
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Persistency.delete();
	}

}
