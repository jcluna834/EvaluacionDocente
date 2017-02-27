package co.edu.unicauca.simcav2.control;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import co.edu.unicauca.simcav2.dao.evaluaciondocente.AutoEvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.dao.evaluaciondocente.EvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.logica.UserBO;
import co.edu.unicauca.simcav2.logica.evaluaciondocente.EvaluacionJefeLaborBO;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Auxiliar;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Usuario;

/**
 * Clase que se utiliza para procesar la información del usuario
 *
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

@ManagedBean(name= "user")
@SessionScoped
public class UserControl implements Serializable{

	/**
	 * Serial generado automáticamente
	 */
	private static final long serialVersionUID = 7124876753490038788L;
	private UserBO userBO;
	private boolean login=false;
	
	//Variables para los permisos por usuario
	private boolean jefeDep, asigJefeLabor, cuestionario;
	private boolean jefeLab, realizarEva, decano;
	private boolean docente, autoEva;
	private String notificar;
	private static String idDocente;

	//Creando acceso a BO para acceso a Base de Datos
	EvaluacionJefeLaborBO evaldocBO;
	
	public boolean isDecano() {
		return decano;
	}

	public void setDecano(boolean decano) {
		this.decano = decano;
	}
	
	public boolean isRealizarEva() {
		return realizarEva;
	}

	public void setRealizarEva(boolean realizarEva) {
		this.realizarEva = realizarEva;
	}

	public boolean isAutoEva() {
		return autoEva;
	}

	public void setAutoEva(boolean autoEva) {
		this.autoEva = autoEva;
	}

	public boolean isAsigJefeLabor() {
		return asigJefeLabor;
	}

	public void setAsigJefeLabor(boolean asigJefeLabor) {
		this.asigJefeLabor = asigJefeLabor;
	}

	public boolean isCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(boolean cuestionario) {
		this.cuestionario = cuestionario;
	}

	public boolean isJefeDep() {
		return jefeDep;
	}

	public void setJefeDep(boolean jefeDep) {
		this.jefeDep = jefeDep;
	}

	public boolean isJefeLab() {
		return jefeLab;
	}

	public void setJefeLab(boolean jefeLab) {
		this.jefeLab = jefeLab;
	}

	public boolean isDocente() {
		return docente;
	}

	public void setDocente(boolean docente) {
		this.docente = docente;
	}

	//Variables para el inicio de sesion
	private ArrayList<Usuario> usuarios;
	private Usuario us;
	private String rutaDos;

	//Get Y Set
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNotificar() {
		return notificar;
	}

	public void setNotificar(String notificar) {
		this.notificar = notificar;
	}
	
	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}
		
	public String getRutaDos() {
		return rutaDos;
	}

	public void setRutaDos(String rutaDos) {
		this.rutaDos = rutaDos;
	}
	
	// constructor por defecto
		public UserControl() {
			evaldocBO = new EvaluacionJefeLaborBO();
			//inicializo las variables para inicio de sesion
			usuarios = new ArrayList<Usuario>();
			
			//cargar bd para iniciar sesion
			setUsuarios(this.evaldocBO.cargarInicioSesion());
				
			us = new Usuario();
			
			jefeDep = false;
			asigJefeLabor = false;
			cuestionario = false;
			jefeLab = false;
			realizarEva = false;
			docente = false;
			autoEva = false;
			decano = false;
			notificar = "display:none";
			//notificar = "display:run-in";
		}
	
	/**
	 * Método utilizado para validar el acceso al sistema
	 * @return La ruta a la que se va a redireccionar, según el resultado de la validación en el sistema.
	 */
	public String acceder() {
		String ruta = "";
		userBO = new UserBO();
		
		FacesContext context = FacesContext.getCurrentInstance();        
		int validationResult = userBO.validateUser();		
		switch (validationResult)
		{
			case 1: //Si retorna 1, se valida el acceso correctamente.
				this.setLogin(true);
				ruta = "main?faces-redirect=true";				
				break;
			case 2: //Si retorna 2, hay problemas con la información de acceso.
				context.addMessage(null, new FacesMessage("Error", "Nombre de usuario o contraseña erróneos!"));
				break;
			default: //Valores diferentes, no se puede acceder por otras razones.
				System.out.println("UserControl.java - Error de acceso - No se puede acceder al sistema, verifique su información!");
				//context.addMessage(null, new FacesMessage("Error", "Error del servidor, intente más tarde..."));
				break;
		}
		return ruta;
	}
	
	//Controlar permisos de vista 
		public void permisos(int i)
		{
			//Permisos para decano
			if(i == 1)
			{
				jefeDep = false;
				asigJefeLabor = false;
				cuestionario = false;
				jefeLab = false;
				realizarEva = false;
				docente = false;
				autoEva = false;
				decano = true;
				System.out.println("decano");
			}
			if(i == 2)
			{
				jefeDep = true;
				asigJefeLabor = true;
				cuestionario = false;
				jefeLab = true;
				realizarEva = true;
				docente = true;
				autoEva = true;
				decano = false;
				System.out.println("jefe departamento");
			}
			if(i == 3)
			{
				jefeDep = false;
				asigJefeLabor = false;
				cuestionario = false;
				jefeLab = true;
				realizarEva = true;
				docente = true;
				autoEva = true;
				decano = false;
				System.out.println("jefe labor");
			}
			if(i == 4)
			{
				jefeDep = false;
				asigJefeLabor = false;
				cuestionario = false;
				jefeLab = false;
				realizarEva = false;
				docente = true;
				autoEva = true;
				decano = false;
				System.out.println("docente");
			}
			
			
		}
	
	//Mensaje de advertencia
	public void validacion() {
		if(us.getUsuario() == "" && us.getPass() == "")
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", "Usuario y Contraseña requerida."));
		if(us.getUsuario() == "" && us.getPass() != "")
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", "Usuario requerido."));
		if(us.getUsuario() != "" && us.getPass() == "")
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", "Contraseña requerida."));
    }
	
	//Inicando Sesion con mensajes emergentes
	public String iniciarSesion()throws NumberFormatException, SQLException {
			
		String ruta = "index";
		int privilegio = 0;
		String codigo = "";
		System.out.println("Entro al metodo inicio sesion");
			
		boolean user = false, pass = false;
		for(int i=0 ; i<usuarios.size() ; i++)
		{
			if(usuarios.get(i).getUsuario().equals(us.getUsuario())){
				user = true;
				if(usuarios.get(i).getPass().equals(us.getPass())){
					pass = true;
					privilegio = usuarios.get(i).getPrivilegio();

					codigo = usuarios.get(i).getId();

					codigo = usuarios.get(i).getId();

					idDocente = usuarios.get(i).getId();
					break;
				}else{
					pass = false;
					break;
				}
			}else{					
				user = false;
				pass = false;
			}
		}
			
		if(user==true && pass==true)
			ruta = "main?faces-redirect=true";
		else{
			if(user==false && pass==false && us.getUsuario()!="" && us.getPass()!="")
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", "Nombre de usuario o contraseña erroneos."));
			if(user==true && pass==false && us.getUsuario()!="" && us.getPass()!="")
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia!", "Contraseña erronea."));		
		}
			
		//Metodo para proporcionar privilegios al usuario
		permisos(privilegio);
		//Metodo de validacion de notificaciones
		notificar = notificar(codigo);
		System.out.println(us.getPrivilegio());
		return ruta;
			
	}
	
