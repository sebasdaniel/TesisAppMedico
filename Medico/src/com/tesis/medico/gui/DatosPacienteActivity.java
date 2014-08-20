package com.tesis.medico.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tesis.medico.AlertasPacienteActivity;
import com.tesis.medico.R;
import com.tesis.medico.R.id;

public class DatosPacienteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_paciente);
		
		final Bundle bundle = getIntent().getExtras();
		
		TextView tvNombres = (TextView) findViewById(id.tvNombres);
		TextView tvSexo = (TextView) findViewById(id.tvSexo);
		TextView tvTel = (TextView) findViewById(id.tvTelefono);
		
		Button btnDiagnostico = (Button) findViewById(id.btnDiagnostico);
		Button btnEstadisticas = (Button) findViewById(id.btnEstadisticas);
		Button btnHistorial = (Button) findViewById(id.btnHistorial);
		
		tvNombres.setText(bundle.getString("nombres"));
		tvSexo.setText("Sexo: " + bundle.getString("sexo"));
		tvTel.setText("Telefono: " + bundle.getString("telefono"));
		
		btnDiagnostico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				Intent intent = new Intent(DatosPacienteActivity.this, DiagnosticoActivity.class);
//				startActivity(intent);
				Intent intent =  new Intent(DatosPacienteActivity.this, AlertasPacienteActivity.class);
				intent.putExtra("tipoid", bundle.getString("tipoid"));
				intent.putExtra("numeroid", bundle.getString("id"));
				startActivity(intent);
			}
		});
		
		btnEstadisticas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(DatosPacienteActivity.this, ConfigEstadistica.class);
				
				intent.putExtra("tipoid", bundle.getString("tipoid"));
				intent.putExtra("numeroid", bundle.getString("id"));
				
				startActivity(intent);
			}
		});
		
		btnHistorial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(DatosPacienteActivity.this, HistorialActivity.class);
				
				intent.putExtra("tipoid", bundle.getString("tipoid"));
				intent.putExtra("numeroid", bundle.getString("id"));
				
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.datos_paciente, menu);
		return true;
	}

}
