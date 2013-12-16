package com.vipshop.microscope.sample.jsonp;

import java.util.Map;

public class MapResult extends BasicResult {
	
	private Map<String, Object> result;

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> traceLists) {
		this.result = traceLists;
	}
}
