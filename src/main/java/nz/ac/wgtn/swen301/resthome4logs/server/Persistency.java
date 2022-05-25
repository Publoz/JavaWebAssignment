package nz.ac.wgtn.swen301.resthome4logs.server;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Persistency {

	public static List<JSONObject> logs = new ArrayList<JSONObject>();
	
	
	
	public static JSONArray getLogs(int level, int num){
		//ArrayList<JSONObject> list = new ArrayList<>();
		
		JSONArray arr = new JSONArray(); 
		int count = 0;
		
		
		for(int i = logs.size() - 1; i >= 0; i--) {
			if(getLevel(logs.get(i).getString("level")) >= level) {
				arr.put(logs.get(i));
				count++;
				if(count >= num) {
					break;
				}
			}
		}
		
		return arr;
	}
	
	public static int getLevel(String level) {
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
	
	public static void delete() {
		logs.clear();
	}
	
}
