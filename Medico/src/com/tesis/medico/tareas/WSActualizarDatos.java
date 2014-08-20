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

import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;
import com.tesis.medico.registros.RegistroMedico;

public class WSActualizarDatos extends AsyncTask<RegistroMedico, Void, Void> {

	private String resultado;
	private Context context;
	
	public void setParams(Context context){
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(RegistroMedico... params) {
		
		SessionManagement session = new SessionManagement(context);
		UrlServer config = new UrlServer(context);
		String url = config.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "actualizarMedico";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/actualizarMedicoRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("correo", session.getEmail());
		request.addProperty("clave", session.getPass());
		request.addProperty("nuevoCorreo", params[0].getCorreo());
		request.addProperty("nuevaClave", params[0].getClave());
		request.addProperty("cedula", params[0].getCedula());
		request.addProperty("nombres", params[0].getNombres());
		request.addProperty("apellidos", params[0].getApellidos());
		request.addProperty("sexo", params[0].getSexo());
		request.addProperty("numeroTP", params[0].getNumeroTP());
		request.addProperty("nacionalidad", params[0].getNacionalidad());
		request.addProperty("especializacion", params[0].getEspecializacion());
		request.addProperty("direccion", params[0].getDireccion());
		request.addProperty("telefono", params[0].getTelefono());
		request.addProperty("idMunicipio", Integer.parseInt(params[0].getMunicipio()));
		
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
		
		//System.out.println(resultado);
		
		if(resultado.equals("ok")){
			
			Toast toast = Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_LONG);
			toast.show();
			
		} else {
			
			Toast toast = Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_LONG);
			toast.show();
		}
	}
}
