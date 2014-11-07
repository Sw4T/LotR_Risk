package net;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Emission {

	private PrintWriter printer;
	private ThreadEnvoiReception T_Reception;
	
	public Emission(OutputStream os, ThreadEnvoiReception T_Read) {
		this.printer = new PrintWriter(os, true);
		this.T_Reception = T_Read;
	}
	
	public void sendMultipleStrings(String[] tabStr)
	{
		for (int i = 0; i < tabStr.length; i++) {
			sendString(tabStr[i]);
		}
	}
	
	public void sendString(String message) {
		if (this.T_Reception.isSendMode() == true)
			this.printer.println(message);
	}
	
	public void sendInt(int toSend) {
		if (this.T_Reception.isSendMode() == true)
			this.printer.println(toSend);
	}

}
