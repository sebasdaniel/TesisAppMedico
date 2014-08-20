package com.tesis.medico.gui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tesis.medico.R;
import com.tesis.medico.R.id;
import com.tesis.medico.gui.DatePickerFragment.DatePickerFragmentListener;

public class ConfigEstadistica extends FragmentActivity implements DatePickerFragmentListener {

	private TextView labelFechaInicio;
	private TextView labelFechaFin;
	private String fechaInicio;
	private String fechaFin;
	private boolean esFechaInicio;
	private boolean esFechaFin;
	//private SessionManagement session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_estadistica);
		
//		session = new SessionManagement(getApplicationContext());
		
//		ActionBar actionBar = getActionBar();
//		actionBar.setTitle(session.getName());
		
		final Bundle bundle = getIntent().getExtras();
		
		final Spinner spinTipoChequeo = (Spinner) findViewById(id.spinnerTiposChequeos);
		
		labelFechaInicio = (TextView) findViewById(id.tvFechaInicio);
		labelFechaFin = (TextView) findViewById(id.tvFechaFin);
		
		ImageButton btnFechaInicio = (ImageButton) findViewById(id.buttonFecha1);
		ImageButton btnFechaFin = (ImageButton) findViewById(id.buttonFecha2);
		Button btnVer = (Button) findViewById(id.buttonVer);
		
		fechaInicio = null;
		fechaFin = null;
		
		esFechaInicio = false;
		esFechaFin = false;
		
		btnFechaInicio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				esFechaInicio = true;
				selecFecha();
			}
		});
		
		btnFechaFin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				esFechaFin = true;
				selecFecha();
			}
		});
		
		btnVer.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View arg0) {
				
				if(fechaInicio != null && fechaFin != null){
					
					String tipoChequeo = (String)spinTipoChequeo.getSelectedItem();
					
					Intent intent = new Intent(getApplicationContext(), GraficaEstadistica.class);
					
					intent.putExtra("TipoChequeo", tipoChequeo.toLowerCase());
					intent.putExtra("FechaInicio", fechaInicio);
					intent.putExtra("FechaFin", fechaFin);
					intent.putExtra("NumeroId", bundle.getString("numeroid"));
					intent.putExtra("TipoId", bundle.getString("tipoid"));
					
					startActivity(intent);
				}
			}
		});
		
	}

	@Override
	public void onFinishDatePickerDialog(int year, int month, int day) {
		
		if(esFechaInicio){
			
			fechaInicio = year + "-" + (month + 1) + "-" + day;
			labelFechaInicio.setText(fechaInicio);
			esFechaInicio = false;
			
		} else if(esFechaFin){
			
			fechaFin = year + "-" + (month + 1) + "-" + day;
			labelFechaFin.setText(fechaFin);
			esFechaFin = false;
		}
	}
	
	private void selecFecha(){
		
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

}
