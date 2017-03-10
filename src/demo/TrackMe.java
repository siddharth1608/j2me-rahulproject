package demo;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TrackMe extends MIDlet implements CommandListener, LocationListener {

	
	
	private Display display;
	private Form form;
	private Command exit;
	private Command start;
	private TextField email;
	private TextField interval;
	private StringItem info;
	private String emailStr;
	private int sec = 1;
	private Command stop;
	private LocationProvider locationProvider;
	
	public TrackMe() {
		
		
		display = Display.getDisplay(this);
		
		//Form - Title
	    form = new Form("This is my first Eclipse J2me App");
	    
	    //Form - Exit Command
	    exit = new Command("Exit", Command.EXIT, 1);
	    form.addCommand(exit);
	    
	    //Form - Start Command
	    start = new Command("Start", Command.SCREEN, 1);
	    form.addCommand(start);
	    
	    //Form - Stop Command
	    stop = new Command("Stop", Command.SCREEN, 1);
	    
	    form.setCommandListener(this);
	    
	    //Form - Email field
	    email = new TextField("Email","nicogoeminne@gmail.com", 50,TextField.EMAILADDR);
	    form.append(email);
	    
	    //Form - Interval field
	    interval = new TextField("Update Interval(sec)","1", 5, TextField.NUMERIC);
	    form.append(interval);
	    
	    //Form - Location co-ordinates field
	    info = new StringItem("Location:","unknown");
	    form.append(info);
	    
	    
	    try {
	        locationProvider = LocationProvider.getInstance(null);
	      } catch (Exception e) {
	        exit();
	      }
	}

	protected void startApp() throws MIDletStateChangeException {
		
		display.setCurrent(form);
		
	}
	
	public void commandAction(Command cmd, Displayable dis) {
		
		if(cmd == exit){
			exit();
		}
		
		if (cmd == start) {
			form.removeCommand(start);
			emailStr = (email.getString() !=null)? email.getString():"demo@xyz.com";
			
			sec = (interval.getString() != null)? Integer.parseInt(interval.getString()) : 5;
			
			// Start querying GPS data :
			new Thread(){
		        public void run(){
		          locationProvider.setLocationListener(TrackMe.this, sec, -1, -1);
			  }
			}.start();
			
		    form.addCommand(stop);
		}
		
		if (cmd == stop) {
			form.removeCommand(stop);
			
		      // Stop querying GPS data :			
			new Thread(){
		        public void run(){
		          locationProvider.setLocationListener(null, -1, -1, -1);
			  }
			}.start();
			 
		      form.addCommand(start);
		}
		
	}
	
	public void locationUpdated(LocationProvider provider, Location location) {
		
		if (location != null && location.isValid()) {
		      QualifiedCoordinates qc = location.getQualifiedCoordinates();
		      info.setText(
		        "Lat: " + qc.getLatitude() + "n" +
		        "Lon: " + qc.getLongitude() + "n" +
		        "Alt: " + qc.getAltitude() + "n"
		      );
		      
				HttpConnection connection = null;
			    try {
			      String url = "http://localhost:80/updatelocation.jsp?email=" +
			    		  emailStr +
			        "&lat=" + qc.getLatitude() +
			        "&lon=" + qc.getLongitude() +
			        "&alt=" + qc.getAltitude();
			      connection = (HttpConnection) Connector.open(url);
			      int rc = connection.getResponseCode();
			      connection.close();
			    }
			    catch(Exception e){}
			    finally {
			      try {
			        connection.close();
			      }
			      catch(Exception io){
			        io.printStackTrace();
			      }
			    }
		    }
		

	}

	public void providerStateChanged(LocationProvider provider, int newState) {
		// TODO Auto-generated method stub
		
	}
	
	protected void destroyApp(boolean arg0) {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}




	
	public void exit(){
		destroyApp(false);
		notifyDestroyed();
	}


	
	
}
