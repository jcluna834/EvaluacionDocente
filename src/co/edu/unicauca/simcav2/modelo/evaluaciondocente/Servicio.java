package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteServicio.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 2.0 09 Oct 2015
 * @author Julio César Luna
 */

public class Servicio implements Bean {
	private String identificacion;
	private String nombresDocente;
	private String actividad;
	private String horas;
	private String objetivo;
	
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
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
}
