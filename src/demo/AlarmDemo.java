package demo;

import java.util.Date;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

// 25-Mar-17: This MIDlet is working as expected.restarts within a min after exit.
// Only issue it asks user permission each time before start and exit

public class AlarmDemo extends MIDlet implements CommandListener {

	private Display display;
	private Form form;
	private Command exit;
	
	public AlarmDemo() {
		
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
		try {
			//When the app is cliosed. Set an alarm to automatically start the app
			//1000*60 is 1 minute
			long l = PushRegistry.registerAlarm("demo.AlarmDemo", new Date().getTime()+1000*60);
			
		} catch (ConnectionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getName() + "started");
		
		display.setCurrent(form);
	}
	
	public void exit(){
		try {
			destroyApp(false);
		} catch (MIDletStateChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notifyDestroyed();
	}

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO Auto-generated method stub
		if(cmd==exit){
			
			exit();
		}
		
	}

}
