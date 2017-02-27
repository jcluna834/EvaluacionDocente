package co.edu.unicauca.simcav2.logica.evaluaciondocente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import co.edu.unicauca.simcav2.dao.evaluaciondocente.AutoEvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.dao.evaluaciondocente.EvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.modelo.Bean;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Administracion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Asesoria;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Auxiliar;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Capacitacion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Docencia;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Extension;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Investigacion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Otros;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Servicio;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.TrabajosInvestigacion;


/**
 * Esta clase se encarga de la lógica relacionada con la evaluacion docente 
 *
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */
public class EvaluacionDocenteBO {
	/**
	 * Condición utilizada para cargar la información correspondiente a 
	 * la Docencia Directa asociada a una labor docente
	 */
	private static final String CONDITION_LOAD_LABOR = "WHERE l.facultadunidades=? AND l.oidperiodo=? ORDER BY NOMBREDOCENTE ASC";
	private static final String CONDITION_LOAD_LABOR1 = "where nombreproyecto=?";
	
	/*CONDICION USADA PARA AUTOEVALUACION*/
	private static final String CONDITION_LOAD_LABOR2 = "WHERE l.facultadunidades=? AND l.oidperiodo=? AND l.oiddocente=?";
	
	/**
	 * Arreglo utilizado para almacenar información de la Docencia asociada a una labor docente
	 */
	private ArrayList<Docencia> docenteDocenciaData = new ArrayList<Docencia>();

	/**
	 * Arreglo utilizado para almacenar información de los Trabajos de Investigacion asociados a una labor docente
	 */
	private ArrayList<TrabajosInvestigacion> docentetrabajosInvestigacionData = new ArrayList<TrabajosInvestigacion>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Investigación asociados a una labor docente
	 */
	private ArrayList<Investigacion> docenteInvestigacionData = new ArrayList<Investigacion>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Administración asociados a una labor docente
	 */
	private ArrayList<Administracion> docenteAdministracionData = new ArrayList<Administracion>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Asesoría asociados a una labor docente
	 */
	private ArrayList<Asesoria> docenteAsesoriaData = new ArrayList<Asesoria>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Servicio asociados a una labor docente
	 */
	private ArrayList<Servicio> docenteServicioData = new ArrayList<Servicio>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Extension asociados a una labor docente
	 */
	private ArrayList<Extension> docenteExtensionData = new ArrayList<Extension>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Capacitacion asociados a una labor docente
	 */
	private ArrayList<Capacitacion> docenteCapacitacionData = new ArrayList<Capacitacion>();
	
	/**
	 * Arreglo utilizado para almacenar información de los Otros asociados a una labor docente
	 */
	private ArrayList<Otros> docenteOtrosData = new ArrayList<Otros>();
	
	/**
	 * Objeto de la capa de acceso a datos que se utilizará cuando se requiera realizar consultas
	 * relacionadas con la información de la tabla DocenteDocencia de labor academica.
	 */
	private EvaluacionDocenteDAO objDAO = new EvaluacionDocenteDAO();
	
