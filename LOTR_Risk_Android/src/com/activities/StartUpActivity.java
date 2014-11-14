package com.activities;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lotr_risk.R;
import com.net.DonneesConnexion;
import com.net.TacheConnexion;

public class StartUpActivity extends Activity {

	Dialog dialog;
	Button BT_Connexion;
	EditText ET_addrServ, ET_numPort;
	private DonneesConnexion connexion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_up);
		BT_Connexion = (Button) findViewById(R.id.BT_Connexion);
		ET_addrServ = (EditText) findViewById(R.id.ET_addrServeur);
		ET_numPort = (EditText) findViewById(R.id.ET_portServeur);
		BT_Connexion.setOnClickListener(loggerListener);
	}
	
	public void connexion_Serveur() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String addresse = ET_addrServ.getText().toString();
				String numPort = ET_numPort.getText().toString();
				Toast.makeText(StartUpActivity.this, addresse + " / " + numPort, Toast.LENGTH_SHORT).show();
			}
		});
		String addresse = ET_addrServ.getText().toString();
		String numPort = ET_numPort.getText().toString();
		AsyncTask<String, Void, DonneesConnexion> t = new TacheConnexion(getBaseContext()).execute(addresse, numPort);
		try {
			this.connexion = t.get();
		} catch (InterruptedException e) {e.printStackTrace();
		} catch (ExecutionException e) {e.printStackTrace();}
	}
	
	private OnClickListener loggerListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			connexion_Serveur();	
		}		
	};
}
