package co.edu.unicauca.simcav2.control.evaluaciondocente;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SelectEvent;

import co.edu.unicauca.simcav2.control.UserControl;
import co.edu.unicauca.simcav2.dao.evaluaciondocente.AutoEvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.dao.evaluaciondocente.EvaluacionDocenteDAO;
import co.edu.unicauca.simcav2.logica.evaluaciondocente.EvaluacionDocenteBO;
import co.edu.unicauca.simcav2.logica.evaluaciondocente.EvaluacionJefeLaborBO;
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
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.nombreDocentes;
import co.edu.unicauca.simcav2.modelo.evaluaciondocente.reportes;

/**
 * Clase que se utiliza para procesar la información de la evaluacion docente
 * 
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */

/**
 * @author JulioCésar
 *
 */
@ManagedBean(name = "evaluacionDocenteControl")
@SessionScoped
public class EvaluacionDocenteControl implements Serializable {

	/**
	 * Serial generado automáticamente
	 */
	private static final long serialVersionUID = 51643300846314150L;

	/**
	 * Objeto de la capa de lógica de Evaluación Docente.
	 */
	private EvaluacionDocenteBO evaldocBO;

	/**
	 * Ruta URL a la que se redireccionará al usuario.
	 */
	private String ruta;

	/**
	 * Arreglo utilizado para guardar la información de la Docencia asociada a
	 * una labor docente
	 */
	private ArrayList<Docencia> docenteDocenciaData;

	/**
	 * Arreglo utilizado para guardar la información de Trabajos de
	 * investigacion asociada a una labor docente
	 */
	private ArrayList<TrabajosInvestigacion> docenteTrabajosInvestigacionData;

	/**
	 * Arreglo utilizado para guardar la información de Proyectos de
	 * investigacion asociada a una labor docente
	 */
	private ArrayList<Investigacion> docenteInvestigacionData;

	/**
	 * Arreglo utilizado para guardar la información de Administracion asociada
	 * a una labor docente
	 */
	private ArrayList<Administracion> docenteAdministracionData;

	/**
	 * Arreglo utilizado para guardar la información de Asesoría asociada a una
	 * labor docente
	 */
	private ArrayList<Asesoria> docenteAsesoriaData;

	/**
	 * Arreglo utilizado para almacenar información de los Servicio asociados a
	 * una labor docente
	 */
	private ArrayList<Servicio> docenteServicioData;

	/**
	 * Arreglo utilizado para almacenar información de los Extension asociados a
	 * una labor docente
	 */
	private ArrayList<Extension> docenteExtensionData;

	/**
	 * Arreglo utilizado para almacenar información de los Capacitacion
	 * asociados a una labor docente
	 */
	private ArrayList<Capacitacion> docenteCapacitacionData;

	/**
	 * Arreglo utilizado para almacenar información de los Otros asociados a una
	 * labor docente
	 */
	
	private ArrayList<Otros> docenteOtrosData;
	//lista de docentes que no han hecho las evaluaciones
	private ArrayList<String> docentesAux;
	//atributo utilizado para renderizar el mensaje de notificaciones
	private String mensaje;
	
	// constructor por defecto
	public EvaluacionDocenteControl() {
		this.setRuta("");
		this.docenteDocenciaData = new ArrayList<Docencia>();
		this.docenteTrabajosInvestigacionData = new ArrayList<TrabajosInvestigacion>();
		this.docenteInvestigacionData = new ArrayList<Investigacion>();
		this.docenteAdministracionData = new ArrayList<Administracion>();
		this.docenteAsesoriaData = new ArrayList<Asesoria>();
		this.docenteServicioData = new ArrayList<Servicio>();
		this.docenteExtensionData = new ArrayList<Extension>();
		this.docenteCapacitacionData = new ArrayList<Capacitacion>();
		this.docenteOtrosData = new ArrayList<Otros>();
		this.docentesAux=new ArrayList<String>();
		this.setMensaje("display:none");
		
	}

	/**
	 * Método utilizado para validar el acceso al sistema
	 * 
	 * @return La ruta a la que se va a redireccionar, según el resultado de la
	 *         validación en el sistema.
	 */
	public void loadLaborData() {
		// Se limpian los arreglos de información para evitar duplicados
		this.docenteDocenciaData.clear();
		this.docenteTrabajosInvestigacionData.clear();
		this.docenteInvestigacionData.clear();
		this.docenteAdministracionData.clear();
		this.docenteAsesoriaData.clear();
		this.docenteServicioData.clear();
		this.docenteExtensionData.clear();
		this.docenteCapacitacionData.clear();
		this.docenteOtrosData.clear();
		this.docentesAux.clear();

		// Se crea el objeto de la capa de lógica
		evaldocBO = new EvaluacionDocenteBO();

		// FacesContext context = FacesContext.getCurrentInstance();
		// En esta parte se debe obtener el departamento asociado al jefe de
		// departamento que va a realizar la
		// asignación de evaluaciñon docente. Para este ejemplo se va a suponer
		// que ya se conoce...

		String departamento = "1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2

		// Realiza la consulta y asigna los resultados a los arreglos con la
		// información de laboracademica
		this.setDocenteDocenciaData(this.evaldocBO.loadDocenteDocencia(
				departamento, periodo));
		this.setDocenteTrabajosInvestigacionData(this.evaldocBO
				.loadDocenteTrabajosInvestigacion(departamento, periodo));
		this.setDocenteInvestigacionData(this.evaldocBO
				.loadDocenteInvestigacion(departamento, periodo));
		this.setDocenteAdministracionData(this.evaldocBO
				.loadDocenteAdministracion(departamento, periodo));
		this.setDocenteAsesoriaData(this.evaldocBO.loadDocenteAsesoria(
				departamento, periodo));
		this.setDocenteServicioData(this.evaldocBO.loadDocenteServicio(
				departamento, periodo));
		this.setDocenteExtensionData(this.evaldocBO.loadDocenteExtension(
				departamento, periodo));
		this.setDocenteCapacitacionData(this.evaldocBO.loadDocenteCapacitacion(
				departamento, periodo));
		this.setDocenteOtrosData(this.evaldocBO.loadDocenteOtros(departamento,
				periodo));
		// Realiza la consulta y asigna los resultados al arreglo que contiene
		// la informacion de Administración
		// this.setDocenteDocenciaData(this.evaldocBO.loadDocenteDocencia(departamento,
		// periodo));

		// se establece la ruta del archivo de la capa de vista que desplegará
		// la información.
		this.setRuta("/administrativo/evaluacionDocente/evaluacionDocente.xhtml");

	}

