package com.tesis.medico.registros;

public class RegistroSolicitudes {
	
	private String tipoId;
	private String numeroId;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String correo;
	private String sexo;
	private String edad;
	private String fecha;
	
	public RegistroSolicitudes(){
		
	}
	
	public String getTipoId() {
		return tipoId;
	}
	
	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}
	
	public String getNumeroId() {
		return numeroId;
	}
	
	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}
	
	public String getNombres() {
		return nombres;
	}
	
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
