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
import com.tesis.medico.gui.HistorialActivity;

import android.os.AsyncTask;

public class WSObtenerHistorial extends AsyncTask<Void, Void, Void> {

	private String resultado;
	private HistorialActivity mActivity;
	private String id;
	private String numId;
	
	public void setParam(HistorialActivity activity, String id, String numId) {
		mActivity = activity;
		
		this.id = id;
		this.numId = numId;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		SessionManagement session = new SessionManagement(mActivity);
		UrlServer config = new UrlServer(mActivity);
		String url = config.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "obtenerHistorial";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/obtenerHistorialRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		request.addProperty("modo", 1);
		request.addProperty("pacienteTipoId", id);
		request.addProperty("pacienteNumId", numId);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		
		ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
		headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			transporte.call(SOAP_ACTION, envelope, headerPropertyArrayList);
			
			SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
			
			resultado = resultado_xml.toString();
			
		} catch (Exception e) {
			resultado = "";
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(!resultado.equals("none")){
			
			mActivity.cargarHistorial(resultado);
		}
//		mActivity.cargarHistorial(resultado);
	}

}