	/**
	 * @return the evaldocBO
	 */
	public EvaluacionDocenteBO getEvaldocBO() {
		return evaldocBO;
	}
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @param evaldocBO
	 *            the evaldocBO to set
	 */
	public void setEvaldocBO(EvaluacionDocenteBO evaldocBO) {
		this.evaldocBO = evaldocBO;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta
	 *            the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	/**
	 * @return the docenteDocenciaData
	 */
	public ArrayList<Docencia> getDocenteDocenciaData() {
		return docenteDocenciaData;
	}

	/**
	 * @param docenteDocenciaData
	 *            the docenteDocenciaData to set
	 */
	public void setDocenteDocenciaData(ArrayList<Docencia> docenteDocenciaData) {
		this.docenteDocenciaData = docenteDocenciaData;
	}

	/**
	 * @return the docenteTrabajosInvestigacionData
	 */
	public ArrayList<TrabajosInvestigacion> getDocenteTrabajosInvestigacionData() {
		return docenteTrabajosInvestigacionData;
	}

	/**
	 * @param docenteTrabajosInvestigacionData
	 *            the docenteTrabajosInvestigacionData to set
	 */
	public void setDocenteTrabajosInvestigacionData(
			ArrayList<TrabajosInvestigacion> docenteTrabajosInvestigacionData) {
		this.docenteTrabajosInvestigacionData = docenteTrabajosInvestigacionData;
	}

	/**
	 * @return the docenteInvestigacionData
	 */
	public ArrayList<Investigacion> getDocenteInvestigacionData() {
		return docenteInvestigacionData;
	}

	/**
	 * @param docenteInvestigacionData
	 *            the docenteInvestigacionData to set
	 */
	public void setDocenteInvestigacionData(
			ArrayList<Investigacion> docenteInvestigacionData) {
		this.docenteInvestigacionData = docenteInvestigacionData;
	}

	/**
	 * @return the docenteAdministracionData
	 */
	public ArrayList<Administracion> getDocenteAdministracionData() {
		return docenteAdministracionData;
	}

	/**
	 * @param docenteAdministracionData
	 *            the docenteAdministracionData to set
	 */
	public void setDocenteAdministracionData(
			ArrayList<Administracion> docenteAdministracionData) {
		this.docenteAdministracionData = docenteAdministracionData;
	}

	/**
	 * Objetos creados con el fin de guardar en ellos los datos de un objeto de la vista
	 * se utilizan cuando se tiene un menú dinamico y se es necesario seleccionar un docente
	 * específico en una lista.
	 */
	private Administracion selectAdmon;
	private Asesoria selectAsesoria = new Asesoria();
	private Investigacion selectInvestigacion = new Investigacion();
	private Capacitacion selectCapacitacion;
	private Extension selectExtension;
	private Servicio selectServicio;
	private TrabajosInvestigacion selectTrabInv;
	private Otros selectOtros;
	private Docencia selectDocencia;

	
	public Investigacion getSelectInvestigacion() {
		return selectInvestigacion;
	}

	public void setSelectInvestigacion(Investigacion selectInvestigacion) {
		this.selectInvestigacion = selectInvestigacion;
	}

	public Asesoria getSelectAsesoria() {
		return selectAsesoria;
	}

	public void setSelectAsesoria(Asesoria selectAsesoria) {
		this.selectAsesoria = selectAsesoria;
	}

	public Administracion getSelectAdmon() {
		return selectAdmon;
	}

	public void setSelectAdmon(Administracion selectAdmon) {
		this.selectAdmon = selectAdmon;
	}

	public Capacitacion getSelectCapacitacion() {
		return selectCapacitacion;
	}

	public void setSelectCapacitacion(Capacitacion selectCapacitacion) {
		this.selectCapacitacion = selectCapacitacion;
	}

	public Extension getSelectExtension() {
		return selectExtension;
	}

	public void setSelectExtension(Extension selectExtension) {
		this.selectExtension = selectExtension;
	}

	public Servicio getSelectServicio() {
		return selectServicio;
	}

	public void setSelectServicio(Servicio selectServicio) {
		this.selectServicio = selectServicio;
	}

	public TrabajosInvestigacion getSelectTrabInv() {
		return selectTrabInv;
	}

	public void setSelectTrabInv(TrabajosInvestigacion selectTrabInv) {
		this.selectTrabInv = selectTrabInv;
	}

	public Otros getSelectOtros() {
		return selectOtros;
	}

	public void setSelectOtros(Otros selectOtros) {
		this.selectOtros = selectOtros;
	}

	public Docencia getSelectDocencia() {
		return selectDocencia;
	}

	public void setSelectDocencia(Docencia selectDocencia) {
		this.selectDocencia = selectDocencia;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Admon Selected",
				((Administracion) event.getObject()).getIdentificacion());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Lista de objetos creados para realizar un a busqueda filtrada. 
	 * Se utiliza en "Examinar labor docente"
	 */

	private List<Administracion> filteredAdministracion;
	private List<Asesoria> filteredAsesoria;
	private List<Capacitacion> filteredCapacitacion;
	private List<Docencia> filteredDocencia;
	private List<Extension> filteredExtension;
	private List<Investigacion> filteredInvetigacion;
	private List<Otros> filteredOtros;
	private List<TrabajosInvestigacion> filteredTrabajosInvestigacion;
	private List<Servicio> filteredServicio;

	public List<Administracion> getFilteredAdministracion() {
		return filteredAdministracion;
	}

	public void setFilteredAdministracion(
			List<Administracion> filteredAdministracion) {
		this.filteredAdministracion = filteredAdministracion;
	}

	public List<Asesoria> getFilteredAsesoria() {
		return filteredAsesoria;
	}

	public void setFilteredAsesoria(List<Asesoria> filteredAsesoria) {
		this.filteredAsesoria = filteredAsesoria;
	}

	public List<Capacitacion> getFilteredCapacitacion() {
		return filteredCapacitacion;
	}

	public void setFilteredCapacitacion(List<Capacitacion> filteredCapacitacion) {
		this.filteredCapacitacion = filteredCapacitacion;
	}

	public List<Docencia> getFilteredDocencia() {
		return filteredDocencia;
	}

	public void setFilteredDocencia(List<Docencia> filteredDocencia) {
		this.filteredDocencia = filteredDocencia;
	}

	public List<Extension> getFilteredExtension() {
		return filteredExtension;
	}

	public void setFilteredExtension(List<Extension> filteredExtension) {
		this.filteredExtension = filteredExtension;
	}

	public List<Investigacion> getFilteredInvetigacion() {
		return filteredInvetigacion;
	}

	public void setFilteredInvetigacion(List<Investigacion> filteredInvetigacion) {
		this.filteredInvetigacion = filteredInvetigacion;
	}

	public List<Otros> getFilteredOtros() {
		return filteredOtros;
	}

	public void setFilteredOtros(List<Otros> filteredOtros) {
		this.filteredOtros = filteredOtros;
	}

	public List<TrabajosInvestigacion> getFilteredTrabajosInvestigacion() {
		return filteredTrabajosInvestigacion;
	}

	public void setFilteredTrabajosInvestigacion(
			List<TrabajosInvestigacion> filteredTrabajosInvestigacion) {
		this.filteredTrabajosInvestigacion = filteredTrabajosInvestigacion;
	}

	public List<Servicio> getFilteredServicio() {
		return filteredServicio;
	}

	public void setFilteredServicio(List<Servicio> filteredServicio) {
		this.filteredServicio = filteredServicio;
	}

	/**
	 * @return the docenteAsesoriaData
	 */
	public ArrayList<Asesoria> getDocenteAsesoriaData() {
		return docenteAsesoriaData;
	}

	/**
	 * @param docenteAsesoriaData
	 *            the docenteAsesoriaData to set
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

	public void setDocenteExtensionData(
			ArrayList<Extension> docenteExtensionData) {
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
	
	/**
	 * Arreglo utilizado para cargar los docentes pertenecientes a una labor especifica
	 */
	private ArrayList<String> docentesInv;

	public ArrayList<String> getDocentesInv() {
		return docentesInv;
	}

	public void setDocentesInv(ArrayList<String> docentesInv) {
		this.docentesInv = docentesInv;
	}

	/**
	 * Arreglo utilizado para cargar los docentes pertenecientes a una labor especifica
	 */
	private ArrayList<String> docentesAs;

	public ArrayList<String> getDocentesAs() {
		return docentesAs;
	}

	public void setDocentesAs(ArrayList<String> docentesAs) {
		this.docentesAs = docentesAs;
	}

	String departamento = "1193003"; // Departamento de sistemas
	Integer periodo = 82; // Periodo 2015-2

	/**
	 * Método utilizado para cargar los docentes pertenecientes a una labor específica 
	 * @param codigoVRI
	 * @return
	 */
	public ArrayList<String> listaDocentesLabor(String codigoVRI) {
		evaldocBO = new EvaluacionDocenteBO();
		docentesInv.clear();
		this.setDocentesInv(this.evaldocBO.loadDocentes(codigoVRI,
				departamento, periodo, "investigacion"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarInvestigacion.xhtml");
		return docentesInv;
	}

	/**
	 * Método utilizado para insertar en la BD el jefe de labor de una asesoría específica
	 * @param labor
	 * @param nombreDoc
	 */
	public void insertJefeLabor(String labor, String nombreDoc) {
		if (nombreDoc.isEmpty()) {
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		} else {
			evaldocBO = new EvaluacionDocenteBO();
			int oidInt = Integer.parseInt(labor);
			this.evaldocBO
					.insertJefeLabor(oidInt, nombreDoc, "JEFEDELABOR_ASE");
			this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAsesoria.xhtml");
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		}
	}

	/**
	 * Método utilizado para insertar en la BD el jefe de labor de una administración específica
	 * @param labor
	 * @param nombreDoc
	 */
	public void insertJefeLaborAdmin(String labor, String nombreDoc) {
		if (nombreDoc.isEmpty()) {
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		} else {
			evaldocBO = new EvaluacionDocenteBO();
			int oidInt = Integer.parseInt(labor);
			this.evaldocBO
					.insertJefeLabor(oidInt, nombreDoc, "JEFEDELABOR_ADM");
			this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAdministracion.xhtml");
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		}
	}

	/**
	 * Método utilizado para insertar en la BD el jefe de labor de una investigación específica
	 * @param labor
	 * @param nombreDoc
	 */
	public void insertJefeLaborInves(String labor, String nombreDoc) {
		if (nombreDoc.isEmpty()) {
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		} else {
			evaldocBO = new EvaluacionDocenteBO();
			int oidInt = Integer.parseInt(labor);
			this.evaldocBO
					.insertJefeLabor(oidInt, nombreDoc, "JEFEDELABOR_INV");
			this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarInvestigacion.xhtml");
			System.out.println("Valor combo:" + labor + " - " + nombreDoc);
		}
	}

	/**
	 * Arreglos utilizados para guardar las distintas labores existentes (Investigación, asesoría, administración)
	 */
	private ArrayList<Investigacion> codigoVRI = new ArrayList<Investigacion>();
	private ArrayList<Asesoria> actividadAsesoria = new ArrayList<Asesoria>();
	private ArrayList<Administracion> actividadAdministracion = new ArrayList<Administracion>();

	public ArrayList<Asesoria> getActividadAsesoria() {
		return actividadAsesoria;
	}

	public void setActividadAsesoria(ArrayList<Asesoria> actividadAsesoria) {
		this.actividadAsesoria = actividadAsesoria;
	}

	public ArrayList<Administracion> getActividadAdministracion() {
		return actividadAdministracion;
	}

	public void setActividadAdministracion(
			ArrayList<Administracion> actividadAdministracion) {
		this.actividadAdministracion = actividadAdministracion;
	}

	public ArrayList<Investigacion> getCodigoVRI() {
		return codigoVRI;
	}

	public void setCodigoVRI(ArrayList<Investigacion> codigoVRI) {
		this.codigoVRI = codigoVRI;
	}

	/**
	 * Método utilizado para cargar todas las investigaciones
	 */
	public void cargaVRI() {
		evaldocBO = new EvaluacionDocenteBO();
		this.setCodigoVRI(this.evaldocBO.loadCodigoVRI());
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarInvestigacion.xhtml");
	}
	
	/**
	 * Método utilizado para cargar el nombre de una investigación específica
	 * @param codigoVRI
	 * @return
	 */
	public ArrayList<String> nombreProyecto(String codigoVRI) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, codigoVRI, "investigacion"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarInvestigacion.xhtml");
		return docentesInv;
	}

	/**
	 * Método utilizado para cargar todas las asesorias
	 */
	public void cargaActividadAsesoria() {
		evaldocBO = new EvaluacionDocenteBO();
		this.setActividadAsesoria(this.evaldocBO.loadActividadAsesoria());
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAsesoria.xhtml");
	}

	/**
	 * Método utilizado para cargar los nombres de una asesoría específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> nombreAsesoria(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		docentesInv.clear();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, actividad, "asesoria"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAsesoria.xhtml");
		docentesAs = docentesInv;
		return docentesAs;
	}

	/**
	 * Método utilizado para cargar los profesores pertenecientes a una asesoria específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> listaDocentesAsesoria(String actividad) {
		docentesInv.clear();
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadDocentes(actividad,
				departamento, periodo, "asesoria"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAsesoria.xhtml");
		return docentesInv;
	}

	/**
	 * Método utilizado para cargar todas las administraciones
	 */
	public void cargaActividadAdministracion() {
		evaldocBO = new EvaluacionDocenteBO();
		this.setActividadAdministracion(this.evaldocBO
				.loadActividadAdministracion());
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAdministracion.xhtml");
	}

	/**
	 * Método utilizado para cargar el nombre de una Administración específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> nombreAdministracion(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, actividad, "administracion"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAdministracion.xhtml");
		docentesAs = docentesInv;
		return docentesAs;
	}

	/**
	 * Método utilizado para cargar los profesores pertenecientes a una administración específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> listaDocentesAdministracion(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadDocentes(actividad,
				departamento, periodo, "administracion"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAdministracion.xhtml");
		return docentesInv;
	}

	/**
	 * Atributo utilizado para guardar el profesor que se escoge como jefe de labor
	 */
	private String docenteEscoger = "";

	public String getDocenteEscoger() {
		return docenteEscoger;
	}

	public void setDocenteEscoger(String docenteEscoger) {
		this.docenteEscoger = docenteEscoger;
	}

	public void stringNull() {
		docenteEscoger = "";
	}

	/**
	 * Método utilizado para guardar en la BD el jefe de labor de una asesoría específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> jefeLaborAsesoria(String actividad) {
		docentesInv = null;
		// docentesInv = new ArrayList<String>();
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadJefeLabor(actividad,
				departamento, periodo, "ASE"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAsesoria.xhtml");
		if (docentesInv.isEmpty()) {
			docentesInv.add(("Seleccione jefe labor").toString());
		}
		return docentesInv;
	}

	/**
	 * Método utilizado para guardar en la BD el jefe de labor de una investigación específica
	 * @param codigoVRI
	 * @return
	 */
	public ArrayList<String> jefeLaborInvestigacion(String codigoVRI) {
		docentesInv = null;
		// docentesInv = new ArrayList<String>();
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadJefeLabor(codigoVRI,
				departamento, periodo, "INV"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarInvestigacion.xhtml");
		if (docentesInv.isEmpty()) {
			docentesInv.add(("Seleccione jefe labor").toString());
		}
		return docentesInv;
	}

	/**
	 * Método utilizado para guardar en la BD el jefe de labor de una administración específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> jefeLaborAdministracion(String actividad) {
		docentesInv = null;
		// docentesInv = new ArrayList<String>();
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadJefeLabor(actividad,
				departamento, periodo, "ADM"));
		this.setRuta("/administrativo/evaluacionDocente/asignarLabor/AsignarAdministracion.xhtml");
		if (docentesInv.isEmpty()) {
			docentesInv.add(("Seleccione jefe labor").toString());
		}
		return docentesInv;
	}

	/***
	 * Aquí empieza logíca de evaluación
	 */

	/**
	 * Objeto de la capa de lógica de Evaluación Jefe de labor.
	 */
	private EvaluacionJefeLaborBO evalJefeLaborBO;

	public EvaluacionJefeLaborBO getEvalJefeLaborBO() {
		return evalJefeLaborBO;
	}

	public void setEvalJefeLaborBO(EvaluacionJefeLaborBO evalJefeLaborBO) {
		this.evalJefeLaborBO = evalJefeLaborBO;
	}

	private UserControl usuario = new UserControl();

	public UserControl getUsuario() {
		return usuario;
	}

	public void setUsuario(UserControl usuario) {
		this.usuario = usuario;
	}
	
	private String idJefe = getUsuario().getIdDocente();

	public String getIdJefe() {
		return idJefe;
	}

	public void setIdJefe(String idJefe) {
		this.idJefe = idJefe;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	/**
	 * Método utilizado para cargar las investigaciones asosiadas a un jefe de labor
	 */
	public void cargaGrupoJefeInvestigacion() {
		this.docenteInvestigacionData.clear();
		evalJefeLaborBO = new EvaluacionJefeLaborBO();

		this.setDocenteInvestigacionData(this.evalJefeLaborBO
				.loadMateriasInvestigacion(idJefe));
		// se establece la ruta del archivo de la capa de vista que desplegará
		// la información.
		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteInvestigacion.xhtml");
	}

	/**
	 * Método utilizado para cargar las asesorías asosiadas a un jefe de labor
	 */
	public void cargaGrupoJefeAsesoria() {
		this.docenteAsesoriaData.clear();
		evalJefeLaborBO = new EvaluacionJefeLaborBO();
		this.setDocenteAsesoriaData(this.evalJefeLaborBO
				.loadMateriasAsesoria(idJefe));
		// se establece la ruta del archivo de la capa de vista que desplegará
		// la información.
		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteAsesoria.xhtml");

	}

	/**
	 * Método utilizado para cargar el nombre de una investigación específica
	 * @param codigoVRI
	 * @return
	 */
	public ArrayList<String> nombreProyecto2(String codigoVRI) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, codigoVRI, "investigacion"));
		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteInvestigacion.xhtml");
		return docentesInv;
	}

	/**
	 * Arreglo utilizado para cargar los docentes pertenecientes a una investigación
	 * que ya fueron evaluados
	 */
	private ArrayList<String> docentesEliminarInvestigacion = new ArrayList<String>();

	public ArrayList<String> getDocentesEliminarInvestigacion() {
		return docentesEliminarInvestigacion;
	}

	public void setDocentesEliminarInvestigacion(
			ArrayList<String> docentesEliminarInvestigacion) {
		this.docentesEliminarInvestigacion = docentesEliminarInvestigacion;
	}

	/**
	 * Método utilizado para cargar los docentes que aún no han sido evaluados en una 
	 * investigación específica
	 * @param codigoVRI
	 * @return
	 */

	public ArrayList<String> listaDocentesLabor2(String codigoVRI) {
		evaldocBO = new EvaluacionDocenteBO();
		docentesInv.clear();
		this.setDocentesInv(this.evaldocBO.loadDocentes(codigoVRI,
				departamento, periodo, "investigacion"));
		this.setDocentesEliminarInvestigacion(this.evalJefeLaborBO
				.loadDocInvestigacionEvaluado(codigoVRI));
		for (int i = 0; i < docentesInv.size(); i++) {
			if (docentesInv.get(i).equalsIgnoreCase(nombreUsuario(idJefe))) {
				docentesInv.remove(i);
				break;
			}
		}

		ArrayList<String> doc = new ArrayList<String>();
		boolean encontrado = false;
		for (int i = 0; i < docentesInv.size(); i++) {
			encontrado = false;
			for (int j = 0; j < docentesEliminarInvestigacion.size(); j++) {
				System.out.println("eliminar for: " + docentesInv.get(i));
				if (docentesInv.get(i).equalsIgnoreCase(
						docentesEliminarInvestigacion.get(j))) {
					System.out.println("eliminar for de adentro: "
							+ docentesInv.get(i) + "Evaluados: "
							+ docentesEliminarInvestigacion.get(j));
					j = docentesEliminarInvestigacion.size();
					encontrado = true;
				}
			}
			if (!encontrado){
				doc.add(docentesInv.get(i));
			}
		}

		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteInvestigacion.xhtml");
		return doc;
	}
	/**
	 * Método utilizado para cargar el nombre de una asesoría específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> nombreAsesoria2(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, actividad, "asesoria"));
		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteAsesoria.xhtml");
		docentesAs = docentesInv;
		return docentesInv;
	}

	/**
	 * Arreglo utilizado para cargar los docentes pertenecientes a una asesoría
	 * que ya fueron evaluados
	 */
	private ArrayList<String> docentesEliminarAsesoria = new ArrayList<String>();

	public ArrayList<String> getDocentesEliminarAsesoria() {
		return docentesEliminarAsesoria;
	}

	public void setDocentesEliminarAsesoria(
			ArrayList<String> docentesEliminarAsesoria) {
		this.docentesEliminarAsesoria = docentesEliminarAsesoria;
	}

	/**
	 * Método utilizado para cargar los docentes que aún no han sido evaluados en una
	 * asesoría específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> listaDocentesAsesoria2(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		docentesInv.clear();
		this.setDocentesInv(this.evaldocBO.loadDocentes(actividad,
				departamento, periodo, "asesoria"));
		this.setDocentesEliminarAsesoria(this.evalJefeLaborBO
				.loadDocAsesoriaEvaluado(actividad));
		for (int i = 0; i < docentesInv.size(); i++) {
			if (docentesInv.get(i).equalsIgnoreCase(nombreUsuario(idJefe))) {
				docentesInv.remove(i);
				break;
			}
		}
		ArrayList<String> doc = new ArrayList<String>();
		boolean encontrado = false;
		for (int i = 0; i < docentesInv.size(); i++) {
			encontrado = false;
			for (int j = 0; j < docentesEliminarAsesoria.size(); j++) {
				System.out.println("eliminar for: " + docentesInv.get(i));
				if (docentesInv.get(i).equalsIgnoreCase(
						docentesEliminarAsesoria.get(j))) {
					System.out.println("eliminar for de adentro: "
							+ docentesInv.get(i) + "Evaluados: "
							+ docentesEliminarAsesoria.get(j));
					j = docentesEliminarAsesoria.size();
					encontrado = true;
				}
			}
			if (!encontrado){
				doc.add(docentesInv.get(i));
			}
		}
		this.setRuta("/administrativo/evaluacionDocente/jefeLabor/CargaDocenteAsesoria.xhtml");
		return doc;
	}
	/**
	 * Atributos utilizados para guardar la labor que fue evaluada y el docente a quien se evaluó
	 */
	private String laborEvaluar;
	private String profesorEvaluar;

	public String getLaborEvaluar() {
		return laborEvaluar;
	}

	public void setLaborEvaluar(String laborEvaluar) {
		this.laborEvaluar = laborEvaluar;
	}

	public String getProfesorEvaluar() {
		return profesorEvaluar;
	}

	public void setProfesorEvaluar(String profesorEvaluar) {
		this.profesorEvaluar = profesorEvaluar;
	}

	/**
	 * Método utilizado para guardar los datos de la labor que fue evaluada y el docente 
	 * a quién se evaluó
	 * @param labor
	 * @param profesor
	 */
	public void SaveDatos(String labor, String profesor) {
		this.laborEvaluar = labor;
		this.profesorEvaluar = profesor;
		System.out.println("Profesor: " + profesorEvaluar + " Labor: "
				+ laborEvaluar);
	}

	private String docentes;

	public String getDocentes() {
		return docentes;
	}

	public void setDocentes(String docentes) {
		this.docentes = docentes;
	}

	/**
	 * Objeto utilizado para obtener la fecha actual
	 */
	private java.util.Date fecha = new Date();

	public java.util.Date getFecha() {
		return fecha;
	}

	public void setFecha(java.util.Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Arreglo utilizado para cargar las preguntas correspondientes a cada cuestionario
	 */
	private ArrayList<String> listaPreguntas = new ArrayList<String>();

	public ArrayList<String> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(ArrayList<String> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	/**
	 * Método utilizado para cargar las preguntas desde la BD dependiendo el tipo de labor a evaluar
	 * 1 : para Asesoría
	 * 2 : para Investigación 
	 * @param opc
	 * @return
	 */
	public ArrayList<String> cargaListaPreguntas(String opc) {
		evalJefeLaborBO = new EvaluacionJefeLaborBO();
		this.listaPreguntas.clear();
		this.setListaPreguntas(this.evalJefeLaborBO.loadListaPreguntas(opc));
		return listaPreguntas;
	}

	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
	/**
	 * Atributo utilizado para guardar el nombre del usuario que inició sesión
	 */
	private String nombreUsuario;

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	/**
	 * Método utilizado para obtener el nombre de usuario que inició sesión
	 * @param id
	 * @return
	 */
	public String nombreUsuario(String id) {
		evalJefeLaborBO = new EvaluacionJefeLaborBO();
		System.out.println("Identificacion: " + getUsuario().getIdDocente());
		this.setNombreUsuario(this.evalJefeLaborBO.loadNombreUsuario(getUsuario().getIdDocente()));
		return nombreUsuario;
	}

	/**
	 * Método utilizado para guardar la labor que se evaluó, el nombre del evaluado y evaluador, el perido y la calificación
	 * @param labor
	 * @param evaluado
	 * @param evaluador
	 * @param periodo
	 * @param calificacion
	 */
	public void SaveDatos2(String labor, String evaluado, String evaluador,
			int periodo, int calificacion) {
		System.out.println("Labor: " + labor + " Evaluado: " + evaluado
				+ " Evaluador:" + evaluador + " periodo:" + periodo
				+ " calificaion:" + calificacion);
	}

	/**
	 * Atributo utilizado para guardar la cantidad de preguntas, dicha cantidad varía según el cuestionario
	 */
	private int cantidad = -1;

	/**
	 * Arreglo utilizado para guardar las calificaciones del cuestionario 
	 */
	private ArrayList<Integer> calificaciones;

	public ArrayList<Integer> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(ArrayList<Integer> calificaciones) {
		this.calificaciones = calificaciones;
	}

	/**
	 * Método utilizado para cargar desde la vista las calificaciones a cada pregunta del cuestionario
	 * @param event
	 */
	public void updateCalificacion(ValueChangeEvent event) {
		cantidad = (Integer) event.getNewValue();
		System.out.println("cantidad: " + cantidad);
		if (calificaciones == null) {
			calificaciones = new ArrayList<Integer>();
			calificaciones.add(cantidad);
		} else {
			calificaciones.add(cantidad);
		}
		cantidad = -1;
		System.out.println("tamaño " + calificaciones.size());
	}

	/**
	 * Método utilizado para guardar los datos correspondientes de una evaluación a un integrante de una asesoría
	 * @param tipoCuest
	 * @param labor
	 * @param evaluado
	 * @param actividad
	 * @param opcion
	 */
	public void mostrarCalificacionAsesoria(String tipoCuest, String labor,
			String evaluado, String actividad, String opcion) {
		for (int i = 0; i < calificaciones.size(); i++) {
			System.out.print(calificaciones.get(i));
		}
		System.out.println("pre: " + listaPreguntas.size() + " y cal: "
				+ calificaciones.size());

		if (listaPreguntas.size() == calificaciones.size()) {
			evalJefeLaborBO = new EvaluacionJefeLaborBO();
			this.evalJefeLaborBO.createCuestionario(tipoCuest, labor, evaluado,
					nombreUsuario(idJefe), periodo, calificaciones);
			System.out.print("todas las preguntas constestadas");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se registro correctamente la evaluacion para: "
									+ evaluado + "", ""));

			number = 100;
			console = null;
			calificaciones.clear();

			if (opcion.equalsIgnoreCase("JefesAsesoria")) {
				cargaJefesAsesoria(labor);
			} else if (opcion.equalsIgnoreCase("JefesDepto")) {
				cargaJefeDepto(labor);
			}

		} else {
			// MENSAJE
			System.out.print("faltan pregutas por contestar");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Hubo un error en la evaluacion, debido a que no se califaron todos los items",
									""));
		}

