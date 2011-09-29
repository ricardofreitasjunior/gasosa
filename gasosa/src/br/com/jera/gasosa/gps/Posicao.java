package br.com.jera.gasosa.gps;

import java.util.List;
import java.util.Locale;

import br.com.jera.gasosa.gps.Ponto;

import com.google.android.maps.GeoPoint;

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
	

	public Location localizacao;
	public String estado;
	public String cidade;
	public String bairro;
	public String endereco;
	public String cep; 
	
	public Context context;

	public String pais;

	public LocationManager locationManager;
		
	public Posicao(Context context) {
		super();
		this.context = context;		
	}

	public String pegaPosicao() {
		String mensagem = "Não foi possível encontrar localização do usuário.";
		
		localizacao = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (localizacao != null) {
			Log.i(LOG_TAG, "Localização: " + localizacao.getLatitude() + ", " + localizacao.getLongitude());

			Geocoder gc = new Geocoder(context, Locale.getDefault());
		
			try {
				List<Address> addresses = gc.getFromLocation(localizacao.getLatitude(), localizacao.getLongitude(), 1);
				
				if (addresses.size() > 0) {
					Address address = addresses.get(0);

//					String num = address.getFeatureName();
//					String rua = address.getThoroughfare();
//					bairro = address.getSubLocality();
					
					endereco = address.getAddressLine(0);
					cep = address.getPostalCode();
					pais = address.getCountryName();
					cidade = address.getLocality().toString();
					estado = address.getAdminArea().toString();
				}
			
				mensagem = "Sua posição é: " + "[" + endereco + "], [" + cidade + "], [" + estado + "]";
			} catch (Exception e) {
				mensagem = "Não foi possível encontrar localização do usuário." + e;
			}
		}
		getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		return mensagem;
	}

	private LocationManager getLocationManager() {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}

	public void onLocationChanged(Location location) {
//		pegaPosicao();
		localizacao = location;
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