	/**
	 * Metodo utilizado para cargar la información de la Docencia asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteDocencia.  
	 */
	public ArrayList<Docencia> loadDocenteDocencia(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectDocenteDocencia(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteDocenciaData.add((Docencia) iteratorBean);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteDocenciaData();
	}
	
	/**
	 * Metodo utilizado para cargar la información de trabajos de investigacion asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteTrabajosInvestigacion.  
	 */
	public ArrayList<TrabajosInvestigacion> loadDocenteTrabajosInvestigacion(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectTrabajosInvestigacion(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docentetrabajosInvestigacionData.add((TrabajosInvestigacion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocentetrabajosInvestigacionData();
	}
	public ArrayList<Investigacion> loadDocenteInvestigacion(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectInvestigacion(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteInvestigacionData.add((Investigacion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteInvestigacionData();
	}
	


	public ArrayList<Administracion> loadDocenteAdministracion(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectAdministracion(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteAdministracionData.add((Administracion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAdministracionData();
	}
	
	public ArrayList<Asesoria> loadDocenteAsesoria(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectAsesoria(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteAsesoriaData.add((Asesoria) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAsesoriaData();
	}
	
	public ArrayList<Servicio> loadDocenteServicio(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectServicio(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteServicioData.add((Servicio) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteServicioData();
	}
	
	public ArrayList<Extension> loadDocenteExtension(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectExtension(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteExtensionData.add((Extension) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteExtensionData();
	}
	
	public ArrayList<Capacitacion> loadDocenteCapacitacion(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectCapacitacion(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteCapacitacionData.add((Capacitacion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteCapacitacionData();
	}
	
	public ArrayList<Otros> loadDocenteOtros(String depto, Integer oidPeriodo) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			results = this.objDAO.selectOtros(CONDITION_LOAD_LABOR, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteOtrosData.add((Otros) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteOtrosData();
	}

	/**
	 * @return the docenteDocenciaData
	 */
	public ArrayList<Docencia> getDocenteDocenciaData() {
		return docenteDocenciaData;
	}

	/**
	 * @param docenteDocenciaArray the docenteDocenciaData to set
	 */
	public void setDocenteDocenciaData(ArrayList<Docencia> docenteDocenciaData) {
		this.docenteDocenciaData = docenteDocenciaData;
	}

	/**
	 * @return the docentetrabajosInvestigacionData
	 */
	public ArrayList<TrabajosInvestigacion> getDocentetrabajosInvestigacionData() {
		return docentetrabajosInvestigacionData;
	}

	/**
	 * @param docentetrabajosInvestigacionData the docentetrabajosInvestigacionData to set
	 */
	public void setDocentetrabajosInvestigacionData(
			ArrayList<TrabajosInvestigacion> docentetrabajosInvestigacionData) {
		this.docentetrabajosInvestigacionData = docentetrabajosInvestigacionData;
	}

	/**
	 * @return the docenteInvestigacionData
	 */
	public ArrayList<Investigacion> getDocenteInvestigacionData() {
		return docenteInvestigacionData;
	}

	/**
	 * @param docenteInvestigacionData the docenteInvestigacionData to set
	 */
	public void setDocenteInvestigacionData(ArrayList<Investigacion> docenteInvestigacionData) {
		this.docenteInvestigacionData = docenteInvestigacionData;
	}

	/**
	 * @return the docenteAdministracionData
	 */
	public ArrayList<Administracion> getDocenteAdministracionData() {
		return docenteAdministracionData;
	}

	/**
	 * @param docenteAdministracionData the docenteAdministracionData to set
	 */
	public void setDocenteAdministracionData(
			ArrayList<Administracion> docenteAdministracionData) {
		this.docenteAdministracionData = docenteAdministracionData;
	}

	/**
	 * @return the docenteAsesoriaData
	 */
	public ArrayList<Asesoria> getDocenteAsesoriaData() {
		return docenteAsesoriaData;
	}
	
	/**
	 * @param docenteAsesoriaData the docenteAsesoriaData to set
	 */
	public void setDocenteAsesoriaData(ArrayList<Asesoria> docenteAsesoriaData) {
		this.docenteAsesoriaData = docenteAsesoriaData;
	}
	
	public ArrayList<Servicio> getDocenteServicioData() {
		return docenteServicioData;
	}

	public void setDocenteServicioData(ArrayList<Servicio> docenteServicioData) {
		this.docenteServicioData = docenteServicioData;
	}

	public ArrayList<Extension> getDocenteExtensionData() {
		return docenteExtensionData;
	}

	public void setDocenteExtensionData(ArrayList<Extension> docenteExtensionData) {
		this.docenteExtensionData = docenteExtensionData;
	}

	public ArrayList<Capacitacion> getDocenteCapacitacionData() {
		return docenteCapacitacionData;
	}

	public void setDocenteCapacitacionData(
			ArrayList<Capacitacion> docenteCapacitacionData) {
		this.docenteCapacitacionData = docenteCapacitacionData;
	}

	public ArrayList<Otros> getDocenteOtrosData() {
		return docenteOtrosData;
	}

	public void setDocenteOtrosData(ArrayList<Otros> docenteOtrosData) {
		this.docenteOtrosData = docenteOtrosData;
	}





	/**
	 * A partir de aquí están los métodos necesarios para ejecutar AsignarLabor
	 */
	
	private ArrayList<String> docentesInv = new ArrayList<String>();
	
	public ArrayList<String> getDocentesInv() {
		return docentesInv;
	}

	public void setDocentesInv(ArrayList<String> docentesInv) {
		this.docentesInv = docentesInv;
	}
	
	private ArrayList<String> nombreProyecto = new ArrayList<String>();
	
	public ArrayList<String> getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(ArrayList<String> nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public ArrayList<String> loadDocentes(String codigo, String depto, Integer oidPeriodo, String opcion) {
		ArrayList<String> results = new ArrayList<String>();
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(codigo);
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		try {
			if(opcion =="investigacion"){
				results = this.objDAO.docentesInvestigacion(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.docentesInv.add(iteratorBean.toString());
				}
			}
			else if(opcion =="asesoria"){
				results = this.objDAO.docentesAsesoria(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.docentesInv.add(iteratorBean.toString());
				}
			}
			else if(opcion =="administracion"){
				results = this.objDAO.docentesAdministracion(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.docentesInv.add(iteratorBean.toString());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocentesInv();
	}
	
	/*public void updateJefeLabor (int oid,String nombreDoc) {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(String.valueOf(oid));		
		parameters.add(nombreDoc);
		try {
			this.objDAO.updateJefeLabor(oid, nombreDoc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void insertJefeLabor (int oid, String nombreDoc, String opcion) {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(String.valueOf(oid));		
		parameters.add(nombreDoc);
		try {
			if(opcion =="JEFEDELABOR_ASE"){
				this.objDAO.insertJefeLaborAsesoria(oid,nombreDoc);
			}
			else if(opcion =="JEFEDELABOR_ADM"){
				this.objDAO.insertJefeLaborAdministracion(oid,nombreDoc);
			}
			else if(opcion =="JEFEDELABOR_INV"){
				this.objDAO.insertJefeLaborInvestigacion(oid,nombreDoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Investigacion> codigoVRI = new ArrayList<Investigacion>();
	

	public ArrayList<Investigacion> getCodigoVRI() {
		return codigoVRI;
	}

	public void setCodigoVRI(ArrayList<Investigacion> codigoVRI) {
		this.codigoVRI = codigoVRI;
	}
	
	private ArrayList<Asesoria> Actividad = new ArrayList<Asesoria>();
	
	public ArrayList<Asesoria> getActividad() {
		return Actividad;
	}

	public void setActividad(ArrayList<Asesoria> actividad) {
		Actividad = actividad;
	}
	
	private ArrayList<Administracion> ActividadAdmin = new ArrayList<Administracion>();
	
	
	public ArrayList<Administracion> getActividadAdmin() {
		return ActividadAdmin;
	}

	public void setActividadAdmin(ArrayList<Administracion> actividadAdmin) {
		ActividadAdmin = actividadAdmin;
	}

	public ArrayList<Investigacion> loadCodigoVRI() {
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			results = this.objDAO.selectCogidoVRI();
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.codigoVRI.add((Investigacion) iteratorBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getCodigoVRI();
	}

	public ArrayList<String> loadNombreProyecto(int oid, String nombreP, String codigo, String opcion) {
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(String.valueOf(oid));
		parameters.add(nombreP);
		parameters.add(codigo);
		try {
			if(opcion =="investigacion"){
				results = this.objDAO.selectNombreProyectos(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.nombreProyecto.add(iteratorBean.toString());
				}
			}
			else if(opcion =="asesoria"){
				results = this.objDAO.selectDescripcionAsesoria(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.nombreProyecto.add(iteratorBean.toString());
				}
			}
			else if(opcion =="administracion"){
				results = this.objDAO.selectDescripcionAdministracion(parameters);
				// Se recorre el arreglo de Beans y se convierte al tipo Docencia
				for (String iteratorBean : results) {
					this.nombreProyecto.add(iteratorBean.toString());
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getNombreProyecto();
	}
	
	public ArrayList<Asesoria> loadActividadAsesoria() {
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			results = this.objDAO.selectActividadAsesoria();
			for (Bean iteratorBean : results) {
				this.Actividad.add((Asesoria) iteratorBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getActividad();
	}

	public ArrayList<Administracion> loadActividadAdministracion() {
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			results = this.objDAO.selectActividadAdministracion();
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.ActividadAdmin.add((Administracion) iteratorBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getActividadAdmin();
	}
	
	public ArrayList<String> loadJefeLabor(String codigo, String depto, Integer oidPeriodo, String tabla) {
		ArrayList<String> results = new ArrayList<String>();
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(codigo);
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(tabla);
		try {
			results = this.objDAO.jefeLabor(parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (String iteratorBean : results) {
				this.docentesInv.add(iteratorBean.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocentesInv();
	}
	
	
	/*METODOS AUTOEVALUACION DOCENTE*/
	private AutoEvaluacionDocenteDAO objADAO = new AutoEvaluacionDocenteDAO();
	/**
	 * Metodo utilizado para cargar la información de docencia asociada a la labor academica de un docente especifico
	 * @return Retorna un arreglo con la información de DocenteDocencia.  
	 */
	
	public ArrayList<Docencia> loadDocenteDocencias(String depto, Integer oidPeriodo, Integer docente) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(String.valueOf(docente));
		try {
			results = this.objADAO.selectDocenteDocencia(CONDITION_LOAD_LABOR2, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteDocenciaData.add((Docencia) iteratorBean);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteDocenciaData();
	}
	
	/**
	 * Metodo utilizado para cargar la información de trabajos de investigacion asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteTrabajosInvestigacion.  
	 */
	public ArrayList<TrabajosInvestigacion> loadDocenteTrabajosInvestigaciones(String depto, Integer oidPeriodo, Integer docente) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(String.valueOf(docente));
		try {
			results = this.objADAO.selectTrabajosInvestigacion(CONDITION_LOAD_LABOR2, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Trabajosivestigacion
			for (Bean iteratorBean : results) {
				this.docentetrabajosInvestigacionData.add((TrabajosInvestigacion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocentetrabajosInvestigacionData();
	}
	
	/**
	 * Metodo utilizado para cargar la información de asesoria asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteAsesoria.  
	 */
	public ArrayList<Asesoria> loadDocenteAsesorias(String depto, Integer oidPeriodo,Integer docente) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(String.valueOf(docente));
		try {
			results = this.objADAO.selectAsesoria(CONDITION_LOAD_LABOR2, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteAsesoriaData.add((Asesoria) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAsesoriaData();
	}
	
	/**
	 * Metodo utilizado para cargar la información de administracion asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteAdministracion.  
	 */
	public ArrayList<Administracion> loadDocenteAdministracion(String depto, Integer oidPeriodo,Integer docente) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(String.valueOf(docente));
		try {
			results = this.objADAO.selectAdministracion(CONDITION_LOAD_LABOR2, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteAdministracionData.add((Administracion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAdministracionData();
	}
	
	/**
	 * Metodo utilizado para cargar la información de  investigacion asociada a la labor academica 
	 * @return Retorna un arreglo con la información de DocenteInvestigacion.  
	 */
	public ArrayList<Investigacion> loadDocenteInvestigacion(String depto, Integer oidPeriodo,Integer docente) {
		// Arreglo donde se almacenarán objetos de tipo Bean con el resultado del llamado a la capa DAO.
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(String.valueOf(docente));
		try {
			results = this.objADAO.selectInvestigacion(CONDITION_LOAD_LABOR2, parameters);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.docenteInvestigacionData.add((Investigacion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteInvestigacionData();
	}

	
	public void ingresarCalificacionDoc(String docencia, String identificacion, String periodo, String calificacion) {
		// TODO Auto-generated method stub
		System.out.println("Paso BO"+ docencia+" "+identificacion+" "+periodo+" "+calificacion);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(docencia);
		parameters.add(identificacion);
		parameters.add(periodo);
		parameters.add(calificacion);
		try{
			this.objADAO.ingresarCalificacionDoc(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	public void ingresarCalificacionInvest(String investigacion, String identificacion, String periodo, String calificacion) {
		// TODO Auto-generated method stub
		System.out.println("Paso BO "+ investigacion+" "+identificacion+" "+periodo+" "+calificacion);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(investigacion);
		parameters.add(identificacion);
		parameters.add(periodo);
		parameters.add(calificacion);
		try{
			this.objADAO.ingresarCalificacionInvest(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionAsesoria(String asesoria, String identificacion, String periodo, String calificacion) {
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(asesoria);
		parameters.add(identificacion);
		parameters.add(periodo);
		parameters.add(calificacion);
		try{
			this.objADAO.ingresarCalificacionAsesoria(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionTrabajosInvestigacion(String trabajosIvestigacion, String identificacion, String periodo, String calificacion) {
		// TODO Auto-generated method stub
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(trabajosIvestigacion);
		parameters.add(identificacion);
		parameters.add(periodo);
		parameters.add(calificacion);
		try {
			this.objADAO.ingresarCalificacionTrabajosInvestigacion(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionAdministracion(String administracion, String identificacion, String periodo, String calificacion) {
		// TODO Auto-generated method stub
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(administracion);
		parameters.add(identificacion);
		parameters.add(periodo);
		parameters.add(calificacion);
		try {
			this.objADAO.ingresarCalificacionAdministracion(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionDocencia(ArrayList<Integer> oiddocencia, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.ingresarCalificacionDocencias(oiddocencia, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCalificacionDocencia(ArrayList<Integer> oiddocencia, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarCalificacionDocencia(oiddocencia, identificacion, periodo, calificaciones);
		} catch (Exception e){
			e.printStackTrace();
		}
	} 

	public void ingresarCalificacionInvestigacion(ArrayList<Integer> oidinvestigacion, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.ingresarCalificacionInvestigacion(oidinvestigacion, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCalificacionInvestigacion(ArrayList<Integer> oidinvestigacion, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarCalificacionInvestigacion(oidinvestigacion, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionAsesorias(ArrayList<Integer> oidasesoria, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.ingresarCalificacionAsesorias(oidasesoria, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCalificacionAsesoria(ArrayList<Integer> oidasesoria, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarCalificacionAsesoria(oidasesoria, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionTrabajoInvestigacion(ArrayList<Integer> oidtrbinves, String identificacion, 	String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.ingresarCalificacionTrabajoInvestigacion(oidtrbinves, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCalificacionTrabajoInvestigacion(ArrayList<Integer> oidtrbinves, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarCalificacionTrabajoInvestigacion(oidtrbinves, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarCalificacionAdministraciones(ArrayList<Integer> oidadministracion, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.ingresarCalificacionAdmministraciones(oidadministracion, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarCalificacionAdministracion(ArrayList<Integer> oidadministracion, String identificacion, String periodo, ArrayList<Integer> calificaciones) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarCalificacionAdmministraciones(oidadministracion, identificacion, periodo, calificaciones);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Auxiliar> auxiliar = new ArrayList<Auxiliar>();

	public ArrayList<Auxiliar> getAuxiliar() {
		return auxiliar;
	}

	public void setAuxiliar(ArrayList<Auxiliar> auxiliar) {
		this.auxiliar = auxiliar;
	}

	public ArrayList<Auxiliar> obtenerObsDocencia(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerObsDocencia(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	} 
	
	public void insertarObservacionDocencia(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try {
			this.objADAO.insertarObservacionDocencia(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Auxiliar> obtenerObsAdmin(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerObsAdmin(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public void insertarObservacionAdmin(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try {
			this.objADAO.insertarObservacionAdmin(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public ArrayList<Auxiliar> obtenerObsAsesoria(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerObsAsesoria(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public void insertarObservacionAsesoria(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try {
			this.objADAO.insertarObservacionAsesoria(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public ArrayList<Auxiliar> obtenerObsInvestigacion(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerObsInvestigacion(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public void insertarObservacionInvestigacion(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try {
			this.objADAO.insertarObservacionInvestigacion(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public ArrayList<Auxiliar> obtenerObsTrbInvestigacion(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerObsTrbInvestigacion(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public void insertarObservacionTrbInvestigacion(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try {
			this.objADAO.insertarObservacionTrbInvestigacion(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Auxiliar> obtenerAutoevaluacionDocencia(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerAutoevaluacionDocencia(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public ArrayList<Auxiliar> obtenerAutoevaluacionInvestigacion(	String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerAutoevaluacionInvestigacion(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public ArrayList<Auxiliar> obtenerAutoevaluacionAdministracion(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerAutoevaluacionAdministracion(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public ArrayList<Auxiliar> obtenerAutoevaluacionAsesoria(String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerAutoevaluacionAsesoria(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public ArrayList<Auxiliar> obtenerAutoevaluacionTrabajosInvestigacion(	String periodo, String identificacion) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		int cont = 0;
		try {
			result = this.objADAO.obtenerAutoevaluacionTrabajosInvestigacion(periodo, identificacion);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return getAuxiliar();
	}

	public void actualizarObsDocencia(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarObsDocencia(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarObsAdministracion(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarObsAdministracion(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarObsAsesoria(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarObsAsesoria(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarObsInvestigacion(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarObsInvestigacion(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarObsTrbInvestigacion(String periodo, String identificacion, String obs) {
		// TODO Auto-generated method stub
		try{
			this.objADAO.actualizarObsTrbInvestigacion(periodo, identificacion, obs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*INICIO REPORTES*/

	public ArrayList<String> obtenerDocentes(String departamento, int periodo) throws SQLException {
		
		ArrayList<String> listaDocentes = new ArrayList<String>();
		listaDocentes.addAll(this.objDAO.obtenerDocentes(departamento, periodo));
		return listaDocentes;
	}

	public ArrayList<String> ObtenerIdDocente(String docente) throws NumberFormatException, SQLException {
		ArrayList<String> identificacion = new ArrayList<String>();
		identificacion.addAll(this.objDAO.obtenerIdDocente(docente));
		return identificacion;
	}

	public ArrayList<Auxiliar> cantidadDocencia(int id, int periodo, String departamento) throws NumberFormatException, SQLException {
		ArrayList<Bean> result = new ArrayList<Bean>();
		try {
			//result = this.objDAO.cantidadDocencia(id, periodo, departamento);
			for(Bean iteratorBean : result) {
				this.auxiliar.add((Auxiliar) iteratorBean);
			}
			//this.docenteAsesoriaData.add((Asesoria) iteratorBean);
		} catch (Exception e){
			e.printStackTrace();
		}
		return this.getAuxiliar();
	}
}
