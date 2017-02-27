package co.edu.unicauca.simcav2.dao.evaluaciondocente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.net.aso.r;

import co.edu.unicauca.simcav2.conexion.Conexion;
import co.edu.unicauca.simcav2.modelo.Bean;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.*;

/**
 * Clase de acceso a datos para la gestion de la Docencia Directa de la labor académica
 * 
 *  @version 1.0 10 Sept 2015
 *  @author Giovanny Palacios
 *
 */
public class EvaluacionDocenteDAO{
	
	public int oidJefeLabor = 10000;
	
	private static final String SELECT_COMUN = "select d.identificacion, d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE, ";
	private static final String SQL_LOAD_DOCENTEDOCENCIA = SELECT_COMUN + " dd.nombremateria, dd.grupo, dd.nombreprograma from docentes d join laboracademica l on d.identificacion=l.identificacion join docentedocencia dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTETRABAJOSINVESTIGACION = SELECT_COMUN + " dti.nombreestudiante, dti.objetivo, dti.actoadmin from docentes d join laboracademica l on d.identificacion=l.identificacion join docentetrabajosinvestigacion dti on dti.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEINVESTIGACION = SELECT_COMUN + " di.nombreproyecto, di.fechainiciacion, di.fechafinalizacion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEADMINISTRACION = SELECT_COMUN + " dad.descripcion, dad.unidad, dad.actoadmin from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteadministracion dad on dad.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEASESORIA= SELECT_COMUN + " das.descripcion, das.unidad, das.actoadmin	 from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTESERVICIO= SELECT_COMUN + " dd.actividad, dd.horas, dd.objetivo from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteservicios dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEEXTENSION= SELECT_COMUN + " dd.resolucion, dd.nombreproyecto, dd.fechainiciacion, dd.fechafinalizacion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteextension dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTECAPACITACION= SELECT_COMUN + " dd.resolucion, dd.descripcion from docentes d join laboracademica l on d.identificacion=l.identificacion join docentecapacitacion dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEOTROS= SELECT_COMUN + " dd.resolucion, dd.descripcion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteotros dd on dd.laboracademica = l.oidlabor ";
	
	
	private static final String SQL_LOAD_DOCENTEINVESTIGACION1 = "select d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE, di.nombreproyecto from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEASESORIA1 = "select d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE, das.descripcion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEADMINISTRACION1 = "select d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE,  dad.descripcion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteadministracion dad on dad.laboracademica = l.oidlabor ";
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteDocencia.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectDocenteDocencia(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEDOCENCIA + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanDocencia(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteDocencia.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectTrabajosInvestigacion(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTETRABAJOSINVESTIGACION + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanTrabajosInvestigacion(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	public ArrayList<Bean> selectInvestigacion(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEINVESTIGACION + querycondition;
		//String sqlquery = "select d.identificacion, d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre as NOMBREDOCENTE, di.nombreproyecto, di.fechainiciacion, di.fechafinalizacion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor";
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanInvestigacion(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> selectAdministracion(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEADMINISTRACION + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanAdministracion(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> selectAsesoria(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEASESORIA + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanAsesoria(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteServicio.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectServicio(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTESERVICIO + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanServicio(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteExtension.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectExtension(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEEXTENSION + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanExtension(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteExtension.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectCapacitacion(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTECAPACITACION + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanCapacitacion(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteExtension.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectOtros(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEOTROS + querycondition;
		
		System.out.println("SQL QUERY: " + sqlquery);
		
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		
		if (querycondition != null && parameters != null) {
			// Ejecución de consulta SQL
			rset = conexionObj.executeQueryRSET(sqlquery, parameters);
			
			// Se carga y organiza el arreglo de Beans con la información del resulset.
			results = loadBeanOtros(rset);
			
		}
		else if (querycondition == null || querycondition == "") {
			rset = conexionObj.executeQueryRSET(sqlquery);
		}
		
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset.close();
		conexionObj.close();
		return results;
	}
	
	
	/**
	 * Método utilizado para crear un Bean de Docencia a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanDocencia(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Docencia docenciaBean = new Docencia();
				docenciaBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				docenciaBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				docenciaBean.setNombreMateria(rset.getString("NOMBREMATERIA"));
				docenciaBean.setGrupo(rset.getString("GRUPO"));
				docenciaBean.setNombrePrograma(rset.getString("NOMBREPROGRAMA"));
				results.add(docenciaBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Método utilizado para crear un Bean de TrbajosInvestigacion a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanInvestigacion(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Investigacion investigacionBean = new Investigacion();
				investigacionBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				investigacionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				investigacionBean.setNombreProyecto(rset.getString("NOMBREPROYECTO"));
				investigacionBean.setFechaIniciacion(rset.getString("FECHAINICIACION"));
				investigacionBean.setFechaFinalizacion(rset.getString("FECHAFINALIZACION"));
				results.add(investigacionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Bean> loadBeanTrabajosInvestigacion(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				TrabajosInvestigacion trbinvestBean = new TrabajosInvestigacion();
				trbinvestBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				trbinvestBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				trbinvestBean.setNombresEstudiante(rset.getString("NOMBREESTUDIANTE"));
				trbinvestBean.setObjetivo(rset.getString("OBJETIVO"));
				trbinvestBean.setActoAdmin(rset.getString("ACTOADMIN"));
				results.add(trbinvestBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Bean> loadBeanAdministracion(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Administracion administracionBean = new Administracion();
				administracionBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				administracionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				administracionBean.setDescripcion(rset.getNString("DESCRIPCION"));
				administracionBean.setUnidad(rset.getString("UNIDAD"));
				administracionBean.setActoAdmin(rset.getString("ACTOADMIN"));
				results.add(administracionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Bean> loadBeanAsesoria(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Asesoria asesoriaBean = new Asesoria();
				asesoriaBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				asesoriaBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				asesoriaBean.setDescripcion(rset.getNString("DESCRIPCION"));
				asesoriaBean.setUnidad(rset.getString("UNIDAD"));
				asesoriaBean.setActoAdmin(rset.getString("ACTOADMIN"));
				results.add(asesoriaBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	
	/**
	 * Método utilizado para crear un Bean de Servicio a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanServicio(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Servicio servicioBean = new Servicio();
				servicioBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				servicioBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				servicioBean.setActividad(rset.getString("ACTIVIDAD"));
				servicioBean.setHoras(rset.getString("HORAS"));
				servicioBean.setObjetivo(rset.getString("OBJETIVO"));
				results.add(servicioBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Método utilizado para crear un Bean de Extension a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanExtension(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Extension extensionBean = new Extension();
				extensionBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				extensionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				extensionBean.setResolucion(rset.getString("RESOLUCION"));
				extensionBean.setNombreProyecto(rset.getString("NOMBREPROYECTO"));
				extensionBean.setFechaInicio(rset.getString("FECHAINICIACION"));
				extensionBean.setFechaFin(rset.getString("FECHAFINALIZACION"));
				results.add(extensionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Método utilizado para crear un Bean de Extension a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanCapacitacion(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Capacitacion capacitacionBean = new Capacitacion();
				capacitacionBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				capacitacionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				capacitacionBean.setResolucion(rset.getString("RESOLUCION"));
				capacitacionBean.setDescripcion(rset.getString("DESCRIPCION"));
				results.add(capacitacionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Método utilizado para crear un Bean de Otros a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanOtros(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Otros otrosBean = new Otros();
				otrosBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				otrosBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				otrosBean.setResolucion(rset.getString("RESOLUCION"));
				otrosBean.setDescripcion(rset.getString("DESCRIPCION"));
				results.add(otrosBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	
	
	
	
	/**
	 * A partir de aquí están los métodos necesarios para ejecutar AsignarLabor
	 */
	
	
	public ArrayList<String> docentesInvestigacion(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");

		String sqlquery2 = SQL_LOAD_DOCENTEINVESTIGACION1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(2)+" AND di.codigoVRI='"+parameters.get(0)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("NOMBREDOCENTE"));
		}
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset2.close();
		conexionObj.close();

		return results;

	}
	
	public void insertJefeLaborAsesoria (int codLabor, String nombreDoc) throws SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String idDoc="";
		String idDoc2="";
		int maxId = 0;
		String aux[] = nombreDoc.split(" ");
		
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		ResultSet rset3 = null;
		ResultSet rset4 = null;
		String sqlquery1 ="";
		
		String sqlquery4 = "SELECT ID_JEFE_ASE FROM JEFEDELABOR_ASE WHERE COD_LABOR_ASE = '"+codLabor+"'";
		System.out.println("SQL QUERY: " + sqlquery4);
		
		rset4 = conexionObj.executeQueryRSET(sqlquery4);
		while(rset4.next()){
			idDoc = rset4.getString("ID_JEFE_ASE");
		}	
		rset4.close();
		
		if (!idDoc.isEmpty()){
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){			
				String sqlquery5 = "UPDATE jefedelabor_ase SET id_jefe_ase = '"+idDoc2+"' WHERE cod_labor_ase = '"+codLabor+"'";
				System.out.println("SQL QUERY: " + sqlquery5);
				
				conexionObj.executeQueryRSET(sqlquery5);
			}
		}else{
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);			
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){
				String sqlquery3 = "SELECT MAX(OID) AS ID FROM jefedelabor_ase";
				System.out.println("SQL QUERY: " + sqlquery3);
				
				rset3 = conexionObj.executeQueryRSET(sqlquery3);
				while(rset3.next()){
					if (rset3.getString("id")!=null)
						maxId =Integer.parseInt(rset3.getString("id"));
				}
				rset3.close();
				maxId++;
				
				String sqlquery2 = "INSERT INTO jefedelabor_ase (OID,ID_JEFE_ASE,COD_LABOR_ASE) values ('"+maxId+"','"+idDoc2+"','"+codLabor+"')";
				System.out.println("SQL QUERY: " + sqlquery2);
				
				rset2 = conexionObj.executeQueryRSET(sqlquery2);
				rset2.close();
			}	
		}
		conexionObj.close();		
	}
	
	public void insertJefeLaborAdministracion (int codLabor, String nombreDoc) throws SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String idDoc="";
		String idDoc2="";
		int maxId = 0;
		String aux[] = nombreDoc.split(" ");
		if (aux.length<4){
			aux[3] = "";
		}
		
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		ResultSet rset3 = null;
		ResultSet rset4 = null;
		String sqlquery1 ="";
		
		String sqlquery4 = "SELECT ID_JEFE_ADM FROM JEFEDELABOR_ADM WHERE COD_LABOR_ADM = '"+codLabor+"'";
		System.out.println("SQL QUERY: " + sqlquery4);
		
		rset4 = conexionObj.executeQueryRSET(sqlquery4);
		while(rset4.next()){
			idDoc = rset4.getString("ID_JEFE_ADM");
		}	
		rset4.close();
		
		if (!idDoc.isEmpty()){
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){			
				String sqlquery5 = "UPDATE jefedelabor_adm SET id_jefe_adm = '"+idDoc2+"' WHERE cod_labor_adm = '"+codLabor+"'";
				System.out.println("SQL QUERY: " + sqlquery5);
				
				conexionObj.executeQueryRSET(sqlquery5);
			}
		}else{
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);			
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){
				String sqlquery3 = "SELECT MAX(OID) AS ID FROM jefedelabor_adm";
				System.out.println("SQL QUERY: " + sqlquery3);
				
				rset3 = conexionObj.executeQueryRSET(sqlquery3);
				while(rset3.next()){
					if (rset3.getString("id")!=null)
						maxId =Integer.parseInt(rset3.getString("id"));
				}
				rset3.close();
				maxId++;
				String sqlquery2 = "INSERT INTO jefedelabor_adm (OID,ID_JEFE_ADM,COD_LABOR_ADM) values ('"+maxId+"','"+idDoc2+"','"+codLabor+"')";
				System.out.println("SQL QUERY: " + sqlquery2);
				rset2 = conexionObj.executeQueryRSET(sqlquery2);
				rset2.close();
			}	
		}
		conexionObj.close();		
	}
	
	public void insertJefeLaborInvestigacion (int codLabor, String nombreDoc) throws SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String idDoc="";
		String idDoc2="";
		int maxId = 0;
		String aux[] = nombreDoc.split(" ");
		if (aux.length<4){
			aux[3] = "";
		}
		
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		ResultSet rset3 = null;
		ResultSet rset4 = null;
		String sqlquery1 ="";
		
		String sqlquery4 = "SELECT ID_JEFE_INV FROM JEFEDELABOR_INV WHERE COD_LABOR_INV = '"+codLabor+"'";
		System.out.println("SQL QUERY: " + sqlquery4);
		
		rset4 = conexionObj.executeQueryRSET(sqlquery4);
		while(rset4.next()){
			idDoc = rset4.getString("ID_JEFE_INV");
		}	
		rset4.close();
		
		if (!idDoc.isEmpty()){
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){			
				String sqlquery5 = "UPDATE jefedelabor_inv SET id_jefe_inv = '"+idDoc2+"' WHERE cod_labor_inv = '"+codLabor+"'";
				System.out.println("SQL QUERY: " + sqlquery5);
				
				conexionObj.executeQueryRSET(sqlquery5);
			}
		}else{
			if(aux[1].isEmpty()){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido is null AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else if(aux.length<4){
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre is null";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			else{
				sqlquery1 = "SELECT identificacion FROM docentes WHERE primerapellido='"+aux[0]+"' AND segundoapellido='"+aux[1]+"' AND primernombre= '"+aux[2]+"' AND segundonombre = '"+aux[3]+"'";
				System.out.println("SQL QUERY: " + sqlquery1);
			}
			
			rset1 = conexionObj.executeQueryRSET(sqlquery1);			
			while(rset1.next()){
				idDoc2 = rset1.getString("identificacion");
			}
			rset1.close();
			
			if(!idDoc2.isEmpty()){
				String sqlquery3 = "SELECT MAX(OID) AS ID FROM jefedelabor_inv";
				System.out.println("SQL QUERY: " + sqlquery3);
				
				rset3 = conexionObj.executeQueryRSET(sqlquery3);
				while(rset3.next()){
					if (rset3.getString("id")!=null)
						maxId =Integer.parseInt(rset3.getString("id"));
				}
				rset3.close();
				maxId++;
				
				String sqlquery2 = "INSERT INTO jefedelabor_inv (OID,ID_JEFE_INV,COD_LABOR_INV) values ('"+maxId+"','"+idDoc2+"','"+codLabor+"')";
				System.out.println("SQL QUERY: " + sqlquery2);
				
				rset2 = conexionObj.executeQueryRSET(sqlquery2);
				rset2.close();
			}	
		}
		conexionObj.close();		
	}
	
	public ArrayList<Bean> selectCogidoVRI() throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select distinct codigoVRI from docenteinvestigacion";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanInvestigacionFiltrado(rset);
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> loadBeanInvestigacionFiltrado(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Investigacion investigacionBean = new Investigacion();
				investigacionBean.setCondigoVRI(rset.getString("CODIGOVRI"));
				results.add(investigacionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<String> selectNombreProyectos(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = SQL_LOAD_DOCENTEINVESTIGACION1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(0)+" AND di.codigoVRI='"+parameters.get(2)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("NOMBREPROYECTO"));
		}
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset2.close();
		conexionObj.close();

		return results;
	}

	public ArrayList<Bean> selectActividadAsesoria()  throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select distinct actividad from docenteasesoria";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanAsesoriaFiltrado(rset);
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> loadBeanAsesoriaFiltrado(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Asesoria asesoriaBean = new Asesoria();
				asesoriaBean.setActividad(rset.getString("ACTIVIDAD"));
				results.add(asesoriaBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<String> selectDescripcionAsesoria(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = SQL_LOAD_DOCENTEASESORIA1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(0)+" AND das.actividad='"+parameters.get(2)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("DESCRIPCION"));
		}
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset2.close();
		conexionObj.close();

		return results;
	}
	
	public ArrayList<String> docentesAsesoria(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = SQL_LOAD_DOCENTEASESORIA1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(2)+" AND das.actividad='"+parameters.get(0)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("NOMBREDOCENTE"));
		}
		rset2.close();
		conexionObj.close();

		return results;

	}

	public ArrayList<Bean> selectActividadAdministracion() throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select distinct actividad from docenteadministracion where actividad <> 1 AND actividad <> 100";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanAdministracionFiltrado(rset);
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> loadBeanAdministracionFiltrado(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Administracion asesoriaBean = new Administracion();
				asesoriaBean.setActividad(rset.getString("ACTIVIDAD"));
				results.add(asesoriaBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<String> selectDescripcionAdministracion(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = SQL_LOAD_DOCENTEADMINISTRACION1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(0)+" AND dad.actividad='"+parameters.get(2)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("DESCRIPCION"));
		}
		// IMPORTANTE: Se debe cerrar el resulset y la conexion luego de realizar la consulta.
		// Verificar que se cierre la conexión a la base de datos, se pueden generar problemas de concaurrencia.
		rset2.close();
		conexionObj.close();

		return results;
	}
	
	public ArrayList<String> docentesAdministracion(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = SQL_LOAD_DOCENTEADMINISTRACION1 + "WHERE l.facultadunidades="+parameters.get(1)+" AND l.oidperiodo="+parameters.get(2)+" AND dad.actividad='"+parameters.get(0)+"'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();		
		while(rset2.next()){
			results.add(rset2.getString("NOMBREDOCENTE"));
		}
		rset2.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<String> jefeLabor(ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select ID_JEFE_"+parameters.get(3)+" from JEFEDELABOR_"+parameters.get(3)+" WHERE COD_LABOR_"+parameters.get(3)+"="+parameters.get(0);
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results2 = new ArrayList<String>();
		ArrayList<String> results3 = new ArrayList<String>();
		while(rset2.next()){
			if (rset2 != null){
				results2.add(rset2.getString("ID_JEFE_"+parameters.get(3)));
			}
		}
		if (!results2.isEmpty()){
			String sqlquery3 = "select primerapellido || ' ' || segundoapellido || ' ' ||primernombre || ' ' ||segundonombre as nombreDocente FROM docentes WHERE identificacion="+results2.get(0);
		
			System.out.println("SQL QUERY: " + sqlquery3);
			ResultSet rset3 = null;
			rset3 = conexionObj.executeQueryRSET(sqlquery3);	
			while(rset3.next()){
				results3.add(rset3.getString("nombreDocente"));
			}
			rset3.close();	
		}		
		
		rset2.close();
		conexionObj.close();
		return results3;
	}
	
	/*INICIO REPORTES*/

	public ArrayList<String> obtenerDocentes(String departamento, int periodo) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<String> listaDocentes = new ArrayList<String>();
		String sqlquery = "select primernombre || ' ' ||  segundonombre || ' ' || primerapellido || ' ' || segundoapellido as nombreDocente FROM docentes WHERE facultadunidades = '"+departamento+"'";
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		while (rset.next()){
			listaDocentes.add(rset.getString("nombreDocente"));
		}
		rset.close();
		conexionObj.close();
		return listaDocentes;
	}
	public ArrayList<String> obtenerNombreDocente(String oid) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<String> listaDocentes = new ArrayList<String>();
		String sqlquery = "select primernombre || ' ' ||  segundonombre || ' ' || primerapellido || ' ' || segundoapellido as nombreDocente FROM docentes WHERE identificacion = '"+oid+"'";
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		while (rset.next()){
			listaDocentes.add(rset.getString("nombreDocente"));
		}
		rset.close();
		conexionObj.close();
		return listaDocentes;
	}
	public ArrayList<String> obtenerIdDocente(String docente) throws NumberFormatException, SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<String> identificacion = new ArrayList<String>();
		String sqlquery = "select identificacion from docentes where primernombre || ' ' ||  segundonombre || ' ' || primerapellido || ' ' || segundoapellido like '"+ docente+"'";
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		while (rset.next()){
			identificacion.add(rset.getString("identificacion"));
		}
		rset.close();
		conexionObj.close();
		return identificacion;
	}


	//funcion que cuenta las evaluaciones hechas por un docente en un periodo
	public ArrayList<Auxiliar> cantidadEvaluaciones(String nombre, Integer periodo) throws SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery="select count(*) as cantidad from CUESTIONARIO where NOMBRE_EVALUADOR = '"+nombre+"' and periodo_evaluado = "+periodo;
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanAuxiliar(rset);
		ArrayList<Auxiliar> auxiliar = new ArrayList<Auxiliar>();
		try {
			for(Bean iteratorBean : results) {
				auxiliar.add((Auxiliar) iteratorBean);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		rset.close();
		conexionObj.close();
		return auxiliar;
	}
	public ArrayList<Bean> loadBeanAuxiliar(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Auxiliar auxiliarBean = new Auxiliar();
				auxiliarBean.setCantidad(Integer.parseInt(rset.getString("cantidad")));
				results.add(auxiliarBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
//metodo para ingresar un docente a la tabla notificacion
	public void IngresarNotificacion(String id) throws NumberFormatException, SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet rset = null;
		String sqlquery2 = "INSERT INTO notificacion (OID) values ('"+id+"')";
		System.out.println(sqlquery2+ " id: "+id);
		rset = conexionObj.executeQueryRSET(sqlquery2);
		rset.close();	
		conexionObj.close();
	}
//funcion que busca si una persona ya esta siendo notificado
	public boolean Buscar(String oid) throws NumberFormatException, SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select * from notificacion n where n.oid='"+ oid + "'";
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		boolean result = true;
		try{
			rset2 = conexionObj.executeQueryRSET(sqlquery2);
			if ( rset2.next() )
			{
				result=false;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		rset2.close();
		conexionObj.close();
		return result;
		}
//metodo que elimina de la tabla notificacion a un docente
	public void quitarnotificacion(String oid)throws NumberFormatException, SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "delete  from notificacion  where oid='"+ oid + "'";
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		rset2.close();
		conexionObj.close();
	}

	public ArrayList<Auxiliar> cantidadLabor(String id, int periodo, String departamento, String nombreTabla) throws NumberFormatException, SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select count(*) as cantidad FROM docentes d join laboracademica l on d.identificacion=l.identificacion join "+nombreTabla+" dd on dd.laboracademica = l.oidlabor WHERE d.identificacion = '"+ id + "' and l.oidperiodo = "+periodo+ " and l.facultadunidades = '"+departamento+"'";
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanAuxiliar(rset);
		ArrayList<Auxiliar> auxiliar = new ArrayList<Auxiliar>();
		try {
			for(Bean iteratorBean : results) {
				auxiliar.add((Auxiliar) iteratorBean);
				System.out.println("Cantidad DAO: "+auxiliar.get(0).getCantidad());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		rset.close();
		conexionObj.close();
		
		return auxiliar;
	}	
}
