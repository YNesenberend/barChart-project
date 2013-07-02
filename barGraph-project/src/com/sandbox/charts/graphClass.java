package com.sandbox.charts;

import processing.core.PApplet;

public class graphClass extends PApplet{
	private static final long serialVersionUID = 1L;
	
	int constructionData[];
	int prevToMark, maximum, toMark, steps, len, widthOffset, widthOfBar, sizeX, sizeY;
	
//----------------------------------------------------------------------------//
//							Constructor										  //
//----------------------------------------------------------------------------//
	graphClass(int inputData[], int sizeX_, int sizeY_){
		sizeX = sizeX_;
		sizeY = sizeY_;
		
		detLength(inputData);
		detMax(inputData);
		detToMark(maximum);
		detSteps(toMark);
		detWidthOffset(len);
		barWidth(widthOffset);
	}
//----------------------------------------------------------------------------//
//						determining values									  //
//----------------------------------------------------------------------------//
	void detLength(int data[]){
		len = data.length;
	}
	
	void detMax(int data[]){
		maximum = max(data);
	}
	
	void detToMark(int maximum_){
		toMark = (int) (maximum_*1.2);
	}
	
	void detSteps(int toMark_){
		steps = floor((float) toMark_/100)*10;
		
		if(steps == 0){
			steps = toMark_/10;
		} 
	}
	
	void detWidthOffset(int len_){
		widthOffset = (sizeX - 40)/len_;
	}
	
	void barWidth(int widthOffset_){
		widthOfBar = widthOffset_*8/10;
	}
	
//----------------------------------------------------------------------------//
//							get functions									  //
//----------------------------------------------------------------------------//
	int getMax(){
		return maximum;
	}
	
	int getLen(){
		return len;
	}
	
	int getToMark(){
		return toMark;
	}
	
	int getPrevToMark(){
		return prevToMark;
	}
	
	int getSteps(){
		return steps;
	}
	
	int getWidthOfBar(){
		return widthOfBar;
	}
	
	int getSizeX(){
		return sizeX;
	}
	
	int getSizeY(){
		return sizeY;
	}
	
	int getWidthOffset(){
		return widthOffset;
	}
	
//----------------------------------------------------------------------------//
//							set Functions									  //
//----------------------------------------------------------------------------//
	void setMax(int maximum_){
		maximum = maximum_;	
		detToMark(maximum);
		detSteps(toMark);
	}
	
	void setLen(int length_){
		len = length_;
		detWidthOffset(len);
		barWidth(widthOffset);
	}
	
	void setToMark(int toMark_){
		toMark = toMark_;
		detSteps(toMark);
	}
	
	void setSteps(int steps_){
		steps = steps_;
	}
	
	void setWidthOfBar(int widthOfBar_){
		widthOfBar = widthOfBar_;
	}
	
	void setSpacing(int spacing_){
		widthOfBar = widthOffset-spacing_;
	}
	
	void setSizeX(int sizeX_){
		sizeX = sizeX_;
		detWidthOffset(len);
		barWidth(widthOffset);
	}
	
	void setSizeY(int sizeY_){
		sizeY = sizeY_;
	}
}
