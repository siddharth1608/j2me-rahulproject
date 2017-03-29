package demo;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class BluetoothDemo extends MIDlet implements DiscoveryListener, CommandListener {

	private Display display;
	private Form form;
	private Command exit;
	private Command bluToothSearch;

	public BluetoothDemo() {
		// TODO Auto-generated constructor stub
		display = Display.getDisplay(this);
		
		//Form - Title
	    form = new Form("TrackMe");
	    
	    //Form - Exit Command
	    exit = new Command("Exit", Command.EXIT, 1);
	    form.addCommand(exit);
	    
	  //Form - Search Command
	    bluToothSearch = new Command("Bluetooth Search", Command.ITEM, 1);
	    form.addCommand(bluToothSearch);
	    
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
		
		 
	}

	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		// TODO Auto-generated method stub
		
	}

	public void inquiryCompleted(int discType) {
		// TODO Auto-generated method stub
		
		Alert obj=new Alert("Bluetooth Inquiry completed");
		obj.setString(""+discType);
		obj.setTimeout(Alert.FOREVER);
		
		display.setCurrent(obj);
		
	}

	public void serviceSearchCompleted(int transID, int respCode) {
		// TODO Auto-generated method stub
		
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		// TODO Auto-generated method stub
		
	}

	public void commandAction(Command cmd, Displayable arg1) {
		// TODO Auto-generated method stub
		if(cmd==exit){
			exit();
			
		}else if(cmd == bluToothSearch){
			try {
				LocalDevice localDevice = LocalDevice.getLocalDevice();
				
				DiscoveryAgent discoveryAgent = localDevice.getDiscoveryAgent();
				
				discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this);
				
				
				
			} catch (BluetoothStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

}
