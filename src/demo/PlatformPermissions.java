package demo;

import javax.bluetooth.LocalDevice;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PlatformPermissions extends MIDlet implements CommandListener{

	private Display display;
	private Form form;
	private Command exit;
	
	private String getPermission(String permission){
		
		return "" + checkPermission(permission);
		
	}
	
	public PlatformPermissions() {
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
		
		StringItem prop1 = new StringItem("javax.microedition.io.Connector.comm", getPermission("javax.microedition.io.Connector.comm"));
		StringItem prop2 = new StringItem("javax.microedition.io.Connector.bluetooth.client", getPermission("javax.microedition.io.Connector.bluetooth.client"));
		StringItem prop3 = new StringItem("javax.microedition.io.Connector.bluetooth.server", getPermission("javax.microedition.io.Connector.bluetooth.server"));
		StringItem prop4 = new StringItem("javax.microedition.io.Connector.obex.client", getPermission("javax.microedition.io.Connector.obex.client"));
		StringItem prop5 = new StringItem("javax.microedition.io.Connector.obex.client.tcp", getPermission("javax.microedition.io.Connector.obex.client.tcp"));
		StringItem prop6 = new StringItem("javax.microedition.io.Connector.obex.server", getPermission("javax.microedition.io.Connector.obex.server"));
		StringItem prop7 = new StringItem("javax.microedition.io.Connector.obex.server.tcp", getPermission("javax.microedition.io.Connector.obex.server.tcp"));
		
		
		form.append(prop1);
		form.append(prop2);
		form.append(prop3);
		form.append(prop4);
		form.append(prop5);
		form.append(prop6);
		form.append(prop7);
		
		
	}

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO Auto-generated method stub
		if(cmd == exit){
			exit();
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
	
	

}
