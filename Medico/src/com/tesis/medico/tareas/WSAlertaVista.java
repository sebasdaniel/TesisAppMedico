package com.tesis.medico.tareas;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;

public class WSAlertaVista extends AsyncTask<String, Void, Void> {

	private Context mContext;
//	private String resultado;
	
	public void setParams(Context context){
		mContext = context;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		
		SessionManagement session = new SessionManagement(mContext);
		UrlServer servidor = new UrlServer(mContext);
		String url = servidor.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "alertaVista";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/alertaVistaRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		request.addProperty("idAntecedente", Integer.parseInt(params[0]));
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			
			String resultado = resultado_xml.toString();
			
			System.out.println("Resultado: " + resultado);
			
		} catch (Exception e) {
//			resultado = "";
			e.printStackTrace();
		}
		
		return null;
	}

}
