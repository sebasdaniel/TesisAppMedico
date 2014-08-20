package com.tesis.medico.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.registros.RegistroDepartamento;
import com.tesis.medico.registros.RegistroMedico;
import com.tesis.medico.registros.RegistroMunicipio;
import com.tesis.medico.tareas.WSActualizarDatos;
import com.tesis.medico.tareas.WSCargarDatos;
import com.tesis.medico.tareas.WSObtenerDepartamentos2;
import com.tesis.medico.tareas.WSObtenerMunicipios2;

public class DatosActivity extends Activity {

	private EditText etEmail;
	private EditText etClave;
	private EditText etNombres;
	private EditText etApellidos;
	private EditText etCedula;
	
	private RadioButton rbMasculino;
	private RadioButton rbFemenino;
	private RadioButton rbOtros;
	
	private EditText etNumTp;
	private EditText etNacionalidad;
	private EditText etEspecialidad;
	private EditText etDireccion;
	private EditText etTelefono;
	private Spinner spinDepartamentos;
	private Spinner spinMunicipios;
	private Button btnEditar;
	private ArrayList<RegistroDepartamento> regDepartamento;
	private ArrayList<RegistroMunicipio> regMunicipios;
	
	private RegistroMedico medico;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos);
		
		etEmail = (EditText) findViewById(id.email);
		etClave = (EditText) findViewById(id.clave);
		etNombres = (EditText) findViewById(id.nombres);
		etApellidos = (EditText) findViewById(id.apellidos);
		etCedula = (EditText) findViewById(id.cedula);
		rbMasculino = (RadioButton) findViewById(id.masculino);
		rbFemenino = (RadioButton) findViewById(id.femenino);
		rbOtros = (RadioButton) findViewById(id.otros);
		etNumTp = (EditText) findViewById(id.numtp);
		etNacionalidad = (EditText) findViewById(id.nacionalidad);
		etEspecialidad = (EditText) findViewById(id.especialidad);
		etDireccion = (EditText) findViewById(id.direccion);
		etTelefono = (EditText) findViewById(id.telefono);
		spinDepartamentos = (Spinner) findViewById(id.departamento);
		spinMunicipios = (Spinner) findViewById(id.municipio);
		btnEditar = (Button) findViewById(id.btnEditar);
		
		btnEditar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				medico.setCorreo(etEmail.getText().toString());
				medico.setClave(etClave.getText().toString());
				medico.setNombres(etNombres.getText().toString());
				medico.setApellidos(etApellidos.getText().toString());
				medico.setCedula(etCedula.getText().toString());
				
				if(rbMasculino.isChecked()){
					medico.setSexo("Masculino");
				} else if(rbFemenino.isChecked()){
					medico.setSexo("Femenino");
				} else {
					medico.setSexo("Otro");
				}
				
				medico.setNumeroTP(etNumTp.getText().toString());
				medico.setNacionalidad(etNacionalidad.getText().toString());
				medico.setEspecializacion(etEspecialidad.getText().toString());
				medico.setDireccion(etDireccion.getText().toString());
				medico.setTelefono(etTelefono.getText().toString());
				medico.setMunicipio(regMunicipios.get(spinMunicipios.getSelectedItemPosition()).getId() + "");
				
				WSActualizarDatos actualizar = new WSActualizarDatos();
				actualizar.setParams(getApplicationContext());
				actualizar.execute(medico);
			}
		});
		
		WSObtenerDepartamentos2 od = new WSObtenerDepartamentos2();
		od.setParams(DatosActivity.this);
		od.execute();
		
		WSCargarDatos cargar = new WSCargarDatos();
		cargar.setParams(DatosActivity.this);
		cargar.execute();
		
	}
	
	public void cargarDepartamentos(ArrayList<RegistroDepartamento> departamentos){
		
		regDepartamento = departamentos;
		
		ArrayAdapter<RegistroDepartamento> adaptador = new ArrayAdapter<RegistroDepartamento>(this,
				android.R.layout.simple_spinner_item, regDepartamento);
		
		spinDepartamentos.setAdapter(adaptador);
		
		spinDepartamentos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int index, long id) {
				
				WSObtenerMunicipios2 om = new WSObtenerMunicipios2();
				om.setParams(DatosActivity.this);
				om.execute(regDepartamento.get(index).getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void cargarMunicipios(ArrayList<RegistroMunicipio> municipios){
		
		regMunicipios = municipios;
		
		ArrayAdapter<RegistroMunicipio> adaptador = new ArrayAdapter<RegistroMunicipio>(this,
				android.R.layout.simple_spinner_item, regMunicipios);
		
		spinMunicipios.setAdapter(adaptador);
		
		if(medico != null){
			
			spinMunicipios.setSelection(indiceMunicipio(medico.getMunicipio()));
		}
	}
	
	public void cargarDatos(RegistroMedico registro){
		
		medico = registro;
		
		etEmail.setText(medico.getCorreo());
		etClave.setText(medico.getClave());
		etNombres.setText(medico.getNombres());
		etApellidos.setText(medico.getApellidos());
		etCedula.setText(medico.getCedula());
		
		if(medico.getSexo().equalsIgnoreCase("masculino")){
			rbMasculino.setChecked(true);
		} else if (medico.getSexo().equalsIgnoreCase("femenino")) {
			rbFemenino.setChecked(true);
		} else {
			rbOtros.setSelected(true);
		}
		
		etNumTp.setText(medico.getNumeroTP());
		etNacionalidad.setText(medico.getNacionalidad());
		etEspecialidad.setText(medico.getEspecializacion());
		etDireccion.setText(medico.getDireccion());
		etTelefono.setText(medico.getTelefono());
		
		spinDepartamentos.setSelection(indiceDepartamento(medico.getDepartamento()));
		
		
		
	}
	
	private int indiceDepartamento(String id){
		
		int idDep = Integer.parseInt(id);
		int cont = 0;
		
		for(RegistroDepartamento depto : regDepartamento){
			
			if(depto.getId() == idDep){
				return cont;
			}
			cont++;
		}
		
		return 0;
	}
	
	private int indiceMunicipio(String id){
		
		int idMun = Integer.parseInt(id);
		int cont = 0;
		
		for(RegistroMunicipio mun : regMunicipios){
			
			if(mun.getId() == idMun){
				return cont;
			}
			cont++;
		}
		
		return 0;
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.datos, menu);
//		return true;
//	}

}
