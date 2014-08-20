package com.tesis.medico.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.tareas.WSAlertaVista;
import com.tesis.medico.tareas.WSDiagnosticar;

public class DiagnosticoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnostico);
		
		final Bundle bundle = getIntent().getExtras();
		
		WSAlertaVista tarea = new WSAlertaVista();
		tarea.setParams(getApplicationContext());
		tarea.execute(bundle.getString("idantecedente"));
		
		double lat = Double.parseDouble(bundle.getString("lat"));
		double lon = Double.parseDouble(bundle.getString("lon"));
		
		final EditText txtDiagnostico = (EditText) findViewById(id.etDiagnostico);
		Button btnEnviar = (Button) findViewById(id.btnEnviarDiagnostico);
		
		GoogleMap mapa = ((MapFragment)getFragmentManager().findFragmentById(id.map)).getMap();
//		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		LatLng localizacion = new LatLng(lat, lon);
		
//		CameraPosition camPos = new CameraPosition.Builder().target(localizacion).zoom(15).build();
		
		CameraUpdate camUpate = CameraUpdateFactory.newLatLngZoom(localizacion, 15);//newLatLng(localizacion);
		
		mapa.moveCamera(camUpate);
		mapa.addMarker(new MarkerOptions().position(localizacion).title(bundle.getString("nombre"))
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		btnEnviar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String diagnostico = txtDiagnostico.getText().toString();
				
				if(diagnostico.length() > 0){
					
					WSDiagnosticar diagnosticar = new WSDiagnosticar();
					diagnosticar.setParams(DiagnosticoActivity.this);
					diagnosticar.execute(bundle.getString("idantecedente"), txtDiagnostico.getText().toString());
				}
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.diagnostico, menu);
//		return true;
//	}

}
