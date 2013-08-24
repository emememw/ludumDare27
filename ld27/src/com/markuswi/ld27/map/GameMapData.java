package com.markuswi.ld27.map;

public enum GameMapData {

	MAP1("map1.png", false), MAP2("map2.png", false), MAP3("map3.png", true);

	private String filename;
	private boolean rotating;

	private GameMapData(String filename, boolean rotating) {
		this.filename = filename;
		this.rotating = rotating;
	}

	public String getFilename() {
		return this.filename;
	}

	public boolean isRotating() {
		return this.rotating;
	}

}
