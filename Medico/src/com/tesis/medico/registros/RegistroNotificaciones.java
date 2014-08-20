package com.tesis.medico.registros;

public class RegistroNotificaciones {

	private String idAntecedente;
	private String tipoId;
	private String numId;
	private String nombres;
	private String fecha;
	private String hora;
	private String tipoPrueba;
	private String valor;
	private String unidades;
	private String estado;
	private String lat;
	private String lon;
	
	public RegistroNotificaciones(){
		
	}
	
	public String getIdAntecedente() {
		return idAntecedente;
	}

	public void setIdAntecedente(String idAntecedente) {
		this.idAntecedente = idAntecedente;
	}

	public String getTipoId() {
		return tipoId;
	}
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
	public String getNumId() {
		return numId;
	}
	public void setNumId(String numId) {
		this.numId = numId;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTipoPrueba() {
		return tipoPrueba;
	}
	public void setTipoPrueba(String tipoPrueba) {
		this.tipoPrueba = tipoPrueba;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
	
}
