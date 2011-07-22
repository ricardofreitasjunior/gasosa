package br.com.jera.gasosa.db;

import java.util.ArrayList;
import java.util.List;

import br.com.jera.gasosa.db.Posto.Postos;
import br.com.jera.gasosa.gps.Posicao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

public class DataHelper {

	public static final String TABLE_NAME = "posto";
	public static final int DATABASE_VERSION = 1;
	public static final String CREATE_SQL;
	public static final String DROP_SQL;
	static final String DATABASE_NAME = "GasosaDB.db";
	static final String DATABASE_TEST_NAME = "GasosaDB-test.db";
	private int RAIO_PROXIMIDADE = 3450;
//	public GeoPoint ponto;

	public String TipoCombustivel;
	public String KmlG;
	public String KmlA;
	public String KmlD;
    public String Raio;
	
	private Context context;
	public static SQLiteDatabase db;
	private static boolean testing;
	private static DataHelper dataHelperInstance;
	public static final String LOG_TAG = "Mensagem";

	static {
		StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
		sql.append(DataHelper.TABLE_NAME);
		sql.append(" (");
		sql.append("_id integer primary key autoincrement, ");
		sql.append("nome text, ");
		sql.append("endereco text, ");
		sql.append("bairro text, ");
		sql.append("cidade text, ");
		sql.append("uf text, ");
		sql.append("data text, ");
		sql.append("latitude text, ");
		sql.append("longitude text, ");
		sql.append("vlgas text not null, ");
		sql.append("vlalc text not null, ");
		sql.append("vldie text not null, ");
		sql.append("vlgnv text ");
		sql.append(")");

		CREATE_SQL = String.format(sql.toString(), TABLE_NAME);
		DROP_SQL = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
	}

	public static String getDatabaseName() {
		if (testing) {
			return DATABASE_TEST_NAME;
		} else {
			return DATABASE_NAME;
		}
	}

	public static DataHelper getDataHelper(Context context) {
		if (dataHelperInstance == null) {
			dataHelperInstance = new DataHelper(context);
		}
		dataHelperInstance.context = context;
		return dataHelperInstance;
	}

