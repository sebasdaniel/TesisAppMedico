package com.tesis.medico.tareas;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;
import com.tesis.medico.gui.SolicitudesActivity;
import android.os.AsyncTask;

public class WSAprobarSolicitud extends AsyncTask<String, Void, Void> {

	private SolicitudesActivity mActivity;
	private String respuesta;
	
	public void setParams(SolicitudesActivity activity){
		mActivity = activity;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		
		SessionManagement session = new SessionManagement(mActivity);
		UrlServer servidor = new UrlServer(mActivity);
		String url = servidor.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "aprobarSolicitud";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/aprobarSolicitudRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		request.addProperty("tipoId", params[0]);
		request.addProperty("numeroId", params[1]);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			
			respuesta = resultado_xml.toString();
			
		} catch (Exception e) {
			System.out.println("---se produjo una exception---");
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(respuesta != null && respuesta.equals("ok")){
			System.out.println("operacion exitosa");
		}
	}

}
