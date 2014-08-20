package com.tesis.medico;

import com.tesis.medico.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ServidorActivity extends Activity {

	private UrlServer config;
	private EditText entrada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servidor);
		
		entrada = (EditText) findViewById(id.etUrl);
		Button btnGuardar = (Button) findViewById(id.btnGuardar);
		
		config = new UrlServer(ServidorActivity.this);
		
		if(config.getUrl() != null){
			entrada.setText(config.getUrl());
		}
		
		btnGuardar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(entrada.getText().toString() != ""){
					
					config.saveUrl(entrada.getText().toString());
					
					Toast toast = Toast.makeText(ServidorActivity.this, "URL guardada correctamente", Toast.LENGTH_LONG);
					toast.show();
					
				} else {
					Toast toast = Toast.makeText(ServidorActivity.this, "Debe ingresar una url", Toast.LENGTH_LONG);
					toast.show();
				}
				
				
			}
		});
	}

}
