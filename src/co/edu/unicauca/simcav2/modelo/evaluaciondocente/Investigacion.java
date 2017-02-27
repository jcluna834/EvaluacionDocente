package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteAdministracion.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

public class Investigacion implements Bean{
	
	private String identificacion;
	private String nombresDocente;
	private String nombreProyecto;
	private String fechaIniciacion;
	private String fechaFinalizacion;
	private String condigoVRI;
	
	/*ATRIBUTOS NECESARIOS PARA AUTOEVALUACION*/
	private int oid;
	private String periodo;
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getCondigoVRI() {
		return condigoVRI;
	}
	public void setCondigoVRI(String condigoVRI) {
		this.condigoVRI = condigoVRI;
	}
	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return the nombresDocente
	 */
	public String getNombresDocente() {
		return nombresDocente;
	}
	/**
	 * @param nombresDocente the nombresDocente to set
	 */
	public void setNombresDocente(String nombresDocente) {
		this.nombresDocente = nombresDocente;
	}
	/**
	 * @return the nombreProyecto
	 */
	public String getNombreProyecto() {
		return nombreProyecto;
	}
	/**
	 * @param nombreProyecto the nombreProyecto to set
	 */
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}
	/**
	 * @return the fechaIniciacion
	 */
	public String getFechaIniciacion() {
		return fechaIniciacion;
	}
	/**
	 * @param fechaIniciacion the fechaIniciacion to set
	 */
	public void setFechaIniciacion(String fechaIniciacion) {
		this.fechaIniciacion = fechaIniciacion;
	}
	/**
	 * @return the fechaFinalizacion
	 */
	public String getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	/**
	 * @param fechaFinalizacion the fechaFinalizacion to set
	 */
	public void setFechaFinalizacion(String fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	
}