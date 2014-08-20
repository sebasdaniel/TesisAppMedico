package com.tesis.medico.tareas;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;

import com.tesis.medico.SessionManagement;
import com.tesis.medico.UrlServer;
import com.tesis.medico.gui.SolicitudesActivity;
import com.tesis.medico.registros.RegistroSolicitudes;

public class WSSolicitudes extends AsyncTask<Void, Void, Void> {

	private SolicitudesActivity mActivity;
	private ArrayList<RegistroSolicitudes> registro;
	
	public void setParams(SolicitudesActivity activity){
		mActivity = activity;
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		
		SessionManagement session = new SessionManagement(mActivity);
		UrlServer servidor = new UrlServer(mActivity);
		String url = servidor.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "listarSolicitudes";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/listarSolicitudesRequest";
		
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
			
			String respuesta = resultado_xml.toString();
			
			int inicio, fin;
			
			inicio = -1;
			fin = respuesta.indexOf("\n");
			
			registro = new ArrayList<RegistroSolicitudes>();
			
			while(fin != -1){
				
				String linea = respuesta.substring(inicio + 1, fin);
				
				String[] datos = linea.split(";");
					
				RegistroSolicitudes temp = new RegistroSolicitudes();
				
				temp.setTipoId(datos[0]);
				temp.setNumeroId(datos[1]);
				temp.setNombres(datos[2]);
				temp.setApellidos(datos[3]);
				temp.setTelefono(datos[4]);
				temp.setCorreo(datos[5]);
				temp.setSexo(datos[6]);
				temp.setEdad(datos[7]);
				temp.setFecha(datos[8]);
				
				registro.add(temp);
				
				inicio = fin;
				fin = respuesta.indexOf("\n", inicio + 1);
			}
			
		} catch (Exception e) {
			System.out.println("---se produjo una exception---");
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(registro != null && !registro.isEmpty()){
			
			RegistroSolicitudes[] reg = new RegistroSolicitudes[registro.size()];
			
			registro.toArray(reg);
			
			mActivity.cargarLista(reg);
		} else {
			
			new AlertDialog.Builder(mActivity).setTitle("Mansaje").setMessage("Al parecer no tiene solicitudes pendientes")
			.setPositiveButton("Aceptar", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			}).show();
		}
	}

}
