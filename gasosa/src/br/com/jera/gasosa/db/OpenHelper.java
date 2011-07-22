package br.com.jera.gasosa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper{

	public OpenHelper(Context context) {
		super(context, DataHelper.getDatabaseName(), null, DataHelper.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DataHelper.LOG_TAG, "Creating database");
		db.execSQL(DataHelper.DROP_SQL);
		db.execSQL(DataHelper.CREATE_SQL);
		db.setVersion(DataHelper.DATABASE_VERSION);
		populateDatabase(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(DataHelper.LOG_TAG, "Upgrading database, this will drop tables and recreate.");
		db.execSQL(DataHelper.DROP_SQL);

		Log.i(DataHelper.LOG_TAG, "Creating database");
		db.execSQL(DataHelper.CREATE_SQL);
		db.setVersion(newVersion);
		populateDatabase(db);
	}

	private void populateDatabase(SQLiteDatabase db) {
		db.beginTransaction();
		
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 1' ,'Rua 20','Centro'			  ,'Campo Grande','MS','15/06/2011', '-20.4579', '-54.6090', 'R$ 2,333','R$ 1,9879','R$ 1,6500','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 2' ,'Rua 19','Carandá Bosque I'  ,'Campo Grande','MS','15/06/2011', '-20.4578', '-54.6091', 'R$ 2,334','R$ 1,9878','R$ 1,6501','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 3' ,'Rua 18','Jardim dos Estados','Campo Grande','MS','15/06/2011', '-20.4577', '-54.6092', 'R$ 2,335','R$ 1,9877','R$ 1,6502','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 4' ,'Rua 17','Monte Castelo'	  ,'Campo Grande','MS','15/06/2011', '-20.4576', '-54.6093', 'R$ 2,336','R$ 1,9876','R$ 1,6503','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 5' ,'Rua 16','Centro'			  ,'Campo Grande','MS','15/06/2011', '-20.4575', '-54.6094', 'R$ 2,337','R$ 1,9875','R$ 1,6504','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 6' ,'Rua 15','Jardim Petropolis' ,'Campo Grande','MS','15/06/2011', '-20.4574', '-54.6095', 'R$ 2,338','R$ 1,9874','R$ 1,6505','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 7' ,'Rua 14','Centro'			  ,'Campo Grande','MS','15/06/2011', '-20.4573', '-54.6096', 'R$ 2.339','R$ 1,9873','R$ 1,6506','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 8' ,'Rua 13','Carandá'			  ,'Campo Grande','MS','15/06/2011', '-20.4572', '-54.6097', 'R$ 2.340','R$ 1,9872','R$ 1,6507','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 9' ,'Rua 12','Centro'			  ,'Campo Grande','MS','15/06/2011', '-20.4571', '-54.6098', 'R$ 2.341','R$ 1,9871','R$ 1,6508','')");
		db.execSQL("INSERT INTO posto (nome, endereco, bairro, cidade, uf, data, latitude, longitude, vlgas, vlalc, vldie, vlgnv) VALUES ('Posto 10','Rua 11','Centro'			  ,'Campo Grande','MS','15/06/2011', '-20.4570', '-54.6099', 'R$ 2.342','R$ 1,9870','R$ 1,6509','')");
		
		db.setTransactionSuccessful();
		db.endTransaction();
	}

}
