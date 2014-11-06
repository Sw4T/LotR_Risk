package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThreadReception implements Runnable {

	private BufferedReader bufferLecture;
	
	public ThreadReception(InputStream is) {
		this.bufferLecture = new BufferedReader(new InputStreamReader(is));
	}


	@Override
	public void run() {
	
			System.out.println(afficherMessage());
		
	}
	
	public String afficherMessage() 
	{
		try {
			return (this.bufferLecture.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Rien reçu";
	}
}
