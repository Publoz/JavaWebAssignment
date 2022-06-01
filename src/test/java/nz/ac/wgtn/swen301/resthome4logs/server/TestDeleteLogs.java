package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.ServletException;

import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestDeleteLogs {

	
	@Test
	public void test_DeleteLogs1() throws ServletException, IOException {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
	
		LogsServlet ls = new LogsServlet();
		
		Persistency.DB.clear();
		
		
		JSONObject log1 = new JSONObject();
		log1.put("id", "jeffrey");
		log1.put("message", "I am a log yo");
		log1.put("timestamp", "14-03-2022 13:30:45");
		log1.put("thread", "main");
		log1.put("logger", "loggerJeff");
		log1.put("level", "DEBUG");
		
		Persistency.DB.add(log1);
		
		System.out.println(Persistency.DB.size());
		assertEquals(1, Persistency.DB.size());
		
		ls.doDelete(request, response);
		
		assertTrue(Persistency.DB.size() == 0);
		assertTrue(response.getStatus() == 200);
		
	}
	
	
	
}
