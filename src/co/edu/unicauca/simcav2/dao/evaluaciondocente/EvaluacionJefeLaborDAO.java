package co.edu.unicauca.simcav2.dao.evaluaciondocente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import co.edu.unicauca.simcav2.conexion.Conexion;
import co.edu.unicauca.simcav2.modelo.Bean;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.*;

/**
 * Clase de acceso a datos para la gestion de la Docencia Directa de la labor
 * académica
 * 
 * @version 2.0 27 Oct 2015
 * @author Grupo2 Proyecto II
 * 
 */
public class EvaluacionJefeLaborDAO {

	private static final String SELECT_COMUN = "select d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE ";
	private static final String SQL_LOAD_DOCENTEINVESTIGACION = SELECT_COMUN
			+ " di.nombreproyecto, di.fechainiciacion, di.fechafinalizacion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEADMINISTRACION = SELECT_COMUN
			+ " dad.descripcion, dad.unidad, dad.actoadmin from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteadministracion dad on dad.laboracademica = l.oidlabor ";
	private static final String SQL_LOAD_DOCENTEASESORIA = SELECT_COMUN
			+ "from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor ";

	public ArrayList<Bean> docentesIntegrantesAsesoria(String querycondition,
			ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = SQL_LOAD_DOCENTEASESORIA + querycondition;
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery, parameters);
		// Se carga y organiza el arreglo de Beans con la información del
		// resulset.
		results = loadBeanAsesoria(rset, 0);
		rset.close();
		conexionObj.close();
		return results;
	}

	public ArrayList<Bean> materiasAsesoria(String querycondition,
			ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select COD_LABOR_ASE as ACTIVIDAD from JEFEDELABOR_ASE "
				+ querycondition;
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery, parameters);
		// Se carga y organiza el arreglo de Beans con la información del
		// resulset.
		results = loadBeanAsesoria(rset, 0);
		rset.close();
		conexionObj.close();
		return results;
	}

	public ArrayList<Bean> materiasInvestigacion(String querycondition,
			ArrayList<String> parameters) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ArrayList<Bean> results = new ArrayList<Bean>();
		String sqlquery = "select COD_LABOR_INV as CODIGOVRI from JEFEDELABOR_INV "
				+ querycondition;
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery, parameters);
		// Se carga y organiza el arreglo de Beans con la información del
		// resulset.
		results = loadBeanInvestigacion(rset, 0);
		rset.close();
		conexionObj.close();
		return results;
	}

	public ArrayList<Bean> loadBeanAsesoria(ResultSet rset, int opc) {
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			while (rset.next()) {
				Asesoria asesoriaBean = new Asesoria();
				asesoriaBean.setActividad(rset.getString("ACTIVIDAD"));
				if (opc == 1)
					asesoriaBean.setNombresDocente(rset
							.getString("NOMBREDOCENTE"));
				results.add(asesoriaBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<Bean> loadBeanInvestigacion(ResultSet rset, int opc) {

		// Arreglo de resultados (beans de tipo Docencia)
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			while (rset.next()) {
				Investigacion investigacionBean = new Investigacion();
				investigacionBean.setCondigoVRI(rset.getString("CODIGOVRI"));
				if (opc == 1)
					investigacionBean.setNombresDocente(rset
							.getString("NOMBREDOCENTE"));
				results.add(investigacionBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public ArrayList<String> listaPreguntas(String opc) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select pregunta from evaluacionDocente.pregunta where id_cuestionario = '"
				+ opc + "'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		ArrayList<String> results = new ArrayList<String>();
		while (rset2.next()) {
			results.add(rset2.getString("pregunta"));
		}
		rset2.close();
		conexionObj.close();

		return results;
	}

	public String nomUsuario(String id) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select d.identificacion, d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE from docentes d where d.identificacion='"
				+ id + "'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		String result = "";
		while (rset2.next()) {
			result = (rset2.getString("NOMBREDOCENTE"));
		}
		rset2.close();
		conexionObj.close();

		return result;
	}

	public void insertCuestionario(ArrayList<String> parameters,
			ArrayList<Integer> calificaciones) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		int maxId = 0;
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		String sysdate = "sysdate";

		String sqlquery1 = "SELECT MAX(ID_CUESTIONARIO) AS ID FROM CUESTIONARIO";
		System.out.println("SQL QUERY: " + sqlquery1);
		rset1 = conexionObj.executeQueryRSET(sqlquery1);
		while (rset1.next()) {
			if (rset1.getString("id") != null)
				maxId = Integer.parseInt(rset1.getString("id"));
		}
		rset1.close();
		maxId++;

		String sqlquery2 = "INSERT INTO evaluaciondocente.CUESTIONARIO(ID_CUESTIONARIO,NOMBRE_CUESTIONARIO,NOMBRE_DE_LO_QUE_SEEVALUA,NOMBRE_EVALUADO,NOMBRE_EVALUADOR,PERIODO_EVALUADO,FECHA_EVALUACION,ID_TIPO_CUESTIONARIO)  values ('"
				+ maxId
				+ "','cuestionario_"
				+ maxId
				+ "','"
				+ parameters.get(1)
				+ "','"
				+ parameters.get(3)
				+ "','"
				+ parameters.get(2)
				+ "','"
				+ parameters.get(4)
				+ "',"
				+ sysdate + ",'" + parameters.get(0) + "')";
		System.out.println("SQL QUERY: " + sqlquery2);

		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		rset2.close();
		String tabla = "";

		if (parameters.get(0).equalsIgnoreCase("1"))
			tabla = "INT_COMITES";
		else
			tabla = "PRO_INVES";

		insertResultados(maxId, calificaciones, tabla);
		conexionObj.close();

	}

	public void insertResultados(int cuestionario,
			ArrayList<Integer> calificaciones, String tabla)
			throws SQLException {
		int maxIdPregunta = 0;
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		ResultSet rset1 = null;
		String sqlquery1 = "SELECT MAX(ID_VALOR_PREGUNTA) AS ID FROM VALOR_PREGUNTA_"
				+ tabla;
		System.out.println("SQL QUERY: " + sqlquery1);
		rset1 = conexionObj.executeQueryRSET(sqlquery1);
		while (rset1.next()) {
			if (rset1.getString("id") != null)
				maxIdPregunta = Integer.parseInt(rset1.getString("id"));
		}
		rset1.close();
		maxIdPregunta++;
		int contador = maxIdPregunta;
		int pos = 0;
		for (int i = 0; i < calificaciones.size(); i++) {
			Conexion conexionObj1 = new Conexion("EvalDocenteDS");
			ResultSet rset2 = null;
			contador++;
			if (tabla.equalsIgnoreCase("INT_COMITES"))
				pos = i + 1;
			else if (tabla.equalsIgnoreCase("PRO_INVES"))
				pos = i + 10;
			String sqlquery2 = "INSERT INTO VALOR_PREGUNTA_"
					+ tabla
					+ "(ID_VALOR_PREGUNTA,ID_CUESTIONARIO,ID_PREGUNTA,VALOR_PREGUNTA) VALUES('"
					+ contador + "','" + cuestionario + "','" + pos + "','"
					+ calificaciones.get(i) + "')";
			System.out.println("SQL QUERY: " + sqlquery2);
			rset2 = conexionObj1.executeQueryRSET(sqlquery2);
			rset2.close();
			conexionObj1.close();
		}

		conexionObj.close();
	}

	public ArrayList<String> docAsesoriaEvaluado(String actividad)
			throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery = "select das.descripcion from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteasesoria das on das.laboracademica = l.oidlabor WHERE das.actividad='"
				+ actividad + "'";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		ArrayList<String> results = new ArrayList<String>();
		while (rset.next()) {
			results.add(rset.getString("descripcion"));
		}
		rset.close();
		conexionObj.close();

		Conexion conexionObj2 = new Conexion("EvalDocenteDS");
		ResultSet rset2 = null;
		String sqlquery2 = "select NOMBRE_EVALUADO from cuestionario where NOMBRE_DE_LO_QUE_SEEVALUA = '"
				+ results.get(0) + "'";
		System.out.println("SQL QUERY: " + sqlquery2);
		rset2 = conexionObj2.executeQueryRSET(sqlquery2);
		ArrayList<String> results2 = new ArrayList<String>();
		while (rset2.next()) {
			results2.add(rset2.getString("NOMBRE_EVALUADO"));
		}
		rset2.close();
		conexionObj2.close();
		return results2;
	}

	public ArrayList<String> docInvestigacionEvaluado(String codigoVRI)
			throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery = "select di.nombreproyecto from docentes d join laboracademica l on d.identificacion=l.identificacion join docenteinvestigacion di on di.laboracademica = l.oidlabor WHERE di.codigoVRI='"
				+ codigoVRI + "'";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		ArrayList<String> results = new ArrayList<String>();
		while (rset.next()) {
			results.add(rset.getString("nombreproyecto"));
		}
		rset.close();
		conexionObj.close();

		Conexion conexionObj2 = new Conexion("EvalDocenteDS");
		ResultSet rset2 = null;
		String sqlquery2 = "select NOMBRE_EVALUADO from cuestionario where NOMBRE_DE_LO_QUE_SEEVALUA = '"
				+ results.get(0) + "'";
		System.out.println("SQL QUERY: " + sqlquery2);
		rset2 = conexionObj2.executeQueryRSET(sqlquery2);
		ArrayList<String> results2 = new ArrayList<String>();
		while (rset2.next()) {
			results2.add(rset2.getString("NOMBRE_EVALUADO"));
		}
		rset2.close();
		conexionObj2.close();
		return results2;
	}
	
	//Extraer lista de usuarios que pueden iniciar sesion
	public ArrayList<Usuario> inicioUsuario() throws SQLException {
		ArrayList<Usuario> result = new ArrayList<Usuario>();
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select * from inicio";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		
		try{
			while (rset2.next()) {
				Usuario u = new Usuario();
				u.setUsuario(rset2.getString("USUARIO"));
				u.setPass(rset2.getString("CONTRASENIA"));
				u.setPrivilegio(Integer.parseInt(rset2.getString("OID")));
				u.setId(rset2.getString("OIDOCENTE"));
				result.add(u);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		rset2.close();
		conexionObj.close();

		return result;
	}
	
	//Funcionalidad para extraer datos notificacion de la base de datos
	public int notificarDAO(String oid) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery2 = "select * from notificacion n where n.oid='"
				+ oid + "'";
		System.out.println("SQL QUERY: " + sqlquery2);
		ResultSet rset2 = null;
		rset2 = conexionObj.executeQueryRSET(sqlquery2);
		
		int result = -1;
		try{
			rset2 = conexionObj.executeQueryRSET(sqlquery2);
			
			if ( rset2.next() )
			{
				System.out.println("Hay algo");
				result = 1;
			}
			else
			{
				System.out.println("No hay nada");
				result = 0;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		rset2.close();
		conexionObj.close();

		return result;
	}
	
	public ArrayList<Bean> jefesInvestigacion() throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		// El arreglo de beans que se obtiene comom resultado de la ejecución de
		// consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Consulta sql que se ejecutará
		String sqlquery = "SELECT DISTINCT (di.codigovri), d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE "
				+ "from jefedelabor_inv lfinv inner join docentes d on lfinv.id_jefe_inv = d.identificacion inner join docenteinvestigacion di on lfinv.cod_labor_inv = di.codigovri";

		System.out.println("SQL QUERY: " + sqlquery);
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanInvestigacion(rset, 1);

		rset.close();
		conexionObj.close();
		return results;
	}

	public ArrayList<Bean> jefesAsesoria() throws SQLException {
		// se crea el objeto de la conexion
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		// El arreglo de beans que se obtiene comom resultado de la ejecución de
		// consulta
		ArrayList<Bean> results = new ArrayList<Bean>();
		// Consulta sql que se ejecutará
		String sqlquery = "SELECT DISTINCT (da.actividad) ,d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE "
				+ "FROM jefedelabor_ase lfase join docentes d on lfase.id_jefe_ase = d.identificacion inner join docenteasesoria da on lfase.cod_labor_ase = da.actividad";

		System.out.println("SQL QUERY: " + sqlquery);
		// Resulset con los resultados de la ejecución de consulta
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		results = loadBeanAsesoria(rset, 1);

		rset.close();
		conexionObj.close();
		return results;
	}

	public ArrayList<String> decentesEliminar(String evaluador)
			throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery = "select nombre_evaluado from cuestionario where nombre_evaluador = '"
				+ evaluador + "'";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		ArrayList<String> results = new ArrayList<String>();
		while (rset.next()) {
			results.add(rset.getString("nombre_evaluado"));
		}
		rset.close();
		conexionObj.close();

		return results;
	}

	public ArrayList<String> jefesEliminar(String labor) throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery = "select nombre_evaluado from cuestionario where NOMBRE_DE_LO_QUE_SEEVALUA = '"
				+ labor + "'";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		ArrayList<String> results = new ArrayList<String>();
		while (rset.next()) {
			results.add(rset.getString("nombre_evaluado"));
		}
		rset.close();
		conexionObj.close();

		return results;
	}

	public ArrayList<Bean> jefesDepto() throws SQLException {
		Conexion conexionObj = new Conexion("EvalDocenteDS");
		String sqlquery = " select d.primerapellido || ' ' ||  d.segundoapellido || ' ' ||  d.primernombre || ' ' ||  d.segundonombre NOMBREDOCENTE, da.descripcion "
				+ "from docenteadministracion da inner join laboracademica la on da.laboracademica = la.oidlabor inner join docentes d on d.identificacion = la.identificacion "
				+ "where descripcion LIKE 'JEFE DEPARTAMENTO%'";
		System.out.println("SQL QUERY: " + sqlquery);
		ResultSet rset = null;
		rset = conexionObj.executeQueryRSET(sqlquery);
		ArrayList<Bean> results = new ArrayList<Bean>();
		results = loadBeanAdministracion(rset);
		rset.close();
		conexionObj.close();
		return results;
	}
	
	public ArrayList<Bean> loadBeanAdministracion(ResultSet rset) {
		ArrayList<Bean> results = new ArrayList<Bean>();
		try {
			while (rset.next()) {
				Administracion administracionBean = new Administracion();
				administracionBean.setNombresDocente(rset.getString("NOMBREDOCENTE"));
				administracionBean.setDescripcion(rset.getNString("DESCRIPCION"));
				results.add(administracionBean);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

}