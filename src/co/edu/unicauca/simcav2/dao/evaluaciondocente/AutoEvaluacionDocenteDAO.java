package co.edu.unicauca.simcav2.dao.evaluaciondocente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import co.edu.unicauca.simcav2.conexion.Conexion;
import co.edu.unicauca.simcav2.modelo.Bean;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Administracion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Asesoria;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Auxiliar;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Docencia;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Investigacion;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.TrabajosInvestigacion;

public class AutoEvaluacionDocenteDAO {
	
	private static final String SELECT_COMUN = "select d.identificacion, d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE, ";
	/*private static final String SQL_LOAD_DOCENTEDOCENCIA = SELECT_COMUN + " dd.oid, dd.nombremateria, dd.grupo, dd.nombreprograma from docentes d join laboracademica l on d.identificacion=l.identificacion join docentedocencia dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTETRABAJOSINVESTIGACION = SELECT_COMUN + " dti.oid, dti.nombreestudiante, dti.objetivo, dti.actoadmin, dti.laboracademica from docentes d join laboracademica l on d.identificacion=l.identificacion join docentetrabajosinvestigacion dti on dti.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEINVESTIGACION = SELECT_COMUN + " di.oid, di.nombreproyecto, di.fechainiciacion, di.fechafinalizacion, di.laboracademica from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEADMINISTRACION = SELECT_COMUN + " dad.id, dad.descripcion, dad.unidad, dad.actoadmin, dad.laboracademica from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteadministracion dad.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEASESORIA= SELECT_COMUN + " das.id, das.descripcion, das.unidad, das.actoadmin, das.laboracademica from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor ";
	*/
	private static final String SQL_LOAD_DOCENTEDOCENCIA = SELECT_COMUN + " dd.oid, dd.nombremateria, dd.grupo, dd.nombreprograma, l.oidperiodo from docentes d join laboracademica l on d.identificacion=l.identificacion join docentedocencia dd on dd.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTETRABAJOSINVESTIGACION = SELECT_COMUN + " dti.oid, dti.nombreestudiante, dti.objetivo, dti.actoadmin, dti.laboracademica, l.oidperiodo from docentes d join laboracademica l on d.identificacion=l.identificacion join docentetrabajosinvestigacion dti on dti.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEINVESTIGACION = SELECT_COMUN + " di.oid, di.nombreproyecto, di.fechainiciacion, di.fechafinalizacion, di.laboracademica, l.oidperiodo, di.codigovri from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEADMINISTRACION = SELECT_COMUN + " dad.id, dad.descripcion, dad.unidad, dad.actoadmin, dad.laboracademica, l.oidperiodo from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteadministracion dad on dad.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEASESORIA= SELECT_COMUN + " das.id, das.descripcion, das.unidad, das.actoadmin, das.laboracademica, l.oidperiodo from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor ";
	
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
	
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteAsesoria.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
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
	 * de traer los datos de la tabla DocenteAsesoria.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
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
	
