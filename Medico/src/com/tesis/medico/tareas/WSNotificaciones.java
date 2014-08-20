package com.tesis.medico.tareas;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;
import com.tesis.medico.gui.NotificacionesActivity;
import com.tesis.medico.registros.RegistroNotificaciones;

public class WSNotificaciones extends AsyncTask<Void, Void, Void> {

	private NotificacionesActivity mContext;
	private String resultado;
	private ArrayList<RegistroNotificaciones> registro = null;
	
	public void setParams(NotificacionesActivity context){
		mContext = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		SessionManagement session = new SessionManagement(mContext);
		UrlServer servidor = new UrlServer(mContext);
		String url = servidor.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		//final String URL = "http://192.168.43.214:8080/SIMOP/SIMOP?WSDL"; //cambiar la url
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "obtenerAlertasNoVistas";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/obtenerAlertasNoVistasRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			
			resultado = resultado_xml.toString();
			
//			if(resultado.length() == 0){
//				return null;
//			}
			
			int inicio, fin;
			
			inicio = -1;
			fin = resultado.indexOf("\n");
			
			registro = new ArrayList<RegistroNotificaciones>();
			
			while(fin != -1){
				
				String linea = resultado.substring(inicio + 1, fin);
				
				String[] datos = linea.split(";");
					
				RegistroNotificaciones temp = new RegistroNotificaciones();
				
				temp.setIdAntecedente(datos[0]);
				temp.setTipoId(datos[1]);
				temp.setNumId(datos[2]);
				temp.setNombres(datos[3]);
				temp.setFecha(datos[4]);
				temp.setHora(datos[5]);
				temp.setTipoPrueba(datos[6]);
				temp.setValor(datos[7]);
				temp.setUnidades(datos[8]);
				temp.setEstado(datos[9]);
				temp.setLat(datos[10]);
				temp.setLon(datos[11]);
				
				registro.add(temp);
				
				inicio = fin;
				fin = resultado.indexOf("\n", inicio + 1);
			}
			
		} catch (Exception e) {
			resultado = "";
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(registro != null && !registro.isEmpty()){
			
			RegistroNotificaciones[] notificaciones = new RegistroNotificaciones[registro.size()];
			
			registro.toArray(notificaciones);
			
			mContext.cargarLista(notificaciones);
		}
	}

}
