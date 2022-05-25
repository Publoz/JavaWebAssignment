package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class StatsCSVServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/csv");
		
		PrintWriter out = resp.getWriter();
		
		out.print("logger\tALL\tTRACE\tDEBUG\tINFO\tWARN\tERROR\tFATAL\tOFF\n");
		
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
		
		for(String key: loggerCounts.keySet()) {
			Integer[] arr = loggerCounts.get(key);
			out.print(key);
			for(int i = 0; i < 8; i++) {
				int val;
				if(arr[i] == null) {
					val = 0;
				} else {
					val = arr[i];
				}
				out.print("\t" + val);
			}
			out.print("\n");
		}
		
	}

}
