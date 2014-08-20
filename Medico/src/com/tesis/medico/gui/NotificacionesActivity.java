package com.tesis.medico.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.registros.RegistroNotificaciones;
import com.tesis.medico.tareas.WSNotificaciones;

public class NotificacionesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificaciones);
		
		WSNotificaciones tarea = new WSNotificaciones();
		tarea.setParams(NotificacionesActivity.this);
		tarea.execute();
	}
	
	public void cargarLista(final RegistroNotificaciones[] registro){
		
		String[] datos = new String[registro.length];
		
		for(int i=0; i< registro.length; i++){
			
			datos[i] = registro[i].getNombres() + "\nFecha: " + registro[i].getFecha() + " Hora:" + registro[i].getHora()
					+ "\nPrueba: " + registro[i].getTipoPrueba() + "\nValor: " + registro[i].getValor() + 
					" " + registro[i].getUnidades() + "\nEstado: " + registro[i].getEstado();
		}
		
		ListView lvNotificaciones = (ListView) findViewById(id.lvNotificaciones);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
		lvNotificaciones.setAdapter(adapter);
		
		lvNotificaciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> padre, View v, int index, long id) {
				
				Intent intent = new Intent(NotificacionesActivity.this, DiagnosticoActivity.class);
				
				intent.putExtra("idantecedente", registro[index].getIdAntecedente());
				intent.putExtra("nombre", registro[index].getNombres());
				intent.putExtra("lat", registro[index].getLat());
				intent.putExtra("lon", registro[index].getLon());
				
				startActivity(intent);
			}
		});
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.notificaciones, menu);
//		return true;
//	}

}
