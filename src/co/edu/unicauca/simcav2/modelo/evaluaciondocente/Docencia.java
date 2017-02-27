package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import co.edu.unicauca.simcav2.modelo.Bean;

/**
 * Bean para gestionar la información correspondiente a la tabla DocenteDocencia.
 * Se van a tomar únicamente los campos necesarios para el proceso de Evaluación Docente.
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

public class Docencia implements Bean{
	
	private int oid;
	private String identificacion;
	private String nombresDocente;
	private String nombreMateria;
	private String grupo;
	private String nombrePrograma;
	
	/*ATRIBUTOS NECESARIOS PARA AUTOEVALUACION*/
	private String periodo;
		
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	/**
	 * @return El valor del atributo grupo
	 */
	public String getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo establece el valor del atributo grupo 
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return El valor del atributo nombreMateria
	 */
	public String getNombreMateria() {
		return nombreMateria;
	}
	/**
	 * @param nombreMateria establece el valor del atributo nombreMateria 
	 */
	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}
	/**
	 * @return El valor del atributo nombrePrograma
	 */
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	/**
	 * @param nombrePrograma establece el valor del atributo nombrePrograma 
	 */
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
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
	 * @return the oid
	 */
	public int getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(int oid) {
		this.oid = oid;
	}
	
}