package demo;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PlatformProperties extends MIDlet implements CommandListener{

	private Display display;
	private Form form;
	private Command exit;
	
	public PlatformProperties() {
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
		
		StringItem prop1 = new StringItem("Location API", System.getProperty("microedition.location.version"));
		StringItem prop2 = new StringItem("Microedition Platform", System.getProperty("microedition.platform"));
		StringItem prop3 = new StringItem("CLDC", System.getProperty("microedition.configuration"));
		StringItem prop4 = new StringItem("MIDP", System.getProperty("microedition.profiles"));
		StringItem prop5 = new StringItem("Locale", System.getProperty("microedition.locale"));
		StringItem prop6 = new StringItem("File Separator", System.getProperty("file.separator"));
		StringItem prop7 = new StringItem("Hostname", System.getProperty("microedition.hostname"));
		StringItem prop8 = new StringItem("Wireless messaging SMS", System.getProperty("wireless.messaging.sms.smsc"));
		StringItem prop9 = new StringItem("Wireless messaging MMS", System.getProperty("wireless.messaging.mms.mmsc"));
		StringItem prop10 = new StringItem("SIP", System.getProperty("microedition.sip.version"));
		StringItem prop11 = new StringItem("Smart card slots", System.getProperty("microedition.smartcardslots"));
		StringItem prop12 = new StringItem("CHAPI Version", System.getProperty("CHAPI-Version"));
		StringItem prop13 = new StringItem("CellId", getCellID());
		
		form.append(prop1);
		form.append(prop2);
		form.append(prop3);
		form.append(prop4);
		form.append(prop5);
		form.append(prop6);
		form.append(prop7);
		form.append(prop8);
		form.append(prop9);
		form.append(prop10);
		form.append(prop11);
		form.append(prop12);
		form.append(prop13);
		
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
	
	public String getCellID(){
		String out = "";
		
		try{
 
			out = System.getProperty("CELL-ID");
			if(out== null || out.equals("null") || out.equals(""))
				out = System.getProperty("CellID");
			if(out== null ||out.equals("null")|| out.equals(""))
				System.getProperty("phone.cid");
			//#if polish.Vendor == Nokia
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("com.nokia.mid.cid");
			//#elif polish.Vendor == Sony-Ericsson
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("com.sonyericsson.net.cellid");
			//#elif polish.Vendor == Motorola
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("phone.cid");
			//#elif polish.Vendor == Samsung
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("com.samsung.cellid");
			//#elif polish.Vendor == Siemens
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("com.siemens.cellid");
			//#elif polish.Vendor == BlackBerry
			if(out== null ||out.equals("null")|| out.equals(""))
				//#= out = GPRSInfo.getCellInfo().getCellId();
			//#else
			if(out== null ||out.equals("null")|| out.equals(""))
				out = System.getProperty("cid");
			//#endif
 
		}catch(Exception e){
			return out==null?"":out;
		}
 
		return out==null?"":out;
	}

}
