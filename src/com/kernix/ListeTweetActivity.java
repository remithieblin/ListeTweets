package com.kernix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ListeTweetActivity extends Activity {
	
	private String nomCompte;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonOk = (Button) findViewById(R.id.button1);
        buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.nomCompte);
				nomCompte = editText.getText().toString();
				
				Intent myIntent = new Intent(v.getContext(), MyTwitterActivity.class);
				myIntent.putExtra("idCompte", nomCompte);
                startActivity(myIntent);
				
			}
		});
    }
    
    
    
}