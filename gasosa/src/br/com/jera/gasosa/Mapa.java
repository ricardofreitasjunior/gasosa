package br.com.jera.gasosa;

import br.com.jera.gasosa.gps.ImagemOverlay;
import br.com.jera.gasosa.gps.Ponto;
import br.com.jera.gasosa.gps.Posicao;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class Mapa extends MapActivity {

	private MapView mapa;
	private MapController controlador;
	private Posicao posicao = new Posicao(this);
	private ImagemOverlay imagem;
	private GeoPoint ponto;
	
	private static final String LOG_TAG = "Mensagem";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		mapa = (MapView) findViewById(R.id.mapa);
		mapa.setClickable(true);
		controlador = mapa.getController();
		mapa.setBuiltInZoomControls(true);
		controlador.setZoom(18);
		imagem = new ImagemOverlay(ponto, R.drawable.ponto);
		mapa.getOverlays().add(imagem);

		posicao.pegaPosicao();
		
		ponto = new Ponto(posicao.localizacao);
		imagem.setGeoPoint(ponto);
		controlador.setCenter(ponto);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
