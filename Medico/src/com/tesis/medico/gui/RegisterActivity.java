package com.tesis.medico.gui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.registros.RegistroDepartamento;
import com.tesis.medico.registros.RegistroMunicipio;
import com.tesis.medico.tareas.WSRegistrarMedico;
import com.tesis.medico.tareas.WSObtenerDepartamentos;
import com.tesis.medico.tareas.WSObtenerMunicipios;

public class RegisterActivity extends FragmentActivity /*implements DatePickerFragmentListener*/ {

	private String correo;
	private String clave;
	private String cedula;
	private String nombres;
	private String apellidos;
	private String sexo;
	private String numTp;
	private String nacionalidad;
	private String especializacion;
	private String direccion;
	private String telefono;
	private String municipio;
	
//	private String fecha;
	
	private ArrayList<RegistroMunicipio> regMunicipios;
	
	//private Button botonFecha;
	private Spinner listaDepartamento;
	private Spinner listaMunicipio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//botonFecha = (Button) findViewById(id.btbtnFechaNacimiento);
		Button botonRegistrar = (Button) findViewById(id.btnRegistrar);
		
		listaDepartamento = (Spinner) findViewById(id.spinnerDepartamentos);
		listaMunicipio = (Spinner) findViewById(id.spinnerMunicipios);
		
//		botonFecha.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				
//				DatePickerFragment newFragment = new DatePickerFragment();
//				newFragment.show(getSupportFragmentManager(), "datePicker");
//			}
//		});
		
		botonRegistrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				registrarUsuario();
			}
		});
		
		WSObtenerDepartamentos od = new WSObtenerDepartamentos();
		od.setParams(RegisterActivity.this);
		od.execute();
	}

//	@Override
//	public void onFinishDatePickerDialog(int year, int month, int day) {
//		
//		fecha = year + "-" + month + "-" + day;
//		botonFecha.setText(fecha);
//	}
	
	public void cargarDepartamentos(final ArrayList<RegistroDepartamento> departamentos){
		
		String datos[] = new String[departamentos.size()];
		
		for(int i=0; i<departamentos.size(); i++){
			
			datos[i] = departamentos.get(i).getNombre();
		}
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				datos);
		
		listaDepartamento.setAdapter(adaptador);
		
		listaDepartamento.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int index, long id) {
				
				WSObtenerMunicipios om = new WSObtenerMunicipios();
				om.setParams(RegisterActivity.this);
				om.execute(departamentos.get(index).getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void cargarMunicipios(ArrayList<RegistroMunicipio> municipios){
		
		regMunicipios = municipios;
		
		String datos[] = new String[municipios.size()];
		
		for(int i=0; i<municipios.size(); i++){
			
			datos[i] = municipios.get(i).getNombre();
		}
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				datos);
		
		listaMunicipio.setAdapter(adaptador);
	}
	
	private void registrarUsuario(){
		
		EditText etNombres = (EditText) findViewById(id.edtNombres);
		EditText etApellidos = (EditText) findViewById(id.edtApellidos);
		EditText etNumeroCedula = (EditText) findViewById(id.edtNumeroCedula);
		EditText etCorreo = (EditText) findViewById(id.edtCorreo);
		EditText etClave = (EditText) findViewById(id.edtClave);
		Spinner spinSexo = (Spinner) findViewById(id.spinnerTipoSexo);
		EditText etNumTp = (EditText) findViewById(id.edtNumTP);
		EditText etNacionalidad = (EditText) findViewById(id.edtNacionalidad);
		EditText etEspecializacion = (EditText) findViewById(id.edtEspecializacion);
		EditText etDireccion = (EditText) findViewById(id.edtDireccion);
		EditText etTelefono = (EditText) findViewById(id.edtTelefono);
		
		nombres = etNombres.getText().toString();
		apellidos = etApellidos.getText().toString();
		cedula = etNumeroCedula.getText().toString();
		correo = etCorreo.getText().toString();
		clave = etClave.getText().toString();
		sexo = (String) spinSexo.getSelectedItem();
		numTp = etNumTp.getText().toString();
		nacionalidad = etNacionalidad.getText().toString();
		especializacion = etEspecializacion.getText().toString();
		direccion = etDireccion.getText().toString();
		telefono = etTelefono.getText().toString();
		municipio = regMunicipios.get(listaMunicipio.getSelectedItemPosition()).getId() + "";
		
		if(validarDatos()){
			
			WSRegistrarMedico tarea = new WSRegistrarMedico();
			tarea.setParams(getApplicationContext());
			tarea.execute(correo, clave, cedula, nombres, apellidos, sexo, numTp, nacionalidad, especializacion,
					direccion, telefono, municipio);
			
		}
	}
	
	private boolean validarDatos(){
		
		if(correo == null || correo.isEmpty()){
			return false;
		}
		
		if(clave == null || clave.isEmpty()){
			return false;
		}
		
		if(cedula == null || cedula.isEmpty()){
			return false;
		}
		
		if(nombres == null || nombres.isEmpty()){
			return false;
		}
		
		if(apellidos == null || apellidos.isEmpty()){
			return false;
		}
		
		if(sexo == null || sexo.isEmpty()){
			return false;
		}
		
		if(numTp == null || numTp.isEmpty()){
			return false;
		}
		
		if(nacionalidad == null || nacionalidad.isEmpty()){
			return false;
		}
		
		if(especializacion == null || especializacion.isEmpty()){
			return false;
		}
		
		if(direccion == null || direccion.isEmpty()){
			return false;
		}
		
		if(telefono == null || telefono.isEmpty()){
			return false;
		}
		
		if(municipio == null || municipio.isEmpty()){
			return false;
		}
		
		return true;
		
	}

}
