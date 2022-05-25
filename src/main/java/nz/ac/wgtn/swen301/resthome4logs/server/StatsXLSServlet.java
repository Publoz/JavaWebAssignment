package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.*;
import org.json.JSONObject;


public class StatsXLSServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		XSSFWorkbook spreadsheet = new XSSFWorkbook();
		XSSFSheet sheet = spreadsheet.createSheet("stats");
		
		XSSFRow firstLine = sheet.createRow(0);
		
		firstLine.createCell(0).setCellValue("logger");
		firstLine.createCell(1).setCellValue("ALL");
		firstLine.createCell(2).setCellValue("TRACE");
		firstLine.createCell(3).setCellValue("DEBUG");
		firstLine.createCell(4).setCellValue("INFO");
		firstLine.createCell(5).setCellValue("WARN");
		firstLine.createCell(6).setCellValue("ERROR");
		firstLine.createCell(7).setCellValue("FATAL");
		firstLine.createCell(8).setCellValue("OFF");
		
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
		
		int count = 1;
		for(String key: loggerCounts.keySet()) {
			Integer[] arr = loggerCounts.get(key);
			XSSFRow row = sheet.createRow(count);
			row.createCell(0).setCellValue(key);
			for(int i = 0; i < 8; i++) {
				int val;
				if(arr[i] == null) {
					val = 0;
				} else {
					val = arr[i];
				}
				row.createCell(i + 1).setCellValue(val);
			}
			count++;
		}
		
		resp.setContentType("application/vnd.ms-excel");
		ServletOutputStream out = resp.getOutputStream();
		spreadsheet.write(out);
		out.close();
		
		
	}

}
