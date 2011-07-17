package br.com.jera.gasosa.gps;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;



public class Posicao implements LocationListener {

	protected static final String LOG_TAG = "Mensagem";
	
	public String estado;
	public String cidade;
	public Context context;
	public Location localizacao;
	
	public Posicao(Context context) {
		super();
		this.context = context;
	}

	public void pegaPosicao() {
		Location loc = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (loc != null) {
			// ponto = new Ponto(loc.getLatitude(), loc.getLongitude());
			localizacao = new Location(loc);
			Log.i(LOG_TAG, "Localização: " + loc.getLatitude() + ", " + loc.getLongitude());

			Geocoder gc = new Geocoder(context, Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				
				if (addresses.size() > 0) {
					Address address = addresses.get(0);

					cidade = address.getLocality().toString();
					
					estado = address.getAdminArea().toString();
					

				}
			} catch (Exception e) {
				Log.i(LOG_TAG, "Não foi possível encontrar localização." + e);
			}
		}

		getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	private LocationManager getLocationManager() {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

//		LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		return locationManager;
	}

	public void onLocationChanged(Location location) {
		pegaPosicao();
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
