package br.com.jera.gasosa;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import br.com.jera.gasosa.R;
import br.com.jera.gasosa.db.DataHelper;
import br.com.jera.gasosa.db.Posto;
import br.com.jera.gasosa.gps.Posicao;
import br.com.jeramobstats.JeraAgent;

import com.xtify.android.sdk.PersistentLocationManager;

public class GasosaActivity extends Activity {

	public static final String PREFS_NAME = "br.com.jera.gasosa.Config";

	private PersistentLocationManager persistentLocationManager;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// xtify-specific code
		Context context = this;
		persistentLocationManager = new PersistentLocationManager(context);
		Thread xtifyThread = new Thread(new Runnable() {
			public void run() {
				persistentLocationManager.setNotificationIcon(R.drawable.notification);
				persistentLocationManager.setNotificationDetailsIcon(R.drawable.icon);
				
				boolean trackLocation = persistentLocationManager.isTrackingLocation();
				boolean deliverNotifications = persistentLocationManager.isDeliveringNotifications();
				
				if (trackLocation || deliverNotifications) {
					persistentLocationManager.startService();
				}
			}
		});
		
		xtifyThread.start(); // to avoid Android's application-not-responding
								// dialog box,
								// do non-essential work in another thread
	}

	@Override
	protected void onStart() {
		super.onStart();
//		JeraAgent.onStartSession(this, "QI4YUGV5K7FN7I42RPA1");
	}

	@Override
	protected void onStop() {
		super.onStop();
//		JeraAgent.onEndSession(this);
	}

	
	
	class MoneyTextWatcher implements TextWatcher {
		EditText editText;

		public MoneyTextWatcher(EditText editText) {
			this.editText = editText;
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

//			if (!s.toString().matches(
//					"^(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
//				String userInput = "" + s.toString().replaceAll("[^\\d]", "");
//				StringBuilder cashAmountBuilder = new StringBuilder(userInput);
//
//				while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
//					cashAmountBuilder.deleteCharAt(0);
//				}
//				while (cashAmountBuilder.length() < 3) {
//					cashAmountBuilder.insert(0, '0');
//				}
//				cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
//
//				editText.setText(cashAmountBuilder.toString());
//				editText.setTextKeepState(cashAmountBuilder.toString());
//				Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length());
//			}
		}
	}
}