		number = 100;
		console = null;
		calificaciones.clear();
	}
	
	/**
	 * Método utilizado para guardar los datos correspondientes de la evaluación a un integrante de una investigación
	 * @param tipoCuest
	 * @param labor
	 * @param evaluado
	 * @param codigoVRI
	 * @param opcion
	 */
	public void mostrarCalificacionInvestigacion(String tipoCuest,
			String labor, String evaluado, String codigoVRI, String opcion) {
		for (int i = 0; i < calificaciones.size(); i++) {
			System.out.print(calificaciones.get(i));
		}
		System.out.println("pre: " + listaPreguntas.size() + " y cal: "
				+ calificaciones.size());

		if (listaPreguntas.size() == calificaciones.size()) {
			evalJefeLaborBO = new EvaluacionJefeLaborBO();
			this.evalJefeLaborBO.createCuestionario(tipoCuest, labor, evaluado,
					nombreUsuario(idJefe), periodo, calificaciones);
			System.out.print("todas las preguntas constestadas");
			// listaDocentesLabor2(codigoVRI, evaluado);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Se registro correctamente la evaluacion para: "
									+ evaluado + "", ""));

			number = 100;
			console = null;
			calificaciones.clear();

			if (opcion.equalsIgnoreCase("servicio")) {
				cargaDocenteServicio(nombreUsuario(idJefe));
			} else if (opcion.equalsIgnoreCase("capacitacion")) {
				cargaDocenteCapacitacion(nombreUsuario(idJefe));
			} else if (opcion.equalsIgnoreCase("extension")) {
				cargaDocenteExtension(nombreUsuario(idJefe));
			} else if (opcion.equalsIgnoreCase("JefesInvestigacion")) {
				cargaJefesInvestigacion(labor);
			}

		} else {
			// MENSAJE
			System.out.print("faltan pregutas por contestar");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Hubo un error en la evaluacion, debido a que no se califaron todos los items",
									""));
		}

		number = 100;
		console = null;
		calificaciones.clear();
	}

	/**
	 * Aquí empieza la lógiga de autoevaluación 
	 */
	
	private Integer console;

	public Integer getConsole() {
		return console;
	}

	public void setConsole(Integer console) {
		this.console = console;
	}	
	/* INICIO METODOS AUTOEVALUACION*/
	public void loadLaborDataAutoevaluacionDocencia(){
		this.docenteDocenciaData.clear();
		evaldocBO = new EvaluacionDocenteBO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		Integer docente = 393; //Docente al que se le va a cargar su autoevaluación
		
		this.setDocenteDocenciaData(this.evaldocBO.loadDocenteDocencias(departamento, periodo,docente));
		
		this.setAuxiliar(this.evaldocBO.obtenerAutoevaluacionDocencia(Integer.toString(periodo), Integer.toString(docente)));
		
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborDocenciaDirecta.xhtml");
	}
	
	private ArrayList<Auxiliar> auxiliarAsesoria = new ArrayList<Auxiliar>();
		
	
	public ArrayList<Auxiliar> getAuxiliarAsesoria() {
		return auxiliarAsesoria;
	}

	public void setAuxiliarAsesoria(ArrayList<Auxiliar> auxiliarAsesoria) {
		this.auxiliarAsesoria = auxiliarAsesoria;
	}

	public void loadLaborDataAutoevaluacionAsesoria(){
		this.docenteAsesoriaData.clear();
		evaldocBO = new EvaluacionDocenteBO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		Integer docente = 393; //Docente al que se le va a cargar su autoevaluación
		this.setDocenteAsesoriaData(this.evaldocBO.loadDocenteAsesorias(departamento, periodo,docente));
		this.setAuxiliarAsesoria(this.evaldocBO.obtenerAutoevaluacionAsesoria(Integer.toString(periodo), Integer.toString(docente)));
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborAsesoria.xhtml");
	}
	
	private ArrayList<Auxiliar> auxiliarInvestigacion = new ArrayList<Auxiliar>();
	
	public ArrayList<Auxiliar> getAuxiliarInvestigacion() {
		return auxiliarInvestigacion;
	}

	public void setAuxiliarInvestigacion(ArrayList<Auxiliar> auxiliarInvestigacion) {
		this.auxiliarInvestigacion = auxiliarInvestigacion;
	}
	
	public void loadLaborDataAutoevaluacionInvestigacion(){
		this.docenteInvestigacionData.clear();
		evaldocBO = new EvaluacionDocenteBO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		Integer docente = 393; //Docente al que se le va a cargar su autoevaluación
		this.setDocenteInvestigacionData(this.evaldocBO.loadDocenteInvestigacion(departamento, periodo,docente));
		this.setAuxiliarInvestigacion(this.evaldocBO.obtenerAutoevaluacionInvestigacion(Integer.toString(periodo), Integer.toString(docente)));
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborInvestigacion.xhtml");
	}
	
	private ArrayList<Auxiliar> auxiliarTrbInvest = new ArrayList<Auxiliar>();
	
	public ArrayList<Auxiliar> getAuxiliarTrbInvest() {
		return auxiliarTrbInvest;
	}

	public void setAuxiliarTrbInvest(ArrayList<Auxiliar> auxiliar) {
		this.auxiliarTrbInvest = auxiliar;
	}
	
	public void loadLaborDataAutoevaluacionTrabajosInvestigacion(){
		this.docenteTrabajosInvestigacionData.clear();
		evaldocBO = new EvaluacionDocenteBO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		Integer docente = 557; //Docente al que se le va a cargar su autoevaluación
		System.out.println("pasa");
		this.setDocenteTrabajosInvestigacionData(this.evaldocBO.loadDocenteTrabajosInvestigaciones(departamento, periodo,docente));
		System.out.println("pasa");
		this.setAuxiliarTrbInvest(this.evaldocBO.obtenerAutoevaluacionTrabajosInvestigacion(Integer.toString(periodo), Integer.toString(docente)));
		System.out.println("pasa");
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborTrabajosInvestigacion.xhtml");
	}
	
	private ArrayList<Auxiliar> auxiliarAdmin = new ArrayList<Auxiliar>();
	
	public ArrayList<Auxiliar> getAuxiliarAdmin() {
		return auxiliarAdmin;
	}

	public void setAuxiliarAdmin(ArrayList<Auxiliar> auxiliarAdmin) {
		this.auxiliarAdmin = auxiliarAdmin;
	}
	
	public void loadLaborDataAutoevaluacionAdministracion(){
		this.docenteAdministracionData.clear();
		evaldocBO = new EvaluacionDocenteBO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		Integer docente = 393; //Docente al que se le va a cargar su autoevaluación
		this.setDocenteAdministracionData(this.evaldocBO.loadDocenteAdministracion(departamento, periodo,docente));
		this.setAuxiliarAdmin(evaldocBO.obtenerAutoevaluacionAdministracion(Integer.toString(periodo), Integer.toString(docente)));
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborAdministracion.xhtml");
	}
	
	private List<String> calificacion;
	
	public List<String> getCalificacion(){
		return calificacion;
	}
	
	public void guardarAutoevaluacionDocencia(String identificacion, String periodo) {
		auxiliar.clear();
		ArrayList<Integer> oiddocencia = new ArrayList<Integer>();
		int i =0;
		for( i = 0; i < this.docenteDocenciaData.size(); i++){
			oiddocencia.add(this.docenteDocenciaData.get(i).getOid());
		}
		if(calificaciones == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
		}else{
			if(calificaciones.size() == this.docenteDocenciaData.size()){
				//preguntar si ya se hizo la autoevaluacion para este periodo, si ya existe hacer update, sino, hacer un insert
				this.setAuxiliar(this.evaldocBO.obtenerAutoevaluacionDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
				if(this.getAuxiliar().get(0).getCantidad() == 0) {
					//insertar
					this.evaldocBO.ingresarCalificacionDocencia(oiddocencia, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro correctamente la evaluacion", ""));
				}else {
					this.evaldocBO.actualizarCalificacionDocencia(oiddocencia, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoevaluacion actualizada correctamente", ""));
				}
				console= null;
			} else {
				System.out.print("faltan pregutas por contestar");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
			}
		}
		if(calificaciones != null){
			calificaciones.clear();
		}			
		this.setAuxiliar(this.evaldocBO.obtenerAutoevaluacionDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
		oiddocencia.clear();
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborDocenciaDirecta.xhtml");
	}	
	
	public void guardarAutoevaluacionInvestigacion(String identificacion, String periodo) {
		auxiliarInvestigacion.clear();
		ArrayList<Integer> oidinvestigacion = new ArrayList<Integer>();
		for(int i = 0; i < this.docenteInvestigacionData.size(); i++){
			oidinvestigacion.add(this.docenteInvestigacionData.get(i).getOid());
		}
		if(calificaciones == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
		} else {
			if(calificaciones.size() == this.docenteInvestigacionData.size()) {
				this.setAuxiliarInvestigacion(this.evaldocBO.obtenerAutoevaluacionInvestigacion(this.docenteInvestigacionData.get(0).getPeriodo(), this.docenteInvestigacionData.get(0).getIdentificacion()));
				if(this.getAuxiliarInvestigacion().get(0).getCantidad() == 0) {
					//insertar
					this.evaldocBO.ingresarCalificacionInvestigacion(oidinvestigacion, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro correctamente la evaluacion", ""));
				}else {
					this.evaldocBO.actualizarCalificacionInvestigacion(oidinvestigacion, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoevaluacion actualizada correctamente", ""));
				}
				console= null;
			} else {
				System.out.print("faltan pregutas por contestar");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
			}
		}
		if(calificaciones != null){
			calificaciones.clear();
		}
		oidinvestigacion.clear();
		this.setAuxiliarInvestigacion(this.evaldocBO.obtenerAutoevaluacionInvestigacion(this.docenteInvestigacionData.get(0).getPeriodo(), this.docenteInvestigacionData.get(0).getIdentificacion()));
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborInvestigacion.xhtml");
	}
	
	public void guardarAutoevaluacionAdministracion(String identificacion, String periodo){
		auxiliarAdmin.clear();
		ArrayList<Integer> oidadministracion = new ArrayList<Integer>();
		for(int i = 0; i < this.docenteAdministracionData.size(); i++){
			oidadministracion.add(this.docenteAdministracionData.get(i).getIden());
		}
		if(calificaciones == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
		} else {
			if(calificaciones.size() == this.docenteAdministracionData.size()) {
				this.setAuxiliarAdmin(this.evaldocBO.obtenerAutoevaluacionAdministracion(this.docenteAdministracionData.get(0).getPeriodo(), this.docenteAdministracionData.get(0).getIdentificacion()));
				if(this.getAuxiliarAdmin().get(0).getCantidad() == 0) {
					//insertar
					this.evaldocBO.ingresarCalificacionAdministraciones(oidadministracion, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro correctamente la evaluacion", ""));
				}else {
					this.evaldocBO.actualizarCalificacionAdministracion(oidadministracion, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoevaluacion actualizada correctamente", ""));
				}
				console= null;
			} else {
				System.out.print("faltan pregutas por contestar");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
			}
		}
		if(calificaciones != null){
			calificaciones.clear();
		}
		this.setAuxiliarAdmin(this.evaldocBO.obtenerAutoevaluacionAdministracion(this.docenteAdministracionData.get(0).getPeriodo(), this.docenteAdministracionData.get(0).getIdentificacion()));
		oidadministracion.clear();
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborAdministracion.xhtml");
	}
	
	public void guardarAutoevaluacionAsesoria(String identificacion, String periodo) {
		auxiliarAsesoria.clear();
		ArrayList<Integer> oidasesoria = new ArrayList<Integer>();
		for(int i = 0; i < this.docenteAsesoriaData.size(); i++){
			oidasesoria.add(this.docenteAsesoriaData.get(i).getIden());
		}
		if(calificaciones == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
		} else {
			if(calificaciones.size() == this.docenteAsesoriaData.size()) {
				this.setAuxiliarAsesoria(this.evaldocBO.obtenerAutoevaluacionAsesoria(this.docenteAsesoriaData.get(0).getPeriodo(), this.docenteAsesoriaData.get(0).getIdentificacion()));
				if(this.getAuxiliarAsesoria().get(0).getCantidad() == 0) {
					//insertar
					this.evaldocBO.ingresarCalificacionAsesorias(oidasesoria, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro correctamente la evaluacion", ""));
				}else {
					this.evaldocBO.actualizarCalificacionAsesoria(oidasesoria, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoevaluacion actualizada correctamente", ""));
				}
				console= null;
			} else {
				System.out.print("faltan pregutas por contestar");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
			}
		}	
		if(calificaciones != null){
			calificaciones.clear();
		}
		oidasesoria.clear();
		this.setAuxiliarAsesoria(this.evaldocBO.obtenerAutoevaluacionAsesoria(this.docenteAsesoriaData.get(0).getPeriodo(), this.docenteAsesoriaData.get(0).getIdentificacion()));
	}
	
	public void guardarAutoevaluacionTrabajosInvestigacion(String identificacion, String periodo){
		auxiliarTrbInvest.clear();
		ArrayList<Integer> oidtrbinves = new ArrayList<Integer>();
		for(int i = 0; i < this.docenteTrabajosInvestigacionData.size(); i++){
			oidtrbinves.add(this.docenteTrabajosInvestigacionData.get(i).getIden());
		}
		if(calificaciones == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
		} else {
			if(calificaciones.size() == this.docenteTrabajosInvestigacionData.size()) {
				this.setAuxiliarTrbInvest(this.evaldocBO.obtenerAutoevaluacionTrabajosInvestigacion(this.docenteTrabajosInvestigacionData.get(0).getPeriodo(), this.docenteTrabajosInvestigacionData.get(0).getIdentificacion()));
				if(this.getAuxiliarTrbInvest().get(0).getCantidad() == 0) {
					//insertar
					this.evaldocBO.ingresarCalificacionTrabajoInvestigacion(oidtrbinves, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro correctamente la evaluacion", ""));
				}else {
					this.evaldocBO.actualizarCalificacionTrabajoInvestigacion(oidtrbinves, identificacion, periodo, calificaciones);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Autoevaluacion actualizada correctamente", ""));
				}
				console= null;
			} else {
				System.out.print("faltan pregutas por contestar");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe llenar todos los campos", ""));
			}
		}
		if(calificaciones != null){
			calificaciones.clear();
		}			
		this.setAuxiliarTrbInvest(this.evaldocBO.obtenerAutoevaluacionTrabajosInvestigacion(this.docenteTrabajosInvestigacionData.get(0).getPeriodo(), this.docenteTrabajosInvestigacionData.get(0).getIdentificacion()));
		oidtrbinves.clear();
		this.setRuta("/administrativo/evaluacionDocente/autoevaluacionDocente/cargarLaborTrabajosInvestigacion.xhtml");
	}
	
	private String obs;
	
	
	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public void updateObs(ValueChangeEvent event){
		obs = (String) event.getNewValue();
		System.out.println("observacion: "+obs);		
	}
	
	private ArrayList<Auxiliar> auxiliar = new ArrayList<Auxiliar>();
	
	public ArrayList<Auxiliar> getAuxiliar() {
		return auxiliar;
	}

	public void setAuxiliar(ArrayList<Auxiliar> auxiliar) {
		this.auxiliar = auxiliar;
	}
	
	public void guardarObservacionDocencia(){
		if(obs=="" || obs==null){
			System.out.println("TextArea vacío");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha escrito ninguna observación", ""));
		} else {
			this.setAuxiliar(this.evaldocBO.obtenerObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
			if(this.getAuxiliar().get(0).getCantidad() == 0) {
				//this.evaldocBO.insertarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				this.evaldocBO.insertarObservacionDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación registrada", ""));
			} else {
				this.evaldocBO.actualizarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación actualizada", ""));
			}
			auxiliar.clear();
		}
		obs = "";
	}
	
	public void guardarObservacionAdmin(){
		if(obs=="" || obs==null){
			System.out.println("TextArea vacío");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha escrito ninguna observación", ""));
		} else {
			this.setAuxiliar(this.evaldocBO.obtenerObsAdmin(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
			if(this.getAuxiliar().get(0).getCantidad() == 0) {
				//this.evaldocBO.insertarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				this.evaldocBO.insertarObservacionAdmin(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación registrada", ""));
			} else {
				this.evaldocBO.actualizarObsAdministracion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación actualizada", ""));
			}
		}
		obs = "";
	}
	
	public void guardarObservacionAsesoria(){
		if(obs=="" || obs==null){
			System.out.println("TextArea vacío");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha escrito ninguna observación", ""));
		} else {
			this.setAuxiliar(this.evaldocBO.obtenerObsAsesoria(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
			if(this.getAuxiliar().get(0).getCantidad() == 0) {
				//this.evaldocBO.insertarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				this.evaldocBO.insertarObservacionAsesoria(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación registrada", ""));
			} else {
				this.evaldocBO.actualizarObsAsesoria(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación actualizada", ""));
			}
		}
		obs = "";
	}
	
	public void guardarObservacionInvestigacion(){
		if(obs=="" || obs==null){
			System.out.println("TextArea vacío");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha escrito ninguna observación", ""));
		} else {
			this.setAuxiliar(this.evaldocBO.obtenerObsInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
			if(this.getAuxiliar().get(0).getCantidad() == 0) {
				//this.evaldocBO.insertarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				this.evaldocBO.insertarObservacionInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación registrada", ""));
			} else {
				this.evaldocBO.actualizarObsInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación actualizada", ""));
			}
		}
		obs = "";
	}
	
	public void guardarObservacionTrbInvestigacion(){
		if(obs=="" || obs==null){
			System.out.println("TextArea vacío");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No ha escrito ninguna observación", ""));
		} else {
			this.setAuxiliar(this.evaldocBO.obtenerObsTrbInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion()));
			if(this.getAuxiliar().get(0).getCantidad() == 0) {
				//this.evaldocBO.insertarObsDocencia(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				this.evaldocBO.insertarObservacionTrbInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación registrada", ""));
			} else {
				this.evaldocBO.actualizarObsTrbInvestigacion(this.docenteDocenciaData.get(0).getPeriodo(), this.docenteDocenciaData.get(0).getIdentificacion(), obs);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Observación actualizada", ""));
			}
		}
		obs = "";
	}
	/*FIN METODOS AUTOEVALUACION*/
	
	
	
	/*INICIO METODOS REPORTES*/
	
	private ArrayList<nombreDocentes> nombreDocente = new ArrayList<nombreDocentes>();;
	
	public ArrayList<nombreDocentes> getNombreDocentes() {
		return nombreDocente;
	}

	public void setNombreDocentes(ArrayList<nombreDocentes> nombreDocentes) {
		this.nombreDocente = nombreDocentes;
	}
	
	public void generarReporte() throws NumberFormatException, SQLException {
		ArrayList<String> nombreTabla = new ArrayList<String>();
		this.rep = new ArrayList<reportes>();
		nombreTabla.add("docentedocencia");
		nombreTabla.add("docenteasesoria");
		nombreTabla.add("docenteadministracion");
		nombreTabla.add("docenteinvestigacion");
		nombreTabla.add("docentetrabajosinvestigacion");
		ArrayList<String> tablasAutoevaluacion = new ArrayList<String>();
		tablasAutoevaluacion.add("autoevaluaciondocencia");
		tablasAutoevaluacion.add("autoevaluacionase");
		tablasAutoevaluacion.add("autoevaluacionadmin");
		tablasAutoevaluacion.add("autoevaluacioninvest");		
		tablasAutoevaluacion.add("autoevaluaciontinvest");
		String departamento="1193003"; // Departamento de sistemas
		int periodo = 82; // Periodo 2015-2 
		EvaluacionDocenteDAO objDAO = new EvaluacionDocenteDAO();
		AutoEvaluacionDocenteDAO objADAO = new AutoEvaluacionDocenteDAO();
		//Crear una lista donde se almacenarán las autoevaluaciones
		ArrayList<Double> autoevaluacionesConsolidadas = new ArrayList<Double>();
		//Crear una lista donde se almacenarán las evaluaciones del jefe
		ArrayList<Integer> evaluacionesConsolidadas = new ArrayList<Integer>();
		//Sacar un vector con el nombre de todos los docentes del programa
		ArrayList<String> docentes = new ArrayList<String>();
		
		docentes.addAll(objDAO.obtenerDocentes(departamento, periodo));
		//Obtencion de registro consolidado de autoevaluacion
		ArrayList<Integer> cantidadLabor = new ArrayList<Integer>();
		ArrayList<String> identificacion = new ArrayList<String>();
		for(int i = 0; i < docentes.size(); i++) {
			//Obtener id del docente
			int cant = 0;
			int suma = 0;
			identificacion.clear();
			identificacion.addAll(objDAO.obtenerIdDocente(docentes.get(i)));
			System.out.println("Identificacion: "+ identificacion.get(0)); //Se obtiene la identificacion del docente
			//Por cada docente obtener la cantidad de labor asignada (para hacer un promedio)
			ArrayList<Auxiliar> aux = new ArrayList<Auxiliar>();
			//Cuenta la cantidad de labor de docencia para cada docente 
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(0)));
			if(aux.get(0).getCantidad()>0){ //Pregunta si tiene labor asignada en docencia
				ArrayList<Auxiliar> aux1 = new ArrayList<Auxiliar>();
				//Calcula la suma de sus autoevaluaciones en docencia para el periodo actual
				aux1.addAll(objADAO.sumaAutoevaluacion(identificacion.get(0), periodo, tablasAutoevaluacion.get(0)));
				suma = suma + aux1.get(0).getCantidad();
			}
			cant = cant + aux.get(0).getCantidad();
			//Cuenta la cantidad de labor de asesoria para cada docente 
			aux.clear();
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(1)));
			if(aux.get(0).getCantidad()>0){ //Pregunta si tiene labor asignada en asesria
				ArrayList<Auxiliar> aux1 = new ArrayList<Auxiliar>();
				//Calcula la suma de sus autoevaluaciones en asesoria para el periodo actual
				aux1.addAll(objADAO.sumaAutoevaluacion(identificacion.get(0), periodo, tablasAutoevaluacion.get(1)));
				suma = suma + aux1.get(0).getCantidad();
			}
			cant = cant + aux.get(0).getCantidad();
			//Cuenta la cantidad de labor de administracion para cada docente
			aux.clear();
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(2)));
			if(aux.get(0).getCantidad()>0){//Pregunta si tiene labor asignada en administracion
				ArrayList<Auxiliar> aux1 = new ArrayList<Auxiliar>();
				//Calcula la suma de sus autoevaluaciones en administracion para el periodo actual
				aux1.addAll(objADAO.sumaAutoevaluacion(identificacion.get(0), periodo, tablasAutoevaluacion.get(2)));
				suma = suma + aux1.get(0).getCantidad();
			}
			cant = cant + aux.get(0).getCantidad();
			//Cuenta la cantidad de labor de investigacion para cada docente
			aux.clear();
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(3)));
			if(aux.get(0).getCantidad()>0){
				ArrayList<Auxiliar> aux1 = new ArrayList<Auxiliar>();
				aux1.addAll(objADAO.sumaAutoevaluacion(identificacion.get(0), periodo, tablasAutoevaluacion.get(3)));
				suma = suma + aux1.get(0).getCantidad();
			}
			cant = cant + aux.get(0).getCantidad();
			//Cuenta la cantidad de labor de trabajos de investigacion para cada docente
			aux.clear();
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(4)));
			if(aux.get(0).getCantidad()>0){
				ArrayList<Auxiliar> aux1 = new ArrayList<Auxiliar>();
				aux1.addAll(objADAO.sumaAutoevaluacion(identificacion.get(0), periodo, tablasAutoevaluacion.get(4)));
				suma = suma + aux1.get(0).getCantidad();
			}
			cant = cant + aux.get(0).getCantidad();
			cantidadLabor.add(cant);
			//Obtener la suma de autoevaluación y evaluación del jefe de labor
			
			//Hallar el promedio para autoevaluación y evaluación del jefe de labor
			aux.clear();
			if(cant != 0){
				autoevaluacionesConsolidadas.add((double) (suma/cant));
			} else {
				docentes.remove(i);
				autoevaluacionesConsolidadas.add((double) 0);
			}
			System.out.println("consolidado: "+autoevaluacionesConsolidadas.get(i));
			
			if(i<30){
				reportes reporte = new reportes();
				reporte.setNombreDocente(docentes.get(i));
				reporte.setConsolidado(autoevaluacionesConsolidadas.get(i));
				this.rep.add(reporte);	
			}
			this.setRuta("administrativo/reportes/reporte.xhtml");
			//Calcular el consolidado de las evaluaciones por parte de sus jefes de labor
			//aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(1)));
			//if(aux.get(0).getCantidad()>0){
			//	ArrayList<Auxiliar> identificadoresLabor = new ArrayList<Auxiliar>();
			//	identificadoresLabor.addAll(objDAO.obtenerIdentificadoresLabor(identificacion.get(0), periodo));
			//}
		}
	}
	
	private ArrayList<reportes> rep;
	
	public ArrayList<reportes> getRep() {
		return rep;
	}

	public void setRep(ArrayList<reportes> rep) {
		this.rep = rep;
	}

	/*FIN METODOS REPORTES*/
	
	

	//llamadas al Modulo de Cuestionarios
	public String gestionCuestionario(){
    	setRuta("/administrativo/cuestionario/gestionCuestionario.xhtml");
    	return ruta;
    }
    
    public String crearCuestionario(){
    	setRuta("/administrativo/cuestionario/crearCuestionario.xhtml");
    	return ruta;
    }
    
    public String asignarCuestionario(){
    	setRuta("/administrativo/cuestionario/asignarCuestionario.xhtml");
    	return ruta;
    }
    
    public String gestionCategoria(){
    	setRuta("/administrativo/cuestionario/gestionCategoria.xhtml");
    	return ruta;
    }
    
    /**
	 * Aquí empieza la evaluación del jefe de departamento
	 */

	/**
	 * Arreglo utilizado pra guardar los docentes/jefes de labor que ya fueron evaluados
	 */
	private ArrayList<String> docentesEliminar = new ArrayList<String>();

	public ArrayList<String> getDocentesEliminar() {
		return docentesEliminar;
	}

	public void setDocentesEliminar(ArrayList<String> docentesEliminar) {
		this.docentesEliminar = docentesEliminar;
	}

	/**
	 * Método utilizado para cargar los docentes pertenecientes a servicio que no han sido evaluados
	 * @param evaluador
	 */
	public void cargaDocenteServicio(String evaluador) {
		try {
			this.docenteServicioData.clear();
			this.docentesEliminar.clear();

			// Se crea el objeto de la capa de lógica
			evaldocBO = new EvaluacionDocenteBO();
			evalJefeLaborBO = new EvaluacionJefeLaborBO();

			String departamento = "1193003"; // Departamento de sistemas
			Integer periodo = 82; // Periodo 2015-2

			// Realiza la consulta y asigna los resultados a los arreglos con la
			// información de laboracademica
			this.setDocenteServicioData(this.evaldocBO.loadDocenteServicio(
					departamento, periodo));
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadDocentesEliminar(evaluador));

			for (int i = 0; i < docenteServicioData.size(); i++) {
				if (docenteServicioData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteServicioData.remove(i);
					break;
				}
			}

			ArrayList<Servicio> doc = new ArrayList<Servicio>();
			boolean encontrado = false;
			for (int i = 0; i < docenteServicioData.size(); i++) {
				encontrado = false;
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteServicioData.get(i).getNombresDocente());
					if (docenteServicioData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteServicioData.get(i) + "Evaluados: "
								+ docentesEliminar.get(j));
						j = docentesEliminar.size();
						encontrado = true;
					}
				}
				if (!encontrado){
					doc.add(docenteServicioData.get(i));
				}
			}
			docenteServicioData = doc;
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/DocenteServicio.xhtml");
	}

	/**
	 * Método utilizado para cargar los docentes pertenecientes a extensión que no han sido evaluados
	 * @param evaluador
	 */
	public void cargaDocenteExtension(String evaluador) {
		try {
			this.docenteExtensionData.clear();
			this.docentesEliminar.clear();

			// Se crea el objeto de la capa de lógica
			evaldocBO = new EvaluacionDocenteBO();
			evalJefeLaborBO = new EvaluacionJefeLaborBO();

			String departamento = "1193003"; // Departamento de sistemas
			Integer periodo = 82; // Periodo 2015-2

			// Realiza la consulta y asigna los resultados a los arreglos con la
			// información de laboracademica
			this.setDocenteExtensionData(this.evaldocBO.loadDocenteExtension(
					departamento, periodo));
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadDocentesEliminar(evaluador));

			for (int i = 0; i < docenteExtensionData.size(); i++) {
				if (docenteExtensionData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteExtensionData.remove(i);
					break;
				}
			}
			ArrayList<Extension> doc = new ArrayList<Extension>();
			boolean encontrado = false;
			for (int i = 0; i < docenteExtensionData.size(); i++) {
				encontrado = false;
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteExtensionData.get(i).getNombresDocente());
					if (docenteExtensionData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteExtensionData.get(i) + "Evaluados: "
								+ docentesEliminar.get(j));
						j = docentesEliminar.size();
						encontrado = true;
					}
				}
				if (!encontrado){
					doc.add(docenteExtensionData.get(i));
				}
			}
			docenteExtensionData = doc;
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/DocenteExtension.xhtml");
	}

	/**
	 * Método utilizado para cargar los docentes pertenecientes a capacitación que no han sido evaluados
	 * @param evaluador
	 */
	public void cargaDocenteCapacitacion(String evaluador) {
		try {
			this.docenteCapacitacionData.clear();
			this.docentesEliminar.clear();

			// Se crea el objeto de la capa de lógica
			evaldocBO = new EvaluacionDocenteBO();
			evalJefeLaborBO = new EvaluacionJefeLaborBO();

			String departamento = "1193003"; // Departamento de sistemas
			Integer periodo = 82; // Periodo 2015-2

			// Realiza la consulta y asigna los resultados a los arreglos con la
			// información de laboracademica
			this.setDocenteCapacitacionData(this.evaldocBO
					.loadDocenteCapacitacion(departamento, periodo));
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadDocentesEliminar(evaluador));

			for (int i = 0; i < docenteCapacitacionData.size(); i++) {
				if (docenteCapacitacionData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteCapacitacionData.remove(i);
					break;
				}
			}

			ArrayList<Capacitacion> doc = new ArrayList<Capacitacion>();
			boolean encontrado = false;
			for (int i = 0; i < docenteCapacitacionData.size(); i++) {
				encontrado = false;
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteCapacitacionData.get(i)
									.getNombresDocente());
					if (docenteCapacitacionData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteCapacitacionData.get(i)
								+ "Evaluados: " + docentesEliminar.get(j));
						j = docentesEliminar.size();
						encontrado = true;
					}
				}
				if (!encontrado){
					doc.add(docenteCapacitacionData.get(i));
				}
			}
			docenteCapacitacionData = doc;
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/DocenteCapacitacion.xhtml");
	}

	/**
	 * Método utilizado para cargar los jefes de labor de Investigación que no han sido evaluados
	 * @param labor
	 */
	public void cargaJefesInvestigacion(String labor) {
		try {
			this.docenteInvestigacionData.clear();
			this.docentesEliminar.clear();
			// Se crea el objeto de la capa de lógica
			evalJefeLaborBO = new EvaluacionJefeLaborBO();
			this.setDocenteInvestigacionData(this.evalJefeLaborBO
					.loadJefesInvestigacion());
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadJefesEliminar(labor));

			for (int i = 0; i < docenteInvestigacionData.size(); i++) {
				if (docenteInvestigacionData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteInvestigacionData.remove(i);
					break;
				}
			}
			ArrayList<Investigacion> doc = new ArrayList<Investigacion>();
			boolean encontrado = false;
			for (int i = 0; i < docenteInvestigacionData.size(); i++) {
				encontrado = false;
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteInvestigacionData.get(i)
									.getNombresDocente());
					if (docenteInvestigacionData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteInvestigacionData.get(i)
								+ "Evaluados: " + docentesEliminar.get(j));
						j = docentesEliminar.size();
						encontrado = true;
					}
				}
				if (!encontrado){
					doc.add(docenteInvestigacionData.get(i));
				}
			}
			docenteInvestigacionData = doc;
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/JefesInvestigacion.xhtml");
	}
	/**
	 * Método utilziado para cargar los jefes de labor de asesoría que no han sido evaluados
	 * @param labor
	 */
	public void cargaJefesAsesoria(String labor) {
		try {
			this.docenteAsesoriaData.clear();
			this.docentesEliminar.clear();
			// Se crea el objeto de la capa de lógica
			evalJefeLaborBO = new EvaluacionJefeLaborBO();
			this.setDocenteAsesoriaData(this.evalJefeLaborBO
					.loadJefesAsesoria());
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadJefesEliminar(labor));

			for (int i = 0; i < docenteAsesoriaData.size(); i++) {
				if (docenteAsesoriaData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteAsesoriaData.remove(i);
					break;
				}
			}
			ArrayList<Asesoria> doc = new ArrayList<Asesoria>();
			boolean encontrado = false;
			for (int i = 0; i < docenteAsesoriaData.size(); i++) {
				encontrado = false;
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteAsesoriaData.get(i).getNombresDocente());
					if (docenteAsesoriaData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteAsesoriaData.get(i) + "Evaluados: "
								+ docentesEliminar.get(j));
						j = docentesEliminar.size();
						encontrado = true;
					}
				}
				if (!encontrado){
					doc.add(docenteAsesoriaData.get(i));
				}
			}
			docenteAsesoriaData = doc;
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/JefesAsesoria.xhtml");
	}

	/**
	 * Método utilizado para cargar el nombre de una investigación específica 
	 * @param codigoVRI
	 * @return
	 */
	public ArrayList<String> nombreProyectoJefe(String codigoVRI) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, codigoVRI, "investigacion"));
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/JefesInvestigacion.xhtml");
		return docentesInv;
	}
	
	/**
	 * Método utilizado para cargar el nombre de una asesoría específica
	 * @param actividad
	 * @return
	 */
	public ArrayList<String> nombreAsesoriaJefe(String actividad) {
		evaldocBO = new EvaluacionDocenteBO();
		this.setDocentesInv(this.evaldocBO.loadNombreProyecto(periodo,
				departamento, actividad, "asesoria"));
		this.setRuta("/administrativo/evaluacionDocente/jefeDepartamento/JefesAsesoria.xhtml");
		docentesAs = docentesInv;
		return docentesInv;
	}

	/**
	 * Método utilziado para cargar los jefes de departamento que no han sido evaluados
	 * @param labor
	 */
	public void cargaJefeDepto(String labor) {
		try {
			this.docenteAdministracionData.clear();
			this.docentesEliminar.clear();

			// Se crea el objeto de la capa de lógica
			evaldocBO = new EvaluacionDocenteBO();
			evalJefeLaborBO = new EvaluacionJefeLaborBO();

			this.setDocenteAdministracionData(this.evalJefeLaborBO
					.loadjefeDepto());
			this.setDocentesEliminar(this.evalJefeLaborBO
					.loadJefesEliminar(labor));
			
			for (int i = 0; i < docenteAdministracionData.size(); i++) {
				if (docenteAdministracionData.get(i).getNombresDocente()
						.equalsIgnoreCase(nombreUsuario(idJefe))) {
					docenteAdministracionData.remove(i);
					break;
				}
			}

			for (int i = 0; i < docenteAdministracionData.size(); i++) {
				for (int j = 0; j < docentesEliminar.size(); j++) {
					System.out.println("eliminar for: "
							+ docenteAdministracionData.get(i).getNombresDocente());
					if (docenteAdministracionData.get(i).getNombresDocente()
							.equalsIgnoreCase(docentesEliminar.get(j))) {
						System.out.println("eliminar for de adentro: "
								+ docenteAdministracionData.get(i) + "Evaluados: "
								+ docentesEliminar.get(j));
						if (docenteAdministracionData.remove(i).equals(true))
							j = docentesEliminar.size();
					}
				}
			}
			
		} catch (Exception e1) {
			System.out.println("Error: " + e1.getMessage());
		}
		this.setRuta("/administrativo/evaluacionDocente/decano/EvalJefeDepartamento.xhtml");
	}