	/**
	 * Método utilizado para ejecutar la consulta sql en la base de datos que se encargará 
	 * de traer los datos de la tabla DocenteInvestigacion.
	 * @param querycondition La condición que se le pase a la consulta desde la capa de lógica.
	 * @param parameters los parámetros de la consulta
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectInvestigacion(String querycondition, ArrayList<String> parameters) throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		
		// El arreglo de beans que se obtiene comom resultado de la ejecución de consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		
		// Consulta sql que se ejecutará
		String sqlquery = SQL_LOAD_DOCENTEINVESTIGACION + querycondition;
		
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
				docenciaBean.setOid(Integer.parseInt(rset.getString("OID")));
				docenciaBean.setNombreMateria(rset.getString("NOMBREMATERIA"));
				docenciaBean.setGrupo(rset.getString("GRUPO"));
				docenciaBean.setNombrePrograma(rset.getString("NOMBREPROGRAMA"));
				docenciaBean.setPeriodo(rset.getString("OIDPERIODO"));
				
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
				trbinvestBean.setIden(Integer.parseInt(rset.getString("OID")));
				trbinvestBean.setPeriodo(rset.getString("OIDPERIODO"));
				
				results.add(trbinvestBean);	
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
				Investigacion investBean = new Investigacion(); 
				investBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				investBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				investBean.setNombreProyecto(rset.getString("NOMBREPROYECTO"));
				investBean.setFechaIniciacion(rset.getString("FECHAINICIACION"));
				investBean.setFechaFinalizacion(rset.getString("FECHAFINALIZACION"));
				investBean.setOid(Integer.parseInt(rset.getString("OID")));
				investBean.setPeriodo(rset.getString("OIDPERIODO"));
				investBean.setCondigoVRI(rset.getString("CODIGOVRI"));
				results.add(investBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	/**
	 * Método utilizado para crear un Bean de Asesoria a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanAsesoria(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Asesoria asesoriaBean = new Asesoria();
				asesoriaBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				asesoriaBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				asesoriaBean.setDescripcion(rset.getString("DESCRIPCION"));
				asesoriaBean.setUnidad(rset.getString("UNIDAD"));
				asesoriaBean.setActoAdmin(rset.getString("ACTOADMIN"));
				asesoriaBean.setIden(Integer.parseInt(rset.getString("ID")));
				asesoriaBean.setPeriodo(rset.getString("OIDPERIODO"));
				results.add(asesoriaBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * Método utilizado para crear un Bean de Administracion a partir de la informaciòn que se obtiene
	 * en el resulset, al ejecutar la consulta en la base de datos.
	 * @param rset resulset con los resultados de la ejecución de la consulta sql.
	 * @return Retorna el arreglo de bean Docente con la informaón que se obtuvo de la base de datos.
	 * En caso de que no se obtenga información se retorna el arreglo de resultados vacío.
	 */
	public ArrayList<Bean> loadBeanAdministracion(ResultSet rset) {
		
		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Administracion administracionBean = new Administracion();
				administracionBean.setIdentificacion(rset.getString("IDENTIFICACION"));
				administracionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				administracionBean.setDescripcion(rset.getString("DESCRIPCION"));
				administracionBean.setUnidad(rset.getString("UNIDAD"));
				administracionBean.setActoAdmin(rset.getString("ACTOADMIN"));
				administracionBean.setIden(Integer.parseInt(rset.getString("ID")));
				administracionBean.setPeriodo(rset.getString("OIDPERIODO"));
				results.add(administracionBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public void ingresarCalificacionDoc(ArrayList<String> parameters) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO autoevaluaciondocencia (OIDDOCENTEDOCENCIA, OIDDOCENTES, PERIODO, CALIFICACION) values ("+parameters.get(0)+","+parameters.get(1)+","+parameters.get(2)+","+parameters.get(3)+")";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();		
	}

	public void ingresarCalificacionInvest(ArrayList<String> parameters) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO autoevaluacioninvest (OIDDOCENTEINVEST, OIDDOCENTES, PERIODO, CALIFICACION) values ("+parameters.get(0)+","+parameters.get(1)+","+parameters.get(2)+","+parameters.get(3)+")";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();		
	}

