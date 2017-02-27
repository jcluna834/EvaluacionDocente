package co.edu.unicauca.simcav2.conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.sql.DataSource;

/**
 * Clase de conexión entre la aplicación y la base de datos.
 * 
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */
public class Conexion implements HttpSessionBindingListener {
	private Connection conectionObj = null;
	private Statement stmt = null;
	private PreparedStatement prepstmt = null;
	private ResultSetMetaData rsetmetadata = null;
	private DataSource drvrMngrDataSource;
	private String dataSourceName = "EvalDocenteDS";

	/**
	 * Constructor de la Clase Conexion. 
	 * Se encarga de asignar el DataSource que recibe como parametro.
	 * @param datasourceName Nombre del datasource
	 */
	public Conexion(DataSource datasourceName) {
		drvrMngrDataSource = datasourceName;
	}

	/**
	 * Constructor de la Clase Conexion. 
	 * Se encarga de asignar el nombre del datasource que recibe como parametro.
	 * @param dataSourceName String que se recibe con la información del nombre del datasource
	 */
	public Conexion(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	/**
	 * Función utilizada para iniciar el DataSource.
	 * Se carga el contexto de la aplicacion del usuario.
	 */
	public void loadDataSource() {

		try {
			Context contextObj = (Context) new InitialContext().lookup("java:");
			drvrMngrDataSource = (DataSource) contextObj.lookup("jboss/jdbc/"+ dataSourceName);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Función utilizada para inicializar el Datasource.
	 * @param name Nombre del Datasource que se va a inicializar.
	 */
	public void loadDataSource(String name) {
		try {
			Context objContext = (Context) new InitialContext().lookup("java:");
			drvrMngrDataSource = (DataSource) objContext.lookup(name);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método encargado de ejecutar la sentencia SQL. 
	 * @param sql Contiene la sentencia SQL que se va a ejecutar.
	 * @param parameters Contiene los parámetros de la consulta que se desea ejecutar
	 * @return Retorna el resulset con los datos que se obtienen al ejecutar la consulta en la base de datos.
	 */
	@SuppressWarnings({ "rawtypes" })
	public ResultSet executeQueryRSET(String sql, ArrayList parameters) {
		// Se llama al método encargado de iniciar el datasource
		this.loadDataSource();
		ResultSet rset = null;
		try {
			conectionObj = drvrMngrDataSource.getConnection();
			prepstmt = conectionObj.prepareStatement(sql);
			
			if (parameters.size() > 0)
				for (int i = 0; i < parameters.size(); i++) {
					// Se establece el string de parámetros de la consulta
					prepstmt.setString(i + 1, parameters.get(i).toString());
				}
			// Se ejecuta la consulta SQL
			rset = prepstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rset;
	}

	/**
	 * Método encargado de ejecutar la sentencia SQL. 
	 * @param sql Contiene la sentencia SQL que se va a ejecutar.
	 * @return Retorna el resulset con los datos que se obtienen al ejecutar la consulta en la base de datos.
	 */
	public ResultSet executeQueryRSET(String sql) {
		// Se llama al método encargado de iniciar el datasource
		loadDataSource();
		ResultSet rset = null;
		try {
			conectionObj = drvrMngrDataSource.getConnection();
			stmt = conectionObj.createStatement();
			// Se ejecuta la consulta SQL
			rset = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		return rset;
	}

   /**
    * Se obtiene la conexión. En caso de que no se haya inicializado la conexión,
    * se invoca el método para iniciar el datasource.
    * @return Retorna la conexión a la base de datos.
    */
	public Connection getConexion() {
		try {
			// Si no se ha inicializado la conexión, se invoca el método para iniciar el datasource
			if(this.drvrMngrDataSource==null) this.loadDataSource();
			
			return this.drvrMngrDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Getter para el ResulSetMetaData.
	 * @return retorna el objeto rsetmetadata
	 */
	public ResultSetMetaData getMetaData() {
		return rsetmetadata;
	}

	/**
	 * Método utilizado para cerrar la conexión a la base de datos
	 * Lanza una excepción en caso de que no se pueda cerrar la conexión.
	 */
	public void close() {
		try {
			if(!this.conectionObj.isClosed())this.conectionObj.close();			    
		}catch (NullPointerException e){
			//Si lanza un Error de Null Pointer, quiere decir que la conexión ya fue cerrada previamente.
			System.err.println("Error al tratar de cerrar la conexión, al parecer ya fue cerrada previamente...");
		}		
		catch (SQLException e){
			System.err.println("Error SQL al tratar de cerrar la conexión.");
		}
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
