package com.tesis.medico.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tesis.medico.R;
import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;

@SuppressWarnings("deprecation")
public class HomeActivity extends TabActivity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
	private static final String PROPERTY_USER = "user";
	private static final long EXPIRATION_TIME_MS = 1000 * 3600 * 24;
	
	static final String TAG = "GCMDemoMedico";
	
	String SENDER_ID = "137850652350";
	GoogleCloudMessaging gcm;
	
	private String regid;
	
	private SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		session = new SessionManagement(HomeActivity.this);
		
		ActionBar aBar = getActionBar();
		aBar.setTitle(session.getName());
		
		Resources res = getResources();
		TabHost tabs = getTabHost();
		
		Intent intentHome = new Intent().setClass(this, NotificacionesActivity.class);
		TabSpec tabHome = tabs.newTabSpec("Home").setIndicator("", res.getDrawable(R.drawable.ic_action_home)).setContent(intentHome);
		
		Intent intentPacientes = new Intent().setClass(this, PacientesActivity.class);
		TabSpec tabPacientes = tabs.newTabSpec("Pacientes").setIndicator("", res.getDrawable(R.drawable.ic_action_pacientes)).setContent(intentPacientes);
		
		Intent intentSolicitudes = new Intent().setClass(this, SolicitudesActivity.class);
		TabSpec tabSolicitudes = tabs.newTabSpec("Solicitudes").setIndicator("", res.getDrawable(R.drawable.ic_action_solicitudes)).setContent(intentSolicitudes);
		
//		Intent intentDatos = new Intent().setClass(this, DatosActivity.class);
//		TabSpec tabDatos = tabs.newTabSpec("Datos").setIndicator("", res.getDrawable(R.drawable.ic_action_datos)).setContent(intentDatos);
		
		tabs.addTab(tabHome);
		tabs.addTab(tabPacientes);
		tabs.addTab(tabSolicitudes);
//		tabs.addTab(tabDatos);
		
		tabs.setCurrentTab(0);
		
		verificarGCM();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.action_logout:
			session.logout();
			finish();
			return true;
		case R.id.action_config:
			Intent intent = new Intent(HomeActivity.this, DatosActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}
	
	private void verificarGCM(){
		
        if(checkPlayServices()){
        	
            gcm = GoogleCloudMessaging.getInstance(HomeActivity.this);
            
            regid = getRegistrationId(getApplicationContext());
            
            if (regid.equals("")) {
                RegistroGCM tarea = new RegistroGCM();
                tarea.execute(session.getEmail());
            }
            
        } else {
        	Log.i(TAG, "No se ha encontrado Google Play Services.");
        }
	}
	
	private boolean checkPlayServices() {
		
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if (resultCode != ConnectionResult.SUCCESS) {
			
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				
				System.out.println("Dispositivo no soportado.");
				finish();
			}
			return false;
		}
		
		return true;
	}

	private String getRegistrationId(Context context) {
		
		SharedPreferences prefs = getSharedPreferences("GcmPreferencesMedico", Context.MODE_PRIVATE);

		String registrationId = prefs.getString(PROPERTY_REG_ID, "");

		if (registrationId.length() == 0) {
			
			System.out.println("Registro GCM no encontrado.");
			return "";
		}
		
		String registeredUser = prefs.getString(PROPERTY_USER, "user");

		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);

		long expirationTime = prefs.getLong(PROPERTY_EXPIRATION_TIME, -1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
		
		String expirationDate = sdf.format(new Date(expirationTime));
		
		System.out.println("Registro GCM encontrado (usuario=" + registeredUser + ", version="
				+ registeredVersion + ", expira=" + expirationDate + ")");

		int currentVersion = getAppVersion(context);

		if (registeredVersion != currentVersion) {
			
			System.out.println("Nueva versión de la aplicación.");
			return "";
			
		} else if (System.currentTimeMillis() > expirationTime) {
			
			System.out.println("Registro GCM expirado.");
			return "";
			
		} else if (!session.getEmail().equals(registeredUser)) {
			
			System.out.println("Nuevo nombre de usuario.");
			return "";
		}

		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

			return packageInfo.versionCode;
			
		} catch (NameNotFoundException e) {
			throw new RuntimeException("Error al obtener versión: " + e);
		}
	}
	
	private class RegistroGCM extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			
			String msg = "";

			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(HomeActivity.this);
				}
				
				regid = gcm.register(SENDER_ID);
				
				System.out.println("Registrado en GCM: registration_id=" + regid);
				
				boolean registrado = registroServidor(params[0], regid);
				
				if (registrado) {
					setRegistrationId(HomeActivity.this, params[0], regid);
				}
				
			} catch (IOException ex) {
				System.out.println("Error registro en GCM:" + ex.getMessage());
			}

			return msg;
	        
		}

	}
	
	private boolean registroServidor(String usuario, String regId) {
		
		boolean reg = false;
		
		UrlServer config = new UrlServer(HomeActivity.this);
		String url = config.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "registrarGCM";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/registrarGCMRequest";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("correo", usuario);
		request.addProperty("clave", session.getPass());
		request.addProperty("regGCM", regId);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);

		try {
			transporte.call(SOAP_ACTION, envelope);
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			String res = resultado_xml.toString();

			if (res.equals("ok")) {
				
				System.out.println("Registrado en mi servidor.");
				reg = true;
			}
			
			System.out.println("salida servidor: " + res);
			
		} catch (Exception e) {
			System.out.println("Error registro en mi servidor: " + e.getCause() + " || " + e.getMessage());
		}

		return reg;
	}
	
	private void setRegistrationId(Context context, String user, String regId) {
		
		SharedPreferences prefs = getSharedPreferences("GcmPreferencesMedico", Context.MODE_PRIVATE);
		
		int appVersion = getAppVersion(context);
		
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(PROPERTY_USER, user);
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.putLong(PROPERTY_EXPIRATION_TIME, System.currentTimeMillis() + EXPIRATION_TIME_MS);

		editor.commit();
	}

}
