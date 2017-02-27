package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteServicio.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 2.0 09 Oct 2015
 * @author Julio César Luna
 */

public class Capacitacion implements Bean{
	private String identificacion;
	private String nombresDocente;
	private String resolucion;
	private String descripcion;
	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getNombresDocente() {
		return nombresDocente;
	}
	public void setNombresDocente(String nombresDocente) {
		this.nombresDocente = nombresDocente;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
