package br.com.jera.gasosa;

import java.util.List;

import br.com.jera.gasosa.db.DataHelper;
import br.com.jera.gasosa.db.Posto;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class GasosaSpinner extends Activity {
	
	private static DataHelper repositorio;
	private List<Posto> postos;
//	private String[] lista;
	private ArrayAdapter<CharSequence> adaptador;
	private Spinner combo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_spinner);
		
		combo = (Spinner) findViewById(R.id.sppostos);
		postos = repositorio.getDataHelper(this).listarPostos();

		final EditText gas = (EditText) findViewById(R.id.gasolina_price);
		final EditText eth = (EditText) findViewById(R.id.alcool_price);
		
		adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		
		combo.setAdapter(adaptador);
		adaptador.add("Selecione o posto");
		
		for (int i = 0; i < postos.size(); i++) {
//			CharSequence textHolder = "" + postos.get(i).nome;
			adaptador.add("" + postos.get(i).nome);
		}
		
		
		
		combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
				
				gas.setText(postos.get(posicao).vlgas);
				eth.setText(postos.get(posicao).vlalc);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
