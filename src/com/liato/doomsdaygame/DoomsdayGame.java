package com.liato.doomsdaygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Random;

public class DoomsdayGame extends Activity implements OnClickListener {
	Calendar date, date_from, date_to;
	DateFormat formater;
	int answers_total;
	int answers_correct;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        findViewById(R.id.btnMon).setOnClickListener(this);
        findViewById(R.id.btnTue).setOnClickListener(this);
        findViewById(R.id.btnWed).setOnClickListener(this);
        findViewById(R.id.btnThu).setOnClickListener(this);
        findViewById(R.id.btnFri).setOnClickListener(this);
        findViewById(R.id.btnSat).setOnClickListener(this);
        findViewById(R.id.btnSun).setOnClickListener(this);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
        SharedPreferences settings = getSharedPreferences("doomsdaygame", 0);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        this.date_from = Calendar.getInstance();
        this.date_from.set(settings.getInt("from_year",year), settings.getInt("from_month",0), settings.getInt("from_day",1));
        this.date_to = Calendar.getInstance();
        this.date_to.set(settings.getInt("to_year",year+1), settings.getInt("to_month",0), settings.getInt("to_day",1));
        this.date = getRandomDate();

        TextView datetext = (TextView)findViewById(R.id.txtDate);
        this.formater = DateFormat.getDateInstance();
        datetext.setText(this.formater.format(this.date.getTime()));    	
    }

    //@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = new MenuInflater(this);
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }

    
    public boolean onOptionsItemSelected (MenuItem item){
    	switch (item.getItemId()) {
    	case R.id.exit:
    		this.finish();
    		return true;
    	case R.id.settings:
    		Intent settings = new Intent(this, DoomsdayGameSettings.class);
    		this.startActivity(settings);
    		return true;
    	case R.id.help:
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage("No help available yet!")
    		       .setCancelable(true)
    		       .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                dialog.cancel();
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
    		return true;
    	}
    	return false;
    }
    	

    
    public void onClick(View v) {
    	int day = Integer.parseInt((String)v.getTag());
    	TextView txtResult = (TextView)findViewById(R.id.txtResult);
    	TextView txtAnswers = (TextView)findViewById(R.id.txtAnswers);
    	this.answers_total += 1;
    	if (day == this.date.get(Calendar.DAY_OF_WEEK)) {
    		txtResult.setText(R.string.correct);
    		txtResult.setTextColor(getResources().getColor(R.color.correct));
        	this.answers_correct += 1;
    	}
    	else {
    		txtResult.setText(R.string.wrong);
    		txtResult.setTextColor(getResources().getColor(R.color.wrong));
    	}
    	txtResult.setVisibility(TextView.VISIBLE);
    	AlphaAnimation anim = new AlphaAnimation(1, 0);
    	anim.setFillAfter(true);
    	anim.setStartOffset(5000);
	    anim.setDuration(1000);
    	txtResult.startAnimation(anim);
    	txtAnswers.setText(Html.fromHtml(String.format(getResources().getString(R.string.answers), this.answers_correct, this.answers_total)));
    	txtAnswers.setTextColor(getResources().getColor(R.color.answers));
    	
    	TextView datetext = (TextView)findViewById(R.id.txtDate);
        this.date = getRandomDate();
        datetext.setText(this.formater.format(this.date.getTime()));
    }      
    
    public Calendar getRandomDate() {
        Calendar randdate = Calendar.getInstance();
        Random rand = new Random();
        int daydiff = rand.nextInt(Math.abs((int)((this.date_from.getTimeInMillis() - this.date_to.getTimeInMillis())/(1000*60*60*24))));
        randdate.setTimeInMillis(this.date_from.getTimeInMillis()+((long)daydiff*1000L*60L*60L*24L));
        return randdate;
    }
}

