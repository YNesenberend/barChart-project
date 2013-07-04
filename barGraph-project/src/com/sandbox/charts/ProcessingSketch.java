package com.sandbox.charts;

import com.sandbox.charts.barClass;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Button;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import processing.core.PApplet;

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
		Button button4 = new Button("Instantly");
		Button button8 = new Button("Clear");
		Button button5 = new Button("Dynamic Data");
		Button button6 = new Button("Stacks");
		Button button7 = new Button("Exit");

		Panel panel = new Panel();
		panel.setBackground(Color.gray);
		panel.add(barGraph);
		panel.add(button);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button8);
		panel.add(button5);
		panel.add(button6);
		panel.add(button7);
		frame.add(panel);
		barGraph.init();


		composite.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean conti = false;
				try{
					while(conti == false){
						conti = barGraph.setResized(composite.getSize().x, composite.getSize().y);
					}
				}catch(Exception e){
					System.out.print(e);
				}
			}
		});

		composite.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				barGraph.stop();
			}
		});

		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event2)
			{
				boolean conti = false;
				int data2[] = {100,200,30,95,50,60,70,80,46,100,110}; 
				barGraph.setTitle("Graph showing how the maximum value on the axis changed");
				while(conti == false){
					conti = barGraph.setFlag(data2);}
			}
		});

		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event2)
			{
				boolean conti = false;
				int data2[] = {1,2,3,4,5,6,7,8,9,10,11}; 
				barGraph.setTitle("The bars of the graph animates to reach its new goal");
				while(conti == false){
					conti = barGraph.setFlag(data2);}

			}
		});

		button3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				boolean conti = false;
				int data2[] = {10,2,31,99,56,23,89,35,29,19,110}; 
				barGraph.setTitle("The bars of the graph animates to reach its new goal");
				while(conti == false){
					conti = barGraph.setFlag(data2);}
			}
		});

		button4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				boolean conti = false;
				int data2[] = {19,18,9,8,8,9,8,9,18,19,20}; 
				barGraph.setTitle("...but it can also be done instantly");
				while(conti == false){
					conti = barGraph.setInstant(data2);}
			}
		});

		button5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				boolean conti = false;
				barGraph.setTitle("Data will be scrolling across your screen shortly");
				while(conti == false){
					conti = barGraph.setInterrupt();}
			}
		});

		button6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				boolean conti = false;
				int data2[][] = {{10,120,30,40,50,60,70,80,90,100,110}, {120, 13, 14, 150, 16, 170, 180, 190, 20, 210, 22}, {120, 15, 150, 15, 15, 170, 180, 190, 200, 210, 220}}; 
				barGraph.setTitle("And last but not least, stacked data");
				while(conti == false){
					conti = barGraph.setStackFlag(data2);
				}
			}
		});

		button7.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				barGraph.stop();
				System.exit(0);
			}
		});

		button8.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event3)
			{
				boolean conti = false;
				int data2[] = {0,0,0,0,0,0,0,0,0,0,0}; 
				barGraph.setTitle("Screen cleared");
				while(conti == false){
					conti = barGraph.setInstant(data2);}
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

	//----------------------------------------------------------------------------//
	//	Start of the StaticBarSketch class										  //
	//----------------------------------------------------------------------------//		
	public class StaticBarSketch extends PApplet {

		private static final long serialVersionUID = 1L;

		int place;

		int r,b,g;

		barClass[] bars1D, bars2D;
		graphClass graph;

		int checking = 0;
		int begin = 0;

		int prevY;
		int detMax = 0;

		//----------------------------------------------------------------------------//
		//		Functions that act like a main										  //
		//----------------------------------------------------------------------------//		
		boolean change = false;
		//default settings for X and Y size
		int sizeX = 500;
		int sizeY = 500;

		public void setup() {

			size(sizeX, sizeY); 							//setting the size of the background

			graph = new graphClass(data, sizeX, sizeY);		//creating a graph object
			bars1D = new barClass[graph.getLen()];			//creating an array of bar objects

			createData(data, graph.getLen());				//filling the bars with data
		}

		int count2D = 0;

		public void draw() { 
			change = false;

			if(stacked == false){
				if(flag == true){
					if(interrupt == true){
						dynamicData();
					}
					else{
						//Display the axis and labels
						displayGraph();
						//represent the data
						displayData();
						//Change colour of bar if mouse hovers over it
						changeColour();
					}
				}
				else{
					transition();
				}
			}
			else{
				if(startingPoint < numberOFstacks){
					growing2Ddata();
				}
			}
			change = true;
		}	

		int layer;

		public void change2DColour(){
			place = round((mouseX-20)/graph.getWidthOffset());//returns the value of the data in the array that we are hovering over

			//avoiding an out of bounds exception by checking if the value is inside the size of the array
			if(place < graph.getLen()){
				if((mouseY < sizeY-20)&&(bars2D[place].getYpos() < mouseY)){
					layer = 0;
				}
				else if((bars2D[place+graph.getLen()].getYpos() < mouseY)&&(bars2D[place].getYpos() > mouseY)){
					layer = 1;
				}
				else if((bars2D[place+2*graph.getLen()].getYpos() < mouseY)&&(bars2D[place+graph.getLen()].getYpos() > mouseY)){
					layer = 2;
				}
				//draws a rectangle precisely where the other one was, except in another colour, giving the illusion of highlighting
				fill(0, 150, 184);
				rect(bars2D[place+layer*graph.getLen()].getXpos(),bars2D[place+layer*graph.getLen()].getYpos(),bars2D[place+layer*graph.getLen()].getWidthOFbar(), bars2D[place+layer*graph.getLen()].getHeightOFbar());
				//shows the label with the more exact data to be found in the array
				show2DLabel(place, layer);
			}
		}


		public void show2DLabel(int place_, int layer){

			String txt =  labelX[place_] + ": Layer " + layer + ":"+ bars2D[place_+layer*graph.getLen()].getDataPoint();
			float labelW = textWidth(txt);
			fill(0);
			rect(mouseX+5, mouseY-25, labelW+10, 22);
			fill(255);
			text(txt, mouseX+10, mouseY-10);
		}

		//-----------------------------------------------------------------------------------------------------//
		//								Functions that is API v0 ready 										   //
		//-----------------------------------------------------------------------------------------------------//

		//-----------------------------------------------------------------------------------------------------//
		//										Delays		 												   //
		//-----------------------------------------------------------------------------------------------------//

		//Function provides a delay to be specified in milliseconds - sleeps the thread for an amount of time. 
		public void myDelay(int ms){

			try{    
				Thread.sleep(ms);	//sleeps the current thread, pauses the drawing for a while
			}
			catch(Exception e){}
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Setting of flags												   //
		//-----------------------------------------------------------------------------------------------------//

		boolean flag = true;		//sets default to normal drawing mode
		boolean interrupt = false;	//disables the interrupt used for animations in normal drawing mode
		boolean stacked = false;	//disables the stacked drawing mode by default

		//Function used to interrupt the transition() function and supply it with the new data it needs to change to.
		public boolean setFlag(int newData[]){
			if (change != true){
				return false;
			}
			else{
				noLoop();
				stacked = false;		//Interrupt to signal the program to stop drawing a stacked graph
				interrupt = false;		//breaks the program out of the current dynamicData() 
				flag = true;			//breaks the program out of the current loop of transition()
				setData(newData);
				transition();			//start a new transition
				loop();
				return true;
			}
		}


		//Function that draws data instantly
		public boolean setInstant(int newData[]){
			if (change != true){
				return false;
			}
			else{
				noLoop();
				stacked = false;		//Interrupt to signal the program to stop drawing a stacked graph
				interrupt = false;		//breaks the program out of the current dynamicData() 
				flag = true;			//breaks the program out of the current loop of transition()
				setData(newData);
				createData(newData, graph.getLen());
				loop();
				return true;
			}
		}

		public boolean setResized(int x, int y){
			if (change != true){
				return false;
			}
			else{
				noLoop();
				try{
					resized(x, y);
				}catch(Exception e){
					System.out.print(e);
				}
				loop();
				return true;
			}
		}

		//Function used to set the flag so that the current task can be stopped and the dynamic bar can be started
		public boolean setInterrupt(){
			if (change != true){
				return false;
			}
			else{
				noLoop();
				stacked = false;		//Interrupt to signal the program to stop drawing a stacked graph
				flag = true;			//Interrupts the transition() function if it is running
				interrupt = true;		//sets the flag that allows the dynamicData() function to be called
				createDynamicStart();
				loop();
				return true;
			}
		}

		//Function that sets the flags so that a stacked graph can be drawn
		public boolean setStackFlag(int newData[][]){
			if (change != true){
				return false;
			}
			else{
				noLoop();
				stacked = true;			//Interrupt to signal the program to draw a stacked graph
				flag = true;			//Interrupts the transition() function if it is running
				interrupt = false;		//breaks the program out of the current dynamicData() 
				stackedData(newData);
				loop();
				return true;
			}
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Setting data													   //
		//-----------------------------------------------------------------------------------------------------//
		int data[] = {10,20,30,40,50,60,70,80,90,100,110}; 		//Variable used to store the input data
		int inputData[];
		int maximum;

		//Function used to change the data currently used to the data received from input
		public void setData(int input[]){

			inputData = input;										//change the current data used to create the bars to the data received from input

			maximum = max(inputData);								//getting the maximum of the new set of data,
			if(maximum > graph.getToMark()){						//and checking if it is more than the current maximum mark.
				graph.setMax(maximum);
				changingMax();
			}
		}

		//Function used to assign the values gotten from the input to the array of bar objects
		public void createData(int data2[], int graphLen){

			for(int i = 0; i < graphLen; i++){
				bars1D[i] = new barClass((40 + i*graph.getWidthOffset()), (sizeY-20 - data2[i]*(graph.getSizeY()-40)/graph.getToMark()),graph.getWidthOfBar(),(data2[i]*(graph.getSizeY()-40)/graph.getToMark()),data2[i]);
			}
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Setting up the Graph											   //
		//-----------------------------------------------------------------------------------------------------//
		String labelX[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}; 	//x-labels
		String graph_title = "This is just a set of data";				//title of the graph
		int counter;
		float txtSize;

		//Function used to set the title of the graph
		public void setTitle(String title_){
			graph_title = title_; 
		}

		//Function used to set the x-labels
		public void setLabel(String labelX_[]){
			labelX = labelX_; 
		}

		//Function that draws what the graph will look like
		public void displayGraph(){

			background(100);
			counter = 0;

			textSize(20);											//heading size
			fill(255);												//heading colour
			txtSize = textWidth(graph_title);
			text(graph_title, (graph.getSizeX()/2-txtSize/2), 20);	//graph_title

			textSize(10);											//setting the text size
			stroke(255);											//line colour

			line(40, sizeY-20, 40, 20);
			line(40, sizeY-20, sizeX-20, sizeY-20);					//draw the axis

			for(int i = 0; i < graph.getLen(); i++){
				fill(255);
				text(labelX[i], 40 + floor(graph.getWidthOfBar()/2) + i*graph.getWidthOffset(), sizeY-5);	//display the labels
			}

			while(counter <= graph.getToMark()){
				fill(255);
				text(counter, 5, sizeY-20-(counter*(graph.getSizeY()-40)/graph.getToMark())); 				//display the y-labels
				counter = counter + graph.getSteps();
			}
		}

		//-----------------------------------------------------------------------------------------------------//
		//							Changing the dimensions of the graph									   //
		//-----------------------------------------------------------------------------------------------------//
		public void resized(int x, int y) {
			//Adjusting the variables that specify the dimensions of the graph
			sizeX = x - 20;
			sizeY = y - 50;

			graph.setSizeX(sizeX);
			graph.setSizeY(sizeY);

			for(int i = 0; i < graph.getLen(); i++){
				data[i] = bars1D[i].getDataPoint();
			}

			createData(data, graph.getLen());
			//set the new size of the graph	

			size(sizeX,sizeY);	

			if(flag==false){
				//Display the axis and labels
				displayGraph();
				//represent the data
				displayData();
			}	
		}

		public void changingMax(){

			for(int i = 0 ; i < graph.getLen(); i++){
				bars1D[i].setHeightOFbar((bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
				bars1D[i].setYpos((sizeY-20 - bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
			}
			displayGraph();
			displayData();
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Drawing a normal Graph											   //
		//-----------------------------------------------------------------------------------------------------//
		public void displayData(){

			for(int i = 0; i < graph.getLen(); i++){
				rectMode(CORNER);
				beginShape();
				stroke(255);
				fill(0, 121, 184);
				rect(bars1D[i].getXpos(),bars1D[i].getYpos(),bars1D[i].getWidthOFbar(), bars1D[i].getHeightOFbar());
				endShape();
			}
		}

		//-----------------------------------------------------------------------------------------------------//
		//								Constructing a growing bar											   //
		//-----------------------------------------------------------------------------------------------------//
		public void decayData(){

			for(int i = 0; i < graph.getLen(); i++){
				rectMode(CORNER);
				stroke(100);
				fill(100);
				if(bars1D[i].getXpos()==40){
					rect(bars1D[i].getXpos()+1,20 ,bars1D[i].getWidthOFbar()-1,abs(20-bars1D[i].getYpos()));
				}
				else{
					rect(bars1D[i].getXpos(),20 ,bars1D[i].getWidthOFbar(),abs(20-bars1D[i].getYpos()));
				}
			}
		}

		public void transition(){

			flag = false;
			checking = 0;

			for(int i = 0; i < graph.getLen(); i++){
				if(bars1D[i].getDataPoint() > inputData[i]){
					prevY = bars1D[i].getYpos();
					bars1D[i].setDataPoint(bars1D[i].getDataPoint() - 1);
					bars1D[i].setYpos((sizeY-20 - bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
					bars1D[i].setHeightOFbar((bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
					decayData();
					displayData();
				}
				else if(bars1D[i].getDataPoint() < inputData[i]){
					prevY = bars1D[i].getYpos();
					bars1D[i].setDataPoint(bars1D[i].getDataPoint() + 1);
					bars1D[i].setYpos((sizeY-20 - bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
					bars1D[i].setHeightOFbar((bars1D[i].getDataPoint()*(graph.getSizeY()-40)/graph.getToMark()));
					displayData();
				}
				else{
					checking++;
				}
			}
			if(checking == graph.getLen()){
				flag = true;
			}
		}
		//-----------------------------------------------------------------------------------------------------//
		//									Scrolling Data													   //
		//-----------------------------------------------------------------------------------------------------//
		barClass[] barsDynamic;
		String Xlabels[];
		int spacing;
		int beginMinute, beginSecond;

		public void createDynamicStart(){
			beginMinute = minute();
			barsDynamic = new barClass[graph.getLen()+1];
			Xlabels = new String[graph.getLen()+1];
			for(int i = 0; i <= graph.getLen(); i++){
				barsDynamic[i] = new barClass((40 + i*graph.getWidthOffset()), (sizeY-20 - 10*(graph.getSizeY()-40)/graph.getToMark()),graph.getWidthOfBar(),(10*(graph.getSizeY()-40)/graph.getToMark()),10);
				Xlabels[i] = "0m0s";
			}
			spacing = graph.getWidthOffset()-graph.getWidthOfBar();
		}

		public void dynamicDisplay(){
			for(int i = 0; i < graph.getLen(); i++){
				rectMode(CORNER);
				beginShape();
				stroke(255);
				fill(0, 121, 184);
				rect(barsDynamic[i].getXpos(),barsDynamic[i].getYpos(),barsDynamic[i].getWidthOFbar(), barsDynamic[i].getHeightOFbar());
				endShape();
			}
		}

		public void displayDynamicGraph(){

			background(100);
			counter = 0;

			textSize(20);											//heading size
			fill(255);												//heading colour
			txtSize = textWidth(graph_title);
			text(graph_title, (graph.getSizeX()/2-txtSize/2), 20);	//graph_title

			textSize(10);											//setting the text size
			stroke(255);											//line colour

			line(40, sizeY-20, 40, 20);
			line(40, sizeY-20, sizeX-20, sizeY-20);					//draw the axis

			for(int i = 0; i < graph.getLen(); i++){
				fill(255);
				if(i != 0){
					text(Xlabels[i], floor(graph.getWidthOfBar()/2) + barsDynamic[i].getXpos(), sizeY-5);	//display the labels
				}
			}

			while(counter <= graph.getToMark()){
				fill(255);
				text(counter, 5, sizeY-20-(counter*(graph.getSizeY()-40)/graph.getToMark())); 				//display the y-labels
				counter = counter + graph.getSteps();
			}
		}

		public int getData(){

			int value = 0;
			if((mouseY < graph.getSizeY())&&(mouseY > 0)){
				value = mouseY*graph.getToMark()/(graph.getSizeY()+40);	
			}
			return value;
		}

		public String getLabel(){

			int s = second();
			int m = minute();
			String labelX_; 
			labelX_ = ""+abs(m-beginMinute)+"m"+s+"s";
			return labelX_;
		}

		public void dynamicData(){
			displayDynamicGraph();
			if((barsDynamic[0].getWidthOFbar() - 1) >= 0){
				for(int i = 0; i < graph.getLen(); i++){
					if(i == 0){
						barsDynamic[i].setWidth(barsDynamic[i].getWidthOFbar() - 1);
					}
					else if(i == (graph.getLen())){
						barsDynamic[i].setWidth(graph.getWidthOfBar()-barsDynamic[i].getWidthOFbar());
						barsDynamic[i].setXpos(barsDynamic[i].getXpos() - 1);
					}
					else{
						barsDynamic[i].setXpos(barsDynamic[i].getXpos() - 1);
					}
				}
			}
			else{
				if (spacing > 0){
					for(int i = 1; i < graph.getLen(); i++){

						barsDynamic[i].setXpos(barsDynamic[i].getXpos() - 1);
					}
					spacing--;
				}
				else{
					int dyData = getData();
					barsDynamic[graph.getLen()] = new barClass((40+spacing + (graph.getLen()-1)*graph.getWidthOffset()), (sizeY-20 - dyData*(graph.getSizeY()-40)/graph.getToMark()),graph.getWidthOfBar(),(dyData*(graph.getSizeY()-40)/graph.getToMark()),dyData);
					Xlabels[graph.getLen()] = getLabel();
					for(int i = 0; i < graph.getLen(); i++){
						barsDynamic[i] = barsDynamic[i+1];
						Xlabels[i] = Xlabels[i+1];
					}
					spacing = graph.getWidthOffset()-graph.getWidthOfBar();
				}
			}
			dynamicDisplay();
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Stacked Graph													   //
		//-----------------------------------------------------------------------------------------------------//
		int numberOFstacks;
		int startingPoint;

		public void stackedData(int input[][]){
			numberOFstacks = input.length;	
			startingPoint = 0;
			int numbers[] = new int[graph.getLen()];


			for(int i = 0; i < numberOFstacks; i++){
				for(int j = 0; j < graph.getLen(); j++){
					numbers[j] = numbers[j]+input[i][j];
				}
			}
			int getMax = max(numbers);
			bars2D = new barClass[(graph.getLen()*numberOFstacks)];
			graph.setMax(getMax);
			displayGraph();

			for(int i = 0; i < numberOFstacks; i++){
				create2DData(input[i], i, graph.getLen());
				display2Ddata(i, graph.getLen());
			}	
		}

		public void create2DData(int data2[], int startPlace, int size){
			for(int i = 0; i < size; i++){
				if(startPlace == 0){
					bars2D[i] = new barClass((40 + i*graph.getWidthOffset()), (sizeY-20 - data2[i]*(graph.getSizeY()-40)/graph.getToMark()), 0,(data2[i]*(graph.getSizeY()-40)/graph.getToMark()),data2[i]);
				}
				else{
					prevY = bars2D[i+((startPlace-1)*size)].getYpos();
					bars2D[i+(startPlace*size)] = new barClass((40 + i*graph.getWidthOffset()),  prevY -(data2[i])*(graph.getSizeY()-40)/graph.getToMark(),0,(data2[i]*(graph.getSizeY()-40)/graph.getToMark()),data2[i]);	
				}
			}
		}

		public void display2Ddata(int startPlace, int size){
			for(int i = 0; i < size; i++){
				rectMode(CORNER);

				beginShape();
				stroke(255);
				if((bars2D[startPlace*size + i].getWidthOFbar()==0)&&(bars2D[startPlace*size+i].getXpos() != 40)){
				stroke(100);	
				}
				fill(20*startPlace, 121-(20*startPlace), 184-(30*startPlace));
				rect(bars2D[startPlace*size+i].getXpos(),bars2D[startPlace*size+i].getYpos(),bars2D[startPlace*size + i].getWidthOFbar(), bars2D[startPlace*size + i].getHeightOFbar());
				endShape();
			}
		}

		public void growing2Ddata(){

			for(int i = 0; i < graph.getLen(); i++){
				if(bars2D[i+startingPoint*graph.getLen()].getWidthOFbar() < graph.getWidthOfBar()){
					bars2D[i+startingPoint*graph.getLen()].setWidth(bars2D[i+startingPoint*graph.getLen()].getWidthOFbar()+1);
				}
				else if(startingPoint < numberOFstacks-1){
					startingPoint++;
				}
			}
			display2Ddata(startingPoint, graph.getLen());
		}

		//-----------------------------------------------------------------------------------------------------//
		//									Special effects													   //
		//-----------------------------------------------------------------------------------------------------//

		public void changeColour(){
			place = round((mouseX-20)/graph.getWidthOffset());//returns the value of the data in the array that we are hovering over

			//avoiding an out of bounds exception by checking if the value is inside the size of the array
			if(place < graph.getLen()){
				if((mouseY > bars1D[place].getYpos())&&(mouseY < sizeY-20)){
					//draws a rectangle precisely where the other one was, except in another colour, giving the illusion of highlighting
					fill(0, 150, 184);
					rect(bars1D[place].getXpos(),bars1D[place].getYpos(),bars1D[place].getWidthOFbar(), bars1D[place].getHeightOFbar());
					//shows the label with the more exact data to be found in the array
					showLabel(place);
				}
			}
		}

		public void showLabel(int place){

			String txt =  labelX[place] + ": " + bars1D[place].getDataPoint();
			float labelW = textWidth(txt);
			fill(0);
			rect(mouseX+5, mouseY-25, labelW+10, 22);
			fill(255);
			text(txt, mouseX+10, mouseY-10);
		}

		//-----------------------------------------------------------------------------------------------------//
		//								End of staticBarSketch class										   //
		//-----------------------------------------------------------------------------------------------------//	
	}
}