	private DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();

	}

	public Cursor getCursor() {
		try {
			return db.query(TABLE_NAME, Posto.colunas, null, null, null, null, null, null);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Erro ao buscar postos: " + e.toString());
			return null;
		}
	}
	

	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}
	
	private void pegaConfiguracao() {
	    	SharedPreferences conf = PreferenceManager.getDefaultSharedPreferences(context);
	    	
	    	TipoCombustivel = conf.getString("tipocomb", "-1");
	    	KmlG= conf.getString("kmlg", "Não informado");
	    	KmlA= conf.getString("kmla", "Não informado");
	    	KmlD= conf.getString("kmld", "Não informado");
	    	Raio = conf.getString("raio", "3000");

	}
	
	public long Salvar(Posto posto) {
		long id = posto.id;

		if (id != 0) {
			Atualizar(posto);
		} else {
//			id = Cadastrar(posto);
		}
		return id;
	}

	public int Atualizar(Posto posto) {
		ContentValues dados = new ContentValues();
		dados.put(Postos.NOME, posto.nome);
		dados.put(Postos.ENDERECO, posto.endereco);
		dados.put(Postos.BAIRRO, posto.bairro);
		dados.put(Postos.CIDADE, posto.cidade);
		dados.put(Postos.UF, posto.uf);
		dados.put(Postos.DATA, posto.data);
		dados.put(Postos.LATITUDE, posto.latitude);
		dados.put(Postos.LONGITUDE, posto.longitude);
		dados.put(Postos.VLGASOLINA, posto.vlgas);
		dados.put(Postos.VLALCOOL, posto.vlalc);
//		dados.put(Postos.VLDIESEL, posto.vldie);
//		dados.put(Postos.VLGNV, posto.vlgnv);

		String _id = String.valueOf(posto.id);
		String where = Postos._ID + "=?";
		String[] whereArgs = new String[] { _id };
		int count = Alterar(dados, where, whereArgs);
		return count;
	}

	public int Alterar(ContentValues dados, String where, String[] whereArgs) {
		int count = db.update(TABLE_NAME, dados, where, whereArgs);
		Log.i(LOG_TAG, "Atualizou [" + count + "] registros.");
		return count;
	}

	public Posto buscarPosto(long id) {
		Cursor c = db.query(true, TABLE_NAME, Posto.colunas, Postos._ID + "=" + id, null, null, null, null, null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			Posto posto = new Posto();
			posto.id = c.getLong(0);
			posto.nome = c.getString(1);
			posto.endereco = c.getString(8);
			posto.bairro = c.getString(2);
			posto.cidade = c.getString(3);
			posto.uf = c.getString(4);
			posto.data = c.getString(5);
			posto.latitude = c.getString(6);
			posto.longitude = c.getString(7);
			posto.vlgas = c.getString(9);
			posto.vlalc = c.getString(10);
			posto.vldie = c.getString(11);
			posto.vlgnv = c.getString(12);

			return posto;
		}
		return null;
	}

	public List<Posto> listarSelecionados(String itens) {
		List<Posto> postos = new ArrayList<Posto>();

		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id IN(" + itens + ")", null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					Posto posto = new Posto();

					posto.id = c.getLong(0);
					posto.nome = c.getString(1);
					posto.endereco = c.getString(2);
					posto.bairro = c.getString(3);
					posto.cidade = c.getString(4);
					posto.uf = c.getString(5);
					posto.data = c.getString(6);
					posto.latitude = c.getString(7);
					posto.longitude = c.getString(8);
					posto.vlgas = c.getString(9);
					posto.vlalc = c.getString(10);
					posto.vldie = c.getString(11);
					posto.vlgnv = c.getString(12);

					postos.add(posto);

					Log.i(LOG_TAG, "Posto [" + posto.id + "][" + posto.nome + "].");
				} while (c.moveToNext());
			}
		}

		return postos;
	}
	
	public List<Posto> listarPostos() {
		Cursor c = getCursor();
		List<Posto> postos = new ArrayList<Posto>();
		
		if (c.moveToFirst()) {
			int idxId = c.getColumnIndex(Postos._ID);
			int idxNome = c.getColumnIndex(Postos.NOME);
			int idxEndereco = c.getColumnIndex(Postos.ENDERECO);
			int idxBairro = c.getColumnIndex(Postos.BAIRRO);
			int idxCidade = c.getColumnIndex(Postos.CIDADE);
			int idxUf = c.getColumnIndex(Postos.UF);
			int idxData = c.getColumnIndex(Postos.DATA);
			int idxLatitude = c.getColumnIndex(Postos.LATITUDE);
			int idxLongitude = c.getColumnIndex(Postos.LONGITUDE);
			int idxVlGas = c.getColumnIndex(Postos.VLGASOLINA);
			int idxVlAlc = c.getColumnIndex(Postos.VLALCOOL);
			int idxVlDie = c.getColumnIndex(Postos.VLDIESEL);
			int idxVlGnv = c.getColumnIndex(Postos.VLGNV);

			do {
				Posto posto = new Posto();

				posto.id = c.getLong(idxId);
				posto.nome = c.getString(idxNome);
				posto.endereco = c.getString(idxEndereco);
				posto.bairro = c.getString(idxBairro);
				posto.cidade = c.getString(idxCidade);
				posto.uf = c.getString(idxUf);
				posto.data = c.getString(idxData);
				posto.latitude = c.getString(idxLatitude);
				posto.longitude = c.getString(idxLongitude);
				posto.vlgas = c.getString(idxVlGas);
				posto.vlalc = c.getString(idxVlAlc);
				posto.vldie = c.getString(idxVlDie);
				posto.vlgnv = c.getString(idxVlGnv);

//				double lat = Double.parseDouble(posto.latitude.toString());
//				double lon = Double.parseDouble(posto.longitude.toString());

				postos.add(posto);
//				Log.i(LOG_TAG, "Posto: " + posto.nome);

			} while (c.moveToNext());
		}
		return postos;
	}

	public List<Posto> listarPostosProximos(Posicao posicao) {
		Cursor c = getCursor();
//		this.ponto = ponto;
		List<Posto> postos = new ArrayList<Posto>();
		
		if (c.moveToFirst()) {
			int idxId = c.getColumnIndex(Postos._ID);
			int idxNome = c.getColumnIndex(Postos.NOME);
			int idxEndereco = c.getColumnIndex(Postos.ENDERECO);
			int idxBairro = c.getColumnIndex(Postos.BAIRRO);
			int idxCidade = c.getColumnIndex(Postos.CIDADE);
			int idxUf = c.getColumnIndex(Postos.UF);
			int idxData = c.getColumnIndex(Postos.DATA);
			int idxLatitude = c.getColumnIndex(Postos.LATITUDE);
			int idxLongitude = c.getColumnIndex(Postos.LONGITUDE);
			int idxVlGas = c.getColumnIndex(Postos.VLGASOLINA);
			int idxVlAlc = c.getColumnIndex(Postos.VLALCOOL);
			int idxVlDie = c.getColumnIndex(Postos.VLDIESEL);
			int idxVlGnv = c.getColumnIndex(Postos.VLGNV);

			do {
				Posto posto = new Posto();

				posto.id = c.getLong(idxId);
				posto.nome = c.getString(idxNome);
				posto.endereco = c.getString(idxEndereco);
				posto.bairro = c.getString(idxBairro);
				posto.cidade = c.getString(idxCidade);
				posto.uf = c.getString(idxUf);
				posto.data = c.getString(idxData);
				posto.latitude = c.getString(idxLatitude);
				posto.longitude = c.getString(idxLongitude);
				posto.vlgas = c.getString(idxVlGas);
				posto.vlalc = c.getString(idxVlAlc);
				posto.vldie = c.getString(idxVlDie);
				posto.vlgnv = c.getString(idxVlGnv);

				double lat = Double.parseDouble(posto.latitude.toString());
				double lon = Double.parseDouble(posto.longitude.toString());

				Location bLocation = new Location("reverseGeocoded");
				bLocation.setLatitude(lat); // Value = 3.294391E7
				bLocation.setLongitude(lon); // Value = -9.6564615E7
				Location aLocation = new Location("reverseGeocoded");
				
				aLocation.setLatitude(posicao.localizacao.getLatitude());
				aLocation.setLatitude(posicao.localizacao.getLongitude());
//				aLocation.setLatitude(ponto.getLatitudeE6() / 1E6); 
//				aLocation.setLongitude(ponto.getLongitudeE6() / 1E6); 
				
//				aLocation.set(aLocation); // Don't think I need this
//				bLocation.set(bLocation); // Don't think I need this either

				int distancia = (int) aLocation.distanceTo(bLocation);

//				pegaConfiguracao();
//				
//				if (Raio != ""){
//					RAIO_PROXIMIDADE = Integer.valueOf(Raio)*1000;	
//				}
				
				if (distancia <= RAIO_PROXIMIDADE) {
					postos.add(posto);
				}

			} while (c.moveToNext());
		}
		return postos;
	}
	

	public List<Posto> buscarPostoPorBairro(String bairro) {
		List<Posto> postos = new ArrayList<Posto>();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE bairro like '%" + bairro + "%'", null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					Posto posto = new Posto();

					posto.id = c.getLong(0);
					posto.nome = c.getString(1);
					posto.endereco = c.getString(2);
					posto.bairro = c.getString(3);
					posto.cidade = c.getString(4);
					posto.uf = c.getString(5);
					posto.data = c.getString(6);
					posto.latitude = c.getString(7);
					posto.longitude = c.getString(8);
					posto.vlgas = c.getString(9);
					posto.vlalc = c.getString(10);
					posto.vldie = c.getString(11);
					posto.vlgnv = c.getString(12);

//					double lat = Double.parseDouble(posto.latitude.toString());
//					double lon = Double.parseDouble(posto.longitude.toString());

					postos.add(posto);
//					Log.i(LOG_TAG, "Posto: " + posto.nome);
//
//					Log.i(LOG_TAG, "Posto [" + posto.id + "][" + posto.nome + "].");
				} while (c.moveToNext());
			}
		}
		return postos;
	}
	
	public void Fechar() {
		 if(db != null){
		 db.close();
		 }
	}

}
