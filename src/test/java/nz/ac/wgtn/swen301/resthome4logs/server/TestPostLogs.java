package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;


import org.json.JSONObject;
import org.junit.Test;


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestPostLogs {

	/**
	 * Valid test
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_PostLogs1() throws ServletException, IOException {
		Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogsServlet ls = new LogsServlet();
		
		JSONObject log = new JSONObject();
		log.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
		log.put("message", "I am a log");
		log.put("timestamp", "14-03-2001 16:31:57");
		log.put("thread", "main");
		log.put("logger", "loggerJeff");
		log.put("level", "DEBUG");
		
	
		request.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		request.setContent(log.toString().getBytes("utf-8"));
		
		ls.doPost(request, response);
		assertTrue(response.getStatus() == 201);
		assertTrue(Persistency.logs.size() == 1);
	}
	
	/**
	 * Test misformed log
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_PostLogs2() throws ServletException, IOException {
		Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogsServlet ls = new LogsServlet();
		
		JSONObject log = new JSONObject();
		log.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
		log.put("message", "I am a log");
		log.put("timestamp", "14-03-2001 16:31:57");
		log.put("thread", "main");
		log.put("logga", "loggerJeff");
		log.put("level", "DEBUG");
		
	
		request.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		request.setContent(log.toString().getBytes("utf-8"));
		
		ls.doPost(request, response);
		assertTrue(response.getStatus() == 400);
	}
	
	/**
	 * Test misformed level
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_PostLogs3() throws ServletException, IOException {
		Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogsServlet ls = new LogsServlet();
		
		JSONObject log = new JSONObject();
		log.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
		log.put("message", "I am a log");
		log.put("timestamp", "14-03-2001 16:31:57");
		log.put("thread", "main");
		log.put("logger", "loggerJeff");
		log.put("level", "DEBUGGY");
		
	
		request.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		request.setContent(log.toString().getBytes("utf-8"));
		
		ls.doPost(request, response);
		assertTrue(response.getStatus() == 400);
	}
	
	/**
	 * Test duplicate id
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Test
	public void test_PostLogs4() throws ServletException, IOException {
		Persistency.logs.clear();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogsServlet ls = new LogsServlet();
		
		JSONObject log = new JSONObject();
		log.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
		log.put("message", "I am a log");
		log.put("timestamp", "14-03-2001 16:31:57");
		log.put("thread", "main");
		log.put("logger", "loggerJeff");
		log.put("level", "DEBUG");
		
	
		request.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		request.setContent(log.toString().getBytes("utf-8"));
		
		ls.doPost(request, response);
	
		
		assertTrue(response.getStatus() == 201);
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		log = new JSONObject();
		log.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
		log.put("message", "I am a big log");
		log.put("timestamp", "14-03-2001 16:31:59");
		log.put("thread", "main");
		log.put("logger", "loggerJeff");
		log.put("level", "WARN");
		request.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		request.setContent(log.toString().getBytes("utf-8"));
		
		ls.doPost(request, response);
		assertTrue(response.getStatus() == 409);
		
	}
	
}
