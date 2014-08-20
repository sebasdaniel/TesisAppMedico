package com.tesis.medico.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.registros.RegistroSolicitudes;
import com.tesis.medico.tareas.WSAprobarSolicitud;
import com.tesis.medico.tareas.WSSolicitudes;

public class SolicitudesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitudes);
		
//		String datos[] = {"Fulanito de Tal\nFecha: 2013-3-4"};
//		
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
//		
//		ListView lvSolicitudes = (ListView) findViewById(id.lvSolicitudes);
//		
//		lvSolicitudes.setAdapter(adapter);
		
		WSSolicitudes solicitudes = new WSSolicitudes();
		solicitudes.setParams(SolicitudesActivity.this);
		solicitudes.execute();
		
	}
	
	public void cargarLista(final RegistroSolicitudes[] registros){
		
		String[] datos = new String[registros.length];
		
		for(int i=0; i<registros.length; i++){
			datos[i] = registros[i].getNombres() + " " + registros[i].getApellidos() + "\n"
					+ registros[i].getFecha();
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SolicitudesActivity.this,
				android.R.layout.simple_list_item_1, datos);
		
		ListView lvSolicitudes = (ListView) findViewById(id.lvSolicitudes);
		
		lvSolicitudes.setAdapter(adapter);
		
		lvSolicitudes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> padre, View v, final int index, long id) {
				
				new AlertDialog.Builder(SolicitudesActivity.this).setTitle("Confirmacion").setMessage("Desea aprobar" +
						" la solicitud de este paciente?").setPositiveButton("Si", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								
								WSAprobarSolicitud aprobacion = new WSAprobarSolicitud();
								aprobacion.setParams(SolicitudesActivity.this);
								aprobacion.execute(registros[index].getTipoId(), registros[index].getNumeroId());
								
							}
						}).setNegativeButton("No", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.cancel();
							}
						}).show();
				
			}
		});
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.solicitudes, menu);
//		return true;
//	}

}
