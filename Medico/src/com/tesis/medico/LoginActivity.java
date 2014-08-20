package com.tesis.medico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tesis.medico.R.id;
import com.tesis.medico.gui.HomeActivity;
import com.tesis.medico.gui.RegisterActivity;
import com.tesis.medico.tareas.WSRecuperarCuenta;
import com.tesis.medico.tareas.WSValidarUsuario;

public class LoginActivity extends Activity {

	private TextView result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SessionManagement session = new SessionManagement(getApplicationContext());
		
		if(session.isLoggedIn()){
			iniciar();
		}
		
		Button botonIngreso = (Button) findViewById(R.id.buttonLogin);
		
		final EditText usuario = (EditText) findViewById(R.id.emailInput);
		
		final EditText pass = (EditText) findViewById(R.id.passwordInput);
		
		TextView registrarse = (TextView) findViewById(id.tvRegistrarse);
		TextView tvRecuperar = (TextView) findViewById(id.textRecuperarCuenta);
		
		result = (TextView) findViewById(id.textResult);
		
		botonIngreso.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String txtUsuario = usuario.getText().toString();
				String txtPass = pass.getText().toString();
				
				if(!txtUsuario.equals("") && !txtPass.equals("")){
					
					WSValidarUsuario validador = new WSValidarUsuario();
					validador.setParams(LoginActivity.this);
					validador.execute(txtUsuario, txtPass);
					
				} else {
					mostrarResultado("Por favor ingrese usuario y contraseña!");
				}
				
			}
		});
		
		tvRecuperar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				LayoutInflater li = LayoutInflater.from(LoginActivity.this);
				View recuperarCuenta = li.inflate(R.layout.prompt_recuperar_cuenta, null);
				
				final EditText etCorreo = (EditText) recuperarCuenta.findViewById(id.etCorreo);
				
				AlertDialog.Builder dialogoRecuperar = new AlertDialog.Builder(LoginActivity.this);
				
				dialogoRecuperar.setView(recuperarCuenta);
				
				dialogoRecuperar.setTitle("Recuperar Contraseña");
				
				dialogoRecuperar.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						String email = etCorreo.getText().toString();
						
						if(!email.isEmpty()){
							
							if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
								
								WSRecuperarCuenta rc = new WSRecuperarCuenta();
								rc.setParams(LoginActivity.this);
								rc.execute(etCorreo.getText().toString());
								
							} else {
								Toast toast = Toast.makeText(LoginActivity.this, "Correo no valido",
										Toast.LENGTH_LONG);
								toast.show();
							}
							
						} else {
							Toast toast = Toast.makeText(LoginActivity.this,
									"Debe proporcionar un correo electronico", Toast.LENGTH_LONG);
							toast.show();
						}
					}
				});
				
				AlertDialog dialog = dialogoRecuperar.create();
				dialog.show();
			}
		});
		
		registrarse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent registro = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(registro);
			}
		});
	}
	
	public void iniciar(){
		
		Intent ingreso = new Intent(LoginActivity.this, HomeActivity.class);
		ingreso.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(ingreso);
		
		finish();
	}
	
	public void mostrarResultado(String msg){
		result.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.action_server:
			Intent intent = new Intent(LoginActivity.this, ServidorActivity.class);
			startActivity(intent);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

}
