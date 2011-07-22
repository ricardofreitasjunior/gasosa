package br.com.jera.gasosa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import br.com.jera.gasosa.db.DataHelper;
import br.com.jera.gasosa.db.Posto;
import br.com.jera.gasosa.Calculator.Fuel;
//import br.com.jeramobstats.JeraAgent;

//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class Principal extends GasosaActivity {

	private EditText gasolinePriceText;
	private EditText etanolPriceText;
	private ImageView resultImage;
	private TextView resultGas;
	private TextView resultEtanol;
	private Button calcButton;
	private InputMethodManager imm;
	private Calculator calculator;
	private Animation fadeAnimation;

	String format;
	
	private static DataHelper repositorio;
	private List<Posto> postos;
//	private String[] lista;
	private ArrayAdapter<CharSequence> adaptador;
	private Spinner combo;
	private EditText gas;
	private EditText eth;
	private Posto posto;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		posicao.pegaPosicao();
		Log.i(LOG_TAG, "Posicao: [" + posicao.localizacao.getLatitude() + ", " + posicao.localizacao.getLongitude() + "], Cidade: " +posicao.cidade + ", Estado: " + posicao.estado);
		

		gas = (EditText) findViewById(R.id.gasolina_price);
		eth = (EditText) findViewById(R.id.alcool_price);
		
		combo = (Spinner) findViewById(R.id.sppostos);
		postos = repositorio.getDataHelper(this).listarPostos();

		
		adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		
		combo.setAdapter(adaptador);
		adaptador.add("Selecione o posto");
		
		for (int i = 0; i < postos.size(); i++) {
//			CharSequence textHolder = "" + postos.get(i).nome;
			Log.i(LOG_TAG, "Posto: " + postos.get(i).nome);
			adaptador.add("" + postos.get(i).nome);
		}
		
		combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
				
				gas.setText(postos.get(posicao).vlgas);
				eth.setText(postos.get(posicao).vlalc);
				
				posto.id = 2;//postos.get(posicao).id;
				posto.nome = postos.get(posicao).nome;
				posto.endereco = postos.get(posicao).endereco;
				
				SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
				posto.data = simpleFormat.format( new Date( System.currentTimeMillis() ));
				posto.bairro = postos.get(posicao).bairro;
				posto.cidade = postos.get(posicao).cidade;
				posto.uf = postos.get(posicao).uf;
				posto.latitude = postos.get(posicao).latitude;
				posto.longitude = postos.get(posicao).longitude;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		if (prefs.getBoolean("first_time", true)) {
			SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME,0).edit();
			editor.putBoolean("first_time", false);
			editor.commit();
			startActivity(new Intent(this, Config.class));
		} else {
			setContentView(R.layout.main);
			SplashDialog dialog = new SplashDialog(this);
			if (!dialog.isSplashed()) {
				dialog.show();
				dialog.setSplashed(true);
			}		
//			AdView adView = (AdView) this.findViewById(R.id.adView);
//			adView.loadAd(new AdRequest());
			calculator = new Calculator(getSharedPreferences(PREFS_NAME, 0));
			retrieveReferences();
			gasolinePriceText.addTextChangedListener(new MoneyTextWatcher(gasolinePriceText));
			etanolPriceText.addTextChangedListener(new MoneyTextWatcher(etanolPriceText));
			calcButton.setOnClickListener(this.new CalcHandler());
			
		}
	}

    @Override
    protected void onStart()
    {
        super.onStart();
//        JeraAgent.onStartSession(this, "QI4YUGV5K7FN7I42RPA1");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
//        JeraAgent.onEndSession(this);
    }

	private void retrieveReferences() {
		resultImage = (ImageView) findViewById(R.id.resultImage);
		calcButton = (Button) findViewById(R.id.calcButton);
		gasolinePriceText = (EditText) findViewById(R.id.gasolina_price);
		etanolPriceText = (EditText) findViewById(R.id.alcool_price);
		resultGas = (TextView) findViewById(R.id.resultGas);
		resultEtanol = (TextView) findViewById(R.id.resultEthanol);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
		format = getResources().getString(R.string.result);
	}

	private class CalcHandler implements OnClickListener {
		public void onClick(View view) {
			calculator.setEthanolPriceFromText(etanolPriceText.getText().toString());
			calculator.setGasolinePriceFromText(gasolinePriceText.getText().toString());
			if (calculator.getEthanolPrice() <= 0) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.etanol_required), Toast.LENGTH_SHORT).show();
				return;
			}
			if (calculator.getGasolinePrice() <= 0) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.gasoline_required), Toast.LENGTH_SHORT).show();
				return;
			}
			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("GASOLINE_PRICE", String.valueOf(calculator.getGasolinePrice()));
            parameters.put("ETHANOL_PRICE" , String.valueOf(calculator.getEthanolPrice()));
//			JeraAgent.logEvent("CALC_PRICE", parameters);
			
			Fuel fuel = calculator.evaluatePrice();
			if (fuel.equals(Fuel.GASOLINE)) {
				showResult(R.drawable.gas, resultGas, resultEtanol);
			} else {
				showResult(R.drawable.ethanol, resultEtanol, resultGas);
			}
			imm.hideSoftInputFromWindow(calcButton.getWindowToken(), 0);
			
			posto.vlalc = etanolPriceText.getText().toString();
			posto.vlgas = gasolinePriceText.getText().toString();
			repositorio.Atualizar(posto);

		}

	}

	private void showResult(int imageId, TextView show, TextView hide) {
		resultImage.setImageResource(imageId);
		show.setVisibility(View.VISIBLE);
		hide.setVisibility(View.INVISIBLE);

		show.setText(String.format(format, calculator.ratio()));
		show.startAnimation(fadeAnimation);

		resultImage.startAnimation(fadeAnimation);
		resultImage.setVisibility(View.VISIBLE);
	}
}
