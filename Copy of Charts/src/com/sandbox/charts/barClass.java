package com.sandbox.charts;
import processing.core.*;

class barClass{

	int xPos, yPos, width, height, dataPoint;

	barClass(int xPos_, int yPos_, int width_, int height_, int dataPoint_){
		xPos = xPos_;
		yPos = yPos_;
		width = width_;
		height = height_;
		dataPoint = dataPoint_;
	}

	int getXpos() {
		return xPos;
	}
	
	int getYpos() {
		return yPos;
	}
	
	int getWidthOFbar() {
		return width;
	}
	
	int getHeightOFbar() {
		return height;
	}
	
	int getDataPoint(){
		return dataPoint;
	}
	
	void setYpos(int yPos_) {
		yPos = yPos_;
	}

	void setHeightOFbar(int height_) {
		height = height_;
	}
	
	void setDataPoint(int dataPoint_){
		dataPoint = dataPoint_;
	}
}
