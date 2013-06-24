package com.sandbox.charts;

import com.sandbox.charts.barClass;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.widgets.Control;
import org.gicentre.utils.stat.BarChart;
import org.gicentre.utils.stat.XYChart;
import org.gwoptics.graphics.graph2D.Graph2D;
import org.gwoptics.graphics.graph2D.traces.ILine2DEquation;
import org.gwoptics.graphics.graph2D.traces.RollingLine2DTrace;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PShape;

//----------------------------------------------------------------------------//
//	The part of the program that handles the RCP part of the program		  //
//----------------------------------------------------------------------------//

public class ProcessingSketch extends Sandbox{
	final static List<PApplet> applets = new ArrayList<PApplet>();
	final static List<Composite> composites = new ArrayList<Composite>();

	@Override
	protected void createWindows(final Shell shell) throws Exception {

		shell.setLayout(new FillLayout());

		final Composite composite = new Composite(shell, SWT.EMBEDDED);

		final Frame frame = SWT_AWT.new_Frame(composite); 
		final StaticBarSketch barGraph = new StaticBarSketch();

		Button button = new Button("Data set 1");
		Button button2 = new Button("Data set 2");
		Button button3 = new Button("Data set 3");
		Button button4 = new Button("Dynamic Data");
		Button button5 = new Button("Stacks");
		Button button6 = new Button("Exit");

		Panel panel = new Panel();
		panel.add(barGraph);
		panel.add(button);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		frame.add(panel);
		barGraph.init();

		composite.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				barGraph.resized(composite.getSize().x, composite.getSize().y);
			}
		});

		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event2)
			{
				int data2[] = {100,200,300,400,500,600,700,800,900,1000,1100}; 
				barGraph.setData(data2);
				barGraph.setFlag();
			}
		});


		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event2)
			{
				int data2[] = {1,2,3,4,5,6,7,8,9,10,11}; 
				barGraph.setData(data2);
				barGraph.setFlag();
			}
		});

		button3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				int data2[] = {10,20,30,40,50,60,70,80,90,100,110}; 
				barGraph.setData(data2);
				barGraph.setFlag();
			}
		});

		button4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				int data2[] = {10,20,30,40,50,60,70,80,90,100,110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240, 250}; 
				barGraph.setData(data2);
				barGraph.setInterrupt();
			}
		});

		button5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				int data2[][] = {{10,20,30,40,50,60,70,80,90,100,110}, {120, 13, 140, 150, 16, 170, 180, 190, 20, 210, 220}, {120, 15, 15, 15, 15, 170, 180, 190, 200, 210, 220}}; 

				barGraph.setStackFlag();
				barGraph.stackedData(data2);
			}
		});

		button6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				barGraph.stop();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		new ProcessingSketch().run();

		for (PApplet applet : applets) {
			applet.stop();
			applet.destroy();
		}

		for (Composite composite : composites) {
			composite.dispose();
		}
	}

	//The part of the program that displays the graph in the RCP window

	/*List of things that need to be altered from outside the library
	 *	Grid size of the graph (X and Y dimensions)
	 * 	Colour of the Graphs
	 * 
	 * 	Labels - passed as an array
	 * 	Title - passed as a string
	 * 	Data - Passed as an array
	 * 
	 * 	Text size
	 * 	Text colour
	 * 
	 * 	toMark
	 * 	Step sizes
	 * 
	 */

	//----------------------------------------------------------------------------//
	//	Start of the StaticBarSketch class										  //
	//----------------------------------------------------------------------------//		
	public class StaticBarSketch extends PApplet {

		int data[] = {100,200,300,400,500,600,700,800,900,1000,1100}; 
		int inputData[];
		String labels[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

		int len, maximum, widthOffset, widthOFbar, toMark, place, steps; //Variables I use throughout my program
		int r,b,g;
		String labelX, labelY;

		barClass[] bars1D, bars2D;
		String graph_title = "This is a bar graph"; 

		int checking = 0;
		int begin = 0;

		//default settings for X and Y size
		int sizeX = 500;
		int sizeY = 500;
		
		boolean flag = true;
		boolean interrupt = false;
		boolean stacked = false;
		int prevY;
		int detMax = 0;

		//----------------------------------------------------------------------------//
		//		Functions that act like a main										  //
		//----------------------------------------------------------------------------//		
		public void setup() {
			//size and colour of the background
			size(sizeX, sizeY); 
			//function to determine the maximum data point
			getMax(data);
			//function to determine the number of data points
			getNumberDatapoints(data);

			graphSetup();

			createData(data);
		}

		public void draw() { 
			if(stacked == false){
			if(flag == true){
				if(interrupt == true){
					dynamicData(inputData);
					begin = begin+1;
					graphSetup();
					createData(data);
				}
				background(100);
				//Display the axis and labels
				displayGraph();
				//represent the data
				displayData();
				//Change colour of bar if mouse hovers over it
				changeColour();
			}
			else{
				transition();
			}
			}
		}	
		//----------------------------------------------------------------------------//
		//		Functions used to set up what the graph will look like				  //
		//----------------------------------------------------------------------------//

		public void getMax(int data2[]){

			//Maximum value of the data points, used to scale the sizes of the bars 
			maximum = max(data2);	
		}

		public void getNumberDatapoints(int data2[]){

			//Determine the number of bars
			len = data.length;
			bars1D = new barClass[len];
		}

		public void graphSetup(){

			//determining the maximum y-label
			toMark = ceil((float) maximum/10)*10;
			if(toMark == maximum){
				toMark = toMark/10 + toMark;
			}

			//determine the step size of the y-labels
			steps = floor((float) toMark/100)*10;
			if(steps == 0){
				steps = toMark/10;
			}

			//width and spacing of the bars
			widthOffset = (sizeX - 40)/len;
			widthOFbar = widthOffset*8/10;
		}

		public void displayGraph(){
			int counter = 0;
			background(100);
			stroke(255);
			//outlines of the graph
			line(40, 20, 40, sizeY-20);
			line(40, sizeY-20, sizeX-20, sizeY-20);

			//setting the text size and colour
			textSize(10);

			//drawing the graph
			for(int i = 0; i < len; i++){
				fill(255);
				text(labels[i], 40 + floor(widthOFbar/2) + i*widthOffset, sizeY-5);
			}

			//setting the y-labels
			while(counter <= toMark){
				fill(255);
				text(counter, 5, sizeY-20-counter*(sizeY-40)/toMark);
				counter = counter+steps;
			}
		}

		public void createData(int data2[]){
			for(int i = 0; i < len; i++){
				bars1D[i] = new barClass((40 + i*widthOffset), (sizeY-20 - data2[i]*(sizeY-40)/toMark), (widthOFbar),(data2[i]*(sizeY-40)/toMark),data2[i]);
			}
		}

		public void displayData(){
			for(int i = 0; i < len; i++){
				rectMode(CORNER);

				beginShape();
				stroke(255);
				fill(0, 121, 184);
				rect(bars1D[i].getXpos(),bars1D[i].getYpos(),bars1D[i].getWidthOFbar(), bars1D[i].getHeightOFbar());
				endShape();
			}
		}

		public void buildingblocks(boolean type, int xPos, int yPos, int previousY, int width_, int height){

			if(type == true){
				noStroke();
				fill(0, 121, 184);
				rect(xPos, previousY, width_, abs(previousY-yPos));
				stroke(255);
				beginShape();
				if(previousY == (sizeY-20)){
					vertex(xPos + width_, previousY);
				}
				vertex(xPos, previousY);
				vertex(xPos, yPos);
				vertex(xPos + width_, yPos);
				vertex(xPos + width_, previousY);
				endShape();
			}
			else{
				fill(100);
				stroke(100);
				rect(xPos, previousY, width_, abs(previousY-yPos));
				if(xPos == 40){
					stroke(255);
					beginShape();
					vertex(xPos, previousY);
					vertex(xPos, yPos);
					vertex(xPos+width_,yPos);
					endShape();	
				}
				else{
					stroke(255);
					line(xPos, yPos, xPos+width_,yPos);
				}
			}
		}

		//-----------------------------------------------------------------------------------------------------//
		//		Functions that changes the effects of the graph when the mouse hovers						   //
		//-----------------------------------------------------------------------------------------------------//	
		public void resized(int x, int y) {

			//Adjusting the variables that specify the dimensions of the graph
			sizeX = x - 20;
			sizeY = y - 50;
			//set the new size of the graph
			size(sizeX,sizeY);

			for(int i = 0; i < len; i++){
				data[i] = bars1D[i].getDataPoint();
			}

			//use the new sizes to work out the new bar widths
			graphSetup();

			createData(data);

			if(flag==false){
				background(100);
				//Display the axis and labels
				displayGraph();
				//represent the data
				displayData();
			}
		}
		
		public void create2DData(int data2[], int startPlace, int size){
			for(int i = 0; i < size; i++){
				if(startPlace == 0){
					bars2D[i] = new barClass((40 + i*widthOffset), (sizeY-20 - data2[i]*(sizeY-40)/toMark), (widthOFbar),(data2[i]*(sizeY-40)/toMark),data2[i]);
				}
				else{
					prevY = bars2D[i+((startPlace-1)*size)].getHeightOFbar();
					bars2D[i+(startPlace*size)] = new barClass((40 + i*widthOffset), (sizeY-20 - ((data2[i])*(sizeY-40)/toMark + prevY)), (widthOFbar),(data2[i]*(sizeY-40)/toMark),data2[i]);	
				}
			}
		}
		
		public void display2Ddata(int startPlace, int size){
			for(int i = 0; i < size; i++){
				rectMode(CORNER);

				beginShape();
				stroke(255);
				fill(20*startPlace, 121, 184-(30*startPlace));
				rect(bars2D[startPlace*size+i].getXpos(),bars2D[startPlace*size+i].getYpos(),bars2D[startPlace*size + i].getWidthOFbar(), bars2D[startPlace*size + i].getHeightOFbar());
				endShape();
			}
		}

		public void stackedData(int input[][]){
			int numberOFstacks = input.length;	
			detMax = 0;

			for(int i = 0; i < numberOFstacks; i++){
				getMax(input[i]);
				detMax = detMax + maximum;
			}
			maximum = detMax;
			bars2D = new barClass[(len*numberOFstacks)];
			graphSetup();
			displayGraph();
			
			for(int i = 0; i < numberOFstacks; i++){
				int indivLen = input[0].length;
				create2DData(input[i], i, indivLen);
				display2Ddata(i, indivLen);
			}	
		}

		public void dynamicData(int input[]){

			for(int i = 0; i < len; i++){
				data[i] = input[i+begin];
			}
			
			myDelay(500);

			if(begin+len == input.length){
				interrupt = false;
				begin = 0;
			}	
		}

		public void changeColour(){
			//returns the value of the data in the array that we are hovering over
			place = round((mouseX-20)/widthOffset);

			//avoiding an out of bounds exception by checking if the value is inside the size of the array
			if(place < len){
				if((mouseY > bars1D[place].getYpos())&&(mouseY < sizeY-20)){
					//draws a rectangle precisely where the other one was, except in another colour, giving the illusion of highlighting
					fill(0, 100, 184);
					rect(bars1D[place].getXpos(),bars1D[place].getYpos(),bars1D[place].getWidthOFbar(), bars1D[place].getHeightOFbar());
					//shows the label with the more exact data to be found in the array
					showLabel(place);
				}
			}
		}

		public void showLabel(int place){

			//creates the text for the label
			String txt =  labels[place] + ": " + bars1D[place].getDataPoint();
			float labelW = textWidth(txt);
			fill(0);
			rect(mouseX+5, mouseY-25, labelW+10, 22);
			fill(255);
			text(txt, mouseX+10, mouseY-10);
		}

		public void transition(){

			flag = false;
			checking = 0;

			for(int i = 0; i < len; i++){
				if(bars1D[i].getDataPoint() > inputData[i]){
					prevY = bars1D[i].getYpos();
					bars1D[i].setDataPoint(bars1D[i].getDataPoint() - 1);
					bars1D[i].setYpos((sizeY-20 - bars1D[i].getDataPoint()*(sizeY-40)/toMark));
					bars1D[i].setHeightOFbar((bars1D[i].getDataPoint()*(sizeY-40)/toMark));
					buildingblocks(false, bars1D[i].getXpos(), bars1D[i].getYpos(), prevY, bars1D[i].getWidthOFbar(), bars1D[i].getHeightOFbar());
				}
				else if(bars1D[i].getDataPoint() < inputData[i]){
					prevY = bars1D[i].getYpos();
					bars1D[i].setDataPoint(bars1D[i].getDataPoint() + 1);
					bars1D[i].setYpos((sizeY-20 - bars1D[i].getDataPoint()*(sizeY-40)/toMark));
					bars1D[i].setHeightOFbar((bars1D[i].getDataPoint()*(sizeY-40)/toMark));
					buildingblocks(true, bars1D[i].getXpos(), bars1D[i].getYpos(), prevY, bars1D[i].getWidthOFbar(), bars1D[i].getHeightOFbar());
				}
				else{
					checking = checking+1;
				}
			}
			if(checking == len){
				flag = true;
			}
		}


		//-----------------------------------------------------------------------------------------------------//
		//						Functions that is API v0 ready 												   //
		//-----------------------------------------------------------------------------------------------------//


		//Function provides a delay to be specified in milliseconds - sleeps the thread for an amount of time. 
		public void myDelay(int ms){

			try{    
				Thread.sleep(ms);	//sleeps the current thread, pauses the drawing for a while
			}
			catch(Exception e){}
		}

		//Function used to interrupt the transition() function and supply it with the new data it needs to change to.
		public void setFlag(){

			stacked = false;	//Interrupt to signal the program to stop drawing a stacked graph
			interrupt = false;	//breaks the program out of the current dynamicData() 
			flag = true;		//breaks the program out of the current loop of transition()
			transition();		//start a new transition
		}

		//Function used to set the flag so that the current task can be stopped and the dynamic bar can be started
		public void setInterrupt(){

			stacked = false;	//Interrupt to signal the program to stop drawing a stacked graph
			flag = true;		//Interrupts the transition() function if it is running
			interrupt = true;	//sets the flag that allows the dynamicData() function to be called
		}

		//Function that sets the flags so that a stacked graph can be drawn
		public void setStackFlag(){
			
			stacked = true;		//Interrupt to signal the program to draw a stacked graph
			flag = true;		//Interrupts the transition() function if it is running
			interrupt = false;	//breaks the program out of the current dynamicData() 
		}

		//Function used to change the data currently used to the data received from input
		public void setData(int input[]){

			inputData = input;	//change the current data used to create the bars to the data received from input
		}

		//-----------------------------------------------------------------------------------------------------//
		//						End of staticBarSketch class												   //
		//-----------------------------------------------------------------------------------------------------//	
	}
}

