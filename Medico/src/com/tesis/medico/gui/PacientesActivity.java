package com.tesis.medico.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.registros.RegistroPaceinte;
import com.tesis.medico.tareas.WSListaPacientes;

public class PacientesActivity extends Activity {

	private ListView lvPacientes;
	private EditText etBuscarPaciente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pacientes);
		
		lvPacientes = (ListView) findViewById(id.lvBuscarPaciente);
		etBuscarPaciente = (EditText) findViewById(id.etBuscarPaciente);
		
		WSListaPacientes tarea = new WSListaPacientes();
		tarea.setParams(PacientesActivity.this);
		tarea.execute();
	}
	
	public void cargarLista(final RegistroPaceinte[] registros){
		
//		String datos[] = new String[registros.length];
//		
//		for(int i=0; i<registros.length; i++){
//			datos[i] = registros[i].getNombres() + " " + registros[i].getApellidos();
//		}
		
//		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
		final ArrayAdapter<RegistroPaceinte> adapter = new ArrayAdapter<RegistroPaceinte>(this,
				android.R.layout.simple_list_item_1, registros);
		
//		ListView lvPacientes = (ListView) findViewById(id.lvBuscarPaciente);
		
		lvPacientes.setAdapter(adapter);
		
		lvPacientes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> padre, View v, int index, long id) {
				
				Intent intent = new Intent(PacientesActivity.this, DatosPacienteActivity.class);
				
//				intent.putExtra("tipoid", registros[index].getTipoId());
//				intent.putExtra("id", registros[index].getNumId());
//				intent.putExtra("nombres", registros[index].getNombres() + " " + registros[index].getApellidos());
//				intent.putExtra("sexo", registros[index].getSexo());
//				intent.putExtra("telefono", registros[index].getTelefono());
				
				intent.putExtra("tipoid", adapter.getItem(index).getTipoId());
				intent.putExtra("id", adapter.getItem(index).getNumId());
				intent.putExtra("nombres", adapter.getItem(index).toString());
				intent.putExtra("sexo", adapter.getItem(index).getSexo());
				intent.putExtra("telefono", adapter.getItem(index).getTelefono());
				
				startActivity(intent);
			}
		});
		
		etBuscarPaciente.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
				
				adapter.getFilter().filter(cs);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.pacientes, menu);
//		return true;
//	}

}
