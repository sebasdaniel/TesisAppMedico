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
import android.widget.Toast;

import com.tesis.medico.UrlServer;

public class WSRegistrarMedico extends AsyncTask<String, Void, Void> {

	private String resultado;
	private Context context;
	
	public void setParams(Context context){
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		
		UrlServer config = new UrlServer(context);
		String url = config.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "registrarMedico";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/registrarMedicoRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", params[0]);
		request.addProperty("clave", params[1]);
		request.addProperty("cedula", params[2]);
		request.addProperty("nombres", params[3]);
		request.addProperty("apellidos", params[4]);
		request.addProperty("sexo", params[5]);
		request.addProperty("numeroTP", params[6]);
		request.addProperty("nacionalidad", params[7]);
		request.addProperty("especializacion", params[8]);
		request.addProperty("direccion", params[9]);
		request.addProperty("telefono", params[10]);
		request.addProperty("idMunicipio", Integer.parseInt(params[11]));
		
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
		
		System.out.println(resultado);
		
		if(resultado.equals("ok")){
			Toast toast = Toast.makeText(context, "Medico registrado correctamente", Toast.LENGTH_LONG);
			toast.show();
			
		} else {
			
			Toast toast = Toast.makeText(context, "Error al registrar medico", Toast.LENGTH_LONG);
			toast.show();
		}
	}

}
