package com.tesis.medico.tareas;

import java.util.ArrayList;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.tesis.medico.UrlServer;
import com.tesis.medico.gui.DatosActivity;
import com.tesis.medico.registros.RegistroMunicipio;

import android.os.AsyncTask;

public class WSObtenerMunicipios2 extends AsyncTask<Integer, Void, Void> {

	private String resultado;
	private DatosActivity mActivity;
	private ArrayList<RegistroMunicipio> registro;
	
	public void setParams(DatosActivity activity){
		mActivity = activity;
	}
	
	@Override
	protected Void doInBackground(Integer... params) {
		
		UrlServer config = new UrlServer(mActivity);
		String url = config.getUrl();
		
		final String NAMESPACE = "http://ws.simop.com/";
		final String URL = "http://" + url + "/SIMOP/SIMOP?WSDL";
		final String METHOD_NAME = "listaMunicipios";
		final String SOAP_ACTION = "http://ws.simop.com/SIMOP/listaMunicipiosRequest";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		request.addProperty("idDepartamento", params[0]);
		
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
			
			registro = new ArrayList<RegistroMunicipio>();
			
			while(fin != -1){
				
				String linea = resultado.substring(inicio + 1, fin);
				
				String[] datos = linea.split(";");
					
				RegistroMunicipio temp = new RegistroMunicipio();
				
				temp.setId(Integer.parseInt(datos[0]));
				temp.setNombre(datos[1]);
				
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
		
		if(!resultado.isEmpty() && !registro.isEmpty()){
			
			mActivity.cargarMunicipios(registro);
		} else {
			System.out.println("respesta del servidor: " + resultado);
		}
	}

}
