package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteAdministracion.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

public class TrabajosInvestigacion implements Bean{
	
	private String identificacion;
	private String nombresDocente;
	private String nombresEstudiante;
	private String objetivo;
	private String actoAdmin;
	
	/*ATRIBUTOS NECESARIOS PARA AUTOEVALUACION*/
	private String periodo;
	private int iden;
	
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public int getIden() {
		return iden;
	}
	public void setIden(int iden) {
		this.iden = iden;
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
	/**
	 * @return the nombresEstudiante
	 */
	public String getNombresEstudiante() {
		return nombresEstudiante;
	}
	/**
	 * @param nombresEstudiante the nombresEstudiante to set
	 */
	public void setNombresEstudiante(String nombresEstudiante) {
		this.nombresEstudiante = nombresEstudiante;
	}
	/**
	 * @return the objetivo
	 */
	public String getObjetivo() {
		return objetivo;
	}
	/**
	 * @param objetivo the objetivo to set
	 */
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
}