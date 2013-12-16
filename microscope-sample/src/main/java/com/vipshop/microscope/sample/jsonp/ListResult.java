package com.vipshop.microscope.sample.jsonp;

import java.util.List;
import java.util.Map;

public class ListResult extends BasicResult {
	
	private List<Map<String, Object>> result;

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
}
