package demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PushRegistryDemo extends MIDlet implements CommandListener{

	private Display display;
	private Form form;
	private Command exit;

	public PushRegistryDemo() {
		// TODO Auto-generated constructor stub
		display = Display.getDisplay(this);
		
		//Form - Title
	    form = new Form("TrackMe");
	    
	    exit = new Command("Exit", Command.EXIT, 1);
	    
	    form.addCommand(exit);
	    
	    form.setCommandListener(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		try {
			scheduleMIDlet(1000*60);
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
		display.setCurrent(form);
		
		// cyclic background task info.
		long REFRESH_TIME = (long) (1000*60); // every 1 minute
		
		Timer aTimer = new Timer();
		MyTask myTask = new MyTask();
		aTimer.schedule(myTask, 0, REFRESH_TIME);

				
	}

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO Auto-generated method stub
		if(cmd==exit){
			
			exit();
		}
	}
	
	private void scheduleMIDlet(final long delta) throws ConnectionNotFoundException, ClassNotFoundException
	{

		long t= PushRegistry.registerAlarm("demo.PushRegistryDemo", new Date().getTime() + delta);

	}

	
	public void exit() {
		try {
			destroyApp(false);
			notifyDestroyed();
			
		} catch (MIDletStateChangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	class MyTask extends TimerTask{
		private PushRegistryDemo owner;

		void setOwner(PushRegistryDemo owner)
		{
			this.owner = owner;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			
			Alert obj = new Alert("Hello");
			obj.setTimeout(Alert.FOREVER);
			
			display.setCurrent(obj);
			
		}
		
	}
}
