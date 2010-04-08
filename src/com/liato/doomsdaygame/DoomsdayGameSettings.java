package com.liato.doomsdaygame;
import java.util.Calendar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

public class DoomsdayGameSettings extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        findViewById(R.id.btnSettingsCancel).setOnClickListener(this);
        findViewById(R.id.btnSettingsOk).setOnClickListener(this);
        SharedPreferences settings = getSharedPreferences("doomsdaygame", 0);
		DatePicker dtpFrom = (DatePicker)findViewById(R.id.dtpFrom);
		DatePicker dtpTo = (DatePicker)findViewById(R.id.dtpTo);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		dtpFrom.updateDate(settings.getInt("from_year",year), settings.getInt("from_month",0), settings.getInt("from_day",1));
		dtpTo.updateDate(settings.getInt("to_year",year+1), settings.getInt("to_month",0), settings.getInt("to_day",1));
    }
    public void onClick(View v) {
    	int vid = v.getId(); 
    	if (vid == R.id.btnSettingsOk) {
    		SharedPreferences settings = getSharedPreferences("doomsdaygame", 0);
    		DatePicker dtpFrom = (DatePicker)findViewById(R.id.dtpFrom);
    		DatePicker dtpTo = (DatePicker)findViewById(R.id.dtpTo);
    		SharedPreferences.Editor editor = settings.edit();
    		editor.putInt("from_year", dtpFrom.getYear());
    		editor.putInt("from_month", dtpFrom.getMonth());
    		editor.putInt("from_day", dtpFrom.getDayOfMonth());
    		editor.putInt("to_year", dtpTo.getYear());
    		editor.putInt("to_month", dtpTo.getMonth());
    		editor.putInt("to_day", dtpTo.getDayOfMonth());
    		editor.commit();
    	}
		this.finish();
    }
}