package com.vipshop.microscope.job.mapreduce;

public class PicketGuy {
	private String name;
	private int position;

	public PicketGuy(String name, int position) {
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}