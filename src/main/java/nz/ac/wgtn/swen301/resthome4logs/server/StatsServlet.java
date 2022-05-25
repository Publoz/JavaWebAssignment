package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class StatsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		resp.setContentType("text/html");
		
		out.print("<html>");
		out.print("<table>");
		out.print("<thead>");
		out.print("<tr>");
		out.print("<th>logger</th><th>ALL</th><th>TRACE</th><th>DEBUG</th>"
				+ "<th>INFO</th><th>WARN</th><th>ERROR</th><th>FATAL</th><th>OFF</th>");
		out.print("</tr>");
		out.print("</thead>");
		
		HashMap<String, Integer[]> loggerCounts = new HashMap<String, Integer[]>();
		for(JSONObject jo: Persistency.logs) {
			String logger = jo.getString("logger");
			if(loggerCounts.get(logger) == null) {
				loggerCounts.put(logger, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0});
			}
			Integer[] arr = loggerCounts.get(logger);
			int index = Persistency.getLevel(jo.getString("level"));
			arr[index] += 1;
		}
		out.print("<tbody>");
		for(String key: loggerCounts.keySet()) {
			Integer[] arr = loggerCounts.get(key);
			out.print("<tr>");
			out.print("<td>" + key + "</td>");
			for(int i = 0; i < 8; i++) {
				int val;
				if(arr[i] == null) {
					val = 0;
				} else {
					val = arr[i];
				}
				out.print("<td>" + val + "</td>");
			}
			out.print("</tr>");
		}
		out.print("</tbody>");
		out.print("</table>");
		out.print("</html>");
	}

}