public boolean CantEvaluacionesDocente(String oid) throws NumberFormatException, SQLException {
		ArrayList<String> nombreTabla = new ArrayList<String>();
		nombreTabla.add("docentedocencia");
		nombreTabla.add("docenteasesoria");
		nombreTabla.add("docenteadministracion");
		nombreTabla.add("docenteinvestigacion");
		nombreTabla.add("docentetrabajosinvestigacion");
		ArrayList<String> nombreTabla2 = new ArrayList<String>();
		nombreTabla2.add("autoevaluaciondocencia");
		nombreTabla2.add("autoevaluacionase");
		nombreTabla2.add("autoevaluacionadmin");
		nombreTabla2.add("autoevaluacioninvest");
		nombreTabla2.add("autoevaluaciontinvest");
		int canti=0;
		EvaluacionDocenteDAO objDAO = new EvaluacionDocenteDAO();
		AutoEvaluacionDocenteDAO autoEDAO = new AutoEvaluacionDocenteDAO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		//Sacar un vector con el nombre de todos los docentes del programa
		ArrayList<Auxiliar> aux = new ArrayList<Auxiliar>();
		//Por cada docente agregar el registro consolidado de autoevaluación y evaluación del jefe de labor
		int cant = 0;
		int cantE = 0;
		int cantEv = 0;
		ArrayList<String> docentes1 = new ArrayList<String>();
		docentes1.addAll(objDAO.obtenerNombreDocente(oid));
		String nombre = docentes1.get(0);
		aux.addAll(objDAO.cantidadEvaluaciones(nombre,periodo));
		cantEv=aux.get(0).getCantidad();
		
		aux.addAll(objDAO.cantidadLabor(oid, periodo, departamento, nombreTabla.get(0)));
		cant = cant + aux.get(0).getCantidad();
		aux.clear();
		//Cuenta la cantidad de labor de asesoria para cada docente 
		aux.addAll(objDAO.cantidadLabor(oid, periodo, departamento, nombreTabla.get(1)));
		cant = cant + aux.get(0).getCantidad();
		canti=canti+cant;
		aux.clear();
		//Cuenta la cantidad de labor de administracion para cada docente
		aux.addAll(objDAO.cantidadLabor(oid, periodo, departamento, nombreTabla.get(2)));
		cant = cant + aux.get(0).getCantidad();
		aux.clear();
		//Cuenta la cantidad de labor de investigacion para cada docente
		aux.addAll(objDAO.cantidadLabor(oid, periodo, departamento, nombreTabla.get(3)));
		cant = cant + aux.get(0).getCantidad();
		canti=canti+cant;
		aux.clear();
		//Cuenta la cantidad de labor de trabajos de investigacion para cada docente
		aux.addAll(objDAO.cantidadLabor(oid, periodo, departamento, nombreTabla.get(4)));
		cant = cant + aux.get(0).getCantidad();
		aux.clear();
		//Obtener la cantidad de evaluaciones realizadas por cada docente
		//obtener las autoevaluaciones realizadas por docencia
		System.out.println("id"+oid);
		aux.addAll(autoEDAO.cantidadAutoevaluacion(oid, periodo, departamento, nombreTabla2.get(0)));
		cantE = cantE + aux.get(0).getCantidad();
		aux.clear();
		//obtener las autoevaluaciones realizadas por asesoria			
		aux.addAll(autoEDAO.cantidadAutoevaluacion(oid, periodo, departamento, nombreTabla2.get(1)));
		cantE = cantE + aux.get(0).getCantidad();
		aux.clear();
		//obtener las autoevaluaciones realizadas por administracion
		aux.addAll(autoEDAO.cantidadAutoevaluacion(oid, periodo, departamento, nombreTabla2.get(2)));
		cantE = cantE + aux.get(0).getCantidad();
		aux.clear();
		//obtener las autoevaluaciones realizadas por investigacion
		aux.addAll(autoEDAO.cantidadAutoevaluacion(oid, periodo, departamento, nombreTabla2.get(3)));
		cantE = cantE + aux.get(0).getCantidad();
		aux.clear();
		//obtener las autoevaluaciones realizadas por trabajos de investigacion
		aux.addAll(autoEDAO.cantidadAutoevaluacion(oid, periodo, departamento, nombreTabla2.get(4)));
		cantE = cantE + aux.get(0).getCantidad();
		aux.clear();
		System.out.println("entro a eliminar"+cant);
		System.out.println("entro a eliminar"+cantE);
		System.out.println("entro a eliminar"+cantEv);
		if(cant==cantE && canti==cantE){
			objDAO.quitarnotificacion(oid);
			System.out.println("entro a eliminar");
			return false;
		}
		return true;
}

	
	//Metodo para mostrar o no el mensaje
	public String notificar(String oid)throws NumberFormatException, SQLException {
		String result = "display:none";
		int r=-1;
		
		r = this.evaldocBO.notificarBO(oid);
		System.out.println("entro");
		if(r==1 && CantEvaluacionesDocente(oid))
			result = "display:run-in";
		else
			result = "display:none";
		return result;
	}
	
	//Al momento de cerrar sesion regreso al inicio
	public String cerrarSesion(){
		return "index";
	}
	
	/**
	 * @return the userBO
	 */
	public UserBO getUserBO() {
		return userBO;
	}
	
	/**
	 * @param userBO the userBO to set
	 */
	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}

	/**
	 * @return the login
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getIdDocente() {
		return idDocente;
	}
}
