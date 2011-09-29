package br.com.jera.gasosa.gps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ImagemOverlay extends Overlay{

	private GeoPoint geoPoint;
	private Paint paint = new Paint();
	private final int imagemId;
	
	public ImagemOverlay(GeoPoint geoPoint, int resId){
		this.geoPoint = geoPoint;
		this.imagemId = resId;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow){
		super.draw(canvas, mapView, shadow);
		
		if(geoPoint != null){
			Point p = mapView.getProjection().toPixels(geoPoint, null);
			Bitmap btm = BitmapFactory.decodeResource(mapView.getResources(), this.imagemId);
			RectF r = new RectF(p.x, p.y, p.x+btm.getWidth(), p.y+btm.getHeight());
			canvas.drawBitmap(btm, null, r, paint);
		}
	}

	public void setGeoPoint(GeoPoint p) {
		this.geoPoint = p;
		
	}
	
	@Override
	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
		Point ponto = mapView.getProjection().toPixels(this.geoPoint, null);
		// Cria o ret�ngulo
		RectF recf = new RectF(ponto.x - 5, ponto.y - 5, ponto.x + 5, ponto.y + 5);
		// Converte para ponto em pixels
		Point newPoint = mapView.getProjection().toPixels(geoPoint, null);
		// Verifica se o ponto est� contido no ret�ngulo
		boolean ok = recf.contains(newPoint.x, newPoint.y);
		if (ok) {
			Toast.makeText(mapView.getContext(), "Coodernada da Overlay: " + geoPoint, Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onTap(geoPoint, mapView);
	}

}
