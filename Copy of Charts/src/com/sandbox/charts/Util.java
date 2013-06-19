package com.sandbox.charts;

import processing.core.PApplet;

public class Util extends PApplet {
	public class timer
	{
	  float startTime;
	  int tOver;
	   
	  public timer(int millisecs)
	  {
	    startTime=millis();
	    tOver=millisecs;
	  }
	   
	  boolean over()
	  {
	    if ((millis() - startTime)>tOver)
	      return true;
	    else
	       return false;
	  }
	   
	  void reset()
	  {
	    startTime=millis();
	  }
	   
	  void setOver(int millisecs)
	  {
	    tOver=millisecs;
	    reset();
	  }
	   
	   
	}
	////////////////////////////////////////////////////////////
	String time()
	{
	  String h=str(hour()),m=str(minute()),s=str(second());
	   
	  h=(h.length()==1)?"0"+h:h;
	  m=(m.length()==1)?"0"+m:m;
	  s=(s.length()==1)?"0"+s:s;
	 
	   
	  return h+":"+m+":"+s;
	}
	////////////////////////////////////////////////////////////
	String date()
	{
	  String d=str(day()),m=str(month());
	   
	  d=(d.length()==1)?"0"+d:d;
	  m=(m.length()==1)?"0"+m:m;
	 
	  return d+"/"+m+"/"+str(year());
	}

}
