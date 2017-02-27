package co.edu.unicauca.simcav2.logica.evaluaciondocente;

import java.sql.SQLException;
import java.util.ArrayList;
import co.edu.unicauca.simcav2.dao.evaluaciondocente.EvaluacionJefeLaborDAO;
import co.edu.unicauca.simcav2.modelo.Bean;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Administracion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Asesoria;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Investigacion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Usuario;

/**
 * Esta clase se encarga de la lógica relacionada con la evaluacion docente
 * 
 * @version 2.0 27 Oct 2015
 * @author Grupo2 Proyecto II
 */
public class EvaluacionJefeLaborBO {
	/**
	 * Condición utilizada para cargar la información correspondiente a la
	 * Docencia Directa asociada a una labor docente
	 */
	private static final String CONDITION_LOAD_MAT_ASE = "WHERE ID_JEFE_ASE=?";
	private static final String CONDITION_LOAD_MAT_INV = "WHERE ID_JEFE_INV=?";
	private static final String CONDITION_LOAD_DOC_ASE = "WHERE l.facultadunidades=? AND l.oidperiodo=? AND das.actividad=? AND d.identificacion<>? ORDER BY NOMBREDOCENTE ASC";
	
	/**
	 * Objeto de la capa de acceso a datos que se utilizará cuando se requiera
	 * realizar consultas
	 */
	private EvaluacionJefeLaborDAO objDAO = new EvaluacionJefeLaborDAO();

	private ArrayList<Investigacion> docenteInvestigacionData = new ArrayList<Investigacion>();

	public ArrayList<Investigacion> getDocenteInvestigacionData() {
		return docenteInvestigacionData;
	}

	public void setDocenteInvestigacionData(
			ArrayList<Investigacion> docenteInvestigacionData) {
		this.docenteInvestigacionData = docenteInvestigacionData;
	}

	private ArrayList<Asesoria> docenteAsesoriaData = new ArrayList<Asesoria>();

	public ArrayList<Asesoria> getDocenteAsesoriaData() {
		return docenteAsesoriaData;
	}

	public void setDocenteAsesoriaData(ArrayList<Asesoria> docenteAsesoriaData) {
		this.docenteAsesoriaData = docenteAsesoriaData;
	}

	public ArrayList<Asesoria> loadMateriasAsesoria(String idJefe) {
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(idJefe);
		try {
			results = this.objDAO.materiasAsesoria(CONDITION_LOAD_MAT_ASE,
					parameters);
			for (Bean iteratorBean : results) {
				this.docenteAsesoriaData.add((Asesoria) iteratorBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAsesoriaData();
	}

	public ArrayList<Investigacion> loadMateriasInvestigacion(String idJefe) {
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(idJefe);
		try {
			results = this.objDAO.materiasInvestigacion(CONDITION_LOAD_MAT_INV,
					parameters);
			for (Bean iteratorBean : results) {
				this.docenteInvestigacionData.add((Investigacion) iteratorBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteInvestigacionData();
	}

	public ArrayList<Asesoria> loadDocentesAsesoria(String depto,
			Integer oidPeriodo, String actividad, String idJefe) {
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Arreglo de parámetros de la consulta.
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(depto);
		parameters.add(String.valueOf(oidPeriodo));
		parameters.add(actividad);
		parameters.add(idJefe);
		try {
			results = this.objDAO.docentesIntegrantesAsesoria(
					CONDITION_LOAD_DOC_ASE, parameters);
			for (Bean iteratorBean : results) {
				this.docenteAsesoriaData.add((Asesoria) iteratorBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAsesoriaData();
	}

	private ArrayList<String> listaPreguntas = new ArrayList<String>();

	public ArrayList<String> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(ArrayList<String> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	public ArrayList<String> loadListaPreguntas(String opc) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = this.objDAO.listaPreguntas(opc);
			for (String iteratorBean : results) {
				this.listaPreguntas.add(iteratorBean.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getListaPreguntas();
	}

	public String loadNombreUsuario(String id) {
		String result = "";
		try {
			result = this.objDAO.nomUsuario(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void createCuestionario(String tipoCuest, String labor,
			String evaluado, String nomJefe, Integer periodo,
			ArrayList<Integer> calificaciones) {

		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(tipoCuest);
		parameters.add(labor);
		parameters.add(nomJefe);
		parameters.add(evaluado);
		parameters.add(String.valueOf(periodo));
		try {
			this.objDAO.insertCuestionario(parameters, calificaciones);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> listaDocentesEvaluados = new ArrayList<String>();

	public ArrayList<String> getListaDocentesEvaluados() {
		return listaDocentesEvaluados;
	}

	public void setListaDocentesEvaluados(ArrayList<String> listaDocentesEvaluados) {
		this.listaDocentesEvaluados = listaDocentesEvaluados;
	}

	public ArrayList<String> loadDocAsesoriaEvaluado(String actividad) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = this.objDAO.docAsesoriaEvaluado(actividad);
			for (String iteratorBean : results) {
				this.listaDocentesEvaluados.add(iteratorBean.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getListaDocentesEvaluados();
	}

	public ArrayList<String> loadDocInvestigacionEvaluado(String codigoVRI) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = this.objDAO.docInvestigacionEvaluado(codigoVRI);
			for (String iteratorBean : results) {
				this.listaDocentesEvaluados.add(iteratorBean.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getListaDocentesEvaluados();
	}
	
	public ArrayList<Usuario> cargarInicioSesion(){
		ArrayList<Usuario> results = new ArrayList<Usuario>();
		try {
			results = this.objDAO.inicioUsuario();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public int notificarBO(String oid){
		int result = -1;
		
		try {
			result = this.objDAO.notificarDAO(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<Investigacion> loadJefesInvestigacion() {
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Arreglo de parámetros de la consulta.
		try {
			results = this.objDAO.jefesInvestigacion();
			for (Bean iteratorBean : results) {
				this.docenteInvestigacionData.add((Investigacion) iteratorBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteInvestigacionData();
	}

	public ArrayList<Asesoria> loadJefesAsesoria() {
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Arreglo de parámetros de la consulta.
		try {
			results = this.objDAO.jefesAsesoria();
			for (Bean iteratorBean : results) {
				this.docenteAsesoriaData.add((Asesoria) iteratorBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDocenteAsesoriaData();
	}

	public ArrayList<String> loadDocentesEliminar(String evaluador) {
		listaDocentesEvaluados.clear();
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = this.objDAO.decentesEliminar(evaluador);
			for (String iteratorBean : results) {
				this.listaDocentesEvaluados.add(iteratorBean.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getListaDocentesEvaluados();
	}

	public ArrayList<String> loadJefesEliminar(String labor) {
		listaDocentesEvaluados.clear();
		ArrayList<String> results = new ArrayList<String>();
		try {
			results = this.objDAO.jefesEliminar(labor);
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (String iteratorBean : results) {
				this.listaDocentesEvaluados.add(iteratorBean.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getListaDocentesEvaluados();
	}
	
	private ArrayList<Administracion> jefeDepto = new ArrayList<Administracion>();
	

	public ArrayList<Administracion> getJefeDepto() {
		return jefeDepto;
	}

	public void setJefeDepto(ArrayList<Administracion> jefeDepto) {
		this.jefeDepto = jefeDepto;
	}

	public ArrayList<Administracion> loadjefeDepto() {
		jefeDepto.clear();
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			results = this.objDAO.jefesDepto();
			// Se recorre el arreglo de Beans y se convierte al tipo Docencia
			for (Bean iteratorBean : results) {
				this.jefeDepto.add((Administracion) iteratorBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJefeDepto();
	}

}
