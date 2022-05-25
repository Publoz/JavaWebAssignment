package nz.ac.wgtn.swen301.resthome4logs.server;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestGetLogs {

	
	@Test
	public void test_GetLogs1() throws ServletException, IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		request.setParameter("level", "ALL");
		request.setParameter("limit", "3");
		
		
		LogsServlet ls = new LogsServlet();
		Persistency p = new Persistency();
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		p.logs.add(log1);
		
		JSONObject log2 = new JSONObject();
		log2.put("id", "jeffrey");
		log2.put("message", "I am also a log yo");
		log2.put("timestamp", "14-03-2022 14:35:47");
		log2.put("thread", "main");
		log2.put("logger", "loggerJeff");
		log2.put("level", "INFO");
		
		p.logs.add(log2);
		
		JSONObject log3 = new JSONObject();
		log3.put("id", "jeffrey");
		log3.put("message", "I am too a log yo");
		log3.put("timestamp", "14-03-2022 15:37:48");
		log3.put("thread", "main");
		log3.put("logger", "loggerJeff");
		log3.put("level", "WARN");
		
		p.logs.add(log3);
		
		JSONObject log4 = new JSONObject();
		log4.put("id", "jeffrey");
		log4.put("message", "I am three a log yo");
		log4.put("timestamp", "14-03-2022 16:38:49");
		log4.put("thread", "main");
		log4.put("logger", "loggerJeff");
		log4.put("level", "WARN");
		
		p.logs.add(log4);
		
		
		ls.doGet(request, response);
		
		assertEquals(200, response.getStatus());
		

	}
	
}
