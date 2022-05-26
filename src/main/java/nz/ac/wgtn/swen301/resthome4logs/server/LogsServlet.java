package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
		BufferedReader br = req.getReader();
		StringBuffer data = new StringBuffer();
		String line = br.readLine();
		while(line != null) {
			data.append(line);
			line = br.readLine();
		}
		
		JSONObject log = new JSONObject(data.toString());
		try {
			if(log.getString("id") == null || log.getString("message") == null
					|| log.getString("timestamp") == null || log.getString("thread") == null
					|| log.getString("logger") == null || log.getString("level") == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			} else if(Persistency.hasId(log.get("id").toString())) {
				resp.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			} 
		} catch (JSONException e){
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		try {
			getLevel(log.getString("level"));
		} catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Persistency.logs.add(log);
		resp.setStatus(HttpServletResponse.SC_CREATED);
		
		
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Persistency.delete();
	}

}
