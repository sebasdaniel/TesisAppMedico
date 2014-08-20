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
import com.tesis.medico.gui.PacientesActivity;
import com.tesis.medico.registros.RegistroPaceinte;

public class WSListaPacientes extends AsyncTask<Void, Void, Void> {

	private PacientesActivity mContext;
	private String resultado;
	private ArrayList<RegistroPaceinte> registro;
	
	public void setParams(PacientesActivity context){
		mContext = context;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		SessionManagement session = new SessionManagement(mContext);
		UrlServer servidor = new UrlServer(mContext);
		String url = servidor.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL"; //cambiar la url
		final String METHOD_NAME = "listaPacientes";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/listaPacientesRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		request.addProperty("soloConsultorio", false);
		request.addProperty("cedulaMedico", 0);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			
			resultado = resultado_xml.toString();
			
			int inicio, fin;
			
			inicio = -1;
			fin = resultado.indexOf("\n");
			
			registro = new ArrayList<RegistroPaceinte>();
			
			while(fin != -1){
				
				String linea = resultado.substring(inicio + 1, fin);
				
				String[] datos = linea.split(";");
					
				RegistroPaceinte temp = new RegistroPaceinte();
				
				temp.setTipoId(datos[0]);
				temp.setNumId(datos[1]);
				temp.setNombres(datos[2]);
				temp.setApellidos(datos[3]);
				temp.setTelefono(datos[4]);
				temp.setCorreo(datos[5]);
				temp.setSexo(datos[6]);
				temp.setEdad(datos[7]);
				
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
		
		if(resultado != "" && !registro.isEmpty()){
			
			RegistroPaceinte[] reg = new RegistroPaceinte[registro.size()];
			
			registro.toArray(reg);
			
			mContext.cargarLista(reg);
		}
	}

}
