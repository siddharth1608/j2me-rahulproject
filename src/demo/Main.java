package demo;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;

public class Main extends MIDlet {

	private Form form;
	private Display display;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		form = new Form("Welcome");
		form.append("Welcome to demo app");
		display = Display.getDisplay(this);
		display.setCurrent(form);
	}

}