	public void ingresarCalificacionAsesoria(ArrayList<String> parameters) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO autoevaluacionase (OIDDOCENTEASE, OIDDOCENTES, PERIODO, CALIFICACION) values ("+parameters.get(0)+","+parameters.get(1)+","+parameters.get(2)+","+parameters.get(3)+")";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();	
	}

	public void ingresarCalificacionTrabajosInvestigacion(ArrayList<String> parameters) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO autoevaluaciontinvest (OIDDOCENTETINVEST, OIDDOCENTES, PERIODO, CALIFICACION) values ("+parameters.get(0)+","+parameters.get(1)+","+parameters.get(2)+","+parameters.get(3)+")";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();	
	}

	public void ingresarCalificacionAdministracion(ArrayList<String> parameters) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO autoevaluacionadmin (OIDDOCENTEADMIN, OIDDOCENTES, PERIODO, CALIFICACION) values ("+parameters.get(0)+","+parameters.get(1)+","+parameters.get(2)+","+parameters.get(3)+")";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();	
	}

	
	//------------------------------------------------------------//
	public void ingresarCalificacionDocencias(ArrayList<Integer> oiddocencia, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oiddocencia.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "INSERT INTO autoevaluaciondocencia  (OIDDOCENTEDOCENCIA, OIDDOCENTES, PERIODO, CALIFICACION) values ("+oiddocencia.get(i)+",'"+identificacion+"',"+periodo+","+calificaciones.get(i)+")";
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public void actualizarCalificacionDocencia(ArrayList<Integer> oiddocencia, String identificacion, String periodo, 	ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oiddocencia.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "UPDATE autoevaluaciondocencia SET CALIFICACION="+calificaciones.get(i)+" WHERE  OIDDOCENTES='"+identificacion+"' AND PERIODO="+periodo+" AND OIDDOCENTEDOCENCIA="+oiddocencia.get(i);
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public void ingresarCalificacionInvestigacion(ArrayList<Integer> oidinvestigacion, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidinvestigacion.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "INSERT INTO autoevaluacioninvest  (OIDDOCENTEINVEST, OIDDOCENTES, PERIODO, CALIFICACION) values ("+oidinvestigacion.get(i)+",'"+identificacion+"',"+periodo+","+calificaciones.get(i)+")";
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}
	
	public void actualizarCalificacionInvestigacion(ArrayList<Integer> oidinvestigacion, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidinvestigacion.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "UPDATE autoevaluacioninvest SET CALIFICACION="+calificaciones.get(i)+" WHERE  OIDDOCENTES='"+identificacion+"' AND PERIODO="+periodo+" AND OIDDOCENTEDOCENCIA="+oidinvestigacion.get(i);
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public void ingresarCalificacionAsesorias(ArrayList<Integer> oidasesoria, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidasesoria.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "INSERT INTO autoevaluacionase (OIDDOCENTEASE, OIDDOCENTES, PERIODO, CALIFICACION) values ("+oidasesoria.get(i)+",'"+identificacion+"',"+periodo+","+calificaciones.get(i)+")";
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}
	
	public void actualizarCalificacionAsesoria(ArrayList<Integer> oidasesoria, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidasesoria.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "UPDATE autoevaluacionase SET CALIFICACION="+calificaciones.get(i)+" WHERE  OIDDOCENTES='"+identificacion+"' AND PERIODO="+periodo+" AND OIDDOCENTEDOCENCIA="+oidasesoria.get(i);
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public void ingresarCalificacionTrabajoInvestigacion(ArrayList<Integer> oidtrbinves, String identificacion, 	String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidtrbinves.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "INSERT INTO autoevaluaciontinvest (OIDDOCENTETINVEST, OIDDOCENTES, PERIODO, CALIFICACION) values ("+oidtrbinves.get(i)+",'"+identificacion+"',"+periodo+","+calificaciones.get(i)+")";
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}
	
	public void actualizarCalificacionTrabajoInvestigacion(ArrayList<Integer> oidtrbinves, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidtrbinves.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "UPDATE autoevaluaciontinvest SET CALIFICACION="+calificaciones.get(i)+" WHERE  OIDDOCENTES='"+identificacion+"' AND PERIODO="+periodo+" AND OIDDOCENTEDOCENCIA="+oidtrbinves.get(i);
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public void ingresarCalificacionAdmministraciones(	ArrayList<Integer> oidadministracion, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidadministracion.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "INSERT INTO autoevaluacionadmin (OIDDOCENTEADMIN, OIDDOCENTES, PERIODO, CALIFICACION) values ("+oidadministracion.get(i)+",'"+identificacion+"',"+periodo+","+calificaciones.get(i)+")";
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}
	
	public void actualizarCalificacionAdmministraciones(ArrayList<Integer> oidadministracion, String identificacion, String periodo, ArrayList<Integer> calificaciones) throws SQLException {
		// TODO Auto-generated method stub
		for(int i = 0; i < oidadministracion.size(); i++) {
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			ResultSet r1 = null;
			String sqlquery = "UPDATE autoevaluacionadmin SET CALIFICACION="+calificaciones.get(i)+" WHERE  OIDDOCENTES='"+identificacion+"' AND PERIODO="+periodo+" AND OIDDOCENTEDOCENCIA="+oidadministracion.get(i);
			System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
			r1 = conexionObj.executeQueryRSET(sqlquery);
			r1.close();
		}
	}

	public ArrayList<Bean> obtenerObsDocencia(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM observaciondocencia WHERE oiddocente=? AND periodo=?";
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery, parameters);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}
	
	private ArrayList<Bean> loadBeanObservacion(ResultSet r1) {
		// TODO Auto-generated method stub
		ArrayList<Bean> result = new ArrayList<Bean>();
		try{
			while(r1.next()){
				Auxiliar auxObsBean = new Auxiliar();
				auxObsBean.setCantidad(Integer.parseInt(r1.getString("COUNT(*)")));
				result.add(auxObsBean);
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	public void insertarObservacionDocencia(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO observaciondocencia (OIDDOCENTE, PERIODO, OBSERVACION) values ("+identificacion+","+periodo+",'"+obs+"')";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}

	public ArrayList<Bean> obtenerObsAdmin(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM observacionadmin WHERE oiddocente=? AND periodo=?";
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery, parameters);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public void insertarObservacionAdmin(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO observacionadmin (OIDDOCENTE, PERIODO, OBSERVACION) values ("+identificacion+","+periodo+",'"+obs+"')";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public ArrayList<Bean> obtenerObsAsesoria(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM observacionasesoria WHERE oiddocente=? AND periodo=?";
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery, parameters);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public void insertarObservacionAsesoria(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO observacionasesoria (OIDDOCENTE, PERIODO, OBSERVACION) values ("+identificacion+","+periodo+",'"+obs+"')";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public ArrayList<Bean> obtenerObsInvestigacion(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM observacioninvestigacion WHERE oiddocente=? AND periodo=?";
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery, parameters);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public void insertarObservacionInvestigacion(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO observacioninvestigacion (OIDDOCENTE, PERIODO, OBSERVACION) values ("+identificacion+","+periodo+",'"+obs+"')";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public ArrayList<Bean> obtenerObsTrbInvestigacion(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM observaciontinvest WHERE oiddocente=? AND periodo=?";
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery, parameters);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public void insertarObservacionTrbInvestigacion(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "INSERT INTO observaciontinvest (OIDDOCENTE, PERIODO, OBSERVACION) values ("+identificacion+","+periodo+",'"+obs+"')";
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}

	public ArrayList<Bean> obtenerAutoevaluacionDocencia(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM autoevaluaciondocencia WHERE oiddocentes=(SELECT IDENTIFICACION FROM docentes WHERE OID="+identificacion+" OR IDENTIFICACION="+identificacion+") AND periodo="+periodo;
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public ArrayList<Bean> obtenerAutoevaluacionInvestigacion(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM autoevaluacioninvest WHERE oiddocentes=(SELECT IDENTIFICACION FROM docentes WHERE OID="+identificacion+" OR IDENTIFICACION="+identificacion+") AND periodo="+periodo;
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public ArrayList<Bean> obtenerAutoevaluacionAdministracion(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM autoevaluacionadmin WHERE oiddocentes=(SELECT IDENTIFICACION FROM docentes WHERE OID="+identificacion+" OR IDENTIFICACION="+identificacion+") AND periodo="+periodo;
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public ArrayList<Bean> obtenerAutoevaluacionAsesoria(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM autoevaluacionase WHERE oiddocentes=(SELECT IDENTIFICACION FROM docentes WHERE OID="+identificacion+" OR IDENTIFICACION="+identificacion+") AND periodo="+periodo;
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public ArrayList<Bean> obtenerAutoevaluacionTrabajosInvestigacion(String periodo, String identificacion) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> result = new ArrayList<Bean>();
		ResultSet r1 = null;
		String sqlquery = "SELECT count(*) FROM autoevaluaciontinvest WHERE oiddocentes='(SELECT IDENTIFICACION FROM docentes WHERE OID="+identificacion+" OR IDENTIFICACION="+identificacion+")' AND periodo="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add(identificacion);
		parameters.add(periodo);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		result = loadBeanObservacion(r1);
		r1.close();
		return result;
	}

	public void actualizarObsDocencia(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "UPDATE observaciondocencia SET OBSERVACION="+obs+" WHERE OIDDOCENTE="+identificacion+" AND PERIODO="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public void actualizarObsAdministracion(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "UPDATE observacionadmin SET OBSERVACION="+obs+" WHERE OIDDOCENTE="+identificacion+" AND PERIODO="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}

	public void actualizarObsAsesoria(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "UPDATE observacionasesoria SET OBSERVACION="+obs+" WHERE OIDDOCENTE="+identificacion+" AND PERIODO="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public void actualizarObsInvestigacion(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "UPDATE observacioninvestigacion SET OBSERVACION="+obs+" WHERE OIDDOCENTE="+identificacion+" AND PERIODO="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
	
	public void actualizarObsTrbInvestigacion(String periodo, String identificacion, String obs) throws SQLException {
		// TODO Auto-generated method stub
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet r1 = null;
		String sqlquery = "UPDATE observaciontinvest SET OBSERVACION="+obs+" WHERE OIDDOCENTE="+identificacion+" AND PERIODO="+periodo;
		System.out.println("SQL INSERT CALIFICACION= " +sqlquery);
		r1 = conexionObj.executeQueryRSET(sqlquery);
		r1.close();
	}
//metodo para obtener la cantidad de autoevaluaciones realizadas por un docente en un periodo determinado 
	public Collection<Auxiliar> cantidadAutoevaluacion(String id, Integer periodo, String departamento, String nombreTabla) throws NumberFormatException, SQLException{
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		int cant = 0;
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select count(*) as resultado FROM "+nombreTabla+"  WHERE oiddocentes = '"+id+"' and periodo = "+periodo;
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
		
		// TODO Auto-generated method stub
		return auxiliar;
	}
	//metodo iterador
	

	/*Metodos Reportes*/
	public ArrayList<Auxiliar> sumaAutoevaluacion(String id, int periodo, String nombreTabla) throws SQLException {
		ArrayList<Bean> results = new ArrayList<Bean>();
		ArrayList<Auxiliar> auxiliar = new ArrayList<Auxiliar>();
		ArrayList<Auxiliar> aux = new ArrayList<Auxiliar>();
		aux.addAll(this.cantidadAutoevaluaciones(id, periodo, nombreTabla));
		if(aux.get(0).getCantidad() > 0){
			Conexion conexionObj = new Conexion("EvalDocenteDS");
			String sqlquery = "select sum(calificacion) as resultado from "+nombreTabla+" where oiddocentes = '"+id+"' and periodo = "+periodo;
			ResultSet rset = null;
			rset = conexionObj.executeQueryRSET(sqlquery);
			results = loadBeanAuxiliar(rset);			
			try {
				for(Bean iteratorBean : results) {
					auxiliar.add((Auxiliar) iteratorBean);
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			rset.close();
			conexionObj.close();
		} else {
			Auxiliar a = new Auxiliar();
			a.setCantidad(0);
			auxiliar.add(a);
		}
		
		
		
		return auxiliar;
	}

	private ArrayList<Bean> loadBeanAuxiliar(ResultSet rset) {
		ArrayList<Bean> results=new ArrayList<Bean>();
		try{
			while (rset.next()) {
				Auxiliar auxiliarBean = new Auxiliar();
				auxiliarBean.setCantidad(Integer.parseInt(rset.getString("resultado")));
				results.add(auxiliarBean);	
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<Auxiliar> cantidadAutoevaluaciones(String id, int periodo, String nombreTabla) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select count(*) as resultado from "+nombreTabla+" where oiddocentes = '"+id+"' and periodo = "+periodo;
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

}
