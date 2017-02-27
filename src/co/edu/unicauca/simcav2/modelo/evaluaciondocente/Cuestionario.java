package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

public class Cuestionario {
	
	private int id;
	private String nombre;
	private String departamento;
	
	
	
	public Cuestionario(int id, String nombre, String departamento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.departamento = departamento;
	}
	public int getId() {return id;	}
	public void setId(int id) {	this.id = id;}
	
	public String getNombre() {	return nombre;	}
	public void setNombre(String nombre) {	this.nombre = nombre;}
	
	public String getDepartamento() {return departamento;}
	public void setDepartamento(String departamento) {	this.departamento = departamento;}
	

}
