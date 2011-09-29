package br.com.jera.gasosa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jera.gasosa.Calculator.Fuel;
import br.com.jera.gasosa.db.DataHelper;
import br.com.jera.gasosa.db.Posto;
import br.com.jera.gasosa.gps.Posicao;
import br.com.jeramobstats.JeraAgent;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

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
	
	private Posicao posicao = new Posicao(this);
	public String estado;
	public String cidade;

	private ProgressDialog dialog;
	protected static final String LOG_TAG = "Mensagem";
	private String mensagem;

	private static DataHelper repositorio;
	private Posto posto;
	private List<Posto> postos;
//	private ArrayAdapter<CharSequence> adaptador;
	private CharSequence[] lista;
	private Button btnTodos;
	private Button btnProximos;
	private EditText gas;
	private EditText eth;
	private long id;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id2) {
		this.id = id2;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		dialog = ProgressDialog.show(this, "Gasosa 2.0", "Buscando posição do usuário...", false, true);

		pegaPosicao();

		mostraMensagem();
		
		preferencias();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.todos: {
			pegaPostos("todos");
			return true;
		}
		case R.id.proximos: {
			pegaPostos("proximos");
			return true;
		}
		case R.id.bairro: {
			
			return true;
		}
		case R.id.mapa: {
			startActivity(new Intent(this, Mapa.class));
			return true;
		}
		case R.id.config: {
			startActivity(new Intent(this, Config.class));
			return true;
		}
		case R.id.principal: {
			Intent intent = new Intent(this, Principal.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void pegaPostos(String busca) {
		if (busca == "todos"){
			postos = repositorio.getDataHelper(this).listarPostos();	
		} else if (busca == "proximos"){
			if (posicao.localizacao != null) {
				postos = repositorio.getDataHelper(this).listarPostosProximos(posicao.localizacao);
			}
		}
		
		lista = new CharSequence[postos.size()];
		
		for (int i = 0; i < postos.size(); i++) {
//			adaptador.add("" + postos.get(i).nome);
			lista[i] = postos.get(i).nome;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Selecione o posto");
		builder.setSingleChoiceItems(lista, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	gasolinePriceText.setText(postos.get(item).vlgas);
		    	etanolPriceText.setText(postos.get(item).vleta);
				setId(postos.get(item).id);
				Log.i(LOG_TAG, "ID do select: " + getId());
				dialog.dismiss();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void pegaPosicao() {
		mensagem = posicao.pegaPosicao();
		if (posicao.localizacao != null) {
			dialog.dismiss();
			Log.i(LOG_TAG, "Posição: " + mensagem);
//			posicao.locationManager.removeUpdates(posicao);
		} else {
			Log.i(LOG_TAG, mensagem);
			dialog.dismiss();
		}
	}
	
	private void mostraMensagem() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(mensagem).setCancelable(false)
				.setTitle("Mensagem de Posicionamento")
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void preferencias() {
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
			AdView adView = (AdView) this.findViewById(R.id.adView);
			adView.loadAd(new AdRequest());
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
        JeraAgent.onStartSession(this, "QI4YUGV5K7FN7I42RPA1");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        JeraAgent.onEndSession(this);
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
			JeraAgent.logEvent("CALC_PRICE", parameters);
//			
			Fuel fuel = calculator.evaluatePrice();
			if (fuel.equals(Fuel.GASOLINE)) {
				showResult(R.drawable.gas, resultGas, resultEtanol);
			} else {
				showResult(R.drawable.ethanol, resultEtanol, resultGas);
			}
//			
			imm.hideSoftInputFromWindow(calcButton.getWindowToken(), 0);
			
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
		
		atualiza();
	}
	
	private void atualiza() {
		Posto posto = new Posto();
		posto.id = getId();
		Log.i(LOG_TAG, "ID do posto: " + posto.id);
		posto.vleta = etanolPriceText.getText().toString();
		posto.vlgas = gasolinePriceText.getText().toString();
		repositorio.getDataHelper(Principal.this).Atualizar(posto);
	}
}
