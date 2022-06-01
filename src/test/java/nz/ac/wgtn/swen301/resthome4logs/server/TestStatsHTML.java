package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsHTML {

	
	@Test
	public void test_StatsHTML1() throws ServletException, IOException {
		Persistency.DB.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		
		StatsServlet ss = new StatsServlet();
	
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "TRACE");
		
		Persistency.DB.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "Chur");
		log2.put("timestamp", "14-03-2022 13:30:45");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "TRACE");
		
		Persistency.DB.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "Chur");
		log3.put("timestamp", "14-03-2022 13:30:45");
		log3.put("thread", "main");
		log3.put("logger", "loggerTatum");
		log3.put("level", "OFF");
		
		Persistency.DB.add(log3);
		
		ss.doGet(request, response);
		
		assertTrue(response.getStatus() == 200);
		assertTrue(response.getContentType().equals("text/html"));
		
		Document doc = Jsoup.parse(response.getContentAsString());
		assertTrue(doc.hasText());
	

		Elements table = doc.getElementsByTag("table");
		assertTrue(table.hasText());
	
		Element headRow = doc.getElementsByTag("thead").get(0).getElementsByTag("tr").get(0);
		assertTrue(headRow.hasText());
		
		assertTrue(headRow.childrenSize() == 9);
		assertEquals("logger", headRow.child(0).text());
		assertEquals("ALL", headRow.child(1).text());
		assertEquals("TRACE", headRow.child(2).text());
		assertEquals("DEBUG", headRow.child(3).text());
		assertEquals("INFO", headRow.child(4).text());
		assertEquals("WARN", headRow.child(5).text());
		assertEquals("ERROR", headRow.child(6).text());
		assertEquals("FATAL", headRow.child(7).text());
		assertEquals("OFF", headRow.child(8).text());
		
		Elements rows = doc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
		assertTrue(rows.size() == 2);
		
		Element jeffRow = rows.get(0);
		assertTrue(jeffRow.child(0).text().equals("loggerJeff"));
		assertTrue(jeffRow.child(2).text().equals("2"));
		for(int i = 3; i < 9; i++) {
			assertTrue(jeffRow.child(i).text().equals("0"));
		}
		assertTrue(jeffRow.child(1).text().equals("0"));
		
		
		Element tatRow = rows.get(1);
		assertTrue(tatRow.child(0).text().equals("loggerTatum"));
		
		
		for(int i = 1; i < 8; i++) {
			assertTrue(tatRow.child(i).text().equals("0"));
		}
		
		assertTrue(tatRow.child(8).text().equals("1"));
		
	}
}
