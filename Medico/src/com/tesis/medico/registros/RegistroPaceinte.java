package com.tesis.medico.registros;

public class RegistroPaceinte {

	private String tipoId;
	private String numId;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String correo;
	private String sexo;
	private String edad;
	
	public RegistroPaceinte(){
		
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
	
	@Override
	public String toString() {
		
		return getNombres() + " " + getApellidos();
	}
	
}
