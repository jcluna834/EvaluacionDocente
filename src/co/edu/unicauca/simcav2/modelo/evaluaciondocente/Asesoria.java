package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteAsesoria.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

public class Asesoria implements Bean{
	
	private String identificacion;
	private String nombresDocente;
	private String descripcion;
	private String unidad;
	private String actoAdmin;
	private String actividad;
	
	/*ATRIBUTOS NECESARIOS PARA LA AUTOEVALUACION*/
	private int iden;
	private String periodo;
	public int getIden() {
		return iden;
	}
	public void setIden(int iden) {
		this.iden = iden;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}
	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	/**
	 * @return the actoAdmin
	 */
	public String getActoAdmin() {
		return actoAdmin;
	}
	/**
	 * @param actoAdmin the actoAdmin to set
	 */
	public void setActoAdmin(String actoAdmin) {
		this.actoAdmin = actoAdmin;
	}
	
}