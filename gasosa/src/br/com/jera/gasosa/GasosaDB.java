package br.com.jera.gasosa;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import br.com.jera.gasosa.gps.Posicao;

public class GasosaDB extends ListActivity {

	protected static final String LOG_TAG = "Mensagem";
	
	public Posicao posicao = new Posicao(this);
	
	public String estado;
	public String cidade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		posicao.pegaPosicao();
		
		Log.i(LOG_TAG, "Posicao: [" + posicao.localizacao.getLatitude() + ", " + posicao.localizacao.getLongitude() + "], Cidade: " +posicao.cidade + ", Estado: " + posicao.estado);
		
//		pegaPosicao();
	}

//	private void pegaPosicao() {
//		Location loc = getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//		if (loc != null) {
//			// ponto = new Ponto(loc.getLatitude(), loc.getLongitude());
//			Log.i(LOG_TAG, "Localização: " + loc.getLatitude() + ", " + loc.getLongitude());
//
//			Geocoder gc = new Geocoder(this, Locale.getDefault());
//			try {
//				List<Address> addresses = gc.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
//				
//				if (addresses.size() > 0) {
//					Address address = addresses.get(0);
//
//					cidade = address.getLocality().toString();
//					estado = address.getAdminArea().toString();
//
//				}
//			} catch (Exception e) {
//				Log.i(LOG_TAG, "Não foi possível encontrar localização." + e);
//			}
//		}
//
//		getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//	}

//	private LocationManager getLocationManager() {
//		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		return locationManager;
//	}
//
//	public void onLocationChanged(Location location) {
//		pegaPosicao();
//	}
//
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//
//	}
}