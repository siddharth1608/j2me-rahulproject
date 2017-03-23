package demo;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PlainLocation extends MIDlet implements CommandListener{

	private Display display;
	private Form form;
	private Command exit;
	
	public PlainLocation() {
		// TODO Auto-generated constructor stub
		
		display = Display.getDisplay(this);
		
		//Form - Title
	    form = new Form("TrackMe");
	    
	    //Form - Exit Command
	    exit = new Command("Exit", Command.EXIT, 1);
	    form.addCommand(exit);
	    
	    form.setCommandListener(this);
		
		
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		display.setCurrent(form);

		// Set criteria for selecting a location provider:
				// accurate to 500 meters horizontally
			    
			    String locationAPIVersion = (String)System.getProperty("microedition.location.version");
			    
			    if(locationAPIVersion == "")
			    {
			    	
			    	Alert obj=new Alert("Technical error");
					obj.setTimeout(Alert.FOREVER);
					obj.setString("No Location Provider available");
					obj.setType(AlertType.WARNING);
					
					display.setCurrent(obj);
			    	
			    }else{
			    	try{
			    		Criteria cr= new Criteria();
				    	cr.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);
						//System.out.print("Cell id" +LocationProvider.);
							
						Alert obj=new Alert("Technical error");
						obj.setTimeout(Alert.FOREVER);
						obj.setString(""+Location.MTE_CELLID+" "+cr.getPreferredResponseTime());
						obj.setType(AlertType.WARNING);
						
						display.setCurrent(obj);
						//LocationProvider lp= LocationProvider.getInstance(cr);
						//lp.toString();

			    	}catch(SecurityException e){
			    		
			    		Alert obj=new Alert("Technical error");
						obj.setTimeout(Alert.FOREVER);
						obj.setString(""+Location.MTE_CELLID+" "+e.getMessage());
						obj.setType(AlertType.WARNING);
						display.setCurrent(obj);
						
			    	}
			    	
			    						
			    }
	}

	public void exit(){
		try {
			destroyApp(false);
		} catch (MIDletStateChangeException e) {
			// TODO Auto-generated catch block
			Alert obj=new Alert(e.getMessage());
			obj.setTimeout(Alert.FOREVER);
			
			display.setCurrent(obj);
		}
		notifyDestroyed();
	}

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO Auto-generated method stub
		if(cmd == exit){
			exit();
		}
	}
}
