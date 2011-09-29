package br.com.jera.gasosa.db;

import java.util.List;

import br.com.jera.gasosa.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ListAdapter extends ArrayAdapter<Posto> {

	private Context context;
	private List<Posto> lista;
	public int itens[];

	public int[] getItens() {
		return itens;
	}

	public void setItens(int[] itens) {
		this.itens = itens;
	}

	public ListAdapter(Context context, List<Posto> lista) {
		super(context, R.layout.form_lista, 0, lista);
		this.context = context;
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Posto getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Posto posto = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.form_lista, null);

		CheckBox id = (CheckBox) view.findViewById(R.id.id);
		id.setText(String.valueOf(posto.id));
		id.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton view, boolean arg1) {
				posto.selecionado = view.isChecked();
			}
		});

		TextView nome = (TextView) view.findViewById(R.id.nome);
		nome.setText("Nome: " + posto.nome);

		TextView endereco = (TextView) view.findViewById(R.id.endereco);
		endereco.setText("Endereço: " + posto.endereco);

		TextView bairro = (TextView) view.findViewById(R.id.bairro);
		bairro.setText("Bairro: " + posto.bairro);

		TextView data = (TextView) view.findViewById(R.id.data);
		data.setText("Data: " + posto.data);

		TextView vlgasolina = (TextView) view.findViewById(R.id.vlgas);
		vlgasolina.setText("G: " + posto.vlgas);

		TextView vlalcool = (TextView) view.findViewById(R.id.vlalc);
		vlalcool.setText("A: " + posto.vleta);

		return view;
	}

}