//metodo que cuenta todas las evaluaciones y autoevaluaciones que todos los docentes deben en un periodo y departamento determinado
	public void CantEvaluaciones() throws NumberFormatException, SQLException {
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
		this.docentesAux.clear();
		EvaluacionDocenteDAO objDAO = new EvaluacionDocenteDAO();
		AutoEvaluacionDocenteDAO autoEDAO = new AutoEvaluacionDocenteDAO();
		String departamento="1193003"; // Departamento de sistemas
		Integer periodo = 82; // Periodo 2015-2 
		//Sacar un vector con el nombre de todos los docentes del programa
		ArrayList<String> docentes1 = new ArrayList<String>();
		docentes1.addAll(objDAO.obtenerDocentes(departamento, periodo));
		ArrayList<Auxiliar> aux = new ArrayList<Auxiliar>();
		ArrayList<String> identificacion = new ArrayList<String>();
		//Por cada docente agregar el registro consolidado de autoevaluación y evaluación del jefe de labor
		for(int i = 0; i < docentes1.size(); i++) {		
			int cant = 0;
			int cantE = 0;
			int cantEv = 0;
			//Obtener id del docente
			String nombre = docentes1.get(i);
			identificacion.clear();
			aux.addAll(objDAO.cantidadEvaluaciones(nombre,periodo));
			cantEv=aux.get(0).getCantidad();
			aux.clear();
			identificacion.addAll(objDAO.obtenerIdDocente(nombre));
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(0)));
			cant = cant + aux.get(0).getCantidad();
			aux.clear();
			//Cuenta la cantidad de labor de asesoria para cada docente 
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(1)));
			cant = cant + aux.get(0).getCantidad();
			aux.clear();
			//Cuenta la cantidad de labor de administracion para cada docente
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(2)));
			cant = cant + aux.get(0).getCantidad();
			aux.clear();
			//Cuenta la cantidad de labor de investigacion para cada docente
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(3)));
			cant = cant + aux.get(0).getCantidad();
			aux.clear();
			//Cuenta la cantidad de labor de trabajos de investigacion para cada docente
			aux.addAll(objDAO.cantidadLabor(identificacion.get(0), periodo, departamento, nombreTabla.get(4)));
			cant = cant + aux.get(0).getCantidad();
			aux.clear();
			//Obtener la cantidad de evaluaciones realizadas por cada docente
			//obtener las autoevaluaciones realizadas por docencia
			aux.addAll(autoEDAO.cantidadAutoevaluacion(identificacion.get(0), periodo, departamento, nombreTabla2.get(0)));
			cantE = cantE + aux.get(0).getCantidad();
			aux.clear();
			//obtener las autoevaluaciones realizadas por asesoria			
			aux.addAll(autoEDAO.cantidadAutoevaluacion(identificacion.get(0), periodo, departamento, nombreTabla2.get(1)));
			cantE = cantE + aux.get(0).getCantidad();
			aux.clear();
			//obtener las autoevaluaciones realizadas por administracion
			aux.addAll(autoEDAO.cantidadAutoevaluacion(identificacion.get(0), periodo, departamento, nombreTabla2.get(2)));
			cantE = cantE + aux.get(0).getCantidad();
			aux.clear();
			//obtener las autoevaluaciones realizadas por investigacion
			aux.addAll(autoEDAO.cantidadAutoevaluacion(identificacion.get(0), periodo, departamento, nombreTabla2.get(3)));
			cantE = cantE + aux.get(0).getCantidad();
			aux.clear();
			//obtener las autoevaluaciones realizadas por trabajos de investigacion
			aux.addAll(autoEDAO.cantidadAutoevaluacion(identificacion.get(0), periodo, departamento, nombreTabla2.get(4)));
			cantE = cantE + aux.get(0).getCantidad();
			aux.clear();
			if(cant!=cantE || cant!=cantEv){
				this.docentesAux.add(docentes1.get(i));
			}
			
		}
		this.setRuta("/administrativo/seguimientoControl/SgmtoControlInvestigacion.xhtml");
	}
	/*metodo que ingresa a los docentes que no han completado las 
	 * evaluaciones en una tabla notificaciones para que posteriormente sea otificados
	 */
	public void notificar()throws NumberFormatException, SQLException{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Todos los docentes han sido notificados", ""));

		if(this.docentesAux.size()!=0){
			int i=0;
			while(i<this.docentesAux.size()){
				EvaluacionDocenteDAO objDAO = new EvaluacionDocenteDAO();
				ArrayList<String> identificacion = new ArrayList<String>();
				String nombre = this.docentesAux.get(i);
				identificacion.addAll(objDAO.obtenerIdDocente(nombre));
				if(objDAO.Buscar(identificacion.get(0))){
					objDAO.IngresarNotificacion(identificacion.get(0));
					
				}else{
					i=this.docentesAux.size();
				}
				i++;
			}
			
		}
	}
	public ArrayList<String> getDocentesAux() {
		return docentesAux;
	}

	public void setDocentesAux(ArrayList<String> docentesAux) {
		this.docentesAux = docentesAux;
	}
	
	
}