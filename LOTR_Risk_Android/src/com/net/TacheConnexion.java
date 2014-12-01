package com.net;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class TacheConnexion extends AsyncTask<String, Void, DonneesConnexion> {

	private Context context;
	
	public TacheConnexion(Context context) {
		this.context = context;
	}
	
	@Override
	protected DonneesConnexion doInBackground(String... params) {
		String servAddr = params[0];
		Integer port = Integer.parseInt(params[1]);
		DonneesConnexion connexion = new DonneesConnexion(servAddr, port);
		return (connexion);	
	}
	
	@Override
	protected void onPostExecute(DonneesConnexion result) {	
		if (result.get_Connexion_Reussi())
			Toast.makeText(this.context, "Connexion OK", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this.context, "Connexion FAILED", Toast.LENGTH_SHORT).show();
    }
}
