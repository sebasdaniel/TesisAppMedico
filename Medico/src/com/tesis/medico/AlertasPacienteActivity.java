package com.tesis.medico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tesis.medico.R.id;
import com.tesis.medico.gui.DiagnosticoActivity;
import com.tesis.medico.registros.RegistroNotificaciones;
import com.tesis.medico.tareas.WSAlertasPaciente;

public class AlertasPacienteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alertas_paciente);
		
		Bundle bundle = getIntent().getExtras();
		
		WSAlertasPaciente tarea = new WSAlertasPaciente();
		tarea.setParams(AlertasPacienteActivity.this);
		tarea.execute(bundle.getString("tipoid"), bundle.getString("numeroid"));
	}
	
	public void cargarLista(final RegistroNotificaciones[] registro){
		
		String[] datos = new String[registro.length];
		
		for(int i=0; i< registro.length; i++){
			
			datos[i] = "Fecha: " + registro[i].getFecha() + " Hora:" + registro[i].getHora()
					+ "\nPrueba: " + registro[i].getTipoPrueba() + "\nValor: " + registro[i].getValor() + 
					" " + registro[i].getUnidades() + "\nEstado: " + registro[i].getEstado();
		}
		
		ListView lvNotificaciones = (ListView) findViewById(id.lvAlertasPaciente);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
		lvNotificaciones.setAdapter(adapter);
		
		lvNotificaciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> padre, View v, int index, long id) {
				
				Intent intent = new Intent(AlertasPacienteActivity.this, DiagnosticoActivity.class);
				
				intent.putExtra("idantecedente", registro[index].getIdAntecedente());
				intent.putExtra("nombre", registro[index].getNombres());
				intent.putExtra("lat", registro[index].getLat());
				intent.putExtra("lon", registro[index].getLon());
				
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alertas_paciente, menu);
		return true;
	}

}
