package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsXLS {

	@Test
	public void test_StatsXLS1() throws ServletException, IOException {
		
Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		
		StatsXLSServlet sxs = new StatsXLSServlet();
	
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "TRACE");
		
		Persistency.logs.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "Chur");
		log2.put("timestamp", "14-03-2022 13:30:45");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "TRACE");
		
		Persistency.logs.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "Chur");
		log3.put("timestamp", "14-03-2022 13:30:45");
		log3.put("thread", "main");
		log3.put("logger", "loggerTatum");
		log3.put("level", "OFF");
		
		Persistency.logs.add(log3);
		
		sxs.doGet(request, response);
		
		assertTrue(response.getStatus() == 200);
		assertTrue(response.getContentType().equals("application-vnd.ms-excel"));
		
		String content = response.getContentAsString();
		
		System.out.println(content);
		
		
		
	}
}
