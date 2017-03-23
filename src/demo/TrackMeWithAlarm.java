package demo;

import java.util.Date;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class TrackMeWithAlarm extends MIDlet implements CommandListener, LocationListener {

	
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
	private StringItem state;
	
	public TrackMeWithAlarm() {
		
		
		display = Display.getDisplay(this);
		
		//Form - Title
	    form = new Form("TrackMe");
	    
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
	    
	    //Form - Location Provider current state
	    state = new StringItem("Status:","Offline");
	    form.append(state);
	    
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
		          locationProvider.setLocationListener(TrackMeWithAlarm.this, sec, -1, -1);
			  }
			}.start();
			
		    form.addCommand(stop);
		}
		
		if (cmd == stop) {
			form.removeCommand(stop);
			
			state.setText("Offline");
			
		      // Stop querying GPS data :			
			new Thread(){
		        public void run(){
		          locationProvider.setLocationListener(null, -1, -1, -1);
			  }
			}.start();
			 
		      form.addCommand(start);
		}		
	}
	
	//This method is called when the location is updated
	public void locationUpdated(LocationProvider provider, Location location) {
		
		if(provider.getState() == LocationProvider.OUT_OF_SERVICE){
			
			state.setText("Disconnected");
			
		}
		else if(provider.getState() == LocationProvider.TEMPORARILY_UNAVAILABLE){
			state.setText("Temporarily down");
		}
		else{
			state.setText("Connected");
		}
		
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
			    catch(Exception e){
			    	
			    }
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

	// This method is called when the state of Location Provider is changed
	public void providerStateChanged(LocationProvider provider, int newState) {
		// TODO Auto-generated method stub
		
		if(newState == LocationProvider.OUT_OF_SERVICE){
			
			state.setText("Disconnected");
			
		}
		else if(newState == LocationProvider.TEMPORARILY_UNAVAILABLE){
			state.setText("Temporarily down");
		}
		else{
			state.setText("Connected");
		}
		
		
	}
	
	protected void destroyApp(boolean arg0) {
		// TODO Auto-generated method stub
		
		new Thread(){
			
			public void run(){
				try {
					
					long l = PushRegistry.registerAlarm("demo.TrackMeWithAlarm", new Date().getTime()+8000);
					
				} catch (ConnectionNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
		
			
		display=null;
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	


	
	public void exit(){
		destroyApp(false);
		notifyDestroyed();
	}


	
	
}